package org.spbu.plweb.diagram.part.update;

import java.io.File;
import java.util.Iterator;
import java.util.List;
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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.spbu.plweb.DiagramRoot;
import org.spbu.plweb.DiagramType;
import org.spbu.plweb.diagram.part.Messages;
import org.spbu.plweb.diagram.part.PlwebDiagramEditorPlugin;
import org.spbu.plweb.diagram.util.PathHelper;
import org.spbu.plweb.diagram.util.ProjectSynchronizer;
import org.spbu.plweb.diagram.util.projects.ProjectOperationException;
import org.spbu.plweb.diagram.util.projects.UpdatePlwebProjectToXmlConverter;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.PartsReader;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.PlwebPartsReader;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.Root;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.TitleAware;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UpdateDiagramAction implements IObjectActionDelegate {
	private IWorkbenchPart targetPart;

	private URI domainModelURI;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		domainModelURI = null;
		action.setEnabled(false);
		if (selection instanceof IStructuredSelection == false
				|| selection.isEmpty()) {
			return;
		}
		IFile file = (IFile) ((IStructuredSelection) selection)
				.getFirstElement();
		domainModelURI = URI.createPlatformResourceURI(file.getFullPath()
				.toString(), true);
		action.setEnabled(true);
	}

	private Shell getShell() {
		return targetPart.getSite().getShell();
	}

	private DiagramRoot getElement(URI uri,
			TransactionalEditingDomain editingDomain) {
		ResourceSet resourceSet = editingDomain.getResourceSet();
		EObject element = null;
		try {
			Resource resource = resourceSet.getResource(uri, true);
			element = (EObject) resource.getContents().get(0);
		} catch (WrappedException ex) {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"Unable to load resource: " + uri, ex); //$NON-NLS-1$
		}
		if (element == null || !(element instanceof DiagramRoot)) {
			MessageDialog.openError(getShell(),
					Messages.AddProduct_ResourceErrorDialogTitle,
					Messages.AddProduct_ResourceErrorDialogMessage);
			return null;
		}
		DiagramRoot diagramRoot = (DiagramRoot) element;
		return diagramRoot;
	}

	public void run(IAction action) {
		try {
			TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE
					.createEditingDomain();

			DiagramRoot diagramRoot = getElement(domainModelURI, editingDomain);

			String resourcePath = domainModelURI.toPlatformString(true);
			String projectName = resourcePath.substring(1,
					resourcePath.indexOf("/", 1));
			String pathPw = PathHelper.getWorkspaceProjectPath(projectName);
			String docPath = diagramRoot.getDocPath();
			System.out.println("pathDoc: " + docPath);
			String pathWr = diagramRoot.getProjectPath();
			System.out.println("ннннннннннннннннннннн");
			System.out.println("pathWr: " + pathWr);
			pathPw = pathPw + "/" + projectName + ".plweb";
			System.out.println("pathPw: " + pathPw);
			File fileWR = new File(pathWr);
			File filePW = new File(pathPw);
			File fileLog = new File(
					"D:/KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK/AcmeLOGTEST.xml");
			long lastModTime = fileLog.lastModified();
			if (fileLog.exists())
				fileLog.delete();
			Root root = PartsReader.readParts(fileWR, lastModTime); // WR!!!
			System.out.println(root);
			// String SOURCE_PATH2 =
			// "D:\\KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK\\runtime-EclipseApplication\\Acme\\Acme.plweb";
			// String SOURCE_PATH2 =
			// "D:/KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK/Acme/Acme.plweb";
			Root rootPlweb = PlwebPartsReader.readParts(filePW); // plweb
			Map<String, TitleAware> mapSV = PartsReader.mapSiteViewPlwebTree;
			Map<String, TitleAware> mapArea = PartsReader.mapAreaPlwebTree;
			Map<String, TitleAware> mapPage = PartsReader.mapPagePlwebTree;
			System.out.println(PlwebPartsReader.check(rootPlweb, root, mapSV,
					mapArea, mapPage));
			// System.out.println(UpdatePlwebProjectToXmlConverter.convert(pathWr,
			// rootPlweb));

			UpdatePlwebProjectToXmlConverter.convertAndWrite(pathWr, docPath,
					rootPlweb, pathPw);

			ResourcesPlugin.getWorkspace().getRoot().getProject(projectName)
					.refreshLocal(IResource.DEPTH_INFINITE, null);
			createLogFile(
					"D:/KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK/AcmeLOGTEST.xml",
					PlwebPartsReader.addedMap, PlwebPartsReader.changedMap,
					PlwebPartsReader.deletedMap);

		} catch (Exception e) {
			e.printStackTrace();
		}

		MessageDialog.openInformation(getShell(), "Done",
				"Operation completed successfully");
	}

	private void createLogFile(String logPath, Map<String, List<String>> added,
			Map<String, List<String>> changed, Map<String, List<String>> deleted) {
		File logFile = new File(logPath);
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

		Element logRoot = doc.createElement("ChangesLog");

		doc.appendChild(logRoot);

		Element addedElements = doc.createElement("addedElements");
		Element deletedElements = doc.createElement("deletedElements");
		Element changedElements = doc.createElement("changedElements");

		logRoot.appendChild(addedElements);
		logRoot.appendChild(deletedElements);
		logRoot.appendChild(changedElements);

		Iterator iteratorAdd = added.keySet().iterator();

		while (iteratorAdd.hasNext()) {
			String key = (String) iteratorAdd.next();
			Element addedElement = doc.createElement("addedElement");
			addedElement.setAttribute("title", key);
			addedElements.appendChild(addedElement);
			for (String topicName : added.get(key)) {
				Element topicElement = doc.createElement("topic");
				topicElement.setAttribute("name", topicName);
				addedElement.appendChild(topicElement);

			}
		}
		
		Iterator iteratorDel = deleted.keySet().iterator();
		
		while (iteratorDel.hasNext()) {
			String key = (String) iteratorDel.next();
			Element deletedElement = doc.createElement("deletedElement");
			deletedElement.setAttribute("title", key);
			deletedElements.appendChild(deletedElement);
			for (String topicName : deleted.get(key)) {
				Element topicElement = doc.createElement("topic");
				topicElement.setAttribute("name", topicName);
				deletedElement.appendChild(topicElement);
				
			}
		}
		
		Iterator iteratorChange = changed.keySet().iterator();
		
		while (iteratorChange.hasNext()) {
			String key = (String) iteratorChange.next();
			Element changedElement = doc.createElement("changedElement");
			changedElement.setAttribute("title", key);
			changedElements.appendChild(changedElement);
			for (String topicName : changed.get(key)) {
				Element topicElement = doc.createElement("topic");
				topicElement.setAttribute("name", topicName);
				changedElement.appendChild(topicElement);
				
			}
		}
//
//		for (String addedEl : added) {
//			Element addedElement = doc.createElement("addedElement");
//			addedElement.setAttribute("title", addedEl);
//			addedElements.appendChild(addedElement);
//		}
//		for (String delEl : deleted) {
//			Element delElement = doc.createElement("deletedElement");
//			delElement.setAttribute("title", delEl);
//			deletedElements.appendChild(delElement);
//		}
//		for (String changedEl : changed) {
//			Element changedElement = doc.createElement("changedElement");
//			changedElement.setAttribute("title", changedEl);
//			changedElements.appendChild(changedElement);
//		}

		writeXml(doc, logPath);

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

}
