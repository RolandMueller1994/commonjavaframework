package test;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.management.AttributeNotFoundException;

import org.junit.Test;

import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;
import startup.ArgParser;

public class ArgParserTest {

	private ArgParser parser = new ArgParser();


	@Test
	public void parseTest() {
		String[] stringArray = { "--testBoolean1", "true", "--testBoolean2", "false", "--testLong", "123456",
				"--testDouble", "1234.56", "--testString", "testString" };
		try {
			parser.parse(stringArray, false);
			assertEquals(new Boolean("true"), GlobalResourceProvider.getInstance().getResource("testBoolean1"));
			assertEquals(new Boolean("false"), GlobalResourceProvider.getInstance().getResource("testBoolean2"));
			assertEquals(new Long(123456), GlobalResourceProvider.getInstance().getResource("testLong"));
			assertEquals(new Double(1234.56), GlobalResourceProvider.getInstance().getResource("testDouble"));
			assertEquals("testString", GlobalResourceProvider.getInstance().getResource("testString"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void missingValueTest() throws ResourceProviderException {
		String[] stringArray = { "--testKey1", "--testKey2"};
		try {
			parser.parse(stringArray, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		assertTrue((Boolean) GlobalResourceProvider.getInstance().getResource("testKey1"));
		assertTrue((Boolean) GlobalResourceProvider.getInstance().getResource("testKey2"));
	}
	
	@Test
	public void missingKeyTest() {
		String[] stringArray = { "--testKey3", "testValue1", "testValue2"};
		try {
			parser.parse(stringArray, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		fail();
	}
	
	@Test
	public void missingValueTest2() {
		String[] stringArray = { "--testKey4", "testValue1", "--testKey5"};
		try {
			parser.parse(stringArray, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void nullArgsTest() {
		try {
			parser.parse(null, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void emptyArgsTest() {
		String[] stringArray = {};
		try {
			parser.parse(stringArray, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void checkTest1() throws ResourceProviderException, IOException {
		GlobalResourceProvider resProv = GlobalResourceProvider.getInstance();
		if(!resProv.checkRegistered("workDir")) {
			String current = new java.io.File(".").getCanonicalPath();
			resProv.registerResource("workDir", current);
		}
		
		String[] stringArray = {"--checktest1", "true", "--checktest2", "12345", "--checktest3", "123.45", "--checktest4", "string"};
		try {
			parser.parse(stringArray, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void checkTest2() throws AttributeNotFoundException, ResourceProviderException, IOException {
		GlobalResourceProvider resProv = GlobalResourceProvider.getInstance();
		if(!resProv.checkRegistered("workDir")) {
			String current = new java.io.File(".").getCanonicalPath();
			resProv.registerResource("workDir", current);
		}
		
		
		String[] stringArray = {"--checktest1", "1234"};
		try {
			parser.parse(stringArray, true);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		fail();
	}
	
	@Test
	public void checkTest3() throws AttributeNotFoundException, ResourceProviderException, IOException {
		GlobalResourceProvider resProv = GlobalResourceProvider.getInstance();
		if(!resProv.checkRegistered("workDir")) {
			String current = new java.io.File(".").getCanonicalPath();
			resProv.registerResource("workDir", current);
		}
		
		
		String[] stringArray = {"--checktest2", "true"};
		try {
			parser.parse(stringArray, true);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		fail();
	}
	
	@Test
	public void checkTest4() throws AttributeNotFoundException, ResourceProviderException, IOException {
		GlobalResourceProvider resProv = GlobalResourceProvider.getInstance();
		if(!resProv.checkRegistered("workDir")) {
			String current = new java.io.File(".").getCanonicalPath();
			resProv.registerResource("workDir", current);
		}
		
		String[] stringArray = {"--checktest3", "string"};
		try {
			parser.parse(stringArray, true);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		fail();
	}
	
	@Test
	public void checkTest5() throws AttributeNotFoundException, ResourceProviderException, IOException {
		GlobalResourceProvider resProv = GlobalResourceProvider.getInstance();
		if(!resProv.checkRegistered("workDir")) {
			String current = new java.io.File(".").getCanonicalPath();
			resProv.registerResource("workDir", current);
		}
		
		String[] stringArray = {"--checktest4", "123.45"};
		try {
			parser.parse(stringArray, true);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		fail();
	}
	
	@Test
	public void checkTest6() throws ResourceProviderException, IOException {
		GlobalResourceProvider resProv = GlobalResourceProvider.getInstance();
		if(!resProv.checkRegistered("workDir")) {
			String current = new java.io.File(".").getCanonicalPath();
			resProv.registerResource("workDir", current);
		}
		
		String[] stringArray = {"--checktest8", "false"};
		try {
			parser.parse(stringArray, true);
		} catch (AttributeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		fail();
	}
	
	@Test
	public void checkTest7() throws ResourceProviderException, IOException {
		GlobalResourceProvider resProv = GlobalResourceProvider.getInstance();
		if(!resProv.checkRegistered("workDir")) {
			String current = new java.io.File(".").getCanonicalPath();
			resProv.registerResource("workDir", current);
		}
		
		String[] stringArray = {"--checktest6"};
		try {
			parser.parse(stringArray, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void checkTest8() throws ResourceProviderException, IOException {
		GlobalResourceProvider resProv = GlobalResourceProvider.getInstance();
		if(!resProv.checkRegistered("workDir")) {
			String current = new java.io.File(".").getCanonicalPath();
			resProv.registerResource("workDir", current);
		}
		
		String[] stringArray = {"--checktest5", "true"};
		try {
			parser.parse(stringArray, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
}
