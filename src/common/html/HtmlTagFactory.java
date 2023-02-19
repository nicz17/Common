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
	
	/**
	 * Creates a div with the specified id.
	 * @param id  the div id.
	 * @return  the created div
	 */
	public static HtmlTag div(String id) {
		HtmlTag div = new HtmlTag("div");
		div.addAttribute("id", id);
		return div;
	}
	
	/**
	 * Creates a div with myBox class.
	 * @param title  the box title
	 * @return  the created div
	 */
	public static HtmlTag blueBox(String title) {
		return blueBox(title, "myBox");
	}
	
	/**
	 * Creates a div with myBox class.
	 * @param title  the box title
	 * @param sClass the CSS class
	 * @return  the created div
	 */
	public static HtmlTag blueBox(String title, String sClass) {
		HtmlTag div = new HtmlTag("div");
		div.setClass(sClass);
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
		return link(href, text, null, isExternal);
	}
	
	/**
	 * Creates a link element.
	 * @param href  the reference
	 * @param text  the link text
	 * @return  the created HTML tag
	 */
	public static HtmlTag link(String href, String text, String title) {
		return link(href, text, title, false);
	}
	
	/**
	 * Creates a link element.
	 * @param href  the reference
	 * @param text  the link text
	 * @param title the link tooltip text
	 * @param isExternal  true to open link in new tab
	 * @return  the created HTML tag
	 */
	public static HtmlTag link(String href, String text, String title, boolean isExternal) {
		HtmlTag link = new HtmlTag("a", text);
		link.addAttribute("href", href);
		if (title != null) {
			link.addAttribute("title", title);
		}
		if (isExternal) {
			link.addAttribute("target", "_blank");
		}
		return link;
	}
	
	/**
	 * Creates an image element.
	 * @param source  the image URL
	 * @param title   the image tooltip title
	 * @param alt     the image alternate text
	 * @return  the created HTML tag
	 */
	public static HtmlTag image(String source, String title, String alt) {
		HtmlTag img = new HtmlTag("img") {
			protected boolean needEndTag() {
				return false;
			}
		};
		img.addAttribute("src", source);
		if (title != null) {
			img.addAttribute("title", title);
		}
		if (alt != null) {
			img.addAttribute("alt", alt);
		}
		return img;
	}
	
	/**
	 * Creates a link with an image and no text.
	 * @param href   the link URL
	 * @param title  the link tooltip
	 * @param imgSrc the image source
	 * @param imgAlt the image alt text
	 * @return  the created link
	 */
	public static HtmlTag imageLink(String href, String title, String imgSrc, String imgAlt) {
		HtmlTag link = link(href, null, title, false);
		HtmlTag img  = image(imgSrc, null, imgAlt);
		link.addTag(img);
		return link;
	}
	
	/**
	 * Creates a font tag with gray color.
	 * @param text  the text
	 * @return  the created tag
	 */
	public static HtmlTag grayFont(String text) {
		HtmlTag tag = new HtmlTag("font", text).addAttribute("color", "gray");
		return tag;
	}
	
	/**
	 * Creates a link tag to reference CSS.
	 * This element has no end tag.
	 * @param url  the CSS URL
	 * @return  the created link tag
	 */
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
	 * Creates a link tag to reference favicon.
	 * This element has no end tag.
	 * @return  the created link tag
	 */
	public static HtmlTag iconLink() {
		HtmlTag tag = new HtmlTag("link") {
			protected boolean needEndTag() {
				return false;
			}
		};
		tag.addAttribute("rel", "icon");
		tag.addAttribute("type", "image/x-icon");
		tag.addAttribute("href", "favicon.ico");
		return tag;
	}
	
	public static HtmlTag script(String ref) {
		HtmlTag tag = new HtmlTag("script");
		tag.addAttribute("src", ref);
		return tag;
	}
	
	/**
	 * Creates an empty meta HTML tag.
	 * This element has no end tag.
	 * @return  the created meta tag
	 */
	public static HtmlTag meta() {
		HtmlTag tag = new HtmlTag("meta") {
			protected boolean needEndTag() {
				return false;
			}
		};
		return tag;
	}
	
	/**
	 * Creates a meta HTML tag.
	 * This element has no end tag.
	 * @param name     the name
	 * @param content  the content
	 * @return  the created meta tag
	 */
	public static HtmlTag meta(String name, String content) {
		HtmlTag tag = meta();
		tag.addAttribute("name", name);
		tag.addAttribute("content", content);
		return tag;
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
			public String toHtml(int iDepth, boolean isInline) {
				return getIndent(iDepth) + "<!-- " + sComment + " -->";
			}
		};
	}
}
