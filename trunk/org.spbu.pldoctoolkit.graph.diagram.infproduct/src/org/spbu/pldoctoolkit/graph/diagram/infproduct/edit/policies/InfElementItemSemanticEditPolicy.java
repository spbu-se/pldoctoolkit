package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.DrlFactory;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.InfElemRefGroup;
import org.spbu.pldoctoolkit.graph.InfElement;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.GenericDocumentPartGroupsReorientCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.InfElemRef2ReorientCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.InfElemRef2TypeLinkCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.InfElemRefReorientCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.InfElemRefTypeLinkCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.InfElementCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.GenericDocumentPartGroupsEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRef2EditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElementEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class InfElementItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelBaseItemSemanticEditPolicy#getCommand(org.eclipse.gef.Request)
	 */
	@Override
	public Command getCommand(Request request) {
		// TODO Auto-generated method stub
		return super.getCommand(request);
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelBaseItemSemanticEditPolicy#getCreateCommand(org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest)
	 */
//	@Override
//	protected Command getCreateCommand(CreateElementRequest req) {
//		if (DrlModelElementTypes.InfElement_1001 == req.getElementType()) {
//			CompoundCommand cc = new CompoundCommand();
//			CreateRelationshipRequest createRefRequest = null;
//			
//			if (req.getContainmentFeature() == null) {
//				req.setContainmentFeature(DrlPackage.eINSTANCE
//						.getDocumentationCore_Parts());
//			}
//			if(DrlFactory.eINSTANCE.getDrlPackage().getInfElement()
//					.isSuperTypeOf(req.getContainer().eClass())) {
//				InfElement container = (InfElement) req.getContainer();
//				req.setContainer(container.eContainer());
//
//				createRefRequest = new CreateRelationshipRequest(
//						req.getEditingDomain(), container, container, null, 
//						DrlModelElementTypes.InfElemRef_3001, 
//						DrlPackage.eINSTANCE.getGenericDocumentPart_InfElemRefs()) {
//					
//					public EObject getTarget() {
//						return ((CreateElementRequest)getParameter("createRequest"))
//							.getNewElement();
//					}
//				};
//				
//				createRefRequest.setParameter("createRequest", req);
//			}
//			cc.add(getMSLWrapper(new InfElementCreateCommand(req)));
//			if(createRefRequest != null) {
//				cc.add(getMSLWrapper(new InfElemRefTypeLinkCreateCommand(createRefRequest)));
//			}
//			
//			return cc;
//		}
//		return super.getCreateCommand(req);
//	}

	/**
	 * @generated
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
					new DestroyElementRequest(((InfElementEditPart) getHost())
							.getEditingDomain(), req.isConfirmationRequired()),
					Collections.EMPTY_MAP);
			cc.add(nextEditPart.getCommand(editCommandRequest));
		}
		cc.add(getMSLWrapper(new DestroyElementCommand(req) {

			protected EObject getElementToDestroy() {
				View view = (View) getHost().getModel();
				EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
				if (annotation != null) {
					return view;
				}
				return super.getElementToDestroy();
			}

		}));
		return cc;
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (DrlModelElementTypes.InfElemRef_3001 == req.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingInfElemRef_3001Command(req)
					: getCreateCompleteIncomingInfElemRef_3001Command(req);
		}
		if (DrlModelElementTypes.GenericDocumentPartGroups_3002 == req
				.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingGenericDocumentPartGroups_3002Command(req)
					: null;
		}
		if (DrlModelElementTypes.InfElemRef_3003 == req.getElementType()) {
			return req.getTarget() == null ? null
					: getCreateCompleteIncomingInfElemRef_3003Command(req);
		}
		return super.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getCreateStartOutgoingInfElemRef_3001Command(
			CreateRelationshipRequest req) {
		EObject sourceEObject = req.getSource();
		if (false == sourceEObject instanceof GenericDocumentPart) {
			return UnexecutableCommand.INSTANCE;
		}
		GenericDocumentPart source = (GenericDocumentPart) sourceEObject;
		if (!DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateInfElemRef_3001(source, null)) {
			return UnexecutableCommand.INSTANCE;
		}
		return new Command() {
		};
	}

	/**
	 * @generated NOT
	 */
	protected Command getCreateCompleteIncomingInfElemRef_3001Command(
			CreateRelationshipRequest req) {
		EObject sourceEObject = req.getSource();
		EObject targetEObject = req.getTarget();
		if (false == sourceEObject instanceof GenericDocumentPart
				|| false == targetEObject instanceof InfElement) {
			return UnexecutableCommand.INSTANCE;
		}
		GenericDocumentPart source = (GenericDocumentPart) sourceEObject;
		InfElement target = (InfElement) targetEObject;
		if (!DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateInfElemRef_3001(source, target)) {
			return UnexecutableCommand.INSTANCE;
		}
		if (req.getContainmentFeature() == null) {
			req.setContainmentFeature(DrlPackage.eINSTANCE
					.getGenericDocumentPart_InfElemRefs());
		}
		return getMSLWrapper(new InfElemRefTypeLinkCreateCommand(req));
	}

	/**
	 * @generated
	 */
	protected Command getCreateStartOutgoingGenericDocumentPartGroups_3002Command(
			CreateRelationshipRequest req) {
		EObject sourceEObject = req.getSource();
		if (false == sourceEObject instanceof GenericDocumentPart) {
			return UnexecutableCommand.INSTANCE;
		}
		GenericDocumentPart source = (GenericDocumentPart) sourceEObject;
		if (!DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateGenericDocumentPartGroups_3002(source, null)) {
			return UnexecutableCommand.INSTANCE;
		}
		return new Command() {
		};
	}

	/**
	 * @generated
	 */
	protected Command getCreateCompleteIncomingInfElemRef_3003Command(
			CreateRelationshipRequest req) {
		EObject sourceEObject = req.getSource();
		EObject targetEObject = req.getTarget();
		if (false == sourceEObject instanceof InfElemRefGroup
				|| false == targetEObject instanceof InfElement) {
			return UnexecutableCommand.INSTANCE;
		}
		InfElemRefGroup source = (InfElemRefGroup) sourceEObject;
		InfElement target = (InfElement) targetEObject;
		GenericDocumentPart container = (GenericDocumentPart) getRelationshipContainer(
				source, DrlPackage.eINSTANCE.getGenericDocumentPart(), req
						.getElementType());
		if (container == null) {
			return UnexecutableCommand.INSTANCE;
		}
		if (!DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateInfElemRef_3003(container, source, target)) {
			return UnexecutableCommand.INSTANCE;
		}
		if (req.getContainmentFeature() == null) {
			req.setContainmentFeature(DrlPackage.eINSTANCE
					.getGenericDocumentPart_InfElemRefs());
		}
		return getMSLWrapper(new InfElemRef2TypeLinkCreateCommand(req,
				container, source, target));
	}

	/**
	 * Returns command to reorient EClass based link. New link target or source
	 * should be the domain model element associated with this node.
	 * 
	 * @generated
	 */
	protected Command getReorientRelationshipCommand(
			ReorientRelationshipRequest req) {
		switch (getVisualID(req)) {
		case InfElemRefEditPart.VISUAL_ID:
			return getMSLWrapper(new InfElemRefReorientCommand(req));
		case InfElemRef2EditPart.VISUAL_ID:
			return getMSLWrapper(new InfElemRef2ReorientCommand(req));
		}
		return super.getReorientRelationshipCommand(req);
	}

	/**
	 * Returns command to reorient EReference based link. New link target or source
	 * should be the domain model element associated with this node.
	 * 
	 * @generated
	 */
	protected Command getReorientReferenceRelationshipCommand(
			ReorientReferenceRelationshipRequest req) {
		switch (getVisualID(req)) {
		case GenericDocumentPartGroupsEditPart.VISUAL_ID:
			return getMSLWrapper(new GenericDocumentPartGroupsReorientCommand(
					req));
		}
		return super.getReorientReferenceRelationshipCommand(req);
	}

	protected Command getCreateCompleteIncomingInfElemRefGroup_InfElemRefsGroup3003Command(
			CreateRelationshipRequest req) {
		return UnexecutableCommand.INSTANCE;
	}
}
