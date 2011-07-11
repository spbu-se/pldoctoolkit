package org.spbu.plweb.diagram.edit.parts;

/**
 * @generated
 */
public class DocTopic4EditPart extends org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart  {

			/**
 * @generated
 */
	public static final int VISUAL_ID = 3008;

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
	public DocTopic4EditPart(org.eclipse.gmf.runtime.notation.View view) {
		super(view);
	}
	
		/**
 * @generated
 */
	protected void createDefaultEditPolicies() {
				super.createDefaultEditPolicies();
			installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.SEMANTIC_ROLE, new org.spbu.plweb.diagram.edit.policies.DocTopic4ItemSemanticEditPolicy());
					installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CANONICAL_ROLE, new org.spbu.plweb.diagram.edit.policies.DocTopic4CanonicalEditPolicy());
		installEditPolicy(org.eclipse.gef.EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
			// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
	// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}
	
		/**
 * @generated
 */
	protected org.eclipse.gef.editpolicies.LayoutEditPolicy createLayoutEditPolicy() {
			
		org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConstrainedToolbarLayoutEditPolicy lep = new org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConstrainedToolbarLayoutEditPolicy() {

			protected org.eclipse.gef.EditPolicy createChildEditPolicy(org.eclipse.gef.EditPart child) {
								if (child.getEditPolicy(org.eclipse.gef.EditPolicy.PRIMARY_DRAG_ROLE) == null) {
					if (child instanceof org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart) {
						return new org.spbu.plweb.diagram.edit.policies.PlwebTextSelectionEditPolicy();
					}
				}
				return super.createChildEditPolicy(child);
			}
		};
		return lep;
		}
	
		/**
 * @generated
 */
	protected org.eclipse.draw2d.IFigure createNodeShape() {
		return primaryShape = new StickyNoteFigure();
	}

		/**
 * @generated
 */
	public StickyNoteFigure getPrimaryShape() {
		return (StickyNoteFigure) primaryShape;
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
		public org.eclipse.gef.EditPart getPrimaryChildEditPart() {
			return getChildBySemanticHint(org.spbu.plweb.diagram.part.PlwebVisualIDRegistry.getType(org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicName3EditPart.VISUAL_ID));
		}
	



	
	
	
/**
 * @generated
 */
public class StickyNoteFigure extends org.eclipse.draw2d.RectangleFigure {


	/**
	 * @generated
	 */
	private org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel fFigureStickyNoteNameFigure; 


	/**
	 * @generated
	 */
	public StickyNoteFigure() {
		
	org.eclipse.draw2d.ToolbarLayout layoutThis = new org.eclipse.draw2d.ToolbarLayout();
	layoutThis.setStretchMinorAxis(false);
	layoutThis.setMinorAlignment(org.eclipse.draw2d.ToolbarLayout.ALIGN_CENTER
);

	layoutThis.setSpacing(0);
	layoutThis.setVertical(true);

	this.setLayoutManager(layoutThis);

			this.setBackgroundColor(THIS_BACK
);
		createContents();
	}
	/**
	 * @generated
	 */
	private void createContents(){


org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel stickyNoteNameFigure0 = new org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel();
stickyNoteNameFigure0.setText("");

this.add(stickyNoteNameFigure0);


	}





	/**
	 * @generated
	 */
	public org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel getFigureStickyNoteNameFigure() {
		return fFigureStickyNoteNameFigure;
	}


}

/**
 * @generated
 */
static final org.eclipse.swt.graphics.Color THIS_BACK = new org.eclipse.swt.graphics.Color(null, 190, 250, 250);


	
	}
