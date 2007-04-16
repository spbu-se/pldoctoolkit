/**
 * 
 */
package org.spbu.pldoctoolkit.graph.command;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.spbu.pldoctoolkit.graph.DrlElement;

/**
 * @author Alexey Semenov
 *
 */
public class SetCommandWrapper extends SetCommand {

	private boolean isDrlEditingEnabled = false;
	
	public SetCommandWrapper(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Object value) {
		super(domain, owner, feature, value);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.command.SetCommand#prepare()
	 */
	@Override
	protected boolean prepare() {
		boolean result = super.prepare();
		
		/*
		 * owner
		 * ownerList
		 * index
		 * oldValue
		 * value
		 */
		
		if( owner == null || owner instanceof DrlElement) {
			isDrlEditingEnabled = true;
		}
		
		return result;
	}



	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.command.SetCommand#doExecute()
	 */
	@Override
	public void doExecute() {
		super.doExecute();
		
	    // Either set or unset the feature.
		//
		if (ownerList != null) {
//			ownerList.set(index, value);
//			Node ownerNode = owner
		} else if (value == UNSET_VALUE) {
			owner.eUnset(feature);
		} else {
			owner.eSet(feature, value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.edit.command.SetCommand#doRedo()
	 */
	@Override
	public void doRedo() {
		// TODO Auto-generated method stub
		super.doRedo();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.command.SetCommand#doUndo()
	 */
	@Override
	public void doUndo() {
		// TODO Auto-generated method stub
		super.doUndo();
	}

	
	
}
