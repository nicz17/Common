package common.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Simple class to display modal message boxes.
 * 
 * <p>Handles the following dialogs :
 * <ul>
 * <li>info, warning, error messages</li>
 * <li>text and password input dialog</li>
 * <li>yes/no confirmation dialog</li>
 * <li>calendar date selection dialog</li>
 * </ul>
 * 
 * @author nicz
 *
 */
public class MessageBox {
	
	/** Shortcut to the {@link WidgetsFactory}, for convenience */
	protected static final WidgetsFactory widgetsFactory =
		WidgetsFactory.getInstance();
	
	private static Shell parent;
	private static String input;
	private static boolean answer;

	private static String appName = "MessageBox";
	
	/**
	 * Initialize the MessageBox with the parent shell and
	 * the application name.
	 * 
	 * @param parent the parent shell
	 * @param appName the application name (sometimes displayed in titles)
	 */
	public static void init(Shell parent, String appName) {
		MessageBox.parent = parent;
		MessageBox.appName = appName;
	}
	
	/**
	 * Displays a message box with an info message
	 * and the system info icon.
	 * 
	 * @param msg the message to display
	 */
	public static void info(String msg) {
		info("Info", msg);
	}
	
	/**
	 * Displays a message box with an info message,
	 * a title, and the system info icon.
	 * 
	 * @param title the dialog title
	 * @param msg the message to display
	 */
	public static void info(String title, String msg) {
		open(msg, title, SWT.ICON_INFORMATION);
	}
	
	/**
	 * Displays a message box with an error message
	 * and the system error icon.
	 * 
	 * @param msg the message to display
	 */
	public static void error(String msg) {
		open(msg, "erreur", SWT.ICON_ERROR);
	}
	
	/**
	 * Displays a message box with an error message
	 * and the system error icon.
	 * 
	 * @param exc the exception to display as error
	 */
	public static void error(Exception exc) {
		error(exc.getMessage());
	}
	
	/**
	 * Displays a message box with a warning message
	 * and the system warning icon.
	 * 
	 * @param msg the message to display
	 */
	public static void warning(String msg) {
		open(msg, "attention", SWT.ICON_WARNING);
	}
	
	/**
	 * Displays a dialog asking the user for text input,
	 * with a preset input text.
	 * 
	 * @param msg the message describing the expected input
	 * @param text the preset input text
	 * @return the user input (null if user canceled)
	 */
	public static String input(String msg, String text) {
		return input(msg, false, text);
	}
	
	/**
	 * Displays a dialog asking the user for text input.
	 * 
	 * @param msg the message describing the expected input
	 * @return the user input (null if user canceled)
	 */
	public static String input(String msg) {
		return input(msg, false, null);
	}
	
	/**
	 * Displays a dialog asking the user for a password.
	 * The user input will be replaced by echo chars.
	 * 
	 * @param msg the message describing the expected password
	 * @return the user input (null if user canceled)
	 */
	public static String password(String msg) {
		return input(msg, true, null);
	}
	
	/**
	 * Displays a dialog asking the user for a yes/no response.
	 * 
	 * @param msg the question asked to user
	 * @return the user response
	 */
	public static boolean askYesNo(String msg) {
		Display display = parent.getDisplay();
		final Shell shell =
			new Shell(parent, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setText(appName);
		shell.setLayout(new GridLayout(3, false));
		
	    Label lblImg = new Label(shell, SWT.NONE);
	    lblImg.setImage(display.getSystemImage(SWT.ICON_QUESTION));
	    lblImg.setLayoutData(new GridData());
		
		Label lblMsg = new Label(shell, SWT.WRAP);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		//data.horizontalIndent = 16;
		data.verticalIndent = 16;
		data.horizontalSpan = 2;
	    lblMsg.setLayoutData(data);
		lblMsg.setText(msg);
		
		widgetsFactory.createHorizontalSeparator(shell, 3);
		
		new Label(shell, 0);

		widgetsFactory.createPushButton(
				shell, "Oui", "ok", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				answer = true;
				shell.dispose();
			}
		});

		widgetsFactory.createPushButton(
				shell, "Non", "cancel", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				answer = false;
				shell.dispose();
			}
		});

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		return answer;
	}
	
	/**
	 * Displays a calendar dialog asking the user to select a date.
	 * 
	 * @return the selected date in 'yyyy-mm-dd' format, 
	 * 		or null if user canceled.
	 */
	public static String calendar() {
		Display display = parent.getDisplay();
		input = null;
		final Shell shell =
			new Shell(parent, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setText("Choisissez une date");
		shell.setLayout(new GridLayout(2, true));
		GridData data;
		
		final DateTime calendar = new DateTime(shell, SWT.CALENDAR | SWT.BORDER);
		data = new GridData();
		data.horizontalSpan = 2;
		calendar.setLayoutData(data);

		final Button btnOk = widgetsFactory.createOkButton(shell,
				new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				input = String.format("%d-%02d-%02d", calendar.getYear(), 
						calendar.getMonth() + 1, calendar.getDay() );
				shell.dispose();
			}
		});
	    data = new GridData(GridData.FILL_HORIZONTAL);
		data.verticalIndent = 16;
	    btnOk.setLayoutData(data);

		final Button btnCancel = widgetsFactory.createCancelButton(shell,
				new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
	    data = new GridData(GridData.FILL_HORIZONTAL);
		data.verticalIndent = 16;
	    btnCancel.setLayoutData(data);

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return input;
	}

	/**
	 * Opens a dialog with a message, title, system icon, and OK button.
	 * 
	 * @param msg the message to display
	 * @param title the dialog title
	 * @param iconId the system icon id
	 */
	private static void open(String msg, String title, int iconId) {
		Display display = parent.getDisplay();
		final Shell shell =
			new Shell(parent, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setText(appName + " : " + title); 
		shell.setLayout(new GridLayout(2, false));
		GridData data;
		
	    Label lblImg = new Label(shell, SWT.NONE);
	    lblImg.setImage(display.getSystemImage(iconId));
		data = new GridData();
	    lblImg.setLayoutData(data);
		
		Label label = new Label(shell, SWT.WRAP);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = 16;
		data.verticalIndent = 16;
	    label.setLayoutData(data);
		label.setText(msg);

		final Button btnOk = widgetsFactory.createOkButton(shell,
				new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
	    data = new GridData();
	    data.horizontalSpan = 2;
	    data.horizontalAlignment = SWT.RIGHT;
	    data.widthHint = 120;
		data.verticalIndent = 16;
	    btnOk.setLayoutData(data);

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
	}
	
	/**
	 * Opens an input dialog with a message and OK/cancel buttons.
	 * 
	 * @param msg the message to display
	 * @param passwdMode if true, replace user input by echo chars
	 * @return the user input, or null if user canceled.
	 */
	private static String input(String msg, boolean passwdMode, String text) {
		Display display = parent.getDisplay();
		final Shell shell =
			new Shell(parent, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setText(appName);
		shell.setLayout(new GridLayout(3, true));

		Label lblMsg = new Label(shell, SWT.NULL);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
	    data.horizontalSpan = 3;
	    lblMsg.setLayoutData(data);
		lblMsg.setText(msg);
		
		final Text txtInput = new Text(shell, SWT.SINGLE | SWT.BORDER);
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    data.horizontalSpan = 3;
	    txtInput.setLayoutData(data);
	    if (passwdMode) 
	    	txtInput.setEchoChar('*');
	    if (text != null)
	    	txtInput.setText(text);

	    Label lblImg = new Label(shell, SWT.NONE);
	    if (passwdMode) 
	    	lblImg.setImage(IconManager.getIcon("locked32"));
	    else lblImg.setImage(display.getSystemImage(SWT.ICON_QUESTION));
	    
	    final Button btnOk = widgetsFactory.createOkButton(shell, new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				input = txtInput.getText();
				shell.dispose();
			}
		});
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    data.widthHint = 160;
	    btnOk.setLayoutData(data);

	    // cancel button
	    widgetsFactory.createCancelButton(shell, new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				input = null;
				shell.dispose();
			}
		});

	    txtInput.addListener(SWT.DefaultSelection, new Listener() {
	    	public void handleEvent(Event e) {
	    		btnOk.setFocus();
	    		btnOk.notifyListeners(SWT.Selection, new Event());
	    	}
	    });

		shell.addListener(SWT.Traverse, new Listener() {
			public void handleEvent(Event event) {
				if(event.detail == SWT.TRAVERSE_ESCAPE)
					event.doit = false;
			}
		});

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		return input;
	}

}
