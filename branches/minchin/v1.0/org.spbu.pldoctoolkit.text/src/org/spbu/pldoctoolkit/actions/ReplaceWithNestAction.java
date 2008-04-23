package org.spbu.pldoctoolkit.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.CreateNest;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.refactor.ReplaceWithNest;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;

public class ReplaceWithNestAction extends Action implements IValidateDRLSelection{//SelectionProviderAction{
	IEditorPart editor;
	TextEditor te;
	IProject project;
	
	ReplaceWithNest refact = new ReplaceWithNest();
	CreateNest createRefact = new CreateNest();
	ProjectContent projectContent;// = new ProjectContent();
	FileEditorInput editorInput;
	
	public final static String refactName = "Replace with nest";
	
	public boolean replace;
	
	public ReplaceWithNestAction(IEditorPart editor) throws Exception {
		super(refactName);
		this.editor = editor;
		te = (TextEditor)editor;
		
		editorInput = (FileEditorInput) editor.getEditorInput();
		project = editorInput.getFile().getProject();
		projectContent = (ProjectContent)((ProjectRegistryImpl)PLDocToolkitPlugin.getRegistry(project.getName())).projectContent;
		DRLMenuListener.instance.addListener(this);
	}
	
	public void validateSelection(IEditorPart part) {
		//projectContent.parseAll(project);
		
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
			
			boolean res;
			if (pos1.compare(pos2) == 0) {
				createRefact.reset();
				createRefact.setValidationPararams(DRLdoc, pos1);
				res = createRefact.validate();
				replace = false;
			}
			else {		
				refact.reset();
				refact.setValidationPararams( DRLdoc, pos1, pos2);
				res = refact.validate();
				replace = true;
			}
						
			setEnabled(res);
		} catch (Exception e) {
			setEnabled(false);
			e.printStackTrace();
		}		
	}
	
	public void run() {	
		String nestId = "nestId";
		String resId = "";
		int i = projectContent.nests.size();
		boolean goodId = false;
		while (!goodId) {
			resId = nestId + String.valueOf(i);
			goodId = true;
			for (LangElem nest : projectContent.nests) {
				if (nest.attrs.getValue(LangElem.ID).equals(resId)) {
					goodId = false;
					break;
				}
			}
			++i;
		}
		
		InputDialog dialog = new InputDialog(editor.getSite().getShell(), "Create nest...", "Replace selected fragment with nest.\nEnter id of new Nest", resId, 
				new IInputValidator() {
					@Override
					public String isValid(String newText) {
						for (LangElem nest : projectContent.nests) {
							if (nest.attrs.getValue(LangElem.ID).equals(newText))
								return "Such id exists";
						}
						
						return null;
					}			
		});		
		
		int res = dialog.open();
		if ( res != Window.OK)
			return;
	
		try {
			if (replace) {
				refact.setPararams(dialog.getValue());			
				refact.perform();
			}
			else {
				createRefact.setPararams(dialog.getValue());			
				createRefact.perform();
			}
			
			projectContent.saveAll();		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}	
}