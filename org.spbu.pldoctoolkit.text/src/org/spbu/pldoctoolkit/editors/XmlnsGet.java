package org.spbu.pldoctoolkit.editors;

import java.io.IOException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlnsGet {

	// Find out namespaces in drl's files
	private String drlXmlns;
	private String docbookXmlns;
	private IEditorPart edit;
	
	
	private void parseXmlns(IEditorPart editor){
		IFileEditorInput editorInput = (IFileEditorInput) editor
				.getEditorInput();
		final IFile editorFile = editorInput.getFile();
		// editorFile.deleteMarkers(IMarker.MARKER, true, IResource.DEPTH_ZERO);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser parser;
		try {
			parser = spf.newSAXParser();
			parser.parse(
					new InputSource(editorFile.getLocationURI().toString()),
					new xmlnParser());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public XmlnsGet(IEditorPart editor) {
		drlXmlns = "";
		docbookXmlns = "";
		edit = editor;
		parseXmlns(edit);
	}
	
	public void update(){
		parseXmlns(edit);
	}
	
	public String getDocbook(){
		return docbookXmlns;
	}

	public String getDRL(){
		return drlXmlns;
	}
	class xmlnParser extends DefaultHandler {
		boolean first;

		public void startDocument() {
			first = true;
		}

		private String parse(String s) {
			if (s.length() == 5) {
				return "";
			} else {
				return (s.substring(6) + ":");
			}
		}

		public void startElement(String uri, String localName, String qname,
				Attributes attr) {
			if (first) {
				if (qname == "ProductLine") {
					drlXmlns = parse(attr.getQName(0));
				} else {
					for (int i = 0; i < attr.getLength(); i++){
					if (attr.getValue(i).compareTo("http://docbook.org/ns/docbook") == 0) {
						docbookXmlns = parse(attr.getQName(i));
					}
					if (attr.getValue(i).compareTo("http://math.spbu.ru/drl") ==  0){
						drlXmlns = parse(attr.getQName(i));
					}}

				}
			}

			first = false;
		}

	}

}
