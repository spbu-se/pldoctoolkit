package plweb.diagram.util.projects;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import plweb.diagram.util.PageLinkInfo;
import plweb.diagram.util.parsers.PageLinksParser;
import plweb.diagram.util.parsers.TitleGetterParser;

public class ProjectWebRatio extends ProjectAbstract {
	public final static String MODEL_FOLDER_PATH = "Model/WebModel";
	public final static String WEBRATIO_EXTENSION = ".wr";
	public final static String PROPERTIES_FILENAME = "Properties.wr";

	public ProjectWebRatio(String path) throws ProjectOperationException {
		if (path == null) {
			throw new ProjectOperationException(
					"Couldn't create ProjectWebRatio instance");
		}
		this.path = new File(path);
	}
	
	public boolean isValid() {
		try {
			if (path == null || path.getName().equals("")) {
				return false;
			}
			getDefaultAreaName();
		}  catch (ProjectOperationException e) {
			return false;
		}
		return true;
	}

	public void update(ProjectPlweb projectPw) throws ProjectOperationException {
		projectPw.setName(getProjectName());
		
		Map<String, String> pagesWr = getPages();
		Map<String, String> pagesToAdd = new HashMap<String, String>();
		Map<String, String> pagesToUpdate = new HashMap<String, String>();
		Map<String, String> pagesPw = projectPw.getPages();
		
		for (String source : pagesPw.keySet()) {
			if (pagesWr.containsKey(source)) {
				if (!pagesWr.get(source).equals(pagesPw.get(source))) {
					pagesToUpdate.put(source, pagesPw.get(source));
				}
				pagesWr.remove(source);
			} else {
				pagesToAdd.put(source, pagesPw.get(source));
			}
		}
		
		for (String source : pagesWr.keySet()) {
			File pageToDelete = new File(getAreaFolder() + "/" + source);
			pageToDelete.delete();
		}
		
		Map<String, String> pwPagesToUpdate = new HashMap<String, String>();
		for (String source : pagesToAdd.keySet()) {
			try {				
				String pageId = generateUniquePageId();
				File newPage = new File(getAreaFolder() + "/" + pageId + WEBRATIO_EXTENSION);
				newPage.createNewFile();
				
				DocumentBuilderFactory docFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				DOMImplementation domImpl = docBuilder.getDOMImplementation();
				Document doc = domImpl.createDocument(null, null, null);
				
				Element page = doc.createElement("Page");
				page.setAttribute("xmlns:gr", "http://www.webratio.com/2006/WebML/Graph");
				page.setAttribute("xmlns:layout", "http://www.webratio.com/2006/WebML/Layout");
				page.setAttribute("id", getDefaultId() + "#" + pageId);
				page.setAttribute("name", pagesToAdd.get(source));
				page.setAttribute("gr:x", "0");
				page.setAttribute("gr:y", "0");
				doc.appendChild(page);
				
				DOMSource domSource = new DOMSource(doc);
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer
						.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				transformer.setOutputProperty(OutputKeys.METHOD, "xml");
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.setOutputProperty(
						"{http://xml.apache.org/xslt}indent-amount", "4");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				StreamResult sr = new StreamResult(newPage);
				transformer.transform(domSource, sr);
				pwPagesToUpdate.put(source, newPage.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		projectPw.updatePageSources(pwPagesToUpdate);
		
		for (String source : pagesToUpdate.keySet()) {
			updatePageTitle(source, pagesToUpdate.get(source));
		}
		
	}

	public String getProjectName() {
		return path.getName();
	}
	
	public Map<String, String> getMissingPages(List<String> pages) {
		Map<String, String> requiredElements = new HashMap<String, String>();
		File areaFolder = getAreaFolder();
		String[] fileList = areaFolder.list();
		Map<String, PageLinkInfo> checkedPages = new HashMap<String, PageLinkInfo>();
		Map<String, PageLinkInfo> notCheckedPages = new HashMap<String, PageLinkInfo>();
		PageLinksParser parser = new PageLinksParser();
		for (String fileName : fileList) {
			if (!fileName.startsWith("page")) {
				continue;
			}
			try {
				getParser().parse(new File(areaFolder + "/" + fileName), parser);
				PageLinkInfo result = parser.getResult();
				if (pages.contains(fileName)) {
					checkedPages.put(result.getId(), result);
				} else {
					notCheckedPages.put(result.getId(), result);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}
		
		for (PageLinkInfo pageInfo : checkedPages.values()) {
			for (String id : pageInfo.getLinks()) {
				if (checkedPages.keySet().contains(id)) {
					continue;
				}
				if (notCheckedPages.keySet().contains(id)) {
					PageLinkInfo required =  notCheckedPages.get(id);
					requiredElements.put(required.getTitle(), pageInfo.getTitle());
				}
			}
		}
		return requiredElements;
	}
	
	protected Map<String, String> getPages() {
		HashMap<String, String> map = new HashMap<String, String>();
		File areaFolder = getAreaFolder();
		String[] fileList = areaFolder.list();
		for (String fileName : fileList) {
			if (fileName.startsWith("page")) {
				TitleGetterParser titleGetter = new TitleGetterParser();
				try {
					getParser().parse(new File(areaFolder + "/" + fileName),
							titleGetter);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				}

				String title = titleGetter.getTitle();
				map.put(fileName, title);
			}
		}
		return map;
	}
	
	protected String getDefaultId() throws ProjectOperationException {
		File modelFolder = new File(path.getAbsoluteFile() + "/"
				+ MODEL_FOLDER_PATH);
		File siteViewFolder = getFirstDirectory(modelFolder);
		File areaFolder = getFirstDirectory(siteViewFolder);
		return siteViewFolder.getName() + "#" + areaFolder.getName();
	}

	protected String getDefaultAreaName() throws ProjectOperationException {
		File propertiesFile = new File(getAreaFolder() + "/"
				+ PROPERTIES_FILENAME);
		if (!propertiesFile.exists()) {
			throw new ProjectOperationException(
					"Default area is missing properties file");
		}
		TitleGetterParser titleGetter = new TitleGetterParser();
		try {
			getParser().parse(propertiesFile, titleGetter);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return titleGetter.getTitle();
	}
	
	private String generateUniquePageId() {
		File areaFolder = getAreaFolder();
		int id = 1;
		String pageId = "page" + id;
		
		while((new File(areaFolder + "/" + pageId + WEBRATIO_EXTENSION)).exists()) {
			pageId = "page" + (++id);
		}
		return pageId;
	}
	
	private void updatePageTitle(String source, String title) {
		try {
			File file = new File(getAreaFolder() + "/" + source);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			Element root = doc.getDocumentElement();
			root.setAttribute("name", title);
			
			DOMSource domSource = new DOMSource(doc);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer
					.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult sr = new StreamResult(file);
			transformer.transform(domSource, sr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public File getAreaFolder() {
		File modelFolder = new File(path + "/"
				+ MODEL_FOLDER_PATH);
		File siteViewFolder = getFirstDirectory(modelFolder);
		File areaFolder = getFirstDirectory(siteViewFolder);
		return areaFolder;
	}

}
