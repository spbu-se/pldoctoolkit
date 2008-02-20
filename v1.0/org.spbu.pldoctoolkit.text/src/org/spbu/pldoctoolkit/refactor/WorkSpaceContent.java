package org.spbu.pldoctoolkit.refactor;

import java.util.HashMap;

import org.eclipse.core.resources.IProject;

public class WorkSpaceContent {
	public static HashMap<IProject ,ProjectContent> projects = new HashMap<IProject, ProjectContent>();
	
	public static ProjectContent getProject(IProject project) {
		ProjectContent projectContent = projects.get(project);
		if (project == null)
			return projects.put(project, new ProjectContent());
		else
			return projectContent;
	}
}
