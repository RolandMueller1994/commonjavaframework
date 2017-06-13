package test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import logging.CommonLogger;
import logging.LogfilePurger;
import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoggerTest {

	@Test
	public void t1_logMessage() {
		
		try {
			String current = new java.io.File(".").getCanonicalPath();
			current = current + File.separator + "logging";
			GlobalResourceProvider.getInstance().registerResource("loggingPath", current);			
		
			CommonLogger logger = CommonLogger.getInstance();
			String message = logger.logMessage("TestMessage");
			logger.logMessage("TestMessage2");
		} catch (ResourceProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void t2_logException() {
		CommonLogger logger = CommonLogger.getInstance();
		logger.logException(new Exception("Test"));
	}
	
	@Test
	public void t3_logExceptionAndMessage() {
		CommonLogger logger = CommonLogger.getInstance();
		logger.logMessageAndException("TestMessage3", new Exception("Test2"));
	}
	
	@Test
	public void t4_logfilePurger() {
		LogfilePurger.purge();
	}
	
}
