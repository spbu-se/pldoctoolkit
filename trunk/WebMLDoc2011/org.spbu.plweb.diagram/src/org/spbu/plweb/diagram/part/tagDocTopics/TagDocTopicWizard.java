package org.spbu.plweb.diagram.part.tagDocTopics;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.spbu.plweb.Area;
import org.spbu.plweb.Group;
import org.spbu.plweb.Node;
import org.spbu.plweb.Page;
import org.spbu.plweb.Root;
import org.spbu.plweb.SiteView;
import org.spbu.plweb.diagram.edit.commands.DocTopic3CreateCommand;
import org.spbu.plweb.diagram.edit.commands.DocTopicCreateCommand;
import org.spbu.plweb.diagram.edit.parts.AreaEditPart;
import org.spbu.plweb.diagram.edit.parts.DiagramRootEditPart;
import org.spbu.plweb.diagram.edit.parts.GroupEditPart;
import org.spbu.plweb.diagram.edit.parts.NodeEditPart;
import org.spbu.plweb.diagram.edit.parts.PageEditPart;
import org.spbu.plweb.diagram.edit.parts.RootEditPart;
import org.spbu.plweb.diagram.edit.parts.SiteViewEditPart;
import org.spbu.plweb.diagram.edit.parts.TargetRefElementEditPart;
import org.spbu.plweb.diagram.edit.policies.DocTopicCanonicalEditPolicy;
import org.spbu.plweb.diagram.edit.policies.SiteViewItemSemanticEditPolicy;
import org.spbu.plweb.diagram.providers.PlwebElementTypes;
import org.spbu.plweb.impl.PlwebFactoryImpl;

public class TagDocTopicWizard extends Wizard {

	private TagDocTopicPage tagDocTopicPage;

	private ShapeNodeEditPart selectedElementOne;
	private List <ShapeNodeEditPart> listOfSelectedElements;

	public List<ShapeNodeEditPart> getListOfSelectedElements() {
		IWizardContainer container = this.getContainer();
		TagDocTopicWizardDialog wizardDialog = (TagDocTopicWizardDialog) container;
		
		
		return wizardDialog.getListOfSelectedElement();
	}

	public void setListOfSelectedElements(
			List<ShapeNodeEditPart> listOfSelectedElements) {
		this.listOfSelectedElements = listOfSelectedElements;
	}

	public TagDocTopicWizard() {
		tagDocTopicPage = new TagDocTopicPage("WtfTitle");
		tagDocTopicPage.setTitle("Select topics to tag");

	}

	private ShapeNodeEditPart getSelectedElement() {
		IWizardContainer container = this.getContainer();
		TagDocTopicWizardDialog wizardDialog = (TagDocTopicWizardDialog) container;
		
		
		return wizardDialog.getSelectedElement();
	}
	
	

	@Override
	public void addPages() {
		addPage(tagDocTopicPage);
	}

	@Override
	public boolean performFinish() {
		selectedElementOne = getSelectedElement();
		listOfSelectedElements = getListOfSelectedElements();
		// Create new node
		for (ShapeNodeEditPart selectedElement : listOfSelectedElements) {
			
		
		CreateViewRequest newNodeRequest = null;
		if (selectedElement instanceof TargetRefElementEditPart) {

			Object[] listCheckedElements = tagDocTopicPage.getCheckedElements();

			for (Object checkedElement : listCheckedElements) {
				CompoundCommand compoundAddTopicCommand = new CompoundCommand(
						"Create docTopic");
				TargetRefElementEditPart TREPselElement = (TargetRefElementEditPart) selectedElement;
				Command addDocTopicCommand;
				if (TREPselElement instanceof SiteViewEditPart) {
					newNodeRequest = CreateViewRequestFactory
							.getCreateShapeRequest(
									PlwebElementTypes.DocTopic_3006,
									selectedElement.getDiagramPreferencesHint());
				} else if (TREPselElement instanceof AreaEditPart) {
					newNodeRequest = CreateViewRequestFactory
							.getCreateShapeRequest(
									PlwebElementTypes.DocTopic_3007,
									selectedElement.getDiagramPreferencesHint());
				} else if (TREPselElement instanceof PageEditPart) {
					newNodeRequest = CreateViewRequestFactory
							.getCreateShapeRequest(
									PlwebElementTypes.DocTopic_3008,
									selectedElement.getDiagramPreferencesHint());
				} else if (TREPselElement instanceof NodeEditPart) {
					newNodeRequest = CreateViewRequestFactory
							.getCreateShapeRequest(
									PlwebElementTypes.DocTopic_3009,
									selectedElement.getDiagramPreferencesHint());
				} else
					return true;
				addDocTopicCommand = TREPselElement.getCommand(newNodeRequest);
				IAdaptable newTopicElementViewAdapter = (IAdaptable) ((List) newNodeRequest
						.getNewObject()).get(0);

				compoundAddTopicCommand.add(addDocTopicCommand);
				selectedElement.getDiagramEditDomain().getDiagramCommandStack()
						.execute(compoundAddTopicCommand);
				final EditPartViewer selectedElementViewer = selectedElement
						.getViewer();
				final EditPart newTopicElementPart = (EditPart) selectedElementViewer
						.getEditPartRegistry().get(
								newTopicElementViewAdapter
										.getAdapter(View.class));

				Object element = ((org.eclipse.gmf.runtime.notation.impl.NodeImpl) newTopicElementPart
						.getModel()).getElement();
				TransactionalEditingDomain editingDomain = TransactionUtil
						.getEditingDomain(element);

				setTopicTitle(selectedElement, element, editingDomain, (String) checkedElement);
			}

		}
		else {

			Object[] listCheckedElements = tagDocTopicPage.getCheckedElements();

			for (Object checkedElement : listCheckedElements) {
				CompoundCommand compoundAddTopicCommand = new CompoundCommand(
						"Create docTopic");
				ShapeNodeEditPart SNEPselElement = (ShapeNodeEditPart) selectedElement;
				Command addDocTopicCommand;
				if (SNEPselElement instanceof RootEditPart) {
					newNodeRequest = CreateViewRequestFactory
							.getCreateShapeRequest(
									PlwebElementTypes.DocTopic_3005,
									selectedElement.getDiagramPreferencesHint());
				}
				addDocTopicCommand = SNEPselElement.getCommand(newNodeRequest);
				IAdaptable newTopicElementViewAdapter = (IAdaptable) ((List) newNodeRequest
						.getNewObject()).get(0);

				compoundAddTopicCommand.add(addDocTopicCommand);
				selectedElement.getDiagramEditDomain().getDiagramCommandStack()
						.execute(compoundAddTopicCommand);
				final EditPartViewer selectedElementViewer = selectedElement
						.getViewer();
				final EditPart newTopicElementPart = (EditPart) selectedElementViewer
						.getEditPartRegistry().get(
								newTopicElementViewAdapter
										.getAdapter(View.class));

				Object element = ((org.eclipse.gmf.runtime.notation.impl.NodeImpl) newTopicElementPart
						.getModel()).getElement();
				TransactionalEditingDomain editingDomain = TransactionUtil
						.getEditingDomain(element);

				setTopicTitle(selectedElement, element, editingDomain, (String) checkedElement);
			}

		}
		}
		return true;
	}

	private void setTopicTitle(ShapeNodeEditPart selectedElement, Object element,
			TransactionalEditingDomain editingDomain, String name) {

		Object obj = name;
		SetRequest setRequestGroupName = new SetRequest(editingDomain,
				(EObject) element, PlwebFactoryImpl.eINSTANCE.getPlwebPackage()
						.getDocTopic_DocTopicName(), obj);

		SetValueCommand operation = new SetValueCommand(setRequestGroupName);

		selectedElement.getDiagramEditDomain().getDiagramCommandStack()
				.execute(new ICommandProxy(operation));
	}

}
