package common.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;


/**
 * Factory to create SWT widgets.
 * 
 * <p>Handles SWT Composite, Group, Button, ToolBar, 
 * Label, Text and Combo.</p>
 * 
 * @author nicz
 *
 */
public class WidgetsFactory {
	
	/** the singleton instance */
	private static WidgetsFactory _instance = null;
	
	/** Private singleton constructor */
	private WidgetsFactory() {
	}
	
	/** Get the singleton WidgetsFactory instance. */
	public static WidgetsFactory getInstance() {
		if (_instance == null)
			_instance = new WidgetsFactory();
		return _instance;
	}
	
	
	/*
	 * COMPOSITES
	 */
	
	/**
	 * Create a new SWT Composite with a single column
	 * and horizontal fill.
	 * 
	 * @param parent the parent composite.
	 * @return the created composite.
	 */
	public Composite createComposite(Composite parent) {
		return createComposite(parent, 1, true, 0);
	}
	
	
	/**
	 * Creates a new top-aligned SWT Composite with a single column.
	 * 
	 * @param parent the parent composite.
	 * @param fill true to fill horizontally
	 * @return the created composite.
	 */
	public Composite createCompositeTop(Composite parent, boolean fill) {
		Composite composite = new Composite(parent, 0);
		composite.setLayout(new GridLayout());
		GridData data;
		if (fill) {
			data = new GridData(GridData.FILL_HORIZONTAL);
		} else {
			data = new GridData();
		}
		data.verticalAlignment = SWT.TOP;
	    composite.setLayoutData(data);
		return composite;
	}
	
	
	/**
	 * Create a new SWT Composite with the given number of columns
	 * and horizontal fill.
	 * 
	 * @param parent the parent composite.
	 * @param nColumns the number of columns in the composite
	 * @param equalWidth if true, columns all have the same width
	 * @param verticalIndent the indentation on the top of the composite
	 * @return the created composite.
	 */
	public Composite createComposite(Composite parent, int nColumns, 
			boolean equalWidth, int verticalIndent) {
		return createComposite(parent, nColumns, equalWidth, verticalIndent, true);
	}
	
	public Composite createComposite(Composite parent, int nColumns, 
			boolean equalWidth, int verticalIndent, boolean fill) {
			
		Composite composite = new Composite(parent, 0);
		composite.setLayout(new GridLayout(nColumns, equalWidth));
		GridData data;
		if (fill) {
			data = new GridData(GridData.FILL_HORIZONTAL);
		} else {
			data = new GridData();
		}
	    data.verticalIndent = verticalIndent;
	    composite.setLayoutData(data);

		return composite;
	}
	
	
	/*
	 * GROUPS
	 */
	
	/**
	 * Create a new SWT Group with a standard GridLayout
	 * and horizontal fill.
	 * 
	 * @param parent the parent composite.
	 * @param label the group's title.
	 * @return the created group.
	 */
	public Group createGroup(Composite parent, String label) {
		return createGroup(parent, label, 0, true);
	}

	/**
	 * Create a new SWT Group with the given number of columns
	 * and horizontal fill.
	 * 
	 * @param parent the parent composite.
	 * @param label the group's title.
	 * @param nCols the number of columns in the grid
	 * @param equalWidth if true, columns all have the same width
	 * @return the created group.
	 */
	public Group createGroup(Composite parent, String label, int nCols, 
			boolean equalWidth) {
		Group group = new Group(parent, 0);
		group.setText(" " + label + " ");
		if (nCols > 0) {
			group.setLayout(new GridLayout(nCols, equalWidth));
		} else {
			group.setLayout(new GridLayout());
		}
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return group;
	}
	
	
	/*
	 *  BUTTONS
	 */
	
	/**
	 * Create a new OK button.
	 * 
	 * @param parent the parent composite.
	 * @param listener the selection listener
	 * @return the created button
	 */
	public Button createOkButton(Composite parent, SelectionListener listener) {
		return createPushButton(parent, "OK", "ok", listener);
	}
	
	/**
	 * Create a new Cancel button.
	 * 
	 * @param parent the parent composite.
	 * @param listener the selection listener
	 * @return the created button
	 */
	public Button createCancelButton(Composite parent, SelectionListener listener) {
		return createPushButton(parent, "Annuler", "cancel", listener);
	}
	
	/**
	 * Create a new Close button.
	 * 
	 * @param parent the parent composite.
	 * @param listener the selection listener
	 * @return the created button
	 */
	public Button createCloseButton(Composite parent, SelectionListener listener) {
		return createPushButton(parent, "Fermer", "cancel", listener);
	}
	
	/**
	 * Create a new Save button.
	 * 
	 * @param parent the parent composite.
	 * @param listener the selection listener
	 * @return the created button
	 */
	public Button createSaveButton(Composite parent, SelectionListener listener) {
		return createPushButton(parent, "Sauver", "filesave", "Enregistrer", listener);
	}
	
	/**
	 * Create a new Delete button.
	 * 
	 * @param parent the parent composite.
	 * @param listener the selection listener
	 * @return the created button
	 */
	public Button createDeleteButton(Composite parent, SelectionListener listener) {
		return createPushButton(parent, "Effacer", "delete", listener);
	}
	
	/**
	 * Create a new SWT push button, with default horizontal fill.
	 * 
	 * @param parent the parent composite.
	 * @param label the button's text. May be null.
	 * @param icon the icon to display in button. May be null.
	 * @param listener the selection listener
	 * @return the created button
	 */
	public Button createPushButton(Composite parent, String label, 
			String icon, SelectionListener listener) {
		return createPushButton(parent, label, icon, null, listener);
	}
	
	/**
	 * Create a new SWT push button, with default horizontal fill.
	 * 
	 * @param parent the parent composite.
	 * @param label the button's text. May be null.
	 * @param icon the icon to display in button. May be null.
	 * @param tooltipText the text to display in button tooltip. May be null.
	 * @param listener the selection listener
	 * @return the created button
	 */
	public Button createPushButton(Composite parent, String label, String icon,
			String tooltipText, SelectionListener listener) {
		return createPushButton(parent, label, icon, tooltipText, true, listener);
	}
	
	/**
	 * Create a new SWT push button.
	 * 
	 * @param parent the parent composite.
	 * @param label the button's text. May be null.
	 * @param icon the icon to display in button. May be null.
	 * @param tooltipText the text to display in button tooltip. May be null.
	 * @param fill if true, button fills grid cell horizontally
	 * @param listener the selection listener. May be null.
	 * @return the created button
	 */
	public Button createPushButton(Composite parent, String label, String icon,
			String tooltipText, boolean fill, SelectionListener listener) {
		Button button = new Button(parent, SWT.PUSH);
		if (label != null)
			button.setText(label);
		if (icon != null)
			button.setImage(IconManager.getIcon(icon));
		button.setToolTipText(tooltipText);
		
		GridData gridData = new GridData();
		if (fill)
			gridData = new GridData(GridData.FILL_HORIZONTAL);
		button.setLayoutData(gridData);
		
		if (listener != null)
			button.addSelectionListener(listener);
		
		return button;
	}
	
	/**
	 * Creates a new SWT toggle button with given icon and tooltip text.
	 * 
	 * @param parent the parent composite.
	 * @param icon the icon to display in button. May be null.
	 * @param tooltipText the text to display in button tooltip. May be null.
	 * @param listener the selection listener. May be null.
	 * @return the created toggle button
	 */
	public Button createToggleButton(Composite parent, String icon,
			String tooltipText, SelectionListener listener) {
		Button toggle = new Button(parent, SWT.TOGGLE);
		toggle.setToolTipText(tooltipText);
		toggle.setImage(IconManager.getIcon(icon));
		if (listener != null)
			toggle.addSelectionListener(listener);
		return toggle;
	}
	
	public Link createLink(Composite parent, String text,
			String tooltipText, SelectionListener listener) {
		Link link = new Link(parent, SWT.CENTER);
		link.setText(text);
		link.setToolTipText(tooltipText);
		if (listener != null)
			link.addSelectionListener(listener);
		return link;
	}
	
	/**
	 * Creates a new SWT Button with check-box behavior.
	 * 
	 * @param parent  the parent composite
	 * @param label   the check-box text
	 * @param checked whether the box is initially checked
	 * @return the created button
	 */
	public Button createCheckBox(Composite parent, String label, boolean checked) {
		Button check = new Button(parent, SWT.CHECK);
	    check.setText(label);
	    check.setSelection(checked);
	    return check;
	}
	
	/**
	 * Creates a new SWT Button with radio behavior.
	 * 
	 * @param parent   the parent composite
	 * @param label    the radio button text
	 * @return the created radio button
	 */
	public Button createRadioButton(Composite parent, String label) {
		return createRadioButton(parent, label, null);
	}
	
	/**
	 * Creates a new SWT Button with radio behavior.
	 * 
	 * @param parent   the parent composite
	 * @param label    the radio button text
	 * @param listener the selection listener. May be null.
	 * @return the created radio button
	 */
	public Button createRadioButton(Composite parent, String label, 
			SelectionListener listener) {
		Button radio = new Button(parent, SWT.RADIO);
	    radio.setText(label);
	    if (listener != null)
	    	radio.addSelectionListener(listener);
	    return radio;
	}
	
	/*
	 * TOOLBAR
	 */
	
	/**
	 * Creates a new SWT {@link ToolBar} without horizontal fill.
	 * 
	 * @param parent the parent composite.
	 * @return the created Toolbar.
	 */
	public ToolBar createToolbar(Composite parent) {
		return createToolbar(parent, false);
	}
	
	/**
	 * Creates a new SWT {@link ToolBar} with specified horizontal fill.
	 * 
	 * @param parent the parent composite.
	 * @param fill true to fill horizontally.
	 * @return the created Toolbar.
	 */
	public ToolBar createToolbar(Composite parent, boolean fill) {
		ToolBar toolBar = new ToolBar(parent, SWT.BORDER);
		if (fill) {
			toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		} else {
			toolBar.setLayoutData(new GridData());
		}
		return toolBar;
	}
	
	/**
	 * Creates a new SWT vertical {@link ToolBar} with top alignment.
	 * 
	 * @param parent the parent composite.
	 * @return the created Toolbar.
	 */
	public ToolBar createVerticalToolbar(Composite parent) {
		ToolBar toolBar = new ToolBar(parent, SWT.FLAT | SWT.VERTICAL);
		GridData data = new GridData();
		data.verticalAlignment = SWT.TOP;
		toolBar.setLayoutData(data);
		return toolBar;
	}

	/**
	 * Creates a new Toolbar push button for the given toolbar.
	 * 
	 * @param toolBar the parent toolbar
	 * @param icon the button icon.
	 * @param tooltip the button tooltip text. May be null.
	 * @param listener the selection listener (not null).
	 * @return the created toolbar item
	 */
	public ToolItem createToolItem(ToolBar toolBar, String icon,
			String tooltip, SelectionListener listener) {
		return createToolItem(toolBar, icon, null, tooltip, listener);
	}

	/**
	 * Creates a new Toolbar push button for the given toolbar.
	 * 
	 * @param toolBar the parent toolbar
	 * @param icon the button icon.
	 * @param label the button text. May be null.
	 * @param tooltip the button tooltip text. May be null.
	 * @param listener the selection listener (not null).
	 * @return the created toolbar item
	 */
	public ToolItem createToolItem(ToolBar toolBar, String icon,
			String label, String tooltip, SelectionListener listener) {
		ToolItem item = new ToolItem(toolBar, SWT.PUSH);
		item.setImage(IconManager.getIcon(icon));
		if (label != null)
			item.setText(label);
		item.setToolTipText(tooltip);
		item.addSelectionListener(listener);
		return item;
	}
	
	public void createToolbarSeparator(ToolBar toolBar) {
		new ToolItem(toolBar, SWT.SEPARATOR);
	}
	
	/*
	 * LABELS
	 */
	
	/**
	 * Creates a new empty SWT Label with default horizontal fill.
	 * 
	 * @param parent the parent composite.
	 * @return the created label
	 */
	public Label createLabel(Composite parent) {
		Label label = new Label(parent, 0);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return label;
	}
	
	/**
	 * Create a new SWT Label with the given text and no fill.
	 * 
	 * @param parent the parent composite.
	 * @param text the label's text
	 * @return the created label
	 */
	public Label createLabel(Composite parent, String text) {
		return createLabel(parent, text, false);
	}
	
	/**
	 * Creates a new SWT {@link Label} with the given text and no fill.
	 * 
	 * @param parent the parent composite.
	 * @param text the label's text
	 * @param alignTop if true, set vertical alignment to top
	 * @return the created label
	 */
	public Label createLabel(Composite parent, String text, boolean alignTop) {
		Label label = new Label(parent, 0);
		label.setText(text);
		if (alignTop) {
			GridData data = new GridData();
			data.verticalAlignment = SWT.TOP;
			label.setLayoutData(data);
		}
		return label;
	}
	
	/**
	 * Creates a new SWT {@link Label} with the given width and no text.
	 * 
	 * @param parent the parent composite.
	 * @param width the preferred width in pixels
	 * @return the created label
	 */
	public Label createLabel(Composite parent, int width) {
		Label label = new Label(parent, 0);
	    GridData data = new GridData();
	    data.widthHint = width;
	    label.setLayoutData(data);
	    return label;
	}
	
	/**
	 * Creates a new SWT {@link Label} with multiple line wrap
	 * and given height.
	 * 
	 * @param parent the parent composite.
	 * @param height the preferred height in pixels
	 * @return the created label
	 */
	public Label createMultilineLabel(Composite parent, int height) {
		Label label = new Label(parent, SWT.WRAP);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = height;
		label.setLayoutData(data);
		return label;
	}
	
	
	/**
	 * Creates a new SWT centered {@link Label} with given height and width,
	 * which may be used to display images.
	 * 
	 * @param parent the parent composite.
	 * @param height the preferred height in pixels
	 * @param width  the preferred width in pixels
	 * @return the created label
	 */
	public Label createPictureLabel(Composite parent, int height, int width) {
		Label label = new Label(parent, SWT.CENTER);
		GridData data = new GridData();
		data.widthHint  = width;
		data.heightHint = height;
		data.horizontalAlignment = SWT.CENTER;
		data.verticalAlignment   = SWT.CENTER;
		label.setLayoutData(data);
		return label;
	}
	
	/**
	 * Creates a new SWT label displaying an icon image.
	 * 
	 * @param parent  the parent composite
	 * @param icon    the name of the icon file
	 * @param tooltip the icon tooltip (may be null)
	 * @return the created label
	 */
	public Label createIconLabel(Composite parent, String icon, String tooltip) {
		Label label = new Label(parent, 0);
		label.setImage(IconManager.getIcon(icon));
		if (tooltip != null) {
			label.setToolTipText(tooltip);
		}
		return label;
	}
	
	/**
	 * Creates a new SWT {@link Label} with horizontal separator behavior.
	 * 
	 * @param parent the parent composite
	 * @param horizontalSpan the number of cells spanned horizontally
	 */
	public void createHorizontalSeparator(Composite parent, int horizontalSpan) {
    	Label lblSep = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
    	GridData data = new GridData(GridData.FILL_HORIZONTAL);
    	data.horizontalSpan = horizontalSpan;
    	lblSep.setLayoutData(data);
	}
	
	/**
	 * Creates a new SWT {@link Label} with vertical separator behavior.
	 * 
	 * @param parent the parent composite
	 */
	public void createVerticalSeparator(Composite parent) {
		Label lblSep = new Label(parent, SWT.SEPARATOR | SWT.VERTICAL);
		GridData data = new GridData();
		data.heightHint = 22;
		lblSep.setLayoutData(data);
	}

	
	/*
	 * TEXT
	 */
	
	/**
	 * Create a new SWT {@link Text} with horizontal fill.
	 * 
	 * @param parent the parent composite
	 * @return the created text widget.
	 */
	public Text createText(Composite parent) {
		return createText(parent, 0, null);
	}
	
	/**
	 * Create a new SWT {@link Text} with a limit and a modification listener.
	 * 
	 * @param parent the parent composite
	 * @param textLimit the text maximum length in characters (applies if > 0)
	 * @param listener the listener to notify when the text is modified (may be null)
	 * @return the created text widget.
	 */
	public Text createText(Composite parent, int textLimit, 
			ModifyListener listener) {
		
		Text text = new Text(parent, SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		if (textLimit > 0)
			text.setTextLimit(textLimit);
		if (listener != null)
			text.addModifyListener(listener);
		return text;
	}
	
	/**
	 * Creates a new SWT multi-line {@link Text} with text wrap, 
	 * a text size limit, preferred height and a modification listener.
	 * 
	 * @param parent the parent composite
	 * @param textLimit the text maximum length in characters (applies if > 0)
	 * @param height the desired height in pixels
	 * @param listener the listener to notify when the text is modified (may be null)
	 * @return the created text widget.
	 */
	public Text createMultilineText(Composite parent, int textLimit, 
			int height, ModifyListener listener) {
		
		Text text = new Text(parent, SWT.WRAP | SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = height;
		data.widthHint = 400;
		text.setLayoutData(data);
		
		if (textLimit > 0)
			text.setTextLimit(textLimit);
		if (listener != null)
			text.addModifyListener(listener);
		return text;
	}
	
	
	/*
	 * COMBO (drop-down list)
	 */
	
	/**
	 * Creates a new SWT {@link Combo} in read-only mode, with horizontal fill.
	 * 
	 * <p>The combo is returned empty.
	 * Read-only mode means the user cannot edit the selected item.</p>
	 * 
	 * @param parent the parent composite
	 * @param listener the listener to notify when the selection is modified (may be null)
	 * @return the created combo.
	 */
	public Combo createCombo(Composite parent, ModifyListener listener) {
		return createCombo(parent, -1, listener);
	}
	
	/**
	 * Creates a new SWT {@link Combo} in read-only mode, with given width.
	 * 
	 * <p>The combo is returned empty.
	 * Read-only mode means the user cannot edit the selected item.</p>
	 * 
	 * @param parent the parent composite
	 * @param width the desired with in pixels (-1 for horizontal fill)
	 * @param listener the listener to notify when the selection is modified (may be null)
	 * @return the created combo.
	 */
	public Combo createCombo(Composite parent, int width, ModifyListener listener) {
		return createCombo(parent, width, true, listener);
	}
	
	/**
	 * Creates a new SWT {@link Combo} in read-only mode, with given width.
	 * 
	 * <p>The combo is returned empty.
	 * 
	 * @param parent the parent composite
	 * @param width the desired with in pixels (-1 for horizontal fill)
	 * @param readonly true if user cannot edit the selected item
	 * @param listener the listener to notify when the selection is modified (may be null)
	 * @return the created combo.
	 */
	public Combo createCombo(Composite parent, int width, boolean readOnly, 
			ModifyListener listener) {
		int style = SWT.BORDER;
		if (readOnly) {
			style = SWT.BORDER | SWT.READ_ONLY;
		}
		Combo combo = new Combo(parent, style);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		if (width > 0) {
			gd = new GridData();
			gd.widthHint = width;
		}
		combo.setLayoutData(gd);
		if (listener != null)
			combo.addModifyListener(listener);
		return combo;
	}
	
	/**
	 * Creates a new SWT {@link Spinner}.
	 * 
	 * @param parent    the parent composite
	 * @param max       the maximum spinner value
	 * @param increment the spinner increment (at least 1)
	 * @param listener  the listener to notify when the selection is modified (may be null)
	 * @return
	 */
	public Spinner createSpinner(Composite parent, int min, int max, int increment, ModifyListener listener) {
		if (increment < 1) increment = 1;
		Spinner spinner = new Spinner(parent, SWT.BORDER);
		spinner.setMinimum(min);
		spinner.setMaximum(max);
		spinner.setIncrement(increment);
		if (listener != null)
			spinner.addModifyListener(listener);
		return spinner;
	}
	
	public List createList(Composite parent, int width, int height) {
		List list = new List(parent, SWT.SINGLE | SWT.V_SCROLL);
		GridData data = new GridData();
		data.heightHint = height;
		data.widthHint  = width;
		list.setLayoutData(data);
		return list;
	}

}
