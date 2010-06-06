package org.eclipse.xslt.conversion.actions;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import javax.xml.parsers.*;

import org.w3c.dom.*;

// class for parsing webml_diagram file and get existing coords of blocks
// returns corresponding DOM
public class UpdateCoordsHandler extends DefaultHandler{
	
	  private Document coords; // DOM with coordinates of blocks
	  private Element rootCoords; // root element of coords DOM
	  private String curId; //id of current element on the diagram
	  	  
	  // returns DOM with coordinates of blocks
	  public Document getCoords() {
	    return coords;
	  }
	  
	  // Event while parsing - Document started
	  @Override
	  public void startDocument() throws SAXException
	  {
		//get an instance of factory
		DocumentBuilderFactory cdbf = DocumentBuilderFactory.newInstance();
		try {
			//get an instance of builder
			DocumentBuilder cdb = cdbf.newDocumentBuilder();

			//create an instance of DOMs
			coords  = cdb.newDocument();
			rootCoords = coords.createElement("root");
			coords.appendChild(rootCoords);
		}catch(Exception err) {
			//err.printStackTrace();
		}
	  }

	  // Event while parsing - new Element started
	  public void startElement(String uri, String localName, String qName, Attributes attr) throws SAXException
	  {
		  if (qName.equals("element")) {
			  int gmfIdPos = attr.getValue("href").lastIndexOf("#") + 1;
			  curId = attr.getValue("href").substring(gmfIdPos);
		  }
		  else if ((qName.equals("layoutConstraint")) && (!curId.substring(0, curId.lastIndexOf(".")).endsWith("topic"))){
			  //create new element with current coordinates 
			  Element newCoords = coords.createElement("element");
			  //add name and coordinates attributes 
			  newCoords.setAttribute("gmfId", curId);
			  String xVal = attr.getValue("x");
			  // if x is not defined let it be 0 by default
			  if (xVal == null)
				  xVal = "0";
			  newCoords.setAttribute("x", xVal);
			  String yVal = attr.getValue("y");
			  // if y is not defined let it be 0 by default
			  if (yVal == null)
				  yVal = "0";
			  newCoords.setAttribute("y", yVal);
			 
			  // append element to root
			  rootCoords.appendChild(newCoords);
		  }
	  }
}
