package de.pniehus.odal.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

public class TaskController{
	
	private final String name;
	private final int numberOfFiles;
	private final boolean keepStructure;
	private final long totalSize;
		
	private long started;
	private long timeElapsed;
	
	private long sizeLeft;
	private int filesLeft;
	
	private RemoteFile files;
	private File outputDirectory;
	
	private List<TaskMonitor> monitors = new ArrayList<TaskMonitor>();
	
	private Task t;
		
	public TaskController(String name, boolean keepStructure, RemoteFile fileTree){
		if(fileTree == null){
			throw new IllegalArgumentException("The root directory may not be empty");
		}
		this.name = name;
		this.keepStructure = true;
		this.files = fileTree;
		
		numberOfFiles = RemoteFile.countFiles((TreeNode) files);
		totalSize = RemoteFile.countSize((TreeNode) files);
		
		t = new Task(this);
		timeElapsed = 0;
	}
	
	/**
	 * Starts the tasks execution
	 */
	public void start(){
		if(t.isRunning) return;
		t.setRunningState(true);
		started = System.currentTimeMillis();
		new Thread(t).start();
	}
	
	/**
	 * Pauses the execution of the task, finishes the currently running download
	 */
	public void pause(){
		t.setRunningState(false);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(t.isRunning);
				timeElapsed += System.currentTimeMillis() - started;
			}
		}).start();
	}
	
	/**
	 * This method is called by the associated task, if a file has been successfully downloaded
	 * @param size
	 */
	public void fileFinnished(long size){
		sizeLeft -= size;
		filesLeft--;
		for(TaskMonitor monitor : monitors){
			monitor.taskUpdated(sizeLeft, filesLeft, ((t.isRunning) ? timeElapsed + (System.currentTimeMillis() - started): timeElapsed));
		}
		// backup task
	}
	
	/**
	 * Adds a task monitor to the list of monitors, which will receive updates, everytime a file has been downloaded
	 * @param m
	 */
	public void addMonitor(TaskMonitor m){
		monitors.add(m);
	}
	
	/**
	 * Removes the given monitor from the list of monitors
	 * @param m
	 */
	public void removeMonitor(TaskMonitor m){
		if(monitors.contains(m)) monitors.remove(m);
	}
}
