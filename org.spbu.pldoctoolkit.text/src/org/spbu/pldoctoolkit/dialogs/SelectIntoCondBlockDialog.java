package org.spbu.pldoctoolkit.dialogs;

import java.util.HashMap;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.spbu.pldoctoolkit.actions.SelectIntoCondBlockAction;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.ProjectContent;

public class SelectIntoCondBlockDialog extends Dialog {
	private HashMap<String, Boolean> setNewValue;
	
	private ProjectContent projectContent;
	private String variable = "";
	private String value = "";


	private Text variableText;
	private Text valueText;


	private Label variableLabel;
	private Label valueLabel;

	public SelectIntoCondBlockDialog(Shell parentShell, ProjectContent projectContent) {
		super(parentShell);
		setBlockOnOpen(true);
		this.projectContent = projectContent;
		setNewValue = new HashMap<String, Boolean>();
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 7;
		composite.setLayout(layout);

		Label description = new Label(composite, SWT.LEFT);
		description.setText("Select text into conditional block");
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 3;
		description.setLayoutData(gd);

		// 1///////////////////////////////////////////////////////////

		variableLabel = new Label(composite, SWT.LEFT);
		variableLabel.setText("Name of new variable:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		variableLabel.setLayoutData(gd);

		variableText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		variableText.setLayoutData(gd);

		new Label(composite, SWT.NONE);

		// 2////////////////////////////////////////////////////////////////////////////////////

		valueLabel = new Label(composite, SWT.LEFT);
		valueLabel.setText("Value of new variable:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		valueLabel.setLayoutData(gd);

		valueText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		valueText.setLayoutData(gd);

		return composite;
	}

	public int open() {
		int res = super.open();

		return res;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Point size = new Point(350, 150);
		newShell.setSize(size);
		Shell p = getParentShell();
		newShell.setLocation(
				p.getLocation().x + p.getSize().x / 2 - size.x / 2, p
						.getLocation().y
						+ p.getSize().y / 2 - size.y / 2);

		newShell.setText(SelectIntoCondBlockAction.refactName);
	}

	protected void okPressed() {
		variable = variableText.getText();
		value =valueText.getText();
		validate();
		super.okPressed();
	}

	
	private void validate() {
		for(LangElem finalInfProd : projectContent.finalInfPrs) {
			if(projectContent.containsVariable(finalInfProd, variable)) {
				String fipName = finalInfProd.attrs.getValue(LangElem.ID);
				VariableExistsDialog dialog = new VariableExistsDialog(this.getParentShell(),fipName,variable);
				
				int res = dialog.open();
				if ( res == Window.OK)
					setNewValue.put(fipName, true);
				else
					setNewValue.put(fipName, false);
			}
		}
	}

	public String getVariable() {
		return variable;
	}

	public String getValue() {
		return value;
	}

	public HashMap<String, Boolean> getSetNewValueMap() {
		return setNewValue;
	}
	
}
