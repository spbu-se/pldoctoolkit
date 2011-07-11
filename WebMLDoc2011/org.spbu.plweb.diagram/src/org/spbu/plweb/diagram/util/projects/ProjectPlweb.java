package org.spbu.plweb.diagram.util.projects;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.spbu.plweb.diagram.util.PathHelper;
import org.spbu.plweb.diagram.util.parsers.PlwebPagesParser;

public class ProjectPlweb extends ProjectAbstract {
	public final static String PLWEB_EXTENSION = ".plweb";
	public final static String PLWEB_DIAGRAM_EXTENSION = ".plweb_diagram";

	private String name = null;

	protected void setName(String name) {
		this.name = name;
	}

	protected String getName() {
		return name;
	}

	public ProjectPlweb(String path) {
		this.path = new File(path);
	}

	public void create() {
		delete();
		path.mkdir();
	}
	
	public void delete() {
		if (path.exists()) {
			path.delete();
		}
	}

	@Override
	public boolean isValid() {
		return true;
	}

	public void importProject(ProjectWebRatio projectWr, String docPath)
			throws ProjectOperationException {
		newImport(projectWr, docPath);
	}
	
	private void newImport(ProjectWebRatio projectWr, String docPath) {
		setName(projectWr.getProjectName());
		WebRatioProjectToXmlConverter.convertAndWrite(
				projectWr.getPath().getAbsolutePath(), docPath,
				projectWr.getProjectParts(), 
				buildPathToWrite()
		);
	}

	private String buildPathToWrite() {
		return path.getAbsolutePath() + "/" + name + PLWEB_EXTENSION;
	}

	public void update(ProjectWebRatio projectWr, String docPath)
			throws ProjectOperationException {

		setName(projectWr.getProjectName());

		File project = new File(path.getAbsoluteFile() + "/" + name
				+ PLWEB_EXTENSION);
		if (!project.exists()) {
			importProject(projectWr,docPath);
			return;
		}

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(project);
			Map<String, Node> nodes = new HashMap<String, Node>();
			NodeList notLinkedNodes = doc.getElementsByTagName("plweb:Page");

			for (int i = 0; i < notLinkedNodes.getLength(); i++) {
				Node node = notLinkedNodes.item(i);
				NamedNodeMap attrs = node.getAttributes();
				Node nodeSource = attrs.getNamedItem("source");
				nodes.put(nodeSource.getNodeValue(), node);
			}
			NodeList linkedNodes = doc.getElementsByTagName("class");
			for (int i = 0; i < linkedNodes.getLength(); i++) {
				Node node = linkedNodes.item(i);
				NamedNodeMap attrs = node.getAttributes();
				Node nodeType = attrs.getNamedItem("xsi:type");
				Node nodeSource = attrs.getNamedItem("source");
				if (nodeType != null && nodeType.getNodeValue().equals("plweb:Page") && nodeSource != null) {
					nodes.put(nodeSource.getNodeValue(), node);
				}
			}
			
			Map<String, String> pagesPw = new HashMap<String, String>();
			for (String source : nodes.keySet()) {
				Node node = nodes.get(source);
				NamedNodeMap attrs = node.getAttributes();
				Node nodeTitle = attrs.getNamedItem("title");
				pagesPw.put(source, nodeTitle.getNodeValue());
			}

			Map<String, String> pagesToAdd = new HashMap<String, String>();
			Map<String, String> pagesToUpdate = new HashMap<String, String>();

			Map<String, String> pagesWr = projectWr.getPages();
			for (String source : pagesWr.keySet()) {
				if (pagesPw.containsKey(source)) {
					if (!pagesPw.get(source).equals(pagesWr.get(source))) {
						pagesToUpdate.put(source, pagesWr.get(source));
					}
					pagesPw.remove(source);
				} else {
					pagesToAdd.put(source, pagesWr.get(source));
				}
			}

			for (String source : pagesPw.keySet()) {
				Node node = nodes.get(source);
				node.getParentNode().removeChild(node);
			}

			for (String source : pagesToAdd.keySet()) {
				Element node = doc.createElement("plweb:Page");
				node.setAttribute("title", pagesToAdd.get(source));
				node.setAttribute("source", source);
				if (!doc.getDocumentElement().getNodeName().equals("xmi:XMI")) {
					updatePlwebRootElement(doc);
				}
				doc.getDocumentElement().appendChild(node);
			}

			for (String source : pagesToUpdate.keySet()) {
				Element node = (Element) nodes.get(source);
				node.setAttribute("title", pagesToUpdate.get(source));
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
					"{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult sr = new StreamResult(project);
			transformer.transform(domSource, sr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Map<String, String> getPages() {
		File project = new File(path.getAbsoluteFile() + "/" + name
				+ PLWEB_EXTENSION);
		
		if (!project.exists()) {
			return new HashMap<String, String>();
		}
		
		PlwebPagesParser pagesParser = new PlwebPagesParser();
		try {
			getParser().parse(project, pagesParser);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return pagesParser.getPagesMap();
	}
	
	protected void updatePageSources(Map<String, String> pagesToUpdate) {
		try {
			int newPagesCounter = 0;
			File project = new File(path.getAbsoluteFile() + "/" + name
					+ PLWEB_EXTENSION);
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(project);
			Map<String, Node> nodes = new HashMap<String, Node>();
			NodeList notLinkedNodes = doc.getElementsByTagName("plweb:Page");
			
			for (int i = 0; i < notLinkedNodes.getLength(); i++) {
				Node node = notLinkedNodes.item(i);
				NamedNodeMap attrs = node.getAttributes();
				Node nodeSource = attrs.getNamedItem("source");
				String source = null; 
				if (nodeSource == null) {
					source = PlwebPagesParser.NEW_PAGE_PREFIX + (++newPagesCounter);
				} else {
					source = nodeSource.getNodeValue();
				}
				nodes.put(source, node);
			}
			NodeList linkedNodes = doc.getElementsByTagName("class");
			for (int i = 0; i < linkedNodes.getLength(); i++) {
				Node node = linkedNodes.item(i);
				NamedNodeMap attrs = node.getAttributes();
				Node nodeType = attrs.getNamedItem("xsi:type");
				Node nodeSource = attrs.getNamedItem("source");
				if (nodeType != null && nodeType.getNodeValue().equals("plweb:Page")) {
					String source = null;
					if (nodeSource == null) {
						source = PlwebPagesParser.NEW_PAGE_PREFIX + (++newPagesCounter);
					} else {
						source = nodeSource.getNodeValue();
					}
					nodes.put(source, node);
				}
			}
			
			for (String source : pagesToUpdate.keySet()) {
				if (nodes.containsKey(source)) {
					Element node = (Element) nodes.get(source);
					node.setAttribute("source", pagesToUpdate.get(source));
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
					"{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult sr = new StreamResult(project);
			transformer.transform(domSource, sr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updatePlwebRootElement(Document doc) {
		Element root = doc.createElement("xmi:XMI");
		root.setAttribute("xmi:version", "2.0");
		root.setAttribute("xmlns:xmi", "http://www.omg.org/XMI");
		root.setAttribute("xmlns:plweb", "http://plweb/1.0");
		root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		
		Element oldRoot = doc.getDocumentElement();
		oldRoot.removeAttribute("xmlns:xmi");
		oldRoot.removeAttribute("xmlns:plweb");
		oldRoot.removeAttribute("xmlns:xsi");
		doc.removeChild(oldRoot);
		doc.appendChild(root);
		root.appendChild(oldRoot);
	}
}
