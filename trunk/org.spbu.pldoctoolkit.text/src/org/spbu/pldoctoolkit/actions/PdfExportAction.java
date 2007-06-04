package org.spbu.pldoctoolkit.actions;

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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorPart;
import org.spbu.pldoctoolkit.DrlPublisherPlugin;
import org.spbu.pldoctoolkit.xmlutil.ContentHandlerAdapter;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.SAXException;

public class PdfExportAction extends BasicExportAction {
	private static final FopFactory FOP_FACTORY = FopFactory.newInstance();
	
	public PdfExportAction(IEditorPart editor) throws Exception {
		super(editor, DrlPublisherPlugin.getURL("xsl/docbook/fo/docbook.xsl"), "PDF", "pdf");
	}

	@Override
	protected void doTransform(IProgressMonitor monitor, File source, File result) throws InvocationTargetException {
		File tempFile = null;
		OutputStream out = null;
		try {
			monitor.beginTask("Exporting to " + format + "...", 3);
			tempFile = File.createTempFile("docbookgen", null);
			System.out.println("temp file location: " + tempFile.getAbsolutePath());

			monitor.subTask("Transforming DRL -> DocBook...");
			drl2docbook.setParameter("finalinfproductid", fipId);
			transform(drl2docbook, new StreamSource(source), new StreamResult(tempFile));
			monitor.worked(1);

			monitor.subTask("Validating DocBook...");
			contentHandler = new DocbookContentHandler(validator.getContentHandler());
			xmlReader.setContentHandler(contentHandler);
			DTDHandler dtdHandler = validator.getDTDHandler();
			if (dtdHandler != null)
				xmlReader.setDTDHandler(dtdHandler);
			xmlReader.parse(tempFile.getAbsolutePath());
			monitor.worked(1);

			out = new FileOutputStream(result);
			Fop fop = FOP_FACTORY.newFop(MimeConstants.MIME_PDF, out);
			
			monitor.subTask("Transforming DocBook -> " + format + "...");
			ContentHandler ch = fop.getDefaultHandler();
			
			// that's a work-around for a saxon's (or FOP's?) bug - the startDocument is actually called twice on
			// the content handler, which leads to FOP error (using a single fop instance two times is not allowed).
			ch.startDocument();
			transform(docbook2type, new StreamSource(tempFile), new SAXResult(new ContentHandlerAdapter(ch) {
				@Override
				public void startDocument() throws SAXException {
				}
			}));
			monitor.done();
		} catch (Exception e) {
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
}
