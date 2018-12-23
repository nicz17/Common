package common.view;

import java.io.IOException;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import common.data.HasMapCoordinates;

/**
 * Widget to define map coordinates : longitude, latitude and zoom.
 * <p>A link to OpenStreetMap is provided, and an OpenStreetMap URL can be parsed to input values.
 * <p>Registered {@link LonLatZoomSelectionListener}s are notified of user changes 
 * in the coordinates.
 * 
 * @author nicz
 *
 */
public class LonLatZoomSelector extends Composite {
	// Input data
	private Double dLongitude;
	private Double dLatitude;
	private int iZoom;
	
	// Widgets
	private Text textLongitude;
	private Text textLatitude;
	private Spinner spiZoom;
	private Button btnPreview;
	private Button btnParse;
	
	private Vector<LonLatZoomSelectionListener> vecListeners;
	
	/**
	 * Listener to notify about selection changes.
	 * @author nicz
	 */
	public interface LonLatZoomSelectionListener {
		/**
		 * Called when user changes the selected coordinates.
		 */
		public void onSelectionChange();
	}

	/**
	 * Constructor.
	 * 
	 * @param parent the parent Composite.
	 */
	public LonLatZoomSelector(Composite parent) {
		super(parent, 0);

		this.dLongitude = null;
		this.dLatitude = null;
		this.vecListeners = new Vector<LonLatZoomSelectionListener>();
		
		GridLayout gl = new GridLayout(8, false);
		gl.marginWidth = 0;
		this.setLayout(gl);
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		buildWidgets();
	}
	
	public Double getLongitude() {
		return dLongitude;
	}

	public void setLongitude(Double dLongitude) {
		this.dLongitude = dLongitude;
		if (dLongitude == null) {
			textLongitude.setText("");
		} else {
			textLongitude.setText(String.valueOf(dLongitude.doubleValue()));
		}
	}

	public Double getLatitude() {
		return dLatitude;
	}

	public void setLatitude(Double dLatitude) {
		this.dLatitude = dLatitude;
		if (dLatitude == null) {
			textLatitude.setText("");
		} else {
			textLatitude.setText(String.valueOf(dLatitude.doubleValue()));
		}
	}

	public int getZoom() {
		return iZoom;
	}

	public void setZoom(int iZoom) {
		this.iZoom = iZoom;
		spiZoom.setSelection(iZoom);
	}
	
	public void setSelection(Double dLongitude, Double dLatitude, int iZoom) {
		setLongitude(dLongitude);
		setLatitude(dLatitude);
		setZoom(iZoom);
		enableWidgets(true);
	}
	
	/**
	 * Sets the selection from the specified object with map coordinates.
	 * @param obj  the object from which to set the selection
	 */
	public void setSelection(HasMapCoordinates obj) {
		if (obj != null) {
			setSelection(obj.getLongitude(), obj.getLatitude(), obj.getMapZoom());
		} else {
			clear();
		}
	}
	
	/**
	 * Clears the data and the input fields.
	 */
	public void clear() {
		setZoom(14);
		setLongitude(null);
		setLatitude(null);
		enableWidgets(false);
	}

	/**
	 * Add a listener to the list of listeners who will be notified of
	 * a change in the selected size.
	 * @param list the listener to add.
	 */
	public void addSelectionListener(LonLatZoomSelectionListener list) {
		vecListeners.add(list);
	}
	
	/**
	 * Notify the registered listeners that the selected date has changed.
	 */
	private void notifySelectionListeners() {
		for (LonLatZoomSelectionListener list : vecListeners) {
			list.onSelectionChange();
		}
	}
	
	/**
	 * Build the input widgets.
	 */
	private void buildWidgets() {
		WidgetsFactory.getInstance().createLabel(this, "Longitude");
		textLongitude = WidgetsFactory.getInstance().createText(this, 10, new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent evt) {
				try {
					dLongitude = Double.valueOf(textLongitude.getText());
					textLongitude.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
					notifySelectionListeners();
				} catch (NumberFormatException exc) {
					dLongitude = null;
					textLongitude.setForeground(getDisplay().getSystemColor(SWT.COLOR_RED));
				}
				enableWidgets(true);
			}
		});

		WidgetsFactory.getInstance().createLabel(this, "Latitude");
		textLatitude = WidgetsFactory.getInstance().createText(this, 10, new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent evt) {
				try {
					dLatitude = Double.valueOf(textLatitude.getText());
					textLatitude.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
					notifySelectionListeners();
				} catch (NumberFormatException exc) {
					dLatitude = null;
					textLatitude.setForeground(getDisplay().getSystemColor(SWT.COLOR_RED));
				}
				enableWidgets(true);
			}
		});

		WidgetsFactory.getInstance().createLabel(this, "Zoom");
		spiZoom = WidgetsFactory.getInstance().createSpinner(this, 1, 19, 1, new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				notifySelectionListeners();
			}
		});
		
		btnParse = WidgetsFactory.getInstance().createPushButton(this, "Lire URL", "edit", "Lire depuis une URL OpenStreetMap", 
				new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				parseOpenStreetMapURL();
			}
		});
		
		btnPreview = WidgetsFactory.getInstance().createPushButton(this, "Aperçu", "internet", "Aperçu dans OpenStreetMap", 
				new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				navigateToOpenStreetMap();
			}
		});
	}
	
	private void parseOpenStreetMapURL() {
		String sMsg = "Entrez une URL OpenStreetMap : \n" +
				"Par exemple,\n" +
				"https://www.openstreetmap.org/#map=17/46.31720/6.97094\n";
		String sUrl = MessageBox.input(sMsg);
		
		if (sUrl != null && sUrl.contains("=")) {
			String[] sCoords = sUrl.split("=")[1].split("/");
			if (sCoords.length == 3) {
				try {
					setLongitude(Double.valueOf(sCoords[2]));
					setLatitude(Double.valueOf(sCoords[1]));
					setZoom(Integer.valueOf(sCoords[0]));
				} catch (NumberFormatException exc) {
					MessageBox.error("URL invalide : " + exc.getMessage());
				}
			}
		}
	}
	
	private void navigateToOpenStreetMap() {
		if (dLongitude != null && dLatitude != null) {
			String url = String.format("https://www.openstreetmap.org/#map=%d/%f/%f", 
					iZoom, dLatitude.doubleValue(), dLongitude.doubleValue());
			try {
				Runtime.getRuntime().exec(new String[] {"google-chrome-stable", url});
			} catch (IOException e) {
				MessageBox.error(e);
			}
		} else {
			MessageBox.error("Veuillez entrer des coordonnées valides !");
		}
	}
	
	private void enableWidgets(boolean isEnabled) {
		boolean isValidCoords = (dLongitude != null && dLatitude != null);
		btnPreview.setEnabled(isEnabled && isValidCoords);
		btnParse.setEnabled(isEnabled);
	}

}
