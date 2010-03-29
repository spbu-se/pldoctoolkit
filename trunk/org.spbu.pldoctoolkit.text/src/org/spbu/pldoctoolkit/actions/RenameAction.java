package org.spbu.pldoctoolkit.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.editors.DrlTextEditor;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.Rename;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;

public class RenameAction extends Action implements IValidateDRLSelection{
	
	IEditorPart editor;
	TextEditor te;
	IProject project;
	
	Rename refact = new Rename();
	ProjectContent projectContent;
	FileEditorInput editorInput;

	public final static String refactName = "Rename"; 
	
	
	public RenameAction(IEditorPart editor) throws Exception {
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
		
		    refact.setValidationParams(DRLdoc, startPosition, endPosition);
			refactoringEnable = refact.validate();
				
			setEnabled(refactoringEnable);
		} catch (BadLocationException  e) {
			setEnabled(false);
			e.printStackTrace();
		}		
		
	}

	public void run(){
		InputDialog newNameDialog = new InputDialog(
				editor.getSite().getShell(), 
				"Rename element", "Enter new id of the "+refact.elementType+" \""+refact.oldName+"\":", 
				refact.oldName, 
				new IInputValidator() {
					@Override
					public String isValid(String newText) {
						for (LangElem nest : LangElem.getElemList(projectContent, refact.elementType)) {
							if (nest.attrs.getValue(LangElem.ID).equals(newText))
								return "Such id exists";
						}
						return null;
					}			
		});
		
		int res = newNameDialog.open();
		if (res != Window.OK)
			return;
		
		try {
		    refact.setParams(newNameDialog.getValue(),projectContent.DRLDocs.values());			
		    refact.perform();
			projectContent.saveAll();		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}	
		
	}
	
}
