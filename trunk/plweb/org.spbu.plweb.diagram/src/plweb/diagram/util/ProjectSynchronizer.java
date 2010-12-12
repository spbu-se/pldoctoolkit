package plweb.diagram.util;

import plweb.diagram.util.projects.ProjectOperationException;
import plweb.diagram.util.projects.ProjectPlweb;
import plweb.diagram.util.projects.ProjectWebRatio;

public class ProjectSynchronizer {

	private ProjectWebRatio projectWr = null;

	private ProjectPlweb projectPw = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Wrong input");
			return;
		}

		try {
			ProjectSynchronizer instance = new ProjectSynchronizer(args[0],
					args[1]);
			if (args[2].equals("wr")) {
				instance.synchronizeWr();
			} else {
				instance.synchronizePw();
			}
		} catch (ProjectOperationException e) {
			e.printStackTrace();
		}
		
		System.out.println("Done");
	}

	public ProjectSynchronizer(String pathWr, String pathPw)
			throws ProjectOperationException {
		try {
			projectWr = new ProjectWebRatio(pathWr);
			projectPw = new ProjectPlweb(pathPw);
		} catch (ProjectOperationException e) {
			throw new ProjectOperationException(
					"Couldn't create ProjectSyncronizer instance");
		}
	}

	public boolean synchronizePw() throws ProjectOperationException {
		if (!projectPw.exists()) {
			try {
				projectPw.create();
				projectPw.importProject(projectWr);
			} catch (ProjectOperationException e) {
				projectPw.delete();
				throw e;
			}
		} else {
			projectPw.update(projectWr);
		}
		return true;
	}

	public boolean synchronizeWr() throws ProjectOperationException {
		projectWr.update(projectPw);
		return true;
	}

}
