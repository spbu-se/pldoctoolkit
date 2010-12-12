package plweb.diagram.part;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.WizardPage;
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

import plweb.diagram.util.projects.ProjectWebRatio;

public class PlwebCreationWizardPage extends WizardPage {
	
	private static final String SAVED_LOCATION_ATTR = "OUTSIDE_LOCATION"; //$NON-NLS-1$
	
	private Text locationPathField;
	private Button browseButton;
	
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
		
		Label labelEnterTitle = new Label(plate, SWT.NONE);
		labelEnterTitle.setText(Messages.PlwebCreationWizard_DiagramModelFilePageDescription);
		labelEnterTitle.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		
		Composite composite = new Composite(plate, SWT.NULL);
		GridLayout lineLayout = new GridLayout();
		lineLayout.numColumns = 4;
		composite.setLayout(lineLayout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label locationLabel = new Label(composite, SWT.NONE);
		locationLabel.setText("Location:");
		
		// project location entry field
		locationPathField = new Text(composite, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 250;
		data.horizontalSpan = 2;
		locationPathField.setLayoutData(data);
		
		browseButton = new Button(composite, SWT.PUSH);
		browseButton.setText("Browse");
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				handleLocationBrowseButtonPressed();
			}
		});
		
		locationPathField.setText("");
		locationPathField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
			}
		});
		
		setPageComplete(validatePage());
        setErrorMessage(null);
        setMessage(null);
        setControl(plate);
        Dialog.applyDialogFont(composite);
	}
	
	/**
	 * Open an appropriate directory browser
	 * 
	 */
	private void handleLocationBrowseButtonPressed() {
		String selectedDirectory = null;
		String dirName = getPathFromLocationField();

		if (dirName.equals("")) {
			String value = getDialogSettings().get(SAVED_LOCATION_ATTR);
			if (value != null) {
				dirName = value;
			}
		}

		DirectoryDialog dialog = new DirectoryDialog(locationPathField.getShell(), SWT.SHEET);
		dialog.setMessage("Select WebRatio project directory.");
		dialog.setFilterPath(dirName);
		selectedDirectory = dialog.open();

		if (selectedDirectory != null) {
			updateLocationField(selectedDirectory);
			getDialogSettings().put(SAVED_LOCATION_ATTR, selectedDirectory);
		}
	}
	
	/**
	 * Return the path on the location field.
	 * 
	 * @return String
	 */
	public String getPathFromLocationField() {
		URI fieldURI;
		try {
			fieldURI = new URI(locationPathField.getText());
		} catch (URISyntaxException e) {
			return locationPathField.getText();
		}
		return fieldURI.getPath();
	}
	
	/**
	 * Set the path in the location field.
	 * 
	 * @param String selectedPath
	 */
	private void updateLocationField(String selectedPath) {
		locationPathField.setText(TextProcessor.process(selectedPath));
	}
	
	/**
	 * Get plug-in dialog settings
	 * 
	 */
	protected IDialogSettings getDialogSettings() {
		IDialogSettings ideDialogSettings = PlwebDiagramEditorPlugin
				.getInstance().getDialogSettings();
		IDialogSettings result = ideDialogSettings.getSection(getClass().getName());
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
			wrProject = new ProjectWebRatio(getPathFromLocationField());
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
