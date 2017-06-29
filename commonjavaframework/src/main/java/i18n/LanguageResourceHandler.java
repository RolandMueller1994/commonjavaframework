package i18n;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

import javax.annotation.Nonnull;

import logging.CommonLogger;
import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;

/**
 * This is a handler for translation tasks.
 * 
 * @author roland
 *
 */
public class LanguageResourceHandler {

	private static LanguageResourceHandler instance;
	private static Locale defaultLocale = Locale.GERMAN;
	private static Locale currentLocale = Locale.GERMAN;

	private boolean diffLocales;
	private HashMap<String, String> defaultStrings = new HashMap<>();
	private HashMap<String, String> currentStrings = new HashMap<>();
	private HashMap<String, String> globalDefaultStrings = new HashMap<>();
	private HashMap<String, String> globalCurrentStrings = new HashMap<>();

	private LanguageResourceHandler() throws ResourceProviderException {
		readResource(false);

		diffLocales = !defaultLocale.equals(currentLocale);

		if (diffLocales) {
			readResource(true);
		}
	}

	/**
	 * Creates and returns a singleton instance of this class.
	 * 
	 * @return The singleton instance. Never null.
	 * @throws ResourceProviderException
	 *             if the current working directory (key: workDir) hasn't
	 *             already been registered at the {@link GlobalResourceProvider}
	 */
	@Nonnull
	public static LanguageResourceHandler getInstance() throws ResourceProviderException {
		if (instance == null) {
			instance = new LanguageResourceHandler();
		}
		return instance;
	}

	/**
	 * Sets the default language for translations. Must be called before
	 * getInstance is called.
	 * 
	 * @param locale
	 *            The default {@link Locale}. Must not be null.
	 */
	public static void setDefaultLanguage(@Nonnull Locale locale) {
		defaultLocale = locale;
	}

	/**
	 * Sets the current language for translations. Must be called before
	 * getInstance is called.
	 * 
	 * @param locale
	 *            The current {@link Locale}. Must not be null.
	 */
	public static void setCurrentLanguage(@Nonnull Locale locale) {
		currentLocale = locale;
	}

	/**
	 * Search for the translation for the given resource.
	 * 
	 * @param clazz
	 *            The class in which the resource is used. Must not be null.
	 * @param resource
	 *            The resource to search. Must not be null.
	 * @return At first the current translation if present. Then the default
	 *         translation if present. Else the class.getName() appended by a
	 *         dot and the resource string. Never null.
	 */
	@Nonnull
	public String getLocalizedText(Class clazz, String resource) {
		String text = currentStrings.get(clazz.getName() + "." + resource);
		if (text == null && diffLocales) {
			text = defaultStrings.getOrDefault(clazz.getName() + "." + resource, clazz.getName() + "." + resource);
		} else if (text == null) {
			text = clazz.getName() + "." + resource;
		}
		return text;
	}

	/**
	 * @param resource
	 * @return
	 */
	@Nonnull
	public String getLocalizedText(String resource) {
		String text = globalCurrentStrings.get(resource);
		if (text == null && diffLocales) {
			text = globalDefaultStrings.getOrDefault(resource, resource);
		} else if (text == null) {
			text = resource;
		}
		return text;
	}

	private void readResource(boolean def) throws ResourceProviderException {
		String path = (String) GlobalResourceProvider.getInstance().getResource("workDir");
		path = path + File.separator + "i18n";

		String language;

		if (def) {
			language = defaultLocale.getLanguage();
		} else {
			language = currentLocale.getLanguage();
		}

		File resourceFile = new File(path + File.separator + "languageresource." + language);
		if (resourceFile.exists()) {
			FileReader reader;
			try {
				reader = new FileReader(resourceFile);
				BufferedReader bufReader = new BufferedReader(reader);
				String line;
				while ((line = bufReader.readLine()) != null) {
					if (!line.isEmpty()) {
						line.replace("%cr", System.lineSeparator());
						if (def) {
							if (line.contains(".")) {
								defaultStrings.put(line.substring(0, line.indexOf("=")),
										line.substring(line.indexOf("=") + 1));
							} else {
								globalDefaultStrings.put(line.substring(0, line.indexOf("=")),
										line.substring(line.indexOf("=") + 1));
							}
						} else {
							if (line.contains(".")) {
								currentStrings.put(line.substring(0, line.indexOf("=")),
										line.substring(line.indexOf("=") + 1));
							} else {
								globalCurrentStrings.put(line.substring(0, line.indexOf("=")),
										line.substring(line.indexOf("=") + 1));
							}
						}
					}
				}
				bufReader.close();
			} catch (FileNotFoundException e) {
				// Won't happen due to previous check.
			} catch (IOException e) {
				CommonLogger.getInstance().logException(e);
				throw new RuntimeException(e);
			}
		}
	}
}
