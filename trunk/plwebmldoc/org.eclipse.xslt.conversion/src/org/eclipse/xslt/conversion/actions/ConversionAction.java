package org.eclipse.xslt.conversion.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;

import net.sf.saxon.s9api.*;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import javax.swing.*;

import com.ercato.core.xml.XOperator;
import javax.xml.parsers.*;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class ConversionAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	
	public ConversionAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action){
		 
		//Create a directory chooser
		final JFileChooser fc = new JFileChooser();		 
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogTitle("Choose WebModel directory");
		fc.setCurrentDirectory(new File("C:\\Program Files\\WebRatio\\WebRatio 5.1.1\\"));
		//In response to a button click:
		int returnVal = fc.showOpenDialog(new JFrame());
		if (returnVal != JFileChooser.APPROVE_OPTION)
		{
			System.out.println("File chooser cancel button clicked");
			return;
		}
		else {
			try {
				// create "WebDocModel" directory where WebModel directory is
				File file = fc.getSelectedFile();
				String parentPath = file.getParent();
				String createPath = parentPath.concat("\\DocModel");
				String webModelPath = file.getAbsolutePath();
				boolean success = (new File(createPath)).mkdir();
			    if (success) {
			      System.out.println("Directory: " + createPath + " created");
			    };
			    assembleWebModel(webModelPath.concat("\\sv1\\"));
				Processor proc = new Processor(false);
            	XsltCompiler comp = proc.newXsltCompiler();
            	XsltExecutable exp = comp.compile(new StreamSource(new File(parentPath.concat("\\webml.xsl"))));
            	XdmNode source = proc.newDocumentBuilder().build(new StreamSource(new File(webModelPath.concat("\\sv1\\siteview1.xml"))));
            	Serializer out = new Serializer();
            	out.setOutputProperty(Serializer.Property.METHOD, "xml");
            	out.setOutputProperty(Serializer.Property.INDENT, "yes");
            	out.setOutputFile(new File(createPath.concat("\\temp.webml")));
            	XsltTransformer trans = exp.load();
            	trans.setInitialContextNode(source);
            	trans.setDestination(out);
            	trans.transform();
            	File fromFile = new File(createPath.concat("\\temp.webml"));
            	FileInputStream fis = null;
            	BufferedInputStream bis = null;
            	DataInputStream dis = null;

            	// start modifying xsi_type to xsi:type
            	fis = new FileInputStream(fromFile);

            	// Here BufferedInputStream is added for fast reading.
           	 	bis = new BufferedInputStream(fis);
            	dis = new DataInputStream(bis);
            	FileWriter fstream = new FileWriter(createPath.concat("\\siteview1.webml"));
            	BufferedWriter outfile = new BufferedWriter(fstream);
            	// dis.available() returns 0 if the file does not have more lines.
            	while (dis.available() != 0) {            	  	
            	// this statement reads strings from temporary file, modify xsi_type
            	// to xsi:type and write it to output file
            		outfile.write(dis.readLine().replace("xsi_type", "xsi:type") + "\n");
            	}
             	// dispose all the resources after using them.
            	fis.close();
            	bis.close();
            	dis.close();
          		//Close the output stream
            	outfile.close();
            	// delete temporary file
            	fromFile.delete();
			} catch (SaxonApiException err) {
				handleException(err);
			} catch (FileNotFoundException err) {
            	err.printStackTrace();
			} catch (IOException err) {
				err.printStackTrace();
        	}
        }
	}
	
	private static void handleException(Exception ex) {
		MessageDialog.openInformation( 
				null,
				"Conversion Plug-in",      
				"Exception!"); 
    }
	
	public static void getWebRatioDiff(){
	    try {
	    	// test code, will be removed
		    String oldFile = "C:\\Program Files\\WebRatio\\WebRatio 5.1.1\\workspace\\Acme\\Model\\DocModel\\siteview1.old";
		    String newFile = "C:\\Program Files\\WebRatio\\WebRatio 5.1.1\\workspace\\Acme\\Model\\DocModel\\siteview1.xml";
	        // Create a factory
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        // Use the factory to create a builder
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        org.w3c.dom.Document oldwr = builder.parse(oldFile);
	        org.w3c.dom.Document newwr = builder.parse(newFile);
	        //org.w3c.dom.Document changes = difference(newwr, oldwr);
	    }
	    catch (Exception e) {
	    	return;
	    }
	}

	// assembles WebRatio WebModel in one file
	public static void assembleWebModel(String dirPath){
		File dir = new File(dirPath);
		// create filter to read only WebRatio files
		FilenameFilter wrFile = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".wr")&&(!name.equals("Properties.wr"));
		    }
		};
		FileFilter isDir = new FileFilter() {
		    public boolean accept(File file) {
		    	return file.isDirectory();
		    }
		};
		// check if there are Properties.wr file in Siteview directory
		File sPropWr = new File(dirPath.concat("\\Properties.wr"));
		if (sPropWr == null) {
			System.out.println("No Properties.wr file!");
			return;
		}
		try {
			FileInputStream fis = null;
    		BufferedInputStream bis = null;
    		DataInputStream dis = null;

    		fis = new FileInputStream(sPropWr);
    		// Here BufferedInputStream is added for fast reading.
   	 		bis = new BufferedInputStream(fis);
   	 		dis = new DataInputStream(bis);
    		FileWriter fstream = new FileWriter(dirPath.concat("\\siteview1.xml"));
    		BufferedWriter outfile = new BufferedWriter(fstream);
    		// dis.available() returns 0 if the file does not have more lines.
    		while (dis.available() != 0) {            	
    			String t = dis.readLine();
    			if (t.equals("</SiteView>")) {
    				// create array of files in directory
    				File[] children = dir.listFiles(wrFile);
    				// create array of subdirectories in directory
    				File[] subdir = dir.listFiles(isDir);
    				// include all WebRatio files in this directory
    				if (children != null) { 
    				    for (int i=0; i<children.length; i++) {
    				        FileInputStream curfis = null;
    			    		BufferedInputStream curbis = null;
    			    		DataInputStream curdis = null;
    			    		curfis = new FileInputStream(children[i]);
    			   	 		curbis = new BufferedInputStream(curfis);
    			   	 		curdis = new DataInputStream(curbis);
    			    		// dis.available() returns 0 if the file does not have more lines.
    			    		while (curdis.available() != 0) {            	
    			    			String t1 = curdis.readLine();
    			    			outfile.write(t1 + "\n");
    			    		}
    			    		curfis.close();
    			    		curbis.close();
    			    		curdis.close();
    				    }
    				}
    				// include all WebRatio subdirectories in this directory
    				if (subdir != null) { 
    				    for (int i=0; i<subdir.length; i++) {
    				    	// firstly write area's Properties.wr
    				    	File aPropWr = new File(subdir[i].getAbsolutePath().concat("\\Properties.wr"));
    						if (aPropWr == null) {
    							System.out.println("No Properties.wr file!");
    							return;
    						}
    				        FileInputStream dirfis = null;
    			    		BufferedInputStream dirbis = null;
    			    		DataInputStream dirdis = null;

    			    		dirfis = new FileInputStream(aPropWr);
    			    		// Here BufferedInputStream is added for fast reading.
    			   	 		dirbis = new BufferedInputStream(dirfis);
    			   	 		dirdis = new DataInputStream(dirbis);
    			    		// dis.available() returns 0 if the file does not have more lines.
    			    		while (dirdis.available() != 0) {            	
    			    			String tArea = dirdis.readLine();
    			    			if (tArea.equals("</Area>")){
    			    				// create array of files in every subdirectory
    			    				File[] arpage = subdir[i].listFiles(wrFile);
    			    				if (arpage != null) { 
    			    				    for (int j=0; j<arpage.length; j++) {
    			    				        FileInputStream curfis = null;
    			    			    		BufferedInputStream curbis = null;
    			    			    		DataInputStream curdis = null;
    			    			    		curfis = new FileInputStream(arpage[j]);
    			    			   	 		curbis = new BufferedInputStream(curfis);
    			    			   	 		curdis = new DataInputStream(curbis);
    			    			    		// dis.available() returns 0 if the file does not have more lines.
    			    			    		while (curdis.available() != 0) {            	
    			    			    			String t1 = curdis.readLine();
    			    			    			outfile.write(t1 + "\n");
    			    			    		}
    			    			    		curfis.close();
    			    			    		curbis.close();
    			    			    		curdis.close();
    			    				    }
    			    				}
    			    			}
    			    			outfile.write(tArea + "\n");
    			    		}
    			    		dirfis.close();
    			    		dirbis.close();
    			    		dirdis.close();
    				    }
    				}
    			}
    			outfile.write(t + "\n");
    		}
     		// dispose all the resources after using them.
    		fis.close();
    		bis.close();
    		dis.close();
    		outfile.close();
    	} catch (FileNotFoundException err) {
        	err.printStackTrace();
		} catch (IOException err) {
			err.printStackTrace();
    	}
	}
	
	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}