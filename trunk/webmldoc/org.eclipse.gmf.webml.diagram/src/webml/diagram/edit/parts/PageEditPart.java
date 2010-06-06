/*
 * 
 */
package webml.diagram.edit.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ComponentEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.GroupRequestViaKeyboard;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;

import webml.diagram.edit.policies.OpenDiagramEditPolicy;
import webml.diagram.edit.policies.PageItemSemanticEditPolicy;
import webml.diagram.part.WebmlVisualIDRegistry;
import webml.diagram.providers.WebmlElementTypes;

/**
 * @generated
 */
public class PageEditPart extends ShapeNodeEditPart {

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
	public PageEditPart(View view) {
		super(view);
	}

	/**
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {
		installEditPolicy(EditPolicyRoles.CREATION_ROLE,
				new CreationEditPolicy());
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new PageItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		installEditPolicy(EditPolicyRoles.OPEN_ROLE,
				new OpenDiagramEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
			public Command getCommand(Request request) {
				// If the user presses the delete key, don't delete
				if (request instanceof GroupRequestViaKeyboard 
						&& RequestConstants.REQ_DELETE.equals(request.getType())) {
					return UnexecutableCommand.INSTANCE;
				}
				return super.getCommand(request);
			}
		});
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
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
		PageFigure figure = new PageFigure();
		return primaryShape = figure;
	}

	/**
	 * @generated
	 */
	public PageFigure getPrimaryShape() {
		return (PageFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof PageNameEditPart) {
			((PageNameEditPart) childEditPart).setLabel(getPrimaryShape()
					.getFigurePageLabelFigure());
			return true;
		}
		if (childEditPart instanceof PagePageTopicCompartment2EditPart) {
			IFigure pane = getPrimaryShape().getPageTopicCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.add(((PagePageTopicCompartment2EditPart) childEditPart)
					.getFigure());
			return true;
		}
		if (childEditPart instanceof PagePageElementCompartment2EditPart) {
			IFigure pane = getPrimaryShape().getPageElementCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.add(((PagePageElementCompartment2EditPart) childEditPart)
					.getFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof PageNameEditPart) {
			return true;
		}
		if (childEditPart instanceof PagePageTopicCompartment2EditPart) {
			IFigure pane = getPrimaryShape().getPageTopicCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.remove(((PagePageTopicCompartment2EditPart) childEditPart)
					.getFigure());
			return true;
		}
		if (childEditPart instanceof PagePageElementCompartment2EditPart) {
			IFigure pane = getPrimaryShape().getPageElementCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.remove(((PagePageElementCompartment2EditPart) childEditPart)
					.getFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * @generated
	 */
	protected void removeChildVisual(EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * @generated
	 */
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {
		if (editPart instanceof PagePageTopicCompartment2EditPart) {
			return getPrimaryShape().getPageTopicCompartmentFigure();
		}
		if (editPart instanceof PagePageElementCompartment2EditPart) {
			return getPrimaryShape().getPageElementCompartmentFigure();
		}
		return getContentPane();
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(40, 40);
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
		if (nodeShape.getLayoutManager() == null) {
			ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
			layout.setSpacing(5);
			nodeShape.setLayoutManager(layout);
		}
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
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(WebmlVisualIDRegistry
				.getType(PageNameEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	public List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/getMARelTypesOnSource() {
		List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/types = new ArrayList/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/();
		types.add(WebmlElementTypes.OkLink_4001);
		types.add(WebmlElementTypes.KoLink_4002);
		types.add(WebmlElementTypes.NormalLink_4003);
		types.add(WebmlElementTypes.TransportLink_4004);
		return types;
	}

	/**
	 * @generated
	 */
	public List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/getMARelTypesOnSourceAndTarget(
			IGraphicalEditPart targetEditPart) {
		List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/types = new ArrayList/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/();
		if (targetEditPart instanceof AreaEditPart) {
			types.add(WebmlElementTypes.OkLink_4001);
		}
		if (targetEditPart instanceof webml.diagram.edit.parts.PageEditPart) {
			types.add(WebmlElementTypes.OkLink_4001);
		}
		if (targetEditPart instanceof ContentUnitEditPart) {
			types.add(WebmlElementTypes.OkLink_4001);
		}
		if (targetEditPart instanceof OperationUnitEditPart) {
			types.add(WebmlElementTypes.OkLink_4001);
		}
		if (targetEditPart instanceof Area2EditPart) {
			types.add(WebmlElementTypes.OkLink_4001);
		}
		if (targetEditPart instanceof Page2EditPart) {
			types.add(WebmlElementTypes.OkLink_4001);
		}
		if (targetEditPart instanceof ContentUnit2EditPart) {
			types.add(WebmlElementTypes.OkLink_4001);
		}
		if (targetEditPart instanceof OperationUnit2EditPart) {
			types.add(WebmlElementTypes.OkLink_4001);
		}
		if (targetEditPart instanceof AreaEditPart) {
			types.add(WebmlElementTypes.KoLink_4002);
		}
		if (targetEditPart instanceof webml.diagram.edit.parts.PageEditPart) {
			types.add(WebmlElementTypes.KoLink_4002);
		}
		if (targetEditPart instanceof ContentUnitEditPart) {
			types.add(WebmlElementTypes.KoLink_4002);
		}
		if (targetEditPart instanceof OperationUnitEditPart) {
			types.add(WebmlElementTypes.KoLink_4002);
		}
		if (targetEditPart instanceof Area2EditPart) {
			types.add(WebmlElementTypes.KoLink_4002);
		}
		if (targetEditPart instanceof Page2EditPart) {
			types.add(WebmlElementTypes.KoLink_4002);
		}
		if (targetEditPart instanceof ContentUnit2EditPart) {
			types.add(WebmlElementTypes.KoLink_4002);
		}
		if (targetEditPart instanceof OperationUnit2EditPart) {
			types.add(WebmlElementTypes.KoLink_4002);
		}
		if (targetEditPart instanceof AreaEditPart) {
			types.add(WebmlElementTypes.NormalLink_4003);
		}
		if (targetEditPart instanceof webml.diagram.edit.parts.PageEditPart) {
			types.add(WebmlElementTypes.NormalLink_4003);
		}
		if (targetEditPart instanceof ContentUnitEditPart) {
			types.add(WebmlElementTypes.NormalLink_4003);
		}
		if (targetEditPart instanceof OperationUnitEditPart) {
			types.add(WebmlElementTypes.NormalLink_4003);
		}
		if (targetEditPart instanceof Area2EditPart) {
			types.add(WebmlElementTypes.NormalLink_4003);
		}
		if (targetEditPart instanceof Page2EditPart) {
			types.add(WebmlElementTypes.NormalLink_4003);
		}
		if (targetEditPart instanceof ContentUnit2EditPart) {
			types.add(WebmlElementTypes.NormalLink_4003);
		}
		if (targetEditPart instanceof OperationUnit2EditPart) {
			types.add(WebmlElementTypes.NormalLink_4003);
		}
		if (targetEditPart instanceof AreaEditPart) {
			types.add(WebmlElementTypes.TransportLink_4004);
		}
		if (targetEditPart instanceof webml.diagram.edit.parts.PageEditPart) {
			types.add(WebmlElementTypes.TransportLink_4004);
		}
		if (targetEditPart instanceof ContentUnitEditPart) {
			types.add(WebmlElementTypes.TransportLink_4004);
		}
		if (targetEditPart instanceof OperationUnitEditPart) {
			types.add(WebmlElementTypes.TransportLink_4004);
		}
		if (targetEditPart instanceof Area2EditPart) {
			types.add(WebmlElementTypes.TransportLink_4004);
		}
		if (targetEditPart instanceof Page2EditPart) {
			types.add(WebmlElementTypes.TransportLink_4004);
		}
		if (targetEditPart instanceof ContentUnit2EditPart) {
			types.add(WebmlElementTypes.TransportLink_4004);
		}
		if (targetEditPart instanceof OperationUnit2EditPart) {
			types.add(WebmlElementTypes.TransportLink_4004);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/getMATypesForTarget(
			IElementType relationshipType) {
		List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/types = new ArrayList/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/();
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.Area_2001);
		}
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.Page_2002);
		}
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.ContentUnit_2003);
		}
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.OperationUnit_2004);
		}
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.Area_3005);
		}
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.Page_3001);
		}
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.ContentUnit_3002);
		}
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.OperationUnit_3004);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.Area_2001);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.Page_2002);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.ContentUnit_2003);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.OperationUnit_2004);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.Area_3005);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.Page_3001);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.ContentUnit_3002);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.OperationUnit_3004);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.Area_2001);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.Page_2002);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.ContentUnit_2003);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.OperationUnit_2004);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.Area_3005);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.Page_3001);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.ContentUnit_3002);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.OperationUnit_3004);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.Area_2001);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.Page_2002);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.ContentUnit_2003);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.OperationUnit_2004);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.Area_3005);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.Page_3001);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.ContentUnit_3002);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.OperationUnit_3004);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/getMARelTypesOnTarget() {
		List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/types = new ArrayList/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/();
		types.add(WebmlElementTypes.OkLink_4001);
		types.add(WebmlElementTypes.KoLink_4002);
		types.add(WebmlElementTypes.NormalLink_4003);
		types.add(WebmlElementTypes.TransportLink_4004);
		return types;
	}

	/**
	 * @generated
	 */
	public List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/getMATypesForSource(
			IElementType relationshipType) {
		List/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/types = new ArrayList/*<org.eclipse.gmf.runtime.emf.type.core.IElementType>*/();
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.Area_2001);
		}
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.Page_2002);
		}
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.ContentUnit_2003);
		}
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.OperationUnit_2004);
		}
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.Area_3005);
		}
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.Page_3001);
		}
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.ContentUnit_3002);
		}
		if (relationshipType == WebmlElementTypes.OkLink_4001) {
			types.add(WebmlElementTypes.OperationUnit_3004);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.Area_2001);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.Page_2002);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.ContentUnit_2003);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.OperationUnit_2004);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.Area_3005);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.Page_3001);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.ContentUnit_3002);
		}
		if (relationshipType == WebmlElementTypes.KoLink_4002) {
			types.add(WebmlElementTypes.OperationUnit_3004);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.Area_2001);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.Page_2002);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.ContentUnit_2003);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.OperationUnit_2004);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.Area_3005);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.Page_3001);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.ContentUnit_3002);
		}
		if (relationshipType == WebmlElementTypes.NormalLink_4003) {
			types.add(WebmlElementTypes.OperationUnit_3004);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.Area_2001);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.Page_2002);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.ContentUnit_2003);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.OperationUnit_2004);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.Area_3005);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.Page_3001);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.ContentUnit_3002);
		}
		if (relationshipType == WebmlElementTypes.TransportLink_4004) {
			types.add(WebmlElementTypes.OperationUnit_3004);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public EditPart getTargetEditPart(Request request) {
		if (request instanceof CreateViewAndElementRequest) {
			CreateElementRequestAdapter adapter = ((CreateViewAndElementRequest) request)
					.getViewAndElementDescriptor()
					.getCreateElementRequestAdapter();
			IElementType type = (IElementType) adapter
					.getAdapter(IElementType.class);
			if (type == WebmlElementTypes.DocTopic_3003) {
				return getChildBySemanticHint(WebmlVisualIDRegistry
						.getType(PagePageTopicCompartment2EditPart.VISUAL_ID));
			}
		}
		return super.getTargetEditPart(request);
	}

	/**
	 * @generated
	 */
	protected void handleNotificationEvent(Notification event) {
		if (event.getNotifier() == getModel()
				&& EcorePackage.eINSTANCE.getEModelElement_EAnnotations()
						.equals(event.getFeature())) {
			handleMajorSemanticChange();
		} else {
			super.handleNotificationEvent(event);
		}
	}

	/**
	 * @generated
	 */
	public class PageFigure extends RoundedRectangle {

		/**
		 * @generated
		 */
		private WrappingLabel fFigurePageLabelFigure;
		/**
		 * @generated
		 */
		private RectangleFigure fPageTopicCompartmentFigure;
		/**
		 * @generated
		 */
		private RectangleFigure fPageElementCompartmentFigure;

		/**
		 * @generated
		 */
		public PageFigure() {
			this.setCornerDimensions(new Dimension(getMapMode().DPtoLP(0),
					getMapMode().DPtoLP(0)));
			this.setLineWidth(1);
			this.setForegroundColor(THIS_FORE);
			this.setBackgroundColor(THIS_BACK);
			this.setBorder(new MarginBorder(getMapMode().DPtoLP(5),
					getMapMode().DPtoLP(5), getMapMode().DPtoLP(5),
					getMapMode().DPtoLP(5)));
			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fFigurePageLabelFigure = new WrappingLabel();
			fFigurePageLabelFigure.setText("Page");
			fFigurePageLabelFigure.setMaximumSize(new Dimension(getMapMode()
					.DPtoLP(10000), getMapMode().DPtoLP(50)));

			this.add(fFigurePageLabelFigure);

			fPageElementCompartmentFigure = new RectangleFigure();
			fPageElementCompartmentFigure.setOutline(false);
			fPageElementCompartmentFigure.setLineWidth(1);

			this.add(fPageElementCompartmentFigure);

			fPageTopicCompartmentFigure = new RectangleFigure();
			fPageTopicCompartmentFigure.setOutline(false);
			fPageTopicCompartmentFigure.setLineWidth(1);

			this.add(fPageTopicCompartmentFigure);

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
		public WrappingLabel getFigurePageLabelFigure() {
			return fFigurePageLabelFigure;
		}

		/**
		 * @generated
		 */
		public RectangleFigure getPageTopicCompartmentFigure() {
			return fPageTopicCompartmentFigure;
		}

		/**
		 * @generated
		 */
		public RectangleFigure getPageElementCompartmentFigure() {
			return fPageElementCompartmentFigure;
		}

	}

	/**
	 * @generated
	 */
	static final Color THIS_FORE = new Color(null, 0, 0, 0);

	/**
	 * @generated
	 */
	static final Color THIS_BACK = new Color(null, 255, 255, 255);

}
