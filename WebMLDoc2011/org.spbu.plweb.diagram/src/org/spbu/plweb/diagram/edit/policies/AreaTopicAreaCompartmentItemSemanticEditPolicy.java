package org.spbu.plweb.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.spbu.plweb.diagram.edit.commands.DocTopic3CreateCommand;
import org.spbu.plweb.diagram.providers.PlwebElementTypes;

/**
 * @generated
 */
public class AreaTopicAreaCompartmentItemSemanticEditPolicy extends
		PlwebBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public AreaTopicAreaCompartmentItemSemanticEditPolicy() {
		super(PlwebElementTypes.Area_2003);
	}

	protected Command getSemanticCommand(IEditCommandRequest request) {
		if (request instanceof DestroyRequest) {
			return null;
		}
		return super.getSemanticCommand(request);
	}
}
