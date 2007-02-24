package org.spbu.pldoctoolkit.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public abstract class NewWizard extends Wizard implements INewWizard {
	protected NewWizardPage page;
	protected IStructuredSelection selection;
	
	@Override
	public void addPages() {
		page = createPage();
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		
		return false;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	protected abstract NewWizardPage createPage();
}
