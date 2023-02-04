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
	private final String sContent;
	
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
	
	public int size() {
		return vecTags.size();
	}
	
	protected String toHtml(int iDepth, boolean isInline) {
		StringBuffer sbHtml = new StringBuffer();
		
		if (!isInline) {
			sbHtml.append(getIndent(iDepth));
		}
		
		sbHtml.append("<" + sName);
		for (String sKey : mapAttributes.keySet()) {
			sbHtml.append(" " + sKey + "='" + mapAttributes.get(sKey) + "'");
		}
		sbHtml.append(">");
		
		if (sContent != null) {
			sbHtml.append(sContent);
		}
		
		for (HtmlTag tag : vecTags) {
			sbHtml.append(tag.toHtml(iDepth+1, isInline));
		}

		if (!isInline && !vecTags.isEmpty()) {
			sbHtml.append(getIndent(iDepth));
		}
		sbHtml.append("</" + sName + ">");
		
		return sbHtml.toString();
	}
	
	protected String getIndent(int iDepth) {
		String indent = "\n";
		for (int i=0; i<iDepth; ++i) {
			indent += "  ";
		}
		return indent;
	}
}
