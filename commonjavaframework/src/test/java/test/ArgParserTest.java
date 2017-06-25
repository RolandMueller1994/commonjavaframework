package test;

import static org.junit.Assert.*;

import org.junit.Test;

import resourceframework.GlobalResourceProvider;
import startup.ArgParser;

public class ArgParserTest {

	private ArgParser parser = new ArgParser();


	@Test
	public void parseTest() {
		String[] stringArray = { "--testBoolean1", "true", "--testBoolean2", "false", "--testLong", "123456",
				"--testDouble", "1234.56", "--testString", "testString" };
		try {
			parser.parse(stringArray);
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
	public void missingValueTest() {
		String[] stringArray = { "--testKey1", "--testKey2"};
		try {
			parser.parse(stringArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		fail();
	}
	
	@Test
	public void missingKeyTest() {
		String[] stringArray = { "--testKey3", "testValue1", "testValue2"};
		try {
			parser.parse(stringArray);
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
			parser.parse(stringArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		fail();
	}
	
	@Test
	public void nullArgsTest() {
		try {
			parser.parse(null);
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
			parser.parse(stringArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
}
