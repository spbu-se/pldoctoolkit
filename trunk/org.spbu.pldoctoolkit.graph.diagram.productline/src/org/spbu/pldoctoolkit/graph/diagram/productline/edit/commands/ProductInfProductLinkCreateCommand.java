package org.spbu.pldoctoolkit.graph.diagram.productline.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.swt.widgets.Shell;
import org.spbu.pldoctoolkit.graph.InfProduct;
import org.spbu.pldoctoolkit.graph.Product;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies.DrlModelBaseItemSemanticEditPolicy;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelDiagramEditorPlugin;

/**
 * @generated
 */
public class ProductInfProductLinkCreateCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	private final EObject source;

	/**
	 * @generated
	 */
	private final EObject target;

	private Shell shell;
	
	/**
	 * @generated
	 */
	public ProductInfProductLinkCreateCommand(
			Shell shell, CreateRelationshipRequest request, EObject source, EObject target) {
		super(request.getLabel(), null, request);
		
		this.shell = shell;
		this.source = source;
		this.target = target;
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		if (source == null && target == null) {
			return false;
		}
		if (source != null && !(source instanceof Product)) {
			return false;
		}
		if (target != null && !(target instanceof InfProduct)) {
			return false;
		}
		if (getSource() == null) {
			return true; // link creation is in progress; source is not defined yet
		}
		// target may be null here but it's possible to check constraint
		return DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateProductDocumentationFinalInfProducts_3001(
						getSource(), getTarget());
	}

	/**
	 * @generated
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException(
					"Invalid arguments in create link command"); //$NON-NLS-1$
		}
		//TODO implement
//		if (getSource() != null && getTarget() != null) {
//			getSource().getFinalInfProducts().add(getTarget());
//		}
		
		
		
		DrlModelDiagramEditorPlugin.getInstance().logInfo("product link create command executed! source: " + getSource() + ", target: " + getTarget());
		
		NewFinalInfProductWizard.runWizard(shell, new NewFinalInfProductWizard(), "NewFinalInfProductWizard");
		
		return CommandResult.newOKCommandResult();
	}

	/**
	 * @generated NOT
	 */
	protected Product getSource() {
		return (Product) source;
	}

	/**
	 * @generated NOT
	 */
	protected InfProduct getTarget() {
		return (InfProduct) target;
	}
}
