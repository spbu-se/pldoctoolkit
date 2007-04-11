package org.spbu.pldoctoolkit.graph.diagram.productline.part;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateDiagramViewOperation;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class DrlModelNewDiagramFileWizard extends Wizard {

	/**
	 * @generated
	 */
	private TransactionalEditingDomain myEditingDomain;

	/**
	 * @generated
	 */
	public DrlModelNewDiagramFileWizard(
			org.eclipse.emf.common.util.URI domainModelURI,
			EObject diagramRoot, TransactionalEditingDomain editingDomain) {
		assert domainModelURI != null : "Domain model uri must be specified"; //$NON-NLS-1$
		assert diagramRoot != null : "Doagram root element must be specified"; //$NON-NLS-1$
		assert editingDomain != null : "Editing domain must be specified"; //$NON-NLS-1$

		myFileCreationPage = new WizardNewFileCreationPage(
				"Initialize new diagram file", StructuredSelection.EMPTY);
		myFileCreationPage.setTitle("Diagram file");
		myFileCreationPage.setDescription("Create new diagram based on "
				+ ProductLineEditPart.MODEL_ID + " model content");
		IPath filePath;
		String fileName = domainModelURI.trimFileExtension().lastSegment();
		if (domainModelURI.isPlatformResource()) {
			filePath = new Path(domainModelURI.trimSegments(1)
					.toPlatformString(true));
		} else if (domainModelURI.isFile()) {
			filePath = new Path(domainModelURI.trimSegments(1).toFileString());
		} else {
			// TODO : use some default path
			throw new IllegalArgumentException("Unsupported URI: "
					+ domainModelURI);
		}
		myFileCreationPage.setContainerFullPath(filePath);
		myFileCreationPage.setFileName(DrlModelDiagramEditorUtil
				.getUniqueFileName(filePath, fileName, "productline_diagram")); //$NON-NLS-1$

		diagramRootElementSelectionPage = new DiagramRootElementSelectionPage(
				"Select diagram root element");
		diagramRootElementSelectionPage.setTitle("Diagram root element");
		diagramRootElementSelectionPage
				.setDescription("Select semantic model element to be depicted on diagram");
		diagramRootElementSelectionPage.setModelElement(diagramRoot);

		myEditingDomain = editingDomain;
	}

	/**
	 * @generated
	 */
	private WizardNewFileCreationPage myFileCreationPage;

	/**
	 * @generated
	 */
	private ModelElementSelectionPage diagramRootElementSelectionPage;

	/**
	 * @generated
	 */
	public void addPages() {
		addPage(myFileCreationPage);
		addPage(diagramRootElementSelectionPage);
	}

	/**
	 * @generated NOT
	 */
	public boolean performFinish() {
		List affectedFiles = new LinkedList();
		IFile diagramFile = myFileCreationPage.createNewFile();
		try {
			diagramFile.setCharset("UTF-8", new NullProgressMonitor()); //$NON-NLS-1$
		} catch (CoreException e) {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"Unable to set charset for diagram file", e); //$NON-NLS-1$
		}
		affectedFiles.add(diagramFile);
		org.eclipse.emf.common.util.URI diagramModelURI = org.eclipse.emf.common.util.URI
				.createPlatformResourceURI(
						diagramFile.getFullPath().toString(), true);
		ResourceSet resourceSet = myEditingDomain.getResourceSet();
		final Resource diagramResource = resourceSet
				.createResource(diagramModelURI);
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(
				myEditingDomain, "Initializing diagram contents", affectedFiles) { //$NON-NLS-1$

			protected CommandResult doExecuteWithResult(
					IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				int diagramVID = DrlModelVisualIDRegistry
						.getDiagramVisualID(diagramRootElementSelectionPage
								.getModelElement());
				if (diagramVID != ProductLineEditPart.VISUAL_ID) {
					return CommandResult
							.newErrorCommandResult("Incorrect model object stored as a root resource object"); //$NON-NLS-1$
				}
				Diagram diagram = ViewService.createDiagram(
						diagramRootElementSelectionPage.getModelElement(),
						ProductLineEditPart.MODEL_ID,
						DrlModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				diagramResource.getContents().add(diagram);
				
				 // HAND
				// my custom code
				diagram.setElement(null);
				Node rootNode = ViewService.createNode(diagram,
						diagramRootElementSelectionPage.getModelElement(),
						((IHintedType) DrlModelElementTypes.ProductLine_1001)
								.getSemanticHint(),
						DrlModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				rootNode.setElement(diagramRootElementSelectionPage
						.getModelElement());
				// end of my custom code
				 
				return CommandResult.newOKCommandResult();
			}
		};
		try {
			OperationHistoryFactory.getOperationHistory().execute(command,
					new NullProgressMonitor(), null);
			diagramResource.save(Collections.EMPTY_MAP);
			DrlModelDiagramEditorUtil.openDiagram(diagramResource);
		} catch (ExecutionException e) {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"Unable to create model and diagram", e); //$NON-NLS-1$
		} catch (IOException ex) {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"Save operation failed for: " + diagramModelURI, ex); //$NON-NLS-1$
		} catch (PartInitException ex) {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"Unable to open editor", ex); //$NON-NLS-1$
		}
		return true;
	}

	/**
	 * @generated NOT
	 */
	/*
	 public boolean performFinish() {
	 IFile diagramFile = myFileCreationPage.createNewFile();
	 try {
	 diagramFile.setCharset("UTF-8", new NullProgressMonitor()); //$NON-NLS-1$
	 } catch (CoreException e) {
	 DrlModelDiagramEditorPlugin.getInstance().logError(
	 "Unable to set charset for diagram file", e); //$NON-NLS-1$
	 }
	 ResourceSet resourceSet = myEditingDomain.getResourceSet();
	 final Resource diagramResource = resourceSet
	 .createResource(org.eclipse.emf.common.util.URI
	 .createPlatformResourceURI(diagramFile.getFullPath()
	 .toString(), true));
	 List affectedFiles = new LinkedList();
	 affectedFiles.add(mySelectedModelFile);
	 affectedFiles.add(diagramFile);
	 AbstractTransactionalCommand command = new AbstractTransactionalCommand(
	 myEditingDomain, "Initializing diagram contents", affectedFiles) { //$NON-NLS-1$

	 protected CommandResult doExecuteWithResult(
	 IProgressMonitor monitor, IAdaptable info)
	 throws ExecutionException {
	 int diagramVID = DrlModelVisualIDRegistry
	 .getDiagramVisualID(myDiagramRoot);
	 if (diagramVID != ProductLineEditPart.VISUAL_ID) {
	 return CommandResult
	 .newErrorCommandResult("Incorrect model object stored as a root resource object"); //$NON-NLS-1$
	 }
	 Diagram diagram = ViewService.createDiagram(myDiagramRoot,
	 ProductLineEditPart.MODEL_ID,
	 DrlModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
	 diagramResource.getContents().add(diagram);

	 //HAND
	 // my custom code
	 diagram.setElement(null);
	 Node rootNode = ViewService.createNode(diagram, myDiagramRoot,
	 ((IHintedType) DrlModelElementTypes.ProductLine_1001)
	 .getSemanticHint(),
	 DrlModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
	 rootNode.setElement(myDiagramRoot);
	 // end of my custom code

	 return CommandResult.newOKCommandResult();
	 }
	 };
	 try {
	 OperationHistoryFactory.getOperationHistory().execute(command,
	 new NullProgressMonitor(), null);
	 diagramResource.save(Collections.EMPTY_MAP);
	 DrlModelDiagramEditorUtil.openDiagram(diagramResource);
	 } catch (ExecutionException e) {
	 DrlModelDiagramEditorPlugin.getInstance().logError(
	 "Unable to create model and diagram", e); //$NON-NLS-1$
	 } catch (IOException ex) {
	 DrlModelDiagramEditorPlugin
	 .getInstance()
	 .logError(
	 "Save operation failed for: " + diagramFile.getFullPath().toString(), ex); //$NON-NLS-1$
	 } catch (PartInitException ex) {
	 DrlModelDiagramEditorPlugin.getInstance().logError(
	 "Unable to open editor", ex); //$NON-NLS-1$
	 }
	 return true;
	 }
	 */
	/**
	 * @generated
	 */
	private static class DiagramRootElementSelectionPage extends
			ModelElementSelectionPage {

		/**
		 * @generated
		 */
		protected DiagramRootElementSelectionPage(String pageName) {
			super(pageName);
		}

		/**
		 * @generated
		 */
		protected String getSelectionTitle() {
			return "Select diagram root element:";
		}

		/**
		 * @generated
		 */
		protected boolean validatePage() {
			if (selectedModelElement == null) {
				setErrorMessage("Diagram root element is not selected");
				return false;
			}
			boolean result = ViewService
					.getInstance()
					.provides(
							new CreateDiagramViewOperation(
									new EObjectAdapter(selectedModelElement),
									ProductLineEditPart.MODEL_ID,
									DrlModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT));
			setErrorMessage(result ? null
					: "Invalid diagram root element is selected");
			return result;
		}
	}
}
