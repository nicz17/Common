package common.view;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;


/**
 * A {@link SearchBox} with incremental search feature:
 * the search is triggered each time the text field content is modified.
 * 
 * <p>Do not use this if the search operation is expensive !</>
 * 
 * @author nicz
 *
 */
public abstract class IncrementalSearchBox extends SearchBox {

	/**
	 * Constructor.
	 * @param parent the parent composite
	 */
	public IncrementalSearchBox(Composite parent) {
		super(parent);
		
		// add a listener for incremental search
		txtSearch.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				onSearch();
			}
		});
	}

}
