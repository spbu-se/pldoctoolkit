package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.Product;
import org.spbu.pldoctoolkit.graph.command.diagram.DrlElementDestroyCommand;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.commands.ProductCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class ProductItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	/**
	 * @generated NOT
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		CompoundCommand cc = new CompoundCommand();
		Collection allEdges = new ArrayList();
		View view = (View) getHost().getModel();
		allEdges.addAll(view.getSourceEdges());
		allEdges.addAll(view.getTargetEdges());
		for (Iterator it = allEdges.iterator(); it.hasNext();) {
			Edge nextEdge = (Edge) it.next();
			EditPart nextEditPart = (EditPart) getHost().getViewer()
					.getEditPartRegistry().get(nextEdge);
			EditCommandRequestWrapper editCommandRequest = new EditCommandRequestWrapper(
					new DestroyElementRequest(((ProductEditPart) getHost())
							.getEditingDomain(), req.isConfirmationRequired()),
					Collections.EMPTY_MAP);
			cc.add(nextEditPart.getCommand(editCommandRequest));
		}
		cc.add(getMSLWrapper(new DrlElementDestroyCommand(req)));
		return cc;
	}

	@Override
	protected Command getCreateCommand(CreateElementRequest req) {
		if (DrlModelElementTypes.Product_2003 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(DrlPackage.eINSTANCE
						.getProductLine_Products());
			}
			return getMSLWrapper(new ProductCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

	@Override
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		
		if (DrlModelElementTypes.ProductDocumentationFinalInfProducts_3001 == req.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingProductDocumentationFinalInfProducts_3001Command(req)
					: null;
		}

		return super.getCreateRelationshipCommand(req);
	}
	
	protected Command getCreateStartOutgoingProductDocumentationFinalInfProducts_3001Command(
			CreateRelationshipRequest req) {
		EObject sourceEObject = req.getSource();
		if (false == sourceEObject instanceof Product) {
			return UnexecutableCommand.INSTANCE;
		}
		Product source = (Product) sourceEObject;
		if (!DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateProductDocumentationFinalInfProducts_3001(source, null)) {
			return UnexecutableCommand.INSTANCE;
		}
		return new Command() {
		};
	}
	
}
