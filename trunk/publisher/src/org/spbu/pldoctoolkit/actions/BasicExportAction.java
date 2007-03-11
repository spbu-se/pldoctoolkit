package org.spbu.pldoctoolkit.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.Controller;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPathEditorInput;
import org.spbu.pldoctoolkit.Activator;

public class BasicExportAction extends Action {
	protected static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();
	protected static final URL DRL2DOCBOOK_URL = Activator.getBundleResourceURL("xsl/drl/drl2docbook.xsl");

	protected final Controller drl2docbook;
	protected final Controller docbook2type;
	protected final String type;
	protected final String extension;
	protected final IEditorPart editor;

	public BasicExportAction(IEditorPart editor, String type, String extension) throws Exception {
		super("Export to " + type);
		if (editor == null)
			throw new NullPointerException("editor cannot be null");
		this.editor = editor;
		this.type = type;
		this.extension = extension;
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
			transform(docbook2type, temp, destination);
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
