package common.text;

/**
 * Simple text formatter to display week numbers
 * as more human-readable time periods.
 * 
 * <p>For example, formats 'Week 1' as 'début janvier'.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>20.01.2018: nicz - Creation</li>
 * </ul>
 */
public class WeekNumberFormat {

	/**
	 * The names of the months, in French
	 */
	private final static String[] monthNames = {
		"janvier", "février", "mars", "avril", "mai", "juin", 
		"juillet", "août", "septembre", "octobre", "novembre", "décembre"
	};
	
	/**
	 * Formats the specified week number.
	 * @param weekOfYear a week number, between 1 and 53.
	 * @return the formatted text (never null)
	 */
	public String format(int weekOfYear) {
		if (weekOfYear < 1 || weekOfYear > 53) {
			return "Inconnu";
		}
		
		// get day of year, month, day of month
		int dayOfYear = 1 + 7*(weekOfYear - 1);
		int month = 1 + (dayOfYear/30);
		int dayOfMonth = dayOfYear - 30*(month-1);
		
		String result = "Début ";
		if (dayOfMonth > 20) {
			result = "Fin ";
		} else if (dayOfMonth > 10) {
			result = "Mi-";
		}
		
		if (month > 12) month = 12;
		if (month > 0 && month < 13) {
			result += monthNames[month -1];
		}
		
		result += " (semaine " + weekOfYear + ")";
			
		return result;
	}
}
