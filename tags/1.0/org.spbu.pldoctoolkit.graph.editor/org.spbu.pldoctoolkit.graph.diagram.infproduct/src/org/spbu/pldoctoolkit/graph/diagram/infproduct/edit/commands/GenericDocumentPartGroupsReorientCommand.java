package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.InfElemRefGroup;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelBaseItemSemanticEditPolicy;

/**
 * @generated
 * 
 * XXX this command's execution has been disabled until it is implemented.
 */
public class GenericDocumentPartGroupsReorientCommand extends
		EditElementCommand {

	/**
	 * @generated
	 */
	private final int reorientDirection;

	/**
	 * @generated
	 */
	private final EObject referenceOwner;

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
	public GenericDocumentPartGroupsReorientCommand(
			ReorientReferenceRelationshipRequest request) {
		super(request.getLabel(), null, request);
		reorientDirection = request.getDirection();
		referenceOwner = request.getReferenceOwner();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	/**
	 * @generated NOT
	 */
	public boolean canExecute() {
		//XXX a stub
		if(true) return false;

		if (!(referenceOwner instanceof GenericDocumentPart)) {
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
		if (!(oldEnd instanceof InfElemRefGroup && newEnd instanceof GenericDocumentPart)) {
			return false;
		}
		return DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canExistGenericDocumentPartGroups_3002(getNewSource(),
						getOldTarget());
	}

	/**
	 * @generated
	 */
	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof InfElemRefGroup && newEnd instanceof InfElemRefGroup)) {
			return false;
		}
		return DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canExistGenericDocumentPartGroups_3002(getOldSource(),
						getNewTarget());
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
		getOldSource().getGroups().remove(getOldTarget());
		getNewSource().getGroups().add(getOldTarget());
		return CommandResult.newOKCommandResult(referenceOwner);
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientTarget() throws ExecutionException {
		getOldSource().getGroups().remove(getOldTarget());
		getOldSource().getGroups().add(getNewTarget());
		return CommandResult.newOKCommandResult(referenceOwner);
	}

	/**
	 * @generated
	 */
	protected GenericDocumentPart getOldSource() {
		return (GenericDocumentPart) referenceOwner;
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
	protected InfElemRefGroup getOldTarget() {
		return (InfElemRefGroup) oldEnd;
	}

	/**
	 * @generated
	 */
	protected InfElemRefGroup getNewTarget() {
		return (InfElemRefGroup) newEnd;
	}
}
