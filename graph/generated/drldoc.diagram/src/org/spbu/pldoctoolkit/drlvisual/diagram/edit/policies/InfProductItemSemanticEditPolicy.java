package org.spbu.pldoctoolkit.drlvisual.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.drlvisual.diagram.providers.DRLModelElementTypes;

/**
 * @generated
 */
public class InfProductItemSemanticEditPolicy extends
		DRLModelBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return getMSLWrapper(new DestroyElementCommand(req) {

			protected EObject getElementToDestroy() {
				View view = (View) getHost().getModel();
				EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
				if (annotation != null) {
					return view;
				}
				return super.getElementToDestroy();
			}

		});
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (DRLModelElementTypes.InfElemRef_3001 == req.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingInfElemRef3001Command(req)
					: null;
		}
		if (DRLModelElementTypes.InfElemRef_3002 == req.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingInfElemRef3002Command(req)
					: null;
		}
		if (DRLModelElementTypes.SubelementedElementElements_3003 == req
				.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingSubelementedElement_Elements3003Command(req)
					: null;
		}
		return super.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getCreateStartOutgoingInfElemRef3001Command(
			CreateRelationshipRequest req) {
		return new Command() {
		};
	}

	/**
	 * @generated
	 */
	protected Command getCreateStartOutgoingInfElemRef3002Command(
			CreateRelationshipRequest req) {
		return new Command() {
		};
	}

	/**
	 * @generated
	 */
	protected Command getCreateStartOutgoingSubelementedElement_Elements3003Command(
			CreateRelationshipRequest req) {

		return new Command() {
		};
	}
}
