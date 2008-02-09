/**
 * 
 */
package org.spbu.pldoctoolkit.graph.command.diagram;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

/**
 * @author Alexey Semenov
 *
 * Copies CreateRelationshipCommand behaviour except for canExecute method:
 * local checks are extracted into a separate overridable method.
 */
public class DrlElementCreateRelationshipCommand extends
	DrlElementCreateCommand {

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

}
