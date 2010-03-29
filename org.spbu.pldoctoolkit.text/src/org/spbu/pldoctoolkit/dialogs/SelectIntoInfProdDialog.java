package org.spbu.pldoctoolkit.dialogs;

import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.spbu.pldoctoolkit.actions.SelectIntoInfProductAction;
import org.spbu.pldoctoolkit.wizards.NewDrlFilePage;

public class SelectIntoInfProdDialog extends Dialog {
	private IPath containerPath;
	
	private String infProdId = "";
	private String infProdName = "";
	private String finalInfProdId = "";
	private String infElemId = "";
	private String infElemName = "";
	private String infElemRefInText = "";
	private String infElemRefInProduct = "";
	private String productFile = "";
	private String finalFile = "";

	private Text prodIdText;
	private Text prodNameText;
	private Text finalProdIdText;
	private Text elemIdText;
	private Text elemNameText;
	private Text refInTextIdText;
	private Text refInProductIdText;
	private Text productFileText;
	private Text finalFileText;

	private Label prodIdLabel;
	private Label prodNameLabel;
	private Label finalProdIdLabel;
	private Label elemIdLabel;
	private Label elemNameLabel;
	private Label refInTextIdLabel;
	private Label refInProductIdLabel;
	private Label productFileLabel;
	private Label finalFileLabel;

	// private Label errorMessage;
	private Label errorMessageProdId;
	private Label errorMessageFinalProdId;
	private Label errorMessageElemId;
	private Label errorMessageTextRefId;
	private Label errorMessageProductRefId;
	private Label errorMessageProductFile;
	private Label errorMessageFinalFile;

	private boolean isValideElemId = false;
	private boolean isValideTextRefId = false;
	private boolean isValideProdRefId = false;
	private boolean isValideProdId = false;
	private boolean isValideFinalProdId = false;

	private ArrayList<String> infProdIds = new ArrayList<String>();
	private ArrayList<String> finalInfProdIds = new ArrayList<String>();
	private ArrayList<String> infElemIds = new ArrayList<String>();
	private ArrayList<String> infElemRefIds = new ArrayList<String>();

	public SelectIntoInfProdDialog(Shell parentShell, IPath containerPath) {
		super(parentShell);
		setBlockOnOpen(true);
		this.containerPath = containerPath;
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 20;
		composite.setLayout(layout);

		Label description = new Label(composite, SWT.LEFT);
		description.setText("Extract selection into new InfProduct");
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 3;
		description.setLayoutData(gd);

		// 1///////////////////////////////////////////////////////////

		prodNameLabel = new Label(composite, SWT.LEFT);
		prodNameLabel.setText("Name of new InfProduct:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		prodNameLabel.setLayoutData(gd);

		prodNameText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		prodNameText.setLayoutData(gd);

		KeyListener prodListener = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				setProductTexts();
			}
		};
		prodNameText.addKeyListener(prodListener);

		new Label(composite, SWT.NONE);

		// 2////////////////////////////////////////////////////////////////////////////////////

		prodIdLabel = new Label(composite, SWT.LEFT);
		prodIdLabel.setText("Id of new InfProduct:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		prodIdLabel.setLayoutData(gd);

		prodIdText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		prodIdText.setLayoutData(gd);
		prodIdText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});

		errorMessageProdId = new Label(composite, SWT.LEFT);
		errorMessageProdId
				.setText("                                                 ");
		errorMessageProdId.setForeground(new Color(Display.getCurrent(),
				new RGB(255, 0, 0)));
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		errorMessageProdId.setLayoutData(gd);

		// 4////////////////////////////////////////////////////////////////////////////////////

		finalProdIdLabel = new Label(composite, SWT.LEFT);
		finalProdIdLabel.setText("Id of new FinalInfProduct:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		finalProdIdLabel.setLayoutData(gd);

		finalProdIdText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		finalProdIdText.setLayoutData(gd);
		finalProdIdText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});

		errorMessageFinalProdId = new Label(composite, SWT.LEFT);
		errorMessageFinalProdId
				.setText("                                                   ");
		errorMessageFinalProdId.setForeground(new Color(Display.getCurrent(),
				new RGB(255, 0, 0)));
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		errorMessageFinalProdId.setLayoutData(gd);

		// 5///////////////////////////////////////////////////////////

		elemNameLabel = new Label(composite, SWT.LEFT);
		elemNameLabel.setText("Name of new InfElement:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		elemNameLabel.setLayoutData(gd);

		elemNameText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		elemNameText.setLayoutData(gd);

		KeyListener elementListener = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				setElementTexts();
			}
		};
		elemNameText.addKeyListener(elementListener);

		new Label(composite, SWT.NONE);

		// 6////////////////////////////////////////////////////////////////////////////////////

		elemIdLabel = new Label(composite, SWT.LEFT);
		elemIdLabel.setText("Id of new InfElement:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		elemIdLabel.setLayoutData(gd);

		elemIdText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		elemIdText.setLayoutData(gd);
		elemIdText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});

		errorMessageElemId = new Label(composite, SWT.LEFT);
		errorMessageElemId
				.setText("                                                   ");
		errorMessageElemId.setForeground(new Color(Display.getCurrent(),
				new RGB(255, 0, 0)));
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		errorMessageElemId.setLayoutData(gd);

		// 7//////////////////////////////////////////////////////////////////////////////////////

		refInTextIdLabel = new Label(composite, SWT.LEFT);
		refInTextIdLabel.setText("InfElemtRef in text:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		refInTextIdLabel.setLayoutData(gd);

		refInTextIdText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		refInTextIdText.setLayoutData(gd);
		refInTextIdText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});

		errorMessageTextRefId = new Label(composite, SWT.LEFT);
		errorMessageTextRefId
				.setText("                                                   ");
		errorMessageTextRefId.setForeground(new Color(Display.getCurrent(),
				new RGB(255, 0, 0)));
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		errorMessageTextRefId.setLayoutData(gd);
		// 8//////////////////////////////////////////////////////////////////////////////////////

		refInProductIdLabel = new Label(composite, SWT.LEFT);
		refInProductIdLabel.setText("InfElemtRef in InfProduct:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		refInProductIdLabel.setLayoutData(gd);

		refInProductIdText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		refInProductIdText.setLayoutData(gd);
		refInProductIdText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});

		errorMessageProductRefId = new Label(composite, SWT.LEFT);
		errorMessageProductRefId
				.setText("                                                   ");
		errorMessageProductRefId.setForeground(new Color(Display.getCurrent(),
				new RGB(255, 0, 0)));
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		errorMessageProductRefId.setLayoutData(gd);
//9////////////////////////////////////////////////////////////////////////////////
		productFileLabel = new Label(composite, SWT.LEFT);
		productFileLabel.setText("InfProduct file:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		productFileLabel.setLayoutData(gd);

		productFileText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		productFileText.setLayoutData(gd);
		productFileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});

		errorMessageProductFile = new Label(composite, SWT.LEFT);
		errorMessageProductFile
				.setText("                                                   ");
		errorMessageProductFile.setForeground(new Color(Display.getCurrent(),
				new RGB(255, 0, 0)));
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		errorMessageProductFile.setLayoutData(gd);

		// 10////////////////////////////////////////////////////////////////////////////////////

		finalFileLabel = new Label(composite, SWT.LEFT);
		finalFileLabel.setText("FinalInfProduct File:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		finalFileLabel.setLayoutData(gd);

		finalFileText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		finalFileText.setLayoutData(gd);
		finalFileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});

		errorMessageFinalFile = new Label(composite, SWT.LEFT);
		errorMessageFinalFile
				.setText("                                                   ");
		errorMessageFinalFile.setForeground(new Color(Display.getCurrent(),
				new RGB(255, 0, 0)));
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		errorMessageFinalFile.setLayoutData(gd);

		
		return composite;
	}

	public int open() {
		int res = super.open();

		return res;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Point size = new Point(600, 500);
		newShell.setSize(size);
		Shell p = getParentShell();
		newShell.setLocation(
				p.getLocation().x + p.getSize().x / 2 - size.x / 2, p
						.getLocation().y
						+ p.getSize().y / 2 - size.y / 2);

		newShell.setText(SelectIntoInfProductAction.refactName);
	}

	protected void okPressed() {
		infProdName = prodNameText.getText();
		infProdId = prodIdText.getText();
		finalInfProdId = finalProdIdText.getText();
		infElemId = elemIdText.getText();
		infElemName = elemNameText.getText();
		infElemRefInText = refInTextIdText.getText();
		infElemRefInProduct = refInProductIdText.getText();
		productFile = productFileText.getText();
		finalFile = finalFileText.getText();
		super.okPressed();
	}

	private void setProductTexts() {
		String prodName = prodNameText.getText();
		if (prodName.length() > 0) {
			prodIdText.setText(prodName + "_id");
			finalProdIdText.setText(prodName + "_finalProd");
			elemIdText.setText(prodName + "_elem_id");
			elemNameText.setText(prodName + "_elem");
			refInTextIdText.setText(prodName + "_textRef");
			refInProductIdText.setText(prodName + "_productRef");
			productFileText.setText(prodName + ".drl");
			finalFileText.setText(prodName + "_finalProd.drl");
			validate();
		} else {
			prodIdText.setText("");
			finalProdIdText.setText("");
			elemIdText.setText("");
			elemNameText.setText("");
			refInTextIdText.setText("");
			refInProductIdText.setText("");
			productFileText.setText("");
			finalFileText.setText("");
		}

	}

	private void setElementTexts() {
		String elemName = elemNameText.getText();
		if (elemName.length() > 0) {
			elemIdText.setText(elemName + "_elem_name");
			refInTextIdText.setText(elemName + "_textRef");
			refInProductIdText.setText(elemName + "_productRef");
			validate();
		} else {
			elemIdText.setText("");
			refInTextIdText.setText("");
			refInProductIdText.setText("");
		}
	}

	private void validate() {
		isValideProdId = true;
		for (String otherId : infProdIds) {
			if (prodIdText.getText().equals(otherId)) {
				isValideProdId = false;
				break;
			}
		}

		if (!isValideProdId) {
			errorMessageProdId.setText("Bad infProduct id");
			getButton(IDialogConstants.OK_ID).setEnabled(false);
		} else {
			errorMessageProdId.setText("");
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}

		// //////////////////////////////////////////////////////////////////

		isValideFinalProdId = true;
		for (String otherId : finalInfProdIds) {
			if (finalProdIdText.getText().equals(otherId)) {
				isValideFinalProdId = false;
				break;
			}
		}

		if (!isValideFinalProdId) {
			errorMessageFinalProdId.setText("Bad finalInfProduct id");
			getButton(IDialogConstants.OK_ID).setEnabled(false);
		} else {
			errorMessageFinalProdId.setText("");
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}

		// //////////////////////////////////////////////////////////////////
		isValideElemId = true;
		for (String otherId : infElemIds) {
			if (elemIdText.getText().equals(otherId)) {
				isValideElemId = false;
				break;
			}
		}

		if (!isValideElemId) {
			errorMessageElemId.setText("Bad infElement id");
			getButton(IDialogConstants.OK_ID).setEnabled(false);
		} else {
			errorMessageElemId.setText("");
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}

		// //////////////////////////////////////////////////////////////////

		isValideTextRefId = true;
		for (String otherId : infElemRefIds) {
			if (refInTextIdText.getText().equals(otherId)) {
				isValideTextRefId = false;
				break;
			}
		}

		if (!isValideTextRefId) {
			errorMessageTextRefId.setText("Bad infElementRef id");
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			;
		} else {
			errorMessageTextRefId.setText("");
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
		// //////////////////////////////////////////////////////////////////

		isValideProdRefId = true;
		for (String otherId : infElemRefIds) {
			if (refInProductIdText.getText().equals(otherId)) {
				isValideProdRefId = false;
				break;
			}
		}

		if (!isValideProdRefId) {
			errorMessageProductRefId.setText("Bad infElementRef id");
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			;
		} else {
			errorMessageProductRefId.setText("");
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
		//validate files////////////////////////////////////////////////////
		
		String productMessage = validateProductFile();

		if (productMessage!= null) {
			errorMessageProductFile.setText(productMessage);
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			;
		} else {
			errorMessageProductFile.setText("");
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
		// //////////////////////////////////////////////////////////////////
	
		String finalMessage = validateFinalFile();
		if (finalMessage != null) {
			errorMessageFinalFile.setText(finalMessage);
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			;
		} else {
			errorMessageFinalFile.setText("");
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
		
	}
	
	private String validateProductFile() {
		String message = null;
		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IContainer container = (IContainer) root.findMember(containerPath);
		
		message = NewDrlFilePage.validateFileName(productFileText.getText(), "InfProduct file");
		if (message != null)
			return message;		
		IFile docCoreFile = container.getFile(new Path(productFileText.getText()));
		if (docCoreFile.exists())
			return productFileText.getText() + " file exists in project";
		return null;
	}
	
	private String validateFinalFile() {
		String message = null;
		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IContainer container = (IContainer) root.findMember(containerPath);
		
		message = NewDrlFilePage.validateFileName(finalFileText.getText(), "FinalProduct file");
		if (message != null)
			return message;
		IFile productDocFile = container.getFile(new Path(finalFileText.getText()));
		if (productDocFile.exists())
			return finalFileText.getText() + " file exists in project";
		
		return null;
	}
	
	public String getInfElemId() {
		return infElemId;
	}

	public String getInfElemName() {
		return infElemName;
	}

	public String getInfprodName() {
		return infProdName;
	}

	public String getInfprodId() {
		return infProdId;
	}

	public String getInfElemRefInText() {
		return infElemRefInText;
	}

	public String getInfElemRefInProduct() {
		return infElemRefInProduct;
	}

	public String getFinInfProdId() {
		return finalInfProdId;
	}

	public String getFinalProdFile() {
		return finalFile;
	}

	public String getInfProdFile() {
		return productFile;
	}
	
	public void addInfProdIds(String id) {
		infProdIds.add(id);
	}

	public void addFinInfProdIds(String id) {
		finalInfProdIds.add(id);
	}

	public void addInfElemId(String id) {
		infElemIds.add(id);
	}

	public void addInfElemRefId(String id) {
		infElemRefIds.add(id);
	}

}
