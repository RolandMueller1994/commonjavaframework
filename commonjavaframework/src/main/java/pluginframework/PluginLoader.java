package pluginframework;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * This framework is used to register and generate plugin objects out of .jar
 * files. The .jar has to have the same name as the class itself. The class has
 * to be in a package with the same name as the class, where all characters have
 * to be lower case.<br>
 * <br>
 * Due to the use of generics it isn't possible to generate a singleton
 * instance. You have to generate one instance of this class and distribute this
 * object.
 * 
 * @author roland
 *
 * @param <T>
 *            The interface which has to be implemented by plugins.
 */
public class PluginLoader<T extends PluginInterface> {

	private HashMap<String, Class<T>> externalPluginMap = new HashMap<>();
	private HashMap<String, Class<T>> internalPluginMap = new HashMap<>();

	/**
	 * This method is used to register external plugins. The plugin is searched
	 * in the path to a given .jar file. The class which should be loaded as
	 * plugin has to have the same name as the .jar file. The class has to be in
	 * a package with the same name as the .jar file except that all characters
	 * have to be lower case.
	 * 
	 * @param pluginPath
	 *            The path to the .jar file. Must not be {@code null}.
	 * @throws ClassNotFoundException
	 *             If the searched class couldn't be found.
	 * @throws IOException
	 *             If an error occurs within the file handling.
	 * @throws ClassCastException
	 *             If the class in the .jar file doesn't implement the generic
	 *             type of this class.
	 * @throws IllegalArgumentException
	 *             If the path doesn't point to jar file.
	 */
	@SuppressWarnings("unchecked")
	public synchronized void registerExternPlugin(@Nonnull Path pluginPath)
			throws ClassNotFoundException, IOException, ClassCastException, IllegalArgumentException {
		File jarFile = new File(pluginPath.toString());

		String name = jarFile.getName();

		// Determine if given file is a jar.
		if (!name.substring(name.lastIndexOf(".")).equals(".jar")) {
			throw new IllegalArgumentException("File to register a plugin isn't a jar file");
		}

		// Get the URL to the jar file
		URL[] url = new URL[] { jarFile.toURI().toURL() };
		URLClassLoader loader = new URLClassLoader(url);

		// Delete .jar ending.
		name = name.substring(0, name.lastIndexOf("."));

		// Load the class -> unchecked cast -> ClassCastException if it can't be
		// casted
		Class<T> plugin = (Class<T>) loader.loadClass(name.toLowerCase() + "." + name);

		externalPluginMap.put(name, plugin);
		loader.close();
	}

	/**
	 * This method is used to register internal plugins which aren't located in
	 * a jar file. This plugins have to be part of the code of the project.
	 * 
	 * @param name
	 *            the name of the plugin as {@link String}.
	 * 
	 * @param clazz
	 *            the {@link Class} to register.
	 */
	public synchronized void registerInternalPlugin(@Nonnull String name, @Nonnull Class<T> clazz) {
		internalPluginMap.put(name, clazz);
	}

	/**
	 * Creates a new instance of a registered plugin.
	 * 
	 * @param name
	 *            The name of the plugin. Must not be {@code null}.
	 * @return The new instance or null if the given name isn't mapped.
	 * @throws InstantiationException
	 *             If the new instantiated can't be created.
	 * @throws IllegalAccessException
	 *             If the access to created the new instance isn't granted.
	 */
	@CheckForNull
	public synchronized T getPlugin(String name) throws InstantiationException, IllegalAccessException {
		if (externalPluginMap.containsKey(name)) {
			return externalPluginMap.get(name).newInstance();
		} else if (internalPluginMap.containsKey(name)) {
			return internalPluginMap.get(name).newInstance();
		}
		return null;
	}

	/**
	 * Get a list of external plugin names.
	 * 
	 * @return a {@link List} of {@link String}s which contains the names of all
	 *         external plugins. Will not be {@code null}.
	 */
	@Nonnull
	public synchronized List<String> getAvailableExternPlugins() {
		LinkedList<String> retList = new LinkedList<>();
		retList.addAll(externalPluginMap.keySet());
		return retList;
	}

	/**
	 * Get a list of all plugin names.
	 * 
	 * @return a {@link List} of {@link String}s which contains the names of all
	 *         plugins. Will not be {@code null}.
	 */
	@Nonnull
	public synchronized List<String> getAllAvailablePlugins() {

		LinkedList<String> retList = new LinkedList<>();
		retList.addAll(internalPluginMap.keySet());
		retList.addAll(getAvailableExternPlugins());

		return retList;
	}

	/**
	 * Removes the specified plugin from the plugin loader
	 * 
	 * @param name
	 *            the name of the plugin to remove
	 */
	public synchronized void removeExternalPlugin(@Nonnull String name) {
		externalPluginMap.remove(name);
	}

}
