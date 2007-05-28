package org.spbu.pldoctoolkit.graph.diagram.infproduct.part;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.InfElemRefTypeLinkCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

public class DrlAddInfElementAction implements IObjectActionDelegate {

	private GraphicalEditPart mySelectedElement;

	private Shell myShell;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		myShell = targetPart.getSite().getShell();
	}

	public void run(IAction action) {
		final View mySelectedElementView = (View) mySelectedElement.getModel(); 
		final View diagramView = mySelectedElementView.getDiagram();
		final EObject mySelectedElementObject = mySelectedElementView.getElement();
		
		DrlModelElementChooserDialog elementChooser = new DrlModelElementChooserDialog(
				myShell, diagramView);
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
		
		CreateRelationshipRequest createRefRequest = new CreateRelationshipRequest(
				mySelectedElement.getEditingDomain(), 
				mySelectedElementObject, mySelectedElementObject, selectedElement, 
				DrlModelElementTypes.InfElemRef_3001, 
				DrlPackage.eINSTANCE.getGenericDocumentPart_InfElemRefs());
		
		ICommand command = new InfElemRefTypeLinkCreateCommand(createRefRequest);
		
		try {
			OperationHistoryFactory.getOperationHistory().execute(command,
					new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"Unable to create shortcut", e); //$NON-NLS-1$
		}
		
		EditPart diagramEditPart = (EditPart) mySelectedElement.getViewer()
			.getEditPartRegistry().get(diagramView);
		diagramEditPart.refresh();
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		mySelectedElement = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() == 1
					&& structuredSelection.getFirstElement() instanceof GraphicalEditPart) {
				mySelectedElement = (GraphicalEditPart) structuredSelection
						.getFirstElement();
			}
		}
		action.setEnabled(isEnabled());
	}

	private boolean isEnabled() {
		return mySelectedElement != null;
	}

}
