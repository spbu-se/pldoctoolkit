package org.spbu.pldoctoolkit.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.BasicTextEditorActionContributor;
import org.spbu.pldoctoolkit.actions.AbstractExportAction;
import org.spbu.pldoctoolkit.actions.BasicExportAction;
import org.spbu.pldoctoolkit.actions.PdfExportAction;

public class DrlEditorActionContributor extends BasicTextEditorActionContributor {
	private final List<AbstractExportAction> actions = new ArrayList<AbstractExportAction>();
	
	public DrlEditorActionContributor() {
		actions.add(new BasicExportAction("html"));
		actions.add(new PdfExportAction());
	}

	public void contributeToMenu(IMenuManager menu) {
		super.contributeToMenu(menu);
		MenuManager docbookMenu = new MenuManager("DRL");
		menu.insertAfter("additions", docbookMenu);
		for (AbstractExportAction action : actions)
			docbookMenu.add(action);
	}

	public void contributeToToolBar(IToolBarManager toolBarManager) {
		super.contributeToToolBar(toolBarManager);
		toolBarManager.add(new Separator("DocbookEditor"));
		for (AbstractExportAction action : actions)
			toolBarManager.add(action);
	}

	public void setActiveEditor(IEditorPart editor) {
		super.setActiveEditor(editor);
		for (AbstractExportAction action : actions)
			action.setActiveEditor(editor);
	}
}
