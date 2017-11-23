package configfileframework;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Wrapper for a value and its comment for a key in a config file. Used in
 * {@link ConfigFileHandler}.
 * 
 * @author roland
 *
 */
public class ConfigFileValueWrapper {

	private Object value;
	private String comment;

	/**
	 * Creates a new wrapper
	 * 
	 * @param value
	 *            The value which should be written into the config file.
	 * @param comment
	 *            The comment which should be written into the config file.
	 */
	public ConfigFileValueWrapper(@Nonnull Object value, @Nullable String comment) {
		this.value = value;
		this.comment = comment;
	}

	/**
	 * Get the value. 
	 * @return The value as {@link Object}. Must not be null.
	 */
	@Nonnull
	public Object getValue() {
		return value;
	}

	/**
	 * Get the comment.
	 * @return The comment as {@link String}. Can be null if no comment was set.
	 */
	@CheckForNull
	public String getComment() {
		return comment;
	}

}
