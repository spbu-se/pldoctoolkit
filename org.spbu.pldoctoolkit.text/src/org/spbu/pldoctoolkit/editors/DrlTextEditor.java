package org.spbu.pldoctoolkit.editors;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.editors.text.TextEditor;
import org.spbu.pldoctoolkit.DrlPublisherPlugin;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.actions.BasicExportAction;
import org.spbu.pldoctoolkit.actions.DRLMenuListener;
import org.spbu.pldoctoolkit.actions.ExtractInsertAfterAction;
import org.spbu.pldoctoolkit.actions.ExtractInsertBeforeAction;
import org.spbu.pldoctoolkit.actions.InsertTemplateAction;
import org.spbu.pldoctoolkit.actions.InsertIntoDictionaryAction;
import org.spbu.pldoctoolkit.actions.InsertIntoDirectoryAction;
import org.spbu.pldoctoolkit.actions.MakeRefOptionalAction;
import org.spbu.pldoctoolkit.actions.MakeRefRequiredAction;
import org.spbu.pldoctoolkit.actions.PdfExportAction;
import org.spbu.pldoctoolkit.actions.ReplaceWithInfElemRef;
import org.spbu.pldoctoolkit.actions.ReplaceWithNestAction;
import org.spbu.pldoctoolkit.actions.SearchDictEntryAction;
import org.spbu.pldoctoolkit.actions.SearchDirRefAction;
import org.spbu.pldoctoolkit.actions.SelectIntoCondBlockAction;
import org.spbu.pldoctoolkit.actions.SelectIntoInfElementAction;
import org.spbu.pldoctoolkit.actions.SelectIntoInfProductAction;
import org.spbu.pldoctoolkit.actions.SplitInfElemAction;
import org.spbu.pldoctoolkit.actions.ValidateDrlAction;
import org.spbu.pldoctoolkit.actions.ValidateDrlOnSaveAction;
import org.spbu.pldoctoolkit.templates.TemplatesDocument;
import org.spbu.pldoctoolkit.refactor.ProjectContentCreator;
import org.spbu.pldoctoolkit.actions.RenameAction;

public class DrlTextEditor extends TextEditor {
	public static final String XML_PARTITIONING = "__drl_partitioning";
	
	public static final String VALIDATE_DRL_ON_SAVE = "validate_drl_on_save";
	public static final String VALIDATE_DRL = "validate_drl";
	public static final String EXPORT_TO_HTML = "export_to_html";
	public static final String EXPORT_TO_PDF = "export_to_pdf";
	//public static final String EXPORT_TO_HH = "export_to_html_help";
	//public static final String EXPORT_TO_DB = "export_to_docbook";
	public static final String [] INSERT_TEMPLATES = new String[1000];
	
	//minchin
	public static final String SELECT_INTO_INF_ELEMENT = "select_into_inf_element";
	public static final String INSERT_INTO_DICTIONARY = "insert_into_dictionary";
	public static final String SEARCH_DICT_ENTRY = "search_dict_entry";
	public static final String INSERT_INTO_DIRECTORY = "insert_into_directory";
	public static final String REPLACE_WITH_NEST = "replace_with_nest";
	public static final String SEARCH_DIR_REF = "search_dir_ref";
	public static final String EXTRACT_INSERT_AFTER = "extract_insert_after";
	public static final String EXTRACT_INSERT_BEFORE = "extract_insert_before";
	public static final String REPLACE_WITH_INFELMREF = "replace_with_infElemRef";

	//lebedkova
	public static final String RENAME = "rename";
	public static final String SELECT_INTO_INF_PRODUCT = "select_into_inf_product";
	public static final String SELECT_INTO_COND_BLOCK = "select_into_cond_block";
	public static final String SPLIT_INF_ELEM = "split_inf_elem";
	public static final String MAKE_REF_OPTIONAL = "make_ref_optional";
	public static final String MAKE_REF_REQUIRED = "make_ref_required";
	
	private ColorManager colorManager;
	private DRLMenuListener menuListener = new DRLMenuListener();//minchin

	static {
		if (PLDocToolkitPlugin.getRegistryIndex().getProjectContentCreator() == null)
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
			setAction(EXPORT_TO_HTML, new BasicExportAction(this, DrlPublisherPlugin.getURL("xsl/docbook/html/docbook.xsl"), "Export to HTML", "HTML", "html", false));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			setAction(EXPORT_TO_PDF, new PdfExportAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
/*
		try {
			setAction(EXPORT_TO_HH, new BasicExportAction(this, DrlPublisherPlugin.getURL("xsl/docbook/htmlhelp/htmlhelp.xsl"), "Export to HTML Help", "HTML help", "hh", false));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			setAction(EXPORT_TO_DB, new BasicExportAction(this, null, "Export to DocBook", "DocBook", "db", true));
		} catch (Exception e) {
			e.printStackTrace();
		}
*/		
		
		try {
			TemplatesDocument templatesDocument = new TemplatesDocument();
			for (int i = 0; i < templatesDocument.numOfTemplates;i++){
					INSERT_TEMPLATES[i] = templatesDocument.templates[i].name;
			    	setAction(INSERT_TEMPLATES[i],	new InsertTemplateAction(this, INSERT_TEMPLATES[i]));
			}
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
		
		try {
			setAction(SEARCH_DICT_ENTRY, new SearchDictEntryAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			setAction(INSERT_INTO_DIRECTORY, new InsertIntoDirectoryAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			setAction(REPLACE_WITH_NEST, new ReplaceWithNestAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			setAction(SEARCH_DIR_REF, new SearchDirRefAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			setAction(EXTRACT_INSERT_AFTER, new ExtractInsertAfterAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			setAction(EXTRACT_INSERT_BEFORE, new ExtractInsertBeforeAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			setAction(REPLACE_WITH_INFELMREF, new ReplaceWithInfElemRef(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//lebedkova
		try {
			setAction(RENAME, new RenameAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			setAction(SELECT_INTO_INF_PRODUCT, new SelectIntoInfProductAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			setAction(SELECT_INTO_COND_BLOCK, new SelectIntoCondBlockAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			setAction(SPLIT_INF_ELEM, new SplitInfElemAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			setAction(MAKE_REF_OPTIONAL, new MakeRefOptionalAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			setAction(MAKE_REF_REQUIRED, new MakeRefRequiredAction(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ISourceViewer DrlGetSourceViewer(){
		return this.getSourceViewer();
	}
	
	public void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		
		try {
			MenuManager subInsertTemplate = new MenuManager("Insert Template");
			TemplatesDocument templatesDocument = new TemplatesDocument();
			for(int i=0; i<templatesDocument.numOfTemplates; i++){
			    	addAction(subInsertTemplate, INSERT_TEMPLATES[i]);					
			}
			menu.add(subInsertTemplate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//minchin
		MenuManager refactMenu = new MenuManager("Refactor");
		menu.add(refactMenu);

		addAction(refactMenu, SELECT_INTO_INF_ELEMENT);
		addAction(refactMenu, REPLACE_WITH_NEST);
		addAction(refactMenu, EXTRACT_INSERT_AFTER);
		addAction(refactMenu, EXTRACT_INSERT_BEFORE);
		addAction(refactMenu, INSERT_INTO_DICTIONARY);
		addAction(refactMenu, SEARCH_DICT_ENTRY);
		addAction(refactMenu, INSERT_INTO_DIRECTORY);		
		addAction(refactMenu, SEARCH_DIR_REF);
		addAction(refactMenu, REPLACE_WITH_INFELMREF);		
		
		//lebedkova
		addAction(refactMenu, RENAME);
		addAction(refactMenu, SELECT_INTO_INF_PRODUCT);
		addAction(refactMenu, SELECT_INTO_COND_BLOCK);
		addAction(refactMenu, SPLIT_INF_ELEM);
		addAction(refactMenu, MAKE_REF_OPTIONAL);
		addAction(refactMenu,MAKE_REF_REQUIRED);
		
		//DRLMenuListener.instance.editor = this;
		menuListener.menuAboutToShow(menu);
	}
   
	//minchin
	public void setSelection(int offset, int length) {
		doSetSelection(new TextSelection(offset, length));
	}
	
	public DRLMenuListener getMenuListener() {
		return menuListener; 
	}
}
