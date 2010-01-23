package org.spbu.pldoctoolkit.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.Configuration;
import net.sf.saxon.Controller;
import net.sf.saxon.StandardURIResolver;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.trans.DynamicError;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.value.SequenceExtent;
import net.sf.saxon.value.SingletonNode;
import net.sf.saxon.value.Value;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.DrlPublisherPlugin;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.cache.ControllerCache;
import org.spbu.pldoctoolkit.cache.SchemaCache;
import org.spbu.pldoctoolkit.dialogs.FinalInfProductSelectionDialog;
import org.spbu.pldoctoolkit.registry.ProjectRegistry;
import org.spbu.pldoctoolkit.registry.RegisteredLocation;
import org.xml.sax.DTDHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import com.thaiopensource.validate.Validator;
import com.thaiopensource.xml.sax.Jaxp11XMLReaderCreator;

public class BasicExportAction extends Action {
	protected static final ControllerCache CONTROLLER_CACHE = new ControllerCache();
	protected static final SchemaCache SCHEMA_CACHE = new SchemaCache();

	protected static final URL DRL2DOCBOOK_URL = DrlPublisherPlugin.getURL("xsl/drl/drl2docbook.xsl");
	protected static final URL DOCBOOK_SCHEMA_URL = DrlPublisherPlugin.getURL("schema/docbook/docbook.rng");

	protected final Controller drl2docbook;
	protected final String sFormat;
	protected final String extension;
	protected final IEditorPart editor;
	protected final XMLReader xmlReader;
	protected final Validator validator;
	private final URL docbookTransformationURL;
	protected final Boolean bTransform2DBOnly;
	
	protected Controller docbook2type;
	protected DocbookContentHandler contentHandler;
	protected ProjectRegistry registry;
	protected String fipId;
	
	protected final URIResolver uriResolver = new StandardURIResolver(new Configuration()) {
		private static final long serialVersionUID = -7919352677909462305L;
		public Source resolve(String href, String base) throws XPathException {
			if (registry != null) {
				RegisteredLocation loc = registry.getRegisteredLocation(href);
				if (loc != null)
					return new StreamSource(loc.getFile().getLocationURI().toString());
			}
			return super.resolve(href, base);
		}
	};

	protected final ErrorHandler errorHandler = new ErrorHandler() {
		public void error(SAXParseException exception) throws SAXException {
			createMarker(contentHandler.getSystemId(), contentHandler.getLineNumber(), 
					exception.getMessage() + " (" + exception.getLineNumber() + ")", IMarker.SEVERITY_ERROR);
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			createMarker(contentHandler.getSystemId(), contentHandler.getLineNumber(), 
					exception.getMessage() + " (" + exception.getLineNumber() + ")", IMarker.SEVERITY_ERROR);
		}

		public void warning(SAXParseException exception) throws SAXException {
			createMarker(contentHandler.getSystemId(), contentHandler.getLineNumber(), 
					exception.getMessage() + " (" + exception.getLineNumber() + ")", IMarker.SEVERITY_WARNING);
		}
	};

	protected final ErrorListener errorListener = new ErrorListener() {
		public void error(TransformerException exception) throws TransformerException {
			processTransformerException(exception, IMarker.SEVERITY_ERROR);
		}

		public void fatalError(TransformerException exception) throws TransformerException {
			processTransformerException(exception, IMarker.SEVERITY_ERROR);
		}

		public void warning(TransformerException exception) throws TransformerException {
			processTransformerException(exception, IMarker.SEVERITY_WARNING);
		}
	};
	
	public BasicExportAction(IEditorPart editor, URL docbookTransformationURL, String name, 
				String format, String extension, Boolean bTransform2DBOnly) throws Exception {
		super(name);
		if (editor == null)
			throw new NullPointerException("editor cannot be null");
		this.editor = editor;
		this.docbookTransformationURL = docbookTransformationURL;
		this.sFormat = format;
		this.extension = extension;
		this.bTransform2DBOnly = bTransform2DBOnly;

		// transformers
		drl2docbook = CONTROLLER_CACHE.getController(DRL2DOCBOOK_URL);

		// validator
		validator = SCHEMA_CACHE.getValidator(DOCBOOK_SCHEMA_URL, errorHandler);
		xmlReader = new Jaxp11XMLReaderCreator().createXMLReader();
	}

	protected Controller getDocbookTransformer() throws TransformerConfigurationException {
		if (docbook2type == null)
			docbook2type = CONTROLLER_CACHE.getController(docbookTransformationURL);
		return docbook2type;
	}
	
	@Override
	public void run() {
		try {
			if (editor == null)
				return;

			FileEditorInput editorInput = (FileEditorInput)editor.getEditorInput();
			registry = PLDocToolkitPlugin.getRegistry(editorInput.getFile().getProject().getName());
			IFile file = editorInput.getFile();

			List<RegisteredLocation> list = registry.findForFile(file);
			List<RegisteredLocation> fipList = new ArrayList<RegisteredLocation>();
			for (RegisteredLocation loc: list) {
				if (RegisteredLocation.FINAL_INF_PRODUCT.equals(loc.getType()))
					fipList.add(loc);
			}

			fipId = null;
			if (fipList.isEmpty()) {
				MessageDialog.openError(DrlPublisherPlugin.getShell(), "Error", "This file doesn't contain any FinalInfProducts");
				return;
			} else if (fipList.size() == 1) {
				fipId = fipList.get(0).getId();
			} else {
				FinalInfProductSelectionDialog dialog = new FinalInfProductSelectionDialog(DrlPublisherPlugin.getShell());
				dialog.setList(fipList);
				int result = dialog.open();
				if (result == Dialog.OK && dialog.getSelectionIndex() != -1) {
					fipId = list.get(dialog.getSelectionIndex()).getId();
				}
			}
			
			if (fipId == null)
				return;
			
			final IFile source = file;
			final File result = getDestinationFile();

			editorInput.getFile().getParent().deleteMarkers(IMarker.MARKER, true, IResource.DEPTH_INFINITE);

			IRunnableWithProgress op = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					doTransform(monitor, source, result);
				}
			};
			ProgressMonitorDialog pmDialog = new ProgressMonitorDialog(DrlPublisherPlugin.getShell());
			pmDialog.run(true, false, op);
			int returnCode = pmDialog.getReturnCode();
			if (returnCode == ProgressMonitorDialog.OK)
				showMessage(null);
		} catch (InvocationTargetException e) {
			showMessage(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void showMessage(InvocationTargetException e) {
		if (e == null)
			MessageDialog.openInformation(DrlPublisherPlugin.getShell(), "Information", "Export successfull");
		else
			MessageDialog.openError(DrlPublisherPlugin.getShell(), "Error", "Export failed: " + e.getCause().getMessage());
	}
	
	protected File getDestinationFile() {
//		TODO: replace with workspace-resource choosing dialog... meybe need to remember last export position somehow
		FileDialog fileDialog = new FileDialog(DrlPublisherPlugin.getShell(), SWT.SAVE);
		fileDialog.setText(getText());
		fileDialog.setFilterExtensions(new String[] {"*." + extension});
		String dialogResult = fileDialog.open();
		if (dialogResult == null)
			return null;
		return new File(dialogResult.contains(".") ? dialogResult : dialogResult + "." + extension);
	}

	protected void doTransform(IProgressMonitor monitor, IFile source, File result) throws InvocationTargetException {
		if (result == null)
			return;

		File fileDB = null;
		try {
			monitor.beginTask("Exporting to " + sFormat + "...", 3);
			if (bTransform2DBOnly)
			{
				fileDB = result;
			}
			else
			{
				fileDB = File.createTempFile("docbookgen", null);
				System.out.println(fileDB.getAbsolutePath());
			}

			monitor.subTask("Transforming DRL -> DocBook...");
			drl2docbook.setParameter("finalinfproductid", fipId);
			transform(drl2docbook, new StreamSource(source.getLocationURI().toString()), new StreamResult(fileDB));
			monitor.worked(1);

			monitor.subTask("Validating DocBook...");
			contentHandler = new DocbookContentHandler(validator.getContentHandler());
			xmlReader.setContentHandler(contentHandler);
			DTDHandler dtdHandler = validator.getDTDHandler();
			if (dtdHandler != null)
				xmlReader.setDTDHandler(dtdHandler);
			xmlReader.parse(fileDB.getAbsolutePath());
			monitor.worked(1);

			if (!bTransform2DBOnly)
			{
				monitor.subTask("Transforming DocBook -> " + sFormat + "...");
				transform(getDocbookTransformer(), new StreamSource(fileDB), new StreamResult(result));
			}
			
			monitor.done();
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		} finally {
			validator.reset();
			if (!bTransform2DBOnly && (fileDB != null))
			{
				//fileDB.deleteOnExit();
			}
		}
	}

	protected void transform(Controller transformer, Source source, Result result) throws TransformerException {
		transformer.reset();
		transformer.clearDocumentPool();
		transformer.setErrorListener(errorListener);
		transformer.setURIResolver(uriResolver);
		transformer.transform(source, result);
	}

	private void processTransformerException(TransformerException e, int severity) throws XPathException {
		if (e instanceof DynamicError) {
			processDynamicError((DynamicError)e, severity);
		} else {
			SourceLocator loc = e.getLocator();
			createMarker(loc.getSystemId(), loc.getLineNumber(), e.getMessage(), severity);
			e.printStackTrace();
		}
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
		IResource resource = getFile(systemId);
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

	private IFile getFile(String systemId) {
		RegisteredLocation loc = registry.getRegisteredLocation(systemId);
		if (loc != null)
			return loc.getFile();
		try {
			IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(new URI(systemId));
			if (files.length > 0)
				return files[0];
		} catch (URISyntaxException e) {
		}
		return null;	
	}
}
