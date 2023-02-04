package common.html;

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
	
	public void addCell(String cell) {
		if (row == null) {
			row = new HtmlTag("tr");
			this.addTag(row);
		}
		row.addTag(new HtmlTag("td", cell));
		if (row.size() == nItemsPerRow) {
			row = null;
		}
	}

}
