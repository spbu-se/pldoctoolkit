/**
 * 
 */
package org.spbu.pldoctoolkit.graph.util;

import java.util.Map;

import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.XMLSaveImpl;
import org.spbu.pldoctoolkit.graph.DrlElement;
import org.spbu.pldoctoolkit.graph.DrlGraphPlugin;

/**
 * @author Alexey Semenov
 *
 */
public class DrlSaveAdapter extends XMLSaveImpl {

	/**
	 * @param options
	 * @param helper
	 * @param encoding
	 * @param xmlVersion
	 */
	public DrlSaveAdapter(Map<?, ?> options, XMLHelper helper, String encoding,
			String xmlVersion) {
		super(options, helper, encoding, xmlVersion);
	}

	/**
	 * @param options
	 * @param helper
	 * @param encoding
	 */
	public DrlSaveAdapter(Map<?, ?> options, XMLHelper helper, String encoding) {
		super(options, helper, encoding);
	}

	/**
	 * @param helper
	 */
	public DrlSaveAdapter(XMLHelper helper) {
		super(helper);
	}


	public void updateAttributes(DrlElement o) {
		toDOM = true;
		currentNode = o.getNode();
		if(currentNode == null) {
			DrlGraphPlugin.logInfo("updateAttributes: current node is null!");
			return;
		}
		
		super.saveElementID(o);
	}

}
