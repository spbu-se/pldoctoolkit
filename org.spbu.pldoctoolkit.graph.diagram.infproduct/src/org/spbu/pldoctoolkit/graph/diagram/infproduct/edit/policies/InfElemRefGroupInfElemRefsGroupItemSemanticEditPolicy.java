package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyReferenceCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.part.DrlModelDiagramEditorPlugin;

/**
 * @generated
 */
public class InfElemRefGroupInfElemRefsGroupItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelBaseItemSemanticEditPolicy#getSemanticCommand(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 */
	@Override
	protected Command getSemanticCommand(IEditCommandRequest request) {
		DrlModelDiagramEditorPlugin.getInstance().logInfo(
				"getSemanticCommand, request: " + request.toString());
		return super.getSemanticCommand(request);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.SemanticEditPolicy#completeRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 */
	@Override
	protected IEditCommandRequest completeRequest(IEditCommandRequest request) {
		IEditCommandRequest result = request;
		if (result instanceof DestroyRequest) {
			DestroyRequest destroyRequest = (DestroyRequest) result;
			if (getHostElement() != null) {
				if (destroyRequest instanceof DestroyElementRequest) {
					((DestroyElementRequest) destroyRequest)
							.setElementToDestroy(getHostElement());
					((DestroyElementRequest) destroyRequest).getParameters()
							.clear();
				} else {
					result = new DestroyElementRequest(request
							.getEditingDomain(), getHostElement(),
							destroyRequest.isConfirmationRequired());
					result.addParameters(request.getParameters());
				}
			} else if (getHost() instanceof org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart) {
				EObject container = ViewUtil
						.resolveSemanticElement(((Edge) getHost().getModel())
								.getSource());
				EObject referenceObject = ViewUtil
						.resolveSemanticElement(((Edge) getHost().getModel())
								.getTarget());
				if (destroyRequest instanceof DestroyReferenceRequest) {
					DestroyReferenceRequest destroyReferenceRequest = (DestroyReferenceRequest) result;
					destroyReferenceRequest.setContainer(container);
					destroyReferenceRequest
							.setReferencedObject(referenceObject);
				} else {
					result = new DestroyReferenceRequest(
							((IGraphicalEditPart) getHost()).getEditingDomain(),
							container, null, referenceObject, destroyRequest
									.isConfirmationRequired());
					result.addParameters(request.getParameters());
				}
			}
		}
		return result;
	}

	// XXX remove
	private EObject getHostElement() {
		return ViewUtil.resolveSemanticElement((View) getHost().getModel());
	}

	/**
	 * @generated
	 */
	protected Command getDestroyReferenceCommand(DestroyReferenceRequest req) {
		return getMSLWrapper(new DestroyReferenceCommand(req));
	}
}
