package resourceframework;

/**
 * This exception will be throw if something went wrong within the
 * {@link GlobalResourceProvider}.
 * 
 * @author roland
 *
 */
public class ResourceProviderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7241263236493188393L;

	/**
	 * Creates a ResourceProviderException without message.
	 */
	public ResourceProviderException() {

	}

	/**
	 * Creates a new ResourceProviderException with the given message.
	 * 
	 * @param message
	 *            {@link String} to set the message.
	 */
	public ResourceProviderException(String message) {
		super(message);
	}

}
