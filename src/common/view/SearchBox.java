package common.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;


/**
 * A search widget with a text box, search and clear buttons.
 * 
 * @author nicz
 *
 */
public abstract class SearchBox extends Composite {
	
	/** Shortcut to the {@link WidgetsFactory}, for convenience */
	protected static final WidgetsFactory widgetsFactory =
		WidgetsFactory.getInstance();
	
	/** the search text field */
	protected Text txtSearch;
	
	protected ToolBar toolbar;

	/**
	 * Constructor.
	 * Creates widgets and interactions.
	 * 
	 * @param parent the parent composite
	 */
	public SearchBox(Composite parent) {
		super(parent, 0);
		
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		txtSearch = widgetsFactory.createText(this);
		txtSearch.setToolTipText("Champ de recherche");
		// 'Enter' listener
		txtSearch.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event e) {
				onSearch();
			}
		});
		
		toolbar = widgetsFactory.createToolbar(this);
		
		// clear button
		widgetsFactory.createToolItem(toolbar, "clear-bar", "Effacer la recherche",
				new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				txtSearch.setText("");
				onSearch();
			}
		});
		
		// search button
		widgetsFactory.createToolItem(toolbar, "find", "Chercher", 
				new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				onSearch();
			}
		});
	}
	
	/**
	 * Returns the content of the search text field.
	 * 
	 * @return the search string, or null if empty
	 */
	public String getSearchText() { 
		String strSearch = txtSearch.getText();
		return (strSearch.isEmpty()) ? null : strSearch; 
	}
	
	/**
	 * Sets the content of the search text field.
	 * 
	 * @param searchText the search text (may be null).
	 */
	public void setSearchText(String searchText) {
		if (searchText == null)
			searchText = "";
		txtSearch.setText(searchText);
	}
	
	/**
	 * Called when a search operation is triggered.
	 * Must be implemented by concrete subclasses.
	 */
	public abstract void onSearch();
	
	/**
	 * Enables or disables the search box.
	 */
	@Override
	public void setEnabled(boolean isEnabled) {
		txtSearch.setEnabled(isEnabled);
		toolbar.setEnabled(isEnabled);
	}

}
