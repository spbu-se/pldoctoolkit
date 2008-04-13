package org.spbu.pldoctoolkit.dialogs;

import java.util.Iterator;

import javax.swing.text.BadLocationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.editors.DrlTextEditor;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.refactor.SearchDictEntry;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;

public class SearchDictEntryDialog extends Dialog {	
	private Button nextButton;
	private Button replaceButton;
	private Button replaceAllButton;
	private Button contextButton;
	private Button wholeWordButton;
	
	private Text textTofindText;
	
	private Label contextLabel;
	
	private TextEditor startTE;
	private IFile startFile;
	private SearchDictEntry refact;
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
	
	public SearchDictEntryDialog(Shell parentShell, TextEditor startTE, SearchDictEntry refact) {
		super(parentShell);
		setBlockOnOpen(true);
		
		this.startTE = startTE;
		this.refact = refact;
		
		currentTE = startTE;
		
		FileEditorInput editorInput = (FileEditorInput)startTE.getEditorInput();
		project = editorInput.getFile().getProject();
		currentFile = editorInput.getFile();
		startFile = currentFile; 
		projectContent = (ProjectContent)((ProjectRegistryImpl)PLDocToolkitPlugin.getRegistry(project.getName())).projectContent;
		currentDoc = currentTE.getDocumentProvider().getDocument(editorInput);
		
		it = projectContent.DRLDocs.keySet().iterator();
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		composite.setLayout(layout);
		
////////////////////////////////////////////////////////////////////////////////////
/*		
		Label description = new Label(composite, SWT.LEFT);
		description.setText("\n\n");
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 3;
		description.setLayoutData(gd);
*/	
////////////////////////////////////////////////////////////////////////////////////
		
		new Label(composite, SWT.NONE).setText("Find:");
	
		textTofindText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		textTofindText.setText(refact.getSearchText());
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
		textTofindText.setLayoutData(gd);
		
		textTofindText.setEnabled(false);
		
		nextButton = new Button(composite, SWT.CENTER);
		nextButton.setText("Next");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
		nextButton.setLayoutData(gd);
		nextButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {			
				nextButtonPressed();
			}			
		});
		
////////////////////////////////////////////////////////////////////////////////////////
		
		contextButton = new Button(composite, SWT.LEFT | SWT.CHECK);
		contextButton.setText("Search in all documents");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 2;
		contextButton.setLayoutData(gd);
		
		replaceButton = new Button(composite, SWT.CENTER);
		replaceButton.setText("Replace");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
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
		wholeWordButton = new Button(composite, SWT.LEFT | SWT.CHECK);
		wholeWordButton.setText("Whole word");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 2;
		wholeWordButton.setLayoutData(gd);
		
		replaceAllButton = new Button(composite, SWT.CENTER);
		replaceAllButton.setText("Replace all");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
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
		
        return composite;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Point size = new Point(300, 200);
		newShell.setSize(size);
		Shell p = getParentShell();
		newShell.setLocation(p.getLocation().x + p.getSize().x / 2 - size.x / 2, p.getLocation().y + p.getSize().y / 2 - size.y / 2);
		
		newShell.setText("Find dict entry...");		
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
			int res = curIdx + 1;  
			boolean wasFound = false;
			while (!wasFound) {				
				//res = currentDoc.search(res, refact.getSearchText(), true, true, false);
				
				IRegion reg = findAdapter.find(res, refact.getSearchText(), true, true, false, false);
				
				if (reg == null) {
					if (nextFile()) {
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
						int l = refact.getSearchText().length();
						if (res + l <= findAdapter.length())
							isGoodText &= isDelimeter(findAdapter.charAt(res + l));
					}
					
					if (isGoodText) {
						DRLDocument DRLdoc = projectContent.DRLDocs.get(currentFile);

						int line1 = currentDoc.getLineOfOffset(res);
						int column1 = res - currentDoc.getLineOffset(line1);
						int line2 = currentDoc.getLineOfOffset(res + refact.getSearchText().length());
						int column2 = res + refact.getSearchText().length() - currentDoc.getLineOffset(line2);

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
				((DrlTextEditor)currentTE).setSelection(curIdx, refact.getSearchText().length());
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

			refact.perform();
			
			projectContent.saveAll();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void replaceAllButtonPressed() {
		curIdx = -1;
		it = projectContent.DRLDocs.keySet().iterator();
		try {
			while (!endReached) {
				nextButtonPressed();
				
				refact.perform();
			
				projectContent.saveAll();
			}
			
			okPressed();
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private boolean nextFile() {
		//replaceButton.setEnabled(false);
		//nextButton.setEnabled(false);
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
			return true;
		}
		else
			return false;
	}
	
	public void setEnabled(boolean enabled) {
		nextButton.setEnabled(enabled);
		replaceButton.setEnabled(enabled);
	}
}