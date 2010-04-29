package org.spbu.pldoctoolkit.dialogs;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.editors.DrlTextEditor;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.refactor.SearchDirRef;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;

public class SearchDirRefDialog extends Dialog {
	private Label description;
	
	private Button nextButton;
	private Button replaceButton;
	private Button replaceAllButton;
	private Button contextButton;
	private Button wholeWordButton;
	
	private Combo directoryList;	
	
	private Group templateGroup;
	private List templateList;
	private Button allTemplatesButton;
	private ArrayList<LangElem> templates;
	
	private Group entryGroup;
	private List entryList;
	private Button allEntrysButton;
	private ArrayList<LangElem> entrys;
	
	private Text textTofindText;
	
	private Label contextLabel;
	
	private TextEditor startTE;
	private IFile startFile;
	private SearchDirRef refact;
	private IFile currentFile;
	private TextEditor currentTE;
	private IDocument currentDoc;
	private int curIdx = -1;
	
	private boolean searchEveryWhere = false;
	
	IEditorPart editor;
	//TextEditor te;
	IProject project;
	
	//SearchDictEntry refact = new SearchDictEntry();
	ProjectContent projectContent;// = new ProjectContent();
	
	public IWorkbenchWindow w = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	Iterator<IFile> it; //= projectContent.DRLDocs.keySet().iterator();	
	
	private boolean endReached = false;
	
	private static final char delimeters[] = { ' ', '<', '>', '\n', '\t', '(', ')', '{', '}', '"'};
	private static final String descriptionText = "Search for possible dirRef using selected template and entry\n\n"; 
	
	public SearchDirRefDialog(Shell parentShell, TextEditor startTE) {
		super(parentShell);
		setBlockOnOpen(true);
		
		this.startTE = startTE;	
		
		currentTE = startTE;
		
		FileEditorInput editorInput = (FileEditorInput)startTE.getEditorInput();
		project = editorInput.getFile().getProject();
		currentFile = editorInput.getFile();
		startFile = currentFile; 
		projectContent = (ProjectContent)((ProjectRegistryImpl)PLDocToolkitPlugin.getRegistry(project.getName())).projectContent;
		currentDoc = currentTE.getDocumentProvider().getDocument(editorInput);
		
		refact = new SearchDirRef(projectContent);
		
		it = projectContent.DRLDocs.keySet().iterator();
	}
	
	protected Control createDialogArea(Composite parent) {		
		Composite composite = (Composite) super.createDialogArea(parent);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		composite.setLayout(layout);
		
		description = new Label(composite, SWT.LEFT);
		description.setText(descriptionText);
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 2;
		description.setLayoutData(gd);
	
////////////////////////////////////////////////////////////////////////////////////
		
		new Label(composite, SWT.NONE).setText("Directory:");
		
		directoryList = new Combo(composite, SWT.MULTI | SWT.BORDER);
		setDirListContent();
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		directoryList.setLayoutData(gd);
		directoryList.addSelectionListener(new SelectionAdapter() {		
			public void widgetDefaultSelected(SelectionEvent e) {
				dirSelected();
			}
			public void widgetSelected(SelectionEvent e) {
				dirSelected();
			}			
		});
		
////////////////////////////////////////////////////////////////////////////////////
		
		Composite listsComposite = new Composite(composite, 0);
		
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalSpan = 2;		
		listsComposite.setLayoutData(gd);
		
		layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		layout.makeColumnsEqualWidth = true;
		listsComposite.setLayout(layout);
		
/////////////////////////////////////////////////////////////////////////////////////
		
		templateGroup = new Group(listsComposite, SWT.NONE);
		templateGroup.setLayout(new GridLayout(1, true));
		templateGroup.setText("Select template");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		templateGroup.setLayoutData(gd);
		
		allTemplatesButton = new Button(templateGroup, SWT.CHECK );
		allTemplatesButton.setText("Use all templates");
		gd = new GridData();
		gd.horizontalAlignment = SWT.LEFT;
		gd.verticalAlignment = SWT.TOP;
		gd.grabExcessHorizontalSpace = true;
		allTemplatesButton.setLayoutData(gd);
		allTemplatesButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {				
				allTemplatesButtonPressed();
			}			
		});
		
		templateList = new List(templateGroup, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER );		
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		templateList.setLayoutData(gd);
		templateList.addSelectionListener(new SelectionAdapter() {		
			public void widgetDefaultSelected(SelectionEvent e) {
				templateSelected();
			}
			public void widgetSelected(SelectionEvent e) {
				templateSelected();
			}			
		});
		
		
		entryGroup = new Group(listsComposite, SWT.NONE);
		entryGroup.setLayout(new GridLayout(1, true));
		entryGroup.setText("Select entry");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		entryGroup.setLayoutData(gd);
				
		allEntrysButton = new Button(entryGroup, SWT.CHECK );
		allEntrysButton.setText("Use all entrys");
		gd = new GridData();
		gd.horizontalAlignment = SWT.LEFT;
		gd.verticalAlignment = SWT.TOP;
		gd.grabExcessHorizontalSpace = true;
		allEntrysButton.setLayoutData(gd);
		allEntrysButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {				
				allEntrysButtonPressed();
			}			
		});
		
		entryList = new List(entryGroup, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER );		
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		entryList.setLayoutData(gd);
		entryList.addSelectionListener(new SelectionAdapter() {		
			public void widgetDefaultSelected(SelectionEvent e) {
				entrySelected();
			}
			public void widgetSelected(SelectionEvent e) {
				entrySelected();
			}			
		});
		
////////////////////////////////////////////////////////////////////////////////////		
		
		Composite downPartComposite = new Composite(composite, 0);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.BOTTOM;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 2;		
		downPartComposite.setLayoutData(gd);
		
		layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		downPartComposite.setLayout(layout);
		
////////////////////////////////////////////////////////////////////////////////////		
		
		new Label(downPartComposite, SWT.NONE).setText("Find:");
	
		textTofindText = new Text(downPartComposite, SWT.SINGLE | SWT.BORDER);
		textTofindText.setText(/*refact.getSearchText()*/"");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
		textTofindText.setLayoutData(gd);
		
		textTofindText.setEnabled(false);
		
		nextButton = new Button(downPartComposite, SWT.CENTER);
		nextButton.setText("Next");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		//gd.grabExcessHorizontalSpace = true;		
		nextButton.setLayoutData(gd);
		nextButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {			
				nextButtonPressed();
			}			
		});
		
////////////////////////////////////////////////////////////////////////////////////////
		
		contextButton = new Button(downPartComposite, SWT.LEFT | SWT.CHECK);
		contextButton.setText("Search in all documents");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 2;
		contextButton.setLayoutData(gd);
		
		replaceButton = new Button(downPartComposite, SWT.CENTER);
		replaceButton.setText("Replace");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		//gd.grabExcessHorizontalSpace = true;		
		replaceButton.setLayoutData(gd);
		
		replaceButton.setEnabled(false);
		replaceButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				//super.widgetSelected(e);
				replaceButtonPressed();
			}			
		});
		
////////////////////////////////////////////////////////////////////////////////////////
		wholeWordButton = new Button(downPartComposite, SWT.LEFT | SWT.CHECK);
		wholeWordButton.setText("Whole word");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 2;
		wholeWordButton.setLayoutData(gd);
		
		replaceAllButton = new Button(downPartComposite, SWT.CENTER);
		replaceAllButton.setText("Replace all");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		//gd.grabExcessHorizontalSpace = true;		
		replaceAllButton.setLayoutData(gd);
		
		//replaceAllButton.setEnabled(false);
		replaceAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				//super.widgetSelected(e);
				replaceAllButtonPressed();
			}			
		});
		
/*		
		contextLabel = new Label(composite, SWT.LEFT);
		contextLabel.setText("Search in all documents");
*/	

	/*	replaceButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				//super.widgetSelected(e);
				replaceButtonPressed();
			}			
		});*/
		
		validateContent();
		setError(null);
		
        return composite;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Point size = new Point(600, 500);
		newShell.setSize(size);
		Shell p = getParentShell();
		newShell.setLocation(p.getLocation().x + p.getSize().x / 2 - size.x / 2, p.getLocation().y + p.getSize().y / 2 - size.y / 2);
		
		newShell.setText("Find dirRef...");		
	}	

	protected void okPressed()
	{		
		super.okPressed();
	}	
	
	private void nextButtonPressed() {        
		replaceButton.setEnabled(false);		
		FindReplaceDocumentAdapter findAdapter = new FindReplaceDocumentAdapter(currentDoc);
		//int res = currentText.indexOf(refact.getSearchText(), curIdx);
		try {
			String searchText = textTofindText.getText(); 
			int res = curIdx + 1;  
			boolean wasFound = false;
			while (!wasFound) {				
				//res = currentDoc.search(res, refact.getSearchText(), true, true, false);
				
				IRegion reg = findAdapter.find(res, searchText, true, true, false, false);
				
				if (reg == null) {
					if (nextFileOrSearchText()) {
						searchText = textTofindText.getText();
						findAdapter = new FindReplaceDocumentAdapter(currentDoc);
						res = -1;
					}
					else {
						endReached = true;
						nextButton.setEnabled(false);
						return;						
					}
				}
				else {
					res = reg.getOffset();
					boolean isGoodText = true;
					if (wholeWordButton.getSelection()) {
						if (res > 0)
							isGoodText = isDelimeter(findAdapter.charAt(res-1));
						int l = searchText.length();
						if (res + l <= findAdapter.length())
							isGoodText &= isDelimeter(findAdapter.charAt(res + l));
					}
					
					if (isGoodText) {
						DRLDocument DRLdoc = projectContent.DRLDocs.get(currentFile);

						int line1 = currentDoc.getLineOfOffset(res);
						int column1 = res - currentDoc.getLineOffset(line1);
						int line2 = currentDoc.getLineOfOffset(res + searchText.length());
						int column2 = res + searchText.length() - currentDoc.getLineOffset(line2);

						PositionInText pos1 = new PositionInText(line1 + 1, column1 + 1);
						PositionInText pos2 = new PositionInText(line2 + 1, column2 + 1);

						refact.setPararams(pos1, pos2, DRLdoc);
					
						if (refact.validateSelection()) {
							wasFound = true;
							curIdx = res;
						}
					}
				}
				res += 1;
			}
			
			{				
				((DrlTextEditor)currentTE).setSelection(curIdx, searchText.length());
				replaceButton.setEnabled(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}		
	}
	
	private boolean isDelimeter(char c) {
		for (char curChar : delimeters) {
			if (curChar == c)
				return true;
		}
		
		return false;
	}
	
	private void replaceButtonPressed() {
		try {
			//DRLDocument DRLdoc = projectContent.DRLDocs.get(currentFile);
			LangElem curTemplate = templates.get(templateList.getSelectionIndex()); 
	        LangElem curEntry = entrys.get(entryList.getSelectionIndex());
			refact.perform(curTemplate, curEntry);
			
			projectContent.saveAll();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void replaceAllButtonPressed() {
		curIdx = -1;
		it = projectContent.DRLDocs.keySet().iterator();
		if (!setFirstSearchText())
			return;
		try {
			while (!endReached) {
				nextButtonPressed();
				
				LangElem curTemplate = templates.get(templateList.getSelectionIndex()); 
		        LangElem curEntry = entrys.get(entryList.getSelectionIndex());
				refact.perform(curTemplate, curEntry);
			
				projectContent.saveAll();
			}
			
			okPressed();
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private boolean nextFileOrSearchText() {
		//replaceButton.setEnabled(false);
		//nextButton.setEnabled(false);		
		if (nextSearchText(templateList.getSelectionIndex(), entryList.getSelectionIndex()))
			return true;
		
		if (!contextButton.getSelection())
			return false;		
				
		if (it.hasNext()) {
			IFile file = it.next();
			if (file.equals(startFile)) {
				if (it.hasNext()) 
					file = it.next();
				else
					return false;
			}
			
			FileEditorInput in = new FileEditorInput(file);
			try {
				currentTE = (TextEditor)w.getActivePage().openEditor(in, "org.spbu.pldoctoolkit.editors.DRLEditor");				
				
				FileEditorInput editorInput = (FileEditorInput)currentTE.getEditorInput();
				project = editorInput.getFile().getProject();
				currentFile = file;//editorInput.getFile();
				projectContent = (ProjectContent)((ProjectRegistryImpl)PLDocToolkitPlugin.getRegistry(project.getName())).projectContent;
				currentDoc = currentTE.getDocumentProvider().getDocument(editorInput);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
						
			return setFirstSearchText();//nextSearchText(0, -1);
		}
		else
			return false;
	}
	
	private boolean setFirstSearchText() {
		if (allTemplatesButton.getSelection() && !allEntrysButton.getSelection()) 
			return nextSearchText(-1, entryList.getSelectionIndex());
		else if (!allTemplatesButton.getSelection() && allEntrysButton.getSelection()) 
			return nextSearchText(templateList.getSelectionIndex(), -1);
		else if (allTemplatesButton.getSelection() && allEntrysButton.getSelection()) 
			return nextSearchText(0, -1);
		else 
			return setSearchText();//true;//return nextSearchText(templateList.getSelectionIndex(), entryList.getSelectionIndex());
	}
	
	private boolean nextSearchText(int templIdx, int entryIdx) {		
		int templCount = templateList.getItemCount();		
		int entryCount = entryList.getItemCount(); 
		
		int i, j;		
		boolean wasFound = false;
		
		if (allTemplatesButton.getSelection() && !allEntrysButton.getSelection()) {
			for (i = templIdx + 1; (i < templCount) && !wasFound ; ++i) {			
				templateList.setSelection(i);					
				if (setSearchText())					
					wasFound = true;				
			}
		}
		else if (!allTemplatesButton.getSelection() && allEntrysButton.getSelection()) {
			for (j = entryIdx + 1; (j < entryCount) && !wasFound ; ++j) {				
				entryList.setSelection(j);
				if (setSearchText())					
					wasFound = true;
			}
		}
		else if (allTemplatesButton.getSelection() && allEntrysButton.getSelection()) {	
			for (i = templIdx; (i < templCount) && !wasFound ; ++i) {
				for (j = (i == templIdx ? entryIdx + 1 : 0); (j < entryCount) && !wasFound ; ++j) {
					templateList.setSelection(i);
					entryList.setSelection(j);
					if (setSearchText())					
						wasFound = true;
				}
			}		
		}		
		
		return wasFound;
	}
	
	private void dirSelected() {
		int idx = directoryList.getSelectionIndex();
		setDirectory(projectContent.directories.get(idx));
		
		if (validateContent()) {
			setSearchText();
		}
	}
	
	private void templateSelected() {		
		if (validateContent()) {
			setSearchText();
		}
	}
	
	private void entrySelected() {
		if (validateContent()) {
			setSearchText();
		}
	}
	
	private void allTemplatesButtonPressed() {
		if (allTemplatesButton.getSelection()) {
			templateList.setSelection(0);
			templateList.setEnabled(false);
		}
		else {
			templateList.setEnabled(true);
		}
		
		if (validateContent()) {
			setFirstSearchText();
		}
	}
	
	private void allEntrysButtonPressed() {
		if (allEntrysButton.getSelection()) {
			entryList.setSelection(0);
			entryList.setEnabled(false);
		}
		else {
			entryList.setEnabled(true);
		}
		
		if (validateContent()) {
			setFirstSearchText();
		}
	}
	
	private boolean setSearchText() {
		String textToFind =  refact.getSearchText(templates.get(templateList.getSelectionIndex()), 
                entrys.get(entryList.getSelectionIndex()));
		if (textToFind != null) {
			textTofindText.setText(textToFind);
			setError(null);
			setEnabledButtons(true);
			return true;
		}
		else {
			setEnabledButtons(false);
			textTofindText.setText("");
			setError("There is no all necessary Attr in selcted entry");
			return false;
		}
	}
	
	private boolean validateContent() {
		int dirIdx = directoryList.getSelectionIndex();
		if (dirIdx == -1) {
			setError("Select directory");
			setEnabledButtons(false);
			setEnabledLists(false);
			return false;
		}
		
		setEnabledLists(true);
		
		int templIdx = templateList.getSelectionIndex();
		if (!allTemplatesButton.getSelection() && templIdx == -1) {
			setError("Select template");
			setEnabledButtons(false);			
			return false;
		}
		
		int entryIdx = entryList.getSelectionIndex();
		if (!allEntrysButton.getSelection() && entryIdx == -1 ) {
			setError("Select entry");
			setEnabledButtons(false);			
			return false;
		}
		
		setError(null);
		setEnabledButtons(true);	
		
		return true;
	}
	
	private void setError(String message) {
		if (message == null) {
			description.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			description.setText(descriptionText);
		}
		else {
			description.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
			description.setText(message);
		}
	}
	
	private void setEnabledButtons(boolean enabled) {
		//replaceButton.setEnabled(enabled);
		replaceAllButton.setEnabled(enabled);
		nextButton.setEnabled(enabled);
	}
	
	private void setEnabledLists(boolean enabled) {
		templateGroup.setEnabled(enabled);
		templateList.setEnabled(enabled);
		allTemplatesButton.setEnabled(enabled);
		
		entryGroup.setEnabled(enabled);
		entryList.setEnabled(enabled);
		allEntrysButton.setEnabled(enabled);
	}
		
	private void setDirListContent() {
		for (LangElem dir : projectContent.directories) {
			directoryList.add(dir.attrs.getValue(LangElem.NAME) + " (id :" + dir.attrs.getValue(LangElem.ID) + ")");
		}
	}
	
	private void setDirectory(LangElem dir) {
		templates = refact.getTemplates(dir);
		entrys = refact.getEntrys(dir);
		
		int idx = projectContent.directories.indexOf(dir);
		//directoryList.set
		setTemplateListContent();
		setEntrysListContent();
	}
	
	private void setTemplateListContent() {
		templateList.removeAll();
		
		for (LangElem template : templates) {
			templateList.add(template.getTextRepresentation());
		}
	}
	
	private void setEntrysListContent() {
		entryList.removeAll();
		
		for (LangElem entry : entrys) {
			entryList.add(entry.getTextRepresentation());
		}
	}
	
	public void setEnabled(boolean enabled) {
		nextButton.setEnabled(enabled);
		replaceButton.setEnabled(enabled);
	}
}