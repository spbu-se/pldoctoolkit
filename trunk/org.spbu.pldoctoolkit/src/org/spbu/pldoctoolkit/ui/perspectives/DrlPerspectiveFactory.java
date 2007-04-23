package org.spbu.pldoctoolkit.ui.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class DrlPerspectiveFactory implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		defineActions(layout);
		defineLayout(layout);
	}

	public void defineActions(IPageLayout layout) {
		// Add "new wizards".
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");
		layout.addNewWizardShortcut("org.spbu.pldoctoolkit.text.wizards.new.drlfile");
		layout.addNewWizardShortcut("org.spbu.pldoctoolkit.graph.presentation.DrlModelWizardID");
		layout.addNewWizardShortcut("org.spbu.pldoctoolkit.graph.diagram.infproduct.part.DrlModelCreationWizardID");
		layout.addNewWizardShortcut("org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelCreationWizardID");

		// Add "show views".
		layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
		layout.addShowViewShortcut(IPageLayout.ID_BOOKMARKS);
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);

		layout.addPerspectiveShortcut("org.eclipse.ui.resourcePerspective");
		layout.addPerspectiveShortcut("org.spbu.pldoctoolkit.ui.drlPerspective");
	}

	public void defineLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();

		layout.addView(IPageLayout.ID_RES_NAV, IPageLayout.LEFT, 0.26f, editorArea);
//		layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.BOTTOM,
//				IPageLayout.DEFAULT_VIEW_RATIO, IPageLayout.ID_RES_NAV);

		IFolderLayout bottomFolder = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.75f, editorArea);
		bottomFolder.addView(IPageLayout.ID_PROBLEM_VIEW);
		bottomFolder.addView("org.eclipse.pde.runtime.LogView");
	}
}
