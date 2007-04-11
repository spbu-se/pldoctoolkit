/**
 * No license restriction - you may apply any you like.
 * 
 * First created 11.04.2007 23:35:22 using Eclipse IDE.
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph.util;

import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;

/**
 * Class DrlModelHelper.
 *
 * @author Alexey Semenov
 * @version 1.0
 */
public class DrlModelHelper {
	
	private static final String DRL_EDITOR_ID = "org.spbu.pldoctoolkit.editors.DRLEditor"; //$NON-NLS$
	
	public static void openDrlEditor(EObject drlObject) {
		
		Resource elementResource = drlObject.eResource(); 
		
		URI resourceURI = elementResource.getURI();
		IEditorInput myEditorInput = new URIEditorInput(resourceURI);

		
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
					myEditorInput, DRL_EDITOR_ID, true
					);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
}
