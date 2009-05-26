package org.spbu.publishutil.cache;

import java.io.File;
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
	
	private Map<File, Controller> controllerMap = new HashMap<File, Controller>();
	
	public Controller getController(File file) throws TransformerConfigurationException {
		Controller controller = controllerMap.get(file);
		if (controller == null) {
			controller = (Controller) TRANSFORMER_FACTORY.newTransformer(new StreamSource(file));
			controllerMap.put(file, controller);
		}
		return controller;
	}
}
