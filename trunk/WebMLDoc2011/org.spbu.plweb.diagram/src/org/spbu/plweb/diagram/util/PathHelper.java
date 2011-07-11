package org.spbu.plweb.diagram.util;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

public class PathHelper {
	
	private PathHelper() {}
	
	public static String normalize(String str) {
		return str.replace("\\", "/");
	}
	
	public static String getWorkspaceProjectPath(String projectName) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IPath location = root.getLocation();
		return location + "/" + projectName;
	}
	
	public static String getProjectFile(String projectName, String fileName) {
		return "/" + projectName + "/" + fileName;
	}
	
	public static String getProjectName(String resourcePath) {
		return resourcePath.substring(1, resourcePath.indexOf("/", 1));
	}
	
	public static String getFileNameFromPath(String path) {
		return path.substring(path.lastIndexOf("/") + 1);
	}
	
	public static String getProductName(String resourcePath) {
		return resourcePath.substring(resourcePath.lastIndexOf("/") + 1,
				resourcePath.substring(0, resourcePath.lastIndexOf("."))
						.lastIndexOf("."));
	}
}
