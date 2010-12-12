package plweb.diagram.util.parsers;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PlwebPagesParser extends DefaultHandler {
	public static final String NEW_PAGE_PREFIX = "newPage";
	
	private int newPagesCounter = 0;
	
	private HashMap<String, String> pages = null;
	
	public Map<String, String> getPagesMap() {
		return pages;
	}
	
	@Override
	public void startDocument() throws SAXException {
		if (pages == null) {
			pages = new HashMap<String, String>();
		}
		newPagesCounter = 0;
		super.startDocument();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("plweb:Page")) {
			String title = attributes.getValue("title");
			String source = attributes.getValue("source");
			if (source == null || source.length() == 0) {
				source = NEW_PAGE_PREFIX + (++newPagesCounter);
			}
			pages.put(source, title);
		} else if (qName.equals("class")) {
			String type = attributes.getValue("xsi:type");
			if (type != null && type.equals("plweb:Page")) {
				String title = attributes.getValue("title");
				String source = attributes.getValue("source");
				if (source == null || source.length() == 0) {
					source = NEW_PAGE_PREFIX + (++newPagesCounter);
				}
				pages.put(source, title);
			}
		}
	}
}
