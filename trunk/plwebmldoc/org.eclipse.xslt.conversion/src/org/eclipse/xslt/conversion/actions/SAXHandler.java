package org.eclipse.xslt.conversion.actions;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.util.Stack;

// class for parsing webml model with links formatted by id and returning
// DOM with links in proper format that can be read in GMF editor
public class SAXHandler extends DefaultHandler{
	
	  private Document dom; // output dom with proper links
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
		//get an instance of factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//get an instance of builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//create an instance of DOM
			dom = db.newDocument();
			
		}catch(Exception err) {
			err.printStackTrace();
		}
	  }

	  // Event while parsing - new Element started
	  public void startElement(String uri, String localName, String qName, Attributes attr) throws SAXException
	  {
		  int attrCount = attr.getLength();
		  if (qName.equals("webml:Siteview")) {
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
			  for(int i = 0 ; i<attrCount ; i++)
				  // copy all attributes to dom element
				  newElem.setAttribute(attr.getQName(i), attr.getValue(i));
			  try {
				  // set which attribute has ID type
				  newElem.setIdAttribute("Id", true);
			  } catch (DOMException err) {
				  //err.printStackTrace();
			  }
			  // generate gmfId string. It uses in GMF model links 
			  String gmfId = "/";
			  for (int i = 0; i<=level; i++)
				  gmfId+="/@element." + number[i];
			  // set attribute gmfId
			  newElem.setAttribute("gmfId", gmfId);
			  // add current element in proper place
			  elemParent.peek().appendChild(newElem);
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
		  }
	  }

	  // Event while parsing - Element ended
	  @Override
	  public void endElement(String uri, String localName, String qName) throws SAXException
	  {
		  // element tag ended, pop parent element from stack
		  // and set proper level and number values
		  if (qName.equals("element")) {
			  elemParent.pop();
			  number[--level]++;
		  }
	  }

	  // Event while parsing - Document ended
	  @Override
	  public void endDocument() throws SAXException
	  {
		  // clear stack
		  elemParent.clear();
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
