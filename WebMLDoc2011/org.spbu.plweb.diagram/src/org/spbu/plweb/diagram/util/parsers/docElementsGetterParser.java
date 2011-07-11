package org.spbu.plweb.diagram.util.parsers;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class docElementsGetterParser extends DefaultHandler {
	private String title = null;
	public static List <String> listOfTopics = new ArrayList<String>();

	public static List<String> getListOfTopics() {
		return listOfTopics;
	}

	@Override
	public void startDocument() throws SAXException {
		title = null;
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("d:InfElement")) {
			title = attributes.getValue("name");
			listOfTopics.add(attributes.getValue("id"));
		}
	}

	public String getTitle() {
		return title;
	}
	
	  public void endDocument(){
	       System.out.println(listOfTopics);
	       System.out.println("All recorded logs displayed.");
	       System.out.println("More may have been updated within"
	                  + " the appropriate timeframe.");
	  }
}
