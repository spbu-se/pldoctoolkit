package org.spbu.pldoctoolkit.graph.util;

import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.XMLLoadImpl;
import org.xml.sax.helpers.DefaultHandler;

public class DrlXMLLoadImpl extends XMLLoadImpl {

	public DrlXMLLoadImpl(XMLHelper helper) {
		super(helper);
	}

	@Override
	protected DefaultHandler makeDefaultHandler() {
		return new DrlXMLHandler(resource, helper, options);
	}

	
}
