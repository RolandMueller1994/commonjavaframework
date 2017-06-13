package multithreading;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * This class is used in {@link ScheduledThreadPoolExecutor} if the execution
 * shouldn't be stopped after an exception has been thrown. The exception will
 * be logged with the {@link SecureRunnableLogger}.
 * 
 * @author roland
 *
 */
public abstract class SecureRunnable implements Runnable {

	@Override
	public final void run() {
		try {
			runSecure();
		} catch (UncaughtSecureRunnableException ex) {
			SecureRunnableLogger.getInstance().logMessageAndException(
					"Uncaught exception has been thrown. Runnable won't be executed another time!", ex);
			throw ex;
		} catch (Exception ex) {
			SecureRunnableLogger.getInstance().logException(ex);
		}
	}

	/**
	 * Abstract method which must be implemented by subclasses.
	 */
	public abstract void runSecure();

}
