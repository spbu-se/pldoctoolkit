package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.InfElemRef2ItemSemanticEditPolicy;

/**
 * @generated
 */
public class InfElemRef2EditPart extends ConnectionNodeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3003;

	/**
	 * @generated
	 */
	public InfElemRef2EditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new InfElemRef2ItemSemanticEditPolicy());
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

		return new InfElemRefFigure();
	}

	/**
	 * @generated
	 */
	public class InfElemRefFigure extends PolylineConnectionEx {

		/**
		 * @generated
		 */
		public InfElemRefFigure() {
			this.setFill(true);
			this.setFillXOR(false);
			this.setOutline(true);
			this.setOutlineXOR(false);
			this.setLineWidth(1);
			this.setLineStyle(Graphics.LINE_SOLID);

			setTargetDecoration(createTargetDecoration());
		}

		/**
		 * @generated
		 */
		private RotatableDecoration createTargetDecoration() {
			PolygonDecoration df = new PolygonDecoration();
			df.setFill(false);
			df.setFillXOR(false);
			df.setOutline(true);
			df.setOutlineXOR(false);
			df.setLineWidth(1);
			df.setLineStyle(Graphics.LINE_SOLID);
			PointList pl = new PointList();
			pl.addPoint(getMapMode().DPtoLP(1), getMapMode().DPtoLP(4));
			pl.addPoint(getMapMode().DPtoLP(3), getMapMode().DPtoLP(3));
			pl.addPoint(getMapMode().DPtoLP(4), getMapMode().DPtoLP(1));
			pl.addPoint(getMapMode().DPtoLP(4), getMapMode().DPtoLP(-1));
			pl.addPoint(getMapMode().DPtoLP(3), getMapMode().DPtoLP(-3));
			pl.addPoint(getMapMode().DPtoLP(1), getMapMode().DPtoLP(-4));
			pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(-4));
			pl.addPoint(getMapMode().DPtoLP(-3), getMapMode().DPtoLP(-3));
			pl.addPoint(getMapMode().DPtoLP(-4), getMapMode().DPtoLP(-1));
			pl.addPoint(getMapMode().DPtoLP(-4), getMapMode().DPtoLP(1));
			pl.addPoint(getMapMode().DPtoLP(-3), getMapMode().DPtoLP(3));
			pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(4));
			pl.addPoint(getMapMode().DPtoLP(1), getMapMode().DPtoLP(4));
			df.setTemplate(pl);
			df.setScale(getMapMode().DPtoLP(1), getMapMode().DPtoLP(1));
			return df;
		}

	}

}
