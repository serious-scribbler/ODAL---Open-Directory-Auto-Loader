package de.pniehus.odal.utils;

import javax.swing.JPanel;

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
	 * This method needs to return a JPanel with settings for this filter
	 * @return
	 */
	public abstract JPanel getSettingUI();
	
	/**
	 * This method will apply the filter (return a filtered version of the filetree
	 * @param filetree
	 */
	public abstract void filter(RemoteFile filetree);
	
	/**
	 * Enables or disables the filter
	 * @param enable
	 */
	public void setEnabled(boolean enable){
		this.enabled = enable;
	}
}
