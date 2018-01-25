package common.base;

import java.util.Calendar;

/**
 * Simple logging class
 * with four logging levels: debug, info, warning, error
 * 
 * @author nicz
 *
 */
public class Logger {
	
	/**
	 * Global flag to switch debug on/off
	 */
	private static boolean gDebug = false;
	
	private String unitName;
	
	/**
	 * Local flag to switch debug on/off
	 */
	private boolean isDebug;

	/**
	 * Constructor with debug set to false.
	 * 
	 * @param unitName	the name of the unit (class) sending log messages
	 */
	public Logger(String unitName) {
		this(unitName, false);
	}

	/**
	 * Constructor with debug flag.
	 * 
	 * @param unitName	the name of the unit (class) sending log messages
	 * @param isDebug	flag to print debug level messages
	 */
	public Logger(String unitName, boolean isDebug) {
		this.unitName = unitName;
		this.isDebug = isDebug;
		info("Created logger with debug=" + this.isDebug);
	}
	
	/**
	 * Set the global flag telling if debug messages are logged.
	 * @param isDebug if true, will print debug messages.
	 */
	public static void setGlobalDebug(boolean isDebug) {
		Logger.gDebug = isDebug;
	}
	
	/**
	 * Set the flag telling if debug messages are logged.
	 * @param isDebug if true, will print debug messages.
	 */
	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}
	
	/**
	 * Log a debug message.
	 * @param msg the message to log
	 */
	public void debug(String msg) {
		if (gDebug && isDebug)
			System.out.printf("%s [debug] %10s: %s%n", getTimestamp(), unitName, msg);
	}
	
	/**
	 * Log an info message.
	 * @param msg the message to log
	 */
	public void info(String msg) {
		System.out.printf("%s  [info] %10s: %s%n", getTimestamp(), unitName, msg);
	}
	
	/**
	 * Log a warning message.
	 * @param msg the message to log
	 */
	public void warn(String msg) {
		System.out.printf("%s  [WARN] %10s: %s%n", getTimestamp(), unitName, msg);
	}
	
	/**
	 * Logs an error and an exception.
	 * 
	 * @param msg the error message
	 * @param exc the exception to log after message
	 */
	public void error(String msg, Exception exc) {
		error(msg + ": " + exc.getMessage());
	}
	
	/**
	 * Log an error message.
	 * @param msg the message to log
	 */
	public void error(String msg) {
		System.out.printf("%s [ERROR] %10s: %s%n", getTimestamp(), unitName, msg);
	}
	
	private String getTimestamp() {
		Calendar calendar = Calendar.getInstance();
		return String.format("%02d:%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
	}

}
