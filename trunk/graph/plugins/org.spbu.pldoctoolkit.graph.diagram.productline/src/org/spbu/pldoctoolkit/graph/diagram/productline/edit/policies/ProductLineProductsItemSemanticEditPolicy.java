package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.gef.commands.Command;

import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;

import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import org.eclipse.gmf.runtime.notation.View;

import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.ProductLine;

import org.spbu.pldoctoolkit.graph.diagram.productline.edit.commands.PLSchemeCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.productline.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class ProductLineProductsItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (DrlModelElementTypes.PLScheme_2001 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(DrlPackage.eINSTANCE
						.getProductLine_Scheme());
			}
			return getMSLWrapper(new PLSchemeCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
