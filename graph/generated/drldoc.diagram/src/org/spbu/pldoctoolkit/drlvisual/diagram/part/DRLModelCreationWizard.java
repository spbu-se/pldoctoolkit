package org.spbu.pldoctoolkit.drlvisual.diagram.part;

import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.wizards.EditorCreationWizard;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

/**
 * @generated
 */
public class DRLModelCreationWizard extends EditorCreationWizard {

	/**
	 * @generated
	 */
	public void addPages() {
		super.addPages();
		if (page == null) {
			page = new DRLModelCreationWizardPage(getWorkbench(),
					getSelection());
		}
		addPage(page);
	}

	/**
	 * @generated
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		setWindowTitle("New DRL Model Diagram"); //$NON-NLS-1$
		setDefaultPageImageDescriptor(DRLModelDiagramEditorPlugin
				.getBundledImageDescriptor("icons/wizban/NewdrlWizard.gif")); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}
}
