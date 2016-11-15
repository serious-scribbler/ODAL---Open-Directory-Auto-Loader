package de.pniehus.odal.tools;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class Task extends Thread{
	
	private boolean keepStructure = true;
	private RemoteFile files;
	private long started;
	private final String name;
	private long sizeLeft;
	private final int numberOfFiles;
	private int filesLeft;
	private final long totalSize;
	private long timeElapsed;
	private File outputDirectory;
	
	public Task(String name, boolean keepStructure, RemoteFile files, File outputDirectory){
		this(name, files, outputDirectory);
		this.keepStructure = keepStructure;
	}
	
	public Task(String name, RemoteFile files, File outputDirectory){
		super(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.name = name;
		this.files = files;
		this.outputDirectory = outputDirectory;
		numberOfFiles = RemoteFile.countFiles((TreeNode) files);
		totalSize = RemoteFile.countSize((TreeNode) files);
	}
}
