package de.pniehus.odal.utils;


import com.googlecode.lanterna.gui2.Window;

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
	 * @return
	 */
	public abstract Window getSettingUI();
	
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
}
