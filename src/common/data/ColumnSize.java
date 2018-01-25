package common.data;

/**
 * List of column sizes, for table columns.
 * 
 * @author nicz
 *
 */
public enum ColumnSize {

	TINY(0.05),
	SMALL(0.15),
	NUMBER(0.20),
	NORMAL(0.30),
	NAME(0.40),
	LARGE(0.70);
	
	private double size;

	private ColumnSize(double size) {
		this.size = size;
	}

	public double getSize() {
		return size;
	}
	
	
}
