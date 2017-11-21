package resourceframework;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Listener which will be called if the value for a key on which this listener
 * is registered has changed in {@link GlobalResourceProvider}.
 * 
 * @author roland
 *
 */
public interface GlobalResourceChangedListener {

	/**
	 * Will be called if the value for the given key changed.
	 * 
	 * @param key
	 *            the key whose value has changed. Must not be null. Must not be
	 *            null.
	 * @param newValue
	 *            the new value. Must not be null.
	 * @param oldValue
	 *            the old value. Can be null if the key hasn't been registered
	 *            before.
	 */
	void resourceChanged(@Nonnull String key, @Nonnull Object newValue, @Nullable Object oldValue);

}
