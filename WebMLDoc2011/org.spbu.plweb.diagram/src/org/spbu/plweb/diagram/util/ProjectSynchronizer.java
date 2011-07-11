package org.spbu.plweb.diagram.util;

import org.spbu.plweb.diagram.util.projects.ProjectOperationException;
import org.spbu.plweb.diagram.util.projects.ProjectPlweb;
import org.spbu.plweb.diagram.util.projects.ProjectWebRatio;

public class ProjectSynchronizer {

	private ProjectWebRatio projectWr = null;

	private ProjectPlweb projectPw = null;

	private String docPath = null;

	public ProjectSynchronizer(String pathWr, String pathPw, String docPath)
			throws ProjectOperationException {
		try {
			projectWr = new ProjectWebRatio(pathWr);
			projectPw = new ProjectPlweb(pathPw);
			this.docPath = docPath;
		} catch (ProjectOperationException e) {
			throw new ProjectOperationException(
					"Couldn't create ProjectSyncronizer instance");
		}
	}

	public boolean synchronizePw() throws ProjectOperationException {
		if (!projectPw.exists()) {
			try {
				projectPw.create();
				projectPw.importProject(projectWr, docPath );
			} catch (ProjectOperationException e) {
				projectPw.delete();
				throw e;
			}
		} else {
			projectPw.update(projectWr, docPath);
		}
		return true;
	}

	public boolean synchronizeWr() throws ProjectOperationException {
		projectWr.update(projectPw);
		return true;
	}

}
