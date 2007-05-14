package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;

import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import org.eclipse.gmf.runtime.notation.View;

import org.spbu.pldoctoolkit.graph.DrlPackage;

import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.InfElemRefGroupCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.InfElementCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.InfProductCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class DocumentationCoreItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	
	
	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelBaseItemSemanticEditPolicy#getCommand(org.eclipse.gef.Request)
	 */
	@Override
	public Command getCommand(Request request) {
		// TODO Auto-generated method stub
		return super.getCommand(request);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
//		if (DrlModelElementTypes.InfElement_1001 == req.getElementType()) {
//			if (req.getContainmentFeature() == null) {
//				req.setContainmentFeature(DrlPackage.eINSTANCE
//						.getDocumentationCore_Parts());
//			}
//			return getMSLWrapper(new InfElementCreateCommand(req));
//		}
//		if (DrlModelElementTypes.InfProduct_1002 == req.getElementType()) {
//			if (req.getContainmentFeature() == null) {
//				req.setContainmentFeature(DrlPackage.eINSTANCE
//						.getDocumentationCore_Parts());
//			}
//			return getMSLWrapper(new InfProductCreateCommand(req));
//		}
//		if (DrlModelElementTypes.InfElemRefGroup_1003 == req.getElementType()) {
//			return getMSLWrapper(new InfElemRefGroupCreateCommand(req));
//		}
		return super.getCreateCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getDuplicateCommand(DuplicateElementsRequest req) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
				.getEditingDomain();
		return getMSLWrapper(new DuplicateAnythingCommand(editingDomain, req));
	}

	/**
	 * @generated
	 */
	private static class DuplicateAnythingCommand extends
			DuplicateEObjectsCommand {

		/**
		 * @generated
		 */
		public DuplicateAnythingCommand(
				TransactionalEditingDomain editingDomain,
				DuplicateElementsRequest req) {
			super(editingDomain, req.getLabel(), req
					.getElementsToBeDuplicated(), req
					.getAllDuplicatedElementsMap());
		}
	}
}
