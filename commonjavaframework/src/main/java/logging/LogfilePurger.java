package logging;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;

/**
 * This class is used to clean up logfiles. Logfiles will be deleted after the
 * specified time (GlobalResource "keepLogfileTime" in days) or after 14 days.
 * 
 * @author roland
 *
 */
public class LogfilePurger {
	
	private static long DEFAULT_KEEP_TIME = 14;

	/**
	 * Deletes old logfiles after the specified time. GlobalResource
	 * "loggingPath" has to be set before. Call this method at startup of the
	 * application.
	 */
	public static void purge() {
		try {
			String filePath = (String) GlobalResourceProvider.getInstance().getResource("loggingPath");
			File loggingDirectory = new File(filePath);
			File[] directorys = loggingDirectory.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					if (pathname.isDirectory()) {
						return true;
					}
					return false;
				}

			});

			List<File> directoryArray = Arrays.asList(directorys);

			for (File curDirectory : directoryArray) {
				File[] fileArray = curDirectory.listFiles(new FileFilter() {

					@Override
					public boolean accept(File pathname) {
						if (pathname.isFile()) {
							return true;
						}
						return false;
					}
				});

				List<File> fileList = Arrays.asList(fileArray);

				String curFileDate;
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

				long days;
				if(GlobalResourceProvider.getInstance().checkRegistered("keepLogfileTime")) {
					days = (long) GlobalResourceProvider.getInstance().getResource("keepLogfileTime");
				} else {
					days = DEFAULT_KEEP_TIME;
				}
				
				for (File curFile : fileList) {
					curFileDate = curFile.getName().substring(curFile.getName().lastIndexOf("_") + 1);
					try {
						Date date = df.parse(curFileDate);
						if (System.currentTimeMillis() - date.getTime() > days * 24 * 3600 * 1000) {
							curFile.delete();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} catch (ResourceProviderException e) {
			e.printStackTrace();
		}
	}

}
