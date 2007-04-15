package org.spbu.pldoctoolkit.graph.util;

import java.util.Map;

import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.SAXXMIHandler;

public class DrlXMLHandler extends SAXXMIHandler {

	public static final String DOCBOOK_URI = "http://test.com"; //TODO 
	
	public DrlXMLHandler(XMLResource xmiResource, XMLHelper helper,
			Map<?, ?> options) {
		super(xmiResource, helper, options);
	}

	
	/* 
	 * Ignore docbook tags
	 * 
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLHandler#startElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void startElement(String uri, String localName, String name) {
		if(DOCBOOK_URI.equals(uri)) {
			return;
		}
		
		super.startElement(uri, localName, name);
	}

	/* 
	 * Ignore docbook tags
	 * 
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String name) {
		if(DOCBOOK_URI.equals(uri)) {
			return;
		}
		
		super.endElement(uri, localName, name);
	}

}
