package plweb.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

import plweb.SourceRefElement;
import plweb.TargetRefElement;
import plweb.diagram.edit.policies.PlwebBaseItemSemanticEditPolicy;

/**
 * @generated
 */
public class SourceRefElementClassReorientCommand extends EditElementCommand {

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
	public SourceRefElementClassReorientCommand(
			ReorientReferenceRelationshipRequest request) {
		super(request.getLabel(), null, request);
		reorientDirection = request.getDirection();
		referenceOwner = request.getReferenceOwner();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		if (false == referenceOwner instanceof SourceRefElement) {
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
		if (!(oldEnd instanceof TargetRefElement && newEnd instanceof SourceRefElement)) {
			return false;
		}
		return PlwebBaseItemSemanticEditPolicy.LinkConstraints
				.canExistSourceRefElementClass_4001(getNewSource(),
						getOldTarget());
	}

	/**
	 * @generated NOT
	 */
	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof TargetRefElement && newEnd instanceof TargetRefElement)) {
			return false;
		}
		if (newEnd != null && ((TargetRefElement) newEnd).isSetParent()) {
			return false;
		}
		return PlwebBaseItemSemanticEditPolicy.LinkConstraints
				.canExistSourceRefElementClass_4001(getOldSource(),
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
	 * @generated NOT
	 */
	protected CommandResult reorientSource() throws ExecutionException {
		getOldSource().getClass_().remove(getOldTarget());
		getNewSource().getClass_().add(getOldTarget());
		getOldTarget().setParent(getNewSource());
		return CommandResult.newOKCommandResult(referenceOwner);
	}

	/**
	 * @generated NOT
	 */
	protected CommandResult reorientTarget() throws ExecutionException {
		getOldSource().getClass_().remove(getOldTarget());
		getOldSource().getClass_().add(getNewTarget());
		getOldTarget().unsetParent();
		getNewTarget().setParent(getOldSource());
		return CommandResult.newOKCommandResult(referenceOwner);
	}

	/**
	 * @generated
	 */
	protected SourceRefElement getOldSource() {
		return (SourceRefElement) referenceOwner;
	}

	/**
	 * @generated
	 */
	protected SourceRefElement getNewSource() {
		return (SourceRefElement) newEnd;
	}

	/**
	 * @generated
	 */
	protected TargetRefElement getOldTarget() {
		return (TargetRefElement) oldEnd;
	}

	/**
	 * @generated
	 */
	protected TargetRefElement getNewTarget() {
		return (TargetRefElement) newEnd;
	}
}
