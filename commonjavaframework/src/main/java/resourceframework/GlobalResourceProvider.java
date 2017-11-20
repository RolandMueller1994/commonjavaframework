package resourceframework;

import java.util.HashMap;
import java.util.HashSet;

import javax.annotation.Nonnull;

/**
 * This class is the global provider for resources. Resources can be registered
 * with a key. The registered key is used to retrieve the resource.
 * 
 * @author roland
 *
 */
public class GlobalResourceProvider {

	private static GlobalResourceProvider instance;

	private static HashMap<String, Object> resources;

	private static HashMap<String, HashSet<GlobalResourceChangedListener>> changeListeners = new HashMap<>();

	/**
	 * This method is used to get a global instance of this class.
	 * 
	 * @return The instance.
	 */
	@Nonnull
	public static synchronized GlobalResourceProvider getInstance() {
		if (instance == null) {
			instance = new GlobalResourceProvider();
		}
		return instance;
	}

	private HashMap<String, Object> getResources() {
		if (resources == null) {
			resources = new HashMap<>();
		}
		return resources;
	}

	/**
	 * This method is used to register a resource.
	 * 
	 * @param key
	 *            {@link String} to identify the resource. A
	 *            {@link ResourceProviderException} will be throw if the key is
	 *            already registered. Must not be {@code null}.
	 * @param value
	 *            {@link Object} which represents the value registered for the
	 *            given key. Must not be {@code null}.
	 * @throws ResourceProviderException
	 *             Exception will be throw if the key is already registed.
	 */
	public synchronized void registerResource(@Nonnull String key, @Nonnull Object value)
			throws ResourceProviderException {

		if (value == null || key == null) {
			throw new NullPointerException();
		}
		
		fireResourceChanged(key, value);

		HashMap<String, Object> resources = getResources();

		if (resources.containsKey(key)) {
			throw new ResourceProviderException("The resource " + key + " has already been registered");
		}

		resources.put(key, value);
	}

	/**
	 * This method is used to change the value for a already registered key.
	 * 
	 * @param key
	 *            {@link String} to identify the resource where the value should
	 *            be changed. A {@link ResourceProviderException} will be throw
	 *            if the key can't be found. Must not be {@code null}.
	 * @param value
	 *            The new value to register. Must not be {@code null}.
	 * @throws ResourceProviderException
	 *             Will be throw if the given key can't be found.
	 */
	public synchronized void changeResource(@Nonnull String key, @Nonnull Object value)
			throws ResourceProviderException {

		if (key == null || value == null) {
			throw new NullPointerException();
		}
		
		fireResourceChanged(key, value);

		HashMap<String, Object> resource = getResources();

		if (!resource.containsKey(key)) {
			throw new ResourceProviderException("The key " + key + " can't be found.");
		}

		resource.put(key, value);
	}

	/**
	 * Checks if the given key has already been registered.
	 * 
	 * @param key
	 *            The key to check. Must not be {@code null}.
	 * @return True if the key has been registered, false if not.
	 */
	public synchronized boolean checkRegistered(@Nonnull String key) {

		if (key == null) {
			throw new NullPointerException();
		}
		return resources.containsKey(key);
	}

	/**
	 * This method is used to get the value for the given key
	 * 
	 * @param key
	 *            The {@link String} to search for. A
	 *            {@link ResourceProviderException} will be thrown if the key
	 *            can't be found. Must not be {@code null}.
	 * @return The value for the given key. Won't be {@code null}
	 * @throws ResourceProviderException
	 *             Will be thrown if the key hasn't been registered.
	 */
	@Nonnull
	public synchronized Object getResource(@Nonnull String key) throws ResourceProviderException {

		if (key == null) {
			throw new NullPointerException();
		}

		HashMap<String, Object> resources = getResources();

		if (!resources.containsKey(key)) {
			throw new ResourceProviderException("The key " + key + " can't be found.");
		}

		return resources.get(key);
	}

	/**
	 * Removes the key and value for the given key.
	 * 
	 * @param key
	 *            The key to be removed. Must not be {@code null}
	 */
	public synchronized void deleteResource(@Nonnull String key) {

		if (key == null) {
			throw new NullPointerException();
		}

		getResources().remove(key);
	}

	/**
	 * Adds a {@link GlobalResourceChangedListener} to the given key. The
	 * listener will be called if the value for the key changed or the key gets
	 * registered.
	 * 
	 * @param listener
	 *            The listener to call. Must not be null.
	 * @param key
	 *            The key on which the listener listens. Must not be null.
	 */
	public void registerResourceChangedListener(@Nonnull GlobalResourceChangedListener listener, @Nonnull String key) {
		
		if(listener == null || key == null) {
			throw new NullPointerException();
		}
		
		if (!changeListeners.containsKey(key)) {
			changeListeners.put(key, new HashSet<>());
		}
		changeListeners.get(key).add(listener);
	}
	
	private void fireResourceChanged(String key, Object newValue) {
		
		if(changeListeners.containsKey(key)) {
			Object oldValue = getResources().get(key);
			for(GlobalResourceChangedListener listener : changeListeners.get(key)) {
				listener.resourceChanged(key, newValue, oldValue);
			}
		}
		
	}
}
