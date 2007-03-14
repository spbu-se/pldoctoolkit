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

public class BasicExportAction extends Action {
	protected static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();
	protected static final URL DRL2DOCBOOK_URL = Activator.getBundleResourceURL("xsl/drl/drl2docbook.xsl");

	protected final Controller drl2docbook;
	protected final Controller docbook2type;
	protected final String type;
	protected final String extension;
	protected final IEditorPart editor;

	protected final FolderURIResolver uri_resolver = new FolderURIResolver(new Configuration());
	
	protected final ErrorListener el = new ErrorListener() {
		public void error(TransformerException exception) throws TransformerException {
			if (exception instanceof DynamicError) {
				processDynamicError((DynamicError) exception);
			} else {
				SourceLocator locator = exception.getLocator();
				createMarker(locator.getSystemId(), locator.getLineNumber(), exception.getCause().getMessage());
				exception.printStackTrace();
			}
		}

		public void fatalError(TransformerException exception) throws TransformerException {
			if (exception instanceof DynamicError) {
				processDynamicError((DynamicError) exception);
			} else {
				SourceLocator locator = exception.getLocator();
				createMarker(locator.getSystemId(), locator.getLineNumber(), exception.getCause().getMessage());
				exception.printStackTrace();
			}
		}

		private void processDynamicError(DynamicError dynamicError) throws XPathException {
			Value errorObject = dynamicError.getErrorObject();
			if (errorObject instanceof SingletonNode) {
				NodeInfo node = ((SingletonNode) errorObject).getNode();
				createMarker(node.getSystemId(), node.getLineNumber(), dynamicError.getMessage());
			} else if (errorObject instanceof SequenceExtent) {
				SequenceExtent seq = (SequenceExtent) errorObject;
				SequenceIterator it = seq.iterate(dynamicError.getXPathContext());
				Item item;
				while ((item = it.next()) != null)
					if (item instanceof NodeInfo) {
						NodeInfo node = (NodeInfo) item;
						createMarker(node.getSystemId(), node.getLineNumber(), dynamicError.getMessage());
					}
			} else {
				String file = dynamicError.getLocator().getSystemId();
				int line = dynamicError.getLocator().getLineNumber();
				createMarker(file, line, dynamicError.getMessage());
			}
		}

		private void createMarker(String systemId, int lineNumber, String message) {
			IResource resource = uri_resolver.getResource(systemId);
			if (resource == null) {
				System.out.println("Resource not found: " + systemId + " message: " + message + " on line " + lineNumber);
				return;
			}
			try {
				IMarker marker = resource.createMarker(IMarker.PROBLEM);
				marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
				marker.setAttribute(IMarker.MESSAGE, message);
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			System.out.println("*** Marker: " + message + " @ " + systemId + " line " + lineNumber);
		}

		public void warning(TransformerException exception) throws TransformerException {
			System.out.println(exception); // TODO
		}
	};

	public BasicExportAction(IEditorPart editor, String type, String extension) throws Exception {
		super("Export to " + type);
		if (editor == null)
			throw new NullPointerException("editor cannot be null");
		this.editor = editor;
		this.type = type;
		this.extension = extension;
		TRANSFORMER_FACTORY.setAttribute(FeatureKeys.LINE_NUMBERING, true);
		TRANSFORMER_FACTORY.setURIResolver(uri_resolver);
		System.out.println(TRANSFORMER_FACTORY.getURIResolver());
		drl2docbook = (Controller)TRANSFORMER_FACTORY.newTransformer(new StreamSource(DRL2DOCBOOK_URL.toString()));
		String url = Activator.getBundleResourceURL("xsl/docbook/" + type + "/docbook.xsl").toString();
		docbook2type = (Controller)TRANSFORMER_FACTORY.newTransformer(new StreamSource(url));
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
			uri_resolver.setFolder(editorInput.getFile().getParent());
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
			monitor.worked(1);

			monitor.subTask("Transforming DRL -> DocBook...");
			transform(drl2docbook, source, temp);
			monitor.worked(1);

			monitor.subTask("Transforming DocBook -> " + type + "...");
			transform(docbook2type, temp, destination);
			monitor.done();
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		} finally {
			if (tempFile != null)
				tempFile.delete();
		}
	}

	protected void transform(Controller transformer, String source, String destination) throws TransformerException {
		transformer.reset();
		transformer.clearDocumentPool();
		transformer.setErrorListener(el);
		transformer.setURIResolver(uri_resolver);
		transformer.transform(new StreamSource(source), new StreamResult(destination));
	}
}
