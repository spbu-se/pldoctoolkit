package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import org.spbu.pldoctoolkit.graph.ProductLine;

import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.PLSchemeEditPart;

import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;

/**
 * @generated
 */
public class ProductLineProductsCanonicalEditPolicy extends CanonicalEditPolicy {

	/**
	 * @generated
	 */
	protected List getSemanticChildrenList() {
		List result = new LinkedList();
		EObject modelObject = ((View) getHost().getModel()).getElement();
		View viewObject = (View) getHost().getModel();
		EObject nextValue;
		int nodeVID;
		nextValue = ((ProductLine) modelObject).getScheme();
		nodeVID = DrlModelVisualIDRegistry.getNodeVisualID(viewObject,
				nextValue);
		if (PLSchemeEditPart.VISUAL_ID == nodeVID) {
			result.add(nextValue);
		}
		return result;
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
