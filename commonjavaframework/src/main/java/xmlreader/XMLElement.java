package xmlreader;

import java.util.LinkedList;

public class XMLElement {
	private String tag;
	private LinkedList<XMLAttribute> attributes;
	private String textContent;
	private LinkedList<XMLElement> elements;
	
	public XMLElement(String tag) {
		this.tag = tag;
		attributes = null;
		elements = null;
		textContent = null;
	}
	
	public void addAttribute(XMLAttribute attr) {
		if(attributes == null) {
			attributes = new LinkedList<>();
		}
		
		attributes.add(attr);
	}
	
	public LinkedList<XMLAttribute> getAttributes() {
		return attributes;
	}
	
	public LinkedList<XMLElement> getElements() {
		return elements;
	}
	
	public void addElement(XMLElement elem) {
		if(elements == null) {
			elements = new LinkedList<XMLElement>();
		}
		
		elements.add(elem);
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	
	public String getTextContent() {
		return textContent;
	}
	
	public String parse(int level) {
		String tabString = "";
		for(int i=0; i<level; i++) {
			tabString += "\t";
		}
		
		String retVal = tabString + "<" + tag;
		
		if(attributes != null) {
			for(XMLAttribute attr : attributes) {
				retVal += " " + attr;
			}
		}
		
		retVal += ">";
		
		if(textContent != null) {
			retVal += textContent;
		}
		
		if(elements != null) {
			retVal += System.lineSeparator();
			
			for(XMLElement child : elements) {
				retVal += child.parse(level + 1);
			}
			
			retVal += tabString + "</" + tag + ">";
		} else {
			retVal += "</" + tag + ">";
		}
		
		return retVal + System.lineSeparator();
	}
}

