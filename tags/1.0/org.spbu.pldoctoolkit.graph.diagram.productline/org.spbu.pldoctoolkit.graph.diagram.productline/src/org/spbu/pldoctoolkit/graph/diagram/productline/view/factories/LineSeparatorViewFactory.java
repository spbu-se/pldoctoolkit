package org.spbu.pldoctoolkit.graph.diagram.productline.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractShapeViewFactory;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.LineSeparatorEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;

public class LineSeparatorViewFactory extends AbstractShapeViewFactory {

	protected List createStyles(View view) {
		List styles = new ArrayList();
		styles.add(NotationFactory.eINSTANCE.createFontStyle());
		styles.add(NotationFactory.eINSTANCE.createDescriptionStyle());
		styles.add(NotationFactory.eINSTANCE.createFillStyle());
		styles.add(NotationFactory.eINSTANCE.createLineStyle());
		return styles;
	}

	protected void decorateView(View containerView, View view,
			IAdaptable semanticAdapter, String semanticHint, int index,
			boolean persisted) {

		if (semanticHint == null) {
			semanticHint = DrlModelVisualIDRegistry
					.getType(LineSeparatorEditPart.VISUAL_ID);
			view.setType(semanticHint);
		}
		super.decorateView(containerView, view, semanticAdapter, semanticHint,
				index, persisted);

	}

}
