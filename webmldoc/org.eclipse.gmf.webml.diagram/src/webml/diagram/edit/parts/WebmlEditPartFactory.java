/*
 * 
 */
package webml.diagram.edit.parts;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import webml.diagram.part.WebmlVisualIDRegistry;

/**
 * @generated
 */
public class WebmlEditPartFactory implements EditPartFactory {

	/**
	 * @generated
	 */
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;
			switch (WebmlVisualIDRegistry.getVisualID(view)) {

			case SiteviewEditPart.VISUAL_ID:
				return new SiteviewEditPart(view);

			case AreaEditPart.VISUAL_ID:
				return new AreaEditPart(view);

			case AreaNameEditPart.VISUAL_ID:
				return new AreaNameEditPart(view);

			case PageEditPart.VISUAL_ID:
				return new PageEditPart(view);

			case PageNameEditPart.VISUAL_ID:
				return new PageNameEditPart(view);

			case ContentUnitEditPart.VISUAL_ID:
				return new ContentUnitEditPart(view);

			case ContentUnitNameEditPart.VISUAL_ID:
				return new ContentUnitNameEditPart(view);

			case OperationUnitEditPart.VISUAL_ID:
				return new OperationUnitEditPart(view);

			case OperationUnitNameEditPart.VISUAL_ID:
				return new OperationUnitNameEditPart(view);

			case Area2EditPart.VISUAL_ID:
				return new Area2EditPart(view);

			case AreaName2EditPart.VISUAL_ID:
				return new AreaName2EditPart(view);

			case Page2EditPart.VISUAL_ID:
				return new Page2EditPart(view);

			case PageName2EditPart.VISUAL_ID:
				return new PageName2EditPart(view);

			case ContentUnit2EditPart.VISUAL_ID:
				return new ContentUnit2EditPart(view);

			case ContentUnitName2EditPart.VISUAL_ID:
				return new ContentUnitName2EditPart(view);

			case DocTopicEditPart.VISUAL_ID:
				return new DocTopicEditPart(view);

			case DocTopicNameEditPart.VISUAL_ID:
				return new DocTopicNameEditPart(view);

			case OperationUnit2EditPart.VISUAL_ID:
				return new OperationUnit2EditPart(view);

			case OperationUnitName2EditPart.VISUAL_ID:
				return new OperationUnitName2EditPart(view);

			case AreaAreaTopicCompartmentEditPart.VISUAL_ID:
				return new AreaAreaTopicCompartmentEditPart(view);

			case AreaAreaElementCompartmentEditPart.VISUAL_ID:
				return new AreaAreaElementCompartmentEditPart(view);

			case AreaAreaTopicCompartment2EditPart.VISUAL_ID:
				return new AreaAreaTopicCompartment2EditPart(view);

			case AreaAreaElementCompartment2EditPart.VISUAL_ID:
				return new AreaAreaElementCompartment2EditPart(view);

			case PagePageTopicCompartmentEditPart.VISUAL_ID:
				return new PagePageTopicCompartmentEditPart(view);

			case PagePageElementCompartmentEditPart.VISUAL_ID:
				return new PagePageElementCompartmentEditPart(view);

			case ContentUnitContentUnitTopicCompartmentEditPart.VISUAL_ID:
				return new ContentUnitContentUnitTopicCompartmentEditPart(view);

			case PagePageTopicCompartment2EditPart.VISUAL_ID:
				return new PagePageTopicCompartment2EditPart(view);

			case PagePageElementCompartment2EditPart.VISUAL_ID:
				return new PagePageElementCompartment2EditPart(view);

			case ContentUnitContentUnitTopicCompartment2EditPart.VISUAL_ID:
				return new ContentUnitContentUnitTopicCompartment2EditPart(view);

			case OkLinkEditPart.VISUAL_ID:
				return new OkLinkEditPart(view);

			case KoLinkEditPart.VISUAL_ID:
				return new KoLinkEditPart(view);

			case NormalLinkEditPart.VISUAL_ID:
				return new NormalLinkEditPart(view);

			case TransportLinkEditPart.VISUAL_ID:
				return new TransportLinkEditPart(view);

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
		if (source.getFigure() instanceof WrappingLabel)
			return new TextCellEditorLocator((WrappingLabel) source.getFigure());
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
		private WrappingLabel wrapLabel;

		/**
		 * @generated
		 */
		public TextCellEditorLocator(WrappingLabel wrapLabel) {
			this.wrapLabel = wrapLabel;
		}

		/**
		 * @generated
		 */
		public WrappingLabel getWrapLabel() {
			return wrapLabel;
		}

		/**
		 * @generated
		 */
		public void relocate(CellEditor celleditor) {
			Text text = (Text) celleditor.getControl();
			Rectangle rect = getWrapLabel().getTextBounds().getCopy();
			getWrapLabel().translateToAbsolute(rect);
			if (getWrapLabel().isTextWrapOn()
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
