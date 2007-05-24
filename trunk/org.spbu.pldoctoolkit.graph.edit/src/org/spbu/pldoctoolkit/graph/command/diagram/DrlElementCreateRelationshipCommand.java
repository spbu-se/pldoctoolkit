/**
 * 
 */
package org.spbu.pldoctoolkit.graph.command.diagram;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.spbu.pldoctoolkit.graph.DrlElement;

/**
 * @author Alexey Semenov
 *
 * Copies CreateRelationshipCommand behaviour except for canExecute method:
 * local checks are extracted into a separate overridable method.
 */
public class DrlElementCreateRelationshipCommand extends
	DrlElementCreateCommand {

	/**
	 * The DrlElement create helper. 
	 */
//	private DrlElementCreateHelper helper;

	/**
	 * The relationship source.
	 */
	private final EObject source;

	/**
	 * The relationship target.
	 */
	private final EObject target;

	/**
	 * @param request
	 */
	public DrlElementCreateRelationshipCommand(CreateRelationshipRequest request) {
		super(request);
		
//		helper = new DrlElementCreateHelper();
		this.source = request.getSource();
		this.target = request.getTarget();	
	}

	/**
	 * Gets the relationship source.
	 * 
	 * @return the relationship source
	 */
	public EObject getSource() {
		return source;
	}

	/**
	 * Gets the relationship target.
	 * 
	 * @return the relationship target
	 */
	public EObject getTarget() {
		return target;
	}

	@Override
	protected ConfigureRequest createConfigureRequest() {

		ConfigureRequest result = super.createConfigureRequest();
		result.setParameter(CreateRelationshipRequest.SOURCE, getSource());
		result.setParameter(CreateRelationshipRequest.TARGET, getTarget());
		return result;
	}

	protected boolean doCanExecute() {
		return getSource() != null && getTarget() != null;
	}
	
	public boolean canExecute() {
		return 
			doCanExecute()
			&& super.canExecute();
	}

	
//	@Override
//	protected EObject doDefaultElementCreation() {
//		DrlElement newElement = (DrlElement) super.doDefaultElementCreation();
//
//		helper.doDefaultElementCreation(newElement);
//		
//		return newElement;
//	}
//
//	@Override
//	protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info)
//			throws ExecutionException {
//		IStatus result = super.doRedo(monitor, info);
//
//		helper.doRedo(monitor, info);
//		
//		return result;
//	}
//
//	@Override
//	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
//			throws ExecutionException {
//		helper.doUndo(monitor, info);
//		
//		return super.doUndo(monitor, info);
//	}
}
