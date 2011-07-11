package org.spbu.plweb.diagram.util.projects.parts;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
	
	private static String nameFileProperties = "Properties.wr";

	public static Root readParts(final File baseDir) {
		final File rootDir = new File(baseDir + File.separator
				+ ProjectWebRatio.MODEL_FOLDER_PATH);
		return new Root(baseDir.getName(), readSiteViews(rootDir));
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

	private static SiteView createSiteView(final File file) {
		File svProperties = new File (file.getAbsolutePath() + File.separator + nameFileProperties);
		return new SiteView(extractElementTitle(svProperties), readAreas(file), readPages(file));
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
	
	private static Area createArea(final File file) {
		File aProperties = new File (file.getAbsolutePath() + File.separator + nameFileProperties);
		return new Area(extractElementTitle(aProperties), readPages(file));
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

	private static Page tryCreatePage(final File f) {
		if (!isPage(f)) {
			return null;
		}
		return new Page(extractElementTitle(f), f.getName());
	}

	private static boolean isPage(final File f) {
		return f.getName().startsWith("page");
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
