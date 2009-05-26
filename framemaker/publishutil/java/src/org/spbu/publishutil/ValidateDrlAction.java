package org.spbu.publishutil;

import java.io.File;

import org.spbu.publishutil.cache.SchemaCache;
import org.spbu.publishutil.utils.EventLogger;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import com.thaiopensource.validate.Validator;
import com.thaiopensource.xml.sax.Jaxp11XMLReaderCreator;

public class ValidateDrlAction {
	private static final SchemaCache SCHEMA_CACHE = new SchemaCache();
	private static final File DRL_SCHEMA_FILE;
	
	static {
		 DRL_SCHEMA_FILE = new File(PublishUtil.getRunningAppPath() + "/schema/document-reuse-language.rng");
	}

	private final XMLReader xmlReader;
	private final Validator validator;
	
	protected EventLogger logger;
	protected ContentHandler contentHandler;
	
	protected final ErrorHandler errorHandler = new ErrorHandler() {
		public void error(SAXParseException exception) throws SAXException {
			logger.logError(exception.getSystemId(), exception.getLineNumber(), 
					exception.getMessage() + " (" + exception.getLineNumber() + ")", EventLogger.SEVERITY_ERROR);
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			logger.logError(exception.getSystemId(), exception.getLineNumber(), 
					exception.getMessage() + " (" + exception.getLineNumber() + ")", EventLogger.SEVERITY_ERROR);
		}

		public void warning(SAXParseException exception) throws SAXException {
			logger.logError(exception.getSystemId(), exception.getLineNumber(), 
					exception.getMessage() + " (" + exception.getLineNumber() + ")", EventLogger.SEVERITY_WARNING);
		}
	};
	
	public void setLogger(EventLogger logger) {
		this.logger = logger;
	}
	
	public ValidateDrlAction() throws Exception {
		validator = SCHEMA_CACHE.getValidator(DRL_SCHEMA_FILE, errorHandler);
		xmlReader = new Jaxp11XMLReaderCreator().createXMLReader();
		xmlReader.setErrorHandler(errorHandler);
		
		contentHandler = validator.getContentHandler();
	}

	public void run(File srcFile) throws RuntimeException {
		try {
			validator.reset();
			xmlReader.setContentHandler(validator.getContentHandler());
			DTDHandler dtdHandler = validator.getDTDHandler();
			if (dtdHandler != null)
				xmlReader.setDTDHandler(dtdHandler);
			xmlReader.parse(srcFile.toURI().toURL().toString());
		} catch (SAXException e) { // SAX exception means parsing wasn't successful for one file, can continue
			logger.logException(e, false);
		} catch (Exception e) {    // other errors means something is wrong and we better stop
			throw new RuntimeException(e); // will be logged in main try/catch block
		}
	}
}