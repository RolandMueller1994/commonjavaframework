package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import multithreading.SecureRunnable;
import multithreading.UncaughtSecureRunnableException;
import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SecureThreadTest {

	private int count1 = 0;
	private int count2 = 0;

	@Test
	public void t1_testCaughtException() throws InterruptedException, IOException, ResourceProviderException {

		if(!GlobalResourceProvider.getInstance().checkRegistered("loggingPath")) {
			String current = new java.io.File(".").getCanonicalPath();
			GlobalResourceProvider.getInstance().registerResource("loggingPath", current);			
		}
		
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		
		TestSecureRunnable runnable = new TestSecureRunnable();
		runnable.caught = true;
		executor.scheduleAtFixedRate(runnable, 0, 50, TimeUnit.MILLISECONDS);

		Thread.sleep(1000);

		if (count1 > 1) {
			return;
		}
		
		fail();
	}
	
	@Test
	public void t2_testUncaughtException() throws InterruptedException, IOException, ResourceProviderException {

		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		
		TestSecureRunnable runnable = new TestSecureRunnable();
		runnable.caught = false;
		executor.scheduleAtFixedRate(runnable, 0, 50, TimeUnit.MILLISECONDS);

		Thread.sleep(1000);

		if (count2 == 1) {
			return;
		}
		fail();
	}

	public class TestSecureRunnable extends SecureRunnable {

		public boolean caught = false;
		
		@Override
		public void runSecure() {

			if(caught) {
				count1++;
				System.out.println("count1 = " + count1);
				throw new RuntimeException("TestCaughtException");				
			} else {
				count2++;
				System.out.println("count2 = " + count2);
				throw new UncaughtSecureRunnableException("TestUncaughtException");
			}
		}

	}

}
