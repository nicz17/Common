package common.html;

import java.util.Vector;

/**
 * HTML element with lines of JS code.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>12.02.2023: nicz - Creation</li>
 * </ul>
 */
public class JavascriptHtmlTag extends HtmlTag {
	
	private final Vector<String> vecLines;

	/**
	 * Constructor
	 */
	public JavascriptHtmlTag() {
		super("script");
		this.vecLines = new Vector<String>();
	}

	/**
	 * Adds the specified line of JS code.
	 * @param sCode  the JS code to add
	 */
	public void addLine(String sCode) {
		if (sCode != null) {
			vecLines.add(sCode);
		}
	}
	
	@Override
	public String toHtml(int iDepth, boolean isInline) {
		StringBuffer sbHtml = new StringBuffer();
		int nChildren = vecLines.size();

		if (!isInline) {
			sbHtml.append(getIndent(iDepth));
		}
		sbHtml.append("<script>");
		
		for (String line : vecLines) {
			if (!isInline && nChildren > 1) {
				sbHtml.append(getIndent(iDepth + 1));
			}
			sbHtml.append(line);
		}
		
		if (!isInline && nChildren > 1) {
			sbHtml.append(getIndent(iDepth));
		}
		sbHtml.append("</script>");

		return sbHtml.toString();
	}
}
