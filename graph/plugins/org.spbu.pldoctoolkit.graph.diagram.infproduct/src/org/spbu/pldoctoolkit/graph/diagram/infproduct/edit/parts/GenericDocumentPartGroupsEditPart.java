package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts;

import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.draw2d.Connection;

import org.eclipse.gef.EditPolicy;

import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;

import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelReferenceConnectionEditPolicy;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.GenericDocumentPartGroupsItemSemanticEditPolicy;

/**
 * @generated
 */
public class GenericDocumentPartGroupsEditPart extends ConnectionNodeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3002;

	/**
	 * @generated
	 */
	public GenericDocumentPartGroupsEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new DrlModelReferenceConnectionEditPolicy());
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new GenericDocumentPartGroupsItemSemanticEditPolicy());
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */
	protected Connection createConnectionFigure() {
		return new InfElemRefGroupConnectionFigure();
	}

	/**
	 * @generated
	 */
	public class InfElemRefGroupConnectionFigure extends
			org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx {

		/**
		 * @generated
		 */
		public InfElemRefGroupConnectionFigure() {

			this.setLineStyle(org.eclipse.draw2d.Graphics.LINE_DASH);
		}

	}

}
