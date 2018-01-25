package common.text;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 * A number format for human-readable file sizes.
 * 
 * <p>Does not support parsing.
 * 
 * @author nicz
 *
 */
public class FileSizeNumberFormat extends NumberFormat {

	private static final long serialVersionUID = -3872560322742742524L;
	
	private final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
	
	private final DecimalFormat decFormat;

	public FileSizeNumberFormat() {
		decFormat = new DecimalFormat("#,##0.#");
	}

	@Override
	public StringBuffer format(double arg0, StringBuffer arg1,
			FieldPosition arg2) {
		return null;
	}

	@Override
	public StringBuffer format(long size, StringBuffer sb, FieldPosition pos) {
		if (size <= 0) {
			sb.append("0");
		} else {
			int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
			sb.append(decFormat.format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups]);
		}
		return sb;
	}

	@Override
	public Number parse(String arg0, ParsePosition arg1) {
		// not supported
		return null;
	}

}
