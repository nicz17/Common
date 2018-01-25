package common.base;

/**
 * Utility class to compute the duration of a task.
 * Uses the system time, with millisecond precision.
 * 
 * @author nicz
 *
 */
public class Chronometer {
	
	private long tStart, tStop;
	
	/**
	 * Constructor. Initializes start time
	 */
	public Chronometer() {
		tStart = System.currentTimeMillis();
	}
	
	/**
	 * Resets the start time at the current time
	 */
	public void restart() {
		tStart = System.currentTimeMillis();
	}
	
	/**
	 * Stops the timer and return the elapsed time.
	 * 
	 * @return the elapsed time in milliseconds.
	 */
	public long stop() {
		tStop = System.currentTimeMillis();
		return tStop - tStart;
	}
	
	public long getStart() {
		return tStart;
	}

	public long getStop() {
		return tStop;
	}
	
	/**
	 * Gets the elapsed time in milliseconds.
	 * 
	 * @return the elapsed time in milliseconds.
	 */
	public long getElapsedTime() {
		return tStop - tStart;
	}

	/**
	 * Gets the average time it took to perform a given number of tasks.
	 * 
	 * @param nTasks the number of tasks performed.
	 * @return average time per task in milliseconds
	 */
	public double getAverageTime(int nTasks) {
		long tElapsed = getElapsedTime();
		return (double)tElapsed/(double)nTasks;
	}
}
