package common.io;

import java.util.Vector;



/**
 * Abstract class for generating HTML documents using the Composite 
 * design pattern.
 * 
 * <p>A composite specifies how to write its HTML code through the
 * {@link #write} method.
 * Factory methods allow to add various children to a composite.
 * 
 * @author nicz
 *
 */
public abstract class HtmlComposite {

	/**
	 * The indent between a component and its children
	 */
	private static final String indent = "  ";

	/**
	 * The list of children of this component.
	 */
	protected Vector<HtmlComposite> children;
	
	protected String cssClass;
	
	/**
	 * The number of levels between the root component
	 * and this component.
	 */
	private int level;
	
	/**
	 * Constructor.
	 */
	public HtmlComposite() {
		children = new Vector<HtmlComposite>();
		level = 0;
	}
	
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	
	/**
	 * Add a child to this component.
	 * @param child the child to add.
	 * @return the added child (for method chaining)
	 */
	public HtmlComposite add(HtmlComposite child) {
		child.setLevel(level + 1);
		children.add(child);
		return child;
	}
	
	/**
	 * Write this component's HTML code to the given string buffer.
	 * Must be implemented by subclasses.
	 * 
	 * @param sb the string buffer on which to write
	 */
	public abstract void write(StringBuffer sb);

	/**
	 * Creates an HTML tag with the given name.
	 * Accepts children.
	 * 
	 * @param name the name of the tag
	 * @return the created component
	 */
	public HtmlComposite addTag(final String name) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, makeTag(name));
				writeChildren(sb);
				output(sb, makeEndTag(name));
			}
		});
	}
	
	/**
	 * Creates an HTML tag with the given name and content.
	 * Tag is closed immediately and does not accept children.
	 * 
	 * @param name the name of the tag
	 * @param content the tag content
	 * @return the created component
	 */
	public HtmlComposite addTag(final String name, final String content) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, makeTag(name) + content + makeEndTag(name));
			}
		});
	}
	
	/**
	 * Creates an HTML division with the given id.
	 * Accepts children.
	 * 
	 * @param divId the id of the division
	 * @return the created component
	 */
	public HtmlComposite addDiv(final String divId) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<div" + makeParam("id", divId) + makeParam("class", cssClass) + ">");
				writeChildren(sb);
				output(sb, "</div> <!-- end " + divId + " -->\n");
			}
		});
	}
	
	/**
	 * Creates an HTML division.
	 * Accepts children.
	 * 
	 * @return the created component
	 */
	public HtmlComposite addDiv() {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<div" + makeParam("class", cssClass) + ">\n");
				writeChildren(sb);
				output(sb, "</div>\n");
			}
		});
	}
	
	public void addSpan(final String cssClassm, final String text) {
		add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<span" + makeParam("class", cssClassm) + ">" + text + "</span>");
			}
		});
	}
	
	/**
	 * Adds a heading (for instance H1) to this component.
	 * 
	 * @param level the level of the title (1 to 6)
	 * @param title the title text
	 * @return the created component
	 */
	public HtmlComposite addTitle(final int level, final String title) {
		if (level > 1)
			addNewLine();
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<h" + level + ">" + title + "</h" + level + ">");
			}
		});
	}
	
	/**
	 * Adds an image.
	 * 
	 * @param src the image URL
	 * @param alt the image alternative text
	 * @return the created component.
	 */
	public HtmlComposite addImage(final String src, final String alt) {
		return addImage(src, alt, null);
	}
	
	/**
	 * Adds an image with a CSS class.
	 * 
	 * @param src the image URL
	 * @param alt the image alternative text. May be null.
	 * @param cssClass the CSS class of the image. May be null.
	 * @return the created component.
	 */
	public HtmlComposite addImage(final String src, final String alt, 
			final String cssClass) {
		return addImage(src, alt, cssClass, null);
	}
	
	/**
	 * Adds an image with a CSS class and a title.
	 * 
	 * @param src the image URL
	 * @param alt the image alternative text. May be null.
	 * @param cssClass the CSS class of the image. May be null.
	 * @param title  the image tooltip. May be null.
	 * @return the created component.
	 */
	public HtmlComposite addImage(final String src, final String alt, 
			final String cssClass, final String title) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<img" + 
						makeParam("src", src) + 
						makeParam("alt", alt) + 
						makeParam("class", cssClass)  +
						makeParam("title", title) + ">");
			}
		});
	}
	
	public HtmlComposite addSvgCircle(final String size, final String fillColor, final String title) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<svg" + makeParam("height", size) + makeParam("width", size) + ">" +
						"<circle cx=\"9\" cy=\"9\" r=\"8\" stroke=\"#808080\" stroke-width=\"1\" " +
						makeParam("fill", fillColor) + makeParam("title", title) + "/></svg>");
			}
		});
	}
	
	public HtmlComposite addSvgSquare(final String size, final String fillColor, final String title) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<svg" + makeParam("height", size) + makeParam("width", size) + ">" +
						"<rect " + makeParam("width", size) + makeParam("height", size) + 
						makeParam("stroke", "#c0c0c0") + makeParam("stroke-width", "1") +
						makeParam("fill", fillColor) + makeParam("title", title) + "/></svg>");
			}
		});
	}
	
	/**
	 * Add a paragraph.
	 * @return the created component.
	 */
	public HtmlComposite addPar() {
		return addPar(null);
	}
	
	/**
	 * Add a paragraph.
	 * @param text the initial paragraph text (may add more)
	 * @return the created component.
	 */
	public HtmlComposite addPar(final String text) {
		return addPar(text, null);
	}
	
	/**
	 * Adds a paragraph with the given CSS class.
	 * 
	 * @param text the initial paragraph text (may add more)
	 * @param cssClass the CSS class of the paragraph (may be null).
	 * @return the created component.
	 */
	public HtmlComposite addPar(final String text, final String cssClass) {
		HtmlComposite par = add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<p" + makeParam("class", cssClass) + "> " +
						(text == null ? "" : text));
				writeChildren(sb);
				output(sb, "</p>");
			}
		});
		par.setCssClass(cssClass);
		return par;
	}
	
	/**
	 * Adds some text to this component.
	 * 
	 * @param text the text to add.
	 * @return the created component
	 */
	public HtmlComposite addText(final String text) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, text, false);
			}
		});
	}
	
	/**
	 * Adds a 'BR' tag to this component.
	 */
	public void addBr() {
		addText("<br>");
	}
	
	public HtmlComposite addCenter() {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<center>");
				writeChildren(sb);
				output(sb, "</center>");
			}
		});
	}
	
	/**
	 * Adds an unnumbered list with the given list items.
	 * No other items can be added to list.
	 * 
	 * @param vecItems the list items in the list
	 * @return the created component
	 */
	public HtmlComposite addList(final Vector<String> vecItems) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<ul>");
				for (String item : vecItems) 
					output(sb, indent + "<li>" + item + "</li>");
				output(sb, "</ul>");
			}
		});
	}
	
	/**
	 * Adds an unnumbered list.
	 * 
	 * @return the created component
	 */
	public HtmlComposite addList() {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<ul>");
				writeChildren(sb);
				output(sb, "</ul>");
			}
		});
	}
	
	/**
	 * Adds a list item.
	 * 
	 * @return the created component
	 */
	public HtmlComposite addListItem() {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<li>");
				writeChildren(sb);
				output(sb, "</li>");
			}
		});
	}
	
	
	/**
	 * Adds a simple table.
	 * Use {@link #addTableRow(Vector)} to add data to the table.
	 * 
	 * @return the created component
	 */
	public HtmlComposite addTable() {
		return addTable(null);
	}
	
	/**
	 * Adds a table with column definitions.
	 * Use {@link #addTableRow(Vector)} to add data to the table.
	 * 
	 * @param colDefs the column definitions.
	 * @return the created component
	 */
	public HtmlComposite addTable(final String colDefs) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<table>" + (colDefs == null ? "" : colDefs));
				writeChildren(sb);
				output(sb, "</table>");
			}
		});
	}
	
	/**
	 * Adds a row of data to a table.
	 * 
	 * @param vecData a list of table cells representing a whole table row.
	 * 		Each element must contain the TD start and end tags, possibly with
	 * 		additional formatting.
	 * @return the created component
	 */
	public HtmlComposite addTableRow(final Vector<String> vecData) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				String row = "<tr>";
				for (String item : vecData) 
					row += item + " ";
				row += "</tr>";
				output(sb, row);
			}
		});
	}
	
	/**
	 * Adds a HTML table with the given number of columns 
	 * and 'fill' behavior: the table automatically creates
	 * a new row when the current one is filled.
	 * 
	 * <p>Table width is set to default 100%.
	 * 
	 * @param nCols the number of columns in the table
	 * @return the created component
	 */
	public HtmlComposite addFillTable(final int nCols) {
		return addFillTable(nCols, "100%");
	}
	
	/**
	 * Adds a HTML table with the given number of columns 
	 * and 'fill' behavior: the table automatically creates
	 * a new row when the current one is filled.
	 * 
	 * @param nCols the number of columns in the table
	 * @param width the table width, for example '80%'.
	 * @return the created component
	 */
	public HtmlComposite addFillTable(final int nCols, final String width) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<table" + makeParam("width", width) + makeParam("class", cssClass) + "><tr>");
				int nData = 0;
				for (HtmlComposite child : this.children) {
					child.write(sb);
					nData++;
					if (nData == nCols) {
						nData = 0;
						output(sb, "</tr><tr>");
					}
				}
				// complete the last TR for alignment
				while (nData < nCols) {
					output(sb, "<td></td>");
					++nData;
				}
				output(sb, "</tr></table>");
			}
		});
	}
	
	/**
	 * Adds a table data (TD) element.
	 * 
	 * @return the created component.
	 */
	public HtmlComposite addTableData() {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<td>");
				writeChildren(sb);
				output(sb, "</td>");
			}
		});
	}
	/**
	 * Adds a table data (TD) element with the specified text.
	 * @param text  the cell's text.
	 */
	public void addTableData(final String text) {
		add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<td>" + text + "</td>");
			}
		});
	}
	
	/**
	 * Adds an HTML link to the given URL.
	 * 
	 * @param url the URL to link to
	 * @param title the link title for tooltips
	 * @return the created component
	 */
	public HtmlComposite addLink(final String url, final String title) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<a" + makeParam("href", url) + makeParam("title", title) + ">");
				writeChildren(sb);
				output(sb, "</a>", false);
			}
		});
	}

	/**
	 * Adds an HTML link to the given URL.
	 * 
	 * @param url the URL to link to
	 * @param title the link title for tooltips
	 * @param text  the link text
	 */
	public void addLink(final String url, final String title, final String text) {
		add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<a" + makeParam("href", url) + makeParam("title", title) + ">");
				output(sb, text + "</a>", false);
			}
		});
	}

	/**
	 * Adds an external link to the given URL.
	 * target="_blank"
	 * @param url the URL to link to
	 * @param title the link title for tooltips
	 * @param text  the link text
	 */
	public void addLinkExternal(final String url, final String text) {
		addLinkExternal(url, text, text);
	}

	/**
	 * Adds an external link to the given URL.
	 * target="_blank"
	 * @param url the URL to link to
	 * @param title the link title for tooltips
	 * @param text  the link text
	 */
	public void addLinkExternal(final String url, final String title, final String text) {
		add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<a" + makeParam("href", url) + makeParam("title", title) +
						makeParam("target", "_blank") + ">");
				output(sb, text + "</a>");
			}
		});
	}

	/**
	 * Add an HTML anchor with the given name.
	 * @param name the anchor name
	 * @return the created component.
	 */
	public HtmlComposite addAnchor(final String name) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<a" + makeParam("name", name) + ">");
				writeChildren(sb);
				output(sb, "</a>");
			}
		});
	}
	
	public HtmlComposite addForm(final String method, final String action) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<form" + makeParam("method", method) + makeParam("action", action) + ">");
				writeChildren(sb);
				output(sb, "</form>");
			}
		});
	}
	
	public HtmlComposite addInput(final String type, final String name) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<input" + makeParam("type", type) + makeParam("name", name) + "/>");
			}
		});
	}
	
	public HtmlComposite addButton(final String text) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<button" + makeParam("onClick", "submit") + ">" + text + "</button>");
			}
		});
	}
	
	public HtmlComposite addMeta(final String name, final String content) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<meta" + makeParam("name", name) + makeParam("content", content) + ">");
			}
		});
	}
	
	/**
	 * Adds a documentReady Javascript with the specified code.
	 * @param script the code to run on document ready.
	 * @return the created composite
	 */
	public HtmlComposite addDocumentReady(final String script) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<script>");
				output(sb, "$(document).ready(function() {");
				output(sb, script);
				output(sb, "});");
				output(sb, "</script>");
			}
		});
	}
	
	/**
	 * Adds the specified Javascript code in a script tag.
	 * @param sCode  the Javascript code to add
	 * @return the created script tag
	 */
	public HtmlComposite addJavascript(final String sCode) {
		return add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<script>");
				output(sb, sCode);
				output(sb, "</script>");
			}
		});		
	}
	
	/**
	 * Adds the specified JS script to the head.
	 * 
	 * @param path the relative path to the JS file.
	 */
	public void addScript(final String path) {
		add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<script" + makeParam("src", path) + ">" + makeEndTag("script"));
			}
		});
	}
	
	/**
	 * Adds the specified CSS stylesheet.
	 * 
	 * @param path the relative path to the CSS file.
	 */
	public void addCss(final String path) {
		add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<link" + 
						makeParam("rel", "stylesheet") + 
						makeParam("type", "text/css") + 
						makeParam("href", path) + ">");
			}
		});
	}
	
	/**
	 * Adds an HTML formatted comment.
	 * 
	 * @param text the comment text.
	 */
	public void addComment(final String text) {
		add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				output(sb, "<!-- " + text + " -->");
			}
		});
	}
	
	/**
	 * Adds a empty line in the generated HTML code.
	 */
	public void addNewLine() {
		add(new HtmlComposite() {
			@Override
			public void write(StringBuffer sb) {
				sb.append("\n");
			}
		});
	}
	
	/**
	 * Writes HTML code to the buffer, prepending indentation
	 * and formatting special characters for HTML.
	 * 
	 * @param sb the string buffer to write to
	 * @param out the HTML code to write
	 */
	protected void output(StringBuffer sb, String out) {
		output(sb, out, true);
	}
	
	protected void output(StringBuffer sb, String out, boolean addNewLine) {
		//System.out.println(level + " " + out);
		for (int i=0; addNewLine && i<level; i++)
			sb.append(indent);
		//sb.append(SpecialChars.frToHtml(out));
		sb.append(out);
		if (addNewLine) {
			sb.append("\n");
		}
	}

	/**
	 * Writes this component's children HTML code to the buffer.
	 * No effect if this component has no children.
	 * 
	 * @param sb the buffer to write to
	 */
	protected void writeChildren(StringBuffer sb) {
		for (HtmlComposite child : children)
			child.write(sb);
	}
	
	/**
	 * Sets this component's level.
	 * 
	 * @param level this component's level.
	 */
	protected void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * Builds a start tag.
	 * 
	 * @param name the tag name
	 * @return the start tag
	 */
	protected String makeTag(String name) {
		return "<" + name + ">";
	}
	
	/**
	 * Builds an end tag.
	 * 
	 * @param name the tag name
	 * @return the end tag
	 */
	protected String makeEndTag(String name) {
		return "</" + name + ">";
	}
	
	/**
	 * Builds a string representing a parameter as ' arg="value"'.
	 * A leading space is prepended.
	 * 
	 * @param arg the parameter name
	 * @param value the parameter value.
	 * @return empty string if value is null, else the parameter.
	 */
	protected String makeParam(String arg, String value) {
		if (value == null)
			return "";
		return " " + arg + "=\"" + value + "\"";
	}
}
