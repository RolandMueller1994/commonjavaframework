package logging;

/**
 * This logger is used to log all common messages. This messages are all
 * messages which can't me assigned to special function. You can use this logger
 * to log messages if you don't want to create a separate logger for your
 * function.
 * 
 * @author roland
 *
 */
public class CommonLogger extends AbstractLogger {

	private static CommonLogger instance;

	/**
	 * Constructs a new CommonLogger. Files will be logged in a directory called
	 * 'common'.
	 */
	private CommonLogger() {
		super("common");
	}

	/**
	 * Creates and returns a singleton instance of this logger.
	 * 
	 * @return The singleton instance of this class.
	 */
	public static CommonLogger getInstance() {
		if (instance == null) {
			instance = new CommonLogger();
		}
		return instance;
	}

}
