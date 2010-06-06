package webml.diagram.inserttopic;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

// Dialog window class for selecting InfElement to insert into diagram
public class InsertTopicDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private String selectedTopic = "";

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public InsertTopicDialog(Shell parent, int style) {
		super(parent, style);
		setText("Select DocTopic to insert");
	}

	/**
	 * Open the dialog.
	 * @return which  button was clicked
	 */
	public String open(ArrayList<String> content) {
		// create contents of dialog window
		createContents(content);
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		// return infElement selected in Dialog
		return selectedTopic;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents(ArrayList<String> content) {
		// create ne whell for dialog window
		shell = new Shell(getParent(), SWT.APPLICATION_MODAL);
		shell.setSize(367, 348);
		shell.setText(getText());
		
		centerDialog(shell);

		// create List component inside dialog window
		final List list = new List(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				selectedTopic = list.getSelection()[0];
				shell.dispose();
			}
		});
		
		// fill list with documentation infElements
		for (int i=0; i<content.size(); i++)
			list.add(content.get(i));
		
		// set default selection
		list.setSelection(0);
		
		// set position of List component
		list.setBounds(10, 31, 341, 270);
		
		// create Label for the List component
		Label lblSelectDocumentationTopic = new Label(shell, SWT.NONE);
		lblSelectDocumentationTopic.setBounds(10, 10, 173, 15);
		lblSelectDocumentationTopic.setText("Select Documentation Topic");

		// create Cancel button
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedTopic = "";
				shell.dispose();
			}
		});
		btnCancel.setBounds(276, 307, 75, 25);
		btnCancel.setText("Cancel");
		
		// Create OK button
		Button btnOk = new Button(shell, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedTopic = list.getSelection()[0];
				shell.dispose();
			}
		});
		btnOk.setBounds(183, 307, 75, 25);
		btnOk.setText("OK");
		
	}
	
	// center Dialog with infElement selection
	private void centerDialog(Shell dlg) {
		// coordinates
		int x, y;
		Point parentSize = dlg.getParent().getSize();
		Point dlgSize = dlg.getSize();
		Point parentLeft = dlg.getParent().getLocation();
		
		if (parentSize.x > dlgSize.x) 
		    x = ((parentSize.x - dlgSize.x)/2) + parentLeft.x;
		else 
		    x = parentLeft.x;

		if (parentSize.y > dlgSize.y) 
		    y = ((parentSize.y - dlgSize.y)/2) + parentLeft.y;
		else 
		    y = parentLeft.y;
		
		dlg.setLocation(x, y);
		
	}
}
