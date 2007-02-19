package org.spbu.pldoctoolkit.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public abstract class NewDocumentationElementWizard extends Wizard implements INewWizard {
	protected IWorkbench workbench;
	protected IStructuredSelection selection;
	
	private NewDocumentationElementWizardPage page;
	
	public NewDocumentationElementWizard() {
		setWindowTitle("New Documentation Element");
	}
	
	@Override
	public void addPages() {
		page = createPage();
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		IFile file = page.createNewFile();
		if (file == null)
			return false;
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			IDE.openEditor(page, file);
		} catch (PartInitException e) {
			return false;
		}
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
	}
	
	protected abstract NewDocumentationElementWizardPage createPage();
}
