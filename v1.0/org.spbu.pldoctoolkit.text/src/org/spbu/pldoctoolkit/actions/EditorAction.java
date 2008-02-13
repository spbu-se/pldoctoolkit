package org.spbu.pldoctoolkit.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;

public class EditorAction extends Action {
	protected IEditorPart editor;

	public EditorAction() {
		super();
	}

	public EditorAction(String text, ImageDescriptor image) {
		super(text, image);
	}

	public EditorAction(String text, int style) {
		super(text, style);
	}

	public EditorAction(String text) {
		super(text);
	}
	
	public void setActiveEditor(IEditorPart editor) {
		this.editor = editor;
	}
}
