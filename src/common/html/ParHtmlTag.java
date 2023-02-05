package common.html;

/**
 * A Paragraph html tag.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>04.02.2023: nicz - Creation</li>
 * </ul>
 */
public class ParHtmlTag extends HtmlTag {

	/**
	 * Constructor with initial text.
	 * @param sText  initial paragraph text
	 */
	public ParHtmlTag(String sText) {
		super("p", sText);
	}

	/**
	 * Add the specified text to this paragraph.
	 * @param sText  the text to add.
	 */
    public void addText(String sText) {
        this.sContent += sText;
    }
    
    /**
     * Adds a newline and indent to this paragraph.
     * @param iDepth  the indent depth
     */
    public void addNewLine(int iDepth) {
    	this.sContent += getIndent(iDepth);
    }

    /**
     * Add an external link to this paragraph.
     * @param url
     * @param title
     * @param text
     */
    public void addLinkExternal(final String url, final String title, final String text) {
        HtmlTag link = HtmlTagFactory.link(url, text, title, true);
        this.sContent += link.toHtml(0, true);
    }
}