package plweb.diagram.edit.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.BorderItemSelectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ContainerEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;

import plweb.diagram.edit.policies.DiagramRootCanonicalEditPolicy;
import plweb.diagram.edit.policies.DiagramRootItemSemanticEditPolicy;
import plweb.diagram.layout.TopDownLayoutProvider;
import plweb.diagram.providers.PlwebElementTypes;

/**
 * @generated
 */
public class DiagramRootEditPart extends DiagramEditPart {

	/**
	 * @generated
	 */
	public final static String MODEL_ID = "Plweb"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 1000;

	/**
	 * @generated
	 */
	public DiagramRootEditPart(View view) {
		super(view);
	}
	
	private TopDownLayoutProvider layoutProvider = new TopDownLayoutProvider();

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new DiagramRootItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
				new DiagramRootCanonicalEditPolicy());
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.POPUPBAR_ROLE);
		
//		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new ContainerEditPolicy() {
			protected Command getArrangeCommand(ArrangeRequest request) {
				if (layoutProvider.isWorking()) {
					return null;
				}
				return super.getArrangeCommand(request);
			}
			
			@Override
			public Command getCommand(Request request) {
				if (understandsRequest(request)) {
					System.out.println(request.getType());
				}
				return super.getCommand(request);
			}

			public Runnable layoutNodes(List nodes, boolean offsetFromBoundingBox, IAdaptable layoutHint) {
				return layoutProvider.layoutLayoutNodes(nodes, offsetFromBoundingBox, layoutHint);
			}
		});
		
//		installEditPolicy(EditPolicyRoles.CREATION_ROLE,
//				new CreationEditPolicy() {
//					public Command getCommand(Request request) {
//						if (understandsRequest(request)) {
//							if (request instanceof CreateViewAndElementRequest) {
//								CreateElementRequestAdapter adapter = ((CreateViewAndElementRequest) request)
//										.getViewAndElementDescriptor()
//										.getCreateElementRequestAdapter();
//								IElementType type = (IElementType) adapter
//										.getAdapter(IElementType.class);
//								if (type == PlwebElementTypes.Group_2002) {
//									EObject modelObject = ((View)getHost().getModel()).getElement();
//									request.getExtendedData().put("element", modelObject);
//									EditPart documentationCoreEditPart = getHost().getParent();
//									System.out.println(documentationCoreEditPart);
//									return documentationCoreEditPart == null ? null
//											: documentationCoreEditPart
//													.getCommand(request);
//								}
//							}
//							return super.getCommand(request);
//						}
//						return null;
//					}
//				}
//		);
	}
	
	protected LayoutEditPolicy createLayoutEditPolicy() {
		LayoutEditPolicy lep = new XYLayoutEditPolicy() {
		
			protected EditPolicy createChildEditPolicy(EditPart child) {
				if (child instanceof IBorderItemEditPart) {
					return new BorderItemSelectionEditPolicy();
				}
				
				EditPolicy result = new NonResizableEditPolicy() {
					@Override
					protected IFigure createDragSourceFeedbackFigure() {
						return null;
					}
					
				};

				return result;
			}
		};
		return lep;
	}
	
	/**
	 * Performs relayout on create, move children or resize children request.<p>
	 *
	 * @param request
	 * @return
	 */
	@Override
	public Command getCommand(Request request) {
		Command command = super.getCommand(request);
//		if (request.getType().equals(REQ_CREATE)) {
//			ArrangeRequest layoutRequest = new ArrangeRequest(RequestConstants.REQ_ARRANGE_DEFERRED);
//			List editParts = getChildren();
//			layoutRequest.setViewAdaptersToArrange(new ArrayList(editParts));
//			Command layoutCommand = super.getCommand(layoutRequest);
//			if (layoutCommand != null && command != null) {
//				command = command.chain(layoutCommand);
//			}
//		}
		return command;
	}

}
