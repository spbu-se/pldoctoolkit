/*
 * 
 */
package webml.diagram.navigator;

import org.eclipse.jface.viewers.ViewerSorter;

import webml.diagram.part.WebmlVisualIDRegistry;

/**
 * @generated
 */
public class WebmlNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 7012;

	/**
	 * @generated
	 */
	private static final int SHORTCUTS_CATEGORY = 7011;

	/**
	 * @generated
	 */
	public int category(Object element) {
		if (element instanceof WebmlNavigatorItem) {
			WebmlNavigatorItem item = (WebmlNavigatorItem) element;
			if (item.getView().getEAnnotation("Shortcut") != null) { //$NON-NLS-1$
				return SHORTCUTS_CATEGORY;
			}
			return WebmlVisualIDRegistry.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}
