package plweb.diagram.edit.policies;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;

import plweb.diagram.edit.commands.AreaCreateCommand;
import plweb.diagram.edit.commands.GroupCreateCommand;
import plweb.diagram.edit.commands.NodeCreateCommand;
import plweb.diagram.edit.commands.PageCreateCommand;
import plweb.diagram.providers.PlwebElementTypes;

/**
 * @generated
 */
public class DiagramRootItemSemanticEditPolicy extends
		PlwebBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public DiagramRootItemSemanticEditPolicy() {
		super(PlwebElementTypes.DiagramRoot_1000);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (PlwebElementTypes.Area_2001 == req.getElementType()) {
			return getGEFWrapper(new AreaCreateCommand(req));
		}
		if (PlwebElementTypes.Group_2002 == req.getElementType()) {
			return getGEFWrapper(new GroupCreateCommand(req));
		}
		if (PlwebElementTypes.Page_2003 == req.getElementType()) {
			return getGEFWrapper(new PageCreateCommand(req));
		}
		if (PlwebElementTypes.Node_2004 == req.getElementType()) {
			return getGEFWrapper(new NodeCreateCommand(req));
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
