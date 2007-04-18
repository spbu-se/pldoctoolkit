package org.spbu.pldoctoolkit.registry;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Status;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class ProjectRegistryImpl implements ProjectRegistry {
	private static final String DRLRESOLVE_PREFIX = "drlresolve://";
	private static final String CORE = "Core";

	private static final String INF_ELEMENT = "InfElement";
	private static final String INF_PRODUCT = "InfProduct";
	private static final String DICTIONARY = "Dictionary";

	private static final String DOCUMENTATION_CORE = "DocumentationCore";
	private static final String PRODUCT_DOCUMENTATION = "ProductDocumentation";
	private static final String ID_ATTRIBUTE = "id";

	private final Map<String, RegisteredLocation> locationMap = new HashMap<String, RegisteredLocation>();
	private DocumentBuilder documentBuilder;

	private Document getXMLDocument(IFile file) throws ParserConfigurationException, SAXException, IOException {
		if (documentBuilder == null)
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return documentBuilder.parse(file.getLocationURI().toString());
	}

	public RegisteredLocation getRegisteredLocation(String uri) {
		if (!uri.startsWith(DRLRESOLVE_PREFIX))
			return null;
		String path = uri.substring(DRLRESOLVE_PREFIX.length());
		String[] elements = path.split("/");
		
		if (elements.length != 3)
			return null;
		
		if (CORE.equals(elements[0]))
			return locationMap.get(path);
		
		RegisteredLocation loc = locationMap.get(path);
		if (loc != null)
			return loc;
		
		return locationMap.get(CORE + "/" + elements[1] + "/" + elements[2]);
	}

	void refreshFile(IFile file) throws CoreException {
		String ext = file.getFileExtension();
		if (ext != null && ext.toLowerCase().equals(PLDocToolkitPlugin.DRL_FILE_EXTENSION)) {
			try {
				Document doc = getXMLDocument(file);
				Node node = doc.getChildNodes().item(0);
				if (node == null)
					return;
				String nodeName = node.getNodeName();
				if (node.getNodeType() != Node.ELEMENT_NODE)
					return;
				if (DOCUMENTATION_CORE.equals(nodeName)) {
					NodeList list = node.getChildNodes();
					for (int i = 0; i < list.getLength(); i++) {
						Node child = list.item(i);
						if (child.getNodeType() != Node.ELEMENT_NODE)
							continue;
						String childName = node.getNodeName();
						if (!INF_PRODUCT.equals(childName) && !INF_ELEMENT.equals(childName) && !DICTIONARY.equals(childName))
							continue;
						register(CORE, child, file);
					}
				} else if (PRODUCT_DOCUMENTATION.equals(nodeName)) {
					Node idAttribute = node.getAttributes().getNamedItem(ID_ATTRIBUTE);
					if (idAttribute == null)
						return;
					String id = idAttribute.getNodeValue();
					NodeList list = node.getChildNodes();
					for (int i = 0; i < list.getLength(); i++) {
						Node child = list.item(i);
						if (child.getNodeType() != Node.ELEMENT_NODE)
							continue;
						String childName = node.getNodeName();
						if (!DICTIONARY.equals(childName))
							continue;
						register(id, child, file);
					}
				}
			} catch (ParserConfigurationException e) {
				throw new CoreException(new Status(Status.ERROR, PLDocToolkitPlugin.PLUGIN_ID, 0, "Parser configuration error", e));
			} catch (SAXException e) {
				// ignore
			} catch (IOException e) {
				// ignore
			}
		}
	}

	private void register(String context, Node node, IFile file) {
		Node idAttribute = node.getAttributes().getNamedItem(ID_ATTRIBUTE);
		if (idAttribute == null)
			return;
		RegisteredLocation loc = new RegisteredLocation(file, 0); // TODO: We have to get lineNumber somehow
		locationMap.put(context + "/" + node.getNodeName() + "/" + idAttribute.getNodeValue(), loc);
	}
	
	void refreshContainer(IContainer container) throws CoreException {
		IPath location = container.getLocation();
		for (Iterator<RegisteredLocation> it = locationMap.values().iterator(); it.hasNext();) {
			IFile file = it.next().getFile();
			if (location.isPrefixOf(file.getLocation()))
				it.remove();
		}
		container.accept(new IResourceVisitor() {
			public boolean visit(IResource resource) throws CoreException {
				if (resource instanceof IFile) {
					refreshFile((IFile) resource);
					return false;
				}
				return true;
			}
		});
	}
}
