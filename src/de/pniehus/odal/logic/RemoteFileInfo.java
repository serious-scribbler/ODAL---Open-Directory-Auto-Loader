package de.pniehus.odal.logic;

/**
 * Holds information about a remote file
 * @author Phil Niehus
 *
 */
public class RemoteFileInfo {
	// TODO ADD file type
	private final String url;
	private final long size;
	
	/**
	 * Sets the files url and size
	 * @param url
	 * @param size
	 */
	public RemoteFileInfo(String url, long size){
		this.url = url;
		this.size = size;
	}
	
	/**
	 * Returns the url of the associated file
	 * @return
	 */
	public String getURL(){
		return url;
	}
	
	/**
	 * Returns the size of the associated file
	 * @return
	 */
	public long getSize(){
		return size;
	}
}
