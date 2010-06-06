package org.eclipse.xslt.conversion.actions;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import java.util.ArrayList;

// class for parsing drl file and extracting information about
// InfElements in it
public class GetInfElementsHandler extends DefaultHandler{
	
	  private ArrayList<String> infElems = new ArrayList<String>();
	  
	  // returns array with information about InfElements
	  public ArrayList<String> getInfElements()
	  {
	    return infElems;
	  }
	  
	  // Event while parsing - new element started
	  public void startElement(String uri, String localName, String qName, Attributes attr) throws SAXException
	  {
		  if (qName.endsWith("InfElement")) {
			  // add info about InfElement to infElems ArrayList
			  infElems.add(attr.getValue("name") + " (ID='" + attr.getValue("id") + "')");
		  }
	  }
}
