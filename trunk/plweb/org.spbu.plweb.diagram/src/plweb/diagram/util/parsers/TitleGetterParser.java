package plweb.diagram.util.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TitleGetterParser extends DefaultHandler {
	private String title = null;
	
	@Override
	public void startDocument() throws SAXException {
		title = null;
		super.startDocument();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ((qName.equals("Page") || qName.equals("Area")) && title == null) {
			title = attributes.getValue("name");
		}
	}
	
	public String getTitle() {
		return title;
	}
}
