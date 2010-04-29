package org.spbu.pldoctoolkit.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.dialogs.SearchDirRefDialog;
import org.spbu.pldoctoolkit.editors.DrlTextEditor;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;

public class SearchDirRefAction extends Action implements IValidateDRLSelection {
	IEditorPart editor;
	TextEditor te;
	IProject project;
	
	//SearchDirEntry refact = new SearchDirEntry();
	ProjectContent projectContent;// = new ProjectContent();
	FileEditorInput editorInput;
	
	public final static String refactName = "Search directory ref"; 
	
	public SearchDirRefAction(IEditorPart editor) throws Exception {
		super(refactName);
		this.editor = editor;
		te = (TextEditor)editor;
		
		editorInput = (FileEditorInput) editor.getEditorInput();
		project = editorInput.getFile().getProject();
		projectContent = (ProjectContent)((ProjectRegistryImpl)PLDocToolkitPlugin.getRegistry(project.getName())).projectContent;
		((DrlTextEditor)te).getMenuListener().addListener(this);
	}
	
	public void validateSelection(IEditorPart part) {		
		setEnabled(true);
	}
	
	public void run() {		
		SearchDirRefDialog dialog = new SearchDirRefDialog(editor.getSite().getShell(), te);

		int res = dialog.open();
		if ( res != Window.OK)
			return;		
	}

}