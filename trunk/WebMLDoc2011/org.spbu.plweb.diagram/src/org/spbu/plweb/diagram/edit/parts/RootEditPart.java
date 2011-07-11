package org.spbu.plweb.diagram.edit.parts;

/**
 * @generated
 */
public class RootEditPart extends org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart  {

			/**
 * @generated
 */
	public static final int VISUAL_ID = 2001;

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
	public RootEditPart(org.eclipse.gmf.runtime.notation.View view) {
		super(view);
	}
	
		/**
 * @generated
 */
	protected void createDefaultEditPolicies() {
				installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CREATION_ROLE, new org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy());
	super.createDefaultEditPolicies();
			installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.SEMANTIC_ROLE, new org.spbu.plweb.diagram.edit.policies.RootItemSemanticEditPolicy());
					installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.DRAG_DROP_ROLE, new org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy());
			installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CANONICAL_ROLE, new org.spbu.plweb.diagram.edit.policies.RootCanonicalEditPolicy());
		installEditPolicy(org.eclipse.gef.EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
			// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
	// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}
	
		/**
 * @generated
 */
	protected org.eclipse.gef.editpolicies.LayoutEditPolicy createLayoutEditPolicy() {
					org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy lep = new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy() {

			protected org.eclipse.gef.EditPolicy createChildEditPolicy(org.eclipse.gef.EditPart child) {
								org.eclipse.gef.EditPolicy result = child.getEditPolicy(org.eclipse.gef.EditPolicy.PRIMARY_DRAG_ROLE);
				if (result == null) {
					result = new org.eclipse.gef.editpolicies.NonResizableEditPolicy();
				}
				return result;
			}

			protected org.eclipse.gef.commands.Command getMoveChildrenCommand(org.eclipse.gef.Request request) {
				return null;
			}

			protected org.eclipse.gef.commands.Command getCreateCommand(org.eclipse.gef.requests.CreateRequest request) {
				return null;
			}
		};
		return lep;
		}
	
		/**
 * @generated
 */
	protected org.eclipse.draw2d.IFigure createNodeShape() {
		return primaryShape = new RootFigure();
	}

		/**
 * @generated
 */
	public RootFigure getPrimaryShape() {
		return (RootFigure) primaryShape;
	}
	

			
		/**
 * @generated
 */
	protected org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure createNodePlate() {
		org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure result = new org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure(40, 40);
				return result;
	}
	
						
		/**
 * Creates figure for this edit part.
 * 
 * Body of this method does not depend on settings in generation model
 * so you may safely remove <i>generated</i> tag and modify it.
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
 * Default implementation treats passed figure as content pane.
 * Respects layout one may have set for generated figure.
 * @param nodeShape instance of generated figure class
 * @generated
 */
	protected org.eclipse.draw2d.IFigure setupContentPane(org.eclipse.draw2d.IFigure nodeShape) {
					if (nodeShape.getLayoutManager() == null) {
									org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout layout =new org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout();
					layout.setSpacing(5);
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
	protected void setForegroundColor(org.eclipse.swt.graphics.Color color) {
		if (primaryShape != null) {
			primaryShape.setForegroundColor(color);
		}
	}

		/**
 * @generated
 */
	protected void setBackgroundColor(org.eclipse.swt.graphics.Color color) {
		if (primaryShape != null) {
			primaryShape.setBackgroundColor(color);
		}
	}

		/**
 * @generated
 */
	protected void setLineWidth(int width) {
		if (primaryShape instanceof org.eclipse.draw2d.Shape) {
			((org.eclipse.draw2d.Shape) primaryShape).setLineWidth(width);
		}
	}

		/**
 * @generated
 */
	protected void setLineType(int style) {
		if (primaryShape instanceof org.eclipse.draw2d.Shape) {
			((org.eclipse.draw2d.Shape) primaryShape).setLineStyle(style);
		}
	}

		
		/**
 * @generated
 */
	public java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> getMARelTypesOnSource() {
		java.util.ArrayList<org.eclipse.gmf.runtime.emf.type.core.IElementType> types = new java.util.ArrayList<org.eclipse.gmf.runtime.emf.type.core.IElementType>(1);
				types.add(org.spbu.plweb.diagram.providers.PlwebElementTypes.SourceRefElementClass_4001);
				return types;
	}

		/**
 * @generated
 */
	public java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> getMARelTypesOnSourceAndTarget(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart targetEditPart) {
		java.util.LinkedList<org.eclipse.gmf.runtime.emf.type.core.IElementType> types = new java.util.LinkedList<org.eclipse.gmf.runtime.emf.type.core.IElementType>();
							if (targetEditPart instanceof org.spbu.plweb.diagram.edit.parts.SiteViewEditPart) {
			types.add(org.spbu.plweb.diagram.providers.PlwebElementTypes.SourceRefElementClass_4001);
		}
					if (targetEditPart instanceof org.spbu.plweb.diagram.edit.parts.AreaEditPart) {
			types.add(org.spbu.plweb.diagram.providers.PlwebElementTypes.SourceRefElementClass_4001);
		}
					if (targetEditPart instanceof org.spbu.plweb.diagram.edit.parts.PageEditPart) {
			types.add(org.spbu.plweb.diagram.providers.PlwebElementTypes.SourceRefElementClass_4001);
		}
					if (targetEditPart instanceof org.spbu.plweb.diagram.edit.parts.GroupEditPart) {
			types.add(org.spbu.plweb.diagram.providers.PlwebElementTypes.SourceRefElementClass_4001);
		}
					if (targetEditPart instanceof org.spbu.plweb.diagram.edit.parts.NodeEditPart) {
			types.add(org.spbu.plweb.diagram.providers.PlwebElementTypes.SourceRefElementClass_4001);
		}
							return types;
	}

		/**
 * @generated
 */
	public java.util.List<org.eclipse.gmf.runtime.emf.type.core.IElementType> getMATypesForTarget(org.eclipse.gmf.runtime.emf.type.core.IElementType relationshipType) {
		java.util.LinkedList<org.eclipse.gmf.runtime.emf.type.core.IElementType> types = new java.util.LinkedList<org.eclipse.gmf.runtime.emf.type.core.IElementType>();
				if (relationshipType == org.spbu.plweb.diagram.providers.PlwebElementTypes.SourceRefElementClass_4001) {
						types.add(org.spbu.plweb.diagram.providers.PlwebElementTypes.SiteView_2002);
						types.add(org.spbu.plweb.diagram.providers.PlwebElementTypes.Area_2003);
						types.add(org.spbu.plweb.diagram.providers.PlwebElementTypes.Page_2004);
						types.add(org.spbu.plweb.diagram.providers.PlwebElementTypes.Group_2005);
						types.add(org.spbu.plweb.diagram.providers.PlwebElementTypes.Node_2006);
					}
				return types;
	}



		
	
	
	
/**
 * @generated
 */
public class RootFigure extends org.eclipse.draw2d.RoundedRectangle {


	/**
	 * @generated
	 */
	private org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel fFigureRootTitleFigure; 


	/**
	 * @generated
	 */
	public RootFigure() {
				this.setCornerDimensions(new org.eclipse.draw2d.geometry.Dimension(getMapMode().DPtoLP(12)
, getMapMode().DPtoLP(12)
));
	this.setBackgroundColor(org.eclipse.draw2d.ColorConstants.orange);
	}



	/**
	 * @generated
	 */
	public org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel getFigureRootTitleFigure() {
		return fFigureRootTitleFigure;
	}


}


	
	}
