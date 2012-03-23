package org.spbu.pldoctoolkit.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.HandleDIffWithAnotherInfElem;
import org.spbu.pldoctoolkit.refactor.PositionInDRL;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.refactor.SelectIntoInfElem;

public class HandleDIffWithAnotherInfElemDialog extends Dialog {
	
	/*private SelectIntoInfElem firstInfElem, secondInfElem;
	private Label xLabel;
	private Text xText;
	private Label yLabel;
	private Text yText;

	private Label errorMessageSplit;
	
	private int line, column;
	private PositionInText firstSonStartPos, lastSonEndPos;*/
	
	private ProjectContent projectContent;
	
	private List<DRLDocument> documents;
	private List<LangElem> infElements;
	
	private Label fileNamesLabel;
	private Label elemsLabel;
	private Label errorMessageFile;
	private Label errorMessageElem;
	
	private Combo fileNamesCombo;
	private Combo elemsCombo;

	public HandleDIffWithAnotherInfElemDialog(Shell parentShell, ProjectContent projectContent) {
		super(parentShell);
		setBlockOnOpen(true);
		this.projectContent = projectContent;
		/*this.firstInfElem = firstInfElem;
		this.secondInfElem = secondInfElem;
		
		PositionInDRL from = doc.findByPosition(startPos);
		Element targetElem = from.next;
		firstSonStartPos = targetElem.getChilds().get(0).getStartPos();
		lastSonEndPos = targetElem.getChilds().get(targetElem.getChilds().size()-1).getEndPos();*/
		documents = HandleDIffWithAnotherInfElem.getPossibleDocs(projectContent);
		infElements = new ArrayList<LangElem>();
	}
	
	protected Control createContents(Composite parent) {		
		Control res = super.createContents(parent);
		
		elemsCombo.setEnabled(false);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
		
		return res;
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		composite.setLayout(layout);
		
////////////////////////////////////////////////////////////////////////////////////
		
		Label description = new Label(composite, SWT.LEFT);
		description.setText("Choose inf element to compare via Diff\n\n");
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 3;
		description.setLayoutData(gd);
		
////////////////////////////////////////////////////////////////////////////////////
		
		fileNamesLabel = new Label(composite, SWT.LEFT);		
		fileNamesLabel.setText("Select file which contains inf element\n\n");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
		fileNamesLabel.setLayoutData(gd);
		
		fileNamesCombo = new Combo(composite, SWT.READ_ONLY | SWT.FILL);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
		fileNamesCombo.setLayoutData(gd);
		
		fileNamesCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {				
				fileSelected();
			}			
		});
		
		for (DRLDocument document : documents) {
			fileNamesCombo.add(document.file.getName());
		}
		
		errorMessageFile = new Label(composite, SWT.NONE);
		errorMessageFile.setText("Choose file                 ");
		errorMessageFile.setForeground(new Color(Display.getCurrent(), new RGB(255, 0, 0)));
			
////////////////////////////////////////////////////////////////////////////////////
		
		elemsLabel = new Label(composite, SWT.LEFT);
		elemsLabel.setText("Select inf element");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
		elemsLabel.setLayoutData(gd);
		
		elemsCombo = new Combo(composite, SWT.READ_ONLY | SWT.FILL);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		elemsCombo.setLayoutData(gd);
		
		elemsCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {				
				infElemSelected();
			}			
		});
		
		/*for (int i = 0; i<fileNames.size(); ++i)
			fileNamesCombo.add(fileNames.get(i));*/		
		
		errorMessageElem = new Label(composite, SWT.NONE);
		errorMessageElem.setText("                                 ");
		errorMessageElem.setForeground(new Color(Display.getCurrent(), new RGB(255, 0, 0)));
		
        return composite;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Point size = new Point(600,230);
		newShell.setSize(size);
		Shell p = getParentShell();
		newShell.setLocation(p.getLocation().x + p.getSize().x / 2 - size.x / 2, p.getLocation().y + p.getSize().y / 2 - size.y / 2);
		
		newShell.setText("Choose second inf element...");		
	}
	
	private void fileSelected() {
		DRLDocument document = documents.get(fileNamesCombo.getSelectionIndex());
		List<LangElem> allInfElements = LangElem.getElemList(projectContent, LangElem.INFELEMENT);
		for (LangElem infElem : allInfElements) {
			if (infElem.getDRLDocument().equals(document)) {
				infElements.add(infElem);
				elemsCombo.add(infElem.attrs.getValue("name") + " ( id: " + infElem.attrs.getValue("id") +")");
			}
		}
		elemsCombo.setEnabled(true);
		//getButton(IDialogConstants.OK_ID).setEnabled(true);
		errorMessageFile.setText("");
		errorMessageElem.setText("Choose inf elem            ");
	}
	
	private void infElemSelected() {
		LangElem infElem = infElements.get(elemsCombo.getSelectionIndex());
		System.out.println(infElem.getTextRepresentation());
		getButton(IDialogConstants.OK_ID).setEnabled(true);
		errorMessageElem.setText("");
	}
	
}
