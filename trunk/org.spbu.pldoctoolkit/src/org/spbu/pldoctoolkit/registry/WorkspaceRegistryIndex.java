package org.spbu.pldoctoolkit.registry;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;

public class WorkspaceRegistryIndex {
	private final Map<String, ProjectRegistryImpl> registryMap = new HashMap<String, ProjectRegistryImpl>();
	
	public WorkspaceRegistryIndex(IWorkspace workspace) throws CoreException {
		IProject[] projects = workspace.getRoot().getProjects();
		for (IProject project: projects) {
			if (project.isOpen())
				getRegistryImpl(project.getName()).refreshContainer(project);
		}
		workspace.addResourceChangeListener(new IResourceChangeListener() {
			public void resourceChanged(IResourceChangeEvent event) {
				try {
					event.getDelta().accept(new IResourceDeltaVisitor() {
						public boolean visit(IResourceDelta delta) throws CoreException {
							return processResourceDelta(delta);
						}
					});
				} catch (CoreException e) {
					throw new InternalError();
				}
			}
		});
	}
		
	public ProjectRegistry getRegistry(String projectName) {
		return getRegistryImpl(projectName);
	}

	private ProjectRegistryImpl getRegistryImpl(String projectName) {
		ProjectRegistryImpl registry = registryMap.get(projectName);
		if (registry == null) {
			registry = new ProjectRegistryImpl();
			registryMap.put(projectName, registry);
		}
		return registry;
	}
	
	boolean processResourceDelta(IResourceDelta delta) throws CoreException {
		IResource resource = delta.getResource();
		System.out.println("processing " + resource.getLocationURI());
		if (resource instanceof IContainer) {
			if (resource instanceof IProject) {
				IProject project = (IProject) resource;
				if (delta.getKind() == IResourceDelta.REMOVED) {
					registryMap.remove(project.getName());
					return false;
				} else if (delta.getKind() == IResourceDelta.CHANGED && (delta.getFlags() & IResourceDelta.OPEN) != 0){
					getRegistryImpl(project.getName()).refreshContainer(project);
					return false;
				}
			}
			return true;
		}
		if (resource instanceof IFile) {
			IFile file = (IFile) resource;
			String ext = file.getFileExtension();
			if (ext != null && ext.toLowerCase().equals(PLDocToolkitPlugin.DRL_FILE_EXTENSION)) {
				IProject project = file.getProject();
				getRegistryImpl(project.getName()).refreshFile(file);
			}
		}
		return false;
	}
}




