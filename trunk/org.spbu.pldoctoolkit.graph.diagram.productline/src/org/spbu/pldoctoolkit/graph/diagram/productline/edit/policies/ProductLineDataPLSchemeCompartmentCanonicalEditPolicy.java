package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.PLScheme;
import org.spbu.pldoctoolkit.graph.ProductLine;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.PLSchemeEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;

/**
 * Class ProductLineDataPLSchemeCompartmentCanonicalEditPolicy.
 *
 * @author Alexey Semenov
 * @version 1.0
 */
public class ProductLineDataPLSchemeCompartmentCanonicalEditPolicy extends
		CanonicalEditPolicy implements EditPolicy {

	/**
	 * HAND
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy#getSemanticChildrenList()
	 */
	@Override
	protected List getSemanticChildrenList() {
		List result = new LinkedList();
		EObject modelObject = ((View) getHost().getParent().getModel()).getElement();
		View viewObject = (View) getHost().getModel();
		
		PLScheme scheme = ((ProductLine) modelObject).getScheme();
	    int nodeVID = DrlModelVisualIDRegistry.getNodeVisualID(viewObject, scheme);
		
	    if (PLSchemeEditPart.VISUAL_ID == nodeVID) {
			result.add(scheme);
		}
 
	    return result;
	}

	
	/** 
	 * HAND
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy#getFactoryHint(org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected String getDefaultFactoryHint() {
		return null;
	}
	
}
