package org.spbu.pldoctoolkit.graph.diagram.productline.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.spbu.pldoctoolkit.graph.FinalInfProduct;
import org.spbu.pldoctoolkit.graph.InfProduct;
import org.spbu.pldoctoolkit.graph.Product;
import org.spbu.pldoctoolkit.graph.ProductDocumentation;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies.DrlModelBaseItemSemanticEditPolicy;

/**
 * @generated
 */
public class ProductInfProductLinkReorientCommand extends EditElementCommand {

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
	public ProductInfProductLinkReorientCommand(
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
		if (!(referenceOwner instanceof ProductDocumentation)) {
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
		if (!(oldEnd instanceof FinalInfProduct && newEnd instanceof ProductDocumentation)) {
			return false;
		}
		return DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canExistProductDocumentationFinalInfProducts_3001(
						getNewSource(), getOldTarget());
	}

	/**
	 * @generated
	 */
	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof FinalInfProduct && newEnd instanceof FinalInfProduct)) {
			return false;
		}
		return DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canExistProductDocumentationFinalInfProducts_3001(
						getOldSource(), getNewTarget());
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
		//TODO implement
//		getOldSource().getFinalInfProducts().remove(getOldTarget());
//		getNewSource().getFinalInfProducts().add(getOldTarget());
		return CommandResult.newOKCommandResult(referenceOwner);
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientTarget() throws ExecutionException {
		//TODO implement
//		getOldSource().getFinalInfProducts().remove(getOldTarget());
//		getOldSource().getFinalInfProducts().add(getNewTarget());
		return CommandResult.newOKCommandResult(referenceOwner);
	}

	/**
	 * @generated NOT
	 */
	protected Product getOldSource() {
		return (Product) referenceOwner;
	}

	/**
	 * @generated NOT
	 */
	protected Product getNewSource() {
		return (Product) newEnd;
	}

	/**
	 * @generated NOT
	 */
	protected InfProduct getOldTarget() {
		return (InfProduct) oldEnd;
	}

	/**
	 * @generated NOT
	 */
	protected InfProduct getNewTarget() {
		return (InfProduct) newEnd;
	}
}
