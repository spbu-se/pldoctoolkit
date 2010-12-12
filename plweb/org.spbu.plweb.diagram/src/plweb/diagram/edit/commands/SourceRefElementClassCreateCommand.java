package plweb.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

import plweb.SourceRefElement;
import plweb.TargetRefElement;
import plweb.diagram.edit.policies.PlwebBaseItemSemanticEditPolicy;

/**
 * @generated
 */
public class SourceRefElementClassCreateCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	private final EObject source;

	/**
	 * @generated
	 */
	private final EObject target;

	/**
	 * @generated
	 */
	public SourceRefElementClassCreateCommand(
			CreateRelationshipRequest request, EObject source, EObject target) {
		super(request.getLabel(), null, request);
		this.source = source;
		this.target = target;
	}

	/**
	 * @generated NOT
	 */
	public boolean canExecute() {
		if (source == null && target == null) {
			return false;
		}
		if (source != null && false == source instanceof SourceRefElement) {
			return false;
		}
		if (target != null && false == target instanceof TargetRefElement) {
			return false;
		}
		if (target != null && ((TargetRefElement) target).isSetParent()
				&& !((TargetRefElement) target).getParent().equals(getSource())) {
			return false;
		}
		if (getSource() == null) {
			return true; // link creation is in progress; source is not defined
							// yet
		}
		// target may be null here but it's possible to check constraint
		return PlwebBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateSourceRefElementClass_4001(getSource(), getTarget());
	}

	/**
	 * @generated NOT
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException(
					"Invalid arguments in create link command"); //$NON-NLS-1$
		}

		if (getSource() != null && getTarget() != null) {
			getSource().getClass_().add(getTarget());
			getTarget().setParent(getSource());
		}
		return CommandResult.newOKCommandResult();

	}

	/**
	 * @generated
	 */
	protected void setElementToEdit(EObject element) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @generated
	 */
	protected SourceRefElement getSource() {
		return (SourceRefElement) source;
	}

	/**
	 * @generated
	 */
	protected TargetRefElement getTarget() {
		return (TargetRefElement) target;
	}
}
