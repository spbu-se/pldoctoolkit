package org.spbu.pldoctoolkit.actions;

import java.util.ArrayList;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IEditorPart;

public class DRLMenuListener implements IMenuListener{
	ArrayList<IValidateDRLSelection> listeners = new ArrayList<IValidateDRLSelection>();
	
	public static DRLMenuListener instance = new DRLMenuListener();
	public IEditorPart editor;
	
	private DRLMenuListener() {		
	}
	
	public void menuAboutToShow(IMenuManager manager) {		
		for (IValidateDRLSelection listener : listeners) {
			listener.validateSelection(editor);
		}
	}
	
	public void addListener(IValidateDRLSelection listener) {
		listeners.add(listener);
	}
	
	public void removeListener(IValidateDRLSelection listener) {
		listeners.remove(listener);
	}
}
