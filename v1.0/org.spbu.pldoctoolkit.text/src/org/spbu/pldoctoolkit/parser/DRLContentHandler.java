package org.spbu.pldoctoolkit.parser;

import java.util.ArrayList;
import java.util.Stack;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class DRLContentHandler implements ContentHandler {
	private Locator locator = null;	
	private Stack<Element> elemStack = new Stack<Element>();
	private ProjectContent projectContent;
	private PositionInText prevPos = null;
	
	public DRLDocument doc = null;
	
	public void setProject(ProjectContent project) {
		projectContent = project;
	}
	
	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		Element parent = elemStack.lastElement();
		
		if (parent.getChilds() == null) {
			parent.setChilds(new ArrayList<Element>());
		}
		
		String text = new String(arg0, arg1, arg2);
		Element elem = new TextElement(new PositionInText(prevPos.line, prevPos.column), 
				   									      arg2, text,  
				   									      parent, doc); 
		parent.getChilds().add(elem);
		
		
		assert(elem.getEndPos().line == locator.getLineNumber());
		assert(elem.getEndPos().column == locator.getColumnNumber());
		
		prevPos.line = elem.getEndPos().line;
		prevPos.column = elem.getEndPos().column;
	}

	@Override
	public void endDocument() throws SAXException {
		Element elem = elemStack.pop();
		assert(elem instanceof DRLDocument);
	}

	@Override
	public void endElement(String arg0, String arg1, String arg2) throws SAXException {
		Element elem = elemStack.pop();
		
		assert( ((LangElem)elem).tagNS == arg2 );
		
//		elem.setEndLine(locator.getLineNumber());
//		elem.setEndColumn(locator.getColumnNumber());
		int line = locator.getLineNumber();
		int column = locator.getColumnNumber();
		elem.setEndPos(new PositionInText(line, column));
		
		prevPos.line = line;
		prevPos.column = column;
	}

	@Override
	public void endPrefixMapping(String arg0) throws SAXException {
	}

	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {		
	}

	@Override
	public void processingInstruction(String arg0, String arg1)	throws SAXException {
	}

	@Override
	public void setDocumentLocator(Locator arg0) {
		this.locator = arg0;
	}

	@Override
	public void skippedEntity(String arg0) throws SAXException {
	}

	@Override
	public void startDocument() throws SAXException {
		doc = new DRLDocument(locator.getLineNumber(), locator.getColumnNumber());
		prevPos = new PositionInText(locator.getLineNumber(), locator.getColumnNumber());
		elemStack.push(doc);		
	}

	@Override
	public void startElement(String arg0, String arg1, String arg2,	Attributes arg3) throws SAXException {
		int line = locator.getLineNumber(); 
		int column = locator.getColumnNumber();
		Element parent = elemStack.lastElement();
		
		if (parent.getChilds() == null) {
			parent.setChilds(new ArrayList<Element>());
		}
		
		Element newElem = new LangElem(arg1, arg2, new PositionInText(line, column), parent, doc, arg3);
		newElem.setTagStartPos(new PositionInText(prevPos));		
		elemStack.push(newElem);
		parent.getChilds().add(newElem);
		
		prevPos.line = line;
		prevPos.column = column;
		
		if (arg1.equals("Adapter"))
			projectContent.Adapters.add( (LangElem)newElem );
		else if (arg1.equals("InfElemRef"))
			projectContent.InfElemRefs.add((LangElem)newElem);
	}

	@Override
	public void startPrefixMapping(String arg0, String arg1) throws SAXException {
	}
}
