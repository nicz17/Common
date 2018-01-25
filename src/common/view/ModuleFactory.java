package common.view;

import org.eclipse.swt.widgets.Control;

import common.base.Logger;
import common.exceptions.AppException;



/**
 * Module loading class.
 * 
 * @author nicz
 *
 */
public class ModuleFactory {
	
	private static final Logger log = new Logger("ModuleFactory", true);
	
	private static ModuleFactory instance;
	
	/**
	 * Private singleton constructor.
	 */
	private ModuleFactory() { }
	
	/**
	 * @return the singleton instance.
	 */
	public static ModuleFactory getInstance() {
		if (instance == null)
			instance = new ModuleFactory();
		return instance;
	}
	
	/**
	 * Loads a new instance of the given module.
	 * 
	 * @param module the module to load
	 * @return a new instance of the given module
	 * @throws DrinksException if failed to create module
	 */
	public Control createModule(IsModule module) throws AppException {
		String moduleClass = module.getModuleClass();
		log.info("Loading module " + module.getTitle());
		
		try {
			return (Control) Class.forName(moduleClass).newInstance();
		} catch (Exception e) {
			log.error("Failed to load module: " + e.getMessage());
			throw new AppException("Failed to load module: " + e.getMessage());
		}
	}
	
	
}
