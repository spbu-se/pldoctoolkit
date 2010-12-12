package plweb.diagram.edit.parts;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyReferenceCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.notation.View;

import plweb.TargetRefElement;
import plweb.diagram.edit.policies.SourceRefElementClassItemSemanticEditPolicy;

/**
 * @generated NOT
 */
public class SourceRefElementClassEditPart extends ConnectionNodeEditPart
		implements ITreeBranchEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 4001;

	/**
	 * @generated
	 */
	public SourceRefElementClassEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new SourceRefElementClassItemSemanticEditPolicy() {
			
			protected Command getDestroyReferenceCommand(DestroyReferenceRequest req) {
				Command command = getGEFWrapper(new DestroyReferenceCommand(req) {
					protected CommandResult doExecuteWithResult(
							IProgressMonitor progressMonitor,
							IAdaptable info
					) throws ExecutionException {
						EObject referencedObject = getReferencedObject();
						Resource resource = referencedObject.eResource();
						CommandResult result = super.doExecuteWithResult(progressMonitor,info);
						if (resource != null) {
							resource.getContents().add(referencedObject);
							TargetRefElement target = (TargetRefElement) getEdge().getTarget().getElement();
							target.setParent(null);
						}
						return result;
					}
				});
				return command;
			}
		});
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
		return new SourceRefElementClassFigure();
	}

	/**
	 * @generated
	 */
	public SourceRefElementClassFigure getPrimaryShape() {
		return (SourceRefElementClassFigure) getFigure();
	}
	
	@Override
	public void setTarget(EditPart editPart) {
		super.setTarget(editPart);
		if (editPart instanceof TargetRefElementEditPart) {
			TargetRefElementEditPart target = (TargetRefElementEditPart) editPart;
			TargetRefElement targetElement = (TargetRefElement) ((View) target.getModel()).getElement();
			getPrimaryShape().setOptionalLink(targetElement.isOptional());
		}
	}
	
	/**
	 * @generated NOT
	 */
	public class SourceRefElementClassFigure extends PolylineConnectionEx {

		/**
		 * @generated
		 */
		public SourceRefElementClassFigure() {
			this.setLineWidth(1);
		}

		/**
		 * @generated NOT
		 */
		private RotatableDecoration createTargetDecoration() {
			PolygonDecoration df = new PolygonDecoration();
			df.setLineWidth(1);
			PointList pl = new PointList();

			pl.addPoint(getMapMode().DPtoLP(4), getMapMode().DPtoLP(1));
			pl.addPoint(getMapMode().DPtoLP(3), getMapMode().DPtoLP(3));
			pl.addPoint(getMapMode().DPtoLP(1), getMapMode().DPtoLP(4));
			pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(4));
			pl.addPoint(getMapMode().DPtoLP(-3), getMapMode().DPtoLP(3));
			pl.addPoint(getMapMode().DPtoLP(-4), getMapMode().DPtoLP(1));
			pl.addPoint(getMapMode().DPtoLP(-4), getMapMode().DPtoLP(-1));
			pl.addPoint(getMapMode().DPtoLP(-3), getMapMode().DPtoLP(-3));
			pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(-4));
			pl.addPoint(getMapMode().DPtoLP(1), getMapMode().DPtoLP(-4));
			pl.addPoint(getMapMode().DPtoLP(3), getMapMode().DPtoLP(-3));
			pl.addPoint(getMapMode().DPtoLP(4), getMapMode().DPtoLP(-1));
			pl.addPoint(getMapMode().DPtoLP(4), getMapMode().DPtoLP(1));

			df.setTemplate(pl);
			df.setScale(getMapMode().DPtoLP(1), getMapMode().DPtoLP(1));
			df.setBackgroundColor(ColorConstants.white);
			return df;
		}
		
		public void setOptionalLink(boolean flag) {
			if (flag) {
				setTargetDecoration(createTargetDecoration());
			} else {
				setTargetDecoration(null);
			}
		}

	}

}
