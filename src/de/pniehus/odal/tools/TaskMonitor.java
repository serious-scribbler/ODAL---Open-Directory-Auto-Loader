package de.pniehus.odal.tools;

public interface TaskMonitor {
	public void taskUpdated(long sizeLeft, int filesLeft, long timeElapsed);
}
