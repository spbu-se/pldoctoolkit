package org.spbu.pldoctoolkit.actions;

import org.eclipse.ui.IEditorPart;

public class PdfExportAction extends BasicExportAction {
	public PdfExportAction(IEditorPart editor) throws Exception {
		super(editor, "PDF" ,"pdf");
	}
}
