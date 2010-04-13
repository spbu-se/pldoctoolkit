package org.eclipse.xslt.conversion.actions;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import javax.xml.parsers.*;

import org.w3c.dom.*;

import java.util.Stack;

// class for parsing diff xml file
// returns string with list of changes in webratio xml file
public class DiffHandler extends DefaultHandler{
	
	  private Document dom; // output dom with proper links
	  private Document coords; // DOM with coordinates of blocks
	  private String changesStr = ""; // String for output.
	  private Element rootCoords; // root element of coords DOM
	  private int level = 0; // level of current "element" tag while parsing
	  private int[] number = new int[5]; // array of numbers of current tag "element"
	  									 // for example, [1,0,3] means that current element
	  									 // is on the third level (3 elements in array),
	  									 // it is 3rd child (numbering from 0) of element,
	  									 // that is 0th child of top-level element, that
	  									 // is 1st child of root element.
	  // elemParent - Stack for store parent elements of current handling element
	  private Stack<Element> elemParent = new Stack<Element>();
	  
	  // returns DOM with links in proper format
	  public Document getDom()
	  {
		// remove "gmfId" attributes from every element
		removeGMFId();
	    return dom;
	  }
	  
	  // returns DOM with coordinates of blocks
	  public Document getCoords() {
	    return coords;
	  }

	// removes "gmfId" attribute from every element
	  private void removeGMFId()
	  {
		  // get list of all tags with name "element"
		  NodeList x = dom.getElementsByTagName("element");
		  for (int i = 0; i < x.getLength(); i++) {
			  Element curElem = (Element) x.item(i);
			  // remove attribute named "gmfId"
			  curElem.removeAttribute("gmfId");
		  }
	  }
	  
	  // Event while parsing - Document started
	  @Override
	  public void startDocument() throws SAXException
	  {
		  changesStr += "-----------new Siteview----------";
		  number[0] = -1;
		//get an instance of factory
/*		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilderFactory cdbf = DocumentBuilderFactory.newInstance();
		try {
			//get an instance of builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			DocumentBuilder cdb = cdbf.newDocumentBuilder();

			//create an instance of DOMs
			dom = db.newDocument();
			coords  = cdb.newDocument();
			rootCoords = coords.createElement("root");
			coords.appendChild(rootCoords);
		}catch(Exception err) {
			err.printStackTrace();
		}*/
	  }

	  // Event while parsing - new Element started
	  public void startElement(String uri, String localName, String qName, Attributes attr) throws SAXException
	  {
		  String changedStatus = attr.getValue("erc:inherit");
		  if (changedStatus == null)
			  changesStr  += "\nElement " + qName + " added";
		  else {
			  number[level]++;
			  number[++level] = -1;
			  if (changedStatus.equals("delete"))
				  changesStr += "\nElement " + qName + " deleted";
			  else if (changedStatus.equals("match")){
				  int attrCount = attr.getLength();
				  for(int i = 0 ; i<attrCount ; i++) {
					  String attrName = attr.getQName(i);
					  if (!attrName.equals("erc:inherit"))
						  changesStr += "\nAttribute " + attr.getQName(i) + " of element " + 
						  	qName + " changed. New value is " + attr.getValue(i);
				  }
			  }
		  }
		  /*if (qName.equals("webml:Siteview")) {
			  //create the root element 
			  Element rootElem = dom.createElement(qName);
			  for(int i = 0 ; i<attrCount ; i++)
				  // copy all attributes to dom element
				  rootElem.setAttribute(attr.getQName(i), attr.getValue(i));
			  try {
				  // set which attribute has ID type
				  rootElem.setIdAttribute("Id", true);
			  } catch (DOMException err) {
				  //err.printStackTrace();
			  }
			  dom.appendChild(rootElem);
			  // push element to stack. Next elements will be children of this element
			  elemParent.push(rootElem);
		  } else if (qName.equals("element")) {
			  Element newElem = dom.createElement(qName);
			  Element newCoords = coords.createElement(qName);
			  for(int i = 0 ; i<attrCount ; i++) {
				  // copy all attributes to dom element
				  // except x and y - copy them to coords DOM
				  String attrName = attr.getQName(i);
				  if (attrName.equals("x") || attrName.equals("y"))
					  newCoords.setAttribute(attrName, attr.getValue(i));
				  else
					  newElem.setAttribute(attrName, attr.getValue(i));
			  }
			  // generate gmfId string. It uses in GMF model links 
			  String gmfId = "/";
			  for (int i = 0; i<=level; i++)
				  gmfId+="/@element." + number[i];
			  // set attribute gmfId to all DOMs
			  newElem.setAttribute("gmfId", gmfId);
			  newCoords.setAttribute("Id", gmfId);
			  try {
				  // set which attribute has ID type
				  newElem.setIdAttribute("Id", true);
				  newCoords.setIdAttribute("Id", true);
			  } catch (DOMException err) {
				  //err.printStackTrace();
			  }
			  // add current element in proper place
			  elemParent.peek().appendChild(newElem);
			  rootCoords.appendChild(newCoords);
			  // element was handled so if any element will be started it'll be child
			  // of this element. Its level'll be more by 1 and current number in level is 0
			  number[++level] = 0;
			  elemParent.push(newElem);
		  } else if (qName.endsWith("link")) {
			  // links handler here
			  Element link = dom.createElement(qName);
			  String sourceId = attr.getValue("source");
			  String targetId = attr.getValue("target");
			  //change source and target attributes to proper value
			  Element sourceElem = dom.getElementById(sourceId);
			  Element targetElem = dom.getElementById(targetId);
			  if (sourceElem == null) {
			  	  link.setAttribute("source", sourceId);
			  }
			  else {
				  link.setAttribute("source", sourceElem.getAttribute("gmfId"));
			  }
			  if (targetElem == null) {
			  	  link.setAttribute("target", targetId);
			  }
			  else {
				  link.setAttribute("target", targetElem.getAttribute("gmfId"));
			  }
			  elemParent.peek().appendChild(link);
		  }*/
	  }

	  // Event while parsing - Element ended
	  @Override
	  public void endElement(String uri, String localName, String qName) throws SAXException
	  {
		  // element tag ended, pop parent element from stack
		  // and set proper level and number values
	/*	  if (qName.equals("element")) {
			  elemParent.pop();
			  number[--level]++;
		  }*/
	  }

	  // Event while parsing - Document ended
	  @Override
	  public void endDocument() throws SAXException
	  {
		  // clear stack
//		  elemParent.clear();
		  System.out.println(changesStr+"\n\n");
	  }
	  
	  public void warning(SAXParseException spe) {
		  System.out.println("Warning at line "+spe.getLineNumber());
		  System.out.println(spe.getMessage());
	  }

	  public void fatalError(SAXParseException spe) throws SAXException {
		  System.out.println("Fatal error at line "+spe.getLineNumber());
		  System.out.println(spe.getMessage());
		  throw spe;
	  }
}
