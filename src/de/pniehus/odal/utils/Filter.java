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
	public abstract void filter(RemoteFile filetree);
	
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
	 * Overwrites the toString() method, returns the filters name
	 */
	public String toString(){
		return name;
	}
}
