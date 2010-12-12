package plweb.diagram.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.emf.common.util.EList;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import plweb.Area;
import plweb.DiagramRoot;
import plweb.Group;
import plweb.Node;
import plweb.Page;
import plweb.TargetRefElement;
import plweb.diagram.util.projects.ProjectOperationException;
import plweb.diagram.util.projects.ProjectPlweb;

public class ProductGenerator {
	
	private Document doc = null;
	
	private DiagramRoot diagramRoot = null;
	
	private String productName = null;
	
	private String projectPath = null;
	
	private List<plweb.Element> checkedElements = null;
	
	private List<String> pages = null;
	
	private List<String> oldPages = null;
	
	public ProductGenerator(DiagramRoot diagramRoot, String productName,
			String projectPath, Object[] checkedElementsArray) {
		this.diagramRoot = diagramRoot;
		this.productName = productName;
		this.projectPath = projectPath;
		this.checkedElements = new ArrayList<plweb.Element>();
		this.pages = new ArrayList<String>();
		for (Object object : checkedElementsArray) {
			if (object instanceof plweb.Element) {
				checkedElements.add((plweb.Element) object);
				if (object instanceof Page) {
					pages.add(((Page) object).getSource());
				}
			}
		}
	}
	
	public void setOldPages(List<String> oldPages) {
		this.oldPages = oldPages;
	}
	
	public void generate() throws ProjectOperationException {
		generateProductDiagram();
		generateProductProject();
	}
	
	protected  void generateProductDiagram() throws ProjectOperationException {
		String wrProjectPath = diagramRoot.getProjectPath();
		String projectName = wrProjectPath.substring(wrProjectPath.lastIndexOf("/") + 1);
		String productFilePath = projectPath.substring(0, projectPath
				.lastIndexOf("/"))
				+ "/"
				+ productName
				+ "."
				+ projectName;
		String productModelFilePath = productFilePath + ProjectPlweb.PLWEB_EXTENSION;
		String productDiagramFilePath = productFilePath + ProjectPlweb.PLWEB_DIAGRAM_EXTENSION;
		
		File productModelFile = new File(productModelFilePath);
		if (productModelFile.exists()) {
			productModelFile.delete();
		}
		File productDiagramFile = new File(productDiagramFilePath);
		if (productDiagramFile.exists()) {
			productDiagramFile.delete();
		}
		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			DOMImplementation domImpl = docBuilder.getDOMImplementation();
			doc = domImpl.createDocument(null, null, null);
			
			Element diagramRootElement = doc.createElement("plweb:DiagramRoot");
			diagramRootElement.setAttribute("xmi:version", "2.0");
			diagramRootElement.setAttribute("xmlns:xmi", "http://www.omg.org/XMI");
			diagramRootElement.setAttribute("xmlns:plweb", "http://plweb/1.0");
			diagramRootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			diagramRootElement.setAttribute("projectPath", projectPath);
			diagramRootElement.setAttribute("type", "PRODUCT");
			doc.appendChild(diagramRootElement);
			
			Area area = diagramRoot.getArea();
			if (area != null && checkedElements.contains(area)) {
				Element areaElement = doc.createElement("area");
				areaElement.setAttribute("title", area.getTitle());
				diagramRootElement.appendChild(areaElement);
				
				addElements(areaElement, area.getClass_());
			}
			
			DOMSource domSource = new DOMSource(doc);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult sr = new StreamResult(new File(productModelFilePath));
			transformer.transform(domSource, sr);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProjectOperationException("Error occured while creating product diagram");
		}
	}
	
	protected void generateProductProject() throws ProjectOperationException {
		String wrProjectPath = diagramRoot.getProjectPath();
		String wrProductProjectPath = wrProjectPath.substring(0, wrProjectPath
				.lastIndexOf("/") + 1)
				+ productName;
		
		File projectWrNew = new File(wrProductProjectPath);
		
		if (oldPages == null) {
			if (projectWrNew.exists()) {
				projectWrNew.delete();
			}
		} else {
			deleteOldPages(projectWrNew);
		}
		
		File projectWr = new File(wrProjectPath);
		copyDirs(projectWr, projectWrNew);
	}
	
	private void addElements(Element parentElement, EList<TargetRefElement> elements) {
		for (TargetRefElement element : elements) {
			if (!checkedElements.contains(element)) {
				continue;
			}
			if (element instanceof Group) {
				addElements(parentElement, ((Group) element).getClass_());
				continue;
			} else if (element instanceof Node) {
				Element nodeElement = doc.createElement("class");
				nodeElement.setAttribute("xsi:type", "plweb:Node");
				nodeElement.setAttribute("title", element.getTitle());
				parentElement.appendChild(nodeElement);
				
				addElements(nodeElement, ((Node) element).getClass_());
			} else if (element instanceof Page) {
				Element pageElement = doc.createElement("class");
				pageElement.setAttribute("xsi:type", "plweb:Page");
				pageElement.setAttribute("title", element.getTitle());
				pageElement.setAttribute("source", ((Page)element ).getSource());
				parentElement.appendChild(pageElement);
			}
		}
	}
	
	private void deleteOldPages(File targetDir) {
		if (targetDir == null || !targetDir.exists()) {
			return;
		}
		String[] children = targetDir.list();
		if (children == null) {
			return;
		}
		for (String child : children) {
			File childFile = new File(targetDir + "/" + child);
			if (childFile.isDirectory()) {
				deleteOldPages(childFile);
			} else {
				if (oldPages.contains(child)) {
					childFile.delete();
				}
			}
		}
	}
	
	private void copyDirs(File sourceDir, File targetDir) {
		if (sourceDir == null || targetDir == null) {
			return;
		}
		String[] children = sourceDir.list();
		if (children == null) {
			return;
		}
		if (!targetDir.exists()) {
			targetDir.mkdir();
		}
		for (String child : children) {
			File childFile = new File(sourceDir + "/" + child);
			File targetChildFile = new File(targetDir + "/" + child);
			if (child.equals(".Temp~")) {
				continue;
			}
			if (child.startsWith("page") && !pages.contains(child)) {
				continue;
			}
			if (childFile.isDirectory()) {
				copyDirs(childFile, targetChildFile);
			} else if (!targetChildFile.exists()){
				copyFiles(childFile, targetChildFile);
			}
		}
	}
	
	private void copyFiles(File source, File target) {
		try {
			FileReader in = new FileReader(source);
		    FileWriter out = new FileWriter(target);
		    int c;
		    
		    while ((c = in.read()) != -1) {
		      out.write(c);
		    }
	
		    in.close();
		    out.close();
	    } catch (IOException e) {
	    	// do nothing for now
	    }
	}
}
