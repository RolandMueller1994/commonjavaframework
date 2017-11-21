package test;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import resourceframework.GlobalResourceChangedListener;
import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GlobalResourceProviderTest {

	private final String  TEST_VALUE= "TestValue";
	
	private int count;
	
	@Test
	public void t01_createResourceTest() {
		try {
			GlobalResourceProvider.getInstance().registerResource(TEST_VALUE, TEST_VALUE);
			assertEquals(GlobalResourceProvider.getInstance().getResource(TEST_VALUE), TEST_VALUE);
			GlobalResourceProvider.getInstance().registerResource(TEST_VALUE, TEST_VALUE);
		} catch (ResourceProviderException e) {
			return;
		}		
		fail();
	}
	
	@Test
	public void t02_createNullKeyTest() {
		try {
			GlobalResourceProvider.getInstance().registerResource(null, TEST_VALUE);
		} catch (NullPointerException e) {
			return;
		} catch (ResourceProviderException e) {
			fail();
		}
		fail();
	}
	
	@Test
	public void t03_createNullValueTest() {
		try {
			GlobalResourceProvider.getInstance().registerResource(TEST_VALUE, null);
		} catch (NullPointerException e) {
			return;
		} catch (ResourceProviderException e) {
			fail();
		}
		fail();
	}
	
	@Test
	public void t04_changeResourceTest() {
		try {
			GlobalResourceProvider.getInstance().changeResource(TEST_VALUE, TEST_VALUE + "2");
			assertEquals(GlobalResourceProvider.getInstance().getResource(TEST_VALUE), TEST_VALUE + "2");
		} catch (ResourceProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Not yet implemented");
		}
		
		boolean pass = false;
		try {
			GlobalResourceProvider.getInstance().changeResource(TEST_VALUE, new Integer(10));
		} catch (ResourceProviderException e) {
			pass = true;
		}
		
		if(!pass) {
			fail();
		}
	}
	
	@Test
	public void t05_changeNullKeyTest() {
		try {
			GlobalResourceProvider.getInstance().changeResource(null, TEST_VALUE);
		} catch (NullPointerException e) {
			return;
		} catch (ResourceProviderException e) {
			fail();
		}
		fail();
	}
	
	@Test
	public void t06_changeNullValueTest() {
		try {
			GlobalResourceProvider.getInstance().changeResource(TEST_VALUE, null);
		} catch (NullPointerException e) {
			return;
		} catch (ResourceProviderException e) {
			fail();
		}
		fail();
	}
	
	@Test
	public void t07_changeMissingValueTest() {
		try {
			GlobalResourceProvider.getInstance().changeResource("test", "test");
		} catch (ResourceProviderException e) {
			return;
		}
		fail();
	}
	
	@Test
	public void t08_getMissingResourceTest() {
		try {
			GlobalResourceProvider.getInstance().getResource("test");
		} catch (ResourceProviderException e) {
			return;
		}
		fail();
	}
	
	@Test 
	public void t09_getNullKeyTest() {
		try {
			GlobalResourceProvider.getInstance().getResource(null);
		} catch (ResourceProviderException e) {
			fail();
		} catch (NullPointerException e) {
			return;
		}
		fail();
	}
	
	@Test
	public void t10_deleteResourceTest() {
		GlobalResourceProvider.getInstance().deleteResource(TEST_VALUE);
		try {
			GlobalResourceProvider.getInstance().getResource(TEST_VALUE);
		} catch (ResourceProviderException e) {
			return;
		}
		fail();
	}
	
	@Test
	public void t11_deleteNullKeyTest() {
		try {
			GlobalResourceProvider.getInstance().deleteResource(null);
		} catch (NullPointerException e) {
			return;
		}
		fail();
	}
	
	@Test
	public void t12_checkRegisteredTest() {
		try {
			GlobalResourceProvider.getInstance().registerResource(TEST_VALUE + "12", TEST_VALUE);
			assertTrue(GlobalResourceProvider.getInstance().checkRegistered(TEST_VALUE + "12"));
			assertFalse(GlobalResourceProvider.getInstance().checkRegistered(TEST_VALUE + "13"));
		} catch (ResourceProviderException e) {
			fail();
			e.printStackTrace();
		}	
	}
	
	@Test
	public void t13_checkNullKeyTest() {
		try {
			GlobalResourceProvider.getInstance().checkRegistered(null);			
		} catch (NullPointerException ex) {
			return;
		}
		fail();
	}
	
	@Test
	public void t14_listenerTest() throws ResourceProviderException {
		String changeKey = "test_14_key";
		count = 0;
		
		GlobalResourceProvider.getInstance().registerResourceChangedListener(new GlobalResourceChangedListener() {
			
			@Override
			public void resourceChanged(String key, Object newValue, Object oldValue) {
				
				if(key.equals(changeKey) && newValue != null) {
					count++;
				}
				
			}
		}, changeKey);
		
		GlobalResourceProvider.getInstance().registerResource(changeKey, TEST_VALUE + "14.1");
		GlobalResourceProvider.getInstance().changeResource(changeKey, TEST_VALUE + "14.2");
		GlobalResourceProvider.getInstance().changeResource(changeKey, TEST_VALUE + "14.2");
		
		if(count != 2) {
			fail();
		}
	}
}
