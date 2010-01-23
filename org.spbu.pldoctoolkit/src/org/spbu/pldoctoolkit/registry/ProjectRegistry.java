package org.spbu.pldoctoolkit.registry;

import java.util.List;

import org.eclipse.core.resources.IFile;

public interface ProjectRegistry {
	public RegisteredLocation getRegisteredLocation(String uri);
	public String getContext(IFile file);
	public List<RegisteredLocation> findForId(String id);
	public List<RegisteredLocation> findForType(String type);
	public List<RegisteredLocation> findForFile(IFile file);
	public List<RegisteredLocation> findAll();
	public IProjectContent getProjectContent();
}
