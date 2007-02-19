package org.spbu.pldoctoolkit.wizards;

import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public abstract class NewDocumentationElementWizard extends Wizard implements INewWizard {
	private IWorkbench workbench;
	private IStructuredSelection selection;
	
	private NewDocumentationElementWizardPage page;
	
	@Override
	public void addPages() {
		page = new NewDocumentationElementWizardPage(getPageName(), selection, getTemplateURL());
		page.setTitle(getTitle());
		page.setDescription(getDescription());
		page.setNewFileLabel(getNewFileLabel());
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		IFile file = page.createNewFile();
		if (file == null)
			return false;
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
	}
	
	protected abstract String getPageName();
	
	protected abstract URL getTemplateURL();
	
	protected abstract String getTitle();

	protected abstract String getDescription();
	
	protected abstract String getNewFileLabel();
}
