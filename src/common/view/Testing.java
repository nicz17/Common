package common.view;

import org.eclipse.swt.layout.GridLayout;

import common.base.Logger;

/**
 * A GUI testing class for Common objects.
 * 
 * @author nicz
 *
 */
public class Testing extends AbstractMain {

	private static final Logger log = new Logger("Testing", true);
	
	/** The application name. Appears in the main window. */
	private static final String appName = "Testing";
	
	/** 
	 * The application version. 
	 */
	private static final String appVersion = "1.0.0 DEV";
	
	/** Shortcut to the {@link WidgetsFactory}, for convenience */
	protected static final WidgetsFactory widgetsFactory =
		WidgetsFactory.getInstance();
	
	/** the singleton instance */
	private static Testing instance;
	
	
	/**
	 * @return the singleton instance.
	 */
	public static Testing getInstance() {
		if (instance == null)
			instance = new Testing();
		return instance;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean maximized = false;
		if (args.length > 0 && args[0].equals("-f")) maximized = true;
		
		Testing main = Testing.getInstance();
		main.init();
		main.open(maximized);

		Logger.setGlobalDebug(true);
	}

	/**
	 * Initializes the application and builds the widgets.
	 */
	@Override
	protected void onInit() {
		//controller = Controller.getInstance();
		//moduleFactory = ModuleFactory.getInstance();
		//ViewTools.init(display);
		
		shell.setImage(IconManager.getIcon("run"));
	}

	@Override
	protected void buildWidgets() {
		shell.setLayout(new GridLayout());
		
		new TestingModule(shell, 300);
	}

	@Override
	protected void onTerminate() {
		log.info("Done.");
	}

	/**
	 * Private singleton constructor.
	 * Creates a new display and shell.
	 */
	private Testing() {
		super(appName, appVersion);
		
		shell.setMinimumSize(600, 500);
		shell.setLocation(50, 50);
	}

}
