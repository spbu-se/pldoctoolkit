package org.spbu.publishutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.spbu.publishutil.utils.ContentHandlerAdapter;
import org.spbu.publishutil.utils.DocbookContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.SAXException;

public class PdfPublishAction extends BasicPublishAction {
	private static FopFactory fopFactory;
	protected static final File DOCBOOK2PDF_FILE; 
	
	static {
		DOCBOOK2PDF_FILE = new File(PublishUtil.getRunningAppPath() + "/xsl/db2fo.xsl");
	}
	
	public PdfPublishAction(File destinationFile) throws Exception {
		super(destinationFile, DOCBOOK2PDF_FILE, false);
	}

	@Override
	protected void doTransform(File source, File result) throws Exception {
		if (result == null)
			return;
		
		File tempFile = null;
		OutputStream out = null;
		try {
			logger.logEvent("Exporting to pdf:");
			tempFile = File.createTempFile("docbookgen", null);

			logger.logEvent("Transforming DRL -> DocBook...");
			firstErrorMessage = null;
			firstErrorNode = null;
			drl2docbook.reset();
			drl2docbook.setParameter("finalinfproductid", fipId);
			transform(drl2docbook, new StreamSource(source), new StreamResult(tempFile));

			logger.logEvent("Validating DocBook...");
			contentHandler = new DocbookContentHandler(validator.getContentHandler());
			xmlReader.setContentHandler(contentHandler);
			DTDHandler dtdHandler = validator.getDTDHandler();
			if (dtdHandler != null)
				xmlReader.setDTDHandler(dtdHandler);
			xmlReader.parse(tempFile.getAbsolutePath());

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
		} catch (Exception e) {
			throw e;
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					logger.logException(e, true);
				}
			validator.reset();
			if (tempFile != null)
				tempFile.delete();
		}
	}
	
	private FopFactory getFopFactory() throws SAXException, IOException {
		if (fopFactory == null) {
			fopFactory = FopFactory.newInstance();
			fopFactory.setUserConfig((new File(PublishUtil.getRunningAppPath() + "/fop/fop.xconf")).toURI().toURL().toString());
			fopFactory.setFontBaseURL((new File(PublishUtil.getRunningAppPath() + "/fop/")).toURI().toURL().toString());
		}
		return fopFactory;
	}
}
