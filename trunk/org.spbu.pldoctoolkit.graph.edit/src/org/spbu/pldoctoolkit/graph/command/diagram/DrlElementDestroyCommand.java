/**
 * 
 */
package org.spbu.pldoctoolkit.graph.command.diagram;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.spbu.pldoctoolkit.graph.DrlElement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Alexey Semenov
 *
 */
public class DrlElementDestroyCommand extends DestroyElementCommand {

	private Node rightNeighbour;

	/**
	 * @param request
	 */
	public DrlElementDestroyCommand(DestroyElementRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		DrlElement destructee = (DrlElement) getElementToDestroy();
		
		Element destructeeNode = destructee.getNode();
		rightNeighbour = destructeeNode.getNextSibling();
		destructeeNode.getParentNode().removeChild(destructeeNode);
		
		return super.doExecuteWithResult(monitor, info);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doRedo(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		DrlElement destructee = (DrlElement) getElementToDestroy();
		
		Element destructeeNode = destructee.getNode();
		destructeeNode.getParentNode().removeChild(destructeeNode);

		return super.doRedo(monitor, info);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doUndo(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		IStatus result = super.doUndo(monitor, info);
		
		DrlElement destructee = (DrlElement) getElementToDestroy();
		DrlElement parent = (DrlElement) getElementToEdit();
		
		parent.getNode().insertBefore(destructee.getNode(), rightNeighbour);
		
		return result;
	}

	
}
