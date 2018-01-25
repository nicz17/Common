package common.data;

/**
 * A data entity, like a database field.
 * 
 * @author nicz
 *
 */
public interface IsDataField {
	
	/**
	 * Gets the database name of the field.
	 * 
	 * @return the database name of the field.
	 */
	public String getDbName();

	/**
	 * Gets the description of the field, for GUI.
	 * 
	 * @return the description of the field
	 */
	public String getDescription();

	/**
	 * Gets the width of a table column displaying the field.
	 * 
	 * @return the width of a table column
	 */
	public ColumnSize getWidth();

}
