package de.pniehus.odal.utils;


import com.googlecode.lanterna.gui2.AbstractWindow;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;

import de.pniehus.odal.GUI.OdalGui;
import de.pniehus.odal.logic.RemoteFile;

public abstract class Filter {
	
	private String name;
	private boolean enabled;
	
	/**
	 * Constructor for Filter objects
	 * @param name The filters name
	 */
	public Filter(String name){
		this.name = name;
	}
	
	/**
	 * Returns the filters name
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the state of the filter
	 * @return
	 */
	public boolean isEnabled(){
		return enabled;
	}
	
	/**
	 * This method needs to return a Window with settings for this filter
	 * @param og The gui of odal, this method needs to call og.configureNextFilter() after the configuration is finnished
	 * @return
	 */
	public abstract AbstractWindow getSettingUI(OdalGui og);
	
	/**
	 * This method will apply the filter (returns a filtered version of the filetree)
	 * @param filetree
	 */
	public void filter(RemoteFile filetree) {
		if(filetree == null) return;
		if(filetree.isLeaf()){
			applyFilter(filetree);
			return;
		} else{
			int checked = 0;
			int origCount = filetree.getChildCount();
			int current = 0;
			while(checked < origCount){
				RemoteFile currentlyChecking = (RemoteFile) filetree.getChildAt(current);
				if(currentlyChecking.isDirectory()){
					filter(currentlyChecking);
					current++;
				} else{
					if(!applyFilter(currentlyChecking)){
						current++;
					}
				}
				checked++;
			}
		}
	}

	/**
	 * This method is called by filter() for all leaves and should remove the leaf from its parent if it matches the filter
	 * This method has to return true when it removes a leave from its parent
	 * @param filetree
	 * @return {@code true} if filetree has been removed from its parent, otherwise {@code false}
	 */
	public abstract boolean applyFilter(RemoteFile filetree);
	
	/**
	 * This method will print the filters help, which describes its functions and parameters
	 * @return
	 */
	public abstract String getHelpText();
	
	/**
	 * Enables or disables the filter
	 * @param enable
	 */
	public void setEnabled(boolean enable){
		this.enabled = enable;
	}
	
	/**
	 * This method configures the filter with the selected parameters
	 * @param params
	 */
	public abstract void setUp(String params);
	
	/**
	 * Overwrites the toString() method, returns the filters name
	 */
	public String toString(){
		return name;
	}
}
