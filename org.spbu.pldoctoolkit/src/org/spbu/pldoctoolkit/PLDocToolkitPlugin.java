package org.spbu.pldoctoolkit;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class PLDocToolkitPlugin extends AbstractUIPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "org.spbu.pldoctoolkit";

	// The shared instance
	private static PLDocToolkitPlugin plugin;
	
	public PLDocToolkitPlugin() {
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static PLDocToolkitPlugin getDefault() {
		return plugin;
	}
	
	public static URL getBundleResourceURL(String relativePath) {
		try {
			return new URL(getDefault().getBundle().getEntry("/"), relativePath);
		} catch (MalformedURLException e) {
			return null;
		}
	}
}
