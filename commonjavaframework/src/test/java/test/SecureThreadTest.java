package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import multithreading.SecureRunnable;
import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;

public class SecureThreadTest {

	private int count = 0;

	@Test
	public void test() throws InterruptedException, IOException, ResourceProviderException {

		String current = new java.io.File(".").getCanonicalPath();
		GlobalResourceProvider.getInstance().registerResource("loggingPath", current);

		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		
		executor.scheduleAtFixedRate(new TestSecureRunnable(), 0, 50, TimeUnit.MILLISECONDS);

		Thread.sleep(10000);

		if (count > 1) {
			return;
		}
		fail();
	}

	public class TestSecureRunnable extends SecureRunnable {

		@Override
		public void runSecure() {
			count++;
			System.out.println(count);
			throw new RuntimeException("TestException");
		}

	}

}
