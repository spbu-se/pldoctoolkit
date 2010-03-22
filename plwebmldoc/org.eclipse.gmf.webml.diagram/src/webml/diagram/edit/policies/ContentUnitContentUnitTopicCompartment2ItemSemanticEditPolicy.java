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
public class ContentUnitContentUnitTopicCompartment2ItemSemanticEditPolicy
		extends WebmlBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public ContentUnitContentUnitTopicCompartment2ItemSemanticEditPolicy() {
		super(WebmlElementTypes.ContentUnit_2003);
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
