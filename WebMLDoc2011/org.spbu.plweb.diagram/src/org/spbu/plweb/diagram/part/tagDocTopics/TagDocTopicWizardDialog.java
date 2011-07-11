package org.spbu.plweb.diagram.part.tagDocTopics;

import java.util.List;

import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

public class TagDocTopicWizardDialog extends WizardDialog {
	private ShapeNodeEditPart selectedElement;
	private List<ShapeNodeEditPart> listOfSelectedElement;
	private List<String> listOfTopics;

	public List<String> getListOfTopics() {
		return listOfTopics;
	}

	public List<ShapeNodeEditPart> getListOfSelectedElement() {
		return listOfSelectedElement;
	}

	public void setListOfSelectedElement(
			List<ShapeNodeEditPart> listOfSelectedElement) {
		this.listOfSelectedElement = listOfSelectedElement;
	}

	public IWorkbenchPart targetPart;

	public IWorkbenchPart getTargetPart() {
		return targetPart;
	}

	public void setTargetPart(IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

	public ShapeNodeEditPart getSelectedElement() {
		return selectedElement;
	}

	public void setSelectedElement(ShapeNodeEditPart selectedElement) {
		this.selectedElement = selectedElement;
	}

	public TagDocTopicWizardDialog(Shell parentShell, IWizard newWizard,
			ShapeNodeEditPart selectedElement, List<ShapeNodeEditPart> listOfSelectedElement,List<String> listOfTopics, IWorkbenchPart targetPart) {
		super(parentShell, newWizard);
		this.selectedElement = selectedElement;
		this.targetPart = targetPart;
		this.listOfSelectedElement = listOfSelectedElement;
		this.listOfTopics = listOfTopics;

	}

}
