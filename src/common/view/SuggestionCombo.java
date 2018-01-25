package common.view;

import java.util.Vector;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;


/**
 * A combo that may be supplied with several text suggestions.
 * 
 * @author nicz
 *
 */
public class SuggestionCombo {
	
	/** Shortcut to the {@link WidgetsFactory}, for convenience */
	protected static final WidgetsFactory widgetsFactory =
		WidgetsFactory.getInstance();
	
	/** the combo */
	protected Combo combo;
	
	// TODO add progressive filtering of suggestions list

	/**
	 * Constructor.
	 * Creates widgets and interactions.
	 * 
	 * @param parent the parent composite
	 */
	public SuggestionCombo(Composite parent) {
		
		combo = widgetsFactory.createCombo(parent, -1, false, null);
	}
	
	/**
	 * Returns the content of the search text field.
	 * 
	 * @return the search string, or null if empty
	 */
	public String getText() { 
		String text = combo.getText();
		return text; 
		//return (text.isEmpty()) ? null : text; 
	}
	
	public void setText(String text) {
		if (text != null)
			combo.setText(text);
	}
	
	/**
	 * Sets the content of the search text field.
	 * 
	 * @param searchText the search text (may be null).
	 */
	public void setSearchText(String searchText) {
		if (searchText == null)
			searchText = "";
		combo.setText(searchText);
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
		
		for (String item : combo.getItems()) {
			if (searchText.equals(item))
				return;
		}
		
		combo.add(searchText);
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
	 * Enables or disables the combo.
	 */
	public void setEnabled(boolean isEnabled) {
		combo.setEnabled(isEnabled);
	}

}
