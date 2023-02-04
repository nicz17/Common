package common.html;

import java.util.Vector;

/**
 * An HTML Table element.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>04.02.2023: nicz - Creation</li>
 * </ul>
 */
public class TableHtmlTag extends HtmlTag {
	
	private final int nItemsPerRow;
	private HtmlTag row;

	/**
	 * @param sName
	 */
	public TableHtmlTag(int nItemsPerRow) {
		super("table");
		this.nItemsPerRow = nItemsPerRow;
		this.row = null;
	}
	
	public void addCell(HtmlTag tag) {
		HtmlTag td = new HtmlTag("td");
		td.addTag(tag);
		addTd(td);
	}
	
	public void addCell(Vector<HtmlTag> tags) {
		HtmlTag td = new HtmlTag("td");
		for (HtmlTag tag : tags) {
			td.addTag(tag);
		}
		addTd(td);
	}
	
	public void addCell(String cell) {
		addTd(new HtmlTag("td", cell));
	}
	
	private void addTd(HtmlTag td) {
		if (row == null) {
			row = new HtmlTag("tr");
			this.addTag(row);
		}
		row.addTag(td);
		if (row.size() == nItemsPerRow) {
			row = null;
		}
	}

}
