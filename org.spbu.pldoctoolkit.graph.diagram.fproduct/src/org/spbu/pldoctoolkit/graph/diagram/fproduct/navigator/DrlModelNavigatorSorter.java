package org.spbu.pldoctoolkit.graph.diagram.fproduct.navigator;

import org.eclipse.jface.viewers.ViewerSorter;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.part.DrlModelVisualIDRegistry;

/**
 * @generated
 */
public class DrlModelNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 3003;

	/**
	 * @generated
	 */
	public int category(Object element) {
		if (element instanceof DrlModelNavigatorItem) {
			DrlModelNavigatorItem item = (DrlModelNavigatorItem) element;
			return DrlModelVisualIDRegistry.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}
