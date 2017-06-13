package test;

import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import logging.CommonLogger;
import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoggerTest {

	@Test
	public void t1_logMessage() {
		
		try {
			String current = new java.io.File(".").getCanonicalPath();
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
	
}
