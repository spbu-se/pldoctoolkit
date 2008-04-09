package org.spbu.pldoctoolkit.wizards;

import java.io.File;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;

public class NewDrlFilePage extends WizardPage {
	public static final String PRODUCT_LINE = "ProductLine";
	public static final String DOCUMENTATION_CORE = "DocumentationCore";
	public static final String PRODUCT_DOCUMENTATION = "ProductDocumentation";
	
	private IStructuredSelection selection;
	private String type = PRODUCT_LINE;
	
	private Text containerText;
	private Text nameText;
	
	private Text productDocText;
	private Label pruductDocLabel;
	
	private Text docCoreText;
	private Label docCoreLabel;
	
	private Button docTypeButton1, docTypeButton2, docTypeButton3;
	private Group typeGroup;
	private Button fileBrowseButton;
	
	private boolean sourceType = false; // new DRL doc
	
	public NewDrlFilePage(String pageName, String title, ImageDescriptor titleImage, IStructuredSelection selection) {
		super(pageName, title, titleImage);
		this.selection = selection;
	}

	public NewDrlFilePage(String pageName, IStructuredSelection selection) {
		super(pageName);
		this.selection = selection;
	}

	public void createControl(Composite parent) {
		Composite topLevel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		topLevel.setLayout(layout);
		
		// === container ===
		new Label(topLevel, SWT.NONE).setText("Parent folder:");
		containerText = new Text(topLevel, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		containerText.setLayoutData(gd);
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				notifyDialogChanged();
			}
		});
		Button browseButton = new Button(topLevel, SWT.PUSH);
		browseButton.setText("Browse...");
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		
		// === name ===
		new Label(topLevel, SWT.NONE).setText("File name:");
		nameText = new Text(topLevel, SWT.BORDER | SWT.SINGLE);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		nameText.setLayoutData(gd);
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				notifyDialogChanged();
			}
		});
		
		fileBrowseButton = new Button(topLevel, SWT.PUSH);
		fileBrowseButton.setText("Browse...");
		fileBrowseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleFileBrowse();
			}
		});
		
		//new Label(topLevel, SWT.NONE);
		
		// === productDoc ===
		
		pruductDocLabel = new Label(topLevel, SWT.NONE);
		pruductDocLabel.setText("ProductDocumentation file name:");
		productDocText = new Text(topLevel, SWT.BORDER | SWT.SINGLE);
		productDocText.setText("");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		productDocText.setLayoutData(gd);
		productDocText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				notifyDialogChanged();
			}
		});	
		
		new Label(topLevel, SWT.NONE);
		
		// === docCore ===
		
		docCoreLabel = new Label(topLevel, SWT.NONE);
		docCoreLabel.setText("DocumentationCore file name:");
		docCoreText = new Text(topLevel, SWT.BORDER | SWT.SINGLE);
		docCoreText.setText("");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		docCoreText.setLayoutData(gd);
		docCoreText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				notifyDialogChanged();
			}
		});	
		
		new Label(topLevel, SWT.NONE);
		
		// === sourse group === /////////////////////////////////////////////////////////
		
		Group sourceGroup = new Group(topLevel, SWT.NONE);
		sourceGroup.setLayout(new GridLayout(1, true));
		//sourceGroup.setText("Select ");
		gd = new GridData();
		gd.horizontalSpan = 3;
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		
		sourceGroup.setLayoutData(gd);
		Button button = new Button(sourceGroup, SWT.RADIO);
		button.setSelection(true);
		button.setText("New DRL document");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (((Button)e.getSource()).getSelection()) 
					setSource(false);
			}
		});
		
		sourceGroup.setLayoutData(gd);
		button = new Button(sourceGroup, SWT.RADIO);
		button.setText("Documentation from existing docBook file");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (((Button)e.getSource()).getSelection()) 
					setSource(true);
			}
		});
		
		// === type group ===
		typeGroup = new Group(topLevel, SWT.NONE);
		typeGroup.setLayout(new GridLayout(1, true));
		typeGroup.setText("Select type");
		gd = new GridData();
		gd.horizontalSpan = 3;
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		
		typeGroup.setLayoutData(gd);
		docTypeButton1 = new Button(typeGroup, SWT.RADIO);
		docTypeButton1.setSelection(true);
		docTypeButton1.setText("Product line");
		docTypeButton1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (((Button)e.getSource()).getSelection()) 
					type = PRODUCT_LINE;
			}
		});
		
		typeGroup.setLayoutData(gd);
		docTypeButton2 = new Button(typeGroup, SWT.RADIO);
		docTypeButton2.setText("Documentation core");
		docTypeButton2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (((Button)e.getSource()).getSelection()) 
					type = DOCUMENTATION_CORE;
			}
		});
		
		typeGroup.setLayoutData(gd);
		docTypeButton3 = new Button(typeGroup, SWT.RADIO);
		docTypeButton3.setText("Product documentation");
		docTypeButton3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (((Button)e.getSource()).getSelection()) 
					type = PRODUCT_DOCUMENTATION;
			}
		});
		
		initialize();
		setControl(topLevel);
		
		setSource(sourceType);
	}
	
	private void setSource(boolean fromFile) {
		if (fromFile) {
			typeGroup.setEnabled(false);
			docTypeButton1.setEnabled(false);
			docTypeButton2.setEnabled(false);
			docTypeButton3.setEnabled(false);
			
			fileBrowseButton.setEnabled(true);
			
			docCoreLabel.setEnabled(true);
			docCoreText.setEnabled(true);
			pruductDocLabel.setEnabled(true);
			productDocText.setEnabled(true);
			
			this.setTitle("Create a new DRL file with FinalInfProduct from existing docBook file\nand create new file with DocumentationCore");			
		}
		else {
			typeGroup.setEnabled(true);
			docTypeButton1.setEnabled(true);
			docTypeButton2.setEnabled(true);
			docTypeButton3.setEnabled(true);
			
			fileBrowseButton.setEnabled(false);			
			
			docCoreLabel.setEnabled(false);
			docCoreText.setEnabled(false);
			pruductDocLabel.setEnabled(false);
			productDocText.setEnabled(false);
			
			this.setTitle("Create a new DRL file");
		}
		
		sourceType = fromFile;
	}
	
	private void initialize() {
		if (selection.size() == 1) {
			Object sel = selection.iterator().next();
			IContainer container = null;
			if (sel instanceof IContainer)
				container = (IContainer)sel;
			else if (sel instanceof IResource)
				container = ((IResource)sel).getParent();
			if (container != null)
				containerText.setText(container.getFullPath().toString());
		}
		nameText.setText("Untitled.drl");
	}

	private void notifyDialogChanged() {
		String message;
		if ((message = validateContainer()) != null) {
			setErrorMessage(message);
			setPageComplete(false);
			return;
		}
		
		if (sourceType) {
			if ((message = validateCoreAndProduct()) != null) {
				setErrorMessage(message);
				setPageComplete(false);
				return;
			}
		}
		else {
			if ((message = validateName()) != null) {
				setErrorMessage(message);
				setPageComplete(false);
				return;
			}
		}
		
		setErrorMessage(null);
		setPageComplete(true);
	}
	
	private String validateName() {
		String name = nameText.getText();
		return validateFileName(name, "Name");
	}
	
	private String validateFileName(String name, String messagePrefix) {
		if (name.length() == 0)
			return messagePrefix + " must be specified";
		char[] chars = name.toCharArray();
		if (!Character.isLetter(chars[0]))
			return messagePrefix + " must begin with a letter";
		for (int i = 1; i < chars.length; i++)
			if (!Character.isLetterOrDigit(chars[i]) && chars[i] != '_' && chars[i] != '.')
				return messagePrefix + " must contain only letters, digits or underscores";
		if (!name.endsWith(".drl"))
			return messagePrefix + " must end with '.drl'";
		return null;
	}
	
	private String validateCoreAndProduct() {
		String message = null;
		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IContainer container = (IContainer) root.findMember(new Path(containerText.getText()));
		
		message = validateFileName(docCoreText.getText(), "DocumentationCore file");
		if (message != null)
			return message;		
		IFile docCoreFile = container.getFile(new Path(docCoreText.getText()));
		if (docCoreFile.exists())
			return docCoreText.getText() + " file exists in project";
		
		message = validateFileName(productDocText.getText(), "ProductDocumentation file");
		if (message != null)
			return message;
		IFile productDocFile = container.getFile(new Path(productDocText.getText()));
		if (productDocFile.exists())
			return productDocText.getText() + " file exists in project";
		
		return null;
		/*
		if (message != null) {
			setErrorMessage(message);
			setPageComplete(false);
		}*/		
	}

	private String validateContainer() {
		String containerName = containerText.getText();
		IResource container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(containerName));
		if (containerName.length() == 0)
			return "Parent folder must be specified";
		if (container == null || !(container instanceof IContainer))
			return "Parent folder must exist";
		if (!container.isAccessible())
			return "Parent folder must be accessible";
		return null;
	}

	private void handleBrowse() {
		ContainerSelectionDialog csd = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false, "Select parent folder");
		if (csd.open() == ContainerSelectionDialog.OK) {
			Object[] result = csd.getResult();
			if (result.length == 1)
				containerText.setText(((Path)result[0]).toString());
		}
	}
	
	private void handleFileBrowse() {
		FileDialog dialog = new FileDialog(getShell());
		String path = dialog.open();
		if (path != null) {			
			File file = new File(path);
			if (file.exists()) {
				try {
					nameText.setText(path);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				catch (Error e) {
					e.printStackTrace();
				}
				String name = file.getName();
				int dotPos = name.indexOf('.');
				String nameWithOutExt;
				if (dotPos == -1)
					nameWithOutExt = name;
				else
					nameWithOutExt = name.substring(0, dotPos);
				
				docCoreText.setText(nameWithOutExt + "_core.drl");
				productDocText.setText(nameWithOutExt + "_product.drl");
			}
		}
	}
	
	public IFile getFile() {
		if (validateContainer() != null || validateName() != null)
			return null;
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IContainer container = (IContainer) root.findMember(new Path(containerText.getText()));
		return container.getFile(new Path(nameText.getText()));
	}
	
	public String getContainerName() {
		return containerText.getText();
	}
	
	public String getFileName() {
		return nameText.getText();
	}
	
	public IFile getDocCoreFile() {
//		if (validateContainer() != null || validateName() != null)
	//		return null;
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IContainer container = (IContainer) root.findMember(new Path(containerText.getText()));
		return container.getFile(new Path(docCoreText.getText()));
	}
	
	public IFile getProductDocFile() {
//		if (validateContainer() != null || validateName() != null)
	//		return null;
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IContainer container = (IContainer) root.findMember(new Path(containerText.getText()));
		return container.getFile(new Path(productDocText.getText()));
	}
	
	public String getType() {
		return type;
	}
	
	public boolean getSourceType() {
		return sourceType;
	}
}
