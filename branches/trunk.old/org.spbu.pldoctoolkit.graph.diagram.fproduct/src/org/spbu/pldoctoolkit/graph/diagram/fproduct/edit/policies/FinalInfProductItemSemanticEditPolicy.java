package org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.policies;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.commands.InfElementCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.commands.InfProductCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class FinalInfProductItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (DrlModelElementTypes.InfProduct_1001 == req.getElementType()) {
			return getGEFWrapper(new InfProductCreateCommand(req));
		}
		if (DrlModelElementTypes.InfElement_1002 == req.getElementType()) {
			return getGEFWrapper(new InfElementCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getDuplicateCommand(DuplicateElementsRequest req) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
				.getEditingDomain();
		return getGEFWrapper(new DuplicateAnythingCommand(editingDomain, req));
	}

	/**
	 * @generated
	 */
	private static class DuplicateAnythingCommand extends
			DuplicateEObjectsCommand {

		/**
		 * @generated
		 */
		public DuplicateAnythingCommand(
				TransactionalEditingDomain editingDomain,
				DuplicateElementsRequest req) {
			super(editingDomain, req.getLabel(), req
					.getElementsToBeDuplicated(), req
					.getAllDuplicatedElementsMap());
		}

	}

}
