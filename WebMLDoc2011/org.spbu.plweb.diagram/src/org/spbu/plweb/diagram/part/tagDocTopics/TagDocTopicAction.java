package org.spbu.plweb.diagram.part.tagDocTopics;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.notation.impl.DiagramImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.spbu.plweb.DiagramRoot;
import org.spbu.plweb.diagram.part.PlwebDiagramEditorUtil;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.DocTopicsPartsReader;

;

public class TagDocTopicAction implements IObjectActionDelegate {

	private TagDocTopicActionData data = new TagDocTopicActionData();

	public Shell getShell() {
		return data.targetPart.getSite().getShell();
	}

	@Override
	public void run(IAction action) {
		TagDocTopicWizard wizard = new TagDocTopicWizard();
		wizard.setWindowTitle("Tag DocTopics");
		wizard.setNeedsProgressMonitor(true);
		PlwebDiagramEditorUtil.runTagDocTopicWizard(getShell(), wizard,
				"TagDocTopic", data.selectedElement, data.listOfSelectedElements,
				data.listDocs, data.targetPart);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		data.selectedElement = null;
		data.listOfSelectedElements = new ArrayList<ShapeNodeEditPart>();

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.getFirstElement() instanceof ShapeNodeEditPart) {
				data.selectedElement = (ShapeNodeEditPart) structuredSelection
						.getFirstElement();
				String docPath = ((DiagramRoot) ((DiagramImpl) data.selectedElement
						.getParent().getModel()).getElement()).getDocPath();
				data.listDocs = DocTopicsPartsReader.getListOfDocElements(docPath);

				for (Iterator iterator = structuredSelection.iterator(); iterator
						.hasNext();) {
					ShapeNodeEditPart nextElement = (ShapeNodeEditPart) iterator
							.next();
					data.listOfSelectedElements.add(nextElement);

				}
			}
		}
	}

	@Override
	public void setActivePart(IAction arg0, IWorkbenchPart targetPart) {
		this.data.targetPart = targetPart;
	}

}
