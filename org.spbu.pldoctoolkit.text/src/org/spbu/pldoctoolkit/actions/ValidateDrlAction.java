package org.spbu.pldoctoolkit.actions;

import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.cache.SchemaCache;
import org.spbu.pldoctoolkit.xmlutil.ContentHandlerAdapter;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.thaiopensource.validate.Validator;
import com.thaiopensource.xml.sax.Jaxp11XMLReaderCreator;

public class ValidateDrlAction extends Action {
	private static final SchemaCache SCHEMA_CACHE = new SchemaCache();
	private static final URL SCHEMA_URL = PLDocToolkitPlugin.getBundleResourceURL("schema/document-reuse-language.rng");

	private final MarkingErrorHandler errorHandler = new MarkingErrorHandler();
	private final XMLReader xmlReader;
	private final Validator validator;
	private final IEditorPart editor;
	
	public ValidateDrlAction(IEditorPart editor) throws Exception {
		super("Validate DRL...");
		if (editor == null)
			throw new NullPointerException("editor cannot be null");
		this.editor = editor;
		validator = SCHEMA_CACHE.getValidator(SCHEMA_URL, errorHandler);
		xmlReader = new Jaxp11XMLReaderCreator().createXMLReader();
		xmlReader.setErrorHandler(errorHandler);
	}

	@Override
	public void run() {
		try {
			IFileEditorInput editorInput = (IFileEditorInput) editor.getEditorInput();
			final IFile editorFile = editorInput.getFile();
			editorFile.deleteMarkers(IMarker.MARKER, true, IResource.DEPTH_ZERO);
			xmlReader.setContentHandler(new DrlContentHandler(validator.getContentHandler()));
			DTDHandler dtdHandler = validator.getDTDHandler();
			if (dtdHandler != null)
				xmlReader.setDTDHandler(dtdHandler);
			errorHandler.setResource(editorFile);
			xmlReader.parse(new InputSource(editorFile.getLocationURI().toString()));
		} catch (SAXException e) {
			// ignored
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			validator.reset();
		}
	}
	
	private static class DrlContentHandler extends ContentHandlerAdapter {
		private static final String DRL_URI = "http://math.spbu.ru/drl";
		
		public DrlContentHandler(ContentHandler handler) {
			super(handler);
		}
		
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (uri.equals(DRL_URI))
				handler.endElement(uri, localName, qName);
		}
		
		public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
			if (uri.equals(DRL_URI))
				handler.startElement(uri, localName, qName, atts);
		}
	}
}