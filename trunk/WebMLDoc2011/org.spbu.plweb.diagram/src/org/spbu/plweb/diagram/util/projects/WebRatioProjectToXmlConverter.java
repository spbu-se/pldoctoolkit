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
import org.spbu.plweb.diagram.util.projects.parts.Area;
import org.spbu.plweb.diagram.util.projects.parts.Page;
import org.spbu.plweb.diagram.util.projects.parts.Root;
import org.spbu.plweb.diagram.util.projects.parts.SiteView;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WebRatioProjectToXmlConverter {

	public static Document convert(final String projectPath,final String docPath,  
			final Root webRatioProjectParts) {
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

		Element diagramRoot = doc.createElement("plweb:DiagramRoot");
		diagramRoot.setAttribute("projectPath",
				PathHelper.normalize(projectPath));
		diagramRoot.setAttribute("docPath",
				PathHelper.normalize(docPath));
		diagramRoot.setAttribute("xmi:version", "1.0");
		diagramRoot.setAttribute("xmlns:xmi", "http://www.omg.org/XMI");
		diagramRoot.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		diagramRoot.setAttribute("xmlns:plweb", "http://plweb/2.0");
		doc.appendChild(diagramRoot);

		Element root = doc.createElement("root");
		root.setAttribute("title", webRatioProjectParts.getTitle());
		diagramRoot.appendChild(root);

		createSiteViewNodes(webRatioProjectParts, doc, root);

		return doc;
	}

	private static void createSiteViewNodes(final Root webRatioProjectParts,
			Document doc, Element root) {
		int npp = 0;
		for (final SiteView siteView : webRatioProjectParts.getSiteViews()) {
			root.appendChild(createSiteViewNode(doc, "//@root", siteView, npp++));
		}
	}

	private static Node createSiteViewNode(final Document doc,
			final String parent, final SiteView siteView, final int npp) {
		final Element siteViewElement = doc.createElement("class");
		setTitle(siteViewElement, siteView.getTitle());
		setXsiType(siteViewElement, SiteView.PLWEB_TYPE);
		setParent(siteViewElement, parent);
		int areaNpp = 0;
		for (final Area area : siteView.getAreas()) {
			siteViewElement.appendChild(createAreaNode(doc, buildParentPath(parent, npp), areaNpp, area));
			areaNpp++;
		}
		for (final Page page: siteView.getPages()){
			siteViewElement.appendChild(createPageNode(doc, buildParentPath(parent, npp), page));
		}
		return siteViewElement;
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

	private static Node createPageNode(final Document doc, final String parent,
			final Page page) {
		final Element pageElement = doc.createElement("class");
		setTitle(pageElement, page.getTitle());
		pageElement.setAttribute("source", page.getSource());
		setParent(pageElement, parent);
		setXsiType(pageElement, Page.PLWEB_TYPE);
		return pageElement;
	}

	private static String buildParentPath(final String parent, final int npp) {
		return parent + "/@class." + npp;
	}

	private static Node createAreaNode(final Document doc, final String parent, 
			final int areaNpp, final Area area) {
		final Element areaElement = doc.createElement("class");
		setTitle(areaElement, area.getTitle());
		setXsiType(areaElement, Area.PLWEB_TYPE);
		setParent(areaElement, parent);
		for (final Page page: area.getPages()) {
			areaElement.appendChild(createPageNode(doc, buildParentPath(parent, areaNpp), page));
		}
		return areaElement;
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
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult sr = new StreamResult(new File(path));
			transformer.transform(domSource, sr);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static void convertAndWrite(final String projectPath, final String docPath, 
			final Root webRatioProjectParts, 
			final String pathToWrite) {
		writeXml(convert(projectPath, docPath, webRatioProjectParts), pathToWrite);
	}
	
}
