package common.html;

/**
 * A factory for various HTML elements.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>04.02.2023: nicz - Creation</li>
 * </ul>
 */
public class HtmlTagFactory {
	
	public static HtmlTag blueBox(String title) {
		DivHtmlTag div = new DivHtmlTag(null);
		div.addAttribute("class", "myBox");
		div.addTag(new HtmlTag("h2", title));
		return div;
	}
	
	/**
	 * Creates a link element.
	 * @param href  the reference
	 * @param text  the link text
	 * @return  the created HTML tag
	 */
	public static HtmlTag anchor(String href) {
		HtmlTag link = new HtmlTag("a");
		link.addAttribute("name", href);
		return link;
	}
	
	/**
	 * Creates a link element.
	 * @param href  the reference
	 * @param text  the link text
	 * @return  the created HTML tag
	 */
	public static HtmlTag link(String href, String text) {
		return link(href, text, false);
	}
	
	/**
	 * Creates a link element.
	 * @param href  the reference
	 * @param text  the link text
	 * @return  the created HTML tag
	 */
	public static HtmlTag link(String href, String text, boolean isExternal) {
		HtmlTag link = new HtmlTag("a", text);
		link.addAttribute("href", href);
		if (isExternal) {
			link.addAttribute("target", "_blank");
		}
		return link;
	}
	
	/**
	 * Creates an image element.
	 * @param source  the image URL
	 * @param title   the image tooltip title
	 * @return  the created HTML tag
	 */
	public static HtmlTag image(String source, String title) {
		HtmlTag img = new HtmlTag("img") {
			protected boolean needEndTag() {
				return false;
			}
		};
		img.addAttribute("src", source);
		if (title != null) {
			img.addAttribute("title", title);
		}
		return img;
	}
	
	public static HtmlTag cssLink(String url) {
		HtmlTag tagCss = new HtmlTag("link") {
			protected boolean needEndTag() {
				return false;
			}
		};
		tagCss.addAttribute("rel", "stylesheet");
		tagCss.addAttribute("type", "text/css");
		tagCss.addAttribute("href", url);
		return tagCss;
	}
	
	/**
	 * Creates a H[1-6] title tag.
	 * @param iLevel  the title level (1 to 6).
	 * @param sTitle  the title text
	 * @return  the created HTML tag
	 */
	public static HtmlTag title(int iLevel, String sTitle) {
		return new HtmlTag("h" + iLevel, sTitle);
	}
	
	/**
	 * Creates an HTML comment.
	 * @param sComment  the comment text
	 * @return  the created HTML tag
	 */
	public static HtmlTag comment(final String sComment) {
		return new HtmlTag("c") {
			protected String toHtml(int iDepth, boolean isInline) {
				return getIndent(iDepth) + "<!-- " + sComment + " -->";
			}
		};
	}
}
