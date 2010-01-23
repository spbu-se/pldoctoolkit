package org.spbu.pldoctoolkit.dialogs;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.CreateNewDictionary;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;

public class InsertIntoDictionaryDialog extends Dialog{
	private String entryId = "";
	private String dictId = "";
	private int selectionIdx;
	
	private Text idText;
	
	private Label idLabel;
	private Label dictLabel;
		
	private Combo dictionarysList;
	
	//private ArrayList<String> dictIds = new ArrayList<String>();
	private ArrayList<LangElem> dicts;// = new ArrayList<LangElem>();
	private ArrayList<Element> entrys = new ArrayList<Element>();
	
	private Button newDictButton;
	private Button generateIdButton;
	
	private Label errorMessage;
	private boolean isValidEntryId = false;
	
	ProjectContent projectContent;
	
	public InsertIntoDictionaryDialog(Shell parentShell, ProjectContent projectContent) {
		super(parentShell);
		
		this.projectContent = projectContent;
		dicts = projectContent.dictionarys;
		
		setBlockOnOpen(true);
	}
	
	protected Control createContents(Composite parent) {		
		Control res = super.createContents(parent);
		
		idText.setEnabled(false);
		generateIdButton.setEnabled(false);
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
		description.setText("Insert selected text into dictionary, and replace selected text with new DictRef\n\n");
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 3;
		description.setLayoutData(gd);
		
////////////////////////////////////////////////////////////////////////////////////
		
		idLabel = new Label(composite, SWT.LEFT);
		idLabel.setText("Id of new Enrty:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
		idLabel.setLayoutData(gd);
		
		idText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
		idText.setLayoutData(gd);
		
		KeyListener listener = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {	
				setTexts();
			}
		};
		idText.addKeyListener(listener);
		
		generateIdButton = new Button(composite, SWT.CENTER);
		generateIdButton.setText("Generate Id");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;		
		generateIdButton.setLayoutData(gd);
		generateIdButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				//super.widgetSelected(e);
				generateIdButtonPressed();
			}			
		});
		
////////////////////////////////////////////////////////////////////////////////		
		
		dictLabel = new Label(composite, SWT.LEFT);
		dictLabel.setText("Dictionary to insert into:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 3;
		dictLabel.setLayoutData(gd);
		
/////////////////////////////////////////////////////////////////////////////////
		
		dictionarysList = new Combo(composite, SWT.READ_ONLY | SWT.V_SCROLL | SWT.BORDER /*| SWT.SIMPLE*/);		
		
		//ListViewer lw = new ListViewer(composite, SWT.READ_ONLY | SWT.V_SCROLL | SWT.BORDER);
		//dictionarysList = lw.getList();
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 2;
		//dictionarysList.setBounds(50, 100, 50, 50);
		dictionarysList.setLayoutData(gd);
		
		updateDictsList();
		dictionarysList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				//super.widgetSelected(e);
				entrys = dicts.get(dictionarysList.getSelectionIndex()).getChilds();
				idText.setEnabled(true);
				generateIdButton.setEnabled(true);
				setTexts();
			}			
		});
		
		newDictButton = new Button(composite, SWT.CENTER);
		newDictButton.setText("Create new Ditionary");
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.TOP;
		gd.grabExcessHorizontalSpace = true;		
		newDictButton.setLayoutData(gd);
		
		newDictButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				//super.widgetSelected(e);
				newDictButtonPressed();
			}			
		});
		
//////////////////////////////////////////////////////////////////////////////////////		
		
		
		errorMessage = new Label(composite, SWT.CENTER);
		errorMessage.setText("Select dictionary                                ");
		errorMessage.setForeground(new Color(Display.getCurrent(), new RGB(255, 0, 0)));
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.CENTER;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 3;
		errorMessage.setLayoutData(gd);
		
///////////////////////////////////////////////////////////////////////////////////		
		
		
        return composite;
	}
	
	private void setTexts() {		
		//String name = nameText.getText();
		String id = idText.getText();
		isValidEntryId = true;
		for (Element entry : entrys) {			
			if (entry instanceof LangElem && id.equals(((LangElem)entry).attrs.getValue("id"))) {
				isValidEntryId = false;
				break;
			}
		}
				
		if (!isValidEntryId) {
			errorMessage.setText("Bad dict id");
			getButton(IDialogConstants.OK_ID).setEnabled(false);			
			return;
		}
		else {
			errorMessage.setText("");
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
		
////////////////////////////////////////////////////////////////////		
		
	}
	
	private void updateDictsList() {
		dictionarysList.removeAll();
		for (int i = 0; i<dicts.size(); ++i)
			dictionarysList.add(dicts.get(i).attrs.getValue("name") + " ( id: " + dicts.get(i).attrs.getValue("id") +")");
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Point size = new Point(400, 300);
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
	
	private void generateIdButtonPressed() {
		int size = entrys.size();
		int i = size;
		String id = "Error";
		boolean goodId = false;
		while (!goodId) {
			goodId = true;
			id = "entry_id" + String.valueOf(i);
			for (Element entry : entrys) {
				if (id.equals(((LangElem)entry).attrs.getValue("id"))) {
					goodId = false;
					break;
				}
			}
			++i;
		}
		
		idText.setText(id);
		setTexts();
	}
	
	private void newDictButtonPressed() {		
		CreateNewDictDialog dialog = new CreateNewDictDialog(getShell());
	
		ArrayList<DRLDocument> docs = CreateNewDictionary.getPossipleDocs(projectContent);
		for (DRLDocument doc : docs) {
			dialog.addFileName(doc.file.getName());
			//dialog.s
		}
		
		for (LangElem dict : dicts) {
			dialog.addDictId(dict.attrs.getValue("id"));
		}
		
		int res = dialog.open(); 
		if (res == Dialog.OK) {			
			LangElem newDict = CreateNewDictionary.perform(docs.get(dialog.getSelectionIdx()), dialog.getDictName(), dialog.getDictId());
			projectContent.saveAll();
			
			updateDictsList();
			//dicts.add(newDict);
			//dictionarysList.add(newDict.attrs.getValue("name") + " ( id: " + newDict.attrs.getValue("id") +")");
		}
	}
	
	public String getEntryId() {
		return entryId;
	}	
	
	public int getSelectionIdx() {
		return selectionIdx;
	}
/*	
	public void setDicts(ArrayList<LangElem> dicts) {
		this.dicts = dicts;
		
	}
*/
/*	
	private void add(String id) {
		dictIds.add(id);
	}
	*/
}
