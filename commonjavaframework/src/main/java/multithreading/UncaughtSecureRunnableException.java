package multithreading;

/**
 * This class can be used in a {@link SecureRunnable}. If an instance of this
 * exception is thrown in a {@link SecureRunnable} it won't be caught. So the
 * runnable won't further be executed.
 * 
 * @author roland
 *
 */
public class UncaughtSecureRunnableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -97091243366300731L;

	/**
	 * Creates a new instance of this class.
	 */
	public UncaughtSecureRunnableException() {
		super();
	}

	/**
	 * Creates a new instance of this class with the given message.
	 * 
	 * @param message
	 *            The message to wrap inside the exception.
	 */
	public UncaughtSecureRunnableException(String message) {
		super(message);
	}

	/**
	 * Creates a new instance of this class with the given throwable.
	 * 
	 * @param trw
	 *            The throwable/exception to wrap inside the exception.
	 */
	public UncaughtSecureRunnableException(Throwable trw) {
		super(trw);
	}

	/**
	 * Creates a new instance of this class with the given message and
	 * throwable.
	 * 
	 * @param message
	 *            The message to wrap inside the exception.
	 * @param trw
	 *            The throwable/exception to wrap inside the exception.
	 */
	public UncaughtSecureRunnableException(String message, Throwable trw) {
		super(message, trw);
	}
}
