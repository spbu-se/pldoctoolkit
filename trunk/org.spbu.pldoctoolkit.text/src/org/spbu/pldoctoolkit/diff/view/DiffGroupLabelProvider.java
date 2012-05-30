package org.spbu.pldoctoolkit.diff.view;

import org.eclipse.jface.viewers.LabelProvider;
import org.spbu.pldoctoolkit.diff.IElementInst;
import org.spbu.pldoctoolkit.diff.IPairGroup;

public class DiffGroupLabelProvider extends LabelProvider {
	
	@Override
	public String getText(Object element) {
		if (element instanceof IPairGroup)
			return ((IPairGroup) element).getName();
		else if (element instanceof IElementInst) {
			return ((IElementInst) element).getName();
		} else {
			throw new IllegalArgumentException();
		}
	}

}
