package org.spbu.plweb.diagram.util.projects.partsPLWeb;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static org.spbu.plweb.diagram.util.CollectionsUtils.newList;

import org.spbu.plweb.diagram.util.parsers.TitleGetterParser;
import org.spbu.plweb.diagram.util.projects.ProjectWebRatio;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PartsReader {
	
	public static long lastModDate;
	
	public static List<String> changedList;
	
	public static Map<String, TitleAware> mapAreaPlwebTree = new HashMap<String, TitleAware>();
	public static Map<String, TitleAware> mapSiteViewPlwebTree = new HashMap<String, TitleAware>();
	public static Map<String, TitleAware> mapPagePlwebTree = new HashMap<String, TitleAware>();
	
	private static String nameFileProperties = "Properties.wr";

//	public static Root readParts(final File baseDir) {
//		final File rootDir = new File(baseDir + File.separator
//				+ ProjectWebRatio.MODEL_FOLDER_PATH);
//		return new Root(baseDir.getName(), readSiteViews(rootDir), readNodes(rootDir), readGroups(rootDir));
//	}
	
	public static Root readParts(final File baseDir, long lastMod) {
		changedList = newList();
		lastModDate = lastMod;
		mapAreaPlwebTree.clear();
		mapPagePlwebTree.clear();
		mapSiteViewPlwebTree.clear();
		final File rootDir = new File(baseDir + File.separator
				+ ProjectWebRatio.MODEL_FOLDER_PATH);
		return new Root(baseDir.getName(), readSiteViews(rootDir), readNodes(rootDir), readGroups(rootDir));
	}

	private static List<SiteView> readSiteViews(final File path) {
		final List<SiteView> result = newList();
		for (final File file : path.listFiles()) {
			if (file.isDirectory() && isRealSiteView(file)) {
				result.add(createSiteView(file));
				
			}
		}
		return result;
	}
		private static List<Area> readAreas(final File path) {
		final List<Area> result = newList();
		for (final File file : path.listFiles()) {
			if (file.isDirectory() && isRealArea(file)) {
				result.add(createArea(file));
			}
		}
		return result;
	}	
		private static List<Page> readPages(final File path) {
		final List<Page> result = newList();
		for (final File f : path.listFiles()) {
			final Page page = tryCreatePage(f);
			if (page != null) {
				result.add(page);
			}
		}
		return result;
	}
	private static List<Node> readNodes(final File path) {
		final List<Node> result = newList();
		return result;
	}
	private static List<Group> readGroups(final File path) {
		final List<Group> result = newList();
		return result;
	}
	
	private static List<DocTopic> readDocTopics(final File path) {
		final List<DocTopic> result = newList();
		return result;
	}

	private static SiteView createSiteView(final File file) {
		File svProperties = new File (file.getAbsolutePath() + File.separator + nameFileProperties);
		String elementTitle = extractElementTitle(svProperties);
		SiteView newSiteView = new SiteView(false, elementTitle, readAreas(file), readPages(file), readNodes(file), readGroups(file), readDocTopics(file));
		PartsReader.mapSiteViewPlwebTree.put(elementTitle, newSiteView);
		if (svProperties.lastModified()>lastModDate){
			changedList.add(elementTitle);
		}
		return newSiteView;
	}


	
	private static Area createArea(final File file) {
		File aProperties = new File (file.getAbsolutePath() + File.separator + nameFileProperties);
		String elementTitle = extractElementTitle(aProperties);
		Area newArea = new Area(false, elementTitle, readPages(file), readNodes(file), readGroups(file), readDocTopics(file));
		PartsReader.mapAreaPlwebTree.put(elementTitle, newArea);
		if (aProperties.lastModified()>lastModDate){
			changedList.add(elementTitle);
		}
		return newArea;
	}



	private static Page tryCreatePage(final File file) {
		if (!isPage(file)) {
			return null;
		}
		
		String elementTitle = extractElementTitle(file);
		Page newPage = new Page(false, elementTitle, file.getName(), readDocTopics(file));
		PartsReader.mapPagePlwebTree.put(elementTitle, newPage);
		if (file.lastModified()>lastModDate){
			changedList.add(elementTitle);
		}
		return newPage;
	}

	private static boolean isPage(final File file) {
		return file.getName().startsWith("page");
	}

	private static String extractElementTitle(final File elementFile) {
		final TitleGetterParser titleGetter = new TitleGetterParser();
		try {
			getParser().parse(elementFile,	titleGetter);
		} catch (IOException e) {
			return "Unable to parse title";
		} catch (SAXException e) {
			return "Unable to parse title";
		}
		return titleGetter.getTitle();
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
	
	private static boolean isRealSiteView(final File path) {
		return isRealPart(path, "SiteView");
	}
	
	private static boolean isRealArea(final File path) {
		return isRealPart(path, "Area");
	}
	
	private static boolean isRealPart(final File path, final String partName) {
		final File props = getPropsFile(path);
		if (!props.exists()) {
			return false;
		}
		return containsElement(props, partName);
	}

	private static File getPropsFile(final File path) {
		return new File(path.getAbsolutePath() + File.separator + "Properties.wr");
	}
	
	private static boolean containsElement(final File file, final String elementName) {
		final ContainsElementChecker checker = new ContainsElementChecker(elementName);
		try {
			getParser().parse(file,	checker);
		} catch (IOException e) {
			return false;
		} catch (SAXException e) {
			return false;
		}
		return checker.containsElement();
	}
	
	private static class ContainsElementChecker extends DefaultHandler {
		private final String elementName;
		
		private boolean containsElement = false;
		
		private ContainsElementChecker(final String elementName) {
			this.elementName = elementName;
		}
		
		
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if (elementName.equals(qName)) {
				containsElement = true;
			}
		}
		
		public boolean containsElement() {
			return containsElement;
		}
		
	}
}
