package org.spbu.publishutil.utils;


import static org.spbu.publishutil.utils.RegisteredLocation.CORE_CONTEXT;
import static org.spbu.publishutil.utils.RegisteredLocation.DICTIONARY;
import static org.spbu.publishutil.utils.RegisteredLocation.DIRECTORY;
import static org.spbu.publishutil.utils.RegisteredLocation.DIRTEMPLATE;
import static org.spbu.publishutil.utils.RegisteredLocation.FINAL_INF_PRODUCT;
import static org.spbu.publishutil.utils.RegisteredLocation.INF_ELEMENT;
import static org.spbu.publishutil.utils.RegisteredLocation.INF_ELEM_REF;
import static org.spbu.publishutil.utils.RegisteredLocation.INF_ELEM_REF_GROUP;
import static org.spbu.publishutil.utils.RegisteredLocation.INF_PRODUCT;
import static org.spbu.publishutil.utils.RegisteredLocation.NEST_POINT;
import static org.spbu.publishutil.utils.RegisteredLocation.PRODUCT;
import static org.spbu.publishutil.utils.RegisteredLocation.PRODUCT_CONTEXT;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.saxon.Configuration;
import net.sf.saxon.dom.DocumentBuilderFactoryImpl;
import net.sf.saxon.dom.DocumentBuilderImpl;
import net.sf.saxon.dom.NodeOverNodeInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ProjectRegistryImpl implements ProjectRegistry {
	private static final String DRLRESOLVE_PREFIX = "drlresolve://";
	
	private static final String DOCUMENTATION_CORE = "DocumentationCore";
	private static final String PRODUCT_DOCUMENTATION = "ProductDocumentation";
	private static final String PRODUCT_LINE = "ProductLine";
	
	private static final String ID_ATTRIBUTE = "id";
	private static final String NAME_ATTRIBUTE = "name";
	private static final String PRODUCTID_ATTRIBUTE = "productid";
	
	private static final int TOP_LEVEL = 0;

	private static DocumentBuilderImpl documentBuilder;
	
	private final Map<String, RegisteredLocation> locationMap = new HashMap<String, RegisteredLocation>();
	
	// === Public API ===
	
	public RegisteredLocation getRegisteredLocation(String uri) {
		if (!uri.startsWith(DRLRESOLVE_PREFIX))
			return null;
		String path = uri.substring(DRLRESOLVE_PREFIX.length());
		RegisteredLocation loc = locationMap.get(path);
		if (loc != null)
			return loc;
		String[] elements = path.split("/");
		if (elements.length != 3 || CORE_CONTEXT.equals(elements[0]))
			return null;
		return locationMap.get(CORE_CONTEXT + "/" + elements[1] + "/" + elements[2]);
	}

	public List<RegisteredLocation> findForFile(File file) {
		List<RegisteredLocation> result = new ArrayList<RegisteredLocation>();
		for (RegisteredLocation loc: locationMap.values())
			if (loc.getFile().equals(file))
				result.add(loc);
		return result;
	}
	
	public void registerDirectory(File dir) throws Exception {
		if (!dir.isDirectory())
			return;
		for (File file: dir.listFiles()) {
			if (file.isDirectory())
				registerDirectory(file);
			else 
				registerFile(file);
		}
	}
	
	// === End of public API ===
	
	private Document getXMLDocument(File file) throws ParserConfigurationException, SAXException, IOException {
		if (documentBuilder == null) {
			DocumentBuilderFactoryImpl factory = (DocumentBuilderFactoryImpl)DocumentBuilderFactoryImpl.newInstance();
			factory.setNamespaceAware(true);
			documentBuilder = (DocumentBuilderImpl) factory.newDocumentBuilder();
		}
		
		Configuration config = new Configuration();
		config.setLineNumbering(true);
		documentBuilder.setConfiguration(config);

		return documentBuilder.parse(file.toURI().toString());
	}
	
	private void register(String context, Node node, File file) {
		Node idAttribute = node.getAttributes().getNamedItem(ID_ATTRIBUTE);
		Node nameAttribute = node.getAttributes().getNamedItem(NAME_ATTRIBUTE);
		if (idAttribute == null)
			return;
		String type = node.getLocalName();
		String id = idAttribute.getNodeValue();
		String name = nameAttribute == null ? id : nameAttribute.getNodeValue();
		int lineNumber = ((NodeOverNodeInfo) node).getUnderlyingNodeInfo().getLineNumber();
		
		RegisteredLocation loc = new RegisteredLocation(context, type, id, name, file, lineNumber); 
		locationMap.put(context + "/" + type + "/" + id, loc);
	}
	
	private void registerFile(File file) throws Exception {
		String ext = (file.getName().indexOf(".")==-1) ? "" : 
			file.getName().substring(file.getName().lastIndexOf(".")+1,file.getName().length());
		if (!ext.toLowerCase().equals(UtilConstants.DRL_FILE_EXTENSION))
			return;
		
		try {
			Document doc = getXMLDocument(file);
			NodeList rootList = doc.getChildNodes();
			Node rootNode = null;
			for (int i = 0; i < rootList.getLength(); i++) {
				Node node = rootList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					rootNode = node;
					break;
				}
			}
			if (rootNode == null)
				return;
			String nodeName = rootNode.getLocalName();
			if (DOCUMENTATION_CORE.equals(nodeName) || PRODUCT_LINE.equals(nodeName)) {
				List<Node> nodes = getCoreChildElementsToRegister(rootNode);
				for (Node node: nodes) {
					register(CORE_CONTEXT, node, file);
				}
			} else if (PRODUCT_DOCUMENTATION.equals(nodeName)) {
				Node idAttribute = rootNode.getAttributes().getNamedItem(PRODUCTID_ATTRIBUTE);
				if (idAttribute == null)
					return;
				String id = idAttribute.getNodeValue();
				NodeList list = rootNode.getChildNodes();
				for (int i = 0; i < list.getLength(); i++) {
					Node child = list.item(i);
					if (child.getNodeType() != Node.ELEMENT_NODE)
						continue;
					String childName = child.getLocalName();
					if (!DICTIONARY.equals(childName) && 
						!DIRECTORY.equals(childName) &&
						!DIRTEMPLATE.equals(childName) &&
						!FINAL_INF_PRODUCT.equals(childName))
						continue;
					// Adding a prefix to avoid conflicts in case of CORE_CONTEXT.equals(id)
					register(PRODUCT_CONTEXT + id, child, file);
				}
			}
		} catch (SAXException e) {
			// ignore
		} catch (IOException e) {
			// ignore
		}
	}
	
	private List<Node> getCoreChildElementsToRegister(Node rootNode) {
		return getCoreChildElementsToRegister(rootNode, TOP_LEVEL);
	}
	
	private List<Node> getCoreChildElementsToRegister(Node rootNode, int level) {
		List<Node> nodes = new ArrayList<Node>();
		
		NodeList list = rootNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node child = list.item(i);
			if (child.getNodeType() != Node.ELEMENT_NODE)
				continue;
			String childName = child.getLocalName();
			if ((INF_PRODUCT.equals(childName) || 
				 INF_ELEMENT.equals(childName) ||
				 DICTIONARY.equals(childName)  ||
				 PRODUCT.equals(childName)     ||
				 DIRECTORY.equals(childName)   ||
				 DIRTEMPLATE.equals(childName)   ) && (level == TOP_LEVEL)
				 ||
				 INF_ELEM_REF_GROUP.equals(childName) ||
				 INF_ELEM_REF.equals(childName) ||
				 NEST_POINT.equals(childName)) 
			nodes.add(child);
			
			nodes.addAll(getCoreChildElementsToRegister(child, level+1));
		}
		return nodes;
	}
}
