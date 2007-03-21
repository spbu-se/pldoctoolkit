package org.spbu.pldoctoolkit.drlvisual.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EcoreFactory;

import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;

import org.eclipse.gmf.runtime.diagram.ui.view.factories.ConnectionViewFactory;

import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;

import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElemRefId2EditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.SchemaEditPart;

import org.spbu.pldoctoolkit.drlvisual.diagram.part.DRLModelVisualIDRegistry;

/**
 * @generated
 */
public class InfElemRef2ViewFactory extends ConnectionViewFactory {

	/**
	 * @generated 
	 */
	protected List createStyles(View view) {
		List styles = new ArrayList();
		styles.add(NotationFactory.eINSTANCE.createRoutingStyle());
		styles.add(NotationFactory.eINSTANCE.createFontStyle());
		styles.add(NotationFactory.eINSTANCE.createLineStyle());
		return styles;
	}

	/**
	 * @generated
	 */
	protected void decorateView(View containerView, View view,
			IAdaptable semanticAdapter, String semanticHint, int index,
			boolean persisted) {
		if (semanticHint == null) {
			semanticHint = DRLModelVisualIDRegistry
					.getType(org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElemRef2EditPart.VISUAL_ID);
			view.setType(semanticHint);
		}
		super.decorateView(containerView, view, semanticAdapter, semanticHint,
				index, persisted);
		if (!SchemaEditPart.MODEL_ID.equals(DRLModelVisualIDRegistry
				.getModelID(containerView))) {
			EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE
					.createEAnnotation();
			shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
			shortcutAnnotation.getDetails().put(
					"modelID", SchemaEditPart.MODEL_ID); //$NON-NLS-1$
			view.getEAnnotations().add(shortcutAnnotation);
		}
		getViewService().createNode(
				semanticAdapter,
				view,
				DRLModelVisualIDRegistry
						.getType(InfElemRefId2EditPart.VISUAL_ID),
				ViewUtil.APPEND, true, getPreferencesHint());
	}

}
