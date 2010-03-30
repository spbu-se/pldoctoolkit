package org.eclipse.xslt.conversion.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

import net.sf.saxon.s9api.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;

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
// WebRatio plugin class
public class ConversionAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	
	public ConversionAction() {
	}

	private static final String WR_PATH =
	    "C:\\Program Files\\WebRatio\\WebRatio 5.1.1";
	
	private static final String OPEN_EDITOR =
	    "org.eclipse.xslt.conversion.openeditor";

	private static final String CHECK_DOC =
	    "org.eclipse.xslt.conversion.check"; 

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action){
		//getWebRatioDiff();
		String id = action.getId();
		String projectPath = getWebRatioProjectDirectory();
		String webModelPath = projectPath.concat("\\Model\\WebModel");
		String docModelPath = projectPath.concat("\\Model\\DocModel");
		if (id.equals(OPEN_EDITOR)){
			backupAllModels(docModelPath);
			if (!genAllModels(webModelPath, docModelPath)) return;
			copyTopics(docModelPath);
	    }else if (id.equals(CHECK_DOC)){
	        System.out.println("Check Documentation command selected");
	    }else {
			backupAllModels(docModelPath);
			if (!genAllModels(webModelPath, docModelPath)) return;
        }
	}
	
	//on every opening of GMF editor new diagram id generated.
	// To keep all topics we firstly have to backup all webml files to .old files
	// Input: docModelPath - path of directory with files to backup
	private void backupAllModels(String docModelPath) {
		// create filter to read only generated Webml files
		FilenameFilter webmlFile = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".webml");
		    }
		};
		
		//docModelDir
		File docModelDir = new File(docModelPath);
		
		//copy files to backup .old files
		try {
			File[] curfile = docModelDir.listFiles(webmlFile);
			for (int i=0; i<curfile.length; i++) {
				BufferedReader from =  new BufferedReader(new FileReader(curfile[i]));
				String outputPath =  docModelPath + "\\" + curfile[i].getName().replace(".webml", ".old");
				BufferedWriter to = new BufferedWriter(new FileWriter(outputPath));
				String line = null; // next string read from input file
				boolean notFirst = false;  
				while (( line = from.readLine()) != null){
					if (notFirst)
						to.write("\n");
					else
						notFirst = true;
					to.write(line);
				}
				from.close();
				to.close();
			}
		} catch (Exception err) {
			err.printStackTrace();
    	}
	}

	// copies <topic> elements from old webml files to new ones in docModelPath directory.
	// topic is connection with DRL InfElement
	private void copyTopics(String docModelPath) {
		// create filter to read only generated Webml files
		FilenameFilter webmlFile = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".webml");
		    }
		};
		
		//docModelDir
		File docModelDir = new File(docModelPath);
		
		//copy topics from .old files to current webml file
		try {
			File[] curfile = docModelDir.listFiles(webmlFile);
			for (int i=0; i<curfile.length; i++) {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document outputDom = db.parse(curfile[i]);
								
				String oldFilePath = curfile[i].getAbsolutePath().replace(".webml", ".old");
				File oldFile = new File(oldFilePath);
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser parser = factory.newSAXParser();

				// override default handler
				CopyTopicsHandler handler = new CopyTopicsHandler();
				parser.parse(oldFile, handler);

				// dom - DOM model that consists only of <topic> elements
				Document dom =  handler.getDom();
				NodeList topics =  dom.getElementsByTagName("topic");
				for (int j = 0; j < topics.getLength(); j++) {
					  Element curTopic = (Element) topics.item(j);
					  String parentId = curTopic.getAttribute("parent");
					  NodeList elems =  outputDom.getElementsByTagName("element");
					  for (int k = 0; k < elems.getLength(); k++) {
						  Element curElem = (Element) elems.item(k);
						  curElem.setIdAttribute("Id", true);
					  }
					  Element newTopic = outputDom.createElement("topic");
					  newTopic.setAttribute("name", curTopic.getAttribute("name"));
					  // check getbyid
					  Element parent = outputDom.getElementById(parentId);
					  parent.appendChild(newTopic);
				}
				
				
				//print
				// set up a transformer
	            TransformerFactory transfac = TransformerFactory.newInstance();
	            Transformer trans = transfac.newTransformer();
	            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	            trans.setOutputProperty(OutputKeys.INDENT, "yes");

	            // create string from xml tree
	            StringWriter sw = new StringWriter();
	            StreamResult result = new StreamResult(sw);
	            DOMSource source = new DOMSource(outputDom);
	            trans.transform(source, result);
	            String xmlString = sw.toString();

	            // print xmlString to file
	            BufferedWriter out = new BufferedWriter(new FileWriter(curfile[i]));
		        out.write(xmlString);
		        out.close();
			}
		} catch (FileNotFoundException err) {
			return;
    	} catch (Exception err) {
			err.printStackTrace();
    	}
	}

	// generates gmf editor diagram code for all siteviews 
	private boolean genAllModels(String webModelPath, String docModelPath) {
		File webModelDir = new File(webModelPath);
		
		// create filter to read only Siteview directories
		FileFilter isSvDir = new FileFilter() {
		    public boolean accept(File file) {
		    	return (file.isDirectory())&&(file.getName().startsWith("sv"));
		    }
		};
		
		// get list of all Siteview directories in WebModel folder
		File[] svDir = webModelDir.listFiles(isSvDir);
		//check if there are any Siteview directories
		if (svDir == null) {
			MessageDialog.openInformation( 
					null,
					"Project directory error",      
					"Selected project directory is not correct! No WebModel found"); 
			return false;
		} else {
			//create DocModel directory
			(new File(docModelPath)).mkdir();
			// for each siteview generate its models
			for (int i=0; i<svDir.length; i++) {
				genSiteviewModel(svDir[i].getPath(), docModelPath);
			}
			return true;
		}
	}

	// generates gmf editor diagram code for selected siteview
	private void genSiteviewModel(String siteviewPath, String docModelPath) {
		// firstly, assemble siteview to one xml file
		String svPath = assembleSiteview(siteviewPath, docModelPath);
		// exit if there was error 
		if (svPath.equals(""))
			return;
		// secondly, convert assembled siteview to gmf editor model
		String svGMFModel = convertSiteview(svPath);
		// exit if there was error
		if (svGMFModel.equals(""))
			return;
		// thirdly replace xsi_type with xsi:type in output file
		updateXsiType(svGMFModel);
		// finally convert source and target parameters of links to GMF format
		updateLinks(svGMFModel);
	}

	// xslt transformation generates links in unproper format
	// this procedure convert links to proper GMF editoe format
	private void updateLinks(String svGMFModel) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();

			// override default handler
			SAXHandler handler = new SAXHandler();
			parser.parse(new File(svGMFModel), handler);

			// SAXParser parses svGMFModel and returns dom with correct links
			Document dom =  handler.getDom();
			
			//print
			// set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            // create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(dom);
            trans.transform(source, result);
            String xmlString = sw.toString();

            // print xmlString to file
            BufferedWriter out = new BufferedWriter(new FileWriter(svGMFModel));
	        out.write(xmlString);
	        out.close();
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

	// xslt transformation temporary generates xsi_type instead of xsi:type
	// to make it all work this procedure replaces xsi_type with xsi:type 
	private void updateXsiType(String svGMFModel) {
		try {
			StringBuilder contents = new StringBuilder();
	    	BufferedReader input =  new BufferedReader(new FileReader(new File(svGMFModel)));
	    	String line = null; // next string read from input file
	        while (( line = input.readLine()) != null){
	        	//if (!line.trim().equals("")) {
	        		contents.append(line.replace("xsi_type", "xsi:type"));
	        		contents.append(System.getProperty("line.separator"));
	        	//}
	        }
	        input.close();
	        BufferedWriter out = new BufferedWriter(new FileWriter(svGMFModel));
	        out.write(contents.toString());
	        out.close();
	    } catch (IOException err) {
	    	MessageDialog.openInformation( 
					null,
					"XSLT Transformation error",      
					"Cannot generate xsi:type attribute. xsi_type will be generated instead.");
			return;
	    }
	}

	// makes xslt transformation to GMF editor diagram model from siteview file
	private String convertSiteview(String svPath) {
		// open input file
		File svInput = new File(svPath);
		// generate output path
		String outPath = svInput.getParent() + "\\" + svInput.getName().substring(0, svInput.getName().length()-3) + "webml";
		try {
			Processor proc = new Processor(false);
			XsltCompiler comp = proc.newXsltCompiler();
			// change path to xsl file later
			XsltExecutable exp = comp.compile(new StreamSource(new File(WR_PATH.concat("\\workspace\\webml.xsl"))));
			XdmNode source = proc.newDocumentBuilder().build(new StreamSource(new File(svPath)));
			Serializer out = new Serializer();
			out.setOutputProperty(Serializer.Property.METHOD, "xml");
			out.setOutputProperty(Serializer.Property.INDENT, "yes");
			out.setOutputFile(new File(outPath));
			XsltTransformer trans = exp.load();
			trans.setInitialContextNode(source);
			trans.setDestination(out);
			trans.transform();
			return outPath;
		} catch (SaxonApiException err) {
			MessageDialog.openInformation( 
					null,
					"XSLT Transformation error",      
					"Cannot transformate " + svInput.getName() + " file");
			return "";
		}
	}

	//assembles webratio siteview from many file to one xml document
	//and returns its path
	//Input: path - path of directory with Siteview
	//  	 docModelPath - path of directory to put assembled file
	private String assembleSiteview(String path, String docModelPath) {
		File svDir = new File(path);
		
		// create filter to read only WebRatio files
		FilenameFilter wrFile = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".wr")&&(!name.equals("Properties.wr"));
		    }
		};
		
		//create filter to read only area subdirectories
		FileFilter isAreaDir = new FileFilter() {
		    public boolean accept(File file) {
		    	return (file.isDirectory())&&(file.getName().startsWith("area"));
		    }
		};
		
		try {
			// check if there are Properties.wr file in Siteview directory
			File sPropWr = new File(path.concat("\\Properties.wr"));
			// create StringBuilder for output data
			StringBuilder contents = new StringBuilder();
			
			// reading Properties.wr file and writing it to generic webratio output file
			BufferedReader sPropInput =  new BufferedReader(new FileReader(sPropWr));
		    try {
		    	String line = null; // next string read from input file
		        while (( line = sPropInput.readLine()) != null){
		        	// before writing </Siteview> tag we need to include in output
		        	// all other page files and area subdirectories
		        	if (line.equals("</SiteView>")) {
		        		
		        		// create array of page files in directory
	    				File[] page = svDir.listFiles(wrFile);
	    				
	    				// include all page files in output
	    				if (page != null) { 
	    					// for every page file append all its strings to output
	    				    for (int i=0; i<page.length; i++) {
	    				    	BufferedReader pageInput =  new BufferedReader(new FileReader(page[i]));
	    				    	while (( line = pageInput.readLine()) != null){
	    				    		contents.append(line);
	    			        		contents.append(System.getProperty("line.separator"));
	    				    	}
	    				    	pageInput.close();
	    				    }
	    				}
	    				
	    				// create array of area subdirectories in current siteview folder
	    				File[] area = svDir.listFiles(isAreaDir);
	    				
	    				if (area != null){
	    					// for every area folder append all its pages to output
	    				    for (int i=0; i<area.length; i++) {
	    				    	// check if there are Properties.wr file in area subdirectory
	    				    	File aPropWr = new File(area[i].getAbsolutePath().concat("\\Properties.wr"));
	    				    	// reading Properties.wr file and writing it to generic webratio output file
	    						BufferedReader aPropInput =  new BufferedReader(new FileReader(aPropWr));
	    						while (( line = aPropInput.readLine()) != null){
	    							// before writing </Area> tag we need to include in output
	    					    	// all other page files in this area directory
	    					        if (line.equals("</Area>")) {
	    					        		
	    					        	// create array of page files in directory
	    				    			File[] aPage = area[i].listFiles(wrFile);
	    				    				
	    				    			// include all page files in output
	    				    			if (aPage != null) { 
	    				    				// for every page file append all its strings to output
	    				    				for (int j=0; j<aPage.length; j++) {
	    				    				    BufferedReader pageInput =  new BufferedReader(new FileReader(aPage[j]));
	    				    				    while (( line = pageInput.readLine()) != null){
	    				    				    	contents.append(line);
	    				    			        	contents.append(System.getProperty("line.separator"));
	    				    				    }
	    				    				    pageInput.close();
	    				    				}
	    				    			}
			    				    	contents.append("</Area>");
			    			        	contents.append(System.getProperty("line.separator"));
	    					        } else {
	    					        	contents.append(line);
	    					        	contents.append(System.getProperty("line.separator"));
	    					        }
	    						}
	    						aPropInput.close();
	    				    }
	    				}
	    				contents.append("</SiteView>");
		        	} else {
		        		contents.append(line);
		        		contents.append(System.getProperty("line.separator"));
		        	}
		        }
		    }
		    finally {
		        sPropInput.close();
		    }
		    // write contents to output file
		    String output = docModelPath + "\\siteview" + svDir.getName().substring(2) + ".xml";
		    BufferedWriter out = new BufferedWriter(new FileWriter(output));
	        out.write(contents.toString());
	        out.close();
	        return output;
	        //System.out.println(output);
    	} catch (FileNotFoundException err) {
    		MessageDialog.openInformation( 
					null,
					"Siteview error",      
					"Siteview" + svDir.getName().substring(2) + " was corrupted. Proper diagram cannot be created");
			return "";
    	} catch (IOException err) {
			err.printStackTrace();
			return "";
    	}
	}

	// asks user for directory where webratio project is 
	private String getWebRatioProjectDirectory() {
		DirectoryDialog dd = new DirectoryDialog(new Shell(), SWT.OPEN);
	    dd.setText("Select WebRatio Project Directory");
	    dd.setFilterPath(WR_PATH.concat("\\workspace\\"));
	    return dd.open();
	}
	
	public static void getWebRatioDiff(){
	/*    try {
	    	// test code, will be removed
	        System.out.println("test1");
		    String oldFile = WR_PATH.concat("\\workspace\\Acme\\Model\\DocModel\\siteview1.old");
		    String newFile = WR_PATH.concat("\\workspace\\Acme\\Model\\DocModel\\siteview1.webml");;
	        // Create a factory
	        System.out.println("test2");
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        // Use the factory to create a builder
	        System.out.println("test3");
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        System.out.println("test4");
	        org.w3c.dom.Document oldwr = builder.parse(oldFile);
	        org.w3c.dom.Document newwr = builder.parse(newFile);
	        //org.w3c.dom.Document changes = difference(newwr, oldwr);
	        System.out.println("test5");
	        NodeList list = newwr.getElementsByTagName("*");
	         System.out.println("XML Elements: ");
	         for (int i=0; i<list.getLength(); i++) {
	           // Get element
	           Element element = (Element)list.item(i);
	           System.out.println(element.getNodeName());
	         }
	    }
	    catch (Exception e) {
	        System.out.println("diff exception");
	    	return;
	    }*/
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