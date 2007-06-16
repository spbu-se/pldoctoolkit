package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.BorderItemSelectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ContainerEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DocumentationCoreCanonicalEditPolicy;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DocumentationCoreItemSemanticEditPolicy;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.layout.TopDownLayoutProvider;

/**
 * @generated
 */
public class DocumentationCoreEditPart extends DiagramEditPart {

	/**
	 * @generated
	 */
	public final static String MODEL_ID = "InfProduct"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 79;

	/**
	 * @generated
	 */
	public DocumentationCoreEditPart(View view) {
		super(view);
	}

	private TopDownLayoutProvider layoutProvider = new TopDownLayoutProvider();

	/**
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new DocumentationCoreItemSemanticEditPolicy());
		
		//HAND
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
				new DocumentationCoreCanonicalEditPolicy());
		removeEditPolicy(EditPolicyRoles.POPUPBAR_ROLE);
		
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new ContainerEditPolicy() {
			protected Command getArrangeCommand(ArrangeRequest request) {
				if (layoutProvider.isWorking()) {
					return null;
				}
				return super.getArrangeCommand(request);
			}

			public Runnable layoutNodes(List nodes, boolean offsetFromBoundingBox, IAdaptable layoutHint) {
				return layoutProvider.layoutLayoutNodes(nodes, offsetFromBoundingBox, layoutHint);
			}
		});
		
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
	}

	protected LayoutEditPolicy createLayoutEditPolicy() {
		LayoutEditPolicy lep = new XYLayoutEditPolicy() {

			protected EditPolicy createChildEditPolicy(EditPart child) {
				if (child instanceof IBorderItemEditPart) {
					return new BorderItemSelectionEditPolicy();
				}
				EditPolicy result = child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
				if (result == null) {
					result = new NonResizableEditPolicy();
				}
				return result;
			}

//			protected Command getCreateCommand(CreateRequest request) {
//				return null;
//			}
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
		System.out.println("req: " + request);
		
//		if(CreateViewRequest.class.isAssignableFrom(request.getClass())) {
//			System.out.println("is craete view!");
//		}
		
		Command command = super.getCommand(request);
		if (request.getType().equals(REQ_CREATE) 
				|| request.getType().equals(REQ_MOVE_CHILDREN) 
				|| request.getType().equals(REQ_RESIZE_CHILDREN)
//				|| isCreateViewRequest(request)
				) {
			System.out.println("here!!" + (isCreateViewRequest(request)? " and view!" : ""));
			ArrangeRequest layoutRequest = new ArrangeRequest(RequestConstants.REQ_ARRANGE_DEFERRED);
			List editParts = getChildren();
			layoutRequest.setViewAdaptersToArrange(new ArrayList(editParts));
//			if(isCreateViewRequest(request)) {
//				layoutRequest.setViewAdaptersToArrange(((CreateViewRequest)request).getViewDescriptors());
//			}
			Command layoutCommand = super.getCommand(layoutRequest);
			if (layoutCommand != null && command != null) {
				command = command.chain(layoutCommand);
			}
		}
		return command;
	}

	private boolean isCreateViewRequest(Request request) {
		return CreateViewRequest.class.isAssignableFrom(request.getClass());
	}
	

}
