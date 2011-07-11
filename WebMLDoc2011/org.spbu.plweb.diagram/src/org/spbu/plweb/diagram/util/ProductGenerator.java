package org.spbu.plweb.diagram.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.emf.common.util.EList;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import org.spbu.plweb.Area;
import org.spbu.plweb.DiagramRoot;
import org.spbu.plweb.DocTopic;
import org.spbu.plweb.Group;
import org.spbu.plweb.Node;
import org.spbu.plweb.Page;
import org.spbu.plweb.Root;
import org.spbu.plweb.SiteView;
import org.spbu.plweb.TargetRefElement;
import org.spbu.plweb.diagram.util.projects.ProjectOperationException;
import org.spbu.plweb.diagram.util.projects.ProjectPlweb;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.DocTopicsPartsReader;

public class ProductGenerator {

	private Document doc = null;

	private DiagramRoot diagramRoot = null;

	private String productName = null;

	private String projectPath = null;
	

	private List<org.spbu.plweb.Element> checkedElements = null;

	private List<String> pages = null;

	private List<String> oldPages = null;
	
	private String docPath = null;
	
	private List<String> topics = new ArrayList<String>();

	private List<String> allInfElements = new ArrayList<String>();

	public ProductGenerator(DiagramRoot diagramRoot, String productName,
			String projectPath, Object[] checkedElementsArray, String docPath) {
		this.diagramRoot = diagramRoot;
		this.productName = productName;
		this.projectPath = projectPath;
		this.checkedElements = new ArrayList<org.spbu.plweb.Element>();
		this.pages = new ArrayList<String>();
		this.docPath = docPath;
		for (Object object : checkedElementsArray) {
			if (object instanceof org.spbu.plweb.Element) {
				checkedElements.add((org.spbu.plweb.Element) object);
				if (object instanceof Page) {
					pages.add(((Page) object).getSource());
				}
			}
		}
		this.allInfElements = DocTopicsPartsReader.getListOfDocElements(docPath);
	}

	public void setOldPages(List<String> oldPages) {
		this.oldPages = oldPages;
	}

	public void generate() throws ProjectOperationException {
		generateProductDiagram();
		generateProductProject();
		generateDoc(topics, allInfElements);
	}

	public void generateDoc(List<String> topics, List<String> allInfElements)
			throws TransformerFactoryConfigurationError,
			ProjectOperationException {
		String productDocFilePath = docPath + File.separator + productName
				+ ".drl";
		File productDocFile = new File(productDocFilePath);
		if (productDocFile.exists()) {
			productDocFile.delete();
		}
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			DOMImplementation domImpl = docBuilder.getDOMImplementation();
			Document doc = domImpl.createDocument(null, null, null);
			doc.setXmlStandalone(true);

			Element productDocumentationElement = doc
					.createElement("d:ProductDocumentation");
			doc.appendChild(productDocumentationElement);

			Element finalInfPriductElement = doc
					.createElement("d:FinalInfProduct");
			finalInfPriductElement.setAttribute("id", productName);
			finalInfPriductElement.setAttribute("infproductid", productName);
			productDocumentationElement.appendChild(finalInfPriductElement);

			for (String infElement : allInfElements) {
				if (topics.contains(infElement)) {
					Element el = doc.createElement("d:Adapter");
					el.setAttribute("infelemrefid", infElement);
					finalInfPriductElement.appendChild(el);
				}
			}

			DOMSource domSource = new DOMSource(doc);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer
					.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult sr = new StreamResult(new File(productDocFilePath));
			transformer.transform(domSource, sr);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProjectOperationException(
					"Error occured while creating product diagram");
		}
	}

	public List<String> getAllInfElements() {
		return allInfElements;
	}

	public void setAllInfElements(List<String> allInfElements) {
		this.allInfElements = allInfElements;
	}

	protected void generateProductDiagram() throws ProjectOperationException {
		String wrProjectPath = diagramRoot.getProjectPath();
		String docPath = diagramRoot.getDocPath();
		String projectName = wrProjectPath.substring(wrProjectPath
				.lastIndexOf("/") + 1);
		String productFilePath = projectPath.substring(0,
				projectPath.lastIndexOf("/"))
				+ "/" + productName + "." + projectName;
		String productModelFilePath = productFilePath
				+ ProjectPlweb.PLWEB_EXTENSION;
		String productDiagramFilePath = productFilePath
				+ ProjectPlweb.PLWEB_DIAGRAM_EXTENSION;

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
			diagramRootElement.setAttribute("xmlns:xmi",
					"http://www.omg.org/XMI");
			diagramRootElement.setAttribute("xmlns:plweb", "http://plweb/2.0");
			diagramRootElement.setAttribute("xmlns:xsi",
					"http://www.w3.org/2001/XMLSchema-instance");
			diagramRootElement.setAttribute("projectPath", projectPath);
			diagramRootElement.setAttribute("docPath", docPath);
			// diagramRootElement.setAttribute("type", "PRODUCT");
			doc.appendChild(diagramRootElement);

			Root root = diagramRoot.getRoot();
			if (root != null && checkedElements.contains(root)) {
				Element rootElement = doc.createElement("root");
				rootElement.setAttribute("title", root.getTitle());
				diagramRootElement.appendChild(rootElement);
				addElements(rootElement, root.getClass_());
			}

			DOMSource domSource = new DOMSource(doc);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer
					.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult sr = new StreamResult(new File(productModelFilePath));
			transformer.transform(domSource, sr);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProjectOperationException(
					"Error occured while creating product diagram");
		}
	}

	protected void generateProductProject() throws ProjectOperationException {
		String wrProjectPath = diagramRoot.getProjectPath();
		String wrProductProjectPath = wrProjectPath.substring(0,
				wrProjectPath.lastIndexOf("/") + 1)
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

	private void addElements(Element parentElement,
			EList<TargetRefElement> elements) {
		for (TargetRefElement element : elements) {
			if (!checkedElements.contains(element)) {
				continue;
			}
			Element newElement = null;
			if (element instanceof Group) {
				addElements(parentElement, ((Group) element).getClass_());
				continue;
			} else if (element instanceof Node) {
				Element nodeElement = doc.createElement("class");
				nodeElement.setAttribute("xsi:type", "plweb:Node");
				nodeElement.setAttribute("title", element.getTitle());
				newElement = (Element) parentElement.appendChild(nodeElement);
				addElements(nodeElement, ((Node) element).getClass_());
			} else if (element instanceof Page) {
				Element pageElement = doc.createElement("class");
				pageElement.setAttribute("xsi:type", "plweb:Page");
				pageElement.setAttribute("title", element.getTitle());
				pageElement
						.setAttribute("source", ((Page) element).getSource());
				newElement = (Element) parentElement.appendChild(pageElement);
			} else if (element instanceof Area) {
				Element areaElement = doc.createElement("class");
				areaElement.setAttribute("xsi:type", "plweb:Area");
				areaElement.setAttribute("title", element.getTitle());
				newElement = (Element) parentElement.appendChild(areaElement);
				addElements(areaElement, ((Area) element).getClass_());
			} else if (element instanceof SiteView) {
				Element siteViewElement = doc.createElement("class");
				siteViewElement.setAttribute("xsi:type", "plweb:SiteView");
				siteViewElement.setAttribute("title", element.getTitle());
				newElement = (Element) parentElement
						.appendChild(siteViewElement);
				addElements(siteViewElement, ((SiteView) element).getClass_());
			}
			for (DocTopic docTopic : element.getDocTopic()) {
				Element docTopicElement = doc.createElement("docTopic");
				String docTopicName = docTopic.getDocTopicName();
				docTopicElement.setAttribute("docTopicName",
						docTopicName);
				if (!topics.contains(docTopicName)) topics.add(docTopicName);
				newElement.appendChild(docTopicElement);
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
			} else if (!targetChildFile.exists()) {
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

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	public List<String> getTopics() {
		return topics;
	}
}
