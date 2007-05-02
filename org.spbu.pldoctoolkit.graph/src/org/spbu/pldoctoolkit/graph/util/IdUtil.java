package org.spbu.pldoctoolkit.graph.util;

import org.eclipse.emf.common.util.URI;

public class IdUtil {
	
	public static String idToUriString(String id) {
		System.out.println("id to uri: " + id);
//		return "blabla";
//		return "file://" + type + "/" + id;
		return "";
	}
	
	/**
	 * Returns fragment part of the uri.
	 * 
	 * @param uriString
	 * @return
	 */
	public static String uriStringToId(String uriString) {
		URI uri = URI.createURI(uriString);
		
		return uri.fragment();
	}
}
