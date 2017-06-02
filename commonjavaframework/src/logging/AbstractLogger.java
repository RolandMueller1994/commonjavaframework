package logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;

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
	private String filePath;
	private String subFolder;

	/**
	 * Constructs a logger for the logfile "name_date.log".
	 * 
	 * @param name
	 *            The name of the logfile.
	 */
	public AbstractLogger(String name) {
		this.name = name;
		try {
			filePath = (String) GlobalResourceProvider.getInstance().getResource("loggingPath");
		} catch (ResourceProviderException e) {
			e.printStackTrace();
			filePath = "";
		} finally {
			subFolder = filePath + File.pathSeparator + name;
		}
	}

	private File getLogfile() {

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		/*
		 * Check if logfile is present and if it is the logfile for the current
		 * day.
		 */
		if (logfile != null) {
			if (logfile.getName().substring(logfile.getName().indexOf("_")).equals(df.format(date))) {
				return logfile;
			}
		}

		File directory = new File(subFolder);

		String currentFileName = name + "_" + df.format(date);

		// Check if the path exists and the it is a directory -> Logfile can be
		// search or created in the directory
		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if (name.equals(currentFileName)) {
						return true;
					}
					return false;
				}
			});

			if (files.length != 0) {
				return logfile = files[0];
			}
		}

		// Logfiledirectory doesn't exist, so we create it if the parentdirectory exists.
		File parentDirectory = new File(filePath);

		if (!parentDirectory.exists()) {
			throw new RuntimeException("Logfile parentdirectory doesn't exist!");
		}

		directory.mkdir();
		return logfile = new File(directory.getPath() + File.pathSeparator + currentFileName);
	}

	private void printToFile(String message) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

		String internalMessage = df.format(date) + System.lineSeparator() + message + System.lineSeparator()
				+ System.lineSeparator();

		try {
			PrintWriter writer = new PrintWriter(getLogfile());
			writer.append(internalMessage);
			writer.close();
		} catch (FileNotFoundException e) {
			// Wont happen due to previous checks!
			e.printStackTrace();
		}
	}

	/**
	 * Prints the message to the logfile.
	 * 
	 * @param message
	 *            The message to log.
	 */
	public void logMessage(String message) {
		printToFile(message);
	}

	/**
	 * Prints the stacktrace of the given exception to the logfile.
	 * 
	 * @param ex
	 *            The exception to print.
	 */
	public void printException(Exception ex) {
		String message = "";

		if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
			message = ex.getMessage() + System.lineSeparator();
		}

		message += ex.getStackTrace();
		printToFile(message);
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
	public void printMessageAndException(String message, Exception ex) {

		if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
			message += ex.getMessage() + System.lineSeparator();
		}

		message += ex.getStackTrace();
		printToFile(message);
	}
}
