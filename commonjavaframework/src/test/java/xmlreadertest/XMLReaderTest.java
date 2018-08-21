package xmlreadertest;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import xmlreader.XMLHandler;

public class XMLReaderTest {

	@Test
	public void test() throws ParserConfigurationException, SAXException, IOException {
		
		XMLHandler xmlReader = new XMLHandler("/home/roland/Schreibtisch/xml_test.xml");
		
		xmlReader.writeToNewFile("/home/roland/Schreibtisch/xml_test_2.xml");
		xmlReader.rewriteXMLFile();
	}

}
