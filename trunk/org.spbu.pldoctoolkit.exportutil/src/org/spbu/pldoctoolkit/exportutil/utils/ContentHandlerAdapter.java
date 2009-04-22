package org.spbu.pldoctoolkit.exportutil.utils;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public abstract class ContentHandlerAdapter implements ContentHandler {
	protected final ContentHandler handler;
	
	public ContentHandlerAdapter(ContentHandler handler) {
		if (handler == null)
			throw new NullPointerException("handler cannot be null");
		this.handler = handler;
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		handler.characters(ch, start, length);
	}

	public void endDocument() throws SAXException {
		handler.endDocument();
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		handler.endElement(uri, localName, qName);
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		handler.endPrefixMapping(prefix);
	}

	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		handler.ignorableWhitespace(ch, start, length);
	}

	public void processingInstruction(String target, String data) throws SAXException {
		handler.processingInstruction(target, data);
	}

	public void setDocumentLocator(Locator locator) {
		handler.setDocumentLocator(locator);
	}

	public void skippedEntity(String name) throws SAXException {
		handler.skippedEntity(name);
	}

	public void startDocument() throws SAXException {
		handler.startDocument();
	}

	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		handler.startElement(uri, localName, qName, atts);
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		handler.startPrefixMapping(prefix, uri);
	}
}
