package org.spbu.pldoctoolkit.graph.command.diagram;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.spbu.pldoctoolkit.graph.DrlElement;
import org.spbu.pldoctoolkit.graph.util.IdUtil;
import org.w3c.dom.Node;

public class DrlElementCreateCommand extends CreateElementCommand {

	public DrlElementCreateCommand(CreateElementRequest request) {
		super(request);
	}

	@Override
	protected EObject doDefaultElementCreation() {
		DrlElement newElement = (DrlElement) super.doDefaultElementCreation();
		
		if(newElement != null) {
			newElement.initializeNode(getContainmentFeature());
			IdUtil.initializeId(newElement);
		}
		
		DrlElement container = (DrlElement) newElement.eContainer();
		container.getNode().appendChild(newElement.getNode());
		
		return newElement;
	}

	@Override
	protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
				IStatus result = super.doRedo(monitor, info);
				
				DrlElement newElement = (DrlElement) getNewElement();
				DrlElement container = (DrlElement) newElement.eContainer();
				container.getNode().appendChild(newElement.getNode());
				
				return result;
			}

	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
				DrlElement newElement = (DrlElement) getNewElement();
				Node elementNode = newElement.getNode();
				
				elementNode.getParentNode().removeChild(elementNode);
				
				return super.doUndo(monitor, info);
			}

}