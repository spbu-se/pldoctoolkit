package org.spbu.pldoctoolkit.registry;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public abstract class ResourceRegistry {
	private static final ResourceRegistry registry = new ResourceRegistryImpl();

	public static ResourceRegistry getInstance() {
		return registry;
	}

	public abstract IResource getResource(String drlresolveURL);

	public abstract void refresh(IContainer container) throws CoreException;
}
