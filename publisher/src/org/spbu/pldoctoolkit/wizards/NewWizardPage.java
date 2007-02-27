package org.spbu.pldoctoolkit.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

public class NewWizardPage extends WizardPage {
	private IStructuredSelection selection;
	private String nameLabel;

	private Text containerText;
	private Text nameText;
	
	protected NewWizardPage(String pageName, String nameLabel, IStructuredSelection selection) {
		super(pageName);
		this.selection = selection;
		this.nameLabel = nameLabel;
	}

	public void createControl(Composite parent) {
		Composite topLevel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		topLevel.setLayout(layout);
		
		// === container group ===
		new Label(topLevel, SWT.NONE).setText("Parent folder:");
		containerText = new Text(parent, SWT.BORDER | SWT.SINGLE);
		containerText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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
		
		// === name group ===
		new Label(topLevel, SWT.NONE).setText(nameLabel);
		nameText = new Text(parent, SWT.BORDER | SWT.SINGLE);
		nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				notifyDialogChanged();
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
		nameText.setText("Untitled");
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
			if (!Character.isLetterOrDigit(chars[i]) && chars[i] != '_')
				return "Name must contain only letters, digits or underscores";
		return null;
	}

	private String validateContainer() {
		String containerName = containerText.getText();
		IResource container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(containerName));
		if (containerName.length() == 0)
			return "Parent folder must be specified";
		if (container == null || (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0 )
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
	
	public String getNameString() {
		return nameText.toString();
	}
	
	public String getContainerString() {
		return containerText.toString();
	}
}
