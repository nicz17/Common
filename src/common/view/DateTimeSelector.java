package common.view;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Widget to allow the user to define a date and time.
 * The date is stored as a Java Date object, and displayed using a DateFormat.
 * <p>Registered {@link DateTimeSelectionListener}s are notified of user changes 
 * in the date.
 * <p>If the entered date can't be parsed, the date input text is displayed in red.
 * 
 * @author nicz
 *
 */
public class DateTimeSelector extends Composite {
	private DateFormat dateFormat;
	private int iDateLength;
	private Date tAt;
	private Text textDateTime;
	private Label lblStatus;
	private Vector<DateTimeSelectionListener> vecListeners;
	
	/**
	 * Listener to notify about selection changes.
	 * @author nicz
	 */
	public interface DateTimeSelectionListener {
		/**
		 * Called when user changes the selected size.
		 * @param pNewDate the currently selected date.
		 */
		public void onSelectionChange(Date tNewDate);
	}

	/**
	 * Constructor.
	 * 
	 * @param parent the parent Composite.
	 */
	public DateTimeSelector(Composite parent, DateFormat dateFormat, int iDateLength) {
		super(parent, 0);

		this.dateFormat = dateFormat;
		this.iDateLength = iDateLength;
		this.tAt = null;
		this.vecListeners = new Vector<DateTimeSelectionListener>();
		
		GridLayout gl = new GridLayout(2, true);
		gl.marginWidth = 0;
		this.setLayout(gl);
		buildWidgets();
	}

	/**
	 * @return the currently selected date.
	 */
	public Date getSelection() {
		return tAt;
	}
	
	/**
	 * Returns the currently selected time in milliseconds since epoch.
	 * If date is empty or invalid, returns -1.
	 * @return milliseconds since epoch, or -1 if invalid.
	 */
	public long getTimeMs() {
		if (tAt == null) {
			return -1;
		} else {
			return tAt.getTime();
		}
	}

	/**
	 * Set the selected size and update the spinners.
	 * @param pSize the size to set.
	 */
	public void setSelection(Date tAt) {
		this.tAt = tAt;
		if (textDateTime != null) {
			textDateTime.setText(tAt == null ? "" : dateFormat.format(tAt));
		}
	}
	
	/**
	 * Add a listener to the list of listeners who will be notified of
	 * a change in the selected size.
	 * @param list the listener to add.
	 */
	public void addSelectionListener(DateTimeSelectionListener list) {
		vecListeners.add(list);
	}
	
	/**
	 * Notify the registered listeners that the selected date has changed.
	 */
	private void notifySelectionListeners() {
		for (DateTimeSelectionListener list : vecListeners) {
			list.onSelectionChange(tAt);
		}
	}
	
	/**
	 * Build the to Spinners that allow selecting the size.
	 */
	private void buildWidgets() {
		textDateTime = WidgetsFactory.getInstance().createText(this, iDateLength, new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent evt) {
				try {
					String sAt = textDateTime.getText();
					if (sAt == null || sAt.length() != iDateLength) {
						throw new ParseException("Invalid date length, expected " + iDateLength, 0);
					}
					tAt = dateFormat.parse(textDateTime.getText());
					lblStatus.setText("");
					textDateTime.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
					notifySelectionListeners();
				} catch (ParseException exc) {
					tAt = null;
					lblStatus.setText("Date invalide !");
					textDateTime.setForeground(getDisplay().getSystemColor(SWT.COLOR_RED));
				}
			}
		});
		
		lblStatus = WidgetsFactory.getInstance().createLabel(this);
		lblStatus.setForeground(getDisplay().getSystemColor(SWT.COLOR_RED));
	}

}
