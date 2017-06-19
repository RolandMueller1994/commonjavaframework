package pluginframework;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.HashMap;

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

	private HashMap<String, Class<T>> pluginMap = new HashMap<>();

	/**
	 * This method is used to register plugins. The plugin is searched in the
	 * path to a given .jar file. The class which should be loaded as plugin has
	 * to have the same name as the .jar file. The class has to be in a package
	 * with the same name as the .jar file except that all characters have to be
	 * lower case.
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
	public void registerPlugin(@Nonnull Path pluginPath)
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

		pluginMap.put(name, plugin);
		loader.close();
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
	public T getPlugin(String name) throws InstantiationException, IllegalAccessException {
		if(pluginMap.containsKey(name)) {
			return pluginMap.get(name).newInstance();			
		}
		return null;
	}

}
