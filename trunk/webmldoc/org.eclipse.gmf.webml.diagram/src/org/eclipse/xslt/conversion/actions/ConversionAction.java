package org.eclipse.xslt.conversion.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.util.ObjectAdapter;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateViewAndOptionallyElementCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramCommandStack;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xslt.conversion.changelog.model.DocChange;
import org.eclipse.xslt.conversion.changelog.model.InterfaceChange;
import org.eclipse.xslt.conversion.changelog.treeviewer.ui.ChangeLogDialog;
import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import net.sf.saxon.s9api.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.*;
import javax.xml.parsers.DocumentBuilder;
import com.a7soft.examxml.ExamXML;
import com.thoughtworks.xstream.XStream;

import org.w3c.dom.*;

import webml.DocTopic;
import webml.WebmlPackage;
import webml.diagram.inserttopic.InsertTopicDialog;
import webml.diagram.providers.WebmlElementTypes;


/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
// this class handles actions that could be executed from plugin menu or diagram popup menu
public class ConversionAction implements IWorkbenchWindowActionDelegate, IObjectActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	
	public ConversionAction() {
	}

	private static final String UPDATE_MODEL =
	    "org.eclipse.xslt.conversion.openeditor";
	
	private static final String ARRANGE_ALL =
	    "org.eclipse.xslt.conversion.arrangeall";
		
	private static final String SHOW_LOG =
	    "org.eclipse.xslt.conversion.showlog";
	
	private static final String INS_TOPIC =
		"org.eclipse.gmf.webml.diagram.InsertTopic";
	
	private static final String SET_DIRS =
		"org.eclipse.xslt.conversion.setdirs";
	
	private EditPart selectedElement;
	
	private ArrayList<String> infElements = new ArrayList<String> ();
	
	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action){
		String id = action.getId();
		if (id.equals(UPDATE_MODEL)){
			checkProjectDirWebRatioDir();
		}else if (id.equals(SET_DIRS)){
			setDirectories();
	    }else if (id.equals(ARRANGE_ALL)){
	    	checkProjectDir4Arrange();
	    }else if (id.equals(SHOW_LOG)){
	    	checkProjectDir4ShowLog();
	    }else if (id.equals(INS_TOPIC)){
	    	checkDocDir4InsertTopic();
	    }
	}
	
	// Checks that WebMLDoc directory is specified.
	// If it is not specified, opens dialog with directories settings.
	// If it is specified, arranges all elements in diagrams.
	@SuppressWarnings("deprecation")
	private void checkProjectDir4Arrange() {
		try {
			URL url = Platform.getBundle("org.eclipse.gmf.webml.diagram").getEntry("/");
			url = Platform.asLocalURL(url);
			File projectDirFile = new File(url.getPath().concat("settings.dir"));
			if (projectDirFile.exists()) {
				BufferedReader input =  new BufferedReader(new FileReader(projectDirFile));
		    	String projectPath = input.readLine();
		    	if ((projectPath != null) && (new File(projectPath)).exists()){

		    		// get active Page
		    		IWorkbenchPage page = window.getActivePage();

		    		// get Editor 
		    		DiagramEditor diagramEditor = (DiagramEditor) page.getActiveEditor();

		    		DiagramEditor newEditor = (DiagramEditor) page.getActiveEditor();
		        	//Set the focus to the diagram editor
		        	newEditor.setFocus();
		    		
		    		// arrange all elements, including compartment elements, using standard  function
		    		DiagramEditPart diagramEditPart = newEditor.getDiagramEditPart();  
		    		EditPart epart = (EditPart) diagramEditPart;
		    		gmfArrangeAll(epart, epart);

		    		// save diagram
		    		try {
		    			diagramEditor.getDiagram().eResource().save(webml.diagram.part.WebmlDiagramEditorUtil.getSaveOptions());
		    		} catch (IOException e) {
		    		}
		    		
		    		// arrange all blocks as in Webratio
		    		arrangeAllBlocks(projectPath);

		    		// refresh workspace
		    		try {
		    			IWorkspaceRoot myWorkspaceRoot= ResourcesPlugin.getWorkspace().getRoot();
		    			myWorkspaceRoot.refreshLocal(IResource.DEPTH_INFINITE, null);
		    		} catch (CoreException e) {
		    		}
		    		
		    		//arrangeAllBlocks(projectPath);		    		
		    	} else {
    				setDirectories();    		    		
		    	}    		    	
		        input.close();
				return;
			} else {
				setDirectories();
		        return;
			}
		} catch (Exception e) {
		}
	}
	
	// Checks that WebMLDoc directory is specified.
	// If it is not specified, opens dialog with directories settings.
	// If it is specified, opens Dialog window with Changes Log.
	@SuppressWarnings("deprecation")
	private boolean checkProjectDir4ShowLog() {
		try {
			URL url = Platform.getBundle("org.eclipse.gmf.webml.diagram").getEntry("/");
			url = Platform.asLocalURL(url);
			File projectDirFile = new File(url.getPath().concat("settings.dir"));
			if (projectDirFile.exists()) {
				BufferedReader input =  new BufferedReader(new FileReader(projectDirFile));
		    	String projectPath = input.readLine();
		    	if ((projectPath != null) && (new File(projectPath)).exists()){
		    		File dirSettingsFile = new File(projectPath.concat("/settings.dir"));
					String webRatioDir;
					if (dirSettingsFile.exists()) {
						BufferedReader dirsInput =  new BufferedReader(new FileReader(dirSettingsFile));
				    	webRatioDir = dirsInput.readLine();
				    	if (webRatioDir == null)
				    		webRatioDir = "";
				        dirsInput.close();
					} else
			    		webRatioDir = "";
					if (webRatioDir.equals("")) {
						setDirectories();
						return false;
					}
					else {
						showLog(projectPath, webRatioDir.concat("\\Model\\WebModel"));
						input.close();
						return true;
					}
		    	} else {
    				setDirectories();    		    		
		    	}    		    	
		        input.close();
				return false;
			} else {
				setDirectories();
		        return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	// Checks that WebMLDoc directory and WebRatio are specified.
	// If they are not specified, opens dialog with directories settings.
	// If they are specified, updates WebMLDoc model
	@SuppressWarnings("deprecation")
	private boolean checkProjectDirWebRatioDir() {
		try {
			URL url = Platform.getBundle("org.eclipse.gmf.webml.diagram").getEntry("/");
			url = Platform.asLocalURL(url);
			File projectDirFile = new File(url.getPath().concat("settings.dir"));
			if (projectDirFile.exists()) {
				BufferedReader input =  new BufferedReader(new FileReader(projectDirFile));
		    	String projectPath = input.readLine();
		    	if ((projectPath != null) && (new File(projectPath)).exists()){
		    		File dirSettingsFile = new File(projectPath.concat("/settings.dir"));
					String webRatioDir;
					if (dirSettingsFile.exists()) {
						BufferedReader dirsInput =  new BufferedReader(new FileReader(dirSettingsFile));
				    	webRatioDir = dirsInput.readLine();
				    	if (webRatioDir == null)
				    		webRatioDir = "";
				        dirsInput.close();
					} else
			    		webRatioDir = "";
					if (webRatioDir.equals("")) {
						setDirectories();
						return false;
					}
					else {
						updateModel(projectPath, webRatioDir.concat("\\Model\\WebModel"));
						input.close();
						return true;
					}
		    	} else {
    				setDirectories();    		    		
		    	}    		    	
		        input.close();
				return false;
			} else {
				setDirectories();
		        return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	// updates WebMLDoc model
	private void updateModel(String docModelPath, String webRatioPath) {
		
		// backup old models and generate new ones
		backupAllModels(docModelPath, "webml");
		backupAllModels(docModelPath, "xml");
		if (!genAllModels(webRatioPath, docModelPath)) {
			return;
		}
		
		// copy DocTopics from old model to new generated
		copyTopics(docModelPath);
		
		// get xml diff
		xmlDiff(docModelPath);

		// error here! this refresh doesn't work
		// refresh workspace
		try {
			IWorkspaceRoot myWorkspaceRoot= ResourcesPlugin.getWorkspace().getRoot();
			myWorkspaceRoot.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
		}
	}

	private void showLog(String docModelPath, String webRatioPath) {
		// open log itself
        ChangeLogDialog chlogDlg = new ChangeLogDialog(PlatformUI.getWorkbench().
                getActiveWorkbenchWindow().getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
        chlogDlg.addSource(docModelPath + "\\changes.log");
        chlogDlg.open();

    	// update models
		updateModel(docModelPath, webRatioPath);
		
		// update Tree in Dialog
        chlogDlg.addSource(docModelPath + "\\changes.log");
		chlogDlg.updateTtee();
		Display display = window.getShell().getDisplay();
		while (!chlogDlg.getShell().isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// opens dialog with directories selection
	private void setDirectories() {
		SelectDirDialog selDirDlg = new SelectDirDialog(PlatformUI.getWorkbench().
                getActiveWorkbenchWindow().getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
		selDirDlg.open();
	}

	// Checks that documentation directory is specified.
	// If it is not specified, opens dialog with directories settings.
	// If documentation folder specified, opens dialog with DocTopic selection.
	@SuppressWarnings("deprecation")
	private void checkDocDir4InsertTopic() {
		try {
			URL url = Platform.getBundle("org.eclipse.gmf.webml.diagram").getEntry("/");
			url = Platform.asLocalURL(url);
			File projectDirFile = new File(url.getPath().concat("settings.dir"));
			if (projectDirFile.exists()) {
				BufferedReader input =  new BufferedReader(new FileReader(projectDirFile));
		    	String projectPath = input.readLine();
		    	if ((projectPath != null) && (new File(projectPath)).exists()){
		    		File dirSettingsFile = new File(projectPath.concat("/settings.dir"));
					String webRatioDir;
					String docLineDir;
					if (dirSettingsFile.exists()) {
						BufferedReader dirsInput =  new BufferedReader(new FileReader(dirSettingsFile));
				    	webRatioDir = dirsInput.readLine();
				    	if (webRatioDir == null)
				    		webRatioDir = "";
				    	docLineDir = dirsInput.readLine();
				    	if (docLineDir == null)
				    		docLineDir = "";
				        dirsInput.close();
					} else
			    		docLineDir = "";
					if (docLineDir.equals(""))
						setDirectories();
					else
						createSelectTopicDialog(docLineDir);
		    	} else {
    				setDirectories();    		    		
		    	}    		    	
		        input.close();
				return;
			} else {
				setDirectories();
		        return;
			}
		} catch (Exception e2) {
		}
	}

	// creates dialog with DocTopic selection
	private void createSelectTopicDialog(String docLineDir) {
		InsertTopicDialog insTopicDlg = new InsertTopicDialog(new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
		getInfElemets(docLineDir);
		String topic = insTopicDlg.open(infElements);
		
		if (!topic.equals("")) {
			createDocTopic(topic);
		}		
	}

	// calls standard gmf function 'Arrange all' for all elements including compartments
	@SuppressWarnings("unchecked")
	private void gmfArrangeAll(EditPart epart, EditPart diagramEditPart) {
		List result = epart.getChildren();
		//System.out.println(epart.getChildren().size());
		if (result.size() > 1) {		
			// arrange all elements on current level of compartment
			ArrangeRequest arrangeRequest = new ArrangeRequest(ActionIds.ACTION_ARRANGE_SELECTION);
			arrangeRequest.setPartsToArrange(result);  
			Command arrangeCmd = diagramEditPart.getCommand(arrangeRequest);
			arrangeCmd.execute();
		}
		
		// call gmfArrangeAll for compartment children
		for (int i=0; i<epart.getChildren().size(); i++){
			if (((EditPart)epart.getChildren().get(i)).getChildren().size() > 2) {
				EditPart compartment = (EditPart)((EditPart)epart.getChildren().get(i)).getChildren().get(2);
				gmfArrangeAll(compartment, diagramEditPart);
			}			
		}
	}
	

	// get all InfElments from directory with DRl documentation
	private void getInfElemets(String docPath) {
		// create filter to read only drl files
		FilenameFilter drlFile = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith("drl");
		    }
		};		
		
		//finds InfElements in every .drl file
		try {
			// Directory with Docline documentation
			File docDir = new File(docPath);
			
			// SAXParser for parsing drl files
		    SAXParserFactory diffFactory = SAXParserFactory.newInstance();
		    SAXParser diffParser = diffFactory.newSAXParser();

		    // Override default handler with GetInfElementsHandler
		    GetInfElementsHandler infElemsHandler = new GetInfElementsHandler();
		    
		    // clear previous list of InfElements
		    infElements.clear();
		    
		    // get list of drl files in documentation directory
			File[] curfile = docDir.listFiles(drlFile);
			for (int i=0; i<curfile.length; i++) {
				//System.out.println(curfile[i].getName());
				// parse next drl file and try to find any InfElements 
			    diffParser.parse(curfile[i], infElemsHandler);
			    ArrayList<String> infElems = infElemsHandler.getInfElements();
			    for (int j=0; j<infElems.size();j++)
			    	infElements.add(infElems.get(j));
			    infElems.clear();
			}
			Collections.sort(infElements, String.CASE_INSENSITIVE_ORDER);
		} catch (Exception err) {
			//err.printStackTrace();
    	}
	}

	// arrange all elements in the diagram	
	private void arrangeAllBlocks(String docModelPath) {
		try {
			// create filter to read only diagram files
			FilenameFilter diagramFile = new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.endsWith("webml_diagram");
			    }
			};
			//docModelDir
			File docModelDir = new File(docModelPath);
			
			// array of diagram files in docModelDir directory
			File[] curfile = docModelDir.listFiles(diagramFile);
			for (int i=0; i<curfile.length; i++) {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				
		        // DOM with webml diagram
		        Document diagram = builder.parse(curfile[i]);
		        // get coords dom from file
		        Document coords = builder.parse(new File(curfile[i].getAbsolutePath().replace(".webml_diagram", ".coords")));
		        //set Id attribute for every element on Dom
		        coords = setDocumentIdAttribute(coords, "Id");
				
		        // get webml file to know element Id by its gmfId
		        SAXParserFactory fact = SAXParserFactory.newInstance();
				SAXParser parser = fact.newSAXParser();
				GetIdByGMFIdHandler idhandler = new GetIdByGMFIdHandler();
				parser.parse(new File(curfile[i].getAbsolutePath().replace(".webml_diagram", ".webml")), idhandler);
				Document webmlModel = idhandler.getDom();
				setDocumentIdAttribute(webmlModel, "gmfId");
		        
		        // get list of all tags with name "element" and next to him layoutConstraint
				NodeList elems = diagram.getElementsByTagName("element");
				NodeList coordList = diagram.getElementsByTagName("layoutConstraint");
				for (int j = 0; j < coordList.getLength(); j++) {
					Element curElem = (Element) elems.item(j);
					int gmfIdPos = curElem.getAttribute("href").lastIndexOf("#");
					String gmfId = curElem.getAttribute("href").substring(gmfIdPos + 1);
					//System.out.println(gmfId);
					if (!gmfId.substring(0, gmfId.lastIndexOf(".")).endsWith("topic")) {
						Element webmlElem = webmlModel.getElementById(gmfId);
						if (webmlElem != null ){
							String webmlId = webmlElem.getAttribute("Id");
							Element coordsElem = coords.getElementById(webmlId);
							//System.out.println(gmfId);
							Element coord = (Element) coordList.item(j);
							//System.out.println("hello");
							coord.setAttribute("x", coordsElem.getAttribute("x"));
							coord.setAttribute("y", coordsElem.getAttribute("y"));
						} else {
							Element coord = (Element) coordList.item(j);
							coord.setAttribute("x", "0");
							coord.setAttribute("y", "0");
						}
						//print updated DOM to file
						printXMLToFile(diagram, curfile[i].getAbsolutePath());
						//System.out.println(gmfId + " : x=" + coord.getAttribute("x") + "; y=" + coord.getAttribute("y"));
					}
				}
			}
		}
	    catch (Exception err) {
	        //err.printStackTrace();
	    	return;
	    }
	}

	// Set idAttr as ID attribute of all elements in DOM doc
	private Document setDocumentIdAttribute(Document doc, String idAttr) {
		NodeList elems = doc.getElementsByTagName("element");
		for (int j = 0; j < elems.getLength(); j++) {
			Element curElem = (Element) elems.item(j);
			curElem.setIdAttribute(idAttr, true);
		}
		return doc;
	}
	
	// get xmlDiff in WebRatio project and handle it
	private void xmlDiff(String docModelPath) {
		try {
			// create filter to read only webratio xml files
			FilenameFilter xmlFile = new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.endsWith("xml");
			    }
			};
			
			//docModelDir
			File docModelDir = new File(docModelPath);
			
			// array of xml files in docModelDir directory
			File[] curfile = docModelDir.listFiles(xmlFile);
			for (int i=0; i<curfile.length; i++) {
				// Assign log file to null,
			      // all error messages will be printing to the standard error stream
			      ExamXML.setLogFile(null);
			      // input XML files
			      String oldxml = curfile[i].getAbsolutePath().concat(".old");
			      // if no xml.olf file, continue
			      if (!(new File(oldxml).exists()))
			    	  continue;
			      String newxml = curfile[i].getAbsolutePath();
			      // output XML file with Diff
			      String result = curfile[i].getAbsolutePath().replace(".xml", ".diff");
			      // compare XMLs
			      ExamXML.compareXMLFiles(oldxml, newxml, result, null);
			      updateXMLDiff(result);
			      // parse changes xml
			      SAXParserFactory diffFactory = SAXParserFactory.newInstance();
			      SAXParser diffParser = diffFactory.newSAXParser();

			      // override default handler
			      DiffHandler diffHandler = new DiffHandler();
			      diffParser.parse(new File(result), diffHandler);
			      
			      // handle changes
			      String[] changesStr = diffHandler.getChangesStr();
			      String[] changesId = diffHandler.getChangesId();
			      int changesQuantity = diffHandler.getChangesQuantity();
			      
			      // get sections of user manual which must be checked
			      getDocSections(changesId, changesStr, changesQuantity, docModelPath + "\\changes.log", curfile[i].getAbsolutePath().replace(".xml", ".webml"));
			}
	    }
	    catch (Exception err) {
	        //System.out.println("diff exception");
	        //err.printStackTrace();
	    	return;
	    }
	}
	
	// offers list of user manual sections that must be checked depending on changes in hypertext model
	private void getDocSections(String[] changesId, String[] changesStr,
			int changesQuantity, String logPath, String webmlPath) {
		try {
			// set file with webmldoc model
			File webmlFile = new File(webmlPath);
			
			// parse webmldoc file into Document webmlDom
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document webmlDom = db.parse(webmlFile);
			Document webmlOldDom = db.parse(new File(webmlPath + ".old"));
			
			// set 'Id' attribute as default id attribute in every 'element' of webmlDom
			NodeList elems =  webmlDom.getElementsByTagName("element");
			for (int k = 0; k < elems.getLength(); k++) {
				Element curElem = (Element) elems.item(k);
				curElem.setIdAttribute("Id", true);
			}

			// set 'Id' attribute as default id attribute in every 'element' of webmlOldDom
			elems =  webmlOldDom.getElementsByTagName("element");
			for (int k = 0; k < elems.getLength(); k++) {
				Element curElem = (Element) elems.item(k);
				curElem.setIdAttribute("Id", true);
			}
			
			InterfaceChange root;
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
			// deserialize xml
			XStream xstream = new XStream(); // require XPP3 library
		    xstream.alias("InterfaceChange",  InterfaceChange.class);
		    xstream.alias("DocChange",  DocChange.class);
		    if (!xml.trim().equals(""))
		    	root = (InterfaceChange) xstream.fromXML(xml);
		    else
		    	root = null;
		    
			// handle all changes in hypertext model
			for (int i = 0; i < changesQuantity; i++) {
				InterfaceChange htChange = new InterfaceChange(changesStr[i], changesId[i]);
				if (root == null)
					root = new InterfaceChange();
				root.add(htChange);
				Element changedElem = webmlDom.getElementById(changesId[i]);
				if (changedElem == null) {
					// element has been deleted from webml model
					// let's try to find it in wembl.old backup
					// and add information about all its deleted children topics
					changedElem = webmlOldDom.getElementById(changesId[i]);
					if (changedElem == null) continue;
					addChildrenTopics(changedElem, htChange, true);
				} else
					addChildrenTopics(changedElem, htChange, false);
			}
			
			// Serialize xml
		    xml = xstream.toXML(root);
		    
		    // write xml back to log
            BufferedWriter out = new BufferedWriter(new FileWriter(logPath));
	        out.write(xml);
	        out.close();
			
		} catch (FileNotFoundException err) {
			return;
    	} catch (Exception err) {
			//err.printStackTrace();
    	}
		
	}
	
	// finds all topic children of element changedElem and adds information about them htChange structure
	// allLevels tells where we have to find topics - only in firsl-level children of changedElem
	// or in all its issue
	private void addChildrenTopics(Element changedElem, InterfaceChange htChange, boolean allLevels) {
		NodeList children = changedElem.getChildNodes();
		for (int k = 0; k < children.getLength(); k++) {
			Node child = children.item(k);
			if (child.getNodeName().equals("topic")) {
				Node attrName = child.getAttributes().getNamedItem("name");
				String sectionId = attrName.getNodeValue();
				int to = sectionId.lastIndexOf("'");
				sectionId = sectionId.substring(0, to);
				int from = sectionId.lastIndexOf("'") + 1;
				sectionId = sectionId.substring(from);
				DocChange docChange = new DocChange(sectionId, false);
				htChange.add(docChange);
			} else if (child.getNodeName().equals("element") && allLevels)
				addChildrenTopics((Element) child, htChange, allLevels);
		}
	}

	// changes all generated comments to special elements to parse result
	// diff file without parsing comments
	private void updateXMLDiff(String filename) {
		try {
			StringBuilder contents = new StringBuilder();
	    	BufferedReader input =  new BufferedReader(new FileReader(new File(filename)));
	    	String line = null; // next string read from input file
	    	int lineCounter = 0;
	    	if ((line = input.readLine()) != null) {
	    		lineCounter++;
	    		line = line.replace("<!-- Added Element(s) -->", "<added/>");
	        	line = line.replace("<!-- Deleted Element(s) -->", "<deleted/>");
	        	line = line.replace("<!-- Changed Element:old -->", "<changed_old/>");
	        	line = line.replace("<!-- Changed Element:new -->", "<changed_new/>");
	        	contents.append(line);
	    	}
	        while (( line = input.readLine()) != null){
	    		lineCounter++;
	        	line = line.replace("<!-- Added Element(s) -->", "<added/>");
	        	line = line.replace("<!-- Deleted Element(s) -->", "<deleted/>");
	        	line = line.replace("<!-- Changed Element:old -->", "<changed_old/>");
	        	line = line.replace("<!-- Changed Element:new -->", "<changed_new/>");
	        	contents.append(System.getProperty("line.separator"));
	        	contents.append(line);
	        }
	        input.close();
	        if (lineCounter == 1) {
	        	contents.append(System.getProperty("line.separator"));
	        	contents.append("<empty/>");
	        }
	        BufferedWriter out = new BufferedWriter(new FileWriter(filename));
	        out.write(contents.toString());
	        out.close();
	    } catch (IOException err) {
			return;
	    }
	}

	//on every opening of GMF editor new diagram id generated.
	// To keep all topics we firstly have to backup all webml files to .old files
	// Input: docModelPath - path of directory with files to backup
	private void backupAllModels(String docModelPath, final String extension) {
		// create filter to read only files with specified extension
		FilenameFilter correctFile = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(extension);
		    }
		};
		
		//docModelDir
		File docModelDir = new File(docModelPath);
		
		//copy files to backup .old files
		try {
			File[] curfile = docModelDir.listFiles(correctFile);
			for (int i=0; i<curfile.length; i++) {
				BufferedReader from =  new BufferedReader(new FileReader(curfile[i]));
				String outputPath =  docModelPath + "\\" + curfile[i].getName().concat(".old");
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
			//err.printStackTrace();
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
								
				String oldFilePath = curfile[i].getAbsolutePath().concat(".old");
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
					  Element parent = outputDom.getElementById(parentId);
					  
					  if (parent != null)
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
			//err.printStackTrace();
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
		
		// initialize webml model file
		generateDiagram(svGMFModel);

	}

	// this code initializes webml model file
	public void generateDiagram(String domainModelFileName) {
	    String diagramModelFileName = domainModelFileName + "_diagram";
	    EObject domainRoot = null;
	    try {
	        //load the domain model resource
	        ResourceSet domainResourceSet = new ResourceSetImpl();
	        domainResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,
	        		new XMIResourceFactoryImpl());
	        URI domainFileURI = URI.createFileURI(new File(domainModelFileName).getAbsolutePath());
	        Resource domainResource = domainResourceSet.getResource(domainFileURI, true);
	 
	        //create the diagram model resource
	        ResourceSet diagramResourceSet = new ResourceSetImpl();
	        diagramResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,
	        		new XMIResourceFactoryImpl());
	        URI diagramFileURI = URI.createFileURI(new File(diagramModelFileName).getAbsolutePath());
	        Resource diagramResource = diagramResourceSet.createResource(diagramFileURI);
	 
	        //create the diagram and save it to the file
	        domainRoot = (EObject) domainResource.getContents().get(0);
	        Diagram diagram = ViewService.createDiagram(domainRoot,webml.diagram.edit.parts.SiteviewEditPart.MODEL_ID,
	        		webml.diagram.part.WebmlDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
	        diagramResource.getContents().add(diagram);
	        diagramResource.save(webml.diagram.part.WebmlDiagramEditorUtil.getSaveOptions());
	    }
	 
	    catch (Exception e) {
	        //e.printStackTrace();
	    }
	 
	    if (domainRoot == null) {
	        //MessageDialog.openError(getShell(),webml.diagram.part.Messages.AnnotatedUCInitDiagramFileAction_InitDiagramFileResourceErrorDialogTitle,fct.ample.annotatedUC.annotateduc.diagram.part.Messages.AnnotatedUCInitDiagramFileAction_InitDiagramFileResourceErrorDialogMessage);
	        return;
	    }
	}
	
	// xslt transformation generates links in unproper format
	// this procedure convert links to proper GMF editor format
	private void updateLinks(String svGMFModel) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();

			// override default handler
			SAXHandler handler = new SAXHandler();
			parser.parse(new File(svGMFModel), handler);

			// SAXParser parses svGMFModel and returns dom with correct links
			Document dom =  handler.getDom();
			Document coords = handler.getCoords();
			coords = updateCoords(coords, svGMFModel.concat("_diagram"));
			
			//print
			printXMLToFile(dom, svGMFModel);
			//System.out.println("coords writing started");
			printXMLToFile(coords, svGMFModel.replace(".webml", ".coords"));
			//System.out.println("coords writing ended");
		}
		catch (Exception err) {
			//err.printStackTrace();
		}
	}

	// updates all coordinates in document coords according to changes in WebRatio project
	private Document updateCoords(Document coords, String svGMFModel) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();

			// override default handler
			UpdateCoordsHandler handler = new UpdateCoordsHandler();
			parser.parse(new File(svGMFModel), handler);

			// UpdateCoordsParser parses webml_diagram file and returns
			// dom with existing coords of elements
			Document updCoords =  handler.getCoords();
			
			// set "Id" as ID attribute in coords DOM
			coords = setDocumentIdAttribute(coords, "Id");
			
			// get webml.old file to know element id by gmfId
			GetIdByGMFIdHandler idhandler = new GetIdByGMFIdHandler();
			parser.parse(new File(svGMFModel.replace(".webml_diagram", ".webml.old")), idhandler);
			Document oldWebml = idhandler.getDom();
			setDocumentIdAttribute(oldWebml, "gmfId");
			
			// get all elements 
			NodeList elems = updCoords.getElementsByTagName("element");
			for (int j = 0; j < elems.getLength(); j++) {
				Element curElem = (Element) elems.item(j);
				// get gmfId attribute of current element from webml_diagram
				String gmfId = curElem.getAttribute("gmfId");
				// get Element from old Webml file corresponding current gmfId
				Element oldElem = oldWebml.getElementById(gmfId);
				// get element of coords file corresponding gmfId attribute 
				Element curCoord = coords.getElementById(oldElem.getAttribute("Id"));
				curCoord.setAttribute("x", curElem.getAttribute("x"));
				curCoord.setAttribute("y", curElem.getAttribute("y"));
			}
		}
		catch (Exception err) {
			//err.printStackTrace();
		}
		return coords;
	}

	private void printXMLToFile(Document dom, String output) {
		try {
			// set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            
            //System.out.println("1");

            // create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);            
            DOMSource source = new DOMSource(dom);
            trans.transform(source, result);
            String xmlString = sw.toString();
            
            // print xmlString to file
            // firstly, if file doesn't exist, create it
            File outputFile = new File(output);
            outputFile.createNewFile();
            // secondly, write data to file 
            BufferedWriter out = new BufferedWriter(new FileWriter(output));
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
	@SuppressWarnings("deprecation")
	private String convertSiteview(String svPath) {
		// open input file
		File svInput = new File(svPath);
		// generate output path
		String outPath = svInput.getParent() + "\\" + svInput.getName().substring(0, svInput.getName().length()-3) + "webml";
		try {
			URL url = Platform.getBundle("org.eclipse.gmf.webml.diagram").getEntry("/");
			url = Platform.asLocalURL(url);
			//System.out.println(url.getPath().concat("xslt/webml.xsl"));
			//System.out.println(new File(url.getPath().concat("xslt/webml.xsl")).getAbsolutePath());
			Processor proc = new Processor(false);
			XsltCompiler comp = proc.newXsltCompiler();
			// change path to xsl file later
			//System.out.println(new File(url.getPath().concat("xslt/webml.xsl")).getAbsolutePath());
			XsltExecutable exp = comp.compile(new StreamSource(new File(url.getPath().concat("xslt/webml.xsl")).toURI().toString()));
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
		} catch (Exception err) {
			MessageDialog.openInformation( 
					null,
					"error",      
					"error");
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
			//err.printStackTrace();
			return "";
    	}
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
	
	@SuppressWarnings("unchecked")
	private void createDocTopic(String name) {
		// create new DocTopic
		CompoundCommand cc = new CompoundCommand("Add new Topic");
		ObjectAdapter endAdapter = new ObjectAdapter();
		endAdapter.setObject(WebmlElementTypes.DocTopic_3003);
		CreateViewAndOptionallyElementCommand createTopicCmd = 
		new CreateViewAndOptionallyElementCommand(endAdapter, (ShapeNodeEditPart)selectedElement, new Point(10, 10), ((ShapeNodeEditPart)selectedElement).getDiagramPreferencesHint());
		cc.add(new ICommandProxy(createTopicCmd));
		((ShapeNodeEditPart)selectedElement).getDiagramEditDomain().getDiagramCommandStack().execute(cc);
		
		// set its Name to name
		Collection results = DiagramCommandStack.getReturnValues(cc);
		for (Object res: results) {
			if (res instanceof IAdaptable) {
				IAdaptable adapter = (IAdaptable) res;
				View view = (View) adapter.getAdapter(View.class);
				if (view != null) {
					DocTopic newTopic = (DocTopic)view.getElement();
					SetRequest reqSet = new SetRequest(((ShapeNodeEditPart)selectedElement).getEditingDomain(),
							newTopic, WebmlPackage.eINSTANCE.getDocTopic_Name(),
							name);
					SetValueCommand operation = new SetValueCommand(reqSet);
					((ShapeNodeEditPart)selectedElement).getDiagramEditDomain().getDiagramCommandStack().execute(new 
							ICommandProxy(operation));
				}
			}
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		selectedElement = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.getFirstElement() instanceof EditPart) {
				selectedElement = (EditPart) structuredSelection.getFirstElement();
			}
		}
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
}