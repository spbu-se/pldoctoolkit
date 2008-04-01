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
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;

public class WorkspaceRegistryIndex {
	private final Map<String, ProjectRegistryImpl> registryMap = new HashMap<String, ProjectRegistryImpl>();
	private IProjectContentCreator projectContentCreator = null;
	
	public WorkspaceRegistryIndex(IWorkspace workspace) throws CoreException {
		IProject[] projects = workspace.getRoot().getProjects();
		for (IProject project: projects) {
			if (project.isOpen())
				getRegistryImpl(project).refreshContainer(project);
		}
		workspace.addResourceChangeListener(new IResourceChangeListener() {
			public void resourceChanged(IResourceChangeEvent event) {
				try {
					IResourceDelta delta = event.getDelta();
					if (delta == null)
						return;
					delta.accept(new IResourceDeltaVisitor() {
						public boolean visit(IResourceDelta delta) throws CoreException {
							return processResourceDelta(delta);
						}
					});
				} catch (CoreException e) {
					// ignore
				}
			}
		});
	}
		
	public ProjectRegistry getRegistry(String projectName) {
		//return getRegistryImpl(project);
		ProjectRegistryImpl registry = registryMap.get(projectName);
		if (registry == null)
			throw new RuntimeException();
		
		return registry;
	}

	private ProjectRegistryImpl getRegistryImpl(IProject project) {
		ProjectRegistryImpl registry = registryMap.get(project.getName());
		if (registry == null) {
			registry = new ProjectRegistryImpl();
			registry.project = project;
			registryMap.put(project.getName(), registry);
			if (projectContentCreator != null)
				registry.projectContent = projectContentCreator.create(registry);
		}
		return registry;
	}
	
	boolean processResourceDelta(IResourceDelta delta) throws CoreException {
		IResource resource = delta.getResource();
		if (resource instanceof IContainer) {
			if (resource instanceof IProject) {
				IProject project = (IProject) resource;
				if (delta.getKind() == IResourceDelta.REMOVED) {
					registryMap.remove(project.getName());
					System.out.print("2");
					return false;
				} else if (delta.getKind() == IResourceDelta.CHANGED && (delta.getFlags() & IResourceDelta.OPEN) != 0){
					System.out.print("3");
					getRegistryImpl(project).refreshContainer(project);
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
				ProjectRegistryImpl registry = (ProjectRegistryImpl)getRegistryImpl(project);
				registry.refreshFile(file);
				
				if (registry.projectContent != null) {
					if (delta.getKind() == IResourceDelta.ADDED)
						registry.projectContent.add(file);
					else if (delta.getKind() == IResourceDelta.CHANGED)
						registry.projectContent.change(file);
					else if (delta.getKind() == IResourceDelta.REMOVED)
						registry.projectContent.remove(file);
				}
			}
		}
		return false;
	}
	
	public void setProjectContentCreator(IProjectContentCreator projectContentCreator) {
		this.projectContentCreator = projectContentCreator;
		
		for (ProjectRegistryImpl registryImpl : registryMap.values()) {
			registryImpl.projectContent = projectContentCreator.create(registryImpl);
		
			try {
				registryImpl.project.accept(new IResourceVisitor() {
					public boolean visit(IResource resource) /*throws CoreException */{
						if (resource instanceof IFile) {			
							IFile file = (IFile) resource;
							String ext = file.getFileExtension();
							if (ext != null && ext.toLowerCase().equals(PLDocToolkitPlugin.DRL_FILE_EXTENSION)) {			
								((ProjectRegistryImpl)getRegistryImpl(file.getProject())).projectContent.add(file);							
							}
						}
						return true;
					}
				});			
			} catch (CoreException e) {
				// ignore
			}
		}
	}
	
	public IProjectContentCreator getProjectContentCreator() {
		return projectContentCreator;
	}		
}




