package common.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * A horizontal bar with previous and next arrow buttons, 
 * and a label in between to display the current selection.
 * 
 * <p>Allows to navigate between several items.
 * Navigation is cyclic.</p>
 * 
 * @author nicz
 *
 */
public abstract class CyclicNavigationBar extends Composite {
	
	protected Button btnPrev, btnNext;
	protected Label lblStatus;
	
	protected int curPos;
	protected int maxPos;

	/**
	 * Constructor.
	 * @param parent the parent composite
	 * @param total the total number of items
	 */
	public CyclicNavigationBar(Composite parent, int total) {
		super(parent, 0);
		
		this.setLayout(new GridLayout(3, false));
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		this.curPos = 0;
		this.maxPos = total;
		
		btnPrev = new Button(this, SWT.ARROW | SWT.LEFT);
		btnPrev.setToolTipText("Précédant");
		btnPrev.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				curPos--;
				if (curPos < 0) curPos = maxPos-1;
				onSelectionChange(curPos);
			}
		});
		
		lblStatus = new Label(this, SWT.CENTER);
		lblStatus.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		btnNext = new Button(this, SWT.ARROW | SWT.RIGHT);
		btnNext.setToolTipText("Suivant");
		btnNext.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				curPos++;
				if (curPos >= maxPos) curPos = 0;
				onSelectionChange(curPos);
			}
		});
		
		setStatus(getIofN());
		setEnabled(maxPos > 0);
	}
	
	/**
	 * Get the currently selected item.
	 * The first item is at 0.
	 * @return the current (zero-based) selection 
	 */
	public int getSelection() {
		return curPos;
	}
	
	/**
	 * Get the current selection description as 'i/n'.
	 * Suitable for setStatus method.
	 * @return the current selection description as 'i/n'.
	 */
	public String getIofN() {
		return String.format("%d/%d", curPos+1, maxPos);
	}
	
	/**
	 * Displays the given status string.
	 * 
	 * @param status the status to display.
	 */
	public void setStatus(String status) {
		setStatus(status, false);
	}
	
	/**
	 * Displays the given status string.
	 * 
	 * @param status the status to display.
	 * @param update if true, repaints the label
	 */
	public void setStatus(String status, boolean update) {
		lblStatus.setText(status);
		if (update)
			lblStatus.update();
	}
	
	/**
	 * Set the total number of items.
	 * Keep the current selection if possible.
	 * 
	 * @param total the total number of items.
	 */
	public void setTotal(int total) {
		this.maxPos = total;
		setEnabled(maxPos > 0);
		if (curPos >= maxPos)
			reset(total);
	}
	
	/**
	 * Reset the navigation bar to 0, with new total.
	 * Disables the bar if total is less than 1.
	 * Triggers a selection change.
	 * 
	 * @param total the total number of items
	 */
	public void reset(int total) {
		this.maxPos = total;
		this.curPos = 0;
		setEnabled(maxPos > 0);
		onSelectionChange(curPos);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		boolean canMove = (maxPos > 1);
		btnPrev.setEnabled(enabled && canMove);
		btnNext.setEnabled(enabled && canMove);
		lblStatus.setEnabled(enabled);
	}
	
	/**
	 * What to do when the selection has changed.
	 * @param selection the current selection.
	 */
	public abstract void onSelectionChange(int selection);

}
