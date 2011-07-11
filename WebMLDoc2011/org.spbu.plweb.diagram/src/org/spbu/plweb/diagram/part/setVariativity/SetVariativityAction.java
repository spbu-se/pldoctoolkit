package org.spbu.plweb.diagram.part.setVariativity;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

import org.spbu.plweb.DiagramRoot;
import org.spbu.plweb.DiagramType;
import org.spbu.plweb.diagram.part.PlwebDiagramEditorUtil;
import org.spbu.plweb.diagram.util.PathHelper;

public class SetVariativityAction implements IObjectActionDelegate {

	private SetVariativityActionData data = new SetVariativityActionData();

	public Shell getShell() {
		return data.targetPart.getSite().getShell();
	}

	@Override
	public void run(IAction action) {
		SetVariativityWizard wizard = new SetVariativityWizard();
		wizard.setWindowTitle("Set variativy");
		wizard.setNeedsProgressMonitor(true);
		PlwebDiagramEditorUtil.runSetVariativityWizard(getShell(), wizard,
				"SetVariativity", data.selectedElement, data.targetPart);

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		data.selectedElement = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.getFirstElement() instanceof ShapeNodeEditPart) {
				data.selectedElement = (ShapeNodeEditPart) structuredSelection
						.getFirstElement();
			}
		}
	}

	@Override
	public void setActivePart(IAction arg0, IWorkbenchPart targetPart) {
		this.data.targetPart = targetPart;
	}

}
