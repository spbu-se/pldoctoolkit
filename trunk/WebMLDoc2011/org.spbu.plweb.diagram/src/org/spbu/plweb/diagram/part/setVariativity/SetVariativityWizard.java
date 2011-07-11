package org.spbu.plweb.diagram.part.setVariativity;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.DeferredCreateConnectionViewAndElementCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyReferenceCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.spbu.plweb.GroupType;
import org.spbu.plweb.diagram.edit.parts.DiagramRootEditPart;
import org.spbu.plweb.diagram.edit.parts.TargetRefElementEditPart;
import org.spbu.plweb.diagram.part.Messages;
import org.spbu.plweb.diagram.part.PlwebDiagramEditor;
import org.spbu.plweb.diagram.providers.PlwebElementTypes;
import org.spbu.plweb.impl.GroupImpl;
import org.spbu.plweb.impl.PlwebFactoryImpl;

public class SetVariativityWizard extends Wizard {

	private ShapeNodeEditPart selectedElement;

	private SetVariativityPage setVariativityPage;

	public SetVariativityWizard() {
		setVariativityPage = new SetVariativityPage(
				Messages.SetVariativity_WizardTitle);
		setVariativityPage.setTitle(Messages.SetVariativity_PageName);

	}

	public ShapeNodeEditPart getInvoker() {
		IWizardContainer container = this.getContainer();
		SetVariativityWizardDialog dialog = (SetVariativityWizardDialog) container;
		return dialog.getSelectedElement();
	}

	@Override
	public void addPages() {
		addPage(setVariativityPage);
	}

	@Override
	public boolean performFinish() {
		selectedElement = getSelectedElement();
		CompoundCommand cc1 = new CompoundCommand("Create Subtopic and Link");

		// Create new node
		CreateViewRequest newNodeRequest;
		if (setVariativityPage.getGroupType() == "AND") {
			newNodeRequest = CreateViewRequestFactory.getCreateShapeRequest(
					PlwebElementTypes.Node_2006,
					selectedElement.getDiagramPreferencesHint());
		} else {
			newNodeRequest = CreateViewRequestFactory.getCreateShapeRequest(
					PlwebElementTypes.Group_2005,
					selectedElement.getDiagramPreferencesHint());
		}

		Point p = selectedElement.getFigure().getBounds().getTopRight()
				.getCopy();
		selectedElement.getFigure().translateToAbsolute(p);

		DiagramRootEditPart mapEditPart = (DiagramRootEditPart) selectedElement
				.getParent();

		Command createNewNodeCmd = mapEditPart.getCommand(newNodeRequest);
		IAdaptable variativityElementViewAdapter = (IAdaptable) ((List) newNodeRequest
				.getNewObject()).get(0);

		cc1.add(createNewNodeCmd);

		// create the link command
		ICommand createLinkCmd = new DeferredCreateConnectionViewAndElementCommand(
				new CreateConnectionViewAndElementRequest(
						PlwebElementTypes.SourceRefElementClass_4001,
						((IHintedType) PlwebElementTypes.SourceRefElementClass_4001)
								.getSemanticHint(), selectedElement
								.getDiagramPreferencesHint()),
				new EObjectAdapter((EObject) selectedElement.getModel()),
				variativityElementViewAdapter, selectedElement.getViewer());

		cc1.add(new ICommandProxy(createLinkCmd));

		selectedElement.getDiagramEditDomain().getDiagramCommandStack()
				.execute(cc1);

		// put the new node in edit mode
		final EditPartViewer selectedElementViewer = selectedElement
				.getViewer();
		final EditPart selectedElementPart = (EditPart) selectedElementViewer
				.getEditPartRegistry().get(
						variativityElementViewAdapter.getAdapter(View.class));

		if (selectedElementPart != null) {

			Object element = ((NodeImpl) selectedElementPart.getModel())
					.getElement();
			TransactionalEditingDomain editingDomain = TransactionUtil
					.getEditingDomain(element);

			specifyGroupType(selectedElementPart, element, editingDomain);

			setGroupTitle(element, editingDomain);

			setGroupParent(element, editingDomain,
					((NodeImpl) selectedElement.getModel()).getElement());

			Display.getCurrent().asyncExec(new Runnable() {

				public void run() {
					selectedElementViewer.setSelection(new StructuredSelection(
							selectedElementPart));
					Request der = new Request(RequestConstants.REQ_DIRECT_EDIT);
					selectedElementPart.performRequest(der);
				}
			});
		}

		CompoundCommand cc2 = new CompoundCommand("edit");
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IDiagramWorkbenchPart diagramPart = (IDiagramWorkbenchPart) page
				.getActiveEditor();
		IDiagramGraphicalViewer diagramGraphicalViewer = diagramPart
				.getDiagramGraphicalViewer();

		for (Object childElement : setVariativityPage.getCheckedElements()) {
			List childElementEditParts = diagramGraphicalViewer
					.findEditPartsForElement(
							EMFCoreUtil.getProxyID((EObject) childElement),
							TargetRefElementEditPart.class);
			EditPart childEditPart = (EditPart) childElementEditParts.get(0);

			ICommand createChildLinkCmd = new DeferredCreateConnectionViewAndElementCommand(
					new CreateConnectionViewAndElementRequest(
							PlwebElementTypes.SourceRefElementClass_4001,
							((IHintedType) PlwebElementTypes.SourceRefElementClass_4001)
									.getSemanticHint(),
							((GraphicalEditPart) selectedElementPart)
									.getDiagramPreferencesHint()),
					variativityElementViewAdapter, new EObjectAdapter(
							(EObject) childEditPart.getModel()),
					selectedElementPart.getViewer());

			cc2.add(new ICommandProxy(createChildLinkCmd));

			ICommand removeOldLink = new DestroyReferenceCommand(
					new DestroyReferenceRequest(
							(EObject) selectedElement.getModel(),
							((EObject) selectedElement.getModel())
									.eContainmentFeature(),
							(EObject) childElement, false));
			cc2.add(new ICommandProxy(removeOldLink));

			TransactionalEditingDomain childElementEditingDomain = TransactionUtil
					.getEditingDomain(childElement);

			setElementParent(childElement, childElementEditingDomain,
					((NodeImpl) selectedElementPart.getModel()).getElement());
		}
		selectedElement.getDiagramEditDomain().getDiagramCommandStack()
				.execute(cc2);

		EObject modelElement = ((View) mapEditPart.getModel()).getElement();
		List editPolicies = CanonicalEditPolicy
				.getRegisteredEditPolicies(modelElement);
		for (Iterator it = editPolicies.iterator(); it.hasNext();) {
			CanonicalEditPolicy nextEditPolicy = (CanonicalEditPolicy) it
					.next();
			nextEditPolicy.refresh();
		}

		IEditorPart activeEditor = page.getActiveEditor();
		IEditorInput iEditorInput = activeEditor.getEditorInput();
		page.closeEditor(activeEditor, true);
		try {
			page.openEditor(iEditorInput, PlwebDiagramEditor.ID);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private void setGroupParent(Object element,
			TransactionalEditingDomain editingDomain, EObject parent) {

		SetRequest setRequestGroupParent = new SetRequest(editingDomain,
				(EObject) element, PlwebFactoryImpl.eINSTANCE.getPlwebPackage()
						.getTargetRefElement_Parent(), parent);
		SetValueCommand operation = new SetValueCommand(setRequestGroupParent);

		selectedElement.getDiagramEditDomain().getDiagramCommandStack()
				.execute(new ICommandProxy(operation));
	}

	private void setElementParent(Object element,
			TransactionalEditingDomain editingDomain, EObject parent) {

		SetRequest setRequestGroupParent = new SetRequest(editingDomain,
				(EObject) element, PlwebFactoryImpl.eINSTANCE.getPlwebPackage()
						.getTargetRefElement_Parent(), parent);
		SetValueCommand operation = new SetValueCommand(setRequestGroupParent);

		selectedElement.getDiagramEditDomain().getDiagramCommandStack()
				.execute(new ICommandProxy(operation));
	}

	private void setGroupTitle(Object element,
			TransactionalEditingDomain editingDomain) {

		Object obj = (Object) setVariativityPage.getGroupName();
		SetRequest setRequestGroupName = new SetRequest(editingDomain,
				(EObject) element, PlwebFactoryImpl.eINSTANCE.getPlwebPackage()
						.getElement_Title(), obj);

		SetValueCommand operation = new SetValueCommand(setRequestGroupName);

		selectedElement.getDiagramEditDomain().getDiagramCommandStack()
				.execute(new ICommandProxy(operation));
	}

	private void specifyGroupType(EditPart elementPart, Object element,
			TransactionalEditingDomain editingDomain) {
		if ((element instanceof GroupImpl)
				&& setVariativityPage.getGroupType() == "XOR") {
			GroupImpl group = (GroupImpl) ((NodeImpl) elementPart.getModel())
					.getElement();
			SetRequest setRequestGroup = new SetRequest(editingDomain, group,
					PlwebFactoryImpl.eINSTANCE.getPlwebPackage()
							.getGroup_Type(), GroupType.XOR);
			SetValueCommand operation = new SetValueCommand(setRequestGroup);
			selectedElement.getDiagramEditDomain().getDiagramCommandStack()
					.execute(new ICommandProxy(operation));
		}
	}

	private ShapeNodeEditPart getSelectedElement() {
		IWizardContainer container = this.getContainer();
		SetVariativityWizardDialog wizardDialog = (SetVariativityWizardDialog) container;
		return wizardDialog.selectedElement;
	}
}
