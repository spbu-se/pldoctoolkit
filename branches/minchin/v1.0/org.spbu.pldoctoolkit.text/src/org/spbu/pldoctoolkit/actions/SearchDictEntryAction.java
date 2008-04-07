package org.spbu.pldoctoolkit.actions;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.dialogs.InsertIntoDictionaryDialog;
import org.spbu.pldoctoolkit.dialogs.SearchDictEntryDialog;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.InsertIntoDictionary;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.refactor.SearchDictEntry;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;

public class SearchDictEntryAction extends Action implements IValidateDRLSelection {
	IEditorPart editor;
	TextEditor te;
	IProject project;
	
	SearchDictEntry refact = new SearchDictEntry();
	ProjectContent projectContent;// = new ProjectContent();
	FileEditorInput editorInput;
	
	public final static String refactName = "Search dictionary entry"; 
	
	public SearchDictEntryAction(IEditorPart editor) throws Exception {
		super(refactName);
		this.editor = editor;
		te = (TextEditor)editor;
		
		editorInput = (FileEditorInput) editor.getEditorInput();
		project = editorInput.getFile().getProject();
		projectContent = (ProjectContent)((ProjectRegistryImpl)PLDocToolkitPlugin.getRegistry(project.getName())).projectContent;
		DRLMenuListener.instance.addListener(this);
	}
	
	public void validateSelection(IEditorPart part) {		
		ISelection sel = te.getSelectionProvider().getSelection();
		TextSelection ts = (TextSelection) sel;
		//te.set
		IDocument doc = te.getDocumentProvider().getDocument(editorInput);
		DRLDocument DRLdoc = projectContent.DRLDocs.get(editorInput.getFile());
		try {
			int line1 = ts.getStartLine();
			int column1 = ts.getOffset() - doc.getLineOffset(line1);
			
			PositionInText pos1 = new PositionInText(line1 + 1, column1 + 1);
						
			refact.reset();
			refact.setValidationPararams( DRLdoc,pos1);						   			  	
			
			boolean res = refact.validate();
			setEnabled(res);
		} catch (Exception e) {
			setEnabled(false);
			e.printStackTrace();
		}		
	}
	
	public void run() {	
		//SearchDictEntryDialog ddd = new SearchDictEntryDialog(editor.getSite().getShell());
		//ddd.open();
		
		//if (true)return;
		/*
		IWorkbenchWindow w = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		Iterator<IFile> it = projectContent.DRLDocs.keySet().iterator();
		IFile file = it.next();
		FileEditorInput in = new FileEditorInput(file);
		try {
			w.getActivePage().openEditor(in, "org.spbu.pldoctoolkit.editors.DRLEditor");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		*/
		SearchDictEntryDialog dialog = new SearchDictEntryDialog(editor.getSite().getShell(), te, refact);
		//ArrayList<LangElem> dicts = refact.getPossibleDicts();
		//dialog.setDicts(dicts);
		//dialog.w = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		
		int res = dialog.open();
		if ( res != Window.OK)
			return;	
		/*
		IDocument doc = te.getDocumentProvider().getDocument(editorInput);
		String text = doc.get();
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
			
			//refact.setPararams(pos1, pos2);			
			refact.perform();
			
			projectContent.saveAll();		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}*/		
	}

}
