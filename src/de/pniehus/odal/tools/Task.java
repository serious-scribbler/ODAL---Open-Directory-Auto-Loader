package de.pniehus.odal.tools;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class Task implements Runnable{
	
	
	private boolean keepRunning = true;
	protected transient boolean isRunning = false;
	
	private TaskController ctrl;
	
	public Task(TaskController ctrl){
		this.ctrl = ctrl;
	}

	@Override
	public void run() {
		while(keepRunning){
			isRunning = true;
		}
		isRunning = false;
	}
	
	/**
	 * This state is used to stop the runnable after the current download, it continues execution if set to true
	 * @param state
	 */
	public void setRunningState(boolean state){
		keepRunning = state;
	}
	
}
