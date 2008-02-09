package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.Product;
import org.spbu.pldoctoolkit.graph.ProductLine;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelDiagramEditorPlugin;

/**
 * @generated
 */
public class PLSchemeProductsCompartmentCanonicalEditPolicy extends
		CanonicalEditPolicy {

	/**
	 * @generated NOT
	 */
	protected List getSemanticChildrenList() {
		//HAND
		
		List result = new LinkedList();
		EObject modelObject = ((View) getHost().getParent().getModel()).getElement();
		View viewObject = (View) getHost().getModel();
		
		for (Iterator productsIter = ((ProductLine) modelObject)
				.getProducts().iterator(); productsIter.hasNext();) {

			Product nextProduct = (Product) productsIter.next();
			result.add(nextProduct);
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
