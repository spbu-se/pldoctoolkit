/*
 * 
 */
package webml.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;

import webml.diagram.providers.WebmlElementTypes;

/**
 * @generated
 */
public class NormalLinkItemSemanticEditPolicy extends
		WebmlBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public NormalLinkItemSemanticEditPolicy() {
		super(WebmlElementTypes.NormalLink_4003);
	}

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return getGEFWrapper(new DestroyElementCommand(req));
	}

}
