/*
 * 
 */
package webml.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import webml.diagram.edit.commands.OperationUnit2CreateCommand;
import webml.diagram.edit.commands.Page2CreateCommand;
import webml.diagram.providers.WebmlElementTypes;

/**
 * @generated
 */
public class AreaAreaElementCompartmentItemSemanticEditPolicy extends
		WebmlBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public AreaAreaElementCompartmentItemSemanticEditPolicy() {
		super(WebmlElementTypes.Area_2001);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (WebmlElementTypes.Page_3001 == req.getElementType()) {
			return getGEFWrapper(new Page2CreateCommand(req));
		}
		if (WebmlElementTypes.OperationUnit_3004 == req.getElementType()) {
			return getGEFWrapper(new OperationUnit2CreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
