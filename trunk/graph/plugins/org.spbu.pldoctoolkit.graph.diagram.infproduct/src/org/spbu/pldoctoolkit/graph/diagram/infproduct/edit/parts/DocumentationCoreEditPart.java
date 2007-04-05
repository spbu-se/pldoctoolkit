package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.DrlModelCreateShortcutDecorationsCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DocumentationCoreCanonicalEditPolicy;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DocumentationCoreItemSemanticEditPolicy;

/**
 * @generated
 */
public class DocumentationCoreEditPart extends DiagramEditPart {

	/**
	 * @generated
	 */
	public static final String MODEL_ID = "DrlModel"; //$NON-NLS-1$

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
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new DocumentationCoreItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
				new DocumentationCoreCanonicalEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
				new DiagramDragDropEditPolicy() {
					public Command getDropObjectsCommand(
							DropObjectsRequest dropRequest) {
						List viewDescriptors = new ArrayList();
						for (Iterator it = dropRequest.getObjects().iterator(); it
								.hasNext();) {
							viewDescriptors
									.add(new CreateViewRequest.ViewDescriptor(
											new EObjectAdapter((EObject) it
													.next()), Node.class, null,
											getDiagramPreferencesHint()));
						}
						return createShortcutsCommand(dropRequest,
								viewDescriptors);
					}

					private Command createShortcutsCommand(
							DropObjectsRequest dropRequest, List viewDescriptors) {
						Command command = createViewsAndArrangeCommand(
								dropRequest, viewDescriptors);
						if (command != null) {
							return command
									.chain(new ICommandProxy(
											new DrlModelCreateShortcutDecorationsCommand(
													getEditingDomain(),
													(View) getModel(),
													viewDescriptors)));
						}
						return null;
					}
				});
	}
}
