/**
 * 
 */
package org.spbu.pldoctoolkit.graph.command;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.spbu.pldoctoolkit.graph.DrlElement;
import org.spbu.pldoctoolkit.graph.DrlGraphPlugin;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Alexey Semenov
 *
 */
public class RemoveCommandWrapper extends RemoveCommand {

	private Element drlOwnerNode;
	private Node[] rightNeighbours;

	/**
	 * @param domain
	 * @param list
	 * @param value
	 */
	public RemoveCommandWrapper(EditingDomain domain, EList<?> list,
			Object value) {
		super(domain, list, value);
	}

	/**
	 * @param domain
	 * @param list
	 * @param collection
	 */
	public RemoveCommandWrapper(EditingDomain domain, EList<?> list,
			Collection<?> collection) {
		super(domain, list, collection);
	}

	/**
	 * @param domain
	 * @param owner
	 * @param feature
	 * @param value
	 */
	public RemoveCommandWrapper(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Object value) {
		super(domain, owner, feature, value);
	}

	/**
	 * @param domain
	 * @param owner
	 * @param feature
	 * @param collection
	 */
	public RemoveCommandWrapper(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection<?> collection) {
		super(domain, owner, feature, collection);
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.command.RemoveCommand#doExecute()
	 */
	@Override
	public void doExecute() {
		DrlGraphPlugin.logInfo("remove command wrapper");

		// call parent remove
		super.doExecute();

		if(!(owner instanceof DrlElement)) {
			DrlGraphPlugin.logInfo("not drl at RemoveCommandWrapper.doExecute()");
			return;
		}
		
		// set up fields
		DrlElement drlOwner = (DrlElement) owner;
		drlOwnerNode = drlOwner.getNode();
		
		rightNeighbours = new Node[collection.size()];
		int neighbourIndex = 0;
		
		for(Object obj : collection) {
			DrlElement drlObj = (DrlElement) obj;
			rightNeighbours[neighbourIndex++] = drlObj.getNode().getNextSibling();
		}	
		
		// actually remove the nodes in the tree
		doRemoveNodes();
	}

	private void doRemoveNodes() {
		for(Object obj : collection) {
			DrlElement drlObj = (DrlElement) obj;
			drlOwnerNode.removeChild(drlObj.getNode());
		}	
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.command.RemoveCommand#doRedo()
	 */
	@Override
	public void doRedo() {
		super.doRedo();
		
		doRemoveNodes();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.command.RemoveCommand#doUndo()
	 */
	@Override
	public void doUndo() {
		super.doUndo();
		
		int i = 0;
	    for (Object obj : collection) {
	      DrlElement drlObj = (DrlElement) obj; 
	      drlOwnerNode.insertBefore(drlObj.getNode(), rightNeighbours[i++]);
	    }
	}

}
