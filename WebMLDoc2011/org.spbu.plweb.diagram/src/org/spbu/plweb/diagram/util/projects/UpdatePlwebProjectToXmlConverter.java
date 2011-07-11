package org.spbu.plweb.diagram.util.projects;

import java.io.File;
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

import org.spbu.plweb.diagram.util.PathHelper;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.Area;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.DocTopic;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.Page;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.Root;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.SiteView;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.Node;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.Group;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//import org.w3c.dom.Node;

public class UpdatePlwebProjectToXmlConverter {

	public static Document convert(final String projectPath,final String docPath,
			final Root updateProjectParts) {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		DOMImplementation domImpl = docBuilder.getDOMImplementation();
		Document doc = domImpl.createDocument(null, null, null);
		doc.setXmlStandalone(true);

		Element diagramRoot = doc.createElement("plweb:DiagramRoot");
		diagramRoot.setAttribute("projectPath",
				PathHelper.normalize(projectPath));
		diagramRoot.setAttribute("docPath",
				PathHelper.normalize(docPath));
		diagramRoot.setAttribute("xmi:version", "1.0");
		diagramRoot.setAttribute("xmlns:xmi", "http://www.omg.org/XMI");
		diagramRoot.setAttribute("xmlns:xsi",
				"http://www.w3.org/2001/XMLSchema-instance");
		diagramRoot.setAttribute("xmlns:plweb", "http://plweb/2.0");
		doc.appendChild(diagramRoot);

		Element root = doc.createElement("root");
		root.setAttribute("title", updateProjectParts.getTitle());
		diagramRoot.appendChild(root);

		createSiteViewNodes(updateProjectParts, doc, root);
		createNodeNodes(updateProjectParts, doc, root);
		createGroupNodes(updateProjectParts, doc, root);

		return doc;
	}

	private static void createSiteViewNodes(final Root updateProjectParts,
			Document doc, Element root) {
		int npp = 0;
		for (final SiteView siteView : updateProjectParts.getSiteViews()) {
			root.appendChild(createSiteViewNode(doc, "//@root", siteView, npp++));
		}
	}

	private static void createNodeNodes(final Root updateProjectParts,
			Document doc, Element root) {
		int npp = 0;
		for (final Node node : updateProjectParts.getNodes()) {
			root.appendChild(createNodeNode(doc, "//@root", node, npp++));
		}
	}

	private static void createGroupNodes(final Root updateProjectParts,
			Document doc, Element root) {
		int npp = 0;
		for (final Group group : updateProjectParts.getGroups()) {
			root.appendChild(createGroupNode(doc, "//@root", group, npp++));
		}
	}

	private static org.w3c.dom.Node createSiteViewNode(final Document doc,
			final String parent, final SiteView siteView, final int npp) {
		final Element siteViewElement = doc.createElement("class");
		setTitle(siteViewElement, siteView.getTitle());
		setXsiType(siteViewElement, SiteView.PLWEB_TYPE);
		setParent(siteViewElement, parent);
		int elementNpp = 0;
		for (final Area area : siteView.getAreas()) {
			siteViewElement.appendChild(createAreaNode(doc,
					buildParentPath(parent, npp), area, elementNpp));
			elementNpp++;
		}
		for (final Page page : siteView.getPages()) {
			siteViewElement.appendChild(createPageNode(doc,
					buildParentPath(parent, npp), page));
		}
		for (final Node node : siteView.getNodes()) {
			siteViewElement.appendChild(createNodeNode(doc,
					buildParentPath(parent, npp), node, elementNpp));
			elementNpp++;
		}
		for (final Group group : siteView.getGroups()) {
			siteViewElement.appendChild(createGroupNode(doc,
					buildParentPath(parent, npp), group, elementNpp));
			elementNpp++;
		}
		for (final DocTopic dtopic : siteView.getTopics()) {
			siteViewElement.appendChild(createDocTopicNode(doc, dtopic));
		}
		return siteViewElement;
	}

	private static org.w3c.dom.Node createDocTopicNode(Document doc,
			DocTopic dtopic) {
		final Element docTopicElement = doc.createElement("docTopic");
		docTopicElement.setAttribute("docTopicName", dtopic.getName());
		return docTopicElement;
	}

	private static org.w3c.dom.Node createAreaNode(final Document doc,
			final String parent, final Area area, final int areaNpp) {
		final Element areaElement = doc.createElement("class");
		setTitle(areaElement, area.getTitle());
		setXsiType(areaElement, Area.PLWEB_TYPE);
		setParent(areaElement, parent);
		int elementNpp = 0;
		for (final Node node : area.getNodes()) {
			areaElement.appendChild(createNodeNode(doc,
					buildParentPath(parent, areaNpp), node, elementNpp));
			elementNpp++;
		}
		for (final Group group : area.getGroups()) {
			areaElement.appendChild(createGroupNode(doc,
					buildParentPath(parent, areaNpp), group, elementNpp));
			elementNpp++;
		}
		for (final Page page : area.getPages()) {
			areaElement.appendChild(createPageNode(doc,
					buildParentPath(parent, areaNpp), page));
		}
		for (final DocTopic dtopic : area.getTopics()) {
			areaElement.appendChild(createDocTopicNode(doc, dtopic));
		}
		return areaElement;
	}

	private static org.w3c.dom.Node createNodeNode(final Document doc,
			final String parent, final Node newNode, final int npp) {
		final Element nodeElement = doc.createElement("class");
		setTitle(nodeElement, newNode.getTitle());
		setXsiType(nodeElement, Node.PLWEB_TYPE);
		setParent(nodeElement, parent);
		int elementNpp = 0;
		for (final SiteView sw : newNode.getSiteViews()) {
			nodeElement.appendChild(createSiteViewNode(doc,
					buildParentPath(parent, npp), sw, elementNpp));
			elementNpp++;
		}
		for (final Area area : newNode.getAreas()) {
			nodeElement.appendChild(createAreaNode(doc,
					buildParentPath(parent, npp), area, elementNpp));
			elementNpp++;
		}
		for (final Page page : newNode.getPages()) {
			nodeElement.appendChild(createPageNode(doc,
					buildParentPath(parent, npp), page));
		}
		for (final Node node : newNode.getNodes()) {
			nodeElement.appendChild(createNodeNode(doc,
					buildParentPath(parent, npp), node, elementNpp));
			elementNpp++;
		}
		for (final Group group : newNode.getGroups()) {
			nodeElement.appendChild(createGroupNode(doc,
					buildParentPath(parent, npp), group, elementNpp));
			elementNpp++;
		}
		for (final DocTopic dtopic : newNode.getTopics()) {
			nodeElement.appendChild(createDocTopicNode(doc, dtopic));
		}

		return nodeElement;
	}

	private static org.w3c.dom.Node createGroupNode(final Document doc,
			final String parent, final Group group, final int npp) {
		final Element groupElement = doc.createElement("class");
		setTitle(groupElement, group.getTitle());
		setXsiType(groupElement, Group.PLWEB_TYPE);
		setParent(groupElement, parent);
		int elementNpp = 0;
		for (final SiteView sw : group.getSiteViews()) {
			groupElement.appendChild(createSiteViewNode(doc,
					buildParentPath(parent, npp), sw, elementNpp));
			elementNpp++;
		}
		for (final Area area : group.getAreas()) {
			groupElement.appendChild(createAreaNode(doc,
					buildParentPath(parent, npp), area, elementNpp));
			elementNpp++;
		}
		for (final Page page : group.getPages()) {
			groupElement.appendChild(createPageNode(doc,
					buildParentPath(parent, npp), page));
		}
		for (final Node node : group.getNodes()) {
			groupElement.appendChild(createNodeNode(doc,
					buildParentPath(parent, npp), node, elementNpp));
			elementNpp++;
		}
		for (final Group groupn : group.getGroups()) {
			groupElement.appendChild(createGroupNode(doc,
					buildParentPath(parent, npp), groupn, elementNpp));
			elementNpp++;
		}
		for (final DocTopic dtopic : group.getTopics()) {
			groupElement.appendChild(createDocTopicNode(doc, dtopic));
		}
		return groupElement;
	}

	private static void setTitle(final Element element, final String title) {
		element.setAttribute("title", title);
	}

	private static void setXsiType(final Element element, final String xsiType) {
		element.setAttribute("xsi:type", xsiType);
	}

	private static void setParent(final Element element, final String parent) {
		element.setAttribute("parent", parent);
	}

	private static org.w3c.dom.Node createPageNode(final Document doc,
			final String parent, final Page page) {
		final Element pageElement = doc.createElement("class");
		setTitle(pageElement, page.getTitle());
		pageElement.setAttribute("source", page.getSource());
		setParent(pageElement, parent);
		setXsiType(pageElement, Page.PLWEB_TYPE);
		for (final DocTopic dtopic : page.getTopics()) {
			pageElement.appendChild(createDocTopicNode(doc, dtopic));
		}
		return pageElement;
	}

	private static String buildParentPath(final String parent, final int npp) {
		return parent + "/@class." + npp;
	}

	public static void writeXml(final Document doc, final String path) {
		try {
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
			StreamResult sr = new StreamResult(new File(path));
			transformer.transform(domSource, sr);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	public static void convertAndWrite(final String projectPath, final String docPath,
			final Root webRatioProjectParts, final String pathToWrite) {
		writeXml(convert(projectPath,docPath, webRatioProjectParts), pathToWrite);
	}

}
