package org.spbu.pldoctoolkit.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.Configuration;
import net.sf.saxon.Controller;
import net.sf.saxon.FeatureKeys;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.trans.DynamicError;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.value.SequenceExtent;
import net.sf.saxon.value.SingletonNode;
import net.sf.saxon.value.Value;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.Activator;
import org.xml.sax.DTDHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import com.thaiopensource.util.PropertyMap;
import com.thaiopensource.util.PropertyMapBuilder;
import com.thaiopensource.validate.Schema;
import com.thaiopensource.validate.ValidateProperty;
import com.thaiopensource.validate.Validator;
import com.thaiopensource.validate.auto.AutoSchemaReader;
import com.thaiopensource.xml.sax.Jaxp11XMLReaderCreator;
import com.thaiopensource.xml.sax.XMLReaderCreator;

public class BasicExportAction extends Action {
	protected static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();
	protected static final URL DRL2DOCBOOK_URL = Activator.getBundleResourceURL("xsl/drl/drl2docbook.xsl");
	protected static final URL DOCBOOK_SCHEMA_URL = Activator.getBundleResourceURL("schema/docbook/docbook.rng");
	
	protected final Controller drl2docbook;
	protected final Controller docbook2type;
	protected final String type;
	protected final String extension;
	protected final IEditorPart editor;

	protected final XMLReader xmlReader;
	protected final Validator validator;
	
	protected final FolderURIResolver uriResolver = new FolderURIResolver(new Configuration());
	
	protected final ErrorHandler errorHandler = new ErrorHandler() {
		public void error(SAXParseException exception) throws SAXException {
			createMarker(contentHandler.getSystemId(), contentHandler.getLineNumber(), 
					exception.getMessage() + "(" + exception.getLineNumber() + ")", IMarker.SEVERITY_ERROR);
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			createMarker(contentHandler.getSystemId(), contentHandler.getLineNumber(), 
					exception.getMessage() + "(" + exception.getLineNumber() + ")", IMarker.SEVERITY_ERROR);
		}

		public void warning(SAXParseException exception) throws SAXException {
			createMarker(contentHandler.getSystemId(), contentHandler.getLineNumber(), 
					exception.getMessage() + "(" + exception.getLineNumber() + ")", IMarker.SEVERITY_WARNING);
		}
	};
	
	protected final ErrorListener errorListener = new ErrorListener() {
		private void processException(TransformerException e, int severity) throws TransformerException {
			if (e instanceof DynamicError) {
				processDynamicError((DynamicError)e, severity);
			} else {
				SourceLocator l = e.getLocator();
				createMarker(l.getSystemId(), l.getLineNumber(), e.getCause().getMessage(), severity);
				e.printStackTrace();
			}
		}
		
		public void error(TransformerException exception) throws TransformerException {
			processException(exception, IMarker.SEVERITY_ERROR);
		}

		public void fatalError(TransformerException exception) throws TransformerException {
			processException(exception, IMarker.SEVERITY_ERROR);
		}

		public void warning(TransformerException exception) throws TransformerException {
			processException(exception, IMarker.SEVERITY_WARNING);
		}
	};
	
	protected DocbookContentHandler contentHandler;

	public BasicExportAction(IEditorPart editor, String type, String extension) throws Exception {
		super("Export to " + type);
		if (editor == null)
			throw new NullPointerException("editor cannot be null");
		this.editor = editor;
		this.type = type;
		this.extension = extension;
		
		// transformers
		TRANSFORMER_FACTORY.setAttribute(FeatureKeys.LINE_NUMBERING, true);
		TRANSFORMER_FACTORY.setURIResolver(uriResolver);
		System.out.println(TRANSFORMER_FACTORY.getURIResolver());
		drl2docbook = (Controller)TRANSFORMER_FACTORY.newTransformer(new StreamSource(DRL2DOCBOOK_URL.toString()));
		String url = Activator.getBundleResourceURL("xsl/docbook/" + type + "/docbook.xsl").toString();
		docbook2type = (Controller)TRANSFORMER_FACTORY.newTransformer(new StreamSource(url));
		
		// validator
		PropertyMapBuilder builder = new PropertyMapBuilder();
		XMLReaderCreator xmlReaderCreator = new Jaxp11XMLReaderCreator();
		ValidateProperty.XML_READER_CREATOR.put(builder, xmlReaderCreator);
		ValidateProperty.ERROR_HANDLER.put(builder, errorHandler);
		PropertyMap properties = builder.toPropertyMap();
		InputSource schemaSource = new InputSource(DOCBOOK_SCHEMA_URL.toString());
		Schema schema = new AutoSchemaReader().createSchema(schemaSource, properties);
		validator = schema.createValidator(properties);
		xmlReader = xmlReaderCreator.createXMLReader();
	}

	@Override
	public void run() {
		try {
			if (editor == null)
				return;
			FileDialog fileDialog = new FileDialog(Activator.getShell(), SWT.SAVE);
			fileDialog.setText(getText());
			fileDialog.setFilterExtensions(new String[] {"*." + extension});
			String dialogResult = fileDialog.open();
			if (dialogResult == null)
				return;

			String destinationFilename = dialogResult.contains(".") ? dialogResult : dialogResult + "." + extension;
			FileEditorInput editorInput = (FileEditorInput)editor.getEditorInput();
			uriResolver.setFolder(editorInput.getFile().getParent());
			final String source = editorInput.getFile().getLocationURI().toString();
			final String destination = new File(destinationFilename).toURI().toString();

			editorInput.getFile().getParent().deleteMarkers(IMarker.MARKER, true, IResource.DEPTH_INFINITE);
			
			IRunnableWithProgress op = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					doTransform(monitor, source, destination);
				}
			};
			ProgressMonitorDialog pmDialog = new ProgressMonitorDialog(Activator.getShell());
			pmDialog.run(true, false, op);
			int returnCode = pmDialog.getReturnCode();
			if (returnCode == ProgressMonitorDialog.OK) {
				MessageDialog.openInformation(Activator.getShell(), "Information", "Export successfull");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doTransform(IProgressMonitor monitor, String source, String destination) throws InvocationTargetException {
		File tempFile = null;
		try {
			monitor.beginTask("Exporting to " + type + "...", 3);
			tempFile = File.createTempFile("docbookgen", null);
			String temp = tempFile.toURI().toString();
			
			System.out.println(temp);
			
			monitor.subTask("Transforming DRL -> DocBook...");
			transform(drl2docbook, source, temp);
			monitor.worked(1);

			monitor.subTask("Validating DocBook...");
			contentHandler = new DocbookContentHandler(validator.getContentHandler());
			xmlReader.setContentHandler(contentHandler);
			DTDHandler dtdHandler = validator.getDTDHandler();
			if (dtdHandler != null)
				xmlReader.setDTDHandler(dtdHandler);
			xmlReader.parse(temp);
			monitor.worked(1);
			
			monitor.subTask("Transforming DocBook -> " + type + "...");
			transform(docbook2type, temp, destination);
			monitor.done();
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		} finally {
			validator.reset();
			if (tempFile != null)
				tempFile.delete();
		}
	}

	protected void transform(Controller transformer, String source, String destination) throws TransformerException {
		transformer.reset();
		transformer.clearDocumentPool();
		transformer.setErrorListener(errorListener);
		transformer.setURIResolver(uriResolver);
		transformer.transform(new StreamSource(source), new StreamResult(destination));
	}
	
	private void processDynamicError(DynamicError dynamicError, int severity) throws XPathException {
		Value errorObject = dynamicError.getErrorObject();
		if (errorObject instanceof SingletonNode) {
			NodeInfo node = ((SingletonNode) errorObject).getNode();
			createMarker(node.getSystemId(), node.getLineNumber(), dynamicError.getMessage(), severity);
		} else if (errorObject instanceof SequenceExtent) {
			SequenceExtent seq = (SequenceExtent) errorObject;
			SequenceIterator it = seq.iterate(dynamicError.getXPathContext());
			Item item;
			while ((item = it.next()) != null)
				if (item instanceof NodeInfo) {
					NodeInfo node = (NodeInfo) item;
					createMarker(node.getSystemId(), node.getLineNumber(), dynamicError.getMessage(), severity);
				}
		} else {
			String file = dynamicError.getLocator().getSystemId();
			int line = dynamicError.getLocator().getLineNumber();
			createMarker(file, line, dynamicError.getMessage(), IMarker.SEVERITY_ERROR);
		}
	}

	private void createMarker(String systemId, int lineNumber, String message, int severity) {
		IResource resource = uriResolver.getResource(systemId);
		if (resource == null) {
			System.out.println("Resource not found: " + systemId + " message: " + message + " on line " + lineNumber);
			return;
		}
		try {
			IMarker marker = resource.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		System.out.println("*** Marker: " + message + " @ " + systemId + " line " + lineNumber);
	}
}
