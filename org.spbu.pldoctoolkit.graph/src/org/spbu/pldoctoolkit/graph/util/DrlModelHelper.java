/**
 * No license restriction - you may apply any you like.
 * 
 * First created 11.04.2007 23:35:22 using Eclipse IDE.
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph.util;

import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.registry.RegisteredLocation;

/**
 * Class DrlModelHelper.
 *
 * @author Alexey Semenov
 * @version 1.0
 */
public class DrlModelHelper {
	
	private static final String DRL_EDITOR_ID = "org.spbu.pldoctoolkit.editors.DRLEditor"; //$NON-NLS$
	
	public static void openDrlEditor(EObject obj) {
		
		IFile file = WorkspaceSynchronizer.getFile(obj.eResource());
		IEditorInput myEditorInput = new FileEditorInput(file);
		
		int lineNumber = getLineNumber(obj, file);
		
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			if(page != null) {
				HashMap map = new HashMap();
				map.put(IMarker.LINE_NUMBER, new Integer(lineNumber));
				map.put(IDE.EDITOR_ID_ATTR, DRL_EDITOR_ID);
				IMarker marker = file.createMarker(IMarker.TEXT);
				marker.setAttributes(map);
				IDE.openEditor(page, marker);
				marker.delete();

				page.openEditor(
						myEditorInput, DRL_EDITOR_ID, true
						);
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	private static int getLineNumber(EObject obj, IFile file) {
		final String projectName = obj.eResource().getURI().segment(1);
		final String objId = getId(obj);
		
		int lineNumber = -1;
		
		if(objId == null || "".equals(objId)) {
			return lineNumber;
		}
		
		List<RegisteredLocation> locationList = 
			PLDocToolkitPlugin.getRegistry(projectName).findAll(); //XXX findForFile(file) does not work!
		
		for(RegisteredLocation location : locationList) {
			if(objId.equals(location.getId())) {
				lineNumber = location.getLineNumber();
				break;
			}
		}
		
		return lineNumber;
	}
	
	private static String getId(EObject obj) {
		String objId = null;

		EAttribute idAttr = obj.eClass().getEIDAttribute();
		if(idAttr != null) {
			objId = obj.eGet(idAttr).toString();
		}
		
		return objId;
	}

}
