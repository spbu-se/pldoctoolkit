package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelReferenceConnectionEditPolicy;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.InfElemRefGroupInfElemRefsGroupItemSemanticEditPolicy;

/**
 * @generated NOT
 */
public class InfElemRefGroupInfElemRefsGroupEditPart extends InfElemRefEditPart {

	public InfElemRefGroupInfElemRefsGroupEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3003;

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new DrlModelReferenceConnectionEditPolicy());
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new InfElemRefGroupInfElemRefsGroupItemSemanticEditPolicy());
	}

}
