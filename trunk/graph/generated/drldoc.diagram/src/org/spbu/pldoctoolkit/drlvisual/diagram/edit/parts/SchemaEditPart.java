package org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts;

import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;

import org.spbu.pldoctoolkit.drlvisual.diagram.edit.policies.SchemaCanonicalEditPolicy;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.policies.SchemaItemSemanticEditPolicy;

/**
 * @generated
 */
public class SchemaEditPart extends DiagramEditPart {

	/**
	 * @generated
	 */
	public static String MODEL_ID = "DRL Model"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 79;

	/**
	 * @generated
	 */
	public SchemaEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new SchemaItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
				new SchemaCanonicalEditPolicy());
	}
}
