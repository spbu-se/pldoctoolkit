package org.spbu.pldoctoolkit.wizards;

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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

public class NewDrlFilePage extends WizardPage {
	public static final String PRODUCT_LINE = "ProductLine";
	public static final String DOCUMENTATION_CORE = "DocumentationCore";
	public static final String PRODUCT_DOCUMENTATION = "ProductDocumentation";
	
	private IStructuredSelection selection;
	private String type = PRODUCT_LINE;
	
	private Text containerText;
	private Text nameText;
	
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
		new Label(topLevel, SWT.NONE);
		
		// === type group ===
		Group typeGroup = new Group(topLevel, SWT.NONE);
		typeGroup.setLayout(new GridLayout(1, true));
		typeGroup.setText("Select type");
		gd = new GridData();
		gd.horizontalSpan = 3;
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		
		typeGroup.setLayoutData(gd);
		Button button = new Button(typeGroup, SWT.RADIO);
		button.setSelection(true);
		button.setText("Product line");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (((Button)e.getSource()).getSelection()) 
					type = PRODUCT_LINE;
			}
		});
		
		typeGroup.setLayoutData(gd);
		button = new Button(typeGroup, SWT.RADIO);
		button.setText("Documentation core");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (((Button)e.getSource()).getSelection()) 
					type = DOCUMENTATION_CORE;
			}
		});
		
		typeGroup.setLayoutData(gd);
		button = new Button(typeGroup, SWT.RADIO);
		button.setText("Product documentation");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (((Button)e.getSource()).getSelection()) 
					type = PRODUCT_DOCUMENTATION;
			}
		});
		
		initialize();
		setControl(topLevel);
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
		if ((message = validateName()) != null) {
			setErrorMessage(message);
			setPageComplete(false);
			return;
		}
		setErrorMessage(null);
		setPageComplete(true);
	}
	
	private String validateName() {
		String name = nameText.getText();
		if (name.length() == 0)
			return "Name must be specified";
		char[] chars = name.toCharArray();
		if (!Character.isLetter(chars[0]))
			return "Name must begin with a letter";
		for (int i = 1; i < chars.length; i++)
			if (!Character.isLetterOrDigit(chars[i]) && chars[i] != '_' && chars[i] != '.')
				return "Name must contain only letters, digits or underscores";
		if (!name.endsWith(".drl"))
			return "Name must end with '.drl'";
		return null;
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
	
	public IFile getFile() {
		if (validateContainer() != null || validateName() != null)
			return null;
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IContainer container = (IContainer) root.findMember(new Path(containerText.getText()));
		return container.getFile(new Path(nameText.getText()));
	}
	
	public String getType() {
		return type;
	}
}
