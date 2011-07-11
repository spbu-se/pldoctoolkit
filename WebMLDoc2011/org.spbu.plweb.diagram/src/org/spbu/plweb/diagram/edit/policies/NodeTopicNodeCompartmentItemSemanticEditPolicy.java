package org.spbu.plweb.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.spbu.plweb.diagram.edit.commands.DocTopic5CreateCommand;
import org.spbu.plweb.diagram.providers.PlwebElementTypes;

/**
 * @generated
 */
public class NodeTopicNodeCompartmentItemSemanticEditPolicy extends
		PlwebBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public NodeTopicNodeCompartmentItemSemanticEditPolicy() {
		super(PlwebElementTypes.Node_2006);
	}
	
	protected Command getSemanticCommand(IEditCommandRequest request) {
		if (request instanceof DestroyRequest) {
			return null;
		}
		return super.getSemanticCommand(request);
	}

}
