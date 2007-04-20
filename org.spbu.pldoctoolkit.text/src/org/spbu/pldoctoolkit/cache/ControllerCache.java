package org.spbu.pldoctoolkit.cache;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.Controller;
import net.sf.saxon.FeatureKeys;

public class ControllerCache {
	private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();
	
	static {
		TRANSFORMER_FACTORY.setAttribute(FeatureKeys.LINE_NUMBERING, true);
	}
	
	private Map<String, Controller> controllerMap = new HashMap<String, Controller>();
	
	public Controller getController(URL url) throws TransformerConfigurationException {
		String urlString = url.toString();
		Controller controller = controllerMap.get(urlString);
		if (controller == null) {
			controller = (Controller) TRANSFORMER_FACTORY.newTransformer(new StreamSource(urlString));
			controllerMap.put(urlString, controller);
		}
		return controller;
	}
}
