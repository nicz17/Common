package common.data;

/**
 * Interface for objects with geographical map coordinates :
 * longitude, latitude and map zoom.
 * 
 * <p>This allows to display the object on a map, for example at OpenStreetMap.
 *
 * <p><b>Modifications:</b>
 * <ul>
 * <li>23.12.2018: nicz - Creation</li>
 * </ul>
 */
public interface HasMapCoordinates {
	
	/**
	 * Gets the longitude as a Double.
	 * @return the longitude (may be null).
	 */
	public Double getLongitude();
	
	/**
	 * Gets the latitude as a Double.
	 * @return the latitude (may be null).
	 */
	public Double getLatitude();

	/**
	 * Gets the zoom level preferred for displaying this object on a map.
	 * Zoom level is between 1 (whole world) and 19 (as small area).
	 * @return the zoom level (1 to 19).
	 */
	public int getMapZoom();

	/**
	 * Gets the distance between this object and the specified object.
	 * Unit of distance measurement is left to implementation.
	 * @param objTo  the object to which the distance is computed
	 * @return  the distance, or null if some coordinates are undefined.
	 */
	public Double getDistance(HasMapCoordinates objTo);
}
