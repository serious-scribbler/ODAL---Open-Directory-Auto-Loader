package de.pniehus.odal.logic;

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
	
	// TODO add a list and a method to store errors, maybe error listeners
	
	private RemoteFile files;
	private File outputDirectory;

	private List<TaskMonitor> monitors = new ArrayList<TaskMonitor>();

	private Task t;
	
	/**
	 * Creates and manages a task controller for a download with the following params
	 * @param name The name of the task
	 * @param keepStructure Determines, if the original file structure will be kept
	 * @param fileTree The file structure which will be downloaded
	 * @param outputDirectory The path, where the downloaded files will be saved
	 */
	public TaskController(String name, boolean keepStructure, RemoteFile fileTree, File outputDirectory){
		if(fileTree == null){
			throw new IllegalArgumentException("The root directory may not be empty");
		}
		if(outputDirectory.isFile() || !outputDirectory.canWrite()){
			throw new IllegalArgumentException("Output directory invalid or not writeable");
		}
		this.outputDirectory = outputDirectory;
		this.name = name;
		this.keepStructure = keepStructure;
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
				while (t.isRunning);
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
	 * This method is used to receive error messages from tasks
	 * @param message
	 */
	public void errorDetected(String message){
		for(TaskMonitor monitor : monitors){
			monitor.errorOccured(message);
		}
	}
	
	/**
	 * Adds a task monitor to the list of monitors, which will receive updates,
	 * everytime a file has been successfully downloaded or an error occurred
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
