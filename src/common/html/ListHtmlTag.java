package common.html;


/**
 * An HTML UL list element.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>04.02.2023: nicz - Creation</li>
 * </ul>
 */
public class ListHtmlTag extends HtmlTag {

	/**
	 * Constructor.
	 */
	public ListHtmlTag() {
		super("ul");
	}
	
	public void addItem(String sItem) {
		this.addTag(new HtmlTag("li", sItem));
	}
	
	public void addItem(HtmlTag tag) {
		HtmlTag item = new HtmlTag("li");
		item.addTag(tag);
		this.addTag(item);
	}

}
