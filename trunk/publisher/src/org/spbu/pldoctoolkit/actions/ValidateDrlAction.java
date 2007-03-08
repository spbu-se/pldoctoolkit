package org.spbu.pldoctoolkit.actions;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IPathEditorInput;
import org.spbu.pldoctoolkit.Activator;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import com.thaiopensource.util.PropertyMap;
import com.thaiopensource.validate.Validator;
import com.thaiopensource.validate.auto.AutoSchemaReader;

public class ValidateDrlAction extends EditorAction {
	private static final String SCHEMA_URL = Activator.getResourceURL("schema/document-reuse-language.rng");
	
	private final XMLReader xmlReader;
	private final Validator validator;
	
	public ValidateDrlAction() throws CoreException {
		super("Validate DRL...");
		try {
			validator = new AutoSchemaReader().createSchema(new InputSource(SCHEMA_URL), PropertyMap.EMPTY).createValidator(PropertyMap.EMPTY);
			xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		} catch (Exception e) {
			throw new CoreException(new Status(Status.ERROR, Activator.PLUGIN_ID, 0, "Unable to load DRL schema", e));
		}
	}
	
	@Override
	public void run() {
		try {
			if (editor == null)
				return;
			IPathEditorInput editorInput = (IPathEditorInput) editor.getEditorInput();
			File editorFile = editorInput.getPath().toFile();
			
			xmlReader.setContentHandler(new DrlContentHandler(validator.getContentHandler()));
			DTDHandler dtdHandler = validator.getDTDHandler();
			if (dtdHandler != null)
				xmlReader.setDTDHandler(dtdHandler);
			// TODO Implement the error handler properly
			xmlReader.setErrorHandler(new ErrorHandler() {
				public void error(SAXParseException exception) throws SAXException {
					// TODO Auto-generated method stub
				}

				public void fatalError(SAXParseException exception) throws SAXException {
					// TODO Auto-generated method stub
				}

				public void warning(SAXParseException exception) throws SAXException {
					// TODO Auto-generated method stub
				}
			});
			xmlReader.parse(new InputSource(new FileInputStream(editorFile)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static class DrlContentHandler implements ContentHandler {
		private static final String DRL_URI = "http://math.spbu.ru/drl";
		
		private final ContentHandler handler;
		
		public DrlContentHandler(ContentHandler handler) {
			this.handler = handler;
		}
		
		public void characters(char[] ch, int start, int length) throws SAXException {
			handler.characters(ch, start, length);
		}

		public void endDocument() throws SAXException {
			handler.endDocument();
		}

		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (uri.equals(DRL_URI))
				handler.endElement(uri, localName, qName);
		}

		public void endPrefixMapping(String prefix) throws SAXException {
			handler.endPrefixMapping(prefix);
		}

		public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
			handler.ignorableWhitespace(ch, start, length);
		}

		public void processingInstruction(String target, String data) throws SAXException {
			handler.processingInstruction(target, data);
		}

		public void setDocumentLocator(Locator locator) {
			handler.setDocumentLocator(locator);
		}

		public void skippedEntity(String name) throws SAXException {
			handler.skippedEntity(name);
		}

		public void startDocument() throws SAXException {
			handler.startDocument();
		}

		public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
			if (uri.equals(DRL_URI))
				handler.startElement(uri, localName, qName, atts);
		}

		public void startPrefixMapping(String prefix, String uri) throws SAXException {
			handler.startPrefixMapping(prefix, uri);
		}
	}
}