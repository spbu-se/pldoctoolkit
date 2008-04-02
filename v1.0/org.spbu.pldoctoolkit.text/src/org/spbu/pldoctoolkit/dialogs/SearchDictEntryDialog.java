package org.spbu.pldoctoolkit.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class SearchDictEntryDialog extends Dialog{	
	private Button next;
	private Button replace;
	
	public SearchDictEntryDialog(Shell parentShell) {
		super(parentShell);
		setBlockOnOpen(true);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		//composite.setLayout(new GridLayout(1, false));
		next = new Button(composite, SWT.LEFT);
		next.setText("next");
		
		replace = new Button(composite, SWT.LEFT);
		replace.setText("replace");
		
        return composite;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Point size = new Point(300, 200);
		newShell.setSize(size);
		Shell p = getParentShell();
		newShell.setLocation(p.getLocation().x + p.getSize().x / 2 - size.x / 2, p.getLocation().y + p.getSize().y / 2 - size.y / 2);
		
		newShell.setText("Enter id and name of new InfElem...");		
	}	

	protected void okPressed()
	{		
		super.okPressed();
	}	
	
	public void setEnabled(boolean enabled) {
		next.setEnabled(enabled);
		replace.setEnabled(enabled);
	}
}