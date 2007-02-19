package org.spbu.pldoctoolkit.editors;

import org.eclipse.ui.editors.text.TextEditor;

public class DRLEditor extends TextEditor {

	private ColorManager colorManager;

	public DRLEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
