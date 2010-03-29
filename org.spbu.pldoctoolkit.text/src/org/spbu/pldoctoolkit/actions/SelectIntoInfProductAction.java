package org.spbu.pldoctoolkit.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.dialogs.SelectIntoInfProdDialog;
import org.spbu.pldoctoolkit.editors.DrlTextEditor;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.refactor.SelectIntoInfProd;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;

public class SelectIntoInfProductAction extends Action implements IValidateDRLSelection{
	IEditorPart editor;
	TextEditor te;
	IProject project;
	
	SelectIntoInfProd refact = new SelectIntoInfProd();
	ProjectContent projectContent;
	FileEditorInput editorInput;
	
	public final static String refactName = "Extract into InfProduct"; 
	
	public SelectIntoInfProductAction(IEditorPart editor) throws Exception {
		super(refactName);
		this.editor = editor;
		te = (TextEditor)editor;
		editorInput = (FileEditorInput) editor.getEditorInput();
		project = editorInput.getFile().getProject();
		projectContent = (ProjectContent)((ProjectRegistryImpl)PLDocToolkitPlugin.getRegistry(project.getName())).projectContent;
		((DrlTextEditor)te).getMenuListener().addListener(this);
	}
	
	public void validateSelection(IEditorPart part) {
		
		ISelection sel = te.getSelectionProvider().getSelection();
		TextSelection ts = (TextSelection) sel;
		
		IDocument doc = te.getDocumentProvider().getDocument(editorInput);
		DRLDocument DRLdoc = projectContent.DRLDocs.get(editorInput.getFile());
		try {
			int line1 = ts.getStartLine();
			int column1 = ts.getOffset() - doc.getLineOffset(line1);
			int line2 = ts.getEndLine();
			int column2 = ts.getOffset() + ts.getLength() - doc.getLineOffset(line2);
			PositionInText pos1 = new PositionInText(line1 + 1, column1 + 1);
			PositionInText pos2 = new PositionInText(line2 + 1, column2 + 1);
			
			if (pos1.compare(pos2) == 0) {
				setEnabled(false);
				return;
			}
				
		
			refact.reset();
			refact.setValidationParams(projectContent, DRLdoc, pos1, pos2);			
			
			boolean res = refact.validate();
			setEnabled(res);
		} catch (Exception e) {
			setEnabled(false);
			e.printStackTrace();
		}		
	}

	public void run() {
		//read names and ids
		SelectIntoInfProdDialog dialog = new SelectIntoInfProdDialog(editor.getSite().getShell(), project.getFullPath());
		
		for (LangElem refs : projectContent.infElemRefs) {
			dialog.addInfElemRefId(refs.attrs.getValue("id"));
		}
		for (LangElem elems : projectContent.infElems) {
			dialog.addInfElemId(elems.attrs.getValue("id"));
		}
		for (LangElem prods : projectContent.infPrs) {
			dialog.addInfProdIds(prods.attrs.getValue("id"));
		}
		for (LangElem fprods : projectContent.finalInfPrs) {
			dialog.addFinInfProdIds(fprods.attrs.getValue("id"));
		}
		
		int res = dialog.open();
		if ( res != Window.OK)
			return;
		
		//perform
		
		IDocument doc = te.getDocumentProvider().getDocument(editorInput);
		ISelection sel = te.getSelectionProvider().getSelection();
		TextSelection ts = (TextSelection) sel;
		try {			
			DRLDocument DRLdoc = projectContent.DRLDocs.get(editorInput.getFile());
			int line1 = ts.getStartLine();
			int column1 = ts.getOffset() - doc.getLineOffset(line1);
			int line2 = ts.getEndLine();
			int column2 = ts.getOffset() + ts.getLength() - doc.getLineOffset(line2);
			
			refact.setParams(dialog.getInfprodId(), dialog.getInfprodName(), dialog.getFinInfProdId(),
					dialog.getInfElemId(), dialog.getInfElemName(), dialog.getInfElemRefInText(),
					dialog.getInfElemRefInProduct(), projectContent, DRLdoc, 
					new PositionInText(line1 + 1, column1 + 1),
					new PositionInText(line2 + 1, column2 + 1),
					project.getFullPath(), dialog.getInfProdFile(), dialog.getFinalProdFile());
			
			//refact.setPararams(dialog.getInfElemId(), dialog.getInfElemName(), dialog.getInfElemRefId(),
			//				   projectContent, DRLdoc, 
			//				   new PositionInText(line1 + 1, column1 + 1),
			//				   new PositionInText(line2 + 1, column2 + 1));			
			refact.perform();
			
			projectContent.saveAll();		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		
	}
}
