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

import javax.xml.parsers.*;
import javax.xml.parsers.DocumentBuilder;
import com.a7soft.examxml.ExamXML;

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
	
	private static final String ARRANGE_ALL =
	    "org.eclipse.xslt.conversion.arrangeall"; 

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action){
		String id = action.getId();
		String docModelPath = "C:\\Users\\Bagum\\runtime-EclipseApplication\\DocModel";
		if (id.equals(OPEN_EDITOR)){
			//String projectPath = getWebRatioProjectDirectory();
			String projectPath = "C:\\Program Files\\WebRatio\\WebRatio 5.1.1\\workspace\\Acme";
			String webModelPath = projectPath.concat("\\Model\\WebModel");
			backupAllModels(docModelPath, "webml");
			backupAllModels(docModelPath, "xml");
			if (!genAllModels(webModelPath, docModelPath)) return;
			copyTopics(docModelPath);
			xmlDiff(docModelPath);
		}else if (id.equals(ARRANGE_ALL)){
	        arrangeAllBlocks(docModelPath);
	    }else if (id.equals(CHECK_DOC)){
	        System.out.println("Check Documentation command selected");
	        //String path = getElemPath(sv2#area5#page21#dau11, );
	        //System.out.println();
	    }else  {
			String projectPath = getWebRatioProjectDirectory();
			String webModelPath = projectPath.concat("\\Model\\WebModel");
			backupAllModels(docModelPath, "webml");
			backupAllModels(docModelPath, "xml");
			if (!genAllModels(webModelPath, docModelPath)) return;
			copyTopics(docModelPath);
        }
	}
	
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
		        Document coords = builder.parse(curfile[i].getAbsolutePath().replace("webml_diagram", "coords"));
		        //set Id attribute for every element on Dom
		        coords = setCoordsIdAttribute(coords, "gmfId");
				
		        // get list of all tags with name "element" and next to him layoutConstraint
				NodeList elems = diagram.getElementsByTagName("element");
				NodeList coordList = diagram.getElementsByTagName("layoutConstraint");
				for (int j = 0; j < coordList.getLength(); j++) {
					Element curElem = (Element) elems.item(j);
					int gmfIdPos = curElem.getAttribute("href").lastIndexOf("#");
					String gmfId = curElem.getAttribute("href").substring(gmfIdPos + 1);
					Element coordsElem = coords.getElementById(gmfId);
					//System.out.println(gmfId);
					Element coord = (Element) coordList.item(j);
					//System.out.println("hello");
					coord.setAttribute("x", coordsElem.getAttribute("x"));
					coord.setAttribute("y", coordsElem.getAttribute("y"));
					//print updated DOM to file
					printXMLToFile(diagram, curfile[i].getAbsolutePath());
					//System.out.println(gmfId + " : x=" + coord.getAttribute("x") + "; y=" + coord.getAttribute("y"));
					
				}
			}
		}
	    catch (Exception err) {
	        err.printStackTrace();
	    	return;
	    }
	}

	private Document setCoordsIdAttribute(Document coords, String idAttr) {
		NodeList elems = coords.getElementsByTagName("element");
		for (int j = 0; j < elems.getLength(); j++) {
			Element curElem = (Element) elems.item(j);
			curElem.setIdAttribute(idAttr, true);
		}
		return coords;
	}
	
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
			      getDocSections(changesId, changesStr, changesQuantity, curfile[i].getAbsolutePath().replace(".xml", ".webml"));
			}
	    }
	    catch (Exception err) {
	        System.out.println("diff exception");
	        err.printStackTrace();
	    	return;
	    }
	}
	
	// offers list of user manual sections that must be checked depending on changes in hypertext model
	private void getDocSections(String[] changesId, String[] changesStr,
			int changesQuantity, String webmlPath) {
		try {
			// set file with webmldoc model
			File webmlFile = new File(webmlPath);
			
			// parse webmldoc file into Document webmlDom
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document webmlDom = db.parse(webmlFile);
			
			// set 'Id' attribute as default id attribute in every 'element' tag
			NodeList elems =  webmlDom.getElementsByTagName("element");
			for (int k = 0; k < elems.getLength(); k++) {
				Element curElem = (Element) elems.item(k);
				curElem.setIdAttribute("Id", true);
			}
			
			// handle all changes in hypertext model
			for (int i = 0; i < changesQuantity; i++) {
				Element changedElem = webmlDom.getElementById(changesId[i]);
				if (changedElem == null) continue;
				NodeList children = changedElem.getChildNodes();
				String checkDoc = "";
				for (int k = 0; k < children.getLength(); k++) {
					Node child = children.item(k);
					if (child.getNodeName().equals("topic")) {
						Node attrName = child.getAttributes().getNamedItem("name"); //getAttribute("name");
						String sectionId = attrName.getNodeValue();
						checkDoc = checkDoc + "'" + sectionId + "', ";
					}
				}
				if (checkDoc.length() > 0) {
					checkDoc = " Check documentation sections with these ID attributes: " + checkDoc.substring(0, checkDoc.length() - 2) + ".";
				}
				changesStr[i] = changesStr[i] + checkDoc;
			}
			
			// print all information about changes
			for (int i = 0; i < changesQuantity; i++) {
				System.out.println(changesStr[i]);
			}
			
		} catch (FileNotFoundException err) {
			return;
    	} catch (Exception err) {
			err.printStackTrace();
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
			printXMLToFile(coords, svGMFModel.replace("webml", "coords"));
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

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
			coords = setCoordsIdAttribute(coords, "Id");
			
			// get webml.old file to know element id by gmfId
			GetIdByGMFIdHandler idhandler = new GetIdByGMFIdHandler();
			parser.parse(new File(svGMFModel.replace(".webml_diagram", ".webml.old")), idhandler);
			Document oldWebml = idhandler.getDom();
			
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

            // create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);            
            DOMSource source = new DOMSource(dom);
            trans.transform(source, result);
            String xmlString = sw.toString();

            // print xmlString to file
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