package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class ProductLineProductLineDataCompartmentCanonicalEditPolicy extends
		CanonicalEditPolicy {

	/**
	 * @generated NOT
	 */
	protected List getSemanticChildrenList() {
		return new LinkedList();
	}

	/**
	 * @generated
	 */
	protected boolean shouldDeleteView(View view) {
		return view.isSetElement() && view.getElement() != null
				&& view.getElement().eIsProxy();
	}

	/**
	 * @generated
	 */
	protected String getDefaultFactoryHint() {
		return null;
	}

}
