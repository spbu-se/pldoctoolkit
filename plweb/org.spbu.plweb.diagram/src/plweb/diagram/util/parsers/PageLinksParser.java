package plweb.diagram.util.parsers;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import plweb.diagram.util.PageLinkInfo;

public class PageLinksParser extends DefaultHandler{
	
	private String id = null;
	
	private String title = null;
	
	private List<String> links = null;
	
	public void startDocument() throws SAXException {
		id = null;
		title = null;
		links = new ArrayList<String>();
	}
	
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("Page") && title == null) {
			id = attributes.getValue("id");
			title = attributes.getValue("name");
		} else if (qName.equals("Link")) {
			String link = attributes.getValue("to");
			int pInd = link.indexOf("page");
			int cutInd = link.indexOf("#", pInd);
			link = link.substring(0, cutInd > 0 ? cutInd : link.length());
			links.add(link);
		}
	}
	
	public PageLinkInfo getResult() {
		return new PageLinkInfo(id, title, links);
	}
}
