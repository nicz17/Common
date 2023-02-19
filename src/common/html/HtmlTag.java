package common.html;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * An HTML tag used by HtmlPage class.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>04.02.2023: nicz - Creation</li>
 * </ul>
 */
public class HtmlTag {
	/** The tag name, for example div. */
	private final String sName;
	
	/** The tag textual content. */
	protected String sContent;
	
	/** The tag's attributes. */
	private final Map<String, String> mapAttributes;
	
	/** The child tags. */
	private final Vector<HtmlTag> vecTags;
	
	/** Constructor. */
	public HtmlTag(String sName) {
		this(sName, null);
	}
	
	/** Constructor. */
	public HtmlTag(String sName, String sContent) {
		this.sName = sName;
		this.sContent = sContent;
		this.mapAttributes = new HashMap<String, String>();
		this.vecTags = new Vector<HtmlTag>();
	}
	
	public HtmlTag addAttribute(String sName, String sValue) {
		this.mapAttributes.put(sName, sValue);
		return this;
	}
	
	public void addTag(HtmlTag tag) {
		vecTags.add(tag);
	}

	/**
	 * Adds a H[1-6] title tag to this element.
	 * @param iLevel  the title level (1 to 6).
	 * @param sTitle  the title text
	 */
	public void addTitle(int iLevel, String sTitle) {
		addTag(HtmlTagFactory.title(iLevel, sTitle));
	}
	
	/**
	 * Adds a paragraph element to this element and returns it.
	 * @param text  the paragraph text.
	 * @return  the created paragraph.
	 */
	public ParHtmlTag addParagraph(String text) {
		ParHtmlTag par = new ParHtmlTag(text);
		addTag(par);
		return par;
	}
	
	/**
	 * Adds a myBox div to this element and returns it.
	 * @param title  the myBox title
	 * @return  the created div
	 */
	public HtmlTag addBox(String title) {
		HtmlTag box = HtmlTagFactory.blueBox(title);
		addTag(box);
		return box;
	}

	/**
	 * Adds an empty list to this element and returns it.
	 * @return  the created list
	 */
	public ListHtmlTag addList() {
		ListHtmlTag list = new ListHtmlTag();
		addTag(list);
		return list;
	}
	
	public HtmlTag addLink(String href, String text, String title, boolean isExternal) {
		HtmlTag link = HtmlTagFactory.link(href, text, title, isExternal);
		addTag(link);
		return link;
	}
	
	public HtmlTag addGrayFont(String text) {
		HtmlTag tag = HtmlTagFactory.grayFont(text);
		addTag(tag);
		return tag;
	}
	
	public void setClass(String sClass) {
		this.addAttribute("class", sClass);
	}
	
	public int size() {
		return vecTags.size();
	}
	
	public String toHtml(int iDepth, boolean isInline) {
		StringBuffer sbHtml = new StringBuffer();
		int nChildren = countChildren();
		
		if (!isInline) {
			sbHtml.append(getIndent(iDepth));
		}
		
		sbHtml.append("<" + sName);
		for (String sKey : mapAttributes.keySet()) {
			sbHtml.append(" " + sKey + "=\"" + mapAttributes.get(sKey) + "\"");
		}
		sbHtml.append(">");
		
		if (sContent != null) {
			sbHtml.append(sContent);
		}
		
		for (HtmlTag tag : vecTags) {
			sbHtml.append(tag.toHtml(iDepth+1, nChildren < 2));
		}

		if (needEndTag()) {
			if (!isInline && nChildren > 1) {
				sbHtml.append(getIndent(iDepth));
			}
			sbHtml.append("</" + sName + ">");
		}
		return sbHtml.toString();
	}
	
	/**
	 * Returns a newline followed by 2*iDepth spaces.
	 * @param iDepth  the indent depth
	 * @return newline and indent
	 */
	protected String getIndent(int iDepth) {
		String indent = "\n";
		for (int i=0; i<iDepth; ++i) {
			indent += "  ";
		}
		return indent;
	}
	
	protected int countChildren() {
		int count = size();
		for (HtmlTag tag : vecTags) {
			count += tag.countChildren();
		}
		return count;
	}
	
	protected boolean needEndTag() {
		return true;
	}
}
