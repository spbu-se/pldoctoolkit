package plweb.diagram.part;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
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
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PartInitException;

import plweb.Area;
import plweb.DiagramRoot;
import plweb.Page;
import plweb.diagram.edit.parts.DiagramRootEditPart;
import plweb.diagram.util.DiagramContainer;
import plweb.diagram.util.PathHelper;
import plweb.diagram.util.ProductGenerator;
import plweb.diagram.util.projects.ProjectOperationException;
import plweb.diagram.util.projects.ProjectPlweb;

public class PlwebAddProductWizard extends Wizard {

	private String filePath;
	
	private String projectName;
	
	private VariativitySelectionPage variativitySelectionPage;

	private TransactionalEditingDomain myEditingDomain;
	
	private DiagramRoot diagramRoot;
	
	private DiagramRoot productDiagramRoot;
	
	public PlwebAddProductWizard(URI domainModelURI, DiagramRoot diagramRoot,
			TransactionalEditingDomain editingDomain) {
		assert domainModelURI != null : "Domain model uri must be specified"; //$NON-NLS-1$
		assert diagramRoot != null : "Doagram root element must be specified"; //$NON-NLS-1$
		assert editingDomain != null : "Editing domain must be specified"; //$NON-NLS-1$
		
		variativitySelectionPage = new VariativitySelectionPage(Messages.AddProduct_VariativityPageName);
		variativitySelectionPage.setTitle(Messages.AddProduct_VariativityPageName);
		variativitySelectionPage.setDescription(Messages.AddProduct_VariativityResolveItems);
		Area area = diagramRoot.getArea();
		variativitySelectionPage.setModelElement(area);
		variativitySelectionPage.setDiagram(diagramRoot);
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IPath location = root.getLocation();
		String resourcePath = domainModelURI.toPlatformString(true);
		filePath = location + (new Path(resourcePath)).toString();
		projectName = resourcePath.substring(1, resourcePath.indexOf("/", 1));
		
		myEditingDomain = editingDomain;
		this.diagramRoot = diagramRoot;
	}

	public void addPages() {
		addPage(variativitySelectionPage);
	}
	
	public void setProductDiagram(DiagramRoot diagramRoot) {
		this.productDiagramRoot = diagramRoot;
		variativitySelectionPage.setProductDiagram(diagramRoot);
	}
	
	public void setProductName(String name) {
		variativitySelectionPage.setProductName(name);
	}

	public boolean performFinish() {
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(
				myEditingDomain,
				"Creating new product's project",
				null) {

			protected CommandResult doExecuteWithResult(
					IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				
				try {
					ProductGenerator productGenerator = new ProductGenerator(
							diagramRoot, variativitySelectionPage.getText(),
							filePath, variativitySelectionPage.getCheckedElements());
					
					if (productDiagramRoot != null) {
						List<String> oldPages = new ArrayList<String>();
						DiagramContainer container = new DiagramContainer(productDiagramRoot);
						for (Page page : container.getPageElements()) {
							oldPages.add(page.getSource());
						}
						productGenerator.setOldPages(oldPages);
					}				
					
					productGenerator.generate();
					initDiagramFile();
					
					IProject project = ResourcesPlugin.getWorkspace().getRoot()
							.getProject(projectName);
					project.refreshLocal(IResource.DEPTH_INFINITE, null);
					
				} catch (ProjectOperationException e) {
					e.printStackTrace();
					return CommandResult.newErrorCommandResult(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
					return CommandResult.newErrorCommandResult(e.getMessage());
				}
				
				return CommandResult.newOKCommandResult();
			}
		};
		try {
			OperationHistoryFactory.getOperationHistory().execute(command,
					new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"Unable to create model and diagram", e); //$NON-NLS-1$
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
	
	private IFile createNewFile() {
		String wrProjectPath = diagramRoot.getProjectPath();
		String projectName = wrProjectPath.substring(wrProjectPath.lastIndexOf("/") + 1);
		String fileName = variativitySelectionPage.getText()
				+ "."
				+ projectName
				+ ProjectPlweb.PLWEB_DIAGRAM_EXTENSION;
		
		IPath newFilePath = new Path(PathHelper.getProjectFile(
				projectName,fileName));
		IFile newFileHandle = ResourcesPlugin.getWorkspace().getRoot().getFile(
				newFilePath);
		InputStream contents = new ByteArrayInputStream(new byte[0]);
		try {
			newFileHandle.create(contents, false, null);
		} catch (CoreException e) {
			// TODO: handle exception
		}
		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			// do nothing
		}
		return newFileHandle;
	}
	
	private DiagramRoot getElement(TransactionalEditingDomain editingDomain) {
		String wrProjectPath = diagramRoot.getProjectPath();
		String projectName = wrProjectPath.substring(wrProjectPath.lastIndexOf("/") + 1);
		String fileName = variativitySelectionPage.getText()
				+ "."
				+ projectName
				+ ProjectPlweb.PLWEB_EXTENSION;
		
		IPath filePath = new Path(PathHelper.getProjectFile(
				projectName,fileName));
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
