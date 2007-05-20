/**
 * 
 */
package org.spbu.pldoctoolkit.graph.command;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.spbu.pldoctoolkit.graph.DrlElement;
import org.spbu.pldoctoolkit.graph.DrlGraphPlugin;
import org.w3c.dom.Element;

/**
 * @author Alexey Semenov
 *
 */
public class AddCommandWrapper extends AddCommand {

	/**
	 * @param domain
	 * @param list
	 * @param value
	 */
	public AddCommandWrapper(EditingDomain domain, EList<?> list, Object value) {
		super(domain, list, value);
	}

	/**
	 * @param domain
	 * @param list
	 * @param collection
	 */
	public AddCommandWrapper(EditingDomain domain, EList<?> list,
			Collection<?> collection) {
		super(domain, list, collection);
	}

	/**
	 * @param domain
	 * @param owner
	 * @param feature
	 * @param value
	 */
	public AddCommandWrapper(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Object value) {
		super(domain, owner, feature, value);
	}

	/**
	 * @param domain
	 * @param owner
	 * @param feature
	 * @param collection
	 */
	public AddCommandWrapper(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection<?> collection) {
		super(domain, owner, feature, collection);
	}

	/**
	 * @param domain
	 * @param list
	 * @param value
	 * @param index
	 */
	public AddCommandWrapper(EditingDomain domain, EList<?> list, Object value,
			int index) {
		super(domain, list, value, index);
	}

	/**
	 * @param domain
	 * @param list
	 * @param collection
	 * @param index
	 */
	public AddCommandWrapper(EditingDomain domain, EList<?> list,
			Collection<?> collection, int index) {
		super(domain, list, collection, index);
	}

	/**
	 * @param domain
	 * @param owner
	 * @param feature
	 * @param value
	 * @param index
	 */
	public AddCommandWrapper(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Object value, int index) {
		super(domain, owner, feature, value, index);
	}

	/**
	 * @param domain
	 * @param owner
	 * @param feature
	 * @param collection
	 * @param index
	 */
	public AddCommandWrapper(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection<?> collection, int index) {
		super(domain, owner, feature, collection, index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.command.AddCommand#doExecute()
	 */
	@Override
	public void doExecute() {
		DrlGraphPlugin.logInfo("add command wrapper");
		
		if(!(owner instanceof DrlElement)) {
			DrlGraphPlugin.logInfo("not a drl at AddCommandWrapper.doExecute()");
			super.doExecute();
			return;
		}
		
		boolean containment = feature instanceof EReference && ((EReference)feature).isContainment();
		if(!containment) {
			DrlGraphPlugin.logInfo("not a containment reference, skipping");
			super.doExecute();
			return;
		}
		
		DrlElement drlOwner = (DrlElement) owner;
		Element drlOwnerNode = drlOwner.getNode();
		
		DrlElement itemAtPosition = null;
		Element itemAtPositionNode = null;
		if (index != CommandParameter.NO_INDEX) {
			if(ownerList.size() > index) {
				itemAtPosition = (DrlElement) ownerList.get(index);
				itemAtPositionNode = itemAtPosition.getNode();
			}
		}

		super.doExecute();
		
		for(Object item : collection) {
			if(!(item instanceof DrlElement)) {
				continue;
			}
			
			DrlElement drlItem = (DrlElement) item;
			if(drlItem.getNode() == null) {
				drlItem.initializeNode(feature);
			}
			
			drlOwnerNode.insertBefore(drlItem.getNode(), itemAtPositionNode);
		}
	}

	/**
	 * We do not save any state-specific information, so doRedo is equivalent to doExecute.<p>
	 * 
	 * This method simply calls <code>doExecute</code>
	 * 
	 * @see org.eclipse.emf.edit.command.AddCommand#doRedo()
	 */
	@Override
	public void doRedo() {
		this.doExecute();
	}

	/**
	 * @see org.eclipse.emf.edit.command.AddCommand#doUndo()
	 */
	@Override
	public void doUndo() {
		super.doUndo();
		
		if(!(owner instanceof DrlElement)) {
			DrlGraphPlugin.logInfo("not a drl at AddCommandWrapper.doUndo()");
			return;
		}

		DrlElement drlOwner = (DrlElement) owner;
		Element drlOwnerNode = drlOwner.getNode();
		
		for(Object obj : collection) {
	    	DrlElement drlObj = (DrlElement) obj;
	    	drlOwnerNode.removeChild(drlObj.getNode());
	    }
	}

}
