package de.pniehus.odal.resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.pniehus.odal.utils.*;

/**
 * This class is used to store download settings for reuse
 * 
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
	private transient boolean userProfile = false;
	private String logLevel = "info";
	private boolean windowsConsoleMode = false;
	private transient String url;

	private Map<String, String> filterConfig = new HashMap<String, String>();

	public Profile(List<Filter> filters) {
		for (Filter f : filters) {
			filterConfig.put(f.toString(), f.getSampleConfig());
		}
	}

	/**
	 * Returns the Absolute path to the output directory
	 * 
	 * @return
	 */
	public String getOutputPath() {
		return outputPath;
	}

	/**
	 * Returns true when progress outputs are disabled
	 * 
	 * @return
	 */
	public boolean isSilent() {
		return silent;
	}

	/**
	 * Returns true when the original file structure should be kept
	 * 
	 * @return
	 */
	public boolean isKeepOriginalStructure() {
		return keepOriginalStructure;
	}

	/**
	 * Returns true when all files are set to be selected
	 * 
	 * @return
	 */
	public boolean isSelectAll() {
		return selectAll;
	}

	/**
	 * Returns true when the software is set to parse subdirectories
	 * 
	 * @return
	 */
	public boolean isParseSubdirectories() {
		return parseSubdirectories;
	}

	/**
	 * Returns true when logging is enabled
	 * 
	 * @return
	 */
	public boolean isLogging() {
		return logging;
	}

	/**
	 * Returns the selected log level
	 * 
	 * @return
	 */
	public String getLogLevel() {
		return logLevel;
	}

	/**
	 * Returns true when windows console mode is enabled. Windows console mode
	 * disables the gui and prints status updates to the console
	 * 
	 * @return
	 */
	public boolean isWindowsConsoleMode() {
		return windowsConsoleMode;
	}

	/**
	 * Returns the map with settings for all filters
	 * 
	 * @return
	 */
	public Map<String, String> getFilters() {
		return filterConfig;
	}

	/**
	 * Returns true, if this profile is a user defined profile This is used to
	 * ask the user for his desired options when the profile was created by ODAL
	 * 
	 * @return
	 */
	public boolean isUserProfile() {
		return userProfile;
	}

	/**
	 * Returns the URL of the open directory, null if not set
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the selectAll option to the given state
	 * 
	 * @param selectAll
	 */
	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	/**
	 * Sets the windowsConsoleMode option to the given state
	 * 
	 * @param windowsConsoleMode
	 */
	public void setWindowsConsoleMode(boolean windowsConsoleMode) {
		this.windowsConsoleMode = windowsConsoleMode;
	}

	/**
	 * Sets the user profile option for this profile
	 * 
	 * @param state
	 */
	public void setUserProfile(boolean state) {
		userProfile = state;
	}

	/**
	 * Sets the URL of the open directory
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Sets the output directory to the given path
	 * @param outputPath
	 */
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	/**
	 * Loads a profile from the ODAL directory
	 * 
	 * @param name
	 *            The name of the profile
	 * @return The profile as Profile object
	 * @throws IOException
	 *             On read errors
	 */
	public static Profile loadProfile(String name) throws IOException {
		Gson gson = new Gson();
		BufferedReader bf = new BufferedReader(new FileReader(name + ".odal"));
		String s = "";
		String json = "";
		while ((s = bf.readLine()) != null) {
			json += s;
		}
		bf.close();
		return gson.fromJson(json, Profile.class);
	}

	/**
	 * Saves the given profile under the given name in the ODAL directory
	 * 
	 * @param name The name of the profile
	 * @param p The profile
	 * @throws IOException on write error
	 */
	public static void saveProfile(String name, Profile p) throws IOException {
		if (name == null || name.equals(""))
			System.out.println("Profile names may not be null or empty strings!");
		if (p == null)
			System.out.println("Profiles may not be null!");

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		BufferedWriter bf = new BufferedWriter(new FileWriter(name + ".odal"));
		bf.write(gson.toJson(p));
		bf.close();
	}
}
