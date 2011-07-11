package org.spbu.plweb.diagram.part;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.spbu.plweb.diagram.util.projects.ProjectWebRatio;

/**
 * @generated NOT
 */

public class PlwebCreationWizardPage extends WizardPage {

	private static final String SAVED_LOCATION_ATTR = "OUTSIDE_LOCATION"; //$NON-NLS-1$

	private Text projectLocationPathField;
	private Button browseProjectButton;

	private Text docLocationPathField;

	public Text getDocLocationPathField() {
		return docLocationPathField;
	}

	public void setDocLocationPathField(Text docLocationPathField) {
		this.docLocationPathField = docLocationPathField;
	}

	private Button browseDocButton;

	private ProjectWebRatio wrProject = null;

	public PlwebCreationWizardPage(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite plate = new Composite(parent, SWT.NONE);
		plate.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout();
		layout.marginWidth = 5;
		plate.setLayout(layout);
		setControl(plate);
		plate.setFont(parent.getFont());

		Label labelEnterProjectTitle = new Label(plate, SWT.NONE);
		labelEnterProjectTitle
				.setText(Messages.PlwebCreationWizard_DiagramModelFilePageDescription);
		labelEnterProjectTitle.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_BEGINNING));

		Composite compositeForProject = new Composite(plate, SWT.NULL);
		GridLayout lineLayout = new GridLayout();
		lineLayout.numColumns = 4;
		compositeForProject.setLayout(lineLayout);
		compositeForProject
				.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label projectLocationLabel = new Label(compositeForProject, SWT.NONE);
		projectLocationLabel.setText("Location:");

		// project location entry field
		projectLocationPathField = new Text(compositeForProject, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 250;
		data.horizontalSpan = 2;
		projectLocationPathField.setLayoutData(data);

		browseProjectButton = new Button(compositeForProject, SWT.PUSH);
		browseProjectButton.setText("Browse");
		browseProjectButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				handleLocationBrowseButtonPressed(projectLocationPathField,
						"Select WebRatio project directory.");
			}
		});

		projectLocationPathField.setText("");
		projectLocationPathField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
			}
		});

		Label labelEnterDocumentationTitle = new Label(plate, SWT.NONE);
		labelEnterDocumentationTitle
				.setText("Select file that contains documentation core.");
		labelEnterDocumentationTitle.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_BEGINNING));

		Composite compositeForDocumentation = new Composite(plate, SWT.NULL);
		GridLayout lineLayout1 = new GridLayout();
		lineLayout1.numColumns = 4;
		compositeForDocumentation.setLayout(lineLayout1);
		compositeForDocumentation.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));

		Label documentationLocationLabel = new Label(compositeForDocumentation,
				SWT.NONE);
		documentationLocationLabel.setText("Location:");

		// project location entry field
		docLocationPathField = new Text(compositeForDocumentation, SWT.BORDER);
		GridData data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.widthHint = 250;
		data1.horizontalSpan = 2;
		docLocationPathField.setLayoutData(data1);

		browseDocButton = new Button(compositeForDocumentation, SWT.PUSH);
		browseDocButton.setText("Browse");
		browseDocButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				handleLocationBrowseButtonPressed(docLocationPathField,
						"Select DocLine project directory.");
			}
		});

		docLocationPathField.setText("");
		//		docLocationPathField.addModifyListener(new ModifyListener() {
		//			public void modifyText(ModifyEvent e) {
		////				setPageComplete(validatePage());
		//			}
		//		});

		setPageComplete(validatePage());
		setErrorMessage(null);
		setMessage(null);
		setControl(plate);
		Dialog.applyDialogFont(compositeForProject);
	}

	// Not to generated
	/**
	 * Open an appropriate directory browser
	 * 
	 */
	private void handleLocationBrowseButtonPressed(Text locationPathField,
			String message) {
		String selectedDirectory = null;
		String dirName = getPathFromLocationField(locationPathField);

		if (dirName.equals("")) {
			String value = getDialogSettings().get(SAVED_LOCATION_ATTR);
			if (value != null) {
				dirName = value;
			}
		}

		DirectoryDialog dialog = new DirectoryDialog(
				locationPathField.getShell(), SWT.SHEET);
		dialog.setMessage(message);
		dialog.setFilterPath(dirName);
		selectedDirectory = dialog.open();

		if (selectedDirectory != null) {
			updateLocationField(selectedDirectory, locationPathField);
			getDialogSettings().put(SAVED_LOCATION_ATTR, selectedDirectory);
		}
	}

	//not gerenated
	/**
	 * Return the path on the location field.
	 * 
	 * @return String
	 */
	protected String getPathFromLocationField(Text locationPathField) {
		URI fieldURI;
		try {
			fieldURI = new URI(locationPathField.getText());
		} catch (URISyntaxException e) {
			return locationPathField.getText();
		}
		return fieldURI.getPath();
	}

	public String getPathFromProjectLocationField() {
		URI fieldURI;
		try {
			fieldURI = new URI(projectLocationPathField.getText());
		} catch (URISyntaxException e) {
			return projectLocationPathField.getText();
		}
		return fieldURI.getPath();
	}

	public String getPathFromDocLocationField() {
		URI fieldURI;
		try {
			fieldURI = new URI(docLocationPathField.getText());
		} catch (URISyntaxException e) {
			return docLocationPathField.getText();
		}
		return fieldURI.getPath();
	}

	//not gerenated
	/**
	 * Set the path in the location field.
	 * 
	 * @param String
	 *            selectedPath
	 */
	private void updateLocationField(String selectedPath, Text locationPathField) {
		locationPathField.setText(TextProcessor.process(selectedPath));
	}

	/**
	 * Get plug-in dialog settings
	 * 
	 */
	protected IDialogSettings getDialogSettings() {
		IDialogSettings ideDialogSettings = PlwebDiagramEditorPlugin
				.getInstance().getDialogSettings();
		IDialogSettings result = ideDialogSettings.getSection(getClass()
				.getName());
		if (result == null) {
			result = ideDialogSettings.addNewSection(getClass().getName());
		}
		return result;
	}

	/**
	 * Validates page and sets error message
	 * 
	 * @return boolean - validation result
	 */
	protected boolean validatePage() {
		try {
			wrProject = new ProjectWebRatio(
					getPathFromLocationField(projectLocationPathField));
			if (!wrProject.isValid()) {
				setErrorMessage("Selected folder doen't contain valid WebRatio project");
				wrProject = null;
				return false;
			} else {
				setErrorMessage(null);
				return true;
			}
		} catch (Exception e) {
			setErrorMessage("Selected folder doen't contain valid WebRatio project");
			wrProject = null;
			return false;
		}
	}

	/**
	 * Get name of the selected WebRatio project
	 * 
	 * @return String - name of the project
	 */
	public String getProjectName() {
		if (wrProject == null || !wrProject.isValid()) {
			return "";
		}
		return wrProject.getProjectName();
	}
}
