package common.view;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * Subclass of {@link ProgressBox} also displaying the estimated time left.
 * 
 * @author nicz
 *
 */
public class ProgressTimeBox extends ProgressBox {
	
	protected Label lblTime;
	protected long  tStart;
	protected int   total;

	public ProgressTimeBox(Composite parent, int max) {
		super(parent, max);
		
		lblTime = new Label(this, 0);
		lblTime.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//lblTime.setText("Estimation du temps restant ...");
		reset();
	}
	
	@Override
	public void taskStarted(int total) {
		this.total = total;
		tStart = System.currentTimeMillis();
		super.taskStarted(total);
	}

	@Override
	public void taskProgress() {
		super.taskProgress();
		
		// estimate time left
		long tAt = System.currentTimeMillis();
		int tDiff = (int)(tAt - tStart);
		if (tDiff > 0 && at > 0) {
			int iTime = (tDiff * total / at) - tDiff;
			lblTime.setText("Temps restant : " + iTime/1000 + " secondes");
		}
	}
	
	@Override
	public void reset() {
		super.reset();
		lblTime.setText("Estimation du temps restant ...");
	}

}
