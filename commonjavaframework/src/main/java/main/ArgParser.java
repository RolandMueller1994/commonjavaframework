package main;

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
	 * {@link GlobalResourceProvider}.
	 * 
	 * @param args
	 *            The arguments from the Main method. Can be {@code null}.
	 */
	public synchronized void parse(@Nullable String[] args) {
		
	}

}
