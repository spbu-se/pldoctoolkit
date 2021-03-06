package org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;

/**
 * @generated
 */
public class DrlModelEditPartFactory implements EditPartFactory {

	/**
	 * @generated NOT
	 */
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;
			switch (DrlModelVisualIDRegistry.getVisualID(view)) {

			case ProductLineEditPart.VISUAL_ID:
				return new ProductLineEditPart(view);

			case ProductLine2EditPart.VISUAL_ID:
				return new ProductLine2EditPart(view);

			case ProductLineNameEditPart.VISUAL_ID:
				return new ProductLineNameEditPart(view);

			case ProductLineDataEditPart.VISUAL_ID:
				return new ProductLineDataEditPart(view);

				//HAND
			case LineSeparatorEditPart.VISUAL_ID:
				return new LineSeparatorEditPart(view);

			case PLSchemeEditPart.VISUAL_ID:
				return new PLSchemeEditPart(view);

			case ProductEditPart.VISUAL_ID:
				return new ProductEditPart(view);

			case ProductNameEditPart.VISUAL_ID:
				return new ProductNameEditPart(view);

			case DocumentationCoreEditPart.VISUAL_ID:
				return new DocumentationCoreEditPart(view);

			case InfProductEditPart.VISUAL_ID:
				return new InfProductEditPart(view);

			case InfProductNameEditPart.VISUAL_ID:
				return new InfProductNameEditPart(view);

			case ProductLineProductLineDataCompartmentEditPart.VISUAL_ID:
				return new ProductLineProductLineDataCompartmentEditPart(view);

			case ProductLineDataPLSchemeCompartmentEditPart.VISUAL_ID:
				return new ProductLineDataPLSchemeCompartmentEditPart(view);

			case ProductLineDataDocumentationCoreCompartmentEditPart.VISUAL_ID:
				return new ProductLineDataDocumentationCoreCompartmentEditPart(
						view);

			case PLSchemeProductsCompartmentEditPart.VISUAL_ID:
				return new PLSchemeProductsCompartmentEditPart(view);

			case DocumentationCoreInfProductsCompartmentEditPart.VISUAL_ID:
				return new DocumentationCoreInfProductsCompartmentEditPart(view);

			case ProductInfProductLinkEditPart.VISUAL_ID:
				return new ProductInfProductLinkEditPart(view);
			}
		}
		return createUnrecognizedEditPart(context, model);
	}

	/**
	 * @generated
	 */
	private EditPart createUnrecognizedEditPart(EditPart context, Object model) {
		// Handle creation of unrecognized child node EditParts here
		return null;
	}

	/**
	 * @generated
	 */
	public static CellEditorLocator getTextCellEditorLocator(
			ITextAwareEditPart source) {
		if (source.getFigure() instanceof WrapLabel)
			return new TextCellEditorLocator((WrapLabel) source.getFigure());
		else {
			return new LabelCellEditorLocator((Label) source.getFigure());
		}
	}

	/**
	 * @generated
	 */
	static private class TextCellEditorLocator implements CellEditorLocator {

		/**
		 * @generated
		 */
		private WrapLabel wrapLabel;

		/**
		 * @generated
		 */
		public TextCellEditorLocator(WrapLabel wrapLabel) {
			this.wrapLabel = wrapLabel;
		}

		/**
		 * @generated
		 */
		public WrapLabel getWrapLabel() {
			return wrapLabel;
		}

		/**
		 * @generated
		 */
		public void relocate(CellEditor celleditor) {
			Text text = (Text) celleditor.getControl();
			Rectangle rect = getWrapLabel().getTextBounds().getCopy();
			getWrapLabel().translateToAbsolute(rect);
			if (getWrapLabel().isTextWrapped()
					&& getWrapLabel().getText().length() > 0) {
				rect.setSize(new Dimension(text.computeSize(rect.width,
						SWT.DEFAULT)));
			} else {
				int avr = FigureUtilities.getFontMetrics(text.getFont())
						.getAverageCharWidth();
				rect.setSize(new Dimension(text.computeSize(SWT.DEFAULT,
						SWT.DEFAULT)).expand(avr * 2, 0));
			}
			if (!rect.equals(new Rectangle(text.getBounds()))) {
				text.setBounds(rect.x, rect.y, rect.width, rect.height);
			}
		}

	}

	/**
	 * @generated
	 */
	private static class LabelCellEditorLocator implements CellEditorLocator {

		/**
		 * @generated
		 */
		private Label label;

		/**
		 * @generated
		 */
		public LabelCellEditorLocator(Label label) {
			this.label = label;
		}

		/**
		 * @generated
		 */
		public Label getLabel() {
			return label;
		}

		/**
		 * @generated
		 */
		public void relocate(CellEditor celleditor) {
			Text text = (Text) celleditor.getControl();
			Rectangle rect = getLabel().getTextBounds().getCopy();
			getLabel().translateToAbsolute(rect);
			int avr = FigureUtilities.getFontMetrics(text.getFont())
					.getAverageCharWidth();
			rect.setSize(new Dimension(text.computeSize(SWT.DEFAULT,
					SWT.DEFAULT)).expand(avr * 2, 0));
			if (!rect.equals(new Rectangle(text.getBounds()))) {
				text.setBounds(rect.x, rect.y, rect.width, rect.height);
			}
		}
	}
}
