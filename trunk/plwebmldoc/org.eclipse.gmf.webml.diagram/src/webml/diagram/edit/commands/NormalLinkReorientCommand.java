/*
 * 
 */
package webml.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

import webml.ContentUnit;
import webml.Siteview;
import webml.Unit;
import webml.diagram.edit.policies.WebmlBaseItemSemanticEditPolicy;

/**
 * @generated
 */
public class NormalLinkReorientCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	private final int reorientDirection;

	/**
	 * @generated
	 */
	private final EObject oldEnd;

	/**
	 * @generated
	 */
	private final EObject newEnd;

	/**
	 * @generated
	 */
	public NormalLinkReorientCommand(ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		if (false == getElementToEdit() instanceof webml.normalLink) {
			return false;
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) {
			return canReorientSource();
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET) {
			return canReorientTarget();
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean canReorientSource() {
		if (!(oldEnd instanceof ContentUnit && newEnd instanceof ContentUnit)) {
			return false;
		}
		Unit target = getLink().getTarget();
		if (!(getLink().eContainer() instanceof Siteview)) {
			return false;
		}
		Siteview container = (Siteview) getLink().eContainer();
		return WebmlBaseItemSemanticEditPolicy.LinkConstraints
				.canExistNormalLink_4003(container, getNewSource(), target);
	}

	/**
	 * @generated
	 */
	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof Unit && newEnd instanceof Unit)) {
			return false;
		}
		ContentUnit source = getLink().getSource();
		if (!(getLink().eContainer() instanceof Siteview)) {
			return false;
		}
		Siteview container = (Siteview) getLink().eContainer();
		return WebmlBaseItemSemanticEditPolicy.LinkConstraints
				.canExistNormalLink_4003(container, source, getNewTarget());
	}

	/**
	 * @generated
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException(
					"Invalid arguments in reorient link command"); //$NON-NLS-1$
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) {
			return reorientSource();
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET) {
			return reorientTarget();
		}
		throw new IllegalStateException();
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientSource() throws ExecutionException {
		getLink().setSource(getNewSource());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientTarget() throws ExecutionException {
		getLink().setTarget(getNewTarget());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected webml.normalLink getLink() {
		return (webml.normalLink) getElementToEdit();
	}

	/**
	 * @generated
	 */
	protected ContentUnit getOldSource() {
		return (ContentUnit) oldEnd;
	}

	/**
	 * @generated
	 */
	protected ContentUnit getNewSource() {
		return (ContentUnit) newEnd;
	}

	/**
	 * @generated
	 */
	protected Unit getOldTarget() {
		return (Unit) oldEnd;
	}

	/**
	 * @generated
	 */
	protected Unit getNewTarget() {
		return (Unit) newEnd;
	}
}
