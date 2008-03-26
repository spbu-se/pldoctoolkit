package org.spbu.pldoctoolkit.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import org.xml.sax.InputSource;

import org.spbu.pldoctoolkit.parser.DRLLang.Comment;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.refactor.Util;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class DRLContentHandler implements ContentHandler {
	private Locator locator = null;	
	private Stack<Element> elemStack = new Stack<Element>();
	private ProjectContent projectContent;
	private PositionInText prevPos = null;
	private int prevOffset = 0;
	private HashMap<String, String> prefex = new HashMap<String, String>();
	private boolean isFirstTag = true;
	
	public DRLDocument doc = null;
	
//	public InputSource input;	
	public StringBuffer inputText;
/*	
	public DRLContentHandler(InputSource input) {
		this.input = input;
	}
*/	
	public void setProject(ProjectContent project) {
		projectContent = project;
	}
	
	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		int endLine = locator.getLineNumber();
		int endCol = locator.getColumnNumber();
		int endOffset = Util.getOffset(inputText, endLine, endCol, prevPos.line, prevPos.column, prevOffset);
		while (true) {
			if (inputText.charAt(endOffset) != '<') {
				endCol -= 1;
				endOffset -= 1;
			}
			else
				break;
		}
		
		if (prevOffset < endOffset - arg2) {
			createComment(endOffset - arg2 - prevOffset);			
		}
		else if (prevOffset > endOffset - arg2) {
			int a = 3;
		}
				
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
		
		savePosition(elem.getEndPos().line, elem.getEndPos().column);		
	}

	@Override
	public void endDocument() throws SAXException {
		Element elem = elemStack.pop();
		assert(elem instanceof DRLDocument);
	}

	@Override
	public void endElement(String arg0, String arg1, String arg2) throws SAXException {
		int line = locator.getLineNumber();
		int column = locator.getColumnNumber();
		
		if (line != prevPos.line || column != prevPos.column) {
			int endOffset = Util.getOffset(inputText, line, column, prevPos.line, prevPos.column, prevOffset);
			int tagStartPos = Util.findtagStartPos(inputText, endOffset);		
			if (tagStartPos != prevOffset) {
				createComment(tagStartPos - prevOffset);			
			}
		}
		
		Element elem = elemStack.pop();
		
		assert( ((LangElem)elem).tagNS == arg2 );
		
//		elem.setEndLine(locator.getLineNumber());
//		elem.setEndColumn(locator.getColumnNumber());		
		elem.setEndPos(new PositionInText(line, column));
		
		savePosition(line, column);
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
		prevOffset = Util.getOffset(inputText, prevPos.line, prevPos.column);
		elemStack.push(doc);
		
		isFirstTag = true;
	}

	@Override
	public void startElement(String arg0, String arg1, String arg2,	Attributes arg3) throws SAXException {		 
		int line = locator.getLineNumber(); 
		int column = locator.getColumnNumber();
		
		if (isFirstTag) {
			assert (inputText != null);
			isFirstTag = false;			
			
			int off = Util.getOffset(inputText, line, column);
			int tagStartPos = Util.findtagStartPos(inputText, off);
			
			//try 
			{
				doc.preface = inputText.substring(0, tagStartPos);//.read(buf, 0, tagStartPos);
				//doc.preface = String.valueOf(buf);
			}/*
			catch (IOException e){
				e.printStackTrace();
			}*/			
		}		
		else {
			int endOffset = Util.getOffset(inputText, line, column, prevPos.line, prevPos.column, prevOffset);
			int tagStartPos = Util.findtagStartPos(inputText, endOffset);		
			if (tagStartPos != prevOffset) {
				createComment(tagStartPos - prevOffset);			
			}
		}
		
		Element parent = elemStack.lastElement();
		
		if (parent.getChilds() == null) {
			parent.setChilds(new ArrayList<Element>());
		}
		
		Element newElem = new LangElem(arg1, arg2, new PositionInText(line, column), parent, doc, arg3);
		if (!prefex.isEmpty()) {
			((LangElem)newElem).prefex = new HashMap<String, String>(prefex);
			prefex.clear();
		}
		newElem.setTagStartPos(new PositionInText(prevPos));		
		elemStack.push(newElem);
		parent.getChilds().add(newElem);
		
		savePosition(line, column);
		
		if (arg1.equals("Adapter"))
			projectContent.Adapters.add( (LangElem)newElem );
		else if (arg1.equals("InfElemRef"))
			projectContent.InfElemRefs.add((LangElem)newElem);
	}

	@Override
	public void startPrefixMapping(String arg0, String arg1) throws SAXException {
		prefex.put(arg0, arg1);
		if (arg1 == Constants.DRLns)
			doc.DRLnsPrefix = arg0;
	}
	
	private void savePosition(int line, int col) {
		prevOffset = Util.getOffset(inputText, line, col, prevPos.line, prevPos.column, prevOffset);
		prevPos.line = line;
		prevPos.column = col;		
	}
	
	private void createComment(int length) {
		Element parent = elemStack.lastElement();
		
		if (parent.getChilds() == null) {
			parent.setChilds(new ArrayList<Element>());
		}
		
		char buf[] = new char[length];
		inputText.getChars(prevOffset, prevOffset + length, buf, 0);
		String text = String.valueOf(buf);
		Element elem = new Comment(new PositionInText(prevPos.line, prevPos.column), 
				   									      length, text,  
				   									      parent, doc); 
		parent.getChilds().add(elem);		
		
		//assert(elem.getEndPos().line == locator.getLineNumber());
		//assert(elem.getEndPos().column == locator.getColumnNumber());
		
		savePosition(elem.getEndPos().line, elem.getEndPos().column);
	}
}
