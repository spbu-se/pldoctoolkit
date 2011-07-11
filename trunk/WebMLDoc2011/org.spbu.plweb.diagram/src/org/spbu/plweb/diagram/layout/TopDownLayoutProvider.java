/**
 * No license restriction - you may apply any you like.
 *
 * Created 14.06.2007 4:17:56 using Eclipse IDE.
 *
 * $Id$
 */
package org.spbu.plweb.diagram.layout;

import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.providers.TopDownProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.ILayoutNodeOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutType;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @author Alexey Semenov
 *
 */
public class TopDownLayoutProvider extends TopDownProvider {

	private boolean working;
	
	/**
	 * Returns true when layout is arranging buildings.
	 */
	public final boolean isWorking() {
		return working;
	}
	
	public boolean provides(IOperation operation) {
		View cview = getContainer(operation);
		if (cview == null) {
			return false;
		}
		IAdaptable layoutHint = ((ILayoutNodeOperation) operation).getLayoutHint();
		String layoutType = (String) layoutHint.getAdapter(String.class);
		return LayoutType.DEFAULT.equals(layoutType);
	}

	@Override
	public Command layoutEditParts(GraphicalEditPart containerEditPart,
			IAdaptable layoutHint) {
		if (working) {
			throw new IllegalStateException("Recursive layout invocation"); //$NON-NLS-1$
		}
		
		Command result = null;
		try {
			working = true;
			
			result = super.layoutEditParts(containerEditPart, layoutHint);
		} finally {
			working = false;
		}
		
		return result == null? new Command("nothing to layout") {} : result;
	}

	@Override
	public Command layoutEditParts(List selectedObjects, IAdaptable layoutHint) {
		if (working) {
			throw new IllegalStateException("Recursive layout invocation"); //$NON-NLS-1$
		}
		
		Command result = null;
		try {
			working = true;
			
			result = super.layoutEditParts(selectedObjects, layoutHint);
		} finally {
			working = false;
		}
		
		return result == null? new Command("nothing to layout") {} : result;

	}	
	
	
	/**
	 * XXX A hack: fixes NPE when a connection has target unset.
	 */
	@Override
    protected List getRelevantConnections(Hashtable editPartToNodeDict) {
		Hashtable nonNullHashtable = new Hashtable(editPartToNodeDict) {
			@Override
			public synchronized Object get(Object key) {
				if(key == null) {
					return null;
				}
				
				return super.get(key);
			}
		};
		
		return super.getRelevantConnections(nonNullHashtable);
    }
	
}
