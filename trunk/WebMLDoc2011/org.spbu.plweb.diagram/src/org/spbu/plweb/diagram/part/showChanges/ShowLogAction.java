package org.spbu.plweb.diagram.part.showChanges;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.spbu.plweb.DiagramRoot;
import org.spbu.plweb.DiagramType;
import org.spbu.plweb.diagram.part.Messages;
import org.spbu.plweb.diagram.part.PlwebDiagramEditorPlugin;
import org.spbu.plweb.diagram.part.PlwebDiagramEditorUtil;
import org.spbu.plweb.diagram.part.addProduct.PlwebAddProductWizard;
import org.spbu.plweb.diagram.util.PathHelper;

public class ShowLogAction implements IObjectActionDelegate {

	private IWorkbenchPart targetPart;

	private URI domainModelURI;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		domainModelURI = null;
		action.setEnabled(false);
		if (selection instanceof IStructuredSelection == false || selection.isEmpty()) {
			return;
		}
		IFile file = (IFile) ((IStructuredSelection) selection).getFirstElement();
		domainModelURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		System.out.println(file.getFullPath().toString() + " юссссссссссссссссссс!!!");
		action.setEnabled(true);
	}

	private Shell getShell() {
		return targetPart.getSite().getShell();
	}
	
	private DiagramRoot getElement(URI uri, TransactionalEditingDomain editingDomain) {
		ResourceSet resourceSet = editingDomain.getResourceSet();
		EObject element = null;
		try {
			Resource resource = resourceSet.getResource(uri, true);
			element = (EObject) resource.getContents().get(0);
		} catch (WrappedException ex) {
			PlwebDiagramEditorPlugin.getInstance().logError("Unable to load resource: " + uri, ex); //$NON-NLS-1$
		}
		if (element == null || !(element instanceof DiagramRoot)) {
			MessageDialog.openError(
					getShell(),
					Messages.AddProduct_ResourceErrorDialogTitle,
					Messages.AddProduct_ResourceErrorDialogMessage
			);
			return null;
		}
		DiagramRoot diagramRoot = (DiagramRoot) element;
		return diagramRoot;
	}


	public void run(IAction action) {
		try {
			TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE.createEditingDomain();
			
			DiagramRoot diagramRoot = getElement(domainModelURI, editingDomain);
			
			PlwebAddProductWizard wizard = null;
			if (diagramRoot.getDiagramType().equals(DiagramType.PRODUCT)) {
				String projectName = PathHelper.getProjectName(domainModelURI
						.toPlatformString(true));
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
				IFile file = project.getFile(PathHelper
						.getFileNameFromPath(diagramRoot.getProjectPath()));
				URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
				DiagramRoot mainDiagramRoot = getElement(uri, editingDomain);
				
				wizard = new PlwebAddProductWizard(uri, mainDiagramRoot, editingDomain);
				wizard.setProductName(PathHelper.getProductName(domainModelURI.toPlatformString(true)));
				wizard.setProductDiagram(diagramRoot);
			} else {
				wizard = new PlwebAddProductWizard(domainModelURI, diagramRoot, editingDomain);
			}
			wizard.setWindowTitle(Messages.AddProduct_WizardTitle);
			wizard.setNeedsProgressMonitor(true);
			PlwebDiagramEditorUtil.runShowLogDialog(getShell()); //$NON-NLS-1$
		} catch (Exception e) {
			MessageDialog.openError(getShell(), "Error",
					"Error happened while performing action\n Reason: "
							+ e.getMessage());
		}
	}

}
