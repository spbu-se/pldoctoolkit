package org.spbu.pldoctoolkit.drlvisual.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.core.commands.ExecutionException;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.gef.commands.UnexecutableCommand;

import org.eclipse.gmf.runtime.common.core.command.CommandResult;

import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;

import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;

import org.spbu.pldoctoolkit.drlvisual.SubelementedElement;

import org.spbu.pldoctoolkit.drlvisual.diagram.providers.DRLModelElementTypes;

import org.spbu.pldoctoolkit.drlvisual.drlPackage;

/**
 * @generated
 */
public class InfElemRefGroupItemSemanticEditPolicy extends
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

			protected CommandResult doExecuteWithResult(
					IProgressMonitor progressMonitor, IAdaptable info)
					throws ExecutionException {
				EObject eObject = getElementToDestroy();
				boolean removeFromResource = eObject.eContainer() == null;
				CommandResult result = super.doExecuteWithResult(
						progressMonitor, info);
				Resource resource = eObject.eResource();
				if (removeFromResource && resource != null) {
					resource.getContents().remove(eObject);
				}
				return result;
			}
		});
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (DRLModelElementTypes.SubelementedElementElements_3003 == req
				.getElementType()) {
			return req.getTarget() == null ? null
					: getCreateCompleteIncomingSubelementedElement_Elements3003Command(req);
		}
		return super.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCompleteIncomingSubelementedElement_Elements3003Command(
			CreateRelationshipRequest req) {
		if (!(req.getSource() instanceof SubelementedElement)) {
			return UnexecutableCommand.INSTANCE;
		}
		SubelementedElement element = (SubelementedElement) req.getSource();
		if (element.getElements().contains(req.getTarget())) {
			return UnexecutableCommand.INSTANCE;
		}
		SetRequest setReq = new SetRequest(req.getSource(),
				drlPackage.eINSTANCE.getSubelementedElement_Elements(), req
						.getTarget());
		return getMSLWrapper(new SetValueCommand(setReq));
	}
}
