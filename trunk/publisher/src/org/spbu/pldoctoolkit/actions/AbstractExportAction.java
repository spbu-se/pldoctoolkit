package org.spbu.pldoctoolkit.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;

public abstract class AbstractExportAction extends Action {
	protected IEditorPart editor;
	
	public AbstractExportAction(String text, ImageDescriptor image) {
		super(text, image);
	}
	
	public void setActiveEditor(IEditorPart editor) {
		this.editor = editor;
	}
}
