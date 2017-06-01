package logging;

import java.io.File;

/**
 * This abstract class implements a framework to print messages into a logfile.
 * The implementation of this class must specify the name of the logfile.
 * 
 * @author roland
 *
 */
public abstract class AbstractLogger {

	private File logfile;
	private String name;

	/**
	 * Constructs a logger for the logfile "name_date.log".
	 * 
	 * @param name
	 *            The name of the logfile.
	 */
	public AbstractLogger(String name) {
		this.name = name;
	}
	
	private File getLogfile() {
		
		return logfile;
	}

	/**
	 * Prints the message to the logfile.
	 * 
	 * @param message
	 *            The message to log.
	 */
	public void logMessage(String message) {

	}

	/**
	 * Prints the stacktrace of the given exception to the logfile.
	 * 
	 * @param ex
	 *            The exception to print.
	 */
	public void printStackTrace(Exception ex) {

	}

	/**
	 * Prints the message and the stacktrace of the given exception to the
	 * logfile.
	 * 
	 * @param message
	 *            The message to print.
	 * @param ex
	 *            The exception to print.
	 */
	public void printMessageAndStackTrace(String message, Exception ex) {

	}

}
