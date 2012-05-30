package org.spbu.pldoctoolkit.diff.view;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.spbu.pldoctoolkit.diff.IPairGroup;

public class DiffGroupContentProvider implements ITreeContentProvider {

	private static final Object[] EMPTY_ARRAY = new Object[0];
	
	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof List){
			List list = (List) parentElement;
			return list.toArray(new Object[0]);
		} else if (parentElement instanceof IPairGroup){
			Object[] objects = {((IPairGroup)parentElement).getFirstInstance(),((IPairGroup)parentElement).getSecondInstance()};
			return objects;
		}
		return EMPTY_ARRAY;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}

	

}
