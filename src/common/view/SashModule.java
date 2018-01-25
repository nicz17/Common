package common.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;

/**
 * A Composite with two child composites separated by a vertical sash,
 * which allows to resize them dynamically.
 * 
 * <p>This composite should be placed into a GridLayout,
 * although it uses a FormLayout for the sash.
 * 
 * <p>The two child composites use GridLayouts.
 * 
 * @author nicz
 *
 */
public abstract class SashModule extends BaseModule {
	
	/** The composite to the left of the sash. */
	protected final Composite cLeft;
	
	/** The composite to the right of the sash. */
	protected final Composite cRight;

	/**
	 * Constructor.
	 * Creates the two child composites and their vertical sash.
	 * 
	 * @param parent the parent composite.
	 * @param leftWidth the default width of the left composite.
	 */
	public SashModule(Composite parent, int leftWidth) {
		super(parent);
		
		this.setLayout(new FormLayout());
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		cLeft = widgetsFactory.createComposite(this);
		final Sash sash = new Sash(this, SWT.VERTICAL);
		cRight = widgetsFactory.createComposite(this);
		
		FormData leftData = new FormData();
		leftData.left   = new FormAttachment(0, 0);
		leftData.right  = new FormAttachment(sash, 0);
		leftData.top    = new FormAttachment(0, 0);
		leftData.bottom = new FormAttachment(100, 0);
		cLeft.setLayoutData(leftData);
		
		final int limit = 100;
		final FormData sashData = new FormData();
		sashData.left   = new FormAttachment(sash, leftWidth);
		sashData.top    = new FormAttachment(0, 0);
		sashData.bottom = new FormAttachment(100, 0);
		sash.setLayoutData (sashData);
		sash.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				Rectangle sashRect = sash.getBounds ();
				Rectangle shellRect = SashModule.this.getClientArea();
				int right = shellRect.width - sashRect.width - limit;
				e.x = Math.max(Math.min(e.x, right), limit);
				if (e.x != sashRect.x)  {
					sashData.left = new FormAttachment (0, e.x);
					SashModule.this.layout();
				}
			}
		});
		
		FormData rightData = new FormData();
		rightData.left   = new FormAttachment(sash, 0);
		rightData.right  = new FormAttachment(100, 0);
		rightData.top    = new FormAttachment(0, 0);
		rightData.bottom = new FormAttachment(100, 0);
		cRight.setLayoutData(rightData);
		
		loadWidgets();
		loadData();
	}
}
