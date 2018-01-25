package common.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import common.listeners.ProgressListener;


/**
 * A progress bar with an info label.
 * 
 * @author nicz
 *
 */
public class ProgressBox extends Composite implements ProgressListener {
	protected Label lblInfo;
	protected ProgressBar progressBar;
	protected int at;

	/**
	 * Creates a progress box.
	 * 
	 * @param parent the parent composite
	 * @param max the number of tasks to perform (may be reset later)
	 */
	public ProgressBox(Composite parent, int max) {
		super(parent, SWT.BORDER);
		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.setVisible(false);
		this.at = 0;
		
		lblInfo = new Label(this, 0);
		lblInfo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		lblInfo.setText("Ready");
		
		progressBar = new ProgressBar(this, SWT.BORDER);
		progressBar.setMaximum(max);
		progressBar.setSelection(at);
		progressBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}
	
	/**
	 * Set the number of finished steps.
	 * @param at the number of finished steps
	 */
	public void setAt(int at) {
		this.at = at;
		progressBar.setSelection(at);
	}
	
	/**
	 * Set the total number of steps in the task
	 * @param max the total number of steps
	 */
	public void setMax(int max) {
		progressBar.setMaximum(max);
	}
	
	/**
	 * Increase the number of finished steps by 1.
	 */
	public void increment() {
		progressBar.setSelection(++at);

		// TEST to avoid display timeout
		getDisplay().readAndDispatch();
	}
	
	/**
	 * Display the given info about the task
	 * @param info the message to display
	 */
	public void setInfo(String info) {
		lblInfo.setText(info);
		lblInfo.update();
	}
	
	/**
	 * Make the progress box visible.
	 */
	public void open() {
		this.setVisible(true);
	}
	
	/**
	 * Reset the progress box and make it invisible.
	 */
	public void close() {
		this.setVisible(false);
		reset();
	}
	
	/**
	 * Reset and clear the progress box.
	 */
	public void reset() {
		this.at = 0;
		progressBar.setSelection(at);
		lblInfo.setText("");
		lblInfo.update();
	}

	
	/*
	 * ProgressListener methods
	 */
	
	@Override
	public void taskStarted(int total) {
		setMax(total);
		setInfo("Initialisation ...");
		open();
	}

	@Override
	public void taskProgress() {
		increment();
	}

	@Override
	public void info(String msg) {
		setInfo(msg);
	}

	@Override
	public void taskFinished() {
		close();
	}
	
}
