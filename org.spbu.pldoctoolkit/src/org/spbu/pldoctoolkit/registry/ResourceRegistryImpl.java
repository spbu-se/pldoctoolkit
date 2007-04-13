package org.spbu.pldoctoolkit.registry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

class ResourceRegistryImpl extends ResourceRegistry {
	private static final String DRLRESOLVE_PREFIX = "drlresolve://";
	private static final String CORE = "Core";
	private static final String INF_ELEMENT = "InfElement";
	private static final String INF_PRODUCT = "InfProduct";
	private static final String DICTIONARY = "Dictionary";
	private final Map<String, IFile> fileMap = new HashMap<String, IFile>();
	
	public IResource getResource(String drlresolveURL) {
		if (!drlresolveURL.startsWith(DRLRESOLVE_PREFIX))
			return null;
		String path = drlresolveURL.substring(DRLRESOLVE_PREFIX.length());
		String[] elements = path.split("/");
		
		if (elements.length != 3)
			return null;
		
		if (CORE.equals(elements[0]))
			return fileMap.get(path);
		
		IResource res = fileMap.get(path);
		if (res != null)
			return res;
		
		return fileMap.get(CORE + "/" + elements[1] + "/" + elements[2]);
	}
	
	public void refresh(IContainer container) throws CoreException {
		IPath location = container.getLocation();
		for (Iterator<IFile> it = fileMap.values().iterator(); it.hasNext();) {
			IFile file = it.next();
			if (location.isPrefixOf(file.getLocation()))
				it.remove();
		}
		container.accept(new IResourceVisitor() {
			public boolean visit(IResource resource) throws CoreException {
				if (resource instanceof IFile) {
					IFile file = (IFile) resource;
					if (file.getName().endsWith(".drl")) {
						// TODO: we need to parse this file to get an idea of what's inside,
						// and after that place it into the fileMap
						int id = 0;
						fileMap.put(CORE + "/" + INF_ELEMENT + "/" + id, file); 
					}
					return false;
				}
				return true;
			}
		});
	}
}
