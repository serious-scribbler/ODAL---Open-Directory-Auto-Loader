package de.pniehus.odal.tools;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class RemoteFile extends DefaultMutableTreeNode{
	
	private final String name;
	
	/**
	 * Creates a RemoteFile as a single file with the given name and info
	 * @param name The name of the File
	 * @param info The information about the file
	 */
	public RemoteFile(String name, RemoteFileInfo info){
		super(info);
		this.name = name;
		setAllowsChildren(false);
	}
	
	/**
	 * Creates a RemoteFile as a directory
	 * @param name The name of the directory
	 */
	public RemoteFile(String name){
		super();
		this.name = name;
	}
	
	/**
	 * Returns the file info associated to this file, or null if this file is a directory
	 * @return
	 */
	public RemoteFileInfo getFileInfo(){
		if(!isLeaf()) return null;
		return (RemoteFileInfo) getUserObject();
	}
	
	/**
	 * Returns true if this file is a directory
	 * @return
	 */
	public boolean isDirectory(){
		return !isLeaf();
	}
	
	/**
	 * Returns the name of this file/directory
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Counts the number of files in the give tree
	 * @param root
	 * @return
	 */
	public static int countFiles(TreeNode root){
		if(((RemoteFile) root).isLeaf()) return 1;
		int count = 0;
		int children = root.getChildCount();
		for(int i = 0; i < children; i++){
			count += countFiles(root.getChildAt(i));
		}
		return count;
	}
	
	/**
	 * Returns the size of the given file tree
	 * @param root
	 * @return
	 */
	public static long countSize(TreeNode root){
		long size = 0;
		int childcount = root.getChildCount();
		for(int i = 0; i < childcount; i++){
			RemoteFile node = (RemoteFile) root.getChildAt(i);
			size += (node.isDirectory()) ? countSize((TreeNode) node) : node.getFileInfo().getSize();
		}
		return size;
	}
}
