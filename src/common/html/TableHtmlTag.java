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
	
	private final int nCellsPerRow;
	private HtmlTag row;
	private final boolean bAutoFill;

	/**
	 * Constructor.
	 * @param nCellsPerRow  number of cells in a row
	 */
	public TableHtmlTag(int nCellsPerRow) {
		this(nCellsPerRow, false);
	}

	/**
	 * Constructor.
	 * @param nCellsPerRow  number of cells in a row
	 * @param bAutoFill  if true, add empty cells to fill last row
	 */
	public TableHtmlTag(int nCellsPerRow, boolean bAutoFill) {
		super("table");
		this.nCellsPerRow = nCellsPerRow;
		this.bAutoFill = bAutoFill;
		this.row = null;
	}
	
	@Override
	public String toHtml(int iDepth, boolean isInline) {
		if (bAutoFill && row != null) {
			while (row != null && row.size() % nCellsPerRow > 0) {
				addCell();
			}
		}
		return super.toHtml(iDepth, isInline);
	}
	
	/**
	 * Adds the specified tag to a new cell.
	 * @param tag  the tag to add
	 */
	public void addCell(HtmlTag tag) {
		HtmlTag td = new HtmlTag("td");
		td.addTag(tag);
		addTd(td);
	}

	/**
	 * Adds the specified tags to a single new cell.
	 * @param tags  the tags to add
	 */
	public void addCell(Vector<HtmlTag> tags) {
		HtmlTag td = new HtmlTag("td");
		for (HtmlTag tag : tags) {
			td.addTag(tag);
		}
		addTd(td);
	}
	
	/**
	 * Adds the specified text to a new cell.
	 * @param cell  the text to add.
	 */
	public void addCell(String cell) {
		addTd(new HtmlTag("td", cell));
	}
	
	/**
	 * Adds an empty cell to the table and returns it.
	 * @return a TD tag
	 */
	public HtmlTag addCell() {
		HtmlTag td = new HtmlTag("td");
		addTd(td);
		return td;
	}
	
	/**
	 * Add the specified cell to this table.
	 * Create a new row if needed.
	 * @param td  the TD cell to add.
	 */
	private void addTd(HtmlTag td) {
		if (row == null) {
			row = new HtmlTag("tr");
			this.addTag(row);
		}
		row.addTag(td);
		if (row.size() == nCellsPerRow) {
			row = null;
		}
	}

}
