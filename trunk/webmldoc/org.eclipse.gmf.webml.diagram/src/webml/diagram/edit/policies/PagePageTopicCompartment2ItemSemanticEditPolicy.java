/*
 * 
 */
package webml.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import webml.diagram.edit.commands.DocTopicCreateCommand;
import webml.diagram.providers.WebmlElementTypes;

/**
 * @generated
 */
public class PagePageTopicCompartment2ItemSemanticEditPolicy extends
		WebmlBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public PagePageTopicCompartment2ItemSemanticEditPolicy() {
		super(WebmlElementTypes.Page_2002);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (WebmlElementTypes.DocTopic_3003 == req.getElementType()) {
			return getGEFWrapper(new DocTopicCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
