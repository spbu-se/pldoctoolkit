package org.spbu.pldoctoolkit.editors;	

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.RetargetTextEditorAction;

public class DrlEditorActionContributor extends TextEditorActionContributor {
	private final RetargetTextEditorAction exportToHtml = new RetargetTextEditorAction(DrlEditorMessages.getBundle(), "ExportToHtml.");
//	private final RetargetTextEditorAction validateDrl = new RetargetTextEditorAction(DrlEditorMessages.getBundle(), "ValidateDrl.");
	
	public void contributeToMenu(IMenuManager menu) {
		super.contributeToMenu(menu);
		MenuManager docbookMenu = new MenuManager("DRL");
		menu.insertAfter("additions", docbookMenu);
		docbookMenu.add(exportToHtml);
//		docbookMenu.add(validateDrl);
	}

	public void contributeToToolBar(IToolBarManager toolBarManager) {
		super.contributeToToolBar(toolBarManager);
		toolBarManager.add(new Separator("DocbookEditor"));
		toolBarManager.add(exportToHtml);
//		toolBarManager.add(validateDrl);
	}

	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);
		ITextEditor editor = null;
		if (part instanceof ITextEditor)
			editor = (ITextEditor) part;
		exportToHtml.setAction(getAction(editor, DRLEditor.EXPORT_TO_HTML));
//		validateDrl.setAction(getAction(editor, DRLEditor.VALIDATE_DRL));
	}
}
