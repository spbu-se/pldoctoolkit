package org.spbu.pldoctoolkit.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.editors.DrlTextEditor;
import org.spbu.pldoctoolkit.templates.TemplatesDocument;

public class InsertTemplateAction extends Action{
	protected final DrlTextEditor editor;
	TextEditor textEditor;
	String TYPE;
	
	public InsertTemplateAction(DrlTextEditor editor, String name){
		super(name);
		this.editor = editor;
		textEditor = (TextEditor) editor;
		this.TYPE = name;
	}

	public void run(){	
        FileEditorInput editorInput = (FileEditorInput) editor.getEditorInput();
        IDocument doc = textEditor.getDocumentProvider().getDocument(editorInput);
      
        ISourceViewer viewer = editor.DrlGetSourceViewer();
		StyledText textWidget = viewer.getTextWidget();
		int offset = textWidget.getCaretOffset();
        

		try  { 
			
			TemplatesDocument templatesDocument = new TemplatesDocument();
		    for(int i = 0; i < templatesDocument.numOfTemplates; i++){
		        if (templatesDocument.templates[i].name.equals(TYPE)){
			        	doc.replace(offset, 0, templatesDocument.templates[i].textContent);
			        }
			    }	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
