package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Locale;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import i18n.LanguageResourceHandler;
import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LanguageResourceHandlerTest {

	private static final String testval1 = "testval1";
	private static final String testval2 = "testval2";
	
	private static final String testval4 = "testval4";
	private static final String testval5 = "testval5";
	private static final String testval6 = "testval6";
	
	@Test
	public void t1_test() throws ResourceProviderException, IOException {
		GlobalResourceProvider resProv = GlobalResourceProvider.getInstance();
		if(!resProv.checkRegistered("workDir")) {
			String current = new java.io.File(".").getCanonicalPath();
			resProv.registerResource("workDir", current);
		}
		
		LanguageResourceHandler.setCurrentLanguage(Locale.GERMAN);
		LanguageResourceHandler.setDefaultLanguage(Locale.ENGLISH);
		
		LanguageResourceHandler resHandler = LanguageResourceHandler.getInstance();
		
		String curTestVal1 = resHandler.getLocalizedText(LanguageResourceHandlerTest.class, testval1);
		String curTestVal2 = resHandler.getLocalizedText(LanguageResourceHandlerTest.class, testval2);
		String curTestVal3 = resHandler.getLocalizedText(LanguageResourceHandlerTest.class, "testval3");
		
		System.out.println(curTestVal1);
		System.out.println(curTestVal2);
		System.out.println(curTestVal3);
		
		assertEquals("de_testval1", curTestVal1);
		assertEquals("en_testval2", curTestVal2);
		assertEquals("test.LanguageResourceHandlerTest.testval3", curTestVal3);
		
	}
	
	@Test
	public void t2_test() throws ResourceProviderException, IOException{
		GlobalResourceProvider resProv = GlobalResourceProvider.getInstance();
		if(!resProv.checkRegistered("workDir")) {
			String current = new java.io.File(".").getCanonicalPath();
			resProv.registerResource("workDir", current);
		}
		
		LanguageResourceHandler.setCurrentLanguage(Locale.GERMAN);
		LanguageResourceHandler.setDefaultLanguage(Locale.ENGLISH);
		
		LanguageResourceHandler resHandler = LanguageResourceHandler.getInstance();
		
		String curTestVal1 = resHandler.getLocalizedText(LanguageResourceHandlerTest.class, testval4, "fooooo");
		String curTestVal2 = resHandler.getLocalizedText(LanguageResourceHandlerTest.class, testval5, "fooooo");
		String curTestVal3 = resHandler.getLocalizedText(LanguageResourceHandlerTest.class, testval6, "fooooo","fooooo","fooooo");
		System.out.println(curTestVal1);
		System.out.println(curTestVal2);
		
		assertEquals("en_testval4fooooo", curTestVal1);
		assertEquals("de_testval5fooooo", curTestVal2);
		assertEquals("foooooen_foooootestval6fooooo", curTestVal3);
	}

}


