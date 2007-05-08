package org.spbu.pldoctoolkit.graph.util;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.graph.DrlGraphPlugin;
import org.spbu.pldoctoolkit.registry.RegisteredLocation;

public class IdUtil {
	
	public static final char ID_SEPARATOR_CHAR = '#';
	
	private static int lastGeneratedId = 0;

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
		} else {
			DrlGraphPlugin.logError("no element found in project '" + projectName
					+ "' for id '" + id + "'");
		}
		
		DrlGraphPlugin.logInfo("id " + id + " resolved as " + uriResult);
		
		return uriResult;
	}
	
	/**
	 * Returns fragment part of the uri.
	 * 
	 * @param uriString
	 * @return
	 * 
	 * @throws IllegalArgumentException if the <code>uriString</code> is not empty and does not
	 * contain ID part
	 */
	public static String uriStringToId(String uriString) {
		int idIndex = uriString.indexOf(ID_SEPARATOR_CHAR) + 1;

		if(idIndex == 0 && !"".equals(uriString)) {
			throw new IllegalArgumentException("could not parse uri '" + uriString + "': ID part not found");
		}
			
		String id = uriString.substring(idIndex);
		
		return id;
	}

	/**
	 * If the object has an attribute marked as ID, initializes it with a value.<p>
	 * 
	 * TODO this value must be unique at least across the project.
	 * 
	 * @param obj The object whose ID to initialize
	 */
	public static void initializeId(EObject obj) {
		EAttribute idAttr = obj.eClass().getEIDAttribute();
		if(idAttr != null) {
			String newId = generateId(obj);
			obj.eSet(idAttr, newId);
		}
	}

	private static String generateId(EObject obj) {
		return "newId" + lastGeneratedId++;
	}
}
