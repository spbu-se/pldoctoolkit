package plweb.diagram.navigator;

import org.eclipse.jface.viewers.ViewerSorter;

import plweb.diagram.part.PlwebVisualIDRegistry;

/**
 * @generated
 */
public class PlwebNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 4003;

	/**
	 * @generated
	 */
	public int category(Object element) {
		if (element instanceof PlwebNavigatorItem) {
			PlwebNavigatorItem item = (PlwebNavigatorItem) element;
			return PlwebVisualIDRegistry.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}
