package common.html;

import java.io.FileWriter;
import java.text.DateFormat;
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
	protected static final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
	
	private String sTitle;
	private String sFilename;
	private String sCss;
	private String sGenerator;
	protected String sPath;
	protected final HtmlTag html;
	protected final HtmlTag body;
	protected final HtmlTag main;
	
	public HtmlPage(String sTitle, String sFilename, String sPath, String sCss, String sGenerator) {
		this.sTitle = sTitle;
		this.sFilename = sFilename;
		this.sCss = sCss;
		this.sPath = sPath;
		this.sGenerator = sGenerator;
		this.html = new HtmlTag("html");
		this.body = new HtmlTag("body");
		this.main = new DivHtmlTag("main");
		buildHead();
		buildBody();
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
	
	public String getLink(String url, String text, boolean isExternal) {
		HtmlTag link = HtmlTagFactory.link(url, text, isExternal);
		return link.toHtml(0, true);
	}
	
	protected void buildHead() {
		HtmlTag head = new HtmlTag("head");
		String sDate = dateFormat.format(new Date());
		String sComment = "Generated by " + sGenerator + " on " + sDate;
		head.addTag(HtmlTagFactory.comment(sComment));
		head.addTag(new HtmlTag("title", sTitle));
		head.addTag(HtmlTagFactory.cssLink(sPath + sCss));
		html.addTag(head);
	}
	
	protected void buildBody() {
		buildHeader();
		buildMenu();
		html.addTag(body);
		body.addTag(main);
		buildFooter();
	}
	
	protected void buildHeader() {
		// No header by default
	}
	
	protected void buildMenu() {
		// No menu by default
	}
	
	protected void buildFooter() {
		// No footer by default
	}

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
			table.addCell(HtmlTagFactory.image(src, "Orfact random image"));
		}
		test.add(table);
		
		test.save();
	}
	
	/**
	 * Testing method
	 * @param args  unused
	 */
	public static void main(String[] args) {
		testHtmlPage();
	}
}