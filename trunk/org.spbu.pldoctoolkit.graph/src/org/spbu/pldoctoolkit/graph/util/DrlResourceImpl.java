/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.spbu.pldoctoolkit.graph.Plugin;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 * @see org.spbu.pldoctoolkit.graph.util.DrlResourceFactoryImpl
 * @generated NOT
 */
public class DrlResourceImpl extends XMLResourceImpl {
	public static String XMI2DRL_FILE = "/xsl/xmi2drl.xsl";
	public static String DRL2XMI_FILE = "/xsl/drl2xmi.xsl";
	
	private Document drlDocument;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param uri the URI of the new resource.
	 * @generated
	 */
	public DrlResourceImpl(URI uri) {
		super(uri);
	}

	/**
	 * @return the drlDocument
	 */
	public Document getDrlDocument() {
		return drlDocument;
	}

	/**
	 * step 1: xslt-process the file, output - byte array buf
	 * step 2: load the doc from the byte buf into DOM 
	 * 
	 * TODO: first load doc into DOM, then xslt (using DOMSource and DOMResult) 
	 * 
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#doLoad(java.io.InputStream,
	 *      java.util.Map)
	 */
	public void doLoad(InputStream inputStream, Map<?, ?> options)
			throws IOException {
		
		try {
			InputStream stylesheetStream = Plugin.getResourceURL(DRL2XMI_FILE).openStream();
			StreamSource styleSource = new StreamSource(stylesheetStream);
			
			StreamSource fileSource = new StreamSource(inputStream);
			
			ByteArrayOutputStream xslResultStream = new ByteArrayOutputStream();
			StreamResult xslResult = new StreamResult(xslResultStream);
			
			TransformerFactory transFactory = TransformerFactory.newInstance(
					Plugin.JAXP_PROPERTIES.getProperty(Plugin.JAXP_PROPERTY_TRANSFORMER_FACTORY),
					this.getClass().getClassLoader());
			Transformer transformer = transFactory.newTransformer(styleSource);

			transformer.transform(fileSource, xslResult);
			
			ByteArrayInputStream xslResultAsInput = new ByteArrayInputStream(xslResultStream.toByteArray());
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(
					Plugin.JAXP_PROPERTIES.getProperty(Plugin.JAXP_PROPERTY_DOCUMENT_BUILDER_FACTORY),
					this.getClass().getClassLoader());
			factory.setValidating(false);
			DocumentBuilder builder = factory.newDocumentBuilder();

			drlDocument = builder.parse(xslResultAsInput); 
			
			super.doLoad(drlDocument, options);
		} catch (ParserConfigurationException e) {
			throw new IOException(e);
		} catch (SAXException e) {
			throw new IOException(e);
		} catch (TransformerConfigurationException e) {
			throw new IOException(e);
		} catch (TransformerException e) {
			throw new IOException(e);
		}
	}

	/**
	 * 1 step: save the doc to a byte[] buffer 2 step: apply xslt to the buffer
	 * and stream the result to the outputStream
	 * 
	 * The more obvious attempt to use a single Transformer to transform the
	 * document right to the outputStream failed because it caused a strange
	 * exception.
	 * 
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#doSave(java.io.OutputStream,
	 *      java.util.Map)
	 */
	public void doSave(OutputStream outputStream, Map<?, ?> options)
			throws IOException {
		
		try {
			DOMSource source = new DOMSource(drlDocument);
			StreamResult result = new StreamResult(outputStream);

			InputStream stylesheetStream = Plugin.getResourceURL(XMI2DRL_FILE).openStream();
			StreamSource stylesource = new StreamSource(stylesheetStream); 
			
			TransformerFactory transFactory = TransformerFactory.newInstance(
					Plugin.JAXP_PROPERTIES.getProperty(Plugin.JAXP_PROPERTY_TRANSFORMER_FACTORY),
					this.getClass().getClassLoader());
			Transformer docSerializer = transFactory.newTransformer(); // stylesource

			ByteArrayOutputStream docOutStream = new ByteArrayOutputStream();
			StreamResult docStreamResult = new StreamResult(docOutStream);
			docSerializer.transform(source, docStreamResult);
			
			Transformer docTransformer = transFactory.newTransformer(stylesource);
			StreamSource docInStream = new StreamSource(new ByteArrayInputStream(docOutStream.toByteArray()));
			docTransformer.transform(docInStream, result);
			
		} catch (TransformerConfigurationException e) {
			throw new IOException(e);
		} catch (TransformerFactoryConfigurationError e) {
			throw new IOException(e);
		} catch (TransformerException e) {
			throw new IOException(e);
		}
	}

	@Override
	protected XMLLoad createXMLLoad() {
		return new DrlXMLLoadImpl(createXMLHelper());
	}
	
} // DrlResourceImpl
