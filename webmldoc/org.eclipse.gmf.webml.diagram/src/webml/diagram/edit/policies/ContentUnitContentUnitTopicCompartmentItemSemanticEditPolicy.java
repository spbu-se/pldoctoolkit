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
public class ContentUnitContentUnitTopicCompartmentItemSemanticEditPolicy
		extends WebmlBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public ContentUnitContentUnitTopicCompartmentItemSemanticEditPolicy() {
		super(WebmlElementTypes.ContentUnit_3002);
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
