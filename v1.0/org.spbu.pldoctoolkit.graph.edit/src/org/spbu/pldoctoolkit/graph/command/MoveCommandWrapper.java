/**
 * 
 */
package org.spbu.pldoctoolkit.graph.command;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.spbu.pldoctoolkit.graph.DrlElement;
import org.spbu.pldoctoolkit.graph.DrlGraphPlugin;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Alexey Semenov
 *
 */
public class MoveCommandWrapper extends MoveCommand {

	protected int oldNodeIndex;
	private Node oldRightNeighbourNode;
	private Node itemAtPositionNode;
	private Element drlOwnerNode;
	private Element drlValueNode;
	
	/**
	 * @param domain
	 * @param list
	 * @param value
	 * @param index
	 */
	public MoveCommandWrapper(EditingDomain domain, EList<?> list,
			Object value, int index) {
		super(domain, list, value, index);
	}

	/**
	 * @param domain
	 * @param owner
	 * @param feature
	 * @param value
	 * @param index
	 */
	public MoveCommandWrapper(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Object value, int index) {
		super(domain, owner, feature, value, index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.command.MoveCommand#doExecute()
	 */
	@Override
	public void doExecute() {
		DrlGraphPlugin.logInfo("move command wrapper");
		
		if(!(owner instanceof DrlElement) || !(value instanceof DrlElement)) {
			DrlGraphPlugin.logInfo("not drl at MoveCommandWrapper.doExecute()");
			super.doExecute();
			return;
		}
	
		DrlElement drlOwner = (DrlElement) owner;
		drlOwnerNode = drlOwner.getNode();

		DrlElement drlValue = (DrlElement) value;
		drlValueNode = drlValue.getNode();

		oldRightNeighbourNode = drlValueNode.getNextSibling();

		DrlElement itemAtPosition = (DrlElement) ownerList.get(index);
		itemAtPositionNode = itemAtPosition.getNode();
		if(ownerList.indexOf(value) < index) {
			itemAtPositionNode = itemAtPositionNode.getNextSibling();
		}
		
		super.doExecute();
		
		drlOwnerNode.insertBefore(drlValueNode, itemAtPositionNode);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.command.MoveCommand#doRedo()
	 */
	@Override
	public void doRedo() {
		super.doRedo();
		
		drlOwnerNode.insertBefore(drlValueNode, itemAtPositionNode);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.command.MoveCommand#doUndo()
	 */
	@Override
	public void doUndo() {
		super.doUndo();
		
		drlOwnerNode.insertBefore(drlValueNode, oldRightNeighbourNode);
	}

}
