package de.pniehus.odal.logic;

import de.pniehus.odal.utils.ErrorListener;

/**
 * This interface can be used to wirte monitrs for tasks
 * @author Phil Niehus
 *
 */
public interface TaskMonitor extends ErrorListener{
	/**
	 * This method is called when a task has been updated
	 * @param sizeLeft Size of all files left, usually undefined
	 * @param filesLeft Number of files left to download
	 * @param timeElapsed Time in ms since the processing of tasks started
	 * @param description A human readable description of the type of update
	 */
	public void taskUpdated(long sizeLeft, int filesLeft, long timeElapsed, String description);
}
