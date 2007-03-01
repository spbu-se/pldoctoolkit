package org.spbu.pldoctoolkit.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPathEditorInput;
import org.spbu.pldoctoolkit.Activator;

public class BasicExportAction extends AbstractExportAction {
	private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();
	private static final URL BASE_URL = Activator.getDefault().getBundle().getEntry("/");
	private static final URL DRL2DOCBOOK_URL = createURL("xsl/drl/drl2docbook.xsl");
	
	private final String type;
	
	private static URL createURL(String path) {
		try {
			return new URL(BASE_URL, path);
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	public BasicExportAction(String type) {
		super("Export to " + type, Activator.getImageDescriptor("icons/sample.gif"));
		this.type = type;
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
					try {
						monitor.beginTask("Exporting to " + type + "...", 5);
						File dstFile = new File(destinationFilename);
						if (!dstFile.getName().contains(".")) // allow ommitting extension
							dstFile = new File(destinationFilename + "." + type);
						File tmpFile = File.createTempFile("docbookgen", null);

						Source drl2docbook = new StreamSource(DRL2DOCBOOK_URL.openStream());
						
						URL url = getStylesheetURL();
						Source docbook2type = new StreamSource(url.openStream());
						docbook2type.setSystemId(url.toString());
						monitor.worked(1);
						
						monitor.subTask("Loading DRL stylesheet...");
						Transformer drlTransformer = TRANSFORMER_FACTORY.newTransformer(drl2docbook);
						monitor.worked(1);
						if (monitor.isCanceled())
							return;
						
						monitor.subTask("Loading DocBook stylesheet");
						TRANSFORMER_FACTORY.setErrorListener(new ErrorListener() {
							public void error(TransformerException exception) throws TransformerException {
								System.out.println("error " + exception.getMessageAndLocation());
							}

							public void fatalError(TransformerException exception) throws TransformerException {
								System.out.println("fatal error " + exception.getMessageAndLocation());
							}

							public void warning(TransformerException exception) throws TransformerException {
								System.out.println("warning " + exception.getMessageAndLocation());
							}							
						});
						Transformer docbookTransformer = TRANSFORMER_FACTORY.newTransformer(docbook2type);
						monitor.worked(1);
						if (monitor.isCanceled())
							return;
						
						IPathEditorInput editorInput = (IPathEditorInput)editor.getEditorInput();
						
						Source source = new StreamSource(editorInput.getPath().toFile());
						Result temp = new StreamResult(tmpFile);
						Source tempSource = new StreamSource(tmpFile);
						Result result = new StreamResult(dstFile);
						
						monitor.subTask("Transforming DRL -> DocBook...");
						drlTransformer.transform(source, temp);
						monitor.worked(1);
						if (monitor.isCanceled())
							return;

						monitor.subTask("Transforming DocBook -> " + type + "...");
						docbookTransformer.transform(tempSource, result);
						monitor.done();
					} catch (Exception e) {
						throw new InvocationTargetException(e);
					}
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
	
	private URL getStylesheetURL() {
		try {
			return new URL(BASE_URL, "xsl/docbook/" + type + "/docbook.xsl");
		} catch (MalformedURLException e) {
			return null;
		}
	}
}
