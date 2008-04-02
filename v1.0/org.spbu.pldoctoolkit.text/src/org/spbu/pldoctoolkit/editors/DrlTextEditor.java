package org.spbu.pldoctoolkit.editors;

import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.spbu.pldoctoolkit.DrlPublisherPlugin;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.actions.BasicExportAction;
import org.spbu.pldoctoolkit.actions.DRLMenuListener;
import org.spbu.pldoctoolkit.actions.InsertIntoDictionaryAction;
import org.spbu.pldoctoolkit.actions.PdfExportAction;
import org.spbu.pldoctoolkit.actions.ValidateDrlAction;
import org.spbu.pldoctoolkit.actions.ValidateDrlOnSaveAction;
import org.spbu.pldoctoolkit.actions.SelectIntoInfElementAction;
import org.spbu.pldoctoolkit.refactor.InsertIntoDictionary;
import org.spbu.pldoctoolkit.refactor.ProjectContentCreator;

public class DrlTextEditor extends TextEditor {
	public static final String XML_PARTITIONING = "__drl_partitioning";
	
	public static final String VALIDATE_DRL_ON_SAVE = "validate_drl_on_save";
	public static final String VALIDATE_DRL = "validate_drl";
	public static final String EXPORT_TO_HTML = "export_to_html";
	public static final String EXPORT_TO_PDF = "export_to_pdf";
	public static final String SELECT_INTO_INF_ELEMENT = "select_into_inf_element";
	public static final String INSERT_INTO_DICTIONARY = "Iinsert_into_dictionary";
	
	private ColorManager colorManager;
	
	static {
		PLDocToolkitPlugin.getRegistryIndex().setProjectContentCreator(new ProjectContentCreator());
	}

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
		this.getAction(VALIDATE_DRL_ON_SAVE).run();
	}

	@Override
	protected void createActions() {
		super.createActions();
		try {
			setAction(VALIDATE_DRL_ON_SAVE, new ValidateDrlOnSaveAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			setAction(VALIDATE_DRL, new ValidateDrlAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			setAction(EXPORT_TO_HTML, new BasicExportAction(this, DrlPublisherPlugin.getURL("xsl/docbook/html/docbook.xsl"), "Export to HTML", "HTML", "html"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			setAction(EXPORT_TO_PDF, new PdfExportAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			setAction(SELECT_INTO_INF_ELEMENT, new SelectIntoInfElementAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			setAction(INSERT_INTO_DICTIONARY, new InsertIntoDictionaryAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		addAction(menu, SELECT_INTO_INF_ELEMENT);
		addAction(menu, INSERT_INTO_DICTIONARY);
		
		DRLMenuListener.instance.editor = this;
		DRLMenuListener.instance.menuAboutToShow(menu);
	}
}
