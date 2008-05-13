package org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.policies.InfElemRefItemSemanticEditPolicy;

/**
 * @generated
 */
public class InfElemRefEditPart extends ConnectionNodeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3001;

	/**
	 * @generated
	 */
	public InfElemRefEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new InfElemRefItemSemanticEditPolicy());
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
	public InfElemRefGroupConnectionFigure getPrimaryShape() {
		return (InfElemRefGroupConnectionFigure) getFigure();
	}

	/**
	 * @generated
	 */
	public class InfElemRefGroupConnectionFigure extends PolylineConnectionEx {

		/**
		 * @generated
		 */
		public InfElemRefGroupConnectionFigure() {
			this.setLineStyle(Graphics.LINE_DASH);

		}

	}

}
