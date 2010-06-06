package org.eclipse.xslt.conversion.actions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class SelectDirDialog extends Dialog {

	protected boolean okBtnClicked = false;
	protected Shell shlSelectDirectories;
	private Text text_1;
	private Text text_2;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SelectDirDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public boolean open() {
		createContents();
		shlSelectDirectories.open();
		shlSelectDirectories.layout();
		Display display = getParent().getDisplay();
		while (!shlSelectDirectories.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return okBtnClicked;
	}

	/**
	 * Create contents of the dialog.
	 */
	@SuppressWarnings("deprecation")
	private void createContents() {
		shlSelectDirectories = new Shell(getParent(), getStyle());
		shlSelectDirectories.setSize(561, 291);
		shlSelectDirectories.setText("Select directories");
		
		centerDialog(shlSelectDirectories);
		
		Label lblSelectWebratioProject = new Label(shlSelectDirectories, SWT.NONE);
		lblSelectWebratioProject.setBounds(10, 10, 256, 15);
		lblSelectWebratioProject.setText("Select WebMLDoc project directory");
				
		final Combo combo = new Combo(shlSelectDirectories, SWT.READ_ONLY);
		// add handler on combo value changed
		combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				try {
					File dirSettingsFile = new File(combo.getText().concat("/settings.dir"));
					String webRatioDir;
					String docLineDir;
					if (dirSettingsFile.exists()) {
						BufferedReader input =  new BufferedReader(new FileReader(dirSettingsFile));
				    	webRatioDir = input.readLine();
				    	if (webRatioDir == null)
				    		webRatioDir = "";
				    	docLineDir = input.readLine();
				    	if (docLineDir == null)
				    		docLineDir = "";
				        input.close();
					} else {
			    		webRatioDir = "";
			    		docLineDir = "";				
					}
					text_1.setText(webRatioDir);
					text_2.setText(docLineDir);
				} catch (Exception e) {
				}
			}
		});
		combo.setBounds(10, 31, 535, 23);
		for (File i : getDirsInWorkspace())
			combo.add(i.getAbsolutePath());
		
		// set initial value for WebMLDoc project directory
		try {
			URL url = Platform.getBundle("org.eclipse.gmf.webml.diagram").getEntry("/");
			url = Platform.asLocalURL(url);
			File projectDirFile = new File(url.getPath().concat("settings.dir"));
			if (projectDirFile.exists()) {
				BufferedReader input =  new BufferedReader(new FileReader(projectDirFile));
		    	String projectPath = input.readLine();
		    	if ((projectPath != null) && (new File(projectPath)).exists()){
		    		combo.setText(projectPath);
		        }
		    	else {
		    		combo.select(0);
		    	}
		        input.close();
			} else
				combo.select(0);
		} catch (IOException e2) {
		}
		
		Label lblSelectDoclinedocumentationDirectory = new Label(shlSelectDirectories, SWT.NONE);
		lblSelectDoclinedocumentationDirectory.setText("Select WebRatio project directory");
		lblSelectDoclinedocumentationDirectory.setBounds(10, 81, 256, 15);
		
		Button btnSelectWR = new Button(shlSelectDirectories, SWT.NONE);
		btnSelectWR.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text_1.setText(getDirectory("Select WebRatio project directory", text_1.getText()));
			}
		});
		btnSelectWR.setText("...");
		btnSelectWR.setBounds(517, 100, 28, 25);
		
		text_1 = new Text(shlSelectDirectories, SWT.BORDER);
		text_1.setBounds(10, 102, 501, 21);
		
		Label lblSelectWebmldocProject = new Label(shlSelectDirectories, SWT.NONE);
		lblSelectWebmldocProject.setText("Select DocLine documentation directory");
		lblSelectWebmldocProject.setBounds(10, 152, 256, 15);
		
		Button btnSelectDL = new Button(shlSelectDirectories, SWT.NONE);
		btnSelectDL.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text_2.setText(getDirectory("Select DocLine documentation directory", text_2.getText()));
			}
		});
		btnSelectDL.setText("...");
		btnSelectDL.setBounds(517, 171, 28, 25);
		
		// set initial values of text_1 and text_2
		text_2 = new Text(shlSelectDirectories, SWT.BORDER);
		text_2.setBounds(10, 173, 501, 21);
		try {
			File dirSettingsFile = new File(combo.getText().concat("/settings.dir"));
			String webRatioDir;
			String docLineDir;
			if (dirSettingsFile.exists()) {
				BufferedReader input =  new BufferedReader(new FileReader(dirSettingsFile));
		    	webRatioDir = input.readLine();
		    	if (webRatioDir == null)
		    		webRatioDir = "";
		    	docLineDir = input.readLine();
		    	if (docLineDir == null)
		    		docLineDir = "";
		        input.close();
			} else {
	    		webRatioDir = "";
	    		docLineDir = "";
			}
			text_1.setText(webRatioDir);
			text_2.setText(docLineDir);
		} catch (Exception e) {
		}
		
		Button btnCancel = new Button(shlSelectDirectories, SWT.NONE);
		// close dialog if Cancel button clicked
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlSelectDirectories.dispose();
			}
		});
		btnCancel.setBounds(470, 228, 75, 25);
		btnCancel.setText("Cancel");
		
		Button btnSave = new Button(shlSelectDirectories, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if ((!combo.getText().equals("")) && (!text_1.getText().equals("")) &&
							(!text_2.getText().equals(""))) {
						// save DocLine and WebRatio directories
						StringBuilder contents = new StringBuilder();
						contents.append(text_1.getText());
		        		contents.append(System.getProperty("line.separator"));
						contents.append(text_2.getText());
		        		contents.append(System.getProperty("line.separator"));
						BufferedWriter out;
						out = new BufferedWriter(new FileWriter(combo.getText()+"/settings.dir"));
			        	out.write(contents.toString());
			        	out.close();
			        	
			        	// Save current project directory
			        	URL url = Platform.getBundle("org.eclipse.gmf.webml.diagram").getEntry("/");
						url = Platform.asLocalURL(url);
						File dirSettingsFile = new File(url.getPath().concat("settings.dir"));
						out = new BufferedWriter(new FileWriter(dirSettingsFile.getAbsolutePath()));
				        out.write(combo.getText());
				        out.close();	
			        	
				        // set okBtnClicked  to true
				        okBtnClicked = true;
				        
			        	// close dialog
						shlSelectDirectories.dispose();
					} else
						MessageDialog.openInformation( 
								null,
								"Setting directories error",      
								"You have to set all directories first!");
				} catch (IOException e1) {
				}
			}
		});
		btnSave.setBounds(375, 228, 75, 25);
		btnSave.setText("Save");

	}
	
	// opens directory select dialog with specified title and filter
	private String getDirectory(String title, String filter) {
		DirectoryDialog dd = new DirectoryDialog(new Shell(), SWT.OPEN);
	    dd.setText(title);
	    dd.setFilterPath(filter);
	    return dd.open();
	}
	
	// returns list of directories in workspace
	private List<File> getDirsInWorkspace () {

		//create filter to read only project directories
		FileFilter isDir = new FileFilter() {
		    public boolean accept(File file) {
		    	return (file.isDirectory()&&(!file.getName().endsWith(".metadata")));
		    }
		};
		
		// get workspace
		String workspacePath = Platform.getLocation().toOSString();
		File workspaceDir = new File(workspacePath);
		
		// get list of directories in Workspace
		File[] dirs = workspaceDir.listFiles(isDir);
		
		return Arrays.asList(dirs);
	}
	
	// align dialog to the center of parent window
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
