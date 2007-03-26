package org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts;

import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;

import org.spbu.pldoctoolkit.drlvisual.diagram.edit.policies.DocumentationCoreCanonicalEditPolicy;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.policies.DocumentationCoreItemSemanticEditPolicy;

/**
 * @generated
 */
public class DocumentationCoreEditPart extends DiagramEditPart {

	/**
	 * @generated
	 */
	public static String MODEL_ID = "DrlModel"; //$NON-NLS-1$

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
	}
}
