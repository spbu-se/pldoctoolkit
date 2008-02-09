package org.spbu.pldoctoolkit.dialogs;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.spbu.pldoctoolkit.registry.RegisteredLocation;

public class FinalInfProductSelectionDialog extends Dialog {
	private List<RegisteredLocation> registeredLocations;
	private org.eclipse.swt.widgets.List list;
	private int selectedIndex = -1;
	
	public FinalInfProductSelectionDialog(Shell parentShell) {
		super(parentShell);
		setBlockOnOpen(true);
	}

	public List<RegisteredLocation> getList() {
		return registeredLocations;
	}

	public void setList(List<RegisteredLocation> list) {
		this.registeredLocations = list;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout(1, false));
		list = new org.eclipse.swt.widgets.List(composite, SWT.SINGLE | SWT.BORDER);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        list.setLayoutData(data);
        for (RegisteredLocation loc: registeredLocations)
        	list.add(loc.getId());
        list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				okPressed();
			}
        });
        list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedIndex = list.getSelectionIndex();
			}
        });
        return composite;
	}
	
	public int getSelectionIndex() {
		return selectedIndex;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Point size = new Point(300, 200);
		newShell.setSize(size);
		Shell p = getParentShell();
		newShell.setLocation(p.getLocation().x + p.getSize().x / 2 - size.x / 2, p.getLocation().y + p.getSize().y / 2 - size.y / 2);
		newShell.setText("Choose FinalInfProduct to export...");
	}
}
