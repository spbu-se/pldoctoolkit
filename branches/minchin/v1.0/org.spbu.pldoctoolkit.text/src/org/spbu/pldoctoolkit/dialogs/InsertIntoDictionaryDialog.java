package org.spbu.pldoctoolkit.dialogs;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class InsertIntoDictionaryDialog extends Dialog{
	private String entryId = "";
	private String dictId = "";
	private int selectionIdx;
	
	private Text idText;
	
	private Label idLabel;
	private Label dictLabel;
		
	private Combo dictionarysList;
	private ArrayList<String> dictIds = new ArrayList<String>();
	
	public InsertIntoDictionaryDialog(Shell parentShell) {
		super(parentShell);
		setBlockOnOpen(true);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		//composite.setLayout(new GridLayout(1, false));
		idLabel = new Label(composite, SWT.LEFT);
		idLabel.setText("Id of new Enrty");
		idText = new Text(composite, SWT.SINGLE);
				
		dictLabel = new Label(composite, SWT.LEFT);
		dictLabel.setText("Select dictionary to insert into");
		dictionarysList = new Combo(composite, SWT.READ_ONLY);
		for (int i = 0; i<dictIds.size(); ++i)
			dictionarysList.add(dictIds.get(i));
		
        return composite;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Point size = new Point(400, 400);
		newShell.setSize(size);
		Shell p = getParentShell();
		newShell.setLocation(p.getLocation().x + p.getSize().x / 2 - size.x / 2, p.getLocation().y + p.getSize().y / 2 - size.y / 2);
		
		newShell.setText("Insert into dictionary");		
	}	

	protected void okPressed()
	{
		entryId = idText.getText();
		selectionIdx = dictionarysList.getSelectionIndex();
		if (selectionIdx == -1)
			return;
		
		dictId = dictionarysList.getItem(selectionIdx);
		
		super.okPressed();
	}
	
	public String getEntryId()
	{
		return entryId;
	}	
	
	public int getSelectionIdx()
	{
		return selectionIdx;
	}
	
	public void add(String id) {
		dictIds.add(id);
	}
}
