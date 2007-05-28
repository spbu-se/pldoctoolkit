package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DocumentationCoreCanonicalEditPolicy;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DocumentationCoreItemSemanticEditPolicy;

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
		
//		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
//				new DiagramDragDropEditPolicy() {
//					public Command getDropObjectsCommand(
//							DropObjectsRequest dropRequest) {
//						List viewDescriptors = new ArrayList();
//						for (Iterator it = dropRequest.getObjects().iterator(); it
//								.hasNext();) {
//							viewDescriptors
//									.add(new CreateViewRequest.ViewDescriptor(
//											new EObjectAdapter((EObject) it
//													.next()), Node.class, null,
//											getDiagramPreferencesHint()));
//						}
//						return createShortcutsCommand(dropRequest,
//								viewDescriptors);
//					}
//
//					private Command createShortcutsCommand(
//							DropObjectsRequest dropRequest, List viewDescriptors) {
//						Command command = createViewsAndArrangeCommand(
//								dropRequest, viewDescriptors);
//						if (command != null) {
//							return command
//									.chain(new ICommandProxy(
//											new DrlModelCreateShortcutDecorationsCommand(
//													getEditingDomain(),
//													(View) getModel(),
//													viewDescriptors)));
//						}
//						return null;
//					}
//					
//				});
		
//		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
	}

	private EditPolicy createLayoutEditPolicy() {
		LayoutEditPolicy lep = new XYLayoutEditPolicy() {

//			protected EditPolicy createChildEditPolicy(EditPart child) {
//				EditPolicy result = child
//						.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
//				if (result == null) {
//					result = new XYLayoutEditPolicy();
//				}
//				return result;
//			}

//			protected Command getMoveChildrenCommand(Request request) {
//
//			}

			protected Command getCreateCommand(CreateRequest request) {
				Command command = super.getCreateCommand(request);
				CompoundCommand cc = new CompoundCommand();
				if(command != null && request.getNewObject() != null) {
					cc.add(command);
					
					ArrangeRequest arrangeRequest = new ArrangeRequest(
							RequestConstants.REQ_ARRANGE_DEFERRED);
					List shapesList = new ArrayList(getHost().getChildren());
//					shapesList.add(request.getNewObjectType());
					shapesList.add(((CreateViewAndElementRequest)request).getViewAndElementDescriptor().getElementAdapter());
//					DrlModelDiagramEditorPlugin.getInstance().logInfo("new obj: " + request.getNewObject());
					arrangeRequest.setViewAdaptersToArrange(shapesList);
					// a list of adapters to all the shapes you will create,
					// this list of adapters has to be created at command
					// creation time,
					// but they are only adaptable at command execution time);
					Command arrangeCommand = getHost().getCommand(
							arrangeRequest);
					 
					cc.add(arrangeCommand);
				}
				
				return cc;
			}
		};
		return lep;
	}

}
