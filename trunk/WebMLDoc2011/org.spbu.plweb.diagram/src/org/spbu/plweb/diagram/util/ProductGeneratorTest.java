package org.spbu.plweb.diagram.util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.spbu.plweb.diagram.util.projects.ProjectOperationException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProductGeneratorTest {

	@Test
	public void testGenerateDocumentation() throws ProjectOperationException {
		Map<String, String> topics = new HashMap<String, String>();
		topics.put("1", "1");
		topics.put("4", "4");

		List<String> allInfElements = new ArrayList();
		allInfElements.add("1");
		allInfElements.add("2");
		allInfElements.add("3");
		allInfElements.add("4");

		String docPath = "D:\\KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK\\workspace\\doc";
		String projectName = "NYA";

		String productDocFilePath = docPath + "\\" + projectName
				+ ".userManual.drl";

		generateDoc(topics, allInfElements, projectName, productDocFilePath);
	}

	protected void generateDoc(Map<String, String> topics,
			List<String> allInfElements, String projectName,
			String productDocFilePath)
			throws TransformerFactoryConfigurationError,
			ProjectOperationException {
		File productDocFile = new File(productDocFilePath);
		if (productDocFile.exists()) {
			productDocFile.delete();
		}
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			DOMImplementation domImpl = docBuilder.getDOMImplementation();
			Document doc = domImpl.createDocument(null, null, null);
			doc.setXmlStandalone(true);

			Element productDocumentationElement = doc
					.createElement("d:ProductDocumentation");
			doc.appendChild(productDocumentationElement);

			Element finalInfPriductElement = doc
					.createElement("d:FinalInfProduct");
			finalInfPriductElement.setAttribute("id", projectName);
			finalInfPriductElement.setAttribute("infproductid", projectName);
			productDocumentationElement.appendChild(finalInfPriductElement);

			for (String infElement : allInfElements) {
				if (topics.containsKey(infElement)) {
					Element el = doc.createElement("d:Adapter");
					el.setAttribute("infelemrefid", infElement);
					finalInfPriductElement.appendChild(el);
				}
			}

			DOMSource domSource = new DOMSource(doc);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer
					.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult sr = new StreamResult(new File(productDocFilePath));
			transformer.transform(domSource, sr);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ProjectOperationException(
					"Error occured while creating product diagram");
		}
	}

}
