package org.spbu.plweb.diagram.providers;

import org.spbu.plweb.diagram.part.PlwebDiagramEditorPlugin;

/**
 * @generated
 */
public class ElementInitializers {

	protected ElementInitializers() {
		// use #getInstance to access cached instance
	}

	/**
	 * @generated
	 */
	public static ElementInitializers getInstance() {
		ElementInitializers cached = PlwebDiagramEditorPlugin.getInstance()
				.getElementInitializers();
		if (cached == null) {
			PlwebDiagramEditorPlugin.getInstance().setElementInitializers(
					cached = new ElementInitializers());
		}
		return cached;
	}
}
