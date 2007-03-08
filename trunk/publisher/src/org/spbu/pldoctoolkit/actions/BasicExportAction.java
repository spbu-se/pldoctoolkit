package org.spbu.pldoctoolkit.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.Controller;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IPathEditorInput;
import org.spbu.pldoctoolkit.Activator;

public class BasicExportAction extends EditorAction {
	protected static final String DRL2DOCBOOK_URL = Activator.getResourceURL("xsl/drl/drl2docbook.xsl");
	
	protected final TransformerFactory transformerFactory = TransformerFactory.newInstance();
	protected final Controller drl2docbook;
	protected final Controller docbook2type;
	protected final String type;
	
	public BasicExportAction(String type) throws CoreException {
		super("Export to " + type);
		try {
			this.type = type;
			drl2docbook = (Controller)transformerFactory.newTransformer(new StreamSource(DRL2DOCBOOK_URL));
			String url = Activator.getResourceURL("xsl/docbook/" + type + "/docbook.xsl");
			docbook2type = (Controller)transformerFactory.newTransformer(new StreamSource(url));
		} catch (TransformerConfigurationException e) {
			throw new CoreException(new Status(Status.ERROR, Activator.PLUGIN_ID, 0, "Unable to load transformation for " + type, e));
		}
	}

	@Override
	public void run() {
		try {
			if (editor == null)
				return;
			FileDialog fileDialog = new FileDialog(Activator.getShell(), SWT.SAVE);
			fileDialog.setText(getText());
			fileDialog.setFilterExtensions(new String[] {"*." + type});
			String dialogResult = fileDialog.open();
			if (dialogResult == null)
				return;
			
			String destinationFilename = dialogResult.contains(".") ? dialogResult : dialogResult + "." + type;
			IPathEditorInput editorInput = (IPathEditorInput)editor.getEditorInput();
			final File sourceFile = editorInput.getPath().toFile();
			final File destinationFile = new File(destinationFilename);
			
			IRunnableWithProgress op = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					doTransform(monitor, sourceFile, destinationFile);
				}
			};
			ProgressMonitorDialog pmDialog = new ProgressMonitorDialog(Activator.getShell());
			pmDialog.run(true, false, op);
			int returnCode = pmDialog.getReturnCode();
			if (returnCode == ProgressMonitorDialog.OK) {
				MessageDialog.openInformation(Activator.getShell(), "Information", "Export successfull");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void doTransform(IProgressMonitor monitor, File source, File destination) throws InvocationTargetException {
		File temp = null;
		try {
			monitor.beginTask("Exporting to " + type + "...", 3);
			temp = File.createTempFile("docbookgen", null);
			monitor.worked(1);
			
			monitor.subTask("Transforming DRL -> DocBook...");
			transform(drl2docbook, source, temp);
			monitor.worked(1);

			monitor.subTask("Transforming DocBook -> " + type + "...");
			transform(drl2docbook, temp, destination);
			monitor.done();
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		} finally {
			if (temp != null)
				temp.delete();
		}
	}
	
	protected void transform(Controller transformer, File source, File destination) throws TransformerException {
		transformer.reset();
		transformer.clearDocumentPool();
		transformer.transform(new StreamSource(source), new StreamResult(destination));
	}
}
