package de.pniehus.odal.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * A task executes the download of a RemoteFile
 * @author Phil Niehus
 *
 */
public class Task implements Runnable{
	
	
	private boolean keepRunning = true;
	protected transient boolean isRunning = false;
	
	private TaskController ctrl;
	
	/**
	 * Creates a task with the corresponding task controller
	 * @param ctrl
	 */
	public Task(TaskController ctrl){
		this.ctrl = ctrl;
	}

	@Override
	public void run() {
		while(keepRunning && ctrl.getFileTree().getChildCount() != 0){
			isRunning = true;
			loadFile(ctrl.getFileTree());			
		}
		isRunning = false;
	}
	
	/**
	 * Downloads the complete filetree from the server
	 * @param file
	 */
	private void loadFile(RemoteFile file){
		if(file.isDirectory()){
			RemoteFile current = (RemoteFile) file.getFirstChild();
			while(file.getChildCount() > 0 && keepRunning){
				loadFile(current);
				if(file.getChildCount() != 0){
					current = (RemoteFile) file.getFirstChild();
				} else{
					file.removeFromParent();
				}
			}
		} else{
			File writeTo = new File(ctrl.getOutputDirectory().getAbsolutePath() + File.separator + file.getPathString());
			if(!ctrl.keepStructure) writeTo = new File(ctrl.getOutputDirectory().getAbsolutePath() + File.separator + file.getName());
			writeTo.getParentFile().mkdirs();
			URL website;
			try {
				website = new URL(file.getFileInfo().getURL());
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream(writeTo);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
				rbc.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				ctrl.fileFinnished(file.getFileInfo().getSize());
				file.removeFromParent();
			}
		}
	}
	
	/**
	 * This state is used to stop the runnable after the current download, it continues execution if set to true
	 * @param state
	 */
	public void setRunningState(boolean state){
		keepRunning = state;
	}
	
}
