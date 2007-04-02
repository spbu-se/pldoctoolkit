package org.spbu.pldoctoolkit.graph.diagram.infproduct.part;

import org.eclipse.core.commands.ExecutionException;

import org.eclipse.core.commands.operations.OperationHistoryFactory;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;

import org.eclipse.gmf.runtime.common.core.command.CommandResult;

import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;

import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;

import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;

import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

import org.eclipse.jface.action.IAction;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import org.eclipse.jface.window.Window;

import org.eclipse.swt.widgets.Shell;

import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.DocumentationCoreEditPart;

/**
 * @generated
 */
public class DrlModelCreateShortcutAction implements IObjectActionDelegate {

	/**
	 * @generated
	 */
	private DocumentationCoreEditPart mySelectedElement;

	/**
	 * @generated
	 */
	private Shell myShell;

	/**
	 * @generated
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		myShell = targetPart.getSite().getShell();
	}

	/**
	 * @generated
	 */
	public void run(IAction action) {
		final View view = (View) mySelectedElement.getModel();
		DrlModelElementChooserDialog elementChooser = new DrlModelElementChooserDialog(
				myShell, view);
		int result = elementChooser.open();
		if (result != Window.OK) {
			return;
		}
		URI selectedModelElementURI = elementChooser
				.getSelectedModelElementURI();
		final EObject selectedElement;
		try {
			selectedElement = mySelectedElement.getEditingDomain()
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
		CreateViewRequest.ViewDescriptor viewDescriptor = new CreateViewRequest.ViewDescriptor(
				new EObjectAdapter(selectedElement), Node.class, null,
				DrlModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
		CreateCommand command = new CreateCommand(mySelectedElement
				.getEditingDomain(), viewDescriptor, view) {

			protected CommandResult doExecuteWithResult(
					IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				CommandResult result = super.doExecuteWithResult(monitor, info);
				View view = (View) ((IAdaptable) result.getReturnValue())
						.getAdapter(View.class);
				if (view != null && view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
					EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE
							.createEAnnotation();
					shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
					shortcutAnnotation.getDetails().put(
							"modelID", DocumentationCoreEditPart.MODEL_ID); //$NON-NLS-1$
					view.getEAnnotations().add(shortcutAnnotation);
				}
				return result;
			}

		};
		try {
			OperationHistoryFactory.getOperationHistory().execute(command,
					new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"Unable to create shortcut", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		mySelectedElement = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() == 1
					&& structuredSelection.getFirstElement() instanceof DocumentationCoreEditPart) {
				mySelectedElement = (DocumentationCoreEditPart) structuredSelection
						.getFirstElement();
			}
		}
		action.setEnabled(isEnabled());
	}

	/**
	 * @generated
	 */
	private boolean isEnabled() {
		return mySelectedElement != null;
	}

}
