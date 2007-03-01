package org.spbu.pldoctoolkit.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPathEditorInput;
import org.spbu.pldoctoolkit.Activator;

public class BasicExportAction extends AbstractExportAction {
	private final Transformer docbookTransformer;
	private final String type;
	
	public BasicExportAction(String type) throws CoreException {
		super("Export to " + type, Activator.getImageDescriptor("icons/sample.gif"));
		try {
			this.type = type;
			String url = getURL("xsl/docbook/" + type + "/docbook.xsl");
			docbookTransformer = TRANSFORMER_FACTORY.newTransformer(new StreamSource(url));
		} catch (TransformerConfigurationException e) {
			throw new CoreException(new Status(Status.ERROR, Activator.PLUGIN_ID, 0, "Unable to load transformation for " + type, e));
		}
	}

	@Override
	public void run() {
		final Shell shell = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell(); 
		try {
			if (editor == null)
				return;
			FileDialog dialog = new FileDialog(shell, SWT.SAVE);
			dialog.setText(getText());
			dialog.setFilterExtensions(new String[] {"*." + type});
			final String destinationFilename = dialog.open();
			if (destinationFilename == null) // user cancelled operation
				return;
			
			IRunnableWithProgress op = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					doTransform(monitor, destinationFilename);
				}
			};
			
			ProgressMonitorDialog pmd = new ProgressMonitorDialog(shell);
			pmd.run(true, true, op);
			
			int returnCode = pmd.getReturnCode();
			if (returnCode == ProgressMonitorDialog.OK) {
				MessageDialog.openInformation(shell, "Information", "Export successfull");
			} else if (returnCode == ProgressMonitorDialog.CANCEL) {
				MessageDialog.openInformation(shell, "Information", "Operation cancelled");
			}
		} catch (Throwable e) {
			while (e.getCause() != null)
				e = e.getCause();
			MessageDialog.openError(shell, "Export error!", e.getMessage());
			throw new Error(e);
		}
	}
	
	void doTransform(IProgressMonitor monitor, String destinationFilename) throws InvocationTargetException {
		try {
			monitor.beginTask("Exporting to " + type + "...", 3);
			File dstFile = new File(destinationFilename);
			if (!dstFile.getName().contains(".")) // allow ommitting extension
				dstFile = new File(destinationFilename + "." + type);
			File tmpFile = File.createTempFile("docbookgen", null);
			System.out.println(tmpFile);
			monitor.worked(1);
			
			IPathEditorInput editorInput = (IPathEditorInput)editor.getEditorInput();
			
			Source source = new StreamSource(editorInput.getPath().toFile());
			Result temp = new StreamResult(tmpFile);
			Source tempSource = new StreamSource(tmpFile);
			Result result = new StreamResult(dstFile);
			
			monitor.subTask("Transforming DRL -> DocBook...");
			drlTransformer.reset();
			drlTransformer.transform(source, temp);
			monitor.worked(1);
			if (monitor.isCanceled())
				return;

			monitor.subTask("Transforming DocBook -> " + type + "...");
			docbookTransformer.reset();
			docbookTransformer.transform(tempSource, result);
			tmpFile.delete();
			monitor.done();
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		}
	}
}
