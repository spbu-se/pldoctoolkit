package org.spbu.pldoctoolkit.graph.diagram.productline.navigator;

import org.eclipse.jface.viewers.ViewerSorter;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;

/**
 * @generated
 */
public class DrlModelNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 5005;

	/**
	 * @generated
	 */
	private static final int SHORTCUTS_CATEGORY = 5003;

	/**
	 * @generated
	 */
	public int category(Object element) {
		if (element instanceof DrlModelNavigatorItem) {
			DrlModelNavigatorItem item = (DrlModelNavigatorItem) element;
			if (item.getView().getEAnnotation("Shortcut") != null) { //$NON-NLS-1$
				return SHORTCUTS_CATEGORY;
			}
			return DrlModelVisualIDRegistry.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}
