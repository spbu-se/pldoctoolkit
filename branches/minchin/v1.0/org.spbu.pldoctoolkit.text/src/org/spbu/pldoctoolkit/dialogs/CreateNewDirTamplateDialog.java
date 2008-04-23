package org.spbu.pldoctoolkit.dialogs;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.CreateDirTemplate;
import org.spbu.pldoctoolkit.refactor.CreateNewDictionary;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.refactor.Util;
import org.spbu.pldoctoolkit.refactor.CreateDirTemplate.FragmentToReplace;

public class CreateNewDirTamplateDialog extends Dialog {
	private final static int OK = 0;
	private final static int LEFT = 1;
	private final static int RIGHT = 2;
	private final static int NOONE = 3;	
	
	private Button replace;
	private Button undoReplace;
	private Button editId;
	
	private List dirList;	
	
	private List fileNamesList;
	private ArrayList<DRLDocument> docs = new ArrayList<DRLDocument>();
	private DRLDocument doc;
	
	private Text text;
	private String textContent;
	
	private Text templateIdText;
	private String templateId; 
	
	private ProjectContent projectContent;	
	private CreateDirTemplate refact;
	
	private ArrayList<FragmentToReplace> fragmentsToReplace = new ArrayList<FragmentToReplace>();
	
	private String directoryId;
		
	public CreateNewDirTamplateDialog(Shell parentShell, ProjectContent projectContent, CreateDirTemplate refact) {
		super(parentShell);
		setBlockOnOpen(true);
		
		this.projectContent = projectContent;
		this.refact = refact;
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setSize(this.getShell().getClientArea().width, this.getShell().getClientArea().height);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		layout.makeColumnsEqualWidth = true;
		
		
		composite.setLayout(layout);
		
		///////////////////////////////////////////////////////////
/*		
		Label description = new Label(composite, SWT.LEFT);
		description.setText("Create new DirTemplate in selected file\n\n");
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 2;
		description.setLayoutData(gd);
*/		
		////////////////////////////////////////////////////////////
		
		Composite dirComposite = new Composite(composite, SWT.NONE);		
		
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalSpan = 2;
		dirComposite.setLayoutData(gd);
		dirComposite.setLayout(new GridLayout(1, true));
		
		new Label(dirComposite, SWT.NONE).setText("Directory:");
		
		dirList = new List(dirComposite, /*SWT.MULTI |*/ SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		setDirListContent();
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		dirList.setLayoutData(gd);
		dirList.addSelectionListener(new SelectionAdapter() {		
			public void widgetDefaultSelected(SelectionEvent e) {
				dirSelected();
			}
			public void widgetSelected(SelectionEvent e) {
				dirSelected();
			}			
		});
		
		//new Label(composite, SWT.NONE);
		
		////////////////////////////////////////////////////////////
		
		Composite fileComposite = new Composite(composite, SWT.NONE);		
		
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;		
		fileComposite.setLayoutData(gd);
		fileComposite.setLayout(new GridLayout(1, true));
		
		new Label(fileComposite, SWT.NONE).setText("File:");
		
		fileNamesList = new List(fileComposite, SWT.READ_ONLY | SWT.BORDER);
		setFileListContent();
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;	
		gd.grabExcessVerticalSpace = true;
		fileNamesList.setLayoutData(gd);
		
		fileNamesList.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {				
				//fileSelected();
			}			
		});
		
		////////////////////////////////////////////////////////////
		
		Composite textComposite = new Composite(composite, SWT.NONE);		
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;	
		gd.horizontalSpan = 3;
		textComposite.setLayoutData(gd);
		textComposite.setLayout(new GridLayout(2, false));		
		
		text = new Text(textComposite, /*SWT.MULTI | SWT.BORDER*/SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		text.setText(refact.getText());
		text.setEditable(false);
		text.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;	
		text.setLayoutData(gd);		
		
		// Buttons /////////////////////////////////////////////////		
		
		Composite buttonsComposite = new Composite(textComposite, SWT.NONE);
		gd = new GridData();		
		gd.verticalAlignment = SWT.FILL;
		//gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;		
		buttonsComposite.setLayoutData(gd);
		
		GridLayout layout2 = new GridLayout();
		layout2.numColumns = 1;
		layout2.verticalSpacing = 9;
		buttonsComposite.setLayout(layout2);
		
		replace = new Button(buttonsComposite, SWT.PUSH);
		replace.setText("Replace");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.TOP;
		gd.grabExcessHorizontalSpace = true;
		replace.setLayoutData(gd);
		replace.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				replacePressed();
			}
		});		
		
		undoReplace = new Button(buttonsComposite, SWT.PUSH);
		undoReplace.setText("Undo replace");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.TOP;
		gd.grabExcessHorizontalSpace = true;
		undoReplace.setLayoutData(gd);
		undoReplace.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				undoReplacePressed();
			}
		});	
		
		editId = new Button(buttonsComposite, SWT.PUSH);
		editId.setText("Edit Attr id");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.TOP;
		gd.grabExcessHorizontalSpace = true;
		editId.setLayoutData(gd);
		editId.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editIdPressed();				
			}
		});	
		
//////////////////////////////////////////////////////////////////////////////////////
		
		Composite idComposite = new Composite(composite, SWT.NONE);		
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;	
		gd.horizontalSpan = 3;
		idComposite.setLayoutData(gd);
		idComposite.setLayout(new GridLayout(2, false));
		
		new Label(idComposite, SWT.NONE).setText("Template id:");
		
		templateIdText = new Text(idComposite, SWT.BORDER);		
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.TOP;
		gd.grabExcessHorizontalSpace = true;		
		templateIdText.setLayoutData(gd);		
								
        return composite;
	}
	
	private void dirSelected() {
		//templateIdText.setText(Util.getId(, LangElem.DIRTEMPLATE));
	}
	
	private void setFileListContent() {
		docs = CreateNewDictionary.getPossipleDocs(projectContent);
		for (DRLDocument doc : docs) {
			fileNamesList.add(doc.file.getName());			
		}
	}
	
	private void setDirListContent() {
		for (LangElem dir : projectContent.directories) {
			dirList.add(dir.attrs.getValue(LangElem.NAME) + " (id :" + dir.attrs.getValue(LangElem.ID) + ")");
		}
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Point size = new Point(500, 400);
		newShell.setSize(size);
		Shell p = getParentShell();
		newShell.setLocation(p.getLocation().x + p.getSize().x / 2 - size.x / 2, p.getLocation().y + p.getSize().y / 2 - size.y / 2);
		
		newShell.setText("Create new DirTemplate...");		
	}
	
	private void replacePressed() {
		Point sel = text.getSelection();
//		if (sel.x == sel.y)
	//		return;
		
		if (checkNotInAnyFragment(sel) != OK)
			return;
		
		int i = 0;
		int curDiff = 0;
		while (i < fragmentsToReplace.size() && (fragmentsToReplace.get(i).getOffset() + curDiff) < sel.x) {
			curDiff += fragmentsToReplace.get(i).getLengthAfter() - fragmentsToReplace.get(i).getLengthBefore();
			++i;
		}
		
		String newId = getId("attrId");
		if (newId != null) {		
			FragmentToReplace newFragment = new FragmentToReplace(sel.x - curDiff, text.getSelectionText(), 
					newId, projectContent.directories.get(dirList.getSelectionIndex()).getDRLDocument(), text.getText());		
		
			if (!refact.isText(newFragment))
				return;
		
			fragmentsToReplace.add(i, newFragment);		
			
			String textContent = text.getText();
			String newTextCoontent = textContent.substring(0, sel.x) + newFragment.getTexRepresentation() + textContent.substring(sel.y, textContent.length());
			text.setText(newTextCoontent);
			text.setSelection(sel.x, sel.x + newFragment.getLengthAfter());
		}
		else
			return;
	}
	
	private void undoReplacePressed() {
		Point sel = text.getSelection();
//		if (sel.x == sel.y)
	//		return;
		
		int pos;
		
		if (checkNotInAnyFragment(sel) == LEFT)
			pos = sel.x;
		else if (checkNotInAnyFragment(sel) == RIGHT)
			pos = sel.y;
		else
			return;
		
		int i = 0;
		int curDiff = 0;
		while (i < fragmentsToReplace.size() && (fragmentsToReplace.get(i).getOffset() + fragmentsToReplace.get(i).getLengthAfter() + curDiff) < pos) {
			curDiff += fragmentsToReplace.get(i).getLengthAfter() - fragmentsToReplace.get(i).getLengthBefore();
			++i;
		}
		
		FragmentToReplace fragment = fragmentsToReplace.get(i);
		fragmentsToReplace.remove(i);
				
		String textContent = text.getText();
		String newTextCoontent = textContent.substring(0, fragment.getOffset() + curDiff) + 
								 fragment.getReplacedText() + 
								 textContent.substring(fragment.getOffset() + fragment.getLengthAfter() + curDiff, textContent.length());
		text.setText(newTextCoontent);
		text.setSelection(fragment.getOffset() + curDiff, fragment.getOffset() + curDiff + fragment.getLengthBefore());
	}
	
	private void editIdPressed() {
		Point sel = text.getSelection();
		int pos;
		
		if (checkNotInAnyFragment(sel) == LEFT)
			pos = sel.x;
		else if (checkNotInAnyFragment(sel) == RIGHT)
			pos = sel.y;
		else
			return;
		
		int i = 0;
		int curDiff = 0;
		while (i < fragmentsToReplace.size() && (fragmentsToReplace.get(i).getOffset() + fragmentsToReplace.get(i).getLengthAfter() + curDiff) < pos) {
			curDiff += fragmentsToReplace.get(i).getLengthAfter() - fragmentsToReplace.get(i).getLengthBefore();
			++i;
		}
		
		FragmentToReplace fragment = fragmentsToReplace.get(i);
		String newId = getId(fragment.getAttrId());
		if (newId != null) {
			String textContent = text.getText();
			String newTextCoontent1 = textContent.substring(0, fragment.getOffset() + curDiff); 
			String newTextCoontent2 = textContent.substring(fragment.getOffset() + fragment.getLengthAfter() + curDiff, textContent.length());
						
			fragment.setAttrId(newId);
			
			text.setText(newTextCoontent1 + fragment.getTexRepresentation() + newTextCoontent2);
			text.setSelection(fragment.getOffset() + curDiff, fragment.getOffset() + curDiff + fragment.getLengthAfter());			
		}
	}
		
	private String getId(String prev) {
		InputDialog dialog = new InputDialog(this.getShell(), 
											 "Enter attrId...", 
											 "Enter attrId", 
											 prev,
											 null);
		
		if (dialog.open() == Window.OK)
			return dialog.getValue();
		else 
			return null;
	}
	
	/*
	 * 
	 */
	private int checkNotInAnyFragment(Point sel) {
		int curDiff = 0;
		for (FragmentToReplace fragment : fragmentsToReplace) {
			int fragStart = fragment.getOffset() + curDiff;
			int fragEnd = fragment.getOffset() + fragment.getLengthAfter() + curDiff;
			
			if (fragStart < sel.x && fragEnd > sel.x)
				return LEFT;
			
			if (fragStart < sel.y && fragEnd > sel.y)
				return RIGHT;
			
			if (fragStart >= sel.x && fragEnd <= sel.y)
				return NOONE;
			
			curDiff += fragment.getLengthAfter() - fragment.getLengthBefore();
		}
		
		return OK;
	}

	protected void okPressed()
	{/*
		dictId = idText.getText();
		dictName = nameText.getText();
		
		selectionIdx = fileNamesCombo.getSelectionIndex();
		if (selectionIdx == -1) {
			String buttonNames[] = new String[1];
			buttonNames[0] = "Ok";
			MessageDialog dialog = new MessageDialog(getShell(), "", null, "Select file, please", MessageDialog.ERROR, buttonNames, 0);
			dialog.open();
			return;
		}
		*/
		directoryId = projectContent.directories.get(dirList.getSelectionIndex()).attrs.getValue(LangElem.ID);
		textContent = text.getText();
		templateId = templateIdText.getText();
		doc = docs.get(fileNamesList.getSelectionIndex());
		
		super.okPressed();		
	}
	
	public String getDirectoryId() {
		return directoryId;
	}
	
	public ArrayList<FragmentToReplace> getFragmentsToReplace() {
		return fragmentsToReplace;
	}
	
	public String getText() {
		return textContent;
	}
	
	public DRLDocument getDoc() {
		return doc;
	}
	
	public String getTemplateId() {
		return templateId;
	}
}
