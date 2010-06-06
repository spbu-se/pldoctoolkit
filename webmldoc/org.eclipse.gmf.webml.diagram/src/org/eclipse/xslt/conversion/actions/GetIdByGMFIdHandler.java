package org.eclipse.xslt.conversion.actions;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import javax.xml.parsers.*;

import org.w3c.dom.*;

import java.util.Stack;

// Parses old webml model and creates extended dom with gmfId
// DOM with links in proper format that can be read in GMF editor
public class GetIdByGMFIdHandler extends DefaultHandler{
	
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
	    return dom;
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

			//create an instance of DOMs
			dom = db.newDocument();
		}catch(Exception err) {
			//err.printStackTrace();
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
			  dom.appendChild(rootElem);
			  // push element to stack. Next elements will be children of this element
			  elemParent.push(rootElem);
		  } else if (qName.equals("element")) {
			  Element newElem = dom.createElement(qName);
			  for(int i = 0 ; i<attrCount ; i++) {
				  // copy all attributes to dom element
				 	  newElem.setAttribute(attr.getQName(i), attr.getValue(i));
			  }
			  // generate gmfId string. It uses in GMF model links 
			  String gmfId = "/";
			  for (int i = 0; i<=level; i++)
				  gmfId+="/@element." + number[i];
			  // set attribute gmfId to all DOMs
			  newElem.setAttribute("gmfId", gmfId);
			  // add current element in proper place
			  elemParent.peek().appendChild(newElem);
			  // element was handled so if any element will be started it'll be child
			  // of this element. Its level'll be more by 1 and current number in level is 0
			  number[++level] = 0;
			  elemParent.push(newElem);
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
}
