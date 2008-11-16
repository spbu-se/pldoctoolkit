package org.spbu.pldoctoolkit.editors;	

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.RetargetTextEditorAction;
import org.spbu.pldoctoolkit.templates.TemplatesDocument;

public class DrlEditorActionContributor extends TextEditorActionContributor {
	private final RetargetTextEditorAction exportToHtml = new RetargetTextEditorAction(DrlEditorMessages.getBundle(), "ExportToHtml.");
	//private final RetargetTextEditorAction exportToHtmlHelp = new RetargetTextEditorAction(DrlEditorMessages.getBundle(), "ExportToHtml Help.");
	//private final RetargetTextEditorAction exportToDocBook = new RetargetTextEditorAction(DrlEditorMessages.getBundle(), "ExportToDocBook.");
	private final RetargetTextEditorAction exportToPdf = new RetargetTextEditorAction(DrlEditorMessages.getBundle(), "ExportToPdf.");
	private final RetargetTextEditorAction validateDrl = new RetargetTextEditorAction(DrlEditorMessages.getBundle(), "ValidateDrl.");
	private final RetargetTextEditorAction [] insertTemplates = new RetargetTextEditorAction[1000];
	
	public void contributeToMenu(IMenuManager menu) {
		super.contributeToMenu(menu);
		MenuManager docbookMenu = new MenuManager("DRL");
		menu.insertAfter("additions", docbookMenu);
		//docbookMenu.add(exportToDocBook);
		docbookMenu.add(exportToHtml);
		//docbookMenu.add(exportToHtmlHelp);
		docbookMenu.add(exportToPdf);
		docbookMenu.add(validateDrl);
		try{
			TemplatesDocument templatesDocument = new TemplatesDocument();
		    MenuManager subInsertTemplate = new MenuManager("Insert Template");
		    docbookMenu.insert(5, subInsertTemplate);
		    for(int i = 0; i < templatesDocument.numOfTemplates; i++){
			    insertTemplates[i] = new RetargetTextEditorAction(DrlEditorMessages.getBundle(), templatesDocument.templates[i].name+'.');
			    subInsertTemplate.add(insertTemplates[i]);	
		        }	
		} catch(Exception e) {
			e.printStackTrace();
			}
	}

	public void contributeToToolBar(IToolBarManager toolBarManager) {
		super.contributeToToolBar(toolBarManager);
		toolBarManager.add(new Separator("DocbookEditor"));
		//toolBarManager.add(exportToDocBook);
		toolBarManager.add(exportToHtml);
		//toolBarManager.add(exportToHtmlHelp);
		toolBarManager.add(exportToPdf);
		toolBarManager.add(validateDrl);
	}

	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);
		ITextEditor editor = null;
		if (part instanceof ITextEditor)
			editor = (ITextEditor) part;
		//exportToDocBook.setAction(getAction(editor, DrlTextEditor.EXPORT_TO_DB));
		exportToHtml.setAction(getAction(editor, DrlTextEditor.EXPORT_TO_HTML));
		//exportToHtmlHelp.setAction(getAction(editor, DrlTextEditor.EXPORT_TO_HH));
		exportToPdf.setAction(getAction(editor, DrlTextEditor.EXPORT_TO_PDF));
		validateDrl.setAction(getAction(editor, DrlTextEditor.VALIDATE_DRL));
		TemplatesDocument templatesDocument = new TemplatesDocument();
	    for(int i = 0; i < templatesDocument.numOfTemplates; i++){
		    insertTemplates[i].setAction(getAction(editor, DrlTextEditor.INSERT_TEMPLATES[i]));
	    }
	}
}
