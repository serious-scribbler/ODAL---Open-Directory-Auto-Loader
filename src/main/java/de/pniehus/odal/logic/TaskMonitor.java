package de.pniehus.odal.logic;

import de.pniehus.odal.utils.ErrorListener;

/**
 * This interface can be used to wirte monitrs for tasks
 * @author Phil Niehus
 *
 */
public interface TaskMonitor extends ErrorListener{
	public void taskUpdated(long sizeLeft, int filesLeft, long timeElapsed);
}
