package pldoctoolkit;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {
	public void createInitialLayout(IPageLayout layout) {
		defineActions(layout);
		defineLayout(layout);
	}

	@SuppressWarnings("deprecation")
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
		//IFolderLayout leftFolder = layout.createFolder("left", IPageLayout.LEFT, 0.3f, editorArea);
		//leftFolder.addView("org.eclipse.jdt.ui.PackageExplorer");
		layout.addView("plpdoctoolkit.NavigatorView", IPageLayout.LEFT, 0.3f, editorArea);
		//layout.addView(IPageLayout.ID_PROJECT_EXPLORER, IPageLayout.LEFT, 0.3f, editorArea);
		IFolderLayout bottomFolder = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.65f, editorArea);
		bottomFolder.addView(IPageLayout.ID_PROBLEM_VIEW);
		//bottomFolder.addView("org.eclipse.pde.runtime.LogView");
	}
}
