package org.spbu.pldoctoolkit.graph.util;

import java.util.Map;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.SAXXMIHandler;
import org.spbu.pldoctoolkit.graph.DrlElement;
import org.spbu.pldoctoolkit.graph.DrlGraphPlugin;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DrlXMLHandler extends SAXXMIHandler {

	private Node currentNode;
	
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
		if(DrlResourceImpl.DOCBOOK_URI.equals(uri)) {
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
		if(DrlResourceImpl.DOCBOOK_URI.equals(uri)) {
			return;
		}
		
		super.endElement(uri, localName, name);
	}

	/**
	 * @return
	 */
	public Node getCurrentNode() {
		return currentNode;
	}

	/**
	 * @param currentNode
	 */
	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLHandler#createObjectFromFactory(org.eclipse.emf.ecore.EFactory, java.lang.String)
	 * 
	 * TODO override also the new method replacing this deprecated one
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected EObject createObjectFromFactory(EFactory factory, String typeName) {
		DrlGraphPlugin.logInfo("creating object from factory: " + factory + ", type name: " + typeName);
		EObject result = super.createObjectFromFactory(factory, typeName);
		
		if(result instanceof DrlElement) {
			DrlElement drlNode = (DrlElement) result;
			drlNode.setNode((Element) getCurrentNode());
		}
		
		return result;
	}

}
