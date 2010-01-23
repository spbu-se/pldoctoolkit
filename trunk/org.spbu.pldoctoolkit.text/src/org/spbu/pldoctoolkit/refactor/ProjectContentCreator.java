package org.spbu.pldoctoolkit.refactor;

import org.spbu.pldoctoolkit.registry.IProjectContent;
import org.spbu.pldoctoolkit.registry.IProjectContentCreator;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;

public class ProjectContentCreator implements IProjectContentCreator{
	public IProjectContent create(ProjectRegistryImpl projectRegistry) {
		return new ProjectContent();
	}
}
