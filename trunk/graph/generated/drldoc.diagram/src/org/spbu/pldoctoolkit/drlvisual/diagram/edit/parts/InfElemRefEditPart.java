package org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts;

import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.draw2d.Connection;

import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;

import org.spbu.pldoctoolkit.drlvisual.diagram.edit.policies.InfElemRefItemSemanticEditPolicy;

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
	 * @generated NOT
	 */
	protected Connection createConnectionFigure() {
		return new InfElemRefFigure();
	}

	/**
	 * @generated
	 */
	public class InfElemRefFigure extends
			org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx {

		/**
		 * @generated
		 */
		public InfElemRefFigure() {

			setTargetDecoration(createTargetDecoration());
		}

		/**
		 * @generated NOT
		 */
		private org.eclipse.draw2d.PolygonDecoration createTargetDecoration() {
			org.eclipse.draw2d.PolygonDecoration df = new org.eclipse.draw2d.PolygonDecoration();
			// dispatchNext?

			org.eclipse.draw2d.geometry.PointList pl = new org.eclipse.draw2d.geometry.PointList();
			pl.addPoint(1, 4);
			pl.addPoint(3, 3);
			pl.addPoint(4, 1);
			pl.addPoint(4, -1);
			pl.addPoint(3, -3);
			pl.addPoint(1, -4);
			pl.addPoint(-1, -4);
			pl.addPoint(-3, -3);
			pl.addPoint(-4, -1);
			pl.addPoint(-4, 1);
			pl.addPoint(-3, 3);
			pl.addPoint(-1, 4);
			pl.addPoint(1, 4);
			df.setTemplate(pl);
			df.setScale(getMapMode().DPtoLP(1), getMapMode().DPtoLP(1));
			df.setFill(false);

			return df;
		}

	}

}
