/**
 * 
 */
package org.spbu.pldoctoolkit.graph.command.diagram;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateRelationshipCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.spbu.pldoctoolkit.graph.DrlElement;

/**
 * @author Alexey Semenov
 *
 */
public class DrlElementCreateRelationshipCommand extends
		CreateRelationshipCommand {

	private DrlElementCreateHelper helper;

	/**
	 * @param request
	 */
	public DrlElementCreateRelationshipCommand(CreateRelationshipRequest request) {
		super(request);
		helper = new DrlElementCreateHelper();
	}

	@Override
	protected EObject doDefaultElementCreation() {
		DrlElement newElement = (DrlElement) super.doDefaultElementCreation();

		helper.doDefaultElementCreation(newElement);
		
		return newElement;
	}

	@Override
	protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		IStatus result = super.doRedo(monitor, info);

		helper.doRedo(monitor, info);
		
		return result;
	}

	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		helper.doUndo(monitor, info);
		
		return super.doUndo(monitor, info);
	}
}
