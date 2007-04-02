package org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts;

import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;

import org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies.ProductLineCanonicalEditPolicy;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies.ProductLineItemSemanticEditPolicy;

/**
 * @generated
 */
public class ProductLineEditPart extends DiagramEditPart {

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
	public ProductLineEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new ProductLineItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
				new ProductLineCanonicalEditPolicy());
	}
}
