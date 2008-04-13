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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.spbu.pldoctoolkit.actions.SelectIntoInfElementAction;

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
	
	//private Label errorMessage;
	private Label errorMessageId;
	private Label errorMessageRefId;
	
	private boolean isValideElemId = false;
	private boolean isValideElemRefId = false;
	
	private ArrayList<String> infElemIds = new ArrayList<String>();
	private ArrayList<String> infElemRefIds = new ArrayList<String>();
	
	public SelectIntoInfElemDialog(Shell parentShell) {
		super(parentShell);
		setBlockOnOpen(true);
	}
	
	protected Control createDialogArea(Composite parent) {
		//Composite composite = (Composite) super.createDialogArea(parent);
		//composite.setLayout(new GridLayout(1, false));
		
		
		Composite composite = (Composite) super.createDialogArea(parent);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		composite.setLayout(layout);
		
		////////////////////////////////////////////////////////////
/*		
		dirList = new Combo(composite, SWT.MULTI | SWT.BORDER);
		setDirListContent();
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		dirList.setLayoutData(gd);
		
		new Label(composite, SWT.NONE);
	*/	
		
		Label description = new Label(composite, SWT.LEFT);
		description.setText("Extracts selected fragment into new InfElement, insert InfElemRef,\n" +
							"create Adapter for new InfElemRef and change content of appropriate Adapters\n\n");
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 3;
		description.setLayoutData(gd);		
		
		/////////////////////////////////////////////////////////////
		
		nameLabel = new Label(composite, SWT.LEFT);
		nameLabel.setText("Name of new InfElement:");
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
		
//////////////////////////////////////////////////////////////////////////////////////
			
		
		idLabel = new Label(composite, SWT.LEFT);
		idLabel.setText("Id of new InfElement:");
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
		
		errorMessageId = new Label(composite, SWT.LEFT);
		errorMessageId.setText("                                                 ");		
		errorMessageId.setForeground(new Color(Display.getCurrent(), new RGB(255, 0, 0)));
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		errorMessageId.setLayoutData(gd);
				
////////////////////////////////////////////////////////////////////////////////////////		
		
		refIdLabel = new Label(composite, SWT.LEFT);
		refIdLabel.setText("Id of new InfElemtRef:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		refIdLabel.setLayoutData(gd);
		
		refIdText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		refIdText.setLayoutData(gd);
		refIdText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		
		errorMessageRefId = new Label(composite, SWT.LEFT);
		errorMessageRefId.setText("                                                 ");		
		errorMessageRefId.setForeground(new Color(Display.getCurrent(), new RGB(255, 0, 0)));
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		errorMessageRefId.setLayoutData(gd);
						
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
		Point size = new Point(420, 210);
		newShell.setSize(size);
		Shell p = getParentShell();
		newShell.setLocation(p.getLocation().x + p.getSize().x / 2 - size.x / 2, p.getLocation().y + p.getSize().y / 2 - size.y / 2);
		
		newShell.setText(SelectIntoInfElementAction.refactName);		
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
		idText.setText(id);
		
		String refId = name + "_refid";
		refIdText.setText(refId);
		
		validate();
	}
	
	private void validate() {
		isValideElemId = true;
		for (String otherId : infElemIds) {
			if (idText.getText().equals(otherId)) {
				isValideElemId = false;
				break;
			}
		}		
		
		if (!isValideElemId) {
			errorMessageId.setText("Bad infElement id");
			getButton(IDialogConstants.OK_ID).setEnabled(false);			
			//return;
		}
		else {
			errorMessageId.setText("");
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
		
////////////////////////////////////////////////////////////////////		
		
		isValideElemRefId = true;
		for (String otherId : infElemRefIds) {
			if (refIdText.getText().equals(otherId)) {
				isValideElemRefId = false;
				break;
			}
		}
		
		
		if (!isValideElemRefId) {
			errorMessageRefId.setText("Bad infElementRef id");
			getButton(IDialogConstants.OK_ID).setEnabled(false);;			
			//return;
		}
		else {
			errorMessageRefId.setText("");
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
