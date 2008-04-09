package org.spbu.pldoctoolkit.dialogs;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;

public class SelectIntoInfElemDialog extends Dialog{
	private String infElemId = "";
	private String infElemName = "";
	private String infElemRefId = "";
	
	private Text idText;
	private Text nameText;
	private Text refIdText;
	
	private Label idLabel;
	private Label nameLabel;
	private Label refIdLabel;
	
	private Label errorMessage;
	private boolean isValideElemId = false;
	private boolean isValideElemRefId = false;
	
	private ArrayList<String> infElemIds = new ArrayList<String>();
	private ArrayList<String> infElemRefIds = new ArrayList<String>();
	
	public SelectIntoInfElemDialog(Shell parentShell) {
		super(parentShell);
		setBlockOnOpen(true);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		//composite.setLayout(new GridLayout(1, false));
		nameLabel = new Label(composite, SWT.LEFT);
		nameLabel.setText("Name of new InfElement");
		nameText = new Text(composite, SWT.SINGLE);
				
		KeyListener listener = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				//errorMessage.setText(nameText.getText());
				setTexts();
			}
		};
		nameText.addKeyListener(listener);
			
		
		idLabel = new Label(composite, SWT.LEFT);
		idLabel.setText("Id of new InfElement");		
		idText = new Text(composite, SWT.SINGLE | SWT.BORDER);
				
		refIdLabel = new Label(composite, SWT.LEFT);
		refIdLabel.setText("Id of new InfElementRef");
		refIdText = new Text(composite, SWT.SINGLE);
		
		errorMessage = new Label(composite, SWT.LEFT);
		errorMessage.setText("                                                                         ");
		//errorMessage.setSize(300, 40);
		errorMessage.setForeground(new Color(Display.getCurrent(), new RGB(255, 0, 0)));
						
        return composite;
	}
	
	public int open() {
//		getButton(IDialogConstants.OK_ID).setEnabled(false);
		int res = super.open(); 		
				
		return res;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Point size = new Point(500, 500);
		newShell.setSize(size);
		Shell p = getParentShell();
		newShell.setLocation(p.getLocation().x + p.getSize().x / 2 - size.x / 2, p.getLocation().y + p.getSize().y / 2 - size.y / 2);
		
		newShell.setText("Enter id and name of new InfElem...");		
	}	

	protected void okPressed()
	{
		infElemId = idText.getText();
		infElemName = nameText.getText();
		infElemRefId = refIdText.getText();
		super.okPressed();
	}
	
	private void setTexts() {
		String name = nameText.getText();
		String id = name + "_id";
		isValideElemId = true;
		for (String otherId : infElemIds) {
			if (id.equals(otherId)) {
				isValideElemId = false;
				break;
			}
		}
		
		idText.setText(id);
		if (!isValideElemId) {
			errorMessage.setText("Bad infElement id");
			getButton(IDialogConstants.OK_ID).setEnabled(false);			
			return;
		}
		else {
			errorMessage.setText("");
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
		
////////////////////////////////////////////////////////////////////
		
		String refId = name + "_refid";
		isValideElemRefId = true;
		for (String otherId : infElemRefIds) {
			if (refId.equals(otherId)) {
				isValideElemRefId = false;
				break;
			}
		}
		
		refIdText.setText(refId);
		if (!isValideElemRefId) {
			errorMessage.setText("Bad infElementRef id");
			getButton(IDialogConstants.OK_ID).setEnabled(false);;			
			return;
		}
		else {
			errorMessage.setText("");
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
	}
	
	public String getInfElemId()
	{
		return infElemId;
	}
	
	public String getInfElemName()
	{
		return infElemName;
	}
	
	public String getInfElemRefId()
	{
		return infElemRefId;
	}
	
	public void addId(String id) {
		infElemIds.add(id);		
	}
	
	public void addRefId(String id) {
		infElemRefIds.add(id);		
	}
}
