/**
 * 
 */
package org.spbu.pldoctoolkit.graph.command.diagram;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.spbu.pldoctoolkit.graph.DrlElement;
import org.spbu.pldoctoolkit.graph.util.IdUtil;
import org.w3c.dom.Node;

/**
 * @author Alexey Semenov
 *
 */
public class DrlElementCreateHelper {
	
	private DrlElement newElement;

	public EObject doDefaultElementCreation(DrlElement newElement) {
		this.newElement = newElement;
		
		if (newElement != null) {
			newElement.initializeNode(newElement.eContainmentFeature());
			IdUtil.initializeId(newElement);
		}

		DrlElement container = (DrlElement) newElement.eContainer();
		container.getNode().appendChild(newElement.getNode());

		return newElement;
	}

	public void doRedo(IProgressMonitor monitor, IAdaptable info) {
		DrlElement container = (DrlElement) newElement.eContainer();
		container.getNode().appendChild(newElement.getNode());
	}

	public void doUndo(IProgressMonitor monitor, IAdaptable info) {
		Node elementNode = newElement.getNode();
		elementNode.getParentNode().removeChild(elementNode);
	}
}
