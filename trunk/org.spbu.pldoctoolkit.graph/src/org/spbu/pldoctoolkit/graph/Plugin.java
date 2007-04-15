/**
 * No license restriction - you may apply any you like.
 * 
 * First created 13.04.2007 23:28:04 using Eclipse IDE.
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * Class Plugin.
 *
 * @author Alexey Semenov
 * @version 1.0
 */
public class Plugin extends AbstractUIPlugin {

	public static final String JAXP_PROPERTIES_FILE = "/jaxp.properties";
	public static final Properties JAXP_PROPERTIES = new Properties();
	
	public static final String JAXP_PROPERTY_TRANSFORMER_FACTORY = "javax.xml.transform.TransformerFactory";
	public static final String JAXP_PROPERTY_DOCUMENT_BUILDER_FACTORY = "javax.xml.parsers.DocumentBuilderFactory";
	
	private static Plugin instance;
	
	public Plugin() {
	}
	
	public static Plugin getInstance() {
		return instance;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		instance = this;
		setJaxpProps();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		instance = null;
	}
	
	private void setJaxpProps() {
		try {
			InputStream jaxpPropsInputStream = Plugin.getInstance().getBundle()
				.getEntry(JAXP_PROPERTIES_FILE).openStream();
			
			JAXP_PROPERTIES.load(jaxpPropsInputStream);
		} catch (IOException e) {
			System.err.println("Failed to initialize JAXP props from file " + JAXP_PROPERTIES_FILE + ", using defaults");
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns URL of the resource in the plugin corresponding to the path provided.
	 * 
	 * Example: "/xsl/test.drl"
	 * 
	 * @param pathInPlugin
	 * @return 
	 */
	public static URL getResourceURL(String pathInPlugin) {
		return instance.getBundle().getEntry(pathInPlugin);
	}
}
