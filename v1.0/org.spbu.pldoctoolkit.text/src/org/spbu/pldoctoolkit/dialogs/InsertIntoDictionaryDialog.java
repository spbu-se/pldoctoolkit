package org.spbu.pldoctoolkit.dialogs;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
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
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		//composite.setLayout(new GridLayout(1, false));
		idLabel = new Label(composite, SWT.LEFT);
		idLabel.setText("Id of new Enrty");
		idText = new Text(composite, SWT.SINGLE);
		
		KeyListener listener = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {	
				setTexts();
			}
		};
		idText.addKeyListener(listener);
		
		errorMessage = new Label(composite, SWT.LEFT);
		errorMessage.setText("                                                                         ");
		//errorMessage.setSize(300, 40);
		errorMessage.setForeground(new Color(Display.getCurrent(), new RGB(255, 0, 0)));
		
		dictLabel = new Label(composite, SWT.LEFT);
		dictLabel.setText("Select dictionary to insert into");
		dictionarysList = new Combo(composite, SWT.READ_ONLY);		
		updateDictsList();
		dictionarysList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				//super.widgetSelected(e);
				entrys = dicts.get(dictionarysList.getSelectionIndex()).getChilds();
				setTexts();
			}			
		});
		
		newDictButton = new Button(composite, SWT.LEFT);
		newDictButton.setText("Create new Ditionary");
		newDictButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				//super.widgetSelected(e);
				newDictButtonPressed();
			}			
		});
		
		generateIdButton = new Button(composite, SWT.LEFT);
		generateIdButton.setText("Generate Id");
		generateIdButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				//super.widgetSelected(e);
				generateIdButtonPressed();
			}			
		});
		
        return composite;
	}
	
	private void setTexts() {
		//String name = nameText.getText();
		String id = idText.getText();
		isValidEntryId = true;
		for (Element entry : entrys) {
			if (id.equals(((LangElem)entry).attrs.getValue("id"))) {
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
