package org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElement;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.policies.DrlModelBaseItemSemanticEditPolicy;

/**
 * @generated
 */
public class InfElemRefReorientCommand extends EditElementCommand {

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
	public InfElemRefReorientCommand(ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		if (!(getElementToEdit() instanceof InfElemRef)) {
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
		if (!(oldEnd instanceof GenericDocumentPart && newEnd instanceof GenericDocumentPart)) {
			return false;
		}
		InfElement target = getLink().getInfelem();
		return DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canExistInfElemRef_3001(getNewSource(), target);
	}

	/**
	 * @generated
	 */
	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof InfElement && newEnd instanceof InfElement)) {
			return false;
		}
		if (!(getLink().eContainer() instanceof GenericDocumentPart)) {
			return false;
		}
		GenericDocumentPart source = (GenericDocumentPart) getLink()
				.eContainer();
		return DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canExistInfElemRef_3001(source, getNewTarget());
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
		getOldSource().getInfElemRefs().remove(getLink());
		getNewSource().getInfElemRefs().add(getLink());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientTarget() throws ExecutionException {
		getLink().setInfelem(getNewTarget());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected InfElemRef getLink() {
		return (InfElemRef) getElementToEdit();
	}

	/**
	 * @generated
	 */
	protected GenericDocumentPart getOldSource() {
		return (GenericDocumentPart) oldEnd;
	}

	/**
	 * @generated
	 */
	protected GenericDocumentPart getNewSource() {
		return (GenericDocumentPart) newEnd;
	}

	/**
	 * @generated
	 */
	protected InfElement getOldTarget() {
		return (InfElement) oldEnd;
	}

	/**
	 * @generated
	 */
	protected InfElement getNewTarget() {
		return (InfElement) newEnd;
	}
}
