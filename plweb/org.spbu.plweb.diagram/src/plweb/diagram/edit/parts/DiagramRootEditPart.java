package plweb.diagram.edit.parts;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ContainerEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.notation.View;

import plweb.diagram.edit.policies.DiagramRootCanonicalEditPolicy;
import plweb.diagram.edit.policies.DiagramRootItemSemanticEditPolicy;
import plweb.diagram.layout.TopDownLayoutProvider;

/**
 * @generated NOT
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
	
	private TopDownLayoutProvider layoutProvider = new TopDownLayoutProvider();

	/**
	 * @generated
	 */
	public DiagramRootEditPart(View view) {
		super(view);
	}

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
		
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new ContainerEditPolicy() {

			protected Command getArrangeCommand(ArrangeRequest request) {
				if (layoutProvider.isWorking()) {
					return null;
				}
				return super.getArrangeCommand(request);
			}

			public Runnable layoutNodes(List nodes,
					boolean offsetFromBoundingBox, IAdaptable layoutHint) {
				return layoutProvider.layoutLayoutNodes(nodes,
						offsetFromBoundingBox, layoutHint);
			}
		});
	}
	

	@Override
	protected void handleNotificationEvent(Notification event) {
		super.handleNotificationEvent(event);
		
		if(event.getEventType() == Notification.REMOVE) {
			ArrangeRequest layoutRequest = new ArrangeRequest(ActionIds.ACTION_ARRANGE_ALL);
			if (children != null) {
				layoutRequest.setViewAdaptersToArrange(children);
			}
			performRequest(layoutRequest);
		}
	}

	/**
	 * Performs relayout on create, move children or resize children request.
	 * <p>
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public Command getCommand(Request request) {
		Command command = super.getCommand(request);
		if (request.getType().equals(REQ_CREATE) ||
				request.getType().equals(REQ_CONNECTION_END)) {
			ArrangeRequest layoutRequest = new ArrangeRequest(RequestConstants.REQ_ARRANGE_DEFERRED);
			if (children != null) {
				layoutRequest.setViewAdaptersToArrange(children);
			}
			Command layoutCommand = this.getCommand(layoutRequest);
			if (layoutCommand != null) {
				if (command != null) {
					command = command.chain(layoutCommand);
				}
//				getDiagramEditDomain().getDiagramCommandStack().execute(layoutCommand);
			}
		}
		return command;
	}

}
