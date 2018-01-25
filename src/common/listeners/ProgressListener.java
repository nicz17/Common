package common.listeners;


/**
 * Interface to notify about the progress of a task.
 * 
 * <p>Used by the ProgressBox for export tasks.</p>
 * 
 * @author nicz
 *
 */
public interface ProgressListener {
	
	/**
	 * Notify that the task has started.
	 * @param total the total number of steps in the task.
	 */
	public void taskStarted(int total);
	
	/**
	 * Notify that the number of finished steps has incremented by 1.
	 */
	public void taskProgress();
	
	/**
	 * Send a message about the current progress.
	 * @param msg the progress message.
	 */
	public void info(String msg);
	
	/**
	 * Notify that the task is over.
	 */
	public void taskFinished();
}
