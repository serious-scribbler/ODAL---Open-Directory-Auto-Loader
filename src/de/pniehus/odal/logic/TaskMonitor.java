package de.pniehus.odal.logic;

/**
 * This interface can be used to wirte monitrs for tasks
 * @author Phil Niehus
 *
 */
public interface TaskMonitor {
	public void taskUpdated(long sizeLeft, int filesLeft, long timeElapsed);
}
