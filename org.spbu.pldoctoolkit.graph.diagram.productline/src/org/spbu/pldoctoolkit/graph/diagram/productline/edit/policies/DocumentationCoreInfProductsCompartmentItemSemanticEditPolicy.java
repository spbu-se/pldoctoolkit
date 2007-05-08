package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import org.eclipse.gef.commands.Command;

import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;

import org.spbu.pldoctoolkit.graph.DrlPackage;

import org.spbu.pldoctoolkit.graph.diagram.productline.edit.commands.InfProductCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.commands.ProductCreateCommand;

import org.spbu.pldoctoolkit.graph.diagram.productline.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class DocumentationCoreInfProductsCompartmentItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (DrlModelElementTypes.InfProduct_2005 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(DrlPackage.eINSTANCE
						.getDocumentationCore_Parts());
			}
			return getMSLWrapper(new InfProductCreateCommand(req));
		}
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
