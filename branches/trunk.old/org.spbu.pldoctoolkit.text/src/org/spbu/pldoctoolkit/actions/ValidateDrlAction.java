package org.spbu.pldoctoolkit.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.spbu.pldoctoolkit.DrlPublisherPlugin;
import org.spbu.pldoctoolkit.registry.RegisteredLocation;
import org.xml.sax.DTDHandler;

public class ValidateDrlAction extends BasicExportAction {

	public ValidateDrlAction(IEditorPart editor) throws Exception {
		super(editor, null, "Validate", null, null, false);
	}

	@Override
	protected void doTransform(IProgressMonitor monitor, IFile source, File result) throws InvocationTargetException {
		File tempFile = null;
		try {
			monitor.beginTask("Validating...", 2);
			tempFile = File.createTempFile("docbookgen", null);

			monitor.subTask("Transforming DRL -> DocBook...");
			drl2docbook.setParameter("finalinfproductid", fipId);
			transform(drl2docbook, new StreamSource(source.getLocationURI().toString()), new StreamResult(tempFile));
			monitor.worked(1);

			monitor.subTask("Validating DocBook...");
			contentHandler = new DocbookContentHandler(validator.getContentHandler());
			xmlReader.setContentHandler(contentHandler);
			DTDHandler dtdHandler = validator.getDTDHandler();
			if (dtdHandler != null)
				xmlReader.setDTDHandler(dtdHandler);
			xmlReader.parse(tempFile.getAbsolutePath());
			monitor.done();
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		} finally {
			validator.reset();
			if (tempFile != null)
				tempFile.delete();
		}
	}

	@Override
	protected File getDestinationFile() {
		return null;
	}

	@Override
	protected void showMessage(InvocationTargetException e) {
	}
}
