package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;

public class ProductLineDataItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.SemanticEditPolicy#shouldProceed(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest)
	 */
	@Override
	protected boolean shouldProceed(DestroyRequest destroyRequest) {
		return false;
	}
	
}
