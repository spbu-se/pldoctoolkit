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
	private final RetargetTextEditorAction exportToPdf = new RetargetTextEditorAction(DrlEditorMessages.getBundle(), "ExportToPdf.");
	private final RetargetTextEditorAction validateDrl = new RetargetTextEditorAction(DrlEditorMessages.getBundle(), "ValidateDrl.");
	private final RetargetTextEditorAction my = new RetargetTextEditorAction(DrlEditorMessages.getBundle(), "My.");
	
	public void contributeToMenu(IMenuManager menu) {
		super.contributeToMenu(menu);
		MenuManager docbookMenu = new MenuManager("DRL");
		menu.insertAfter("additions", docbookMenu);
		docbookMenu.add(exportToHtml);
		docbookMenu.add(exportToPdf);
		docbookMenu.add(validateDrl);
		docbookMenu.add(my);
	}

	public void contributeToToolBar(IToolBarManager toolBarManager) {
		super.contributeToToolBar(toolBarManager);
		toolBarManager.add(new Separator("DocbookEditor"));
		toolBarManager.add(exportToHtml);
		toolBarManager.add(exportToPdf);
		toolBarManager.add(validateDrl);
	}

	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);
		ITextEditor editor = null;
		if (part instanceof ITextEditor)
			editor = (ITextEditor) part;
		exportToHtml.setAction(getAction(editor, DrlTextEditor.EXPORT_TO_HTML));
		exportToPdf.setAction(getAction(editor, DrlTextEditor.EXPORT_TO_PDF));
		validateDrl.setAction(getAction(editor, DrlTextEditor.VALIDATE_DRL));
		my.setAction(getAction(editor, "asd"));
	}
}
