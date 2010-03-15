package plweb.util;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.management.RuntimeErrorException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

public class ImporterUtil {
	
	private static ImporterUtil instance = null;
	
	private String document = null;
	
	private String wrProjectPath = null;
	private String plwProjectPath = null;
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Wrong input");
			return;
		}
		
		String wrProjectPath = args[0];
		String plwProjectPath = args[1];
		
		instance = new ImporterUtil(wrProjectPath, plwProjectPath);
		instance.run();
	}
	
	private ImporterUtil(String wrPath, String plwPath) {
		this.wrProjectPath = wrPath;
		this.plwProjectPath = plwPath;
	}
	
	private void run() {
		checkInputProject();
		
		document = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		File modelFolder = new File(wrProjectPath + "/Model/WebModel");
		File siteViewFolder = getFirstDirectory(modelFolder);
		File areaFolder = getFirstDirectory(siteViewFolder);
		
		if (areaFolder == null) {
			return;
		}
		
		document += "<xmi:XMI xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:plweb=\"http://plweb/1.0\">";
		document += "<plweb:DiagramRoot>";
		
		String pages = "";
		String[] fileNameList = areaFolder.list();
		if (fileNameList != null) {
			for (String fileName: fileNameList) {
				if (fileName.startsWith("page")) {
					String title = getName(areaFolder + "/" + fileName);
					pages += "<plweb:Page title=\"" + title + "\"/>";
				} else if (fileName.equals("Properties.wr")){
					String title = getName(areaFolder + "/" + fileName);
					document += "<area title=\"" + title + "\"/>";
				}
			}
		}
		document += "</plweb:DiagramRoot>";
		document += pages;
		document += "</xmi:XMI>";
		
		outputDocument();
	}
	
	private void checkInputProject() {
		File wrProject = new File(wrProjectPath);
		File modelFile = new File(wrProject.getAbsoluteFile() + "/Model.wr");
		if (!modelFile.exists()) {
			throw new RuntimeException("WebRatio project not found");
		}
		File modelsFolderFile = new File(wrProject.getAbsoluteFile() + "/Model/WebModel");
		if (!modelsFolderFile.exists()) {
			throw new RuntimeException("WebRatio project is probably corrupted");
		}
	}
	
	private void outputDocument() {
		try {
			File plwProject = new File(plwProjectPath);
			if (plwProject.exists()) {
				plwProject.delete();
			}
			plwProject.createNewFile();
			
			FileWriter fileWriter = new FileWriter(plwProject);
			fileWriter.write(document);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private File getFirstDirectory(File directory) {
		if (directory == null) {
			return null;
		}
		String[] children = directory.list();
		if (children == null) {
			return null;
		}
		File firstDir = null;
		for (String child: children) {
			File childFile = new File(directory + "/" + child);
			if (childFile.isDirectory()) {
				firstDir = childFile;
				break;
			}
		}
		return firstDir;
	}
	
	private String getName(String pageFileName) {
		try {
			File file = new File(pageFileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			return doc.getDocumentElement().getAttribute("name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Invalid page";
	}
}
