package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import org.eclipse.gef.commands.Command;

import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;

/**
 * @generated
 */
public class ProductLineProductLineDataCompartmentItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		return super.getCreateCommand(req);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.SemanticEditPolicy#shouldProceed(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest)
	 */
	@Override
	protected boolean shouldProceed(DestroyRequest destroyRequest) {
		return false;
	}

}
