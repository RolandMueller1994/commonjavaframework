package configfileframework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;

import resourceframework.GlobalResourceChangedListener;
import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;

/**
 * Central access point for creating, loading and changing of config files.
 * 
 * @author roland
 *
 */
public class ConfigFileHandler {

	private GlobalResourceProvider resProv = GlobalResourceProvider.getInstance();
	private ConfigFileValueChangedListener valueChangedListener;

	private HashMap<String, Object> checkedParameters;
	private HashMap<String, Object> inputParameters;

	private String filePath;

	/**
	 * Creates a new ConfigFileHandler with the given parameters. Searchs for a
	 * already existing config file and try's to load it if it matches the given
	 * parameters (only type not value). If there is not config file a new one
	 * will be written. If the config file is corrupted a new file will be
	 * written. <br/>
	 * <br/>
	 * Parameters will be registered at the {@link GlobalResourceProvider}. If
	 * the value changes in {@link GlobalResourceProvider} it will be
	 * automatically written to the config file.
	 * 
	 * @param configName
	 *            the name of this handler. Will be in the config file name.
	 *            Must not be null.
	 * @param parameters
	 *            the parameters which should be in the config file and their
	 *            default value. Must not be null. Only values of type
	 *            {@link String}, {@link Double}, {@link Long} or
	 *            {@link Boolean} are possible.
	 * @throws ResourceProviderException
	 *             if something happens when the parameters are registered at
	 *             the {@link GlobalResourceProvider}.
	 * @throws ConfigFileHandlerException
	 *             if there are parameters given which aren't an instance of
	 *             {@link String}, {@link Double}, {@link Long} or
	 *             {@link Boolean}.
	 */
	public ConfigFileHandler(String configName, HashMap<String, Object> parameters)
			throws ResourceProviderException, ConfigFileHandlerException {

		for (Object value : parameters.values()) {
			if (!(value instanceof Double) && !(value instanceof Long) && !(value instanceof Boolean)
					&& !(value instanceof String)) {
				throw new ConfigFileHandlerException(
						"There is a value which isn't instance of Double, Long, Boolean or String");
			}
		}

		inputParameters = parameters;

		String path = (String) resProv.getResource("workDir");
		path = path + File.separator + ".config";

		File configDir = new File(path);

		if (configDir.exists() && configDir.isDirectory()) {

			filePath = path + File.separator + configName + ".config";
			File[] configFiles = configDir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if (name.equals(configName + ".config")) {
						return true;
					}
					return false;
				}
			});

			if (configFiles.length == 1) {
				File configFile = configFiles[0];
				HashMap<String, String> fileParameters = readFile(configFile);
				try {
					checkedParameters = checkAgainstParameters(parameters, fileParameters);
				} catch (ConfigFileHandlerException ex) {
					checkedParameters = parameters;
					configFiles[0].delete();
					writeFile();
				}

			} else {
				for (int i = 0; i < configFiles.length; i++) {
					configFiles[i].delete();
				}
				checkedParameters = parameters;
				writeFile();
			}
		} else {
			throw new ConfigFileHandlerException("The .config directory doesn't exist!");
		}

		registerResources();
	}

	private HashMap<String, String> readFile(File file) {

		HashMap<String, String> retMap = new HashMap<>();

		FileReader reader;
		try {
			reader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(reader);
			String line;
			while ((line = bufReader.readLine()) != null) {
				if (!line.isEmpty()) {

					if (!line.contains("=")) {
						retMap.clear();
						return retMap;
					}

					String parameter = line.substring(0, line.indexOf("="));
					String value = line.substring(line.indexOf("=") + 1);

					if (retMap.containsKey(parameter)) {
						retMap.clear();
						return retMap;
					}

					retMap.put(parameter, value);
				}
			}
			bufReader.close();
		} catch (IOException e) {
			retMap.clear();
		}

		return retMap;
	}

	private void writeFile() throws ConfigFileHandlerException {
		File configFile = new File(filePath);

		if (configFile.exists() && configFile.isFile()) {
			configFile.delete();
		}

		try {
			FileWriter writer = new FileWriter(configFile);

			for (String param : inputParameters.keySet()) {
				Object value = checkedParameters.get(param);
				writer.append(param + "=" + value.toString() + System.lineSeparator());
			}

			writer.close();
		} catch (IOException e) {
			throw new ConfigFileHandlerException("Unable to write config file");
		}
	}

	private HashMap<String, Object> checkAgainstParameters(HashMap<String, Object> parameters,
			HashMap<String, String> fileParameters) throws ConfigFileHandlerException {

		HashMap<String, Object> retMap = new HashMap<>();

		for (String fileParameter : fileParameters.keySet()) {
			if (parameters.containsKey(fileParameter)) {
				Object param = parameters.get(fileParameter);
				String fileParam = fileParameters.get(fileParameter);
				if (param instanceof Long) {
					try {
						Long fileIntParam = new Long(fileParam);
						retMap.put(fileParameter, fileIntParam);
					} catch (NumberFormatException ex) {
						throw new ConfigFileHandlerException("File config wrong!");
					}
				} else if (param instanceof Double) {
					try {
						Double fileDoubleParam = new Double(fileParam);
						retMap.put(fileParameter, fileDoubleParam);
					} catch (NumberFormatException ex) {
						throw new ConfigFileHandlerException("File config wrong!");
					}
				} else if (param instanceof Boolean) {
					if (fileParam.equals("true")) {
						retMap.put(fileParameter, new Boolean(true));
					} else if (fileParam.equals("false")) {
						retMap.put(fileParameter, new Boolean(true));
					} else {
						throw new ConfigFileHandlerException("File config wrong!");
					}
				} else if (param instanceof String) {
					retMap.put(fileParameter, fileParam);
				} else {
					throw new ConfigFileHandlerException("File config wrong!");
				}
			} else {
				throw new ConfigFileHandlerException("File config wrong!");
			}
		}
		
		for (String parameter : parameters.keySet()) {
			if(!fileParameters.containsKey(parameter)) {
				System.out.println(parameter);
				throw new ConfigFileHandlerException("File config wrong!");
			}
		}

		return retMap;
	}

	private void registerResources() throws ConfigFileHandlerException {
		valueChangedListener = new ConfigFileValueChangedListener();

		for (String param : checkedParameters.keySet()) {
			try {
				resProv.registerResource(param, checkedParameters.get(param));
				resProv.registerResourceChangedListener(valueChangedListener, param);
			} catch (ResourceProviderException e) {
				throw new ConfigFileHandlerException("Couldn't register parameter!");
			}
		}
	}

	public class ConfigFileValueChangedListener implements GlobalResourceChangedListener {

		@Override
		public void resourceChanged(String key, Object newValue, Object oldValue) {

			if (checkedParameters.containsKey(key)) {
				checkedParameters.put(key, newValue);
				try {
					writeFile();
				} catch (ConfigFileHandlerException e) {
					// TODO Check what have to be done
					e.printStackTrace();
				}
			}

		}

	}

}
