/*
 * 
 */
package webml.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import webml.diagram.edit.commands.ContentUnit2CreateCommand;
import webml.diagram.providers.WebmlElementTypes;

/**
 * @generated
 */
public class PagePageElementCompartmentItemSemanticEditPolicy extends
		WebmlBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public PagePageElementCompartmentItemSemanticEditPolicy() {
		super(WebmlElementTypes.Page_3001);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (WebmlElementTypes.ContentUnit_3002 == req.getElementType()) {
			return getGEFWrapper(new ContentUnit2CreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
