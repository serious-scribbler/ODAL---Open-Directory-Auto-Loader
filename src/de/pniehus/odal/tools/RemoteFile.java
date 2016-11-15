package de.pniehus.odal.tools;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;


public class RemoteFile extends DefaultMutableTreeNode{
	
	private final String name;
	private String path;
	/**
	 * Creates a RemoteFile as a single file with the given name and info
	 * @param name The name of the File
	 * @param info The information about the file
	 */
	public RemoteFile(String name, RemoteFileInfo info){
		super(info);
		this.name = name;
		if(name == null) name = "unknown";
		setAllowsChildren(false);
	}
	
	/**
	 * Sets the path string according to the path to the root directory
	 */
	private void setPathString(){
		if(isRoot()){
			path = "";
		} else{
			path = ((RemoteFile) getParent()).getPathString() + name;
			if(isDirectory()) path = path + "/";
		}
	}
	
	/**
	 * Creates a RemoteFile as a directory
	 * @param name The name of the directory
	 */
	public RemoteFile(String name){
		super();
		this.name = name;
		if(name == null) name = "unknown";
	}
	
	/**
	 * Returns the path of this file as string
	 * @return
	 */
	public String getPathString(){
		if(path == null) setPathString();
		return path;
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
	
	@Override
	public String toString(){
		return getStringRepresentation(this, 0);
	}
	
	/**
	 * Returns a String representation for the given file tree
	 * @param root
	 * @param depth the depth of the tree
	 * @return
	 */
	private String getStringRepresentation(TreeNode root, int depth){
		String rep = "";
		if(depth == 0) rep += "+" + ((RemoteFile)root).getName() + "\n";
		depth++;
		int childcount = root.getChildCount();
		for(int i = 0; i < childcount; i++){
			RemoteFile node = (RemoteFile) root.getChildAt(i);
			if(node.isDirectory()){
				rep += repeatChar(' ', depth*2-2) + "+[" + node.getName() + "]\n";
				rep += getStringRepresentation(node, depth++);
			} else{
				rep += repeatChar(' ', depth*2-2) + "|" + node.getName() + "\n";
			}
		}
		depth--;
		return rep;
	}
	
	/**
	 * Repeats c n times
	 * @param c
	 * @param n
	 * @return
	 */
	private String repeatChar(char c, int n){
		String s = "";
		for(int i = 0; i < n; i++){
			s += c;
		}
		return s;
	}
}
