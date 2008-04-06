package org.spbu.pldoctoolkit.dialogs;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
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
	
	private Label errorMessage;
	private boolean isValidDictId = false;
	
	int selectionIdx;
	
	
	private ArrayList<String> dictIds = new ArrayList<String>();
	private ArrayList<String> fileNames = new ArrayList<String>();
	
	private Combo fileNamesCombo;
	
	public CreateNewDictDialog(Shell parentShell) {
		super(parentShell);
		setBlockOnOpen(true);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		//composite.setLayout(new GridLayout(1, false));
		nameLabel = new Label(composite, SWT.LEFT);
		nameLabel.setText("Name of new Dictionary");
		nameText = new Text(composite, SWT.SINGLE);
				
		KeyListener listener = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {	
				setTexts();
			}
		};
		nameText.addKeyListener(listener);
			
		
		idLabel = new Label(composite, SWT.LEFT);
		idLabel.setText("Id of new Dictionary");
		idText = new Text(composite, SWT.SINGLE);		
			
		errorMessage = new Label(composite, SWT.LEFT);
		errorMessage.setText("                                                                         ");
		//errorMessage.setSize(300, 40);
		errorMessage.setForeground(new Color(Display.getCurrent(), new RGB(255, 0, 0)));
		
		fileNamesCombo = new Combo(composite, SWT.READ_ONLY);
		for (int i = 0; i<fileNames.size(); ++i)
			fileNamesCombo.add(fileNames.get(i));
						
        return composite;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Point size = new Point(500, 500);
		newShell.setSize(size);
		Shell p = getParentShell();
		newShell.setLocation(p.getLocation().x + p.getSize().x / 2 - size.x / 2, p.getLocation().y + p.getSize().y / 2 - size.y / 2);
		
		newShell.setText("Enter id and name of new Dictionary...");		
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
		isValidDictId = true;
		for (String otherId : dictIds) {
			if (id.equals(otherId)) {
				isValidDictId = false;
				break;
			}
		}
		
		idText.setText(id);
		if (!isValidDictId) {
			errorMessage.setText("Bad dict id");
			getButton(IDialogConstants.OK_ID).setEnabled(false);			
			return;
		}
		else {
			errorMessage.setText("");
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
		
////////////////////////////////////////////////////////////////////		
		
	}
	
	private void validateSelection() {
		if (fileNamesCombo.getSelectionIndex() == -1) {
			errorMessage.setText("Select File");
			getButton(IDialogConstants.OK_ID).setEnabled(false);			
			return;
		}
		else {
			errorMessage.setText("");
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
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