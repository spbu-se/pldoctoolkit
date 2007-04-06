package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import org.eclipse.gef.commands.Command;

import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import org.spbu.pldoctoolkit.graph.DrlPackage;

import org.spbu.pldoctoolkit.graph.diagram.productline.edit.commands.ProductCreateCommand;

import org.spbu.pldoctoolkit.graph.diagram.productline.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class PLSchemeProductsCompartmentItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (DrlModelElementTypes.Product_2003 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(DrlPackage.eINSTANCE
						.getPLScheme_Products());
			}
			return getMSLWrapper(new ProductCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}
}
