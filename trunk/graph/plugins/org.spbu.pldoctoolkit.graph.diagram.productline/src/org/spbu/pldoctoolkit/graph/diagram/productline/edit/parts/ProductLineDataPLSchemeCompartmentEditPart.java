package org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies.ProductLineDataPLSchemeCompartmentCanonicalEditPolicy;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.Messages;

/**
 * @generated
 */
public class ProductLineDataPLSchemeCompartmentEditPart extends
		ListCompartmentEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 5002;

	/**
	 * @generated
	 */
	public ProductLineDataPLSchemeCompartmentEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected boolean hasModelChildrenChanged(Notification evt) {
		return false;
	}

	/**
	 * @generated
	 */
	public String getCompartmentName() {
		return Messages.ProductLineDataPLSchemeCompartmentEditPart_title;
	}

	/**
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		removeEditPolicy(EditPolicyRoles.SEMANTIC_ROLE);
		installEditPolicy(EditPolicyRoles.CREATION_ROLE,
				new CreationEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
				new DragDropEditPolicy());

		//HAND
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
				new ProductLineDataPLSchemeCompartmentCanonicalEditPolicy());
		removeEditPolicy(EditPolicyRoles.POPUPBAR_ROLE);
	}

	/**
	 * @generated NOT
	 */
	public IFigure createFigure() {
		ResizableCompartmentFigure result = (ResizableCompartmentFigure) super
				.createFigure();
		result.setTitleVisibility(false);

		//HAND
		ConstrainedToolbarLayout layout = (ConstrainedToolbarLayout) result
				.getContentPane().getLayoutManager();
		layout.setSpacing(3);
		layout.setStretchMinorAxis(true);
		layout.setStretchMajorAxis(true);

		return result;
	}

	/**
	 * @generated
	 */
	protected void setRatio(Double ratio) {
		if (getFigure().getParent().getLayoutManager() instanceof ConstrainedToolbarLayout) {
			super.setRatio(ratio);
		}
	}

}
