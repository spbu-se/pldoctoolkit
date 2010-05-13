package org.spbu.pldoctoolkit.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.editors.DrlTextEditor;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.refactor.MakeRefOptional;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;

public class MakeRefOptionalAction extends Action implements IValidateDRLSelection{
	IEditorPart editor;
	TextEditor te;
	IProject project;
	
	MakeRefOptional refact = new MakeRefOptional();
	ProjectContent projectContent;
	FileEditorInput editorInput;
	
	public static String refactName = "Make Ref Optional"; 
	
	public MakeRefOptionalAction(IEditorPart editor) throws Exception {
		super(refactName);
		this.editor = editor;
		te = (TextEditor)editor;
		editorInput = (FileEditorInput) editor.getEditorInput();
		project = editorInput.getFile().getProject();
		projectContent = (ProjectContent)((ProjectRegistryImpl)PLDocToolkitPlugin.getRegistry(project.getName())).projectContent;
		((DrlTextEditor)te).getMenuListener().addListener(this);
	}
	
	@Override
	public void validateSelection(IEditorPart part) {
		ISelection sel = te.getSelectionProvider().getSelection();
		TextSelection ts = (TextSelection) sel;
		
		IDocument doc = te.getDocumentProvider().getDocument(editorInput);
		DRLDocument DRLdoc = projectContent.DRLDocs.get(editorInput.getFile());
		try {
			int startLine = ts.getStartLine();
			int startColumn = ts.getOffset() - doc.getLineOffset(startLine);
			int endLine = ts.getEndLine();
			int endColumn = ts.getOffset() + ts.getLength() - doc.getLineOffset(endLine);
			
			PositionInText startPosition = new PositionInText(startLine + 1, startColumn + 1);
			PositionInText endPosition = new PositionInText(endLine + 1, endColumn + 1);
	
			boolean refactoringEnable;
		
		    refact.setValidationParams(projectContent, DRLdoc, startPosition, endPosition);
			refactoringEnable = refact.validate();
				
			setEnabled(refactoringEnable);
		} catch (Exception  e) {
			setEnabled(false);
			e.printStackTrace();
		}		
		
	}
    public void run() {
    	IDocument doc = te.getDocumentProvider().getDocument(editorInput);
		ISelection sel = te.getSelectionProvider().getSelection();
		TextSelection ts = (TextSelection) sel;
		try {			
			DRLDocument DRLdoc = projectContent.DRLDocs.get(editorInput.getFile());
			int line1 = ts.getStartLine();
			int column1 = ts.getOffset() - doc.getLineOffset(line1);
			int line2 = ts.getEndLine();
			int column2 = ts.getOffset() + ts.getLength() - doc.getLineOffset(line2);
			PositionInText pos1 = new PositionInText(line1 + 1, column1 + 1);
			PositionInText pos2 = new PositionInText(line2 + 1, column2 + 1);
			
			refact.setParams(projectContent, DRLdoc, pos1,pos2);
					
			refact.perform();
			
			projectContent.saveAll();		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}

}
