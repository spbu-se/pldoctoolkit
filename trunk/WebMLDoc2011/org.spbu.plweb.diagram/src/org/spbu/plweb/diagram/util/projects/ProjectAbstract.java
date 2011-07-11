package org.spbu.plweb.diagram.util.projects;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

abstract public class ProjectAbstract {
	
	protected File path = null;

	protected SAXParser parser = null;
	
	public boolean exists() {
		return path != null && path.exists() && path.isDirectory();
	}
	
	public boolean isValid() {
		return true;
	}
	
	protected File getPath() {
		return path;
	}
	

	protected SAXParser getParser() {
		if (parser == null) {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			try {
				parser = factory.newSAXParser();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}
		return parser;
	}

	protected File getFirstDirectory(File directory) {
		if (directory == null) {
			return null;
		}
		String[] children = directory.list();
		if (children == null) {
			return null;
		}
		File firstDir = null;
		for (String child : children) {
			File childFile = new File(directory + "/" + child);
			if (childFile.isDirectory()) {
				firstDir = childFile;
				break;
			}
		}
		return firstDir;
	}
}
