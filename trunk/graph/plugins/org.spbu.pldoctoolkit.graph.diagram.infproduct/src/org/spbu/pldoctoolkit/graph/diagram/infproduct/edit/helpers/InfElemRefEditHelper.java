package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.helpers;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.part.DrlModelDiagramEditorPlugin;

/**
 * @generated
 */
public class InfElemRefEditHelper extends DrlModelBaseEditHelper {

	//XXX delete this

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.helpers.DrlModelBaseEditHelper#getDestroyElementCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest)
	 */
	@Override
	protected ICommand getDestroyElementCommand(DestroyElementRequest req) {
		DrlModelDiagramEditorPlugin.getInstance().logInfo(
				"getDestroyElementCommand");
		return super.getDestroyElementCommand(req);
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.helpers.DrlModelBaseEditHelper#getDestroyReferenceCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest)
	 */
	@Override
	protected ICommand getDestroyReferenceCommand(DestroyReferenceRequest req) {
		DrlModelDiagramEditorPlugin.getInstance().logInfo(
				"getDestroyReferenceCommand");
		return super.getDestroyReferenceCommand(req);
	}

}