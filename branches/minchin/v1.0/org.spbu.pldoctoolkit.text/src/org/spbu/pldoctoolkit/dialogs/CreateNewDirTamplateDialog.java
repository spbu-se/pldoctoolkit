package org.spbu.pldoctoolkit.dialogs;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.CreateDirTemplate;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.refactor.CreateDirTemplate.FragmentToReplace;

public class CreateNewDirTamplateDialog extends Dialog {
	private Button replace;
	private Button undoReplace;
	private Button editId;
	
	private Combo dirList;	
	
	private Text text;
	
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
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		composite.setLayout(layout);
		
		////////////////////////////////////////////////////////////
		
		dirList = new Combo(composite, SWT.MULTI | SWT.BORDER);
		setDirListContent();
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		dirList.setLayoutData(gd);
		
		new Label(composite, SWT.NONE);
		
		////////////////////////////////////////////////////////////
		
		text = new Text(composite, SWT.MULTI | SWT.BORDER);
		text.setText(refact.getText());
		text.setEditable(false);
		text.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		text.setLayoutData(gd);		
		
		// Buttons /////////////////////////////////////////////////		
		
		Composite buttonsComposite = new Composite(composite, 0);
/*		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		buttonsComposite.setLayoutData(gd);*/
		
		GridLayout layout2 = new GridLayout();
		layout2.numColumns = 1;
		layout2.verticalSpacing = 9;
		buttonsComposite.setLayout(layout2);
		
		replace = new Button(buttonsComposite, SWT.PUSH);
		replace.setText("Replace");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
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
		gd.grabExcessHorizontalSpace = true;
		undoReplace.setLayoutData(gd);
						
        return composite;
	}
	
	private void setDirListContent() {
		for (LangElem dir : projectContent.directories) {
			dirList.add(dir.attrs.getValue(LangElem.NAME) + " (id :" + dir.attrs.getValue(LangElem.ID) + ")");
		}
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Point size = new Point(500, 500);
		newShell.setSize(size);
		Shell p = getParentShell();
		newShell.setLocation(p.getLocation().x + p.getSize().x / 2 - size.x / 2, p.getLocation().y + p.getSize().y / 2 - size.y / 2);
		
		newShell.setText("Create new DirTemplate...");		
	}
	
	private void replacePressed() {
		Point sel = text.getSelection();
		if (sel.x == sel.y)
			return;
		
		if (!checkNotInAnyFragment(sel))
			return;
		
		int i = 0;
		int curDiff = 0;
		while (i < fragmentsToReplace.size() && (fragmentsToReplace.get(i).getOffset() + curDiff) < sel.x) {
			curDiff += fragmentsToReplace.get(i).getLengthAfter() - fragmentsToReplace.get(i).getLengthBefore();
			++i;
		}
		
		FragmentToReplace newFragment = new FragmentToReplace(sel.x - curDiff, text.getSelectionText(), 
				"takID", projectContent.directories.get(dirList.getSelectionIndex()).getDRLDocument(), text.getText());		
		
		if (!refact.isText(newFragment))
			return;
		
		fragmentsToReplace.add(newFragment);		
		
		String textContent = text.getText();
		String newTextCoontent = textContent.substring(0, sel.x) + newFragment.getTexRepresentation() + textContent.substring(sel.y, textContent.length());
		text.setText(newTextCoontent);
		text.setSelection(sel.x, sel.x + newFragment.getLengthAfter());
	}
	
	private boolean checkNotInAnyFragment(Point sel) {
		int curDiff = 0;
		for (FragmentToReplace fragment : fragmentsToReplace) {
			int fragStart = fragment.getOffset() + curDiff;
			int fragEnd = fragment.getOffset() + fragment.getLengthAfter() + curDiff;
			
			if (fragStart < sel.x && fragEnd > sel.x)
				return false;
			
			if (fragStart < sel.y && fragEnd > sel.y)
				return false;
			
			if (fragStart >= sel.x && fragEnd <= sel.y)
				return false;
			
			curDiff += fragment.getLengthAfter() - fragment.getLengthBefore();
		}
		
		return true;
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
		
		super.okPressed();
	}
	
	public String getDirectoryId() {
		return directoryId;
	}
	
	public ArrayList<FragmentToReplace> getFragmentsToReplace() {
		return fragmentsToReplace;
	}
}
