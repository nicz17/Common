package common.exceptions;

/**
 * A standard application exception.
 * 
 * @author nicz
 *
 */
public class AppException extends Exception {

	private static final long serialVersionUID = -6783864036862853144L;

	public AppException() {
	}

	public AppException(String message) {
		super(message);
	}

	public AppException(Throwable cause) {
		super(cause);
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

}
