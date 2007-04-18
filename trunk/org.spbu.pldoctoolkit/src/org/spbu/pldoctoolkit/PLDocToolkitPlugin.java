package org.spbu.pldoctoolkit;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.spbu.pldoctoolkit.registry.ProjectRegistry;
import org.spbu.pldoctoolkit.registry.WorkspaceRegistryIndex;

public class PLDocToolkitPlugin extends AbstractUIPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "org.spbu.pldoctoolkit";

	public static final String DRL_FILE_EXTENSION = "drl";

	// The shared instance
	private static PLDocToolkitPlugin plugin;
	
	private final WorkspaceRegistryIndex registryIndex;
	
	public PLDocToolkitPlugin() throws CoreException {
		plugin = this;
		registryIndex = new WorkspaceRegistryIndex(ResourcesPlugin.getWorkspace());
	}

	@Override
	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		super.start(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}
	
	public static ProjectRegistry getRegistry(String projectName) {
		return plugin.registryIndex.getRegistry(projectName);
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
