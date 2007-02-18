package org.spbu.pldoctoolkit.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
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

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (xml).
 */
public class NewFamilyWizardPage extends WizardPage {
	private Text containerText;
	private Text nameText;
	private ISelection selection;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param selection
	 */
	public NewFamilyWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("Documentation Family");
		setDescription("This wizard creates a new Product-Line Documentation Family.");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText("&Container:");
		
		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		containerText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("&Name:");

		nameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */
	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
				containerText.setText(container.getFullPath().toString());
			}
		}
		nameText.setText("NewFamily");
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */
	private void handleBrowse() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), root, false, "Select new file container");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path) result[0]).toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {
		setPageComplete(validateContainer() && validateName());
	}
	
	private boolean validateContainer() {
		if (getName().length() == 0)
			return false;
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource container = root.findMember(new Path(getContainerName()));
		if (container == null || (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0)
			return false;
		return true;
	}

	private boolean validateName() {
		String name = getFamilyName();
		if (name.length() == 0)
			return false;
		char[] chars = name.toCharArray();
		if (!Character.isLetter(chars[0]))
			return false;
		for (int i = 1; i < chars.length; i++)
			if (!Character.isLetterOrDigit(chars[i]))
				return false;
		return true;
	}

	public String getContainerName() {
		return containerText.getText();
	}

	public String getFamilyName() {
		return nameText.getText();
	}
	
	public String getFileName() {
		return nameText.getText() + ".xml";
	}
}