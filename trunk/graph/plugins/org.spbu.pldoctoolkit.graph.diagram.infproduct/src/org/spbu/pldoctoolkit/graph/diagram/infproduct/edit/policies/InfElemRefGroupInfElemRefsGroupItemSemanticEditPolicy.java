package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies;

import org.eclipse.gef.commands.Command;

import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyReferenceCommand;

import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.part.DrlModelDiagramEditorPlugin;

/**
 * @generated
 */
public class InfElemRefGroupInfElemRefsGroupItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelBaseItemSemanticEditPolicy#getCreateRelationshipCommand(org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest)
	 */
	@Override
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		DrlModelDiagramEditorPlugin.getInstance().logInfo(
				"getCreateRelationshipCommand");
		return super.getCreateRelationshipCommand(req);
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelBaseItemSemanticEditPolicy#getConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 */
	@Override
	protected Command getConfigureCommand(ConfigureRequest req) {
		DrlModelDiagramEditorPlugin.getInstance()
				.logInfo("getConfigureCommand");
		return super.getConfigureCommand(req);
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelBaseItemSemanticEditPolicy#getCreateCommand(org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest)
	 */
	@Override
	protected Command getCreateCommand(CreateElementRequest req) {
		DrlModelDiagramEditorPlugin.getInstance().logInfo("getCreateCommand");
		return super.getCreateCommand(req);
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelBaseItemSemanticEditPolicy#getDuplicateCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest)
	 */
	@Override
	protected Command getDuplicateCommand(DuplicateElementsRequest req) {
		DrlModelDiagramEditorPlugin.getInstance()
				.logInfo("getDuplicateCommand");
		return super.getDuplicateCommand(req);
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelBaseItemSemanticEditPolicy#getEditContextCommand(org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest)
	 */
	@Override
	protected Command getEditContextCommand(GetEditContextRequest req) {
		DrlModelDiagramEditorPlugin.getInstance().logInfo(
				"getEditContextCommand");
		return super.getEditContextCommand(req);
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelBaseItemSemanticEditPolicy#getMoveCommand(org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest)
	 */
	@Override
	protected Command getMoveCommand(MoveRequest req) {
		DrlModelDiagramEditorPlugin.getInstance().logInfo("getMoveCommand");
		return super.getMoveCommand(req);
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelBaseItemSemanticEditPolicy#getSemanticCommand(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 */
	@Override
	protected Command getSemanticCommand(IEditCommandRequest request) {
		DrlModelDiagramEditorPlugin.getInstance().logInfo(
				"getSemanticCommand, request: " + request.toString());
		return super.getSemanticCommand(request);
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelBaseItemSemanticEditPolicy#getSetCommand(org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest)
	 */
	@Override
	protected Command getSetCommand(SetRequest req) {
		DrlModelDiagramEditorPlugin.getInstance().logInfo("getSetCommand");
		return super.getSetCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getDestroyReferenceCommand(DestroyReferenceRequest req) {
		return getMSLWrapper(new DestroyReferenceCommand(req));
	}
}
