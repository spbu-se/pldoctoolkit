package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.GenericDocumentPartGroupsReorientCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.InfElemRefReorientCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.GenericDocumentPartGroupsEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class InfProductItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

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
					new DestroyElementRequest(((InfProductEditPart) getHost())
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
					: null;
		}
		if (DrlModelElementTypes.GenericDocumentPartGroups_3002 == req
				.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingGenericDocumentPartGroups_3002Command(req)
					: null;
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
}
