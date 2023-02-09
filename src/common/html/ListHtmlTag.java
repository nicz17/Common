package common.html;

import java.util.Vector;

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
	
	/**
	 * Add a text item to this list.
	 * @param sItem  the text to add
	 */
	public void addItem(String sItem) {
		this.addTag(new HtmlTag("li", sItem));
	}
	
	/**
	 * Add a tag to this list.
	 * @param tag  the tag to add
	 */
	public void addItem(HtmlTag tag) {
		HtmlTag item = new HtmlTag("li");
		item.addTag(tag);
		this.addTag(item);
	}
	
	/**
	 * Add several tags to a single item in this list.
	 * @param tags  the tags to add
	 */
	public void addItem(Vector<HtmlTag> tags) {
		HtmlTag item = new HtmlTag("li");
		for (HtmlTag tag : tags) {
			item.addTag(tag);
		}
		this.addTag(item);
	}

}
