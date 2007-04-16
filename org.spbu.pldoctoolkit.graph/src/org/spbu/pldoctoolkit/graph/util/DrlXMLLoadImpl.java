package org.spbu.pldoctoolkit.graph.util;

import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.XMLLoadImpl;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public class DrlXMLLoadImpl extends XMLLoadImpl {

	public DrlXMLLoadImpl(XMLHelper helper) {
		super(helper);
	}

	@Override
	protected DefaultHandler makeDefaultHandler() {
		return new DrlXMLHandler(resource, helper, options);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLLoadImpl#traverse(org.w3c.dom.Node, org.eclipse.emf.ecore.xmi.impl.XMLLoadImpl.AttributesProxy, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ext.LexicalHandler)
	 */
	@Override
	protected void traverse(Node node, AttributesProxy attributesProxy,
			DefaultHandler handler, LexicalHandler lexicalHandler)
			throws SAXException {
		((DrlXMLHandler)handler).setCurrentNode(node);
		super.traverse(node, attributesProxy, handler, lexicalHandler);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLLoadImpl#traverseElement(org.w3c.dom.Element, org.eclipse.emf.ecore.xmi.impl.XMLLoadImpl.AttributesProxy, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ext.LexicalHandler)
	 */
	@Override
	protected void traverseElement(Element element,
			AttributesProxy attributesProxy, DefaultHandler handler,
			LexicalHandler lexicalHandler) throws SAXException {
		((DrlXMLHandler)handler).setCurrentNode(element);
		super.traverseElement(element, attributesProxy, handler, lexicalHandler);
	}

	
}
