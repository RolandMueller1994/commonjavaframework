package xmlreader;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.naming.PartialResultException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLHandler {

	private String path;
	private XMLElement rootElement;

	public XMLHandler(String path) throws ParserConfigurationException, SAXException, IOException {
		this.path = path;
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
		        .newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(path);
		
		document.getDocumentElement().normalize();
		
		Element docElem = document.getDocumentElement();
		
		rootElement = parseElement(docElem);
	}
	
	public XMLHandler(XMLElement root) {
		rootElement = root;
	}
	
	public void rewriteXMLFile() {
		writeToNewFile(path);
	}
	
	public void writeToNewFile(String path) {
		try {
			FileWriter writer = new FileWriter(path);
			BufferedWriter bufWriter = new BufferedWriter(writer);
			
			bufWriter.write(createText());
			bufWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String createText() {
		String retVal = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + System.lineSeparator();
		retVal += System.lineSeparator();
		
		retVal += rootElement.parse(0);
		return retVal;
	}
	
	private XMLElement parseElement(Element elem) {
		String tag = elem.getTagName();
		XMLElement xmlElement = new XMLElement(tag);
		
		NamedNodeMap attrMap = elem.getAttributes();
		for(int i=0; i<attrMap.getLength(); i++) {
			Node attrNode = attrMap.item(i);
			String id = attrNode.getNodeName();
			String value = attrNode.getNodeValue();
			xmlElement.addAttribute(new XMLAttribute(id, value));
		}
		
		boolean childFound = false;
		NodeList childNodes = elem.getChildNodes();
		for(int i=0; i<childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			
			if(childNode.getNodeType() == Node.ELEMENT_NODE) {
				Element childElem = (Element) childNode;
				XMLElement childXMLElement = parseElement(childElem);
				xmlElement.addElement(childXMLElement);
				childFound = true;
			}
		}
		
		if(!childFound) {
			String textContent = elem.getTextContent();
			textContent.replaceAll(System.lineSeparator(), "");
			xmlElement.setTextContent(textContent);			
		}
		
		return xmlElement;
	}	
	
	public XMLElement getRootElement() {
		return rootElement;
	}
}
