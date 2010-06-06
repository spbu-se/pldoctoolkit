package org.eclipse.xslt.conversion.changelog.treeviewer.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.xslt.conversion.changelog.model.InterfaceChange;
import org.eclipse.xslt.conversion.changelog.model.DocChange;

import com.thoughtworks.xstream.XStream;

//class for showing dialog window with changes log
public class ChangeLogDialog extends Dialog {

	protected Object result;
	protected Shell shlChangeLog;
	protected InterfaceChange root;
	protected String logPath;
	protected Tree tree;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ChangeLogDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}
	
	/**
	 * Add information about changes in hypertext model and documentation to Dialog 
	 */
	public void addSource(String path) {
		logPath = path;
		String xml = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(logPath));
		    String line  = null;
		    StringBuilder stringBuilder = new StringBuilder();
		    String ls = System.getProperty("line.separator");
		    while((line = reader.readLine()) != null ) {
		        stringBuilder.append( line );
		        stringBuilder.append( ls );
		    }
		    xml = stringBuilder.toString();
		} catch (Exception e) {}
		
		// Deserialize xml
		XStream xstream = new XStream(); // require XPP3 library
	    xstream.alias("InterfaceChange",  InterfaceChange.class);
	    xstream.alias("DocChange",  DocChange.class);
	    if (!xml.equals(""))
	    	root = (InterfaceChange) xstream.fromXML(xml);
	    else
	    	root = null;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlChangeLog.open();
		shlChangeLog.layout();
		return result;
	}

	/**
	 * Returns dialog's shell.
	 */
	public Shell getShell() {
		return shlChangeLog;
	}

	/**
	 * Updates contents of the TreeViewer.
	 */
	public void updateTtee() {
	    // set images
	    Image htchange = ImageDescriptor.createFromURL(getClass().getResource("images/htchange.ico")).createImage();
	    Image docchange = ImageDescriptor.createFromURL(getClass().getResource("images/docchange.ico")).createImage();
	    	    
	    // clear old tree
	    tree.removeAll();
	    // add information from root (log tree) to tree component
		if (root != null) for (int i = 0; i<root.size(); i++) {
	    	InterfaceChange htItem = root.getInterfaceChanges().get(i);
	    	TreeItem item = new TreeItem(tree, SWT.NONE, 0);
		    try {
				item.setImage(htchange);
		    } catch (Exception e) {}
		    item.setChecked(htItem.getChecked());
		    item.setGrayed(false);
		    item.setText(new String[] {(htItem.getIndex() + 1) + ". " + htItem.getName(), htItem.getId()});
		    for (int j = 0; j < htItem.getDocChanges().size(); j++) {
		    	DocChange docItem = htItem.getDocChanges().get(j);
		    	TreeItem subItem = new TreeItem(item, SWT.NONE);
			    try {
						subItem.setImage(docchange);
				} catch (Exception e) {}
		        subItem.setChecked(docItem.getChecked());
		        if (docItem.getChecked())
		        	subItem.getParentItem().setChecked(true);
		        else
		        	subItem.getParentItem().setGrayed(true);
		        subItem.setText(new String[] {(docItem.getIndex() + 1) + ". Check documentation" +
		        		" section with ID attribute '" + docItem.getName() + "'", docItem.getName()});
		    }
	    }
	}
	
	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlChangeLog = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shlChangeLog.setSize(800, 300);
		shlChangeLog.setText("Change Log");
		shlChangeLog.setLayout(new FormLayout());
		
		centerDialog(shlChangeLog);
			
		tree = new Tree(shlChangeLog, SWT.CHECK | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		FormData fd_tree = new FormData();
		fd_tree.top = new FormAttachment(0, 10);
		fd_tree.left = new FormAttachment(100, -784);
		fd_tree.bottom = new FormAttachment(0, 231);
		fd_tree.right = new FormAttachment(100, -10);
		tree.setLayoutData(fd_tree);
	    tree.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event event) {
	            if (event.detail == SWT.CHECK) {
	                TreeItem item = (TreeItem) event.item;
	                boolean checked = item.getChecked();
	                checkItems(item, checked);
	                checkPath(item.getParentItem(), checked, false);
	            }
	        }
	    });
	    tree.setLinesVisible(true);
	    tree.setHeaderVisible(true);
	    TreeColumn column1 = new TreeColumn(tree, SWT.LEFT);
	    column1.setText("Hypertext model changes and respective documentation changes");
	    column1.setWidth(593);
	    TreeColumn column2 = new TreeColumn(tree, SWT.LEFT);
	    column2.setText("ID");
	    column2.setWidth(177);
	    
	    // set images
	    Image htchange = ImageDescriptor.createFromURL(getClass().getResource("images/htchange.ico")).createImage();
	    Image docchange = ImageDescriptor.createFromURL(getClass().getResource("images/docchange.ico")).createImage();
	    	    
	    // add information from root (log tree) to tree component
	    if (root != null) for (int i = 0; i<root.size(); i++) {
	    	InterfaceChange htItem = root.getInterfaceChanges().get(i);
	    	TreeItem item = new TreeItem(tree, SWT.NONE, 0);
		    try {
				item.setImage(htchange);
		    } catch (Exception e) {}
		    item.setChecked(htItem.getChecked());
		    item.setGrayed(false);
		    item.setText(new String[] {(htItem.getIndex() + 1) + ". " + htItem.getName(), htItem.getId()});
		    for (int j = 0; j < htItem.getDocChanges().size(); j++) {
		    	DocChange docItem = htItem.getDocChanges().get(j);
		    	TreeItem subItem = new TreeItem(item, SWT.NONE);
			    try {
						subItem.setImage(docchange);
				} catch (Exception e) {}
		        subItem.setChecked(docItem.getChecked());
		        if (docItem.getChecked())
		        	subItem.getParentItem().setChecked(true);
		        else
		        	subItem.getParentItem().setGrayed(true);
		        subItem.setText(new String[] {(docItem.getIndex() + 1) + ". Check documentation" +
		        		" section with ID attribute '" + docItem.getName() + "'", docItem.getName()});
		    }
	    }
	    
	    Button btnClearLog = new Button(shlChangeLog, SWT.NONE);
	    btnClearLog.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		MessageBox messageBox = new MessageBox(shlChangeLog, SWT.CENTER | SWT.ICON_WARNING | SWT.YES | SWT.NO);
	    	        messageBox.setMessage("This will completely erase all information in log. Do you want to proceed?");
	    	        messageBox.setText("Clear change log");
	    	        int response = messageBox.open();
	    	        if (response == SWT.YES) {
	    	        	//erase log
	    	        	tree.removeAll();
	    	        	root = null;
	    	        	XStream xstream = new XStream(); // require XPP3 library
	    	    	    xstream.alias("InterfaceChange",  InterfaceChange.class);
	    	    	    xstream.alias("DocChange",  DocChange.class);
	    	    	    String xml = xstream.toXML(root);
	    			    
	    			    // write xml back to log
						try {
		    	            BufferedWriter out;
							out = new BufferedWriter(new FileWriter(logPath));
		    		        out.write(xml);
		    		        out.close();
						} catch (Exception e1) {}
	    	        }
	    	}
	    });
	    FormData fd_btnClearLog = new FormData();
	    fd_btnClearLog.top = new FormAttachment(0, 237);
	    fd_btnClearLog.left = new FormAttachment(0, 10);
	    btnClearLog.setLayoutData(fd_btnClearLog);
	    btnClearLog.setText("Clear Log");
	    
		Button btnCancel = new Button(shlChangeLog, SWT.NONE);
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.right = new FormAttachment(0, 784);
		fd_btnCancel.top = new FormAttachment(0, 237);
		fd_btnCancel.left = new FormAttachment(0, 709);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlChangeLog.dispose();
			}
		});
		btnCancel.setText("Cancel");
		shlChangeLog.setDefaultButton(btnCancel);
		
		Button btnOk = new Button(shlChangeLog, SWT.NONE);
		FormData fd_btnOk = new FormData();
		fd_btnOk.right = new FormAttachment(0, 695);
		fd_btnOk.top = new FormAttachment(0, 237);
		fd_btnOk.left = new FormAttachment(0, 620);
		btnOk.setLayoutData(fd_btnOk);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// save changes in tree to changes.log
				for (int i = tree.getItemCount() - 1; i >= 0; i--) {
					TreeItem htItem = tree.getItem(i);
						root.getInterfaceChanges().get(tree.getItemCount() - 1 - i).setChecked(htItem.getChecked());
					for (int j = 0; j < htItem.getItemCount(); j++) {
						root.getInterfaceChanges().get(tree.getItemCount() - 1 - i).getDocChanges().get(j).setChecked(htItem.getItem(j).getChecked());
					}
				}
				XStream xstream = new XStream(); // require XPP3 library
	    	    xstream.alias("InterfaceChange",  InterfaceChange.class);
	    	    xstream.alias("DocChange",  DocChange.class);
	    	    String xml = xstream.toXML(root);

			    // write xml back to log
				try {
    	            BufferedWriter out;
					out = new BufferedWriter(new FileWriter(logPath));
    		        out.write(xml);
    		        out.close();
				} catch (Exception e1) {}
				shlChangeLog.dispose();
			}
		});
		btnOk.setText("OK");
	}
	
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

	private static void checkPath(TreeItem item, boolean checked, boolean grayed) {
	    if (item == null) return;
	    if (grayed) {
	        checked = true;
	    } else {
	        int index = 0;
	        TreeItem[] items = item.getItems();
	        while (index < items.length) {
	            TreeItem child = items[index];
	            if (child.getGrayed() || checked != child.getChecked()) {
	                checked = grayed = true;
	                break;
	            }
	            index++;
	        }
	    }
	    item.setChecked(checked);
	    item.setGrayed(grayed);
	    checkPath(item.getParentItem(), checked, grayed);
	}

	private static void checkItems(TreeItem item, boolean checked) {
	    item.setGrayed(false);
	    item.setChecked(checked);
	    TreeItem[] items = item.getItems();
	    for (int i = 0; i < items.length; i++) {
	        checkItems(items[i], checked);
	    }
	}
}
