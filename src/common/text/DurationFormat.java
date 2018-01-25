package common.text;

/**
 * Class to format time durations in milliseconds into a human-readable form.
 * 
 * <p>For example, converts 83000 milliseconds into '1 minute, 23 seconds'.
 * 
 * @author nicz
 *
 */
public class DurationFormat {

	/**
	 * Formats the given duration in milliseconds into a human-readable form.
	 * 
	 * @param duration the duration in milliseconds
	 * @return the formatted duration
	 */
	public String format(long duration) {
		String result = "";
		
		final long second = 1000l;
		final long minute = 60*second;
		final long hour   = 60*minute;
		
		// TODO add days, weeks etc
		
		// TODO use an enum, put code in a loop
		
		if (duration >= hour) {
			int nHours = (int) (duration/hour);
			duration -= nHours*hour;
			result += nHours + " heures ";
		}
		if (duration >= minute) {
			int nMinutes = (int) (duration/minute);
			duration -= nMinutes*minute;
			result += nMinutes + " minutes ";
		}
		if (duration >= second) {
			int nSeconds = (int) (duration/second);
			duration -= nSeconds*second;
			result += nSeconds + " secondes ";
		}
		if (duration > 0) {
			result += duration + " ms";
		}
		
		return result.trim();
	}
}
