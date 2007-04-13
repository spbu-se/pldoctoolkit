/**
 * No license restriction - you may apply any you like.
 * 
 * First created 11.04.2007 23:35:22 using Eclipse IDE.
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
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
		
		IFile file = WorkspaceSynchronizer.getFile(drlObject.eResource());
		IEditorInput myEditorInput = new FileEditorInput(file);
		
		/*
		 * When markers will be available: 
		 * 
		 * IFile file = <choose the file to open>; 
		 * IWorkbenchPage page = <the page to open the editor in>;
		 * HashMap map = new HashMap(); 
		 * map.put(IMarker.LINE_NUMBER, new Integer(5)); 
		 * map.put(IWorkbenchPage.EDITOR_ID_ATTR, "org.eclipse.ui.DefaultTextEditor"); 
		 * IMarker marker = file.createMarker(IMarker.TEXT); 
		 * marker.setAttributes(map);
		 * IDE.openEditor(marker);
		 * API marker.delete();
		 */
		
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
