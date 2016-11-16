package de.pniehus.odal.utils;

/**
 * This interface is used to respond to errors
 * @author Phil Niehus
 *
 */
public interface ErrorListener {
	
	/**
	 * This method is called whenever an error occurs
	 * @param errorMessage A message with information about the error
	 */
	public void errorOccured(String errorMessage);
}
