package common.html;

/**
 * A Div html tag.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>04.02.2023: nicz - Creation</li>
 * </ul>
 */
public class DivHtmlTag extends HtmlTag {

	/**
	 * @param sId
	 */
	public DivHtmlTag(String sId) {
		super("div");
		if (sId != null) {
			this.addAttribute("id", sId);
		}
	}
}
