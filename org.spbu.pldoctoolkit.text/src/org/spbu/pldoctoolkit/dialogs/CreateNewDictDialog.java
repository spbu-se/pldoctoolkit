package org.spbu.pldoctoolkit.dialogs;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CreateNewDictDialog extends Dialog {	
	private String dictId = "";
	private String dictName = "";	
	
	private Text idText;
	private Text nameText;	
	
	private Label idLabel;
	private Label nameLabel;	
	
	private Label errorMessageId;
	private Label errorMessageFile;
	private boolean isValidDictId = false;
	
	int selectionIdx;
	
	
	private ArrayList<String> dictIds = new ArrayList<String>();
	private ArrayList<String> fileNames = new ArrayList<String>();
	
	private Combo fileNamesCombo;
	
	public CreateNewDictDialog(Shell parentShell) {
		super(parentShell);
		setBlockOnOpen(true);
	}
	
	protected Control createContents(Composite parent) {		
		Control res = super.createContents(parent);
		
		idText.setEnabled(false);
		nameText.setEnabled(false);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
		
		return res;
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		composite.setLayout(layout);
		
////////////////////////////////////////////////////////////////////////////////////
		
		Label description = new Label(composite, SWT.LEFT);
		description.setText("Create new Directory in selected file\n\n");
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 3;
		description.setLayoutData(gd);
		
////////////////////////////////////////////////////////////////////////////////////
		
		nameLabel = new Label(composite, SWT.LEFT);		
		nameLabel.setText("Name of new Dictionary:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
		nameLabel.setLayoutData(gd);
		
		nameText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
		nameText.setLayoutData(gd);
				
		KeyListener listener = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {	
				setTexts();
			}
		};
		nameText.addKeyListener(listener);
		
		new Label(composite, SWT.NONE);
			
////////////////////////////////////////////////////////////////////////////////////
		
		idLabel = new Label(composite, SWT.LEFT);
		idLabel.setText("Id of new Dictionary:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
		idLabel.setLayoutData(gd);
		
		idText = new Text(composite, SWT.SINGLE | SWT.BORDER);	
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
		idText.setLayoutData(gd);
		idText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
			
		errorMessageId = new Label(composite, SWT.NONE);
		errorMessageId.setText("                        ");
		//errorMessage.setSize(300, 40);
		errorMessageId.setForeground(new Color(Display.getCurrent(), new RGB(255, 0, 0)));		
		
////////////////////////////////////////////////////////////////////////////////////
		
		new Label(composite, SWT.NONE).setText("File:");
		
		fileNamesCombo = new Combo(composite, SWT.READ_ONLY | SWT.FILL);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
		fileNamesCombo.setLayoutData(gd);
		
		fileNamesCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {				
				fileSelected();
			}			
		});
		
		for (int i = 0; i<fileNames.size(); ++i)
			fileNamesCombo.add(fileNames.get(i));		
		
		errorMessageFile = new Label(composite, SWT.NONE);
		errorMessageFile.setText("Select file           ");
		//errorMessage.setSize(300, 40);
		errorMessageFile.setForeground(new Color(Display.getCurrent(), new RGB(255, 0, 0)));
						
        return composite;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Point size = new Point(400,230);
		newShell.setSize(size);
		Shell p = getParentShell();
		newShell.setLocation(p.getLocation().x + p.getSize().x / 2 - size.x / 2, p.getLocation().y + p.getSize().y / 2 - size.y / 2);
		
		newShell.setText("Create new dictionary...");		
	}	

	protected void okPressed()
	{
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
		
		super.okPressed();
	}
	
	private void setTexts() {
		String name = nameText.getText();
		String id = name + "_id";
		idText.setText(id);
		
		validate();
	}
	
	private void validate() {		
		isValidDictId = true;
		for (String otherId : dictIds) {
			if (idText.getText().equals(otherId)) {
				isValidDictId = false;
				break;
			}
		}
		
		
		if (!isValidDictId) {
			errorMessageId.setText("Bad dict id");
			getButton(IDialogConstants.OK_ID).setEnabled(false);			
			return;
		}
		else {
			errorMessageId.setText("");
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
	}
	
	private void fileSelected() {
		idText.setEnabled(true);
		nameText.setEnabled(true);
		getButton(IDialogConstants.OK_ID).setEnabled(true);
		errorMessageFile.setText("");
	}
	
	public String getDictId()
	{
		return dictId;
	}
	
	public String getDictName()
	{
		return dictName;
	}
	
	public int getSelectionIdx()
	{
		return selectionIdx;
	}

	public void addDictId(String id) {
		dictIds.add(id);		
	}
	
	public void addFileName(String name) {
		fileNames.add(name);
	}
}