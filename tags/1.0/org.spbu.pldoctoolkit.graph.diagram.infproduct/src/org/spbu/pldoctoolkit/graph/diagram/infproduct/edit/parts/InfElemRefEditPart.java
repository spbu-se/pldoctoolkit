package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ComponentEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.InfElemRefItemSemanticEditPolicy;

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
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new InfElemRefItemSemanticEditPolicy());
		
		//HAND
		// disable delete from keyboard
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
			@Override
			protected Command createDeleteSemanticCommand(
					GroupRequest deleteRequest) {
				return UnexecutableCommand.INSTANCE;
			}

			@Override
			protected Command createDeleteViewCommand(GroupRequest deleteRequest) {
				return UnexecutableCommand.INSTANCE;
			}
		});
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

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart#handleNotificationEvent(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	protected void handleNotificationEvent(Notification notification) {
		super.handleNotificationEvent(notification);

		((InfElemRefFigure) getFigure()).updateTargetDecoration();
	}

	/**
	 * @generated
	 */
	public class InfElemRefFigure extends PolylineConnectionEx {

		PolygonDecoration circleDecoration;

		/**
		 * @generated NOT
		 */
		public InfElemRefFigure() {
			circleDecoration = createTargetDecoration();

			updateTargetDecoration();
		}

		public void updateTargetDecoration() {
			InfElemRef infElemRefInstance = (InfElemRef) ((View) getModel())
					.getElement();
			boolean isShowCircle = infElemRefInstance.isOptional();

			RotatableDecoration decoration;
			if (isShowCircle) {
				decoration = circleDecoration;
			} else {
				decoration = null;
			}

			setTargetDecoration(decoration);
		}

		/**
		 * @generated NOT
		 */
		private PolygonDecoration createTargetDecoration() {
			PolygonDecoration df = new PolygonDecoration();
			// dispatchNext?

			PointList pl = new PointList();
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
