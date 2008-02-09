package org.spbu.pldoctoolkit.graph.diagram.infproduct.part;

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
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateDiagramViewOperation;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.DocumentationCoreEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

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
				+ DocumentationCoreEditPart.MODEL_ID + " model content");
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
				.getUniqueFileName(filePath, fileName, "infproduct_diagram")); //$NON-NLS-1$

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
				EObject modelElement = diagramRootElementSelectionPage
						.getModelElement();
				EObject documentCoreParentOfSelected = modelElement
						.eContainer();

				int diagramVID = DrlModelVisualIDRegistry
						.getDiagramVisualID(documentCoreParentOfSelected);
				if (diagramVID != DocumentationCoreEditPart.VISUAL_ID) {
					return CommandResult
							.newErrorCommandResult("Incorrect model object stored as a root resource object"); //$NON-NLS-1$
				}

				// HAND
				// my custom code
				IElementType elementType;
				switch (modelElement.eClass().getClassifierID()) {
				case DrlPackage.INF_ELEMENT:
					elementType = DrlModelElementTypes.InfElement_1001;
					break;
				case DrlPackage.INF_PRODUCT:
					elementType = DrlModelElementTypes.InfProduct_1002;
					break;
				default:
					return CommandResult
							.newErrorCommandResult("Incorrect model object selected"); //$NON-NLS-1$
				}

				Diagram diagram = ViewService.createDiagram(
						documentCoreParentOfSelected,
						DocumentationCoreEditPart.MODEL_ID,
						DrlModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				diagramResource.getContents().add(diagram);

				Node rootNode = ViewService.createNode(diagram, modelElement,
						((IHintedType) elementType).getSemanticHint(),
						DrlModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				rootNode.setElement(modelElement);

				EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE
						.createEAnnotation();
				shortcutAnnotation.setSource("root"); //$NON-NLS-1$
//				shortcutAnnotation.getDetails().put(
//						"modelID", DocumentationCoreEditPart.MODEL_ID); //$NON-NLS-1$
				rootNode.getEAnnotations().add(shortcutAnnotation);
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
		 * @generated NOT
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
									new EObjectAdapter(selectedModelElement.eContainer()),
									DocumentationCoreEditPart.MODEL_ID,
									DrlModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT));
			setErrorMessage(result ? null
					: "Invalid diagram root element is selected");
			return result;
		}
	}
}