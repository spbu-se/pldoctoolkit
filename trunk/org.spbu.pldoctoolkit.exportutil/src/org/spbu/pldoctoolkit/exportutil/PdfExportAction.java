package org.spbu.pldoctoolkit.exportutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.spbu.pldoctoolkit.exportutil.utils.ContentHandlerAdapter;
import org.spbu.pldoctoolkit.exportutil.utils.DocbookContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.SAXException;

public class PdfExportAction extends BasicExportAction {
	private static FopFactory fopFactory;
	protected static final File DOCBOOK2PDF_FILE; 
	
	static {
		DOCBOOK2PDF_FILE = new File(ExportUtil.getRunningAppPath() + "/xsl/db2fo.xsl");
	}
	
	public PdfExportAction(File destinationFile) throws Exception {
		super(destinationFile, DOCBOOK2PDF_FILE, false);
	}

	@Override
	protected void doTransform(File source, File result) throws InvocationTargetException {
		if (result == null)
			return;
		
		File tempFile = null;
		OutputStream out = null;
		try {
			logger.logEvent("Exporting to pdf...");
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

			logger.logEvent("Done");
			out = new FileOutputStream(result);
			Fop fop = getFopFactory().newFop(MimeConstants.MIME_PDF, out);
			
			logger.logEvent("Transforming DocBook -> pdf...");
			ContentHandler ch = fop.getDefaultHandler();
			
			// that's a work-around for a saxon's (or FOP's?) bug - the startDocument is actually called twice on
			// the content handler, which leads to FOP error (using a single fop instance two times is not allowed).
			ch.startDocument();
			getDocbookTransformer().reset();
			transform(getDocbookTransformer(), new StreamSource(tempFile), new SAXResult(new ContentHandlerAdapter(ch) {
				@Override
				public void startDocument() throws SAXException {
				}
			}));
			logger.logEvent("Done");
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvocationTargetException(e);
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			validator.reset();
			if (tempFile != null)
				tempFile.delete();
		}
	}
	
	private FopFactory getFopFactory() throws SAXException, IOException {
		if (fopFactory == null) {
			fopFactory = FopFactory.newInstance();
			fopFactory.setUserConfig((new File(ExportUtil.getRunningAppPath() + "/fop/fop.xconf")).toURI().toURL().toString());
			fopFactory.setFontBaseURL((new File(ExportUtil.getRunningAppPath() + "/fop/")).toURI().toURL().toString());
		}
		return fopFactory;
	}
}
