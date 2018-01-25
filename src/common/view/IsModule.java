package common.view;

/**
 * Interface for enumerations of application modules.
 * 
 * A module instantiates a class, has a title and an icon.
 * 
 * @author nicz
 *
 */
public interface IsModule {
	
	
	/**
	 * Gets the class name of the module.
	 * 
	 * @return the class name of the module.
	 */
	public String getModuleClass();
	
	/**
	 * Gets the title of the module.
	 * 
	 * @return the title of the module.
	 */
	public String getTitle();
	
	/**
	 * Gets the icon of the module.
	 * 
	 * @return the icon of the module.
	 */
	public String getIcon();
	
}
