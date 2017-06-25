package startup;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import resourceframework.GlobalResourceProvider;

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
	 * @throws Exception
	 *             An exception will be be thrown if a key or value is missing.
	 */
	public synchronized void parse(@Nullable String[] args) throws Exception {
		if (args == null || args.length == 0) {
			return;
		}
		List<String> argsList = Arrays.asList(args);

		boolean wasKey = false;
		String curKey = "";
		for (String arg : argsList) {
			if (arg.startsWith("--")) {
				if (wasKey) {
					GlobalResourceProvider.getInstance().registerResource(curKey, new Boolean("true"));
				}
				wasKey = true;
				// Register default true value
				curKey = arg.substring(2);
			} else {
				if (!wasKey) {
					throw new Exception("Missing key for value");
				}
				wasKey = false;
				if (arg.equals("true") || arg.equals("false")) {
					GlobalResourceProvider.getInstance().registerResource(curKey, Boolean.parseBoolean(arg));
					continue;
				}
				try {
					GlobalResourceProvider.getInstance().registerResource(curKey, Long.parseLong(arg));
					continue;
				} catch (NumberFormatException ex) {
					// Nothing to do. Just for checking if parsing went right.
				}
				try {
					GlobalResourceProvider.getInstance().registerResource(curKey, Double.parseDouble(arg));
					continue;
				} catch (NumberFormatException ex) {
					// Nothing to do. Just for checking if parsing went right.
				}
				GlobalResourceProvider.getInstance().registerResource(curKey, arg);
			}
		}
		//Register default value true if last key hasn't got a value
		if(wasKey) {
			GlobalResourceProvider.getInstance().registerResource(curKey, new Boolean("true"));
		}
	}

}
