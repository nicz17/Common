package common.view;


import org.eclipse.swt.widgets.Composite;


/**
 * A SWT {@link Composite} for display in a tabbed folder.
 * 
 * @author nicz
 *
 */
public abstract class BaseModule extends Composite {
	
	/** Shortcut to the {@link WidgetsFactory}, for convenience */
	protected static final WidgetsFactory widgetsFactory =
		WidgetsFactory.getInstance();
	
	/**
	 * Constructor.
	 * 
	 * @param parent the parent composite
	 */
	public BaseModule(Composite parent) {
		super(parent, 0);
	}
	
	/**
	 * Try to select the object with the specified database index.
	 * @param idx  the database index of the object to select.
	 */
	public void selectObject(int idx) {
	}

	/**
	 * Build the module's widgets.
	 * Called by constructor.
	 */
	protected abstract void loadWidgets();
	
	/**
	 * Load the data and display it in the widgets.
	 * Called by constructor.
	 */
	protected abstract void loadData();
	
}
