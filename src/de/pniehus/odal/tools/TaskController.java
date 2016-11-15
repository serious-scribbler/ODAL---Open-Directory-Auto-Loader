package de.pniehus.odal.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

/**
 * This class is used to control the execution of a task
 * @author Phil Niehus
 *
 */
public class TaskController {

	private final String name;
	private final int numberOfFiles;
	public final boolean keepStructure;
	private final long totalSize;

	private long started;
	private long timeElapsed;

	private long sizeLeft;
	private int filesLeft;

	private RemoteFile files;
	private File outputDirectory;

	private List<TaskMonitor> monitors = new ArrayList<TaskMonitor>();

	private Task t;

	public TaskController(String name, boolean keepStructure, RemoteFile fileTree, File outputDirectory){
		if(fileTree == null){
			throw new IllegalArgumentException("The root directory may not be empty");
		}
		if(outputDirectory.isFile() || !outputDirectory.canWrite()){
			throw new IllegalArgumentException("Output directory invalid or not writeable");
		}
		this.outputDirectory = outputDirectory;
		this.name = name;
		this.keepStructure = true;
		this.files = fileTree;
		
		numberOfFiles = RemoteFile.countFiles((TreeNode) files);
		totalSize = RemoteFile.countSize((TreeNode) files);
		
		t = new Task(this);
		timeElapsed = 0;
		filesLeft = numberOfFiles;
		sizeLeft = totalSize;
	}
	
	/**
	 * Returns the total size of all files that haven't been downloaded yet
	 * @return
	 */
	public long getSizeLeft() {
		return sizeLeft;
	}
	
	/**
	 * Returns the number of files taht haven't been downloaded yet
	 * @return
	 */
	public int getFilesLeft() {
		return filesLeft;
	}
	
	/**
	 * Returns the file tree associated to this TaskController
	 * @return
	 */
	public RemoteFile getFileTree() {
		return files;
	}
	
	/**
	 * Returns the output directory
	 * @return
	 */
	public File getOutputDirectory() {
		return outputDirectory;
	}
	
	/**
	 * Returns the tasks name
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the total number of files the task will download
	 * @return
	 */
	public int getNumberOfFiles() {
		return numberOfFiles;
	}
	
	/**
	 * returns the total size of all files the task will download
	 * @return
	 */
	public long getTotalSize() {
		return totalSize;
	}
	
	/**
	 * Starts the tasks execution
	 */
	public void start() {
		if (t.isRunning)
			return;
		t.setRunningState(true);
		started = System.currentTimeMillis();
		new Thread(t).start();
	}

	/**
	 * Pauses the execution of the task, finishes the currently running download
	 */
	public void pause() {
		t.setRunningState(false);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (t.isRunning)
					;
				timeElapsed += System.currentTimeMillis() - started;
			}
		}).start();
	}

	/**
	 * This method is called by the associated task, if a file has been
	 * successfully downloaded
	 * 
	 * @param size
	 */
	public void fileFinnished(long size) {
		sizeLeft -= size;
		filesLeft--;
		for (TaskMonitor monitor : monitors) {
			monitor.taskUpdated(sizeLeft, filesLeft,
					((t.isRunning) ? timeElapsed
							+ (System.currentTimeMillis() - started)
							: timeElapsed));
		}
		// backup task
	}

	/**
	 * Adds a task monitor to the list of monitors, which will receive updates,
	 * everytime a file has been downloaded
	 * 
	 * @param m
	 */
	public void addMonitor(TaskMonitor m) {
		monitors.add(m);
	}

	/**
	 * Removes the given monitor from the list of monitors
	 * 
	 * @param m
	 */
	public void removeMonitor(TaskMonitor m) {
		if (monitors.contains(m))
			monitors.remove(m);
	}
}
