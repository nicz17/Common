package common.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import common.base.Logger;
import common.io.SpecialChars;

public abstract class AbstractMain {

	private static final Logger log = new Logger("AbstractMain", true);
	
	/** Shortcut to the {@link WidgetsFactory}, for convenience */
	protected static final WidgetsFactory widgetsFactory =
		WidgetsFactory.getInstance();

	/** 
	 * The application name. Appears in the main window. 
	 */
	protected String appName;
	
	/** 
	 * The application version. 
	 * Only increment before exporting a new jar.
	 */
	protected String appVersion;
	
	/**
	 * The application's main window. 
	 */
	protected Shell shell;
	
	/**
	 * The application's display.
	 * Handles connection to OS, and events.
	 */
	protected Display display;
	
	
	public String getAppName() {
		return appName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public Display getDisplay() {
		return display;
	}

	/**
	 * Protected constructor for concrete subclasses.
	 * 
	 * @param appName    the application name
	 * @param appVersion the application version (for example '1.0.0')
	 */
	protected AbstractMain(String appName, String appVersion) {
		this.appName = appName;
		this.appVersion = appVersion;
		
		log.info("Initialising " + appName + " version " + appVersion);
		display = new Display();
		shell   = new Shell(display);
		shell.setText(appName + " - v" + appVersion);
	}
	
	/**
	 * Initializes the application.
	 */
	protected abstract void onInit();

	/**
	 * Creates the application's widgets.
	 */
	protected abstract void buildWidgets();
	
	/**
	 * Called on application end, before disposing the display.
	 */
	protected abstract void onTerminate();

	/**
	 * Initializes the application and builds the widgets.
	 */
	protected void init() {
		IconManager.init(display);
		MessageBox.init(shell, appName);
		SpecialChars.init();
		
		onInit();
		
		buildWidgets();
	}
	
	/**
	 * Opens the shell and starts the main event dispatching loop.
	 * Waits until the shell is disposed.
	 * 
	 * @param maximized to open in fullscreen
	 */
	protected void open(boolean maximized) {
		shell.pack();
		shell.setMaximized(maximized);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		terminate();
	}

	/**
	 * Terminates the application, disposing the display.
	 */
	private void terminate() {
		onTerminate();
		IconManager.disposeIcons();	
		display.dispose();		
	}
	
}
