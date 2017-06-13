package multithreading;

/**
 * This class wrappes a {@link Runnable} into a {@link SecureRunnable}
 * 
 * @author roland
 *
 */
public class SecureRunnableWrapper extends SecureRunnable {

	private Runnable r;

	/**
	 * Creates a new instance of this class with the given {@link Runnable}.
	 * 
	 * @param r
	 *            The {@link Runnable} to wrap.
	 */
	public SecureRunnableWrapper(Runnable r) {
		super();
		this.r = r;
	}

	@Override
	public void runSecure() {

		r.run();
	}

}
