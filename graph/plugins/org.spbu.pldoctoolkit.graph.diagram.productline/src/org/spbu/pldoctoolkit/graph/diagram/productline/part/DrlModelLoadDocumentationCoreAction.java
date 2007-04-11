package org.spbu.pldoctoolkit.graph.diagram.productline.part;

import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.spbu.pldoctoolkit.graph.DocumentationCore;
import org.spbu.pldoctoolkit.graph.ProductLine;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLine2EditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineEditPart;

/**
 * HAND
 * 
 * Action to load the resource into the editor's EditingDomain.
 * 
 * Not used anymore.
 */
public class DrlModelLoadDocumentationCoreAction implements IObjectActionDelegate {

	private ProductLineEditPart mySelectedElement;

	private Shell myShell;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		myShell = targetPart.getSite().getShell();
	}

	public void run(IAction action) {
		final View view = (View)mySelectedElement.getModel();
		DrlModelElementChooserDialog elementChooser = new DrlModelElementChooserDialog(myShell, view);
		int result = elementChooser.open();
		if (result != Window.OK) {
			return;
		}
		URI selectedModelElementURI = elementChooser.getSelectedModelElementURI();
		final DocumentationCore selectedElement;
		try {
			selectedElement = (DocumentationCore) mySelectedElement.getEditingDomain()
					.getResourceSet().getEObject(selectedModelElementURI, true);
		} catch (WrappedException e) {
			DrlModelDiagramEditorPlugin
					.getInstance()
					.logError(
							"Exception while loading object: " + selectedModelElementURI.toString(), e); //$NON-NLS-1$
			return;
		}

		if (selectedElement == null) {
			return;
		}
		
		//HAND starts here
		final ProductLine2EditPart productLineView = (ProductLine2EditPart) mySelectedElement.getChildBySemanticHint(
				Integer.toString(ProductLine2EditPart.VISUAL_ID)); 
		final ProductLine pline = (ProductLine)((View) productLineView.getModel()).getElement();

		//XXX
		DrlModelDiagramEditorPlugin.getInstance().logInfo("added");

		TransactionalEditingDomain editingDomain = productLineView.getEditingDomain();
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(
				editingDomain,
				"Adding Documentation Core element", Collections.EMPTY_LIST) {

			@Override
			protected CommandResult doExecuteWithResult(
					IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				pline.getDocumentationCores().add(selectedElement);
				return CommandResult.newOKCommandResult();
			}
			
		};
		
		try {
			OperationHistoryFactory.getOperationHistory().execute(command,
					new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"Unable to create model and diagram", e); //$NON-NLS-1$
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		mySelectedElement = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() == 1
					&& structuredSelection.getFirstElement() instanceof ProductLineEditPart) {
				mySelectedElement = (ProductLineEditPart) structuredSelection
						.getFirstElement();
			}
		}
		action.setEnabled(isEnabled());
	}

	private boolean isEnabled() {
		return mySelectedElement != null;
	}
}
