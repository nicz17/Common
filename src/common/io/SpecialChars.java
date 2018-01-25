package common.io;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Conversion of special characters from French to Html and LaTeX.
 * 
 * @author nicz
 *
 */
public class SpecialChars {
	private static Map<String, String> htmlChars;
	private static Map<String, String> latexChars;
	
	/**
	 * Initialize the maps of special character conversions.
	 */
	public static void init() {
		htmlChars = new HashMap<String, String>();
		htmlChars.put("à", "&agrave;");
		htmlChars.put("â", "&acirc;");
		htmlChars.put("ä", "&auml;");
		htmlChars.put("ç", "&ccedil;");
		htmlChars.put("é", "&eacute;");
		htmlChars.put("è", "&egrave;");
		htmlChars.put("ê", "&ecirc;");
		htmlChars.put("ë", "&euml;");
		htmlChars.put("î", "&icirc;");
		htmlChars.put("ï", "&iuml;");
		htmlChars.put("ô", "&ocirc;");
		htmlChars.put("ö", "&ouml;");
		htmlChars.put("ù", "&ugrave;");
		htmlChars.put("û", "&ucirc;");
		
		latexChars = new HashMap<String, String>();
		latexChars.put("à", "\\\\`a");
		latexChars.put("â", "\\\\^a");
		latexChars.put("ä", "\\\\\"a");
		latexChars.put("ç", "\\\\c{c}");
		latexChars.put("é", "\\\\'e");
		latexChars.put("è", "\\\\`e");
		latexChars.put("ê", "\\\\^e");
		latexChars.put("ë", "\\\\\"e");
		latexChars.put("î", "\\\\^i");
		latexChars.put("ô", "\\\\^o");
		latexChars.put("ö", "\\\\\"o");
		latexChars.put("ù", "\\\\`u");
		latexChars.put("û", "\\\\^u");
		latexChars.put("%", "\\\\%");
	}
	
	/**
	 * Convert the input's special chars from HTML to French.
	 * @param s the input string
	 * @return the converted string
	 */
	public static String htmlToFr(String s) {
		for (String acc : htmlChars.keySet()) {
			Pattern p = Pattern.compile(htmlChars.get(acc));
			Matcher m = p.matcher(s);
			s = m.replaceAll(acc);
		}
		return s;
	}

	/**
	 * Convert the input's special chars from French to HTML.
	 * @param s the input string
	 * @return the converted string
	 */
	public static String frToHtml(String s) {
		for (String acc : htmlChars.keySet()) {
			Pattern p = Pattern.compile(acc);
			Matcher m = p.matcher(s); 
			s = m.replaceAll(htmlChars.get(acc));
		}
		return s;
	}

	/**
	 * Convert the input's special chars from French to Latex.
	 * @param s the input string
	 * @return the converted string
	 */
	public static String frToTex(String s) {
		for (String acc : latexChars.keySet()) {
			Pattern p = Pattern.compile(acc);
			Matcher m = p.matcher(s); 
			s = m.replaceAll(latexChars.get(acc));
		}
		return s;
	}
	
	/**
	 * @deprecated was useful with a US keyboard, to insert 
	 * accented characters in French.
	 * Don't use, since it replaces the standard popup menu.
	 */
//	public static void setPopupMenu(final Text theText, Shell shell) {
//		if (htmlChars == null) init();
//		
//		Menu mnuEdit = new Menu(shell, SWT.POP_UP);
//		theText.setMenu(mnuEdit);
//		MenuItem mitInsert = new MenuItem(mnuEdit, SWT.CASCADE);
//		mitInsert.setText("&Insertion");
//		Menu mnuInsert = new Menu(shell, SWT.DROP_DOWN);
//		mitInsert.setMenu(mnuInsert);
//
//		List<String> listChars = new ArrayList<String>();
//		listChars.addAll(htmlChars.keySet());
//		Collections.sort(listChars);
//		
//		for (final String acc : listChars) {
//			MenuItem mitAccent = new MenuItem(mnuInsert, SWT.PUSH);
//			mitAccent.setText(acc);
//			mitAccent.addListener(SWT.Selection, new Listener() {
//				public void handleEvent(Event event) {
//					theText.insert(acc);
//				}
//			});
//		} 
//	}
}
