package org.spbu.pldoctoolkit.graph.diagram.productline.part;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gmf.runtime.common.ui.services.marker.MarkerNavigationService;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramDropTargetListener;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;
import org.spbu.pldoctoolkit.graph.DocumentationCore;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.ProductLine;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLine2EditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.navigator.DrlModelNavigatorItem;

/**
 * @generated
 */
public class DrlModelDiagramEditor extends DiagramDocumentEditor implements
		IGotoMarker {

	/**
	 * @generated
	 */
	public static final String ID = "org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelDiagramEditorID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final String CONTEXT_ID = "org.spbu.pldoctoolkit.graph.diagram.productline.ui.diagramContext"; //$NON-NLS-1$

	public static final String RESOURCESET_PREFERENCE_ID = "resourceset";
	public static final String RESOURCESET_ITEM_SEPARATOR = "::";

	/**
	 * @generated
	 */
	public DrlModelDiagramEditor() {
		super(true);
	}

	/**
	 * @generated
	 */
	protected String getContextID() {
		return CONTEXT_ID;
	}

	/**
	 * @generated
	 */
	protected PaletteRoot createPaletteRoot(PaletteRoot existingPaletteRoot) {
		PaletteRoot root = super.createPaletteRoot(existingPaletteRoot);
		new DrlModelPaletteFactory().fillPalette(root);
		return root;
	}

	/**
	 * @generated
	 */
	protected PreferencesHint getPreferencesHint() {
		return DrlModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
	}

	protected PreferencesHint getResourceSetPreferencesHint() {
		return DrlModelDiagramEditorPlugin.DIAGRAM_RESOURCESET_PREFERENCES_HINT;
	}

	/**
	 * @generated
	 */
	public String getContributorId() {
		return DrlModelDiagramEditorPlugin.ID;
	}

	/**
	 * @generated
	 */
	public Object getAdapter(Class type) {
		if (type == IShowInTargetList.class) {
			return new IShowInTargetList() {
				public String[] getShowInTargetIds() {
					return new String[] { ProjectExplorer.VIEW_ID };
				}
			};
		}
		return super.getAdapter(type);
	}

	/**
	 * @generated
	 */
	protected IDocumentProvider getDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput
				|| input instanceof URIEditorInput) {
			return DrlModelDiagramEditorPlugin.getInstance()
					.getDocumentProvider();
		}
		return super.getDocumentProvider(input);
	}

	/**
	 * @generated
	 */
	public TransactionalEditingDomain getEditingDomain() {
		IDocument document = getEditorInput() != null ? getDocumentProvider()
				.getDocument(getEditorInput()) : null;
		if (document instanceof IDiagramDocument) {
			return ((IDiagramDocument) document).getEditingDomain();
		}
		return super.getEditingDomain();
	}

	/**
	 * @generated
	 */
	protected void setDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput
				|| input instanceof URIEditorInput) {
			setDocumentProvider(DrlModelDiagramEditorPlugin.getInstance()
					.getDocumentProvider());
		} else {
			super.setDocumentProvider(input);
		}
	}

	/**
	 * @generated
	 */
	public void gotoMarker(IMarker marker) {
		MarkerNavigationService.getInstance().gotoMarker(this, marker);
	}

	/**
	 * @generated
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * @generated
	 */
	public void doSaveAs() {
		performSaveAs(new NullProgressMonitor());
	}

	/**
	 * @generated
	 */
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		Shell shell = getSite().getShell();
		IEditorInput input = getEditorInput();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		IFile original = input instanceof IFileEditorInput ? ((IFileEditorInput) input)
				.getFile()
				: null;
		if (original != null) {
			dialog.setOriginalFile(original);
		}
		dialog.create();
		IDocumentProvider provider = getDocumentProvider();
		if (provider == null) {
			// editor has been programmatically closed while the dialog was open
			return;
		}
		if (provider.isDeleted(input) && original != null) {
			String message = NLS.bind(
					Messages.DrlModelDiagramEditor_SavingDeletedFile, original
							.getName());
			dialog.setErrorMessage(null);
			dialog.setMessage(message, IMessageProvider.WARNING);
		}
		if (dialog.open() == Window.CANCEL) {
			if (progressMonitor != null) {
				progressMonitor.setCanceled(true);
			}
			return;
		}
		IPath filePath = dialog.getResult();
		if (filePath == null) {
			if (progressMonitor != null) {
				progressMonitor.setCanceled(true);
			}
			return;
		}
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = workspaceRoot.getFile(filePath);
		final IEditorInput newInput = new FileEditorInput(file);
		// Check if the editor is already open
		IEditorMatchingStrategy matchingStrategy = getEditorDescriptor()
				.getEditorMatchingStrategy();
		IEditorReference[] editorRefs = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		for (int i = 0; i < editorRefs.length; i++) {
			if (matchingStrategy.matches(editorRefs[i], newInput)) {
				MessageDialog.openWarning(shell,
						Messages.DrlModelDiagramEditor_SaveAsErrorTitle,
						Messages.DrlModelDiagramEditor_SaveAsErrorMessage);
				return;
			}
		}
		boolean success = false;
		try {
			provider.aboutToChange(newInput);
			getDocumentProvider(newInput).saveDocument(progressMonitor,
					newInput,
					getDocumentProvider().getDocument(getEditorInput()), true);
			success = true;
		} catch (CoreException x) {
			IStatus status = x.getStatus();
			if (status == null || status.getSeverity() != IStatus.CANCEL) {
				ErrorDialog.openError(shell,
						Messages.DrlModelDiagramEditor_SaveErrorTitle,
						Messages.DrlModelDiagramEditor_SaveErrorMessage, x
								.getStatus());
			}
		} finally {
			provider.changed(newInput);
			if (success) {
				setInput(newInput);
			}
		}
		if (progressMonitor != null) {
			progressMonitor.setCanceled(!success);
		}
	}

	/**
	 * @generated
	 */
	public ShowInContext getShowInContext() {
		return new ShowInContext(getEditorInput(), getNavigatorSelection());
	}

	/**
	 * @generated
	 */
	private ISelection getNavigatorSelection() {
		IDiagramDocument document = getDiagramDocument();
		if (document == null) {
			return StructuredSelection.EMPTY;
		}
		Diagram diagram = document.getDiagram();
		IFile file = WorkspaceSynchronizer.getFile(diagram.eResource());
		if (file != null) {
			DrlModelNavigatorItem item = new DrlModelNavigatorItem(diagram,
					file, false);
			return new StructuredSelection(item);
		}
		return StructuredSelection.EMPTY;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor#configureGraphicalViewer()
	 * 
	 * HAND
	 */
	/*	protected void loadDocumentationCores() {
	 super.configureGraphicalViewer();
	 
	 ResourceSet resourceSet = getEditingDomain().getResourceSet();
	 
	 ProductLine pline = getProductLine();
	 if(pline == null) {
	 DrlModelDiagramEditorPlugin.getInstance().logInfo("pline is null");
	 return;
	 }
	 
	 IPreferenceStore resourceSetPreferenceStore =
	 (IPreferenceStore) getPreferencesHint().getPreferenceStore();
	 String resourcesString = resourceSetPreferenceStore.getString(RESOURCESET_PREFERENCE_ID);
	 String[] resources = resourcesString.split(RESOURCESET_ITEM_SEPARATOR);
	 
	 //XXX
	 String tempStr = "";
	 for(String resourcePath : resources) {
	 tempStr += resourcePath + "; ";
	 }
	 DrlModelDiagramEditorPlugin.getInstance().logInfo("got resources: " + tempStr);
	 
	 for(String resourceUri : resources) {
	 Resource res = resourceSet.getResource(URI.createFileURI(resourceUri), false);
	 EObject root = res.getContents().get(0);
	 if(root != null && DrlPackage.DOCUMENTATION_CORE == root.eClass().getClassifierID()) {
	 DrlModelDiagramEditorPlugin.getInstance().logInfo("adding " + root);
	 pline.getDocumentationCores().add(root);
	 }
	 }
	 }*/

	/**
	 * Class LoadResourcesAdapter.
	 *
	 * HAND
	 *
	 * @author Alexey Semenov
	 * @version 1.0
	 */
	public static class LoadResourcesAdapter implements Adapter {
		private NotificationFilter myFilter;
		private Notifier myTarget;

		public LoadResourcesAdapter(NotificationFilter addResourceFilter) {
			myFilter = addResourceFilter;
		}

		public Notifier getTarget() {
			return myTarget;
		}

		public boolean isAdapterForType(Object type) {
			return false;
		}

		public void notifyChanged(Notification notification) {
			if (myFilter.matches(notification)) {
				Object value = notification.getNewValue();
				if (value instanceof Resource) {
					Resource res = (Resource) value;

					DrlModelDiagramEditorPlugin.getInstance().logInfo(
							"res: " + res);

					String preferenceKey = res.getURI().toString();

					DrlModelDiagramEditorPlugin.getInstance().logInfo(
							"contents size: " + res.getContents().size()
									+ "; is loaded: " + res.isLoaded());

					EObject root = res.getContents().get(0);
					DrlModelDiagramEditorPlugin.getInstance().logInfo(
							"root: " + root);
					ProductLine pline;
					if (root != null
							&& DrlPackage.PRODUCT_LINE == root.eClass()
									.getClassifierID()) {
						pline = (ProductLine) root;
					} else {
						DrlModelDiagramEditorPlugin.getInstance().logInfo(
								"pline is null or non-pline");
						return;
					}

					DrlModelDiagramEditorPlugin.getInstance().logInfo(
							"pline: " + pline);

					IPreferenceStore resourceSetPreferenceStore = (IPreferenceStore) DrlModelDiagramEditorPlugin.DIAGRAM_RESOURCESET_PREFERENCES_HINT
							.getPreferenceStore();
					String resourcesString = resourceSetPreferenceStore
							.getString(preferenceKey);
					String[] resources = resourcesString
							.split(RESOURCESET_ITEM_SEPARATOR);

					//XXX
					String tempStr = "";
					for (String resourcePath : resources) {
						tempStr += resourcePath + "; ";
					}
					DrlModelDiagramEditorPlugin.getInstance().logInfo(
							"got resources: " + tempStr);

					for (String resourceUri : resources) {
						Resource newRes = res.getResourceSet().getResource(
								URI.createFileURI(resourceUri), false);
						EObject newResRoot = newRes.getContents().get(0);
						if (newResRoot != null
								&& DrlPackage.DOCUMENTATION_CORE == newResRoot
										.eClass().getClassifierID()) {
							DrlModelDiagramEditorPlugin.getInstance().logInfo(
									"adding " + newResRoot);
							//							pline.getDocumentationCores().add(newResRoot);
						}
					}
				}
			}
		}

		public void setTarget(Notifier newTarget) {
			myTarget = newTarget;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 * 
	 * HAND
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		super.doSave(progressMonitor);

		//		saveDocumentationCores(progressMonitor);
	}

	/**
	 * @param progressMonitor
	 */
	private void saveDocumentationCores(IProgressMonitor progressMonitor) {
		IPreferenceStore resourceSetPreferenceStore = (IPreferenceStore) DrlModelDiagramEditorPlugin.DIAGRAM_RESOURCESET_PREFERENCES_HINT
				.getPreferenceStore();

		final ProductLine2EditPart productLineView = (ProductLine2EditPart) getDiagramEditPart()
				.getChildBySemanticHint(
						Integer.toString(ProductLine2EditPart.VISUAL_ID));
		final ProductLine pline = (ProductLine) ((View) productLineView
				.getModel()).getElement();

		if (pline == null) {
			return;
		}

		String resourcesString = "";
		boolean first = true;
		EList<Resource> docCoreResources = new UniqueEList<Resource>();
		//		for (Object doc : pline.getDocumentationCores()) {
		//			Resource docResource = ((DocumentationCore) doc).eResource();
		//			docCoreResources.add(docResource);
		//		}

		for (Resource resource : docCoreResources) {
			if (!first) {
				resourcesString += RESOURCESET_ITEM_SEPARATOR;
			} else {
				first = false;
			}

			resourcesString += resource.getURI().toString();
		}

		String resourceKey = pline.eResource().getURI().toString();

		//XXX
		DrlModelDiagramEditorPlugin.getInstance().logInfo(
				"saved resource data, key: " + resourceKey + "; string: "
						+ resourcesString);

		//TODO move this 'isCanceled' to the front
		if (!progressMonitor.isCanceled()) {
			resourceSetPreferenceStore.setValue(resourceKey, resourcesString);
		}
	}

}