package org.spbu.pldoctoolkit.actions;

import java.util.ArrayList;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.dialogs.HandleDIffWithAnotherInfElemDialog;
import org.spbu.pldoctoolkit.dialogs.InsertIntoDictionaryDialog;
import org.spbu.pldoctoolkit.editors.DrlTextEditor;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.HandleDIffWithAnotherInfElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;


public class HandleDIffWithAnotherInfElemAction extends Action implements
		IValidateDRLSelection {

	IEditorPart editor;
	TextEditor te;
	IProject project;
	HandleDIffWithAnotherInfElem refact = new HandleDIffWithAnotherInfElem();
	ProjectContent projectContent;
	FileEditorInput editorInput;
	
	public static String refactName = "Handle DIff With Another Inf Element";
	
	public HandleDIffWithAnotherInfElemAction(IEditorPart editor) throws Exception {
		super(refactName);
		this.editor = editor;
		te = (TextEditor) editor;
		editorInput = (FileEditorInput) editor.getEditorInput();
		project = editorInput.getFile().getProject();
		projectContent = (ProjectContent) ((ProjectRegistryImpl) PLDocToolkitPlugin
				.getRegistry(project.getName())).projectContent;
		((DrlTextEditor) te).getMenuListener().addListener(this);
	}
	
	@Override
	public void validateSelection(IEditorPart part) {
		// TODO Auto-generated method stub

	}

	public void run() {

		HandleDIffWithAnotherInfElemDialog dialog = new HandleDIffWithAnotherInfElemDialog(editor.getSite().getShell(), projectContent);
		
		int res = dialog.open();
		if ( res != Window.OK)
			return;
		/*DRLDocument DRLdoc = projectContent.DRLDocs.get(editorInput.getFile());
		IDocument doc = te.getDocumentProvider().getDocument(editorInput);
		ISelection sel = te.getSelectionProvider().getSelection();
		TextSelection ts = (TextSelection) sel;
		try {
			int line1 = ts.getStartLine();
			int column1 = ts.getOffset() - doc.getLineOffset(line1);
			int line2 = ts.getEndLine();
			int column2 = ts.getOffset() + ts.getLength()
					- doc.getLineOffset(line2);
			PositionInText startPos = new PositionInText(line1 + 1, column1 + 1);
			PositionInText endPos = new PositionInText(line2 + 1, column2 + 1);

			if(startPos.compare(endPos) != 0)
				return;
			
			refact.setParams(projectContent, DRLdoc, editor, startPos);
			refact.perform();

			projectContent.saveAll();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
}
