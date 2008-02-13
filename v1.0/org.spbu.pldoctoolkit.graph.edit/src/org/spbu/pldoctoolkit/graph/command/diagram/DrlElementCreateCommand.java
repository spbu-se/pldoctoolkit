package org.spbu.pldoctoolkit.graph.command.diagram;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.spbu.pldoctoolkit.graph.DrlElement;

public class DrlElementCreateCommand extends CreateElementCommand {

	protected DrlElementCreateHelper helper;

	public DrlElementCreateCommand(CreateElementRequest request) {
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