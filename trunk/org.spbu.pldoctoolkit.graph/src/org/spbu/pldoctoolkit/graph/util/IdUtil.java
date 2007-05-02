package org.spbu.pldoctoolkit.graph.util;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.graph.DrlGraphPlugin;
import org.spbu.pldoctoolkit.registry.RegisteredLocation;

public class IdUtil {
	
	public static final char ID_SEPARATOR_CHAR = '#';

	public static String idToUriString(String projectName, String id) {
		List<RegisteredLocation> knownLocations = 
			PLDocToolkitPlugin.getRegistry(projectName).findAll();

		RegisteredLocation currentLocation = null;
		for(RegisteredLocation location : knownLocations) {
			if(id.equals(location.getId())) {
				currentLocation = location;
				break;
			}
		}
		
		String uriResult = "";
		if(currentLocation != null) {
			IFile file = currentLocation.getFile();
			
			String filePath = file.getProjectRelativePath().toString();
			
			uriResult = filePath + ID_SEPARATOR_CHAR + id;
		}
		
		DrlGraphPlugin.logInfo("id " + id + " resolved as " + uriResult);
		
		return uriResult;
	}
	
	/**
	 * Returns fragment part of the uri.
	 * 
	 * @param uriString
	 * @return
	 */
	public static String uriStringToId(String uriString) {
		int idIndex = uriString.indexOf(ID_SEPARATOR_CHAR) + 1;
		String id = "";
		if(idIndex != 0) {
			id = uriString.substring(idIndex);
		}
		
		return id;
	}
	
	public static String parseProjectName(String xmlSystemId) {
		//TODO
		
		DrlGraphPlugin.logInfo("got system id: " + xmlSystemId);
		
		return "test";
	}
}
