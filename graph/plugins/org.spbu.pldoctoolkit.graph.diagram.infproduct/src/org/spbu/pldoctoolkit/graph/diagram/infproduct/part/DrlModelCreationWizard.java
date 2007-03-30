package org.spbu.pldoctoolkit.graph.diagram.infproduct.part;

import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.wizards.EditorCreationWizard;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

/**
 * @generated
 */
public class DrlModelCreationWizard extends EditorCreationWizard {

	/**
	 * @generated
	 */
	public void addPages() {
		super.addPages();
		if (page == null) {
			page = new DrlModelCreationWizardPage(getWorkbench(),
					getSelection());
		}
		addPage(page);
	}

	/**
	 * @generated
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		setWindowTitle("New DrlModel Diagram"); //$NON-NLS-1$
		setDefaultPageImageDescriptor(DrlModelDiagramEditorPlugin
				.getBundledImageDescriptor("icons/wizban/NewDrlWizard.gif")); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}
}
