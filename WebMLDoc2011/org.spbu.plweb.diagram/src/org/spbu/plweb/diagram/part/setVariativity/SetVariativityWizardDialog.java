package org.spbu.plweb.diagram.part.setVariativity;

import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

public class SetVariativityWizardDialog extends WizardDialog {
	public ShapeNodeEditPart selectedElement;
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

	public SetVariativityWizardDialog(Shell parentShell, IWizard newWizard,
			ShapeNodeEditPart selectedElement, IWorkbenchPart targetPart) {
		super(parentShell, newWizard);
		this.selectedElement = selectedElement;
		this.targetPart = targetPart;
		
	}

}
