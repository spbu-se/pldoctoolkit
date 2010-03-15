package plweb.diagram.edit.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ContainerEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;

import plweb.diagram.edit.policies.GroupCanonicalEditPolicy;
import plweb.diagram.edit.policies.GroupItemSemanticEditPolicy;
import plweb.diagram.providers.PlwebElementTypes;

/**
 * @generated
 */
public class GroupEditPart extends ShapeNodeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 2002;

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated
	 */
	public GroupEditPart(View view) {
		super(view);
		try {
			throw new RuntimeException();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new GroupItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
				new GroupCanonicalEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		
//		installEditPolicy(EditPolicyRoles.CREATION_ROLE,
//				new CreationEditPolicy() {
//					protected Command getCreateCommand(CreateViewRequest request) {
//						System.out.println(request.getType() + " " + super.getCommand(request));
//						if (understandsRequest(request)) {
//							
////							if (request instanceof CreateViewAndElementRequest) {
////								CreateElementRequestAdapter adapter = ((CreateViewAndElementRequest) request)
////										.getViewAndElementDescriptor()
////										.getCreateElementRequestAdapter();
////								IElementType type = (IElementType) adapter
////										.getAdapter(IElementType.class);
////								if (type == PlwebElementTypes.Group_2002) {
////									EObject modelObject = ((View)getHost().getModel()).getElement();
////									request.getExtendedData().put("element", modelObject);
////									EditPart documentationCoreEditPart = getHost().getParent();
////									System.out.println(documentationCoreEditPart);
////									return documentationCoreEditPart == null ? null
////											: documentationCoreEditPart
////													.getCommand(request);
////								}
////							}
//							return super.getCommand(request);
//						}
//						return null;
//					}
//				}
//		);
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new ContainerEditPolicy() {
			@Override
			public Command getCommand(Request request) {
					System.out.println("Group CONTAINER_ROLE " + request.getType());
				return super.getCommand(request);
			}
		});
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {
		LayoutEditPolicy lep = new LayoutEditPolicy() {

			protected EditPolicy createChildEditPolicy(EditPart child) {
				EditPolicy result = child
						.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
				if (result == null) {
					result = new NonResizableEditPolicy();
				}
				return result;
			}

			protected Command getMoveChildrenCommand(Request request) {
				return null;
			}

			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}
		};
		return lep;
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		GroupFigure figure = new GroupFigure();
		return primaryShape = figure;
	}

	/**
	 * @generated
	 */
	public GroupFigure getPrimaryShape() {
		return (GroupFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(5, 5);
		return result;
	}

	/**
	 * @generated
	 */
	public EditPolicy getPrimaryDragEditPolicy() {
		EditPolicy result = super.getPrimaryDragEditPolicy();
		if (result instanceof ResizableEditPolicy) {
			ResizableEditPolicy ep = (ResizableEditPolicy) result;
			ep.setResizeDirections(PositionConstants.NONE);
		}
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
	protected NodeFigure createNodeFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
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
	protected IFigure setupContentPane(IFigure nodeShape) {
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	protected void setForegroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setForegroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	protected void setBackgroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setBackgroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	protected void setLineWidth(int width) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineWidth(width);
		}
	}

	/**
	 * @generated
	 */
	protected void setLineType(int style) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineStyle(style);
		}
	}

	/**
	 * @generated
	 */
	public List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/getMARelTypesOnSource() {
		List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/types = new ArrayList/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/();
		types.add(PlwebElementTypes.SourceRefElementClass_4001);
		return types;
	}

	/**
	 * @generated
	 */
	public List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/getMARelTypesOnSourceAndTarget(
			IGraphicalEditPart targetEditPart) {
		List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/types = new ArrayList/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/();
		if (targetEditPart instanceof plweb.diagram.edit.parts.GroupEditPart) {
			types.add(PlwebElementTypes.SourceRefElementClass_4001);
		}
		if (targetEditPart instanceof PageEditPart) {
			types.add(PlwebElementTypes.SourceRefElementClass_4001);
		}
		if (targetEditPart instanceof ClassEditPart) {
			types.add(PlwebElementTypes.SourceRefElementClass_4001);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/getMATypesForTarget(
			IElementType relationshipType) {
		List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/types = new ArrayList/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/();
		if (relationshipType == PlwebElementTypes.SourceRefElementClass_4001) {
			types.add(PlwebElementTypes.Group_2002);
		}
		if (relationshipType == PlwebElementTypes.SourceRefElementClass_4001) {
			types.add(PlwebElementTypes.Page_2003);
		}
		if (relationshipType == PlwebElementTypes.SourceRefElementClass_4001) {
			types.add(PlwebElementTypes.Class_2004);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/getMARelTypesOnTarget() {
		List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/types = new ArrayList/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/();
		types.add(PlwebElementTypes.SourceRefElementClass_4001);
		return types;
	}

	/**
	 * @generated
	 */
	public List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/getMATypesForSource(
			IElementType relationshipType) {
		List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/types = new ArrayList/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/();
		if (relationshipType == PlwebElementTypes.SourceRefElementClass_4001) {
			types.add(PlwebElementTypes.Area_2001);
		}
		if (relationshipType == PlwebElementTypes.SourceRefElementClass_4001) {
			types.add(PlwebElementTypes.Group_2002);
		}
		if (relationshipType == PlwebElementTypes.SourceRefElementClass_4001) {
			types.add(PlwebElementTypes.Class_2004);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public class GroupFigure extends Ellipse {

		/**
		 * @generated
		 */
		private Ellipse fFigureGroupFigure;

		/**
		 * @generated
		 */
		public GroupFigure() {
			this.setLineWidth(2);
			this.setBackgroundColor(ColorConstants.lightGray);
			this.setPreferredSize(new Dimension(getMapMode().DPtoLP(5),
					getMapMode().DPtoLP(5)));
			this.setMaximumSize(new Dimension(getMapMode().DPtoLP(5),
					getMapMode().DPtoLP(5)));
			this.setMinimumSize(new Dimension(getMapMode().DPtoLP(5),
					getMapMode().DPtoLP(5)));
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

		/**
		 * @generated
		 */
		public Ellipse getFigureGroupFigure() {
			return fFigureGroupFigure;
		}

	}

}
