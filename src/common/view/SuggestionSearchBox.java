package common.view;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;


/**
 * A search widget with a combo, search and clear buttons.
 * The combo may be supplied with several search suggestions.
 * 
 * @author nicz
 *
 */
public abstract class SuggestionSearchBox extends Composite {
	
	/** Shortcut to the {@link WidgetsFactory}, for convenience */
	protected static final WidgetsFactory widgetsFactory =
		WidgetsFactory.getInstance();
	
	/** the search combo */
	protected Combo cboSearch;
	
	protected ToolBar toolbar;

	/**
	 * Constructor.
	 * Creates widgets and interactions.
	 * 
	 * @param parent the parent composite
	 */
	public SuggestionSearchBox(Composite parent) {
		super(parent, 0);
		
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		cboSearch = widgetsFactory.createCombo(this, -1, false, null);
		cboSearch.setToolTipText("Champ de recherche");
		// 'Enter' listener
		cboSearch.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event e) {
				addSuggestion(getSearchText());
				onSearch();
			}
		});
		
		toolbar = widgetsFactory.createToolbar(this);
		
		// clear button
		widgetsFactory.createToolItem(toolbar, "clear-bar", "Effacer la recherche",
				new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				cboSearch.setText("");
				onSearch();
			}
		});
		
		// search button
		widgetsFactory.createToolItem(toolbar, "find", "Chercher", 
				new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				addSuggestion(getSearchText());
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
		String strSearch = cboSearch.getText();
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
		cboSearch.setText(searchText);
	}
	
	/**
	 * Adds a suggested search string.
	 * Only adds it if it is not null and not already in the combo.
	 * 
	 * @param searchText the search suggestion
	 */
	public void addSuggestion(String searchText) {
		if (searchText == null || searchText.isEmpty())
			return;
		
		for (String item : cboSearch.getItems()) {
			if (searchText.equals(item))
				return;
		}
		
		cboSearch.add(searchText);
		if (cboSearch.getText().isEmpty())
			cboSearch.setText(searchText);
	}
	
	/**
	 * Adds several suggested search strings.
	 * Only adds a suggestion if it is not null and not already in the combo.
	 * 
	 * @param vecSearchTexts the search suggestions
	 */
	public void addSuggestions(Vector<String> vecSearchTexts) {
		if (vecSearchTexts == null)
			return;
		for (String searchText : vecSearchTexts)
			addSuggestion(searchText);
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
		cboSearch.setEnabled(isEnabled);
		toolbar.setEnabled(isEnabled);
	}

}
