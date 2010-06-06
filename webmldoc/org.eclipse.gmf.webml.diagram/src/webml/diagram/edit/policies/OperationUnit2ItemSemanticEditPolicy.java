/*
 * 
 */
package webml.diagram.edit.policies;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

import webml.diagram.edit.commands.KoLinkCreateCommand;
import webml.diagram.edit.commands.KoLinkReorientCommand;
import webml.diagram.edit.commands.NormalLinkCreateCommand;
import webml.diagram.edit.commands.NormalLinkReorientCommand;
import webml.diagram.edit.commands.OkLinkCreateCommand;
import webml.diagram.edit.commands.OkLinkReorientCommand;
import webml.diagram.edit.commands.TransportLinkCreateCommand;
import webml.diagram.edit.commands.TransportLinkReorientCommand;
import webml.diagram.edit.parts.KoLinkEditPart;
import webml.diagram.edit.parts.NormalLinkEditPart;
import webml.diagram.edit.parts.OkLinkEditPart;
import webml.diagram.edit.parts.TransportLinkEditPart;
import webml.diagram.part.WebmlVisualIDRegistry;
import webml.diagram.providers.WebmlElementTypes;

/**
 * @generated
 */
public class OperationUnit2ItemSemanticEditPolicy extends
		WebmlBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public OperationUnit2ItemSemanticEditPolicy() {
		super(WebmlElementTypes.OperationUnit_3004);
	}

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		View view = (View) getHost().getModel();
		CompositeTransactionalCommand cmd = new CompositeTransactionalCommand(
				getEditingDomain(), null);
		cmd.setTransactionNestingEnabled(false);
		for (Iterator it = view.getTargetEdges().iterator(); it.hasNext();) {
			Edge incomingLink = (Edge) it.next();
			if (WebmlVisualIDRegistry.getVisualID(incomingLink) == OkLinkEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(
						incomingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
				continue;
			}
			if (WebmlVisualIDRegistry.getVisualID(incomingLink) == KoLinkEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(
						incomingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
				continue;
			}
			if (WebmlVisualIDRegistry.getVisualID(incomingLink) == NormalLinkEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(
						incomingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
				continue;
			}
			if (WebmlVisualIDRegistry.getVisualID(incomingLink) == TransportLinkEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(
						incomingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
				continue;
			}
		}
		for (Iterator it = view.getSourceEdges().iterator(); it.hasNext();) {
			Edge outgoingLink = (Edge) it.next();
			if (WebmlVisualIDRegistry.getVisualID(outgoingLink) == OkLinkEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(
						outgoingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
			if (WebmlVisualIDRegistry.getVisualID(outgoingLink) == KoLinkEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(
						outgoingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
			if (WebmlVisualIDRegistry.getVisualID(outgoingLink) == NormalLinkEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(
						outgoingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
			if (WebmlVisualIDRegistry.getVisualID(outgoingLink) == TransportLinkEditPart.VISUAL_ID) {
				DestroyElementRequest r = new DestroyElementRequest(
						outgoingLink.getElement(), false);
				cmd.add(new DestroyElementCommand(r));
				cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
				continue;
			}
		}
		EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
		if (annotation == null) {
			// there are indirectly referenced children, need extra commands: false
			addDestroyShortcutsCommand(cmd, view);
			// delete host element
			cmd.add(new DestroyElementCommand(req));
		} else {
			cmd.add(new DeleteCommand(getEditingDomain(), view));
		}
		return getGEFWrapper(cmd.reduce());
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		Command command = req.getTarget() == null ? getStartCreateRelationshipCommand(req)
				: getCompleteCreateRelationshipCommand(req);
		return command != null ? command : super
				.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getStartCreateRelationshipCommand(
			CreateRelationshipRequest req) {
		if (WebmlElementTypes.OkLink_4001 == req.getElementType()) {
			return getGEFWrapper(new OkLinkCreateCommand(req, req.getSource(),
					req.getTarget()));
		}
		if (WebmlElementTypes.KoLink_4002 == req.getElementType()) {
			return getGEFWrapper(new KoLinkCreateCommand(req, req.getSource(),
					req.getTarget()));
		}
		if (WebmlElementTypes.NormalLink_4003 == req.getElementType()) {
			return getGEFWrapper(new NormalLinkCreateCommand(req, req
					.getSource(), req.getTarget()));
		}
		if (WebmlElementTypes.TransportLink_4004 == req.getElementType()) {
			return getGEFWrapper(new TransportLinkCreateCommand(req, req
					.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getCompleteCreateRelationshipCommand(
			CreateRelationshipRequest req) {
		if (WebmlElementTypes.OkLink_4001 == req.getElementType()) {
			return getGEFWrapper(new OkLinkCreateCommand(req, req.getSource(),
					req.getTarget()));
		}
		if (WebmlElementTypes.KoLink_4002 == req.getElementType()) {
			return getGEFWrapper(new KoLinkCreateCommand(req, req.getSource(),
					req.getTarget()));
		}
		if (WebmlElementTypes.NormalLink_4003 == req.getElementType()) {
			return getGEFWrapper(new NormalLinkCreateCommand(req, req
					.getSource(), req.getTarget()));
		}
		if (WebmlElementTypes.TransportLink_4004 == req.getElementType()) {
			return getGEFWrapper(new TransportLinkCreateCommand(req, req
					.getSource(), req.getTarget()));
		}
		return null;
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
		case OkLinkEditPart.VISUAL_ID:
			return getGEFWrapper(new OkLinkReorientCommand(req));
		case KoLinkEditPart.VISUAL_ID:
			return getGEFWrapper(new KoLinkReorientCommand(req));
		case NormalLinkEditPart.VISUAL_ID:
			return getGEFWrapper(new NormalLinkReorientCommand(req));
		case TransportLinkEditPart.VISUAL_ID:
			return getGEFWrapper(new TransportLinkReorientCommand(req));
		}
		return super.getReorientRelationshipCommand(req);
	}

}
