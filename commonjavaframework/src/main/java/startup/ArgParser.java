package startup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.management.AttributeNotFoundException;

import logging.CommonLogger;
import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;

/**
 * This parser extracts the arguments at startup and registers them at the
 * {@link GlobalResourceProvider}
 * 
 * @author roland
 *
 */
public class ArgParser {

	/**
	 * This method will parse the arguments and registers them at the
	 * {@link GlobalResourceProvider}. <br>
	 * <br>
	 * It will be checked if the argument can be represented by either a
	 * {@link Boolean}, {@link Long} or {@link Double}. If not the given
	 * {@link String} will be stored. If there is no value for a key, a boolean
	 * with value true will be stored.
	 *
	 * 
	 * @param args
	 *            The arguments from the Main method. Can be {@code null}.
	 * @param check
	 *            True if arguments shall be checked against ./arguments.txt
	 * @throws AttributeNotFoundException
	 *             If check==true and the argument isn't specified in
	 *             ./arguments.txt
	 * @throws IllegalArgumentException
	 *             If check==true and the value doesn't match the specified type
	 */
	public synchronized void parse(@Nullable String[] args, boolean check)
			throws AttributeNotFoundException, IllegalArgumentException {
		if (args == null || args.length == 0) {
			return;
		}

		HashMap<String, String> possibleArgs = new HashMap<>();

		if (check) {
			File file;
			try {
				file = new File(
						GlobalResourceProvider.getInstance().getResource("workDir") + File.separator + "arguments.txt");
				FileReader reader = new FileReader(file);
				BufferedReader bufReader = new BufferedReader(reader);

				String line;
				while ((line = bufReader.readLine()) != null) {
					String arg = line.substring(0, line.indexOf(" "));
					String type = line.substring(line.indexOf(" ") + 1);
					possibleArgs.put(arg, type);
				}
				bufReader.close();
			} catch (ResourceProviderException | IOException e) {
				CommonLogger.getInstance().logException(e);
				throw new RuntimeException(e);
			}
		}

		List<String> argsList = Arrays.asList(args);

		boolean wasKey = false;
		String curKey = "";
		try {
			for (String arg : argsList) {
				if (arg.startsWith("--")) {
					if (wasKey) {
						if (check) {
							String targetType = (String) possibleArgs.get(curKey);
							if (!targetType.equals("boolean")) {
								throw new IllegalArgumentException(
										"Wrong value for key " + curKey + ": boolean instead of " + targetType);
							}
						}

						// Register default true value
						GlobalResourceProvider.getInstance().registerResource(curKey, new Boolean("true"));
					}
					wasKey = true;
					curKey = arg.substring(2);
					if (check && !possibleArgs.containsKey(curKey)) {
						throw new AttributeNotFoundException("Unknown parameter: " + curKey);
					}
				} else {
					if (!wasKey) {
						throw new IllegalArgumentException("Missing key for value!");
					}
					wasKey = false;
					if (arg.equals("true") || arg.equals("false")) {
						if (check) {
							String targetType = (String) possibleArgs.get(curKey);
							if (!targetType.equals("boolean")) {
								throw new IllegalArgumentException(
										"Wrong value for key " + curKey + ": boolean instead of " + targetType);
							}
						}

						GlobalResourceProvider.getInstance().registerResource(curKey, Boolean.parseBoolean(arg));
						continue;
					}
					try {
						Long value = Long.parseLong(arg);

						if (check) {
							String targetType = (String) possibleArgs.get(curKey);
							if (!targetType.equals("long")) {
								throw new IllegalArgumentException(
										"Wrong value for key " + curKey + ": long instead of " + targetType);
							}
						}

						GlobalResourceProvider.getInstance().registerResource(curKey, value);
						continue;
					} catch (NumberFormatException ex) {
						// Nothing to do. Just for checking if parsing went
						// right.
					}
					try {
						Double value = Double.parseDouble(arg);

						if (check) {
							String targetType = (String) possibleArgs.get(curKey);
							if (!targetType.equals("double")) {
								throw new IllegalArgumentException(
										"Wrong value for key " + curKey + ": double instead of " + targetType);
							}
						}

						GlobalResourceProvider.getInstance().registerResource(curKey, value);
						continue;
					} catch (NumberFormatException ex) {
						// Nothing to do. Just for checking if parsing went
						// right.
					}

					if (check) {
						String targetType = (String) possibleArgs.get(curKey);
						if (!targetType.equals("string")) {
							throw new IllegalArgumentException(
									"Wrong value for key " + curKey + ": string instead of " + targetType);
						}
					}
					GlobalResourceProvider.getInstance().registerResource(curKey, arg);
				}
			}
			// Register default value true if last key hasn't got a value
			if (wasKey) {
				if (check) {
					String targetType = (String) possibleArgs.get(curKey);
					if (!targetType.equals("boolean")) {
						throw new IllegalArgumentException(
								"Wrong value for key " + curKey + ": There shouldn't be a value for the last key");
					}
				}
				GlobalResourceProvider.getInstance().registerResource(curKey, new Boolean("true"));
			}
		} catch (ResourceProviderException ex) {
			CommonLogger.getInstance().logException(ex);
			throw new RuntimeException(ex);
		}
	}

}
