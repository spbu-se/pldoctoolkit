package org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Graphics;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies.ProductInfProductLinkItemSemanticEditPolicy;

/**
 * @generated
 */
public class ProductInfProductLinkEditPart extends ConnectionNodeEditPart 	 {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3001;

	/**
	 * @generated
	 */
	public ProductInfProductLinkEditPart(View view) {
		super(view);
	}

	/**
	 * @generate NOT
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new ProductInfProductLinkItemSemanticEditPolicy());
		
		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, null);
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, null);

		disableEditMode();
		
		//HAND
		// disable delete from keyboard
		// not needed as edit mode is now disabled
//		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
//			@Override
//			protected Command createDeleteSemanticCommand(
//					GroupRequest deleteRequest) {
//				return UnexecutableCommand.INSTANCE;
//			}
//
//			@Override
//			protected Command createDeleteViewCommand(GroupRequest deleteRequest) {
//				return UnexecutableCommand.INSTANCE;
//			}
//		});
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
		return new ProductInfProductLinkFigure();
	}

	/**
	 * @generated NOT
	 * 
	 * HAND: commented out
	 */
//	public ProductInfProductLinkFigure getPrimaryShape() {
//		return (ProductInfProductLinkFigure) getFigure();
//	}

	/**
	 * @generated NOT
	 * 
	 * copied manually from GenericDocumentPartGroupsEditPart
	 * + renamed
	 */
	public class ProductInfProductLinkFigure extends PolylineConnectionEx {

		public ProductInfProductLinkFigure() {
			this.setFill(true);
			this.setFillXOR(false);
			this.setOutline(true);
			this.setOutlineXOR(false);
			this.setLineWidth(1);
			this.setLineStyle(Graphics.LINE_DASH);
		}

	}

	
	}
