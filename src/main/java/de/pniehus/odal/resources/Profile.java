package de.pniehus.odal.resources;

import java.util.HashMap;
import java.util.Map;

import de.pniehus.odal.utils.*;

/**
 * This class is used to store download settings for reuse
 * @author Phil Niehus
 *
 */
public class Profile {
	private String outputPath = "";
	private boolean silent = false;
	private boolean keepOriginalStructure = true;
	private boolean selectAll = true;
	private boolean parseSubdirectories = true;
	private boolean logging = false;
	private String logLevel = "info";
	private Map<String, String> filters = new HashMap<String, String>();
	
	public Profile(){
		// Not in Use
	}
	
	/**
	 * Returns the Absolute path to the output directory
	 * @return
	 */
	public String getOutputPath() {
		return outputPath;
	}
	
	/**
	 * Returns true when progress outputs are disabled
	 * @return
	 */
	public boolean isSilent() {
		return silent;
	}
	
	/**
	 * Returns true when the original file structure should be kept
	 * @return
	 */
	public boolean isKeepOriginalStructure() {
		return keepOriginalStructure;
	}
	
	/**
	 * Returns true when all files are set to be selected
	 * @return
	 */
	public boolean isSelectAll() {
		return selectAll;
	}
	
	/**
	 * Returns true when the software is set to parse subdirectories
	 * @return
	 */
	public boolean isParseSubdirectories() {
		return parseSubdirectories;
	}
	
	/**
	 * Returns true when logging is enabled
	 * @return
	 */
	public boolean isLogging() {
		return logging;
	}
	
	/**
	 * Returns the selected log level
	 * @return
	 */
	public String getLogLevel() {
		return logLevel;
	}
	
	/**
	 * Returns the map with settings for all filters
	 * @return
	 */
	public Map<String, String> getFilters() {
		return filters;
	}
	
	
}
