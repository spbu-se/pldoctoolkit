package org.spbu.pldoctoolkit.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.BasicTextEditorActionContributor;
import org.spbu.pldoctoolkit.actions.BasicExportAction;
import org.spbu.pldoctoolkit.actions.EditorAction;
import org.spbu.pldoctoolkit.actions.PdfExportAction;
import org.spbu.pldoctoolkit.actions.ValidateDrlAction;

public class DrlEditorActionContributor extends BasicTextEditorActionContributor {
	private final List<EditorAction> actions = new ArrayList<EditorAction>();
	
	public DrlEditorActionContributor() throws CoreException {
		actions.add(new BasicExportAction("html"));
		actions.add(new PdfExportAction());
		actions.add(new ValidateDrlAction());
	}

	public void contributeToMenu(IMenuManager menu) {
		super.contributeToMenu(menu);
		MenuManager docbookMenu = new MenuManager("DRL");
		menu.insertAfter("additions", docbookMenu);
		for (Action action : actions)
			docbookMenu.add(action);
	}

	public void contributeToToolBar(IToolBarManager toolBarManager) {
		super.contributeToToolBar(toolBarManager);
		toolBarManager.add(new Separator("DocbookEditor"));
		for (EditorAction action : actions)
			toolBarManager.add(action);
	}

	public void setActiveEditor(IEditorPart editor) {
		super.setActiveEditor(editor);
		for (EditorAction action : actions)
			action.setActiveEditor(editor);
	}
}
