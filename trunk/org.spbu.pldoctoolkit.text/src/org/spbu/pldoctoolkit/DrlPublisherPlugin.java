package org.spbu.pldoctoolkit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class DrlPublisherPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.spbu.pldoctoolkit.text";

	// The shared instance
	private static DrlPublisherPlugin plugin;
	
	/**
	 * The constructor
	 */
	public DrlPublisherPlugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static DrlPublisherPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public static URL getURL(String relativePath) {
		try {
			return new URL(getDefault().getBundle().getEntry("/"), relativePath);
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public static Shell getShell() {
		return getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
	}
	
	public String getPath() {
		try {
			String s = getResource("/");
			if (s == null)
				s = "";
			s = (!s.endsWith("/")) ? s += "/" : s;
			return s;
		} catch (Exception e) {
		}
		return "";
	}


	public String getResource(String path) {
		try {
			return new Path(FileLocator.resolve(
					FileLocator.find(plugin.getBundle(), new Path(path), new HashMap()))
					.getFile()).toFile().toString();
		} catch (IOException e) {
		}
		return null;
	}
}
