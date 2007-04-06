package org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies.ProductLine2ItemSemanticEditPolicy;

/**
 * @generated
 */
public class ProductLine2EditPart extends
		org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 1001;

	/**
	 * @generated
	 */
	protected org.eclipse.draw2d.IFigure contentPane;

	/**
	 * @generated
	 */
	protected org.eclipse.draw2d.IFigure primaryShape;

	/**
	 * @generated
	 */
	public ProductLine2EditPart(org.eclipse.gmf.runtime.notation.View view) {
		super(view);
	}

	/**
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {
		installEditPolicy(
				org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CREATION_ROLE,
				new org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy() {
					public org.eclipse.gef.commands.Command getCommand(
							org.eclipse.gef.Request request) {
						if (understandsRequest(request)) {
							if (request instanceof org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest) {
								org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter adapter = ((org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest) request)
										.getViewAndElementDescriptor()
										.getCreateElementRequestAdapter();
								org.eclipse.gmf.runtime.emf.type.core.IElementType type = (org.eclipse.gmf.runtime.emf.type.core.IElementType) adapter
										.getAdapter(org.eclipse.gmf.runtime.emf.type.core.IElementType.class);
								if (type == org.spbu.pldoctoolkit.graph.diagram.productline.providers.DrlModelElementTypes.Node_2001) {
									org.eclipse.gef.EditPart compartmentEditPart = getChildBySemanticHint(org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry
											.getType(org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineProductLineDataCompartmentEditPart.VISUAL_ID));
									return compartmentEditPart == null ? null
											: compartmentEditPart
													.getCommand(request);
								}
							}
							return super.getCommand(request);
						}
						return null;
					}
				});

		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new ProductLine2ItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		
		//HAND
		removeEditPolicy(EditPolicyRoles.POPUPBAR_ROLE);
	}

	/**
	 * @generated
	 */
	protected org.eclipse.gef.editpolicies.LayoutEditPolicy createLayoutEditPolicy() {
		org.eclipse.gef.editpolicies.LayoutEditPolicy lep = new org.eclipse.gef.editpolicies.LayoutEditPolicy() {

			protected org.eclipse.gef.EditPolicy createChildEditPolicy(
					org.eclipse.gef.EditPart child) {
				org.eclipse.gef.EditPolicy result = child
						.getEditPolicy(org.eclipse.gef.EditPolicy.PRIMARY_DRAG_ROLE);
				if (result == null) {
					result = new org.eclipse.gef.editpolicies.NonResizableEditPolicy();
				}
				return result;
			}

			protected org.eclipse.gef.commands.Command getMoveChildrenCommand(
					org.eclipse.gef.Request request) {
				return null;
			}

			protected org.eclipse.gef.commands.Command getCreateCommand(
					org.eclipse.gef.requests.CreateRequest request) {
				return null;
			}
		};
		return lep;
	}

	/**
	 * @generated
	 */
	protected org.eclipse.draw2d.IFigure createNodeShape() {
		ProductLineFigure figure = new ProductLineFigure();
		return primaryShape = figure;
	}

	/**
	 * @generated
	 */
	public ProductLineFigure getPrimaryShape() {
		return (ProductLineFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(org.eclipse.gef.EditPart childEditPart) {
		if (childEditPart instanceof org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineNameEditPart) {
			((org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineNameEditPart) childEditPart)
					.setLabel(getPrimaryShape()
							.getFigureProductLineNameFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(org.eclipse.gef.EditPart childEditPart) {

		return false;
	}

	/**
	 * @generated
	 */
	protected void addChildVisual(org.eclipse.gef.EditPart childEditPart,
			int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * @generated
	 */
	protected void removeChildVisual(org.eclipse.gef.EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * @generated
	 */
	protected org.eclipse.draw2d.IFigure getContentPaneFor(
			org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart editPart) {

		return super.getContentPaneFor(editPart);
	}

	/**
	 * @generated
	 */
	protected org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure createNodePlate() {
		org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure result = new org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure(
				getMapMode().DPtoLP(40), getMapMode().DPtoLP(40));
		return result;
	}

	/**
	 * @generated
	 */
//	public org.eclipse.gef.EditPolicy getPrimaryDragEditPolicy() {
//		 return new ();
//	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model so
	 * you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */
	protected org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure createNodeFigure() {
		org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new org.eclipse.draw2d.StackLayout());
		org.eclipse.draw2d.IFigure shape = createNodeShape();
		figure.add(shape);
		contentPane = setupContentPane(shape);
		return figure;
	}

	/**
	 * Default implementation treats passed figure as content pane. Respects
	 * layout one may have set for generated figure.
	 * 
	 * @param nodeShape
	 *            instance of generated figure class
	 * @generated
	 */
	protected org.eclipse.draw2d.IFigure setupContentPane(
			org.eclipse.draw2d.IFigure nodeShape) {
		if (nodeShape.getLayoutManager() == null) {
			org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout layout = new org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout();
			layout.setSpacing(getMapMode().DPtoLP(5));
			nodeShape.setLayoutManager(layout);
		}
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	public org.eclipse.draw2d.IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	public org.eclipse.gef.EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry
				.getType(org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineNameEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	protected void handleNotificationEvent(
			org.eclipse.emf.common.notify.Notification event) {
		if (event.getNotifier() == getModel()
				&& org.eclipse.emf.ecore.EcorePackage.eINSTANCE
						.getEModelElement_EAnnotations().equals(
								event.getFeature())) {
			handleMajorSemanticChange();
		} else {
			super.handleNotificationEvent(event);
		}
	}

	/**
	 * @generated
	 */
	public class ProductLineFigure extends org.eclipse.draw2d.RectangleFigure {
		/**
		 * @generated
		 */
		public ProductLineFigure() {
			this.setFill(true);
			this.setFillXOR(false);
			this.setOutline(true);
			this.setOutlineXOR(false);
			this.setLineWidth(1);
			this.setLineStyle(org.eclipse.draw2d.Graphics.LINE_SOLID);
			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel productLineNameFigure0 = new org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel();
			productLineNameFigure0.setText("<...>");

			this.add(productLineNameFigure0);
			setFigureProductLineNameFigure(productLineNameFigure0);

		}

		/**
		 * @generated
		 */
		private org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel fProductLineNameFigure;

		/**
		 * @generated
		 */
		public org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel getFigureProductLineNameFigure() {
			return fProductLineNameFigure;
		}

		/**
		 * @generated
		 */
		private void setFigureProductLineNameFigure(
				org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel fig) {
			fProductLineNameFigure = fig;
		}

		/**
		 * @generated
		 */
		private boolean myUseLocalCoordinates = false;

		/**
		 * @generated
		 */
		protected boolean useLocalCoordinates() {
			return myUseLocalCoordinates;
		}

		/**
		 * @generated
		 */
		protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
			myUseLocalCoordinates = useLocalCoordinates;
		}

	}

}
