package org.spbu.pldoctoolkit.graph.diagram.productline.part;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.common.ui.URIEditorInput;

/**
 * @generated
 */
public class DrlModelUriEditorInputTester extends PropertyTester {

	/**
	 * @generated
	 */
	public boolean test(Object receiver, String method, Object[] args,
			Object expectedValue) {
		if (false == receiver instanceof URIEditorInput) {
			return false;
		}
		URIEditorInput editorInput = (URIEditorInput) receiver;
		return "productline_diagram".equals(editorInput.getURI().fileExtension()); //$NON-NLS-1$
	}

}
