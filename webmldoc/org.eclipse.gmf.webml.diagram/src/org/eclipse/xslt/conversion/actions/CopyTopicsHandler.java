package org.eclipse.xslt.conversion.actions;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import javax.xml.parsers.*;
import java.util.Stack;

import org.w3c.dom.*;

// class for parsing old Webml diagrams and returning topics only
public class CopyTopicsHandler extends DefaultHandler{
	
	  private Document dom;
	  private Element rootElem;
	  private Stack<String> parentId = new Stack<String>();
	  
	  // returns constructed DOM that consists of topics only
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

			//create an instance of input DOM with topics
			dom = db.newDocument();
			
		}catch(Exception err) {
			//err.printStackTrace();
		}
	  }

	  // Event while parsing - new element started
	  public void startElement(String uri, String localName, String qName, Attributes attr) throws SAXException
	  {
		  int attrCount = attr.getLength();
		  if (qName.equals("webml:Siteview")) {
			  //create the root element 
			  rootElem = dom.createElement("root");
			  dom.appendChild(rootElem);
		  } else if (qName.equals("element")) {
			  parentId.push(attr.getValue("Id"));
		  } else if (qName.equals("topic")){
			  // topic handler here
			  Element topicElem = dom.createElement(qName);
			  rootElem.appendChild(topicElem);
			  for(int i = 0 ; i<attrCount ; i++)
				  // copy all attributes to dom element topic
				  topicElem.setAttribute(attr.getQName(i), attr.getValue(i));
			  topicElem.setAttribute("parent", parentId.peek());
		  }
	  }
	  
	  // Event while parsing - element ended
	  @Override
	  public void endElement(String uri, String localName, String qName) throws SAXException
	  {
		  // element tag ended, pop parent element from stack
		  if (qName.equals("element")) {
			  parentId.pop();
		  }
	  }

	  // Event while parsing - document ended
	  @Override
	  public void endDocument() throws SAXException
	  {
		  // clear stack
		  parentId.clear();
	  }

}
