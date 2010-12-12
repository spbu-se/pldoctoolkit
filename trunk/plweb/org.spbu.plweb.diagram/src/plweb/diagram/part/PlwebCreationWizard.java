package plweb.diagram.part;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import plweb.DiagramRoot;
import plweb.diagram.edit.parts.DiagramRootEditPart;
import plweb.diagram.util.PathHelper;
import plweb.diagram.util.ProjectSynchronizer;
import plweb.diagram.util.projects.ProjectOperationException;
import plweb.diagram.util.projects.ProjectPlweb;

public class PlwebCreationWizard extends Wizard implements INewWizard {

	private IWorkbench workbench;

	protected IStructuredSelection selection;

	protected PlwebCreationWizardPage creationProjectPage;

	protected Resource diagram;

	public IWorkbench getWorkbench() {
		return workbench;
	}

	public IStructuredSelection getSelection() {
		return selection;
	}

	public final Resource getDiagram() {
		return diagram;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		setWindowTitle(Messages.PlwebCreationWizardTitle);
		setDefaultPageImageDescriptor(PlwebDiagramEditorPlugin
				.getBundledImageDescriptor("icons/wizban/NewPlwebWizard.gif")); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}

	public void addPages() {
		creationProjectPage = new PlwebCreationWizardPage("DiagramModelFile"); //$NON-NLS-1$
		creationProjectPage.setTitle(Messages.PlwebCreationWizard_DiagramModelFilePageTitle);
		addPage(creationProjectPage);
	}

	public boolean performFinish() {
		IRunnableWithProgress op = new WorkspaceModifyOperation(null) {
			protected void execute(IProgressMonitor monitor)
					throws CoreException, InterruptedException {
				try {
					String pathWr = PathHelper.normalize(
							creationProjectPage.getPathFromLocationField());
					System.out.println(pathWr);
					String pathPw = PathHelper.getWorkspaceProjectPath(
							creationProjectPage.getProjectName());
					
					ProjectSynchronizer sync = new ProjectSynchronizer(pathWr, pathPw);
					sync.synchronizePw();
					
					IProject project = ResourcesPlugin.getWorkspace().getRoot()
							.getProject(creationProjectPage.getProjectName()); 
					project.create(null);
					project.open(null);
					
					initDiagramFile();
					
					ResourcesPlugin.getWorkspace().getRoot()
							.refreshLocal(IResource.DEPTH_INFINITE, null);

				} catch (ProjectOperationException e) {
					MessageDialog.openError(
							getContainer().getShell(),
							"Error",
							"Error happened while creating new PLWeb project");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		try {
			getContainer().run(false, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof CoreException) {
				MessageDialog.openError(
						getContainer().getShell(),
						"Error",
						"Error happened while creating new PLWeb project");
			} else {
				PlwebDiagramEditorPlugin.getInstance().logError(
						"Error happened while creating new PLWeb project",
						e.getTargetException()); //$NON-NLS-1$
			}
			return false;
		}
		
		return true;
	}
	
	protected void initDiagramFile() throws CoreException {
		List<IFile> affectedFiles = new LinkedList<IFile>();
		final IFile diagramFile = createNewFile();
		PlwebDiagramEditorUtil.setCharset(diagramFile);
		affectedFiles.add(diagramFile);
		URI diagramModelURI = URI.createPlatformResourceURI(diagramFile
				.getFullPath().toString(), true);
		TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE
			.createEditingDomain();
		ResourceSet resourceSet = editingDomain.getResourceSet();
		final Resource diagramResource = resourceSet.createResource(diagramModelURI);
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(
				editingDomain,
				Messages.PlwebNewDiagramFileWizard_InitDiagramCommand,
				affectedFiles) {

			protected CommandResult doExecuteWithResult(
					IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				DiagramRoot element = getElement(getEditingDomain());
				int diagramVID = PlwebVisualIDRegistry.getDiagramVisualID(element);
				if (diagramVID != DiagramRootEditPart.VISUAL_ID) {
					return CommandResult
							.newErrorCommandResult(Messages.PlwebNewDiagramFileWizard_IncorrectRootError);
				}
				Diagram diagram = ViewService.createDiagram(
						element,
						DiagramRootEditPart.MODEL_ID,
						PlwebDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				diagramResource.getContents().add(diagram);
				return CommandResult.newOKCommandResult();
			}
		};
		try {
			OperationHistoryFactory.getOperationHistory().execute(command,
					new NullProgressMonitor(), null);
			diagramResource.save(PlwebDiagramEditorUtil.getSaveOptions());
			PlwebDiagramEditorUtil.openDiagram(diagramResource);
		} catch (ExecutionException e) {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"Unable to create model and diagram", e); //$NON-NLS-1$
		} catch (IOException ex) {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"Save operation failed for: " + diagramModelURI, ex); //$NON-NLS-1$
		} catch (PartInitException ex) {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"Unable to open editor", ex); //$NON-NLS-1$
		}
	}
	
	private IFile createNewFile() throws CoreException {
		IPath newFilePath = new Path(PathHelper.getProjectFile(
				creationProjectPage.getProjectName(),
				creationProjectPage.getProjectName() + ProjectPlweb.PLWEB_DIAGRAM_EXTENSION));
		IFile newFileHandle = ResourcesPlugin.getWorkspace().getRoot().getFile(
				newFilePath);
		InputStream contents = new ByteArrayInputStream(new byte[0]);
		try { 
			newFileHandle.create(contents, false, null);
		} catch (CoreException e) {
			//do nothing
		}
		return newFileHandle;
	}
	
	private DiagramRoot getElement(TransactionalEditingDomain editingDomain) {
		IPath filePath = new Path(PathHelper.getProjectFile(
				creationProjectPage.getProjectName(),
				creationProjectPage.getProjectName() + ProjectPlweb.PLWEB_EXTENSION));
		IFile fileHandle = ResourcesPlugin.getWorkspace().getRoot().getFile(
				filePath);
		URI uri = URI.createPlatformResourceURI(
				fileHandle.getFullPath().toString(), true);
		
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
}
