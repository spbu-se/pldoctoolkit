package org.spbu.plweb.diagram.util.projects.partsPLWeb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static org.spbu.plweb.diagram.util.CollectionsUtils.newList;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.spbu.plweb.DiagramRoot;
import org.spbu.plweb.diagram.util.parsers.TitleGetterParser;
import org.spbu.plweb.diagram.util.parsers.docElementsGetterParser;
import org.spbu.plweb.diagram.util.projects.ProjectWebRatio;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DocTopicsPartsReader {

	public static List<String> listOfTopics = new ArrayList<String>();
	private static String nameFileElements = "Elements.drl";

	public static List<String> getListOfDocElements(String docPath) {
		getElements(new File(docPath + File.separator + nameFileElements));
		return listOfTopics;
	}

	public static List<String> getListOfTopics() {
		return listOfTopics;
	}
	private static String getElements(final File elementFile) {
		final docElementsGetterParser elementsGetter = new docElementsGetterParser();
		try {
			getParser().parse(elementFile, elementsGetter);
		} catch (IOException e) {
			return "Unable to parse title";
		} catch (SAXException e) {
			return "Unable to parse title";
		}
		listOfTopics = elementsGetter.getListOfTopics();
		return "ok";
	}

	private static SAXParser getParser() {
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			return factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
	}
}
