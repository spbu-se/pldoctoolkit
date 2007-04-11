/**
 * No license restriction - you may apply any you like.
 * 
 * First created 11.04.2007 23:35:22 using Eclipse IDE.
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

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
		IPath path = new Path(elementResource.getURI().toPlatformString(true));
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		IEditorInput myEditorInput = new FileEditorInput(file);

		try {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			if(page != null) {
				page.openEditor(
						myEditorInput, DRL_EDITOR_ID, true
						);
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
}
