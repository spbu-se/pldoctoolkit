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
import org.xml.sax.DTDHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.thaiopensource.validate.Validator;
import com.thaiopensource.xml.sax.Jaxp11XMLReaderCreator;

public class ValidateDrlAction extends Action {
	private static final SchemaCache SCHEMA_CACHE = new SchemaCache();
	private static final URL SCHEMA_URL = PLDocToolkitPlugin.getURL("schema/document-reuse-language.rng");

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
			validator.reset();
			IFileEditorInput editorInput = (IFileEditorInput) editor.getEditorInput();
			final IFile editorFile = editorInput.getFile();
			editorFile.deleteMarkers(IMarker.MARKER, true, IResource.DEPTH_ZERO);
			xmlReader.setContentHandler(validator.getContentHandler());
			DTDHandler dtdHandler = validator.getDTDHandler();
			if (dtdHandler != null)
				xmlReader.setDTDHandler(dtdHandler);
			errorHandler.setResource(editorFile);
			xmlReader.parse(new InputSource(editorFile.getLocationURI().toString()));
		} catch (SAXException e) {
			// ignored
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}