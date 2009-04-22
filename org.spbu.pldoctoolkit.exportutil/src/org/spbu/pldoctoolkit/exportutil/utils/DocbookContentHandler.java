package org.spbu.pldoctoolkit.exportutil.utils;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class DocbookContentHandler extends ContentHandlerAdapter {
	private static final String DRL_URI = "http://math.spbu.ru/drl";
	private static final String SYSTEM_ID = "system-id";
	private static final String LINE_NUMBER = "line-number"; 
	
	private String systemId;
	private int lineNumber;
	
	public DocbookContentHandler(ContentHandler handler) {
		super(handler);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		AttributesImpl attributes = new AttributesImpl();
		for (int i = 0; i < atts.getLength(); i++) {
			String attURI = atts.getURI(i);
			String attLocalName = atts.getLocalName(i);
			String attQName = atts.getQName(i);
			String attType = atts.getType(i);
			String attValue = atts.getValue(i);
			if (!DRL_URI.equals(attURI)) {
				attributes.addAttribute(attURI, attLocalName, attQName, attType, attValue);
				continue;
			}
			if (SYSTEM_ID.equals(attLocalName))
				systemId = attValue;
			else if (LINE_NUMBER.equals(attLocalName))
				lineNumber = Integer.valueOf(attValue);
		}
		if (!DRL_URI.equals(uri))
			super.startElement(uri, localName, qName, attributes);
		else if (!"text".equals(localName))
			throw new SAXException("Unexpected DRL element found in document: " + qName);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (!DRL_URI.equals(uri))
			super.endElement(uri, localName, qName);
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getSystemId() {
		return systemId;
	}
}
