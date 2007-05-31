package org.spbu.pldoctoolkit.editors;

import org.eclipse.ui.editors.text.TextEditor;
import org.spbu.pldoctoolkit.DrlPublisherPlugin;
import org.spbu.pldoctoolkit.actions.BasicExportAction;
import org.spbu.pldoctoolkit.actions.PdfExportAction;
import org.spbu.pldoctoolkit.actions.ValidateDrlAction;

public class DrlTextEditor extends TextEditor {
	public static final String XML_PARTITIONING = "__drl_partitioning";
	
	public static final String VALIDATE_DRL = "validate_action";
	public static final String EXPORT_TO_HTML = "export_to_html";
	public static final String EXPORT_TO_PDF = "export_to_pdf";
	
	private ColorManager colorManager;

	public DrlTextEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager, this));
		setDocumentProvider(new XMLDocumentProvider());
	}
	
	@Override
	protected void initializeEditor() {
		super.initializeEditor();
	}

	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}
	
	@Override
	protected void editorSaved() {
		super.editorSaved();
		this.getAction(VALIDATE_DRL).run();
	}

	@Override
	protected void createActions() {
		super.createActions();
		try {
			setAction(VALIDATE_DRL, new ValidateDrlAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			setAction(EXPORT_TO_HTML, new BasicExportAction(this, DrlPublisherPlugin.getURL("xsl/docbook/html/docbook.xsl"),"HTML", "html"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			setAction(EXPORT_TO_PDF, new PdfExportAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}