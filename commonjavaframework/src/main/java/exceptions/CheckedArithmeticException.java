package exceptions;
/**
 * Checked Exception Class for arithmetic errors.
 * @author Andre
 *
 */
public class CheckedArithmeticException extends Exception {
	/**
	 * Constructor for checked Exception Class for arithmetic errors. 
	 * @param messageText
	 */
	public CheckedArithmeticException(String messageText) {
		super(messageText);
	}

}
