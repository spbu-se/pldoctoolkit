package org.spbu.pldoctoolkit.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.spbu.pldoctoolkit.actions.SelectIntoCondBlockAction;


public class VariableExistsDialog extends Dialog {
	String finalInfProd;
	String variable;

	public VariableExistsDialog(Shell parentShell, String finalInfProd,	String variable) {
		super(parentShell);
		setBlockOnOpen(true);
		this.finalInfProd = finalInfProd;
		this.variable = variable;
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.verticalSpacing = 1;
		composite.setLayout(layout);

		String descriptionString = "Variable "+variable+" exists in "+finalInfProd;
		descriptionString += "\n" +"Set new value to this variable?";
		
		Label description = new Label(composite, SWT.LEFT);
		description.setText(descriptionString);
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 3;
		description.setLayoutData(gd);

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
		super.okPressed();
	}
	
}
