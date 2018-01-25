package common.view;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

/**
 * Widget to allow the user to define a two-dimensional size.
 * The size is stored as a Point and is controlled through two Spinners.
 * Registered {@link SizeSelectionListener}s are notified of user changes 
 * in the size.
 * 
 * @author nicz
 *
 */
public class SizeSelector extends Composite {
	private Point pSize;
	private Spinner spiX, spiY;
	private Vector<SizeSelectionListener> vecListeners;
	
	/**
	 * Listener to notify about selection changes.
	 * @author nicz
	 */
	public interface SizeSelectionListener {
		/**
		 * Called when user changes the selected size.
		 * @param pNewSize the currently selected size.
		 */
		public void onSelectionChange(Point pNewSize);
	}

	/**
	 * Constructor. Builds the two Spinners.
	 * 
	 * @param parent the parent Composite.
	 * @param unit the unit of the size represented by this widget (pixels, cm, ...)
	 */
	public SizeSelector(Composite parent, String unit) {
		super(parent, 0);

		pSize = new Point(5, 5);
		vecListeners = new Vector<SizeSelectionListener>();
		
		GridLayout gl = new GridLayout(4, false);
		gl.marginWidth = 0;
		this.setLayout(gl);
		buildWidgets(unit);
	}

	/**
	 * @return the currently selected size.
	 */
	public Point getSelection() {
		return pSize;
	}

	/**
	 * Set the selected size and update the spinners.
	 * @param pSize the size to set.
	 */
	public void setSelection(Point pSize) {
		this.pSize = pSize;
		spiX.setSelection(pSize.x);
		spiY.setSelection(pSize.y);
	}
	
	/**
	 * Add a listener to the list of listeners who will be notified of
	 * a change in the selected size.
	 * @param list the listener to add.
	 */
	public void addSizeSelectionListener(SizeSelectionListener list) {
		vecListeners.add(list);
	}
	
	/**
	 * Notify the registered listeners that the selected size has changed.
	 */
	private void notifySizeSelectionListeners() {
		for (SizeSelectionListener list : vecListeners) 
			list.onSelectionChange(pSize);
	}
	
	/**
	 * Build the to Spinners that allow selecting the size.
	 * @param unit the unit of the size.
	 */
	private void buildWidgets(String unit) {
		spiX = new Spinner(this, SWT.BORDER);
		spiX.setLayoutData(new GridData());
		spiX.setValues(pSize.x, 3, 20, 0, 1, 5);
		spiX.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				pSize.x = spiX.getSelection();
				notifySizeSelectionListeners();
			}
		});
		
		new Label(this, 0).setText(" x ");
		
		spiY = new Spinner(this, SWT.BORDER);
		spiY.setLayoutData(new GridData());
		spiY.setValues(pSize.y, 3, 20, 0, 1, 5);
		spiY.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				pSize.y = spiY.getSelection();
				notifySizeSelectionListeners();
			}
		});
		
		new Label(this, 0).setText(unit);
	}

}
