package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.core.commands.ExecutionException;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.UnexecutableCommand;

import org.eclipse.gmf.runtime.common.core.command.CommandResult;

import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;

import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;

import org.eclipse.gmf.runtime.notation.Edge;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;

import org.spbu.pldoctoolkit.graph.InfElemRefGroup;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefGroupEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class InfElemRefGroupItemSemanticEditPolicy extends
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
					new DestroyElementRequest(
							((InfElemRefGroupEditPart) getHost())
									.getEditingDomain(), req
									.isConfirmationRequired()),
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

			protected CommandResult doExecuteWithResult(
					IProgressMonitor progressMonitor, IAdaptable info)
					throws ExecutionException {
				EObject eObject = getElementToDestroy();
				boolean removeFromResource = eObject.eContainer() == null;
				CommandResult result = super.doExecuteWithResult(
						progressMonitor, info);
				Resource resource = eObject.eResource();
				if (removeFromResource && resource != null) {
					resource.getContents().remove(eObject);
				}
				return result;
			}

		}));
		return cc;
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (DrlModelElementTypes.GenericDocumentPartGroups_3002 == req
				.getElementType()) {
			return req.getTarget() == null ? null
					: getCreateCompleteIncomingGenericDocumentPartGroups_3002Command(req);
		}
		if (DrlModelElementTypes.InfElemRefGroupInfElemRefsGroup_3003 == req
				.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingInfElemRefGroupInfElemRefsGroup_3003Command(req)
					: null;
		}
		return super.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCompleteIncomingGenericDocumentPartGroups_3002Command(
			CreateRelationshipRequest req) {
		EObject sourceEObject = req.getSource();
		EObject targetEObject = req.getTarget();
		if (false == sourceEObject instanceof GenericDocumentPart
				|| false == targetEObject instanceof InfElemRefGroup) {
			return UnexecutableCommand.INSTANCE;
		}
		GenericDocumentPart source = (GenericDocumentPart) sourceEObject;
		InfElemRefGroup target = (InfElemRefGroup) targetEObject;
		if (!DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateGenericDocumentPartGroups_3002(source, target)) {
			return UnexecutableCommand.INSTANCE;
		}
		SetRequest setReq = new SetRequest(sourceEObject, DrlPackage.eINSTANCE
				.getGenericDocumentPart_Groups(), target);
		return getMSLWrapper(new SetValueCommand(setReq));
	}

	/**
	 * @generated
	 */
	protected Command getCreateStartOutgoingInfElemRefGroupInfElemRefsGroup_3003Command(
			CreateRelationshipRequest req) {
		EObject sourceEObject = req.getSource();
		if (false == sourceEObject instanceof InfElemRefGroup) {
			return UnexecutableCommand.INSTANCE;
		}
		InfElemRefGroup source = (InfElemRefGroup) sourceEObject;
		if (!DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateInfElemRefGroupInfElemRefsGroup_3003(source, null)) {
			return UnexecutableCommand.INSTANCE;
		}
		return new Command() {
		};
	}

	/**
	 * @generated NOT
	 */
	protected Command getCreateCompleteIncomingGenericDocumentPart_Groups3002Command(
			CreateRelationshipRequest req) {
		if (!(req.getSource() instanceof GenericDocumentPart)) {
			return UnexecutableCommand.INSTANCE;
		}

		// a manual single owner restriction
		if (req.getTarget().eContainer() != null) {
			return UnexecutableCommand.INSTANCE;
		}

		GenericDocumentPart element = (GenericDocumentPart) req.getSource();
		if (element.getGroups().contains(req.getTarget())) {
			return UnexecutableCommand.INSTANCE;
		}
		SetRequest setReq = new SetRequest(req.getSource(),
				DrlPackage.eINSTANCE.getGenericDocumentPart_Groups(), req
						.getTarget());
		return getMSLWrapper(new SetValueCommand(setReq));
	}
}
