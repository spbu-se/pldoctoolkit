package org.spbu.pldoctoolkit.exportutil;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.Configuration;
import net.sf.saxon.Controller;
import net.sf.saxon.StandardURIResolver;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.value.SequenceExtent;
import net.sf.saxon.value.SingletonNode;
import net.sf.saxon.value.Value;

import org.spbu.pldoctoolkit.exportutil.cache.ControllerCache;
import org.spbu.pldoctoolkit.exportutil.cache.SchemaCache;
import org.spbu.pldoctoolkit.exportutil.utils.DocbookContentHandler;
import org.spbu.pldoctoolkit.exportutil.utils.EventLogger;
import org.spbu.pldoctoolkit.exportutil.utils.ProjectRegistry;
import org.spbu.pldoctoolkit.exportutil.utils.RegisteredLocation;
import org.xml.sax.DTDHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import com.thaiopensource.validate.Validator;
import com.thaiopensource.xml.sax.Jaxp11XMLReaderCreator;

public class BasicExportAction {
	protected static final ControllerCache CONTROLLER_CACHE = new ControllerCache();
	protected static final SchemaCache SCHEMA_CACHE = new SchemaCache();
	
	protected static final File DRL2DOCBOOK_FILE;
	protected static final File DOCBOOK2HTML_FILE;
	protected static final File DOCBOOK_SCHEMA_FILE;
	
	static {
		DRL2DOCBOOK_FILE = new File(ExportUtil.getRunningAppPath() + "/xsl/drl/drl2docbook.xsl");
		DOCBOOK2HTML_FILE = new File(ExportUtil.getRunningAppPath() + "/xsl/docbook/html/docbook.xsl");
		DOCBOOK_SCHEMA_FILE = new File(ExportUtil.getRunningAppPath() + "/schema/docbook/docbook.rng");
	}

	protected final static int drlSeverity = 0;
	
	protected static String firstErrorMessage;
	protected static NodeInfo firstErrorNode;

	protected final Controller drl2docbook;
	protected final XMLReader xmlReader;
	protected final Validator validator;
	protected final Boolean bTransform2DBOnly;
	
	private final File docbook2formatFile;
	private final File destinationFile;
	
	protected ProjectRegistry registry;
	protected EventLogger logger;
	protected Controller docbook2type;
	protected DocbookContentHandler contentHandler;
	protected String fipId;
	
	protected final URIResolver uriResolver = new StandardURIResolver(new Configuration()) {
		private static final long serialVersionUID = -7919352677909462305L;
		public Source resolve(String href, String base) throws XPathException {
			if (registry != null) {
				RegisteredLocation loc = registry.getRegisteredLocation(href);
				if (loc != null)
					return new StreamSource(loc.getFile().toURI().toString());
			}
			return super.resolve(href, base);
		}
	};

	protected final ErrorHandler errorHandler = new ErrorHandler() {
		public void error(SAXParseException exception) throws SAXException {
			logger.logError(contentHandler.getSystemId(), contentHandler.getLineNumber(), 
					exception.getMessage() + " (" + exception.getLineNumber() + ")", EventLogger.SEVERITY_ERROR);
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			logger.logError(contentHandler.getSystemId(), contentHandler.getLineNumber(), 
					exception.getMessage() + " (" + exception.getLineNumber() + ")", EventLogger.SEVERITY_ERROR);
		}

		public void warning(SAXParseException exception) throws SAXException {
			logger.logError(contentHandler.getSystemId(), contentHandler.getLineNumber(), 
					exception.getMessage() + " (" + exception.getLineNumber() + ")", EventLogger.SEVERITY_WARNING);
		}
	};

	protected final ErrorListener errorListener = new ErrorListener() {
		public void error(TransformerException exception) throws TransformerException {
			processTransformerException(exception, EventLogger.SEVERITY_ERROR);
		}

		public void fatalError(TransformerException exception) throws TransformerException {
			processTransformerException(exception, EventLogger.SEVERITY_ERROR);
		}

		public void warning(TransformerException exception) throws TransformerException {
			processTransformerException(exception, EventLogger.SEVERITY_WARNING);
		}
	};
	
	/**
	 * Parses error and decides if it is only a warning, according to current project settings 
	 * @param error message
	 * @param node, where error happened
	 * @param severity of the 
	 */
	public static void parseError(String message, NodeInfo node, int severity) {
		XPathException de = new XPathException(message);
		de.setErrorObject(new SingletonNode(node));
		try {
			Controller drl2docbook = CONTROLLER_CACHE.getController(DRL2DOCBOOK_FILE);
			if (severity <= drlSeverity) {
				if (firstErrorMessage == null) {
					firstErrorMessage = message;
					firstErrorNode = node;
				} else {
					drl2docbook.getErrorListener().error(de);
				}
			} else {
				drl2docbook.getErrorListener().warning(de);
			}
		} catch (Exception e) {}
	}
	
	public static boolean isErrorHappened() {
		return firstErrorMessage != null;
	}
	
	public static String getFirstErrorMessage() {
		return firstErrorMessage;
	}
	
	public static NodeInfo getFirstErrorNode() {
		return firstErrorNode;
	}
	
	public void setRegistry(ProjectRegistry registry) {
		this.registry = registry;
	}
	
	public void setLogger(EventLogger logger) {
		this.logger = logger;
	}
	
	public BasicExportAction(File destinationFile, File docbook2formatFile, Boolean bTransform2DBOnly) throws Exception {
	
		this.destinationFile = destinationFile;
		
		this.docbook2formatFile = docbook2formatFile;
		this.bTransform2DBOnly = bTransform2DBOnly;

		// transformers
		drl2docbook = CONTROLLER_CACHE.getController(DRL2DOCBOOK_FILE);

		// validator
		validator = SCHEMA_CACHE.getValidator(DOCBOOK_SCHEMA_FILE, errorHandler);
		xmlReader = new Jaxp11XMLReaderCreator().createXMLReader();
	}

	protected Controller getDocbookTransformer() throws TransformerConfigurationException {
		if (docbook2type == null)
			docbook2type = CONTROLLER_CACHE.getController(docbook2formatFile);
		return docbook2type;
	}
	
	public void run(File file) throws ExportException {
		this.run(file, null);
	}
	
	public void run(File file, String fipIdExt) throws ExportException {
		try {
			List<RegisteredLocation> list = registry.findForFile(file);
			List<RegisteredLocation> fipList = new ArrayList<RegisteredLocation>();
			for (RegisteredLocation loc: list) {
				if (RegisteredLocation.FINAL_INF_PRODUCT.equals(loc.getType()))
					fipList.add(loc);
			}

			fipId = null;
			if (fipList.isEmpty())
				throw new ExportException("Couldn't find any final inf product in specified file");
			else if (fipList.size() == 1) {
				if (fipIdExt == null || fipList.get(0).getId().equalsIgnoreCase(fipIdExt))
					fipId = fipList.get(0).getId();
			} else {
				for (RegisteredLocation loc: fipList ) {
					if (loc.getId().equalsIgnoreCase(fipIdExt))
						fipId = fipIdExt;
				}
			}
			
			if (fipId == null)
				throw new ExportException("Couldn't select any final inf product");

			doTransform(file, destinationFile);

		} catch (InvocationTargetException e) {
//			throw new ExportException(e.getMessage());
			// ignore
		} catch (Exception e) {
			throw new ExportException(e.getMessage());
		}
	}

	protected void doTransform(File source, File result) throws InvocationTargetException {
		if (source == null || result == null)
			return;

		File fileDB = null;
		try {
			logger.logEvent("Exporting to html...");
			if (bTransform2DBOnly) {
				fileDB = result;
			} else {
				fileDB = File.createTempFile("docbookgen", null);
			}

			logger.logEvent("Transforming DRL -> DocBook...");
			firstErrorMessage = null;
			firstErrorNode = null;
			drl2docbook.reset();
			drl2docbook.setParameter("finalinfproductid", fipId);
			transform(drl2docbook, new StreamSource(source.toURI().toString()), new StreamResult(fileDB));
			logger.logEvent("Done");

			logger.logEvent("Validating DocBook...");
			contentHandler = new DocbookContentHandler(validator.getContentHandler());
			xmlReader.setContentHandler(contentHandler);
			DTDHandler dtdHandler = validator.getDTDHandler();
			if (dtdHandler != null)
				xmlReader.setDTDHandler(dtdHandler);
			xmlReader.parse(fileDB.getAbsolutePath());
			logger.logEvent("Done");

			if (!bTransform2DBOnly) {
				logger.logEvent("Transforming DocBook -> html...");
				getDocbookTransformer().reset();
				transform(getDocbookTransformer(), new StreamSource(fileDB), new StreamResult(result));
			}
			
			logger.logEvent("Done");
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		} finally {
			validator.reset();
			if (!bTransform2DBOnly && (fileDB != null)) {
				fileDB.deleteOnExit();
			}
		}
	}

	protected void transform(Controller transformer, Source source, Result result) throws TransformerException {
		transformer.clearDocumentPool();
		transformer.setErrorListener(errorListener);
		transformer.setURIResolver(uriResolver);
		transformer.transform(source, result);
	}

	private void processTransformerException(TransformerException e, int severity) throws XPathException {
		if (e instanceof XPathException && !((XPathException) e).isStaticError()) {
			processXPathException((XPathException)e, severity);
		} else {
			SourceLocator loc = e.getLocator();
			logger.logError(loc.getSystemId(), loc.getLineNumber(), e.getMessage(), severity);
			e.printStackTrace();
		}
	}

	private void processXPathException(XPathException xpathError, int severity) throws XPathException {
		Value errorObject = xpathError.getErrorObject();
		if (errorObject instanceof SingletonNode) {
			NodeInfo node = ((SingletonNode) errorObject).getNode();
			logger.logError(node.getSystemId(), node.getLineNumber(), xpathError.getMessage(), severity);
		} else if (errorObject instanceof SequenceExtent) {
			SequenceExtent seq = (SequenceExtent) errorObject;
			SequenceIterator it = seq.iterate(xpathError.getXPathContext());
			Item item;
			while ((item = it.next()) != null)
				if (item instanceof NodeInfo) {
					NodeInfo node = (NodeInfo) item;
					logger.logError(node.getSystemId(), node.getLineNumber(), xpathError.getMessage(), severity);
				}
		} else {
			String file = xpathError.getLocator().getSystemId();
			int line = xpathError.getLocator().getLineNumber();
			logger.logError(file, line, xpathError.getMessage(), EventLogger.SEVERITY_ERROR);
		}
	}
}
