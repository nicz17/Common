package common.text;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

/**
 * Fancy subclass of {@link DateFormat} to show a date as
 * an elapsed time, for example '3 days ago', 'less than a minute ago'
 * (in French).
 * 
 * <p>Only formatting is implemented; parsing not.
 * 
 * @author nicz
 *
 */
public class ElapsedTimeDateFormat extends DateFormat {
	
	private static final long serialVersionUID = -2420530437294683879L;

	public enum eTimeRange {
		MINUTES ("minute",  "minutes"),
		HOURS   ("heure",   "heures"),
		DAYS    ("jour",    "jours"),
		WEEKS   ("semaine", "semaines"),
		MONTH   ("mois",    "mois"),
		YEAR    ("an",      "ans");
		
		private final String singular, plural;
		eTimeRange(String singular, String plural) {
			this.singular = singular;
			this.plural = plural;
		}
		public String getSingular() { return singular; }
		public String getPlural()   { return plural; }
	}

	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo,
			FieldPosition fieldPosition) {
		
		Date now = new Date();
		long diffSecs = (now.getTime() - date.getTime())/1000l;
		
		if (diffSecs > 365*24*3600) {
			toAppendTo.append("il y a plus d'un an");
		} else if (diffSecs > 30*24*3600) {
			long nMonths = diffSecs / (30*24*3600l);
			toAppendTo.append(getHumanTimeDiff(eTimeRange.MONTH, nMonths));
		} else if (diffSecs > 7*24*3600) {
			long nWeeks = diffSecs / (7*24*3600l);
			toAppendTo.append(getHumanTimeDiff(eTimeRange.WEEKS, nWeeks));
		} else if (diffSecs > 24*3600) {
			long nDays = diffSecs / (24*3600l);
			toAppendTo.append(getHumanTimeDiff(eTimeRange.DAYS, nDays));
		} else if (diffSecs > 3600) {
			long nHours = diffSecs / 3600l;
			toAppendTo.append(getHumanTimeDiff(eTimeRange.HOURS, nHours));
		} else if (diffSecs > 60) {
			long nMins = diffSecs / 60l;
			toAppendTo.append(getHumanTimeDiff(eTimeRange.MINUTES, nMins));
		} else {
			toAppendTo.append("à l'instant");
		}

		return toAppendTo;
	}

	@Override
	public Date parse(String source, ParsePosition pos) {
		return null;
	}
	
	private String getHumanTimeDiff(eTimeRange timeRange, long amount) {
		if (amount == 1) 
			return "il y a 1 " + timeRange.getSingular();
		return "il y a " + amount + " " + timeRange.getPlural();
	}

}
