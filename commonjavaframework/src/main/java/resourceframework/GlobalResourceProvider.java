package resourceframework;

import java.util.HashMap;

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
	public synchronized void registerResource(String key, Object value)
			throws ResourceProviderException {

		if (value == null || key == null) {
			throw new NullPointerException();
		}

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
	 *            {@link Sting} to identify the resource where the value should
	 *            be changed. A {@link ResourceProviderException} will be throw
	 *            if the key can't be found. Must not be {@code null}.
	 * @param value
	 *            The new value to register. Must not be {@code null}.
	 * @throws ResourceProviderException
	 *             Will be throw if the given key can't be found.
	 */
	public synchronized void changeResource(String key, Object value)
			throws ResourceProviderException {

		if (key == null || value == null) {
			throw new NullPointerException();
		}

		HashMap<String, Object> resource = getResources();

		if (!resource.containsKey(key)) {
			throw new ResourceProviderException("The key " + key + " can't be found.");
		}

		resource.put(key, value);
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
	public synchronized Object getResource(String key) throws ResourceProviderException {

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
	public synchronized void deleteResource(String key) {

		if (key == null) {
			throw new NullPointerException();
		}

		getResources().remove(key);
	}
}
