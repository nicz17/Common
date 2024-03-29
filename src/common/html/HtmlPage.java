package common.html;

import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import common.base.Logger;

/**
 * An HTML page.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>04.02.2023: nicz - Creation</li>
 * </ul>
 */
public class HtmlPage {
	
	private static final Logger log = new Logger("HtmlPage", true);
	
	/** Formats date and time */
	protected static final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	/** Formats dates in French without time */
	protected static final DateFormat dateFormatFr = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
	
	private String sTitle;
	private String sFilename;
	private String sCss;
	private String sGenerator;
	protected String sPath;
	protected final HtmlTag html;
	protected final HtmlTag head;
	protected final HtmlTag body;
	protected final HtmlTag main;
	
	/**
	 * Constructor.
	 * @param sTitle      the HTML page title
	 * @param sFilename   the path for saving as file
	 * @param sPath       the relative path for internal links
	 * @param sCss        the CSS URL
	 * @param sGenerator  the name of the program generating this document
	 */
	public HtmlPage(String sTitle, String sFilename, String sPath, String sCss, String sGenerator) {
		this.sTitle = sTitle;
		this.sFilename = sFilename;
		this.sCss = sCss;
		this.sPath = sPath;
		this.sGenerator = sGenerator;
		this.html = new HtmlTag("html");
		this.head = new HtmlTag("head");
		this.body = new HtmlTag("body");
		this.main = HtmlTagFactory.div("main");
		buildHead();
		buildBody();
	}

	/**
	 * Adds a H[1-6] title tag to the main div.
	 * @param iLevel  the title level (1 to 6).
	 * @param sTitle  the title text
	 */
	public void addTitle(int iLevel, String sTitle) {
		add(HtmlTagFactory.title(iLevel, sTitle));
	}
	
	/**
	 * Adds an anchor with the specified name.
	 * @param sName  the anchor name
	 */
	public HtmlTag addAnchor(String sName) {
		HtmlTag tag = HtmlTagFactory.anchor(sName);
		add(tag);
		return tag;
	}
	
	/**
	 * Adds an empty table to the main div and returns it.
	 * @param nCellsPerRow  number of cells per row.
	 * @return  the created table
	 */
	public TableHtmlTag addTable(int nCellsPerRow) {
		return addTable(nCellsPerRow, null);
	}
	
	/**
	 * Adds an empty table to the main div and returns it.
	 * @param nCellsPerRow  number of cells per row.
	 * @param width  table width
	 * @return  the created table
	 */
	public TableHtmlTag addTable(int nCellsPerRow, String width) {
		TableHtmlTag table = new TableHtmlTag(nCellsPerRow);
		if (width != null) {
			table.addAttribute("width", width);
		}
		add(table);
		return table;
	}
	
	/**
	 * Adds an empty table to the main div and returns it.
	 * @param nCellsPerRow  number of cells per row.
	 * @param width  table width
	 * @return  the created table
	 */
	public TableHtmlTag addFillTable(int nCellsPerRow, String width) {
		TableHtmlTag table = new TableHtmlTag(nCellsPerRow, true);
		if (width != null) {
			table.addAttribute("width", width);
		}
		add(table);
		return table;
	}

	/**
	 * Adds an empty list to the main div and returns it.
	 * @return  the created list
	 */
	public ListHtmlTag addList() {
		ListHtmlTag list = new ListHtmlTag();
		add(list);
		return list;
	}
	
	/**
	 * Adds a paragraph element to the main div and returns it.
	 * @param text  the paragraph text.
	 * @return  the created paragraph.
	 */
	public ParHtmlTag addParagraph(String text) {
		ParHtmlTag par = new ParHtmlTag(text);
		add(par);
		return par;
	}
	
	/**
	 * Adds a div to the main div and returns it.
	 * @param id  the div id
	 * @return  the created div
	 */
	public HtmlTag addDiv(String id) {
		HtmlTag div = HtmlTagFactory.div(id);
		add(div);
		return div;
	}
	
	/**
	 * Adds a myBox div to the main div and returns it.
	 * @param title  the myBox title
	 * @return  the created div
	 */
	public HtmlTag addBox(String title) {
		HtmlTag box = HtmlTagFactory.blueBox(title);
		add(box);
		return box;
	}
	
	/**
	 * Creates a span element with the specified class and text
	 * and adds it to the main div.
	 * @param sClass  the span class
	 * @param text    the text
	 * @return  the created span
	 */
	public HtmlTag addSpan(String sClass, String text) {
		HtmlTag span = new HtmlTag("span", text);
		span.setClass(sClass);
		add(span);
		return span;
	}
	
	/**
	 * Add a script tag with the specified Javascript code.
	 * @param sCode  the JS code
	 * @return the craeted tag
	 */
	public HtmlTag addJavascript(String sCode) {
		HtmlTag tag = new HtmlTag("script", sCode);
		add(tag);
		return tag;
	}
	
	/**
	 * Add a tag to the main div.
	 * @param tag  the tag to add.
	 */
	public void add(HtmlTag tag) {
		this.main.addTag(tag);
	}
	
	/**
	 * Save this page as a HTML file.
	 */
	public void save() {
		log.info("Saving " + sFilename);
		try {
			FileWriter fw = new FileWriter(sFilename);
			fw.write("<!DOCTYPE html>");
			fw.write(html.toHtml(0, false));
			fw.close();
		} catch (Exception exc) {
			log.error("Saving document failed: " + exc.getMessage());
		}
	}
	
	public HtmlTag getHead() {
		return head;
	}
	
	public String getLink(String url, String text, boolean isExternal) {
		HtmlTag link = HtmlTagFactory.link(url, text, text, isExternal);
		return link.toHtml(0, true);
	}
	
	/**
	 * Adds the HTML head element with a generation comment,
	 * the title and CSS link.
	 */
	protected void buildHead() {
		String sDate = dateFormat.format(new Date());
		String sComment = "Generated by " + sGenerator + " on " + sDate;
		head.addTag(HtmlTagFactory.comment(sComment));
		head.addTag(new HtmlTag("title", sTitle));
		head.addTag(HtmlTagFactory.cssLink(sPath + sCss));
		head.addTag(HtmlTagFactory.iconLink());
		head.addTag(HtmlTagFactory.meta("author", "Nicolas Zwahlen"));
		head.addTag(HtmlTagFactory.meta().addAttribute("charset", "utf-8"));
		html.addTag(head);
	}
	
	/**
	 * Adds the HTML body element with its main div.
	 */
	protected void buildBody() {
		buildHeader();
		buildMenu();
		html.addTag(body);
		body.addTag(main);
		buildFooter();
	}
	
	/**
	 * Adds the header div.
	 */
	protected void buildHeader() {
		// No header by default
	}

	/**
	 * Adds the menu div.
	 */
	protected void buildMenu() {
		// No menu by default
	}

	/**
	 * Adds the footer div.
	 */
	protected void buildFooter() {
		// No footer by default
	}

	/**
	 * Internal test case.
	 */
	private static void testHtmlPage() {
		log.info("Testing HtmlPage");
		final String sFilename = "/home/nicz/www/test/test.html";
		final String css = "../scripts/style.css";
		HtmlPage test = new HtmlPage("Test", sFilename, "", css, "testHtmlPage.java");
		test.add(HtmlTagFactory.title(1, "HtmlPage test"));

		// Test list
		test.add(HtmlTagFactory.title(2, "List test"));
		ListHtmlTag list = new ListHtmlTag();
		for (int i=0; i<4; ++i) {
			list.addItem("item " + (i+1));
		}
		test.add(list);
		
		// Test table
		test.add(HtmlTagFactory.title(2, "Table test"));
		TableHtmlTag table = new TableHtmlTag(4);
		for (int i=0; i<11; ++i) {
			table.addCell("cell " + (i+1));
		}
		test.add(table);
		
		// Test links
		test.add(HtmlTagFactory.title(2, "Link test"));
		test.add(HtmlTagFactory.link("http://www.google.com", "Google"));
		test.add(HtmlTagFactory.link("../recettes/index.html", "Recettes"));
		
		// Test images
		test.add(HtmlTagFactory.title(2, "Image test"));
		table = new TableHtmlTag(2);
		for (int i=0; i<2; ++i) {
			String src = "../../prog/python/Orfact/images/RandomImage0" + i + ".png";
			table.addCell(HtmlTagFactory.image(src, "Orfact random image", "fail"));
		}
		test.add(table);
		
		test.save();
	}
	
	/**
	 * Main entry point.
	 * @param args  unused
	 */
	public static void main(String[] args) {
		testHtmlPage();
	}
}