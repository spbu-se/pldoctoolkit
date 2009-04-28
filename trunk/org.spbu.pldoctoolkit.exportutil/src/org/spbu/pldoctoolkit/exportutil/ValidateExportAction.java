package org.spbu.pldoctoolkit.exportutil;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.spbu.pldoctoolkit.exportutil.utils.DocbookContentHandler;
import org.xml.sax.DTDHandler;

public class ValidateExportAction extends BasicExportAction {

	public ValidateExportAction() throws Exception {
		super(null, null, false);
	}

	@Override
	protected void doTransform(File source, File result) throws InvocationTargetException {
		File tempFile = null;
		try {
			logger.logEvent("Validating project:");
			tempFile = File.createTempFile("docbookgen", null);

			logger.logEvent("Transforming DRL -> DocBook...");
			firstErrorMessage = null;
			firstErrorNode = null;
			drl2docbook.reset();
			drl2docbook.setParameter("finalinfproductid", fipId);
			transform(drl2docbook, new StreamSource(source), new StreamResult(tempFile));
			logger.logEvent("Done");

			logger.logEvent("Validating DocBook...");
			contentHandler = new DocbookContentHandler(validator.getContentHandler());
			xmlReader.setContentHandler(contentHandler);
			DTDHandler dtdHandler = validator.getDTDHandler();
			if (dtdHandler != null)
				xmlReader.setDTDHandler(dtdHandler);
			xmlReader.parse(tempFile.getAbsolutePath());
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		} finally {
			validator.reset();
			if (tempFile != null)
				tempFile.delete();
		}
	}
}
