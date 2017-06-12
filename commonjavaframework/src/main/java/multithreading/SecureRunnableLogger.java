package multithreading;

import logging.AbstractLogger;

/**
 * Logger for secure thread execution.
 * 
 * @author roland
 *
 */
public class SecureRunnableLogger extends AbstractLogger {

	private static SecureRunnableLogger instance;
	
	/**
	 * Creates a new logger with the logging directory
	 * <working_directory>/logging/secure_thread.
	 */
	public SecureRunnableLogger() {

		super("secure_thread");
	}
	
	/**
	 * Creates a static instance of this class.
	 * 
	 * @return The static instance.
	 */
	public static SecureRunnableLogger getInstance() {
		if(instance == null) {
			instance = new SecureRunnableLogger();
		}
		return instance;
	}
}
