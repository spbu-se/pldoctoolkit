package org.spbu.pldoctoolkit.dialogs;


import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.spbu.pldoctoolkit.actions.SelectIntoCondBlockAction;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.refactor.PositionInDRL;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.SelectIntoInfElem;

public class SplitInfElemDialog extends Dialog {

	private SelectIntoInfElem firstInfElem, secondInfElem;
	private Label xLabel;
	private Text xText;
	private Label yLabel;
	private Text yText;

	private Label errorMessageSplit;
	
	private int line, column;
	private PositionInText firstSonStartPos, lastSonEndPos;
	
	public SplitInfElemDialog(Shell parentShell, SelectIntoInfElem firstInfElem, SelectIntoInfElem secondInfElem, PositionInText startPos,  DRLDocument doc) {
		super(parentShell);
		setBlockOnOpen(true);
		this.firstInfElem = firstInfElem;
		this.secondInfElem = secondInfElem;

		
		PositionInDRL from = doc.findByPosition(startPos);
		Element targetElem = from.next;
		firstSonStartPos = targetElem.getChilds().get(0).getStartPos();
		lastSonEndPos = targetElem.getChilds().get(targetElem.getChilds().size()-1).getEndPos();
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 7;
		composite.setLayout(layout);

		Label description = new Label(composite, SWT.LEFT);
		description.setText("Split infElement on two parts");
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 3;
		description.setLayoutData(gd);

		// 1///////////////////////////////////////////////////////////

		xLabel = new Label(composite, SWT.LEFT);
		xLabel.setText("split line:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		xLabel.setLayoutData(gd);

		xText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		xText.setText("0");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		xText.setLayoutData(gd);
		xText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		
		new Label(composite, SWT.NONE);

		// 2////////////////////////////////////////////////////////////////////////////////////

		yLabel = new Label(composite, SWT.LEFT);
		yLabel.setText("column:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		yLabel.setLayoutData(gd);

		yText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		yText.setText("0");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		yText.setLayoutData(gd);
		yText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		
		new Label(composite, SWT.NONE);
        
		/////////////////////////////////////////////////////////////////
		
		errorMessageSplit = new Label(composite, SWT.LEFT);
		errorMessageSplit
				.setText("                                                   ");
		errorMessageSplit.setForeground(new Color(Display.getCurrent(),
				new RGB(255, 0, 0)));
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		errorMessageSplit.setLayoutData(gd);
		
		return composite;
	}

	public int open() {
		int res = super.open();

		return res;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Point size = new Point(350, 180);
		newShell.setSize(size);
		Shell p = getParentShell();
		newShell.setLocation(
				p.getLocation().x + p.getSize().x / 2 - size.x / 2, p
						.getLocation().y
						+ p.getSize().y / 2 - size.y / 2);

		newShell.setText(SelectIntoCondBlockAction.refactName);
	}

	private void validate() {
		try {
			line = Integer.decode(xText.getText());
			column = Integer.decode(yText.getText());
			
			firstInfElem.setValidationPararams(firstInfElem.project, firstInfElem.doc, firstSonStartPos, new PositionInText(line, column-1));
			secondInfElem.setValidationPararams(secondInfElem.project, secondInfElem.doc,  new PositionInText(line, column), lastSonEndPos);
			firstInfElem.reset();
			firstInfElem.reset();
			boolean isValidSplit = firstInfElem.validate() && secondInfElem.validate();
			if(isValidSplit) {
				errorMessageSplit.setText("");
			}
			else {
				errorMessageSplit.setText("New infElems is not valid");
			}
			getButton(IDialogConstants.OK_ID).setEnabled(isValidSplit);
		}
		catch(Exception e) {
			errorMessageSplit.setText("Bad coordinates");
			getButton(IDialogConstants.OK_ID).setEnabled(false);
		}
	}
	
	protected void okPressed() {
		line = Integer.decode(xText.getText());
		column = Integer.decode(yText.getText());
		super.okPressed();
	}
	
	public PositionInText getSplitPosition() {
		return new PositionInText(line, column);
	}
	
	
}
