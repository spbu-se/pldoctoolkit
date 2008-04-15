package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;

/**
 * Shows source and target connections on host edit part's selection
 * and hides on unselection. Thus, a connection is shown only if it relates
 * to the selected edit part.<p>
 * 
 * @author Alexey
 */
public class HideConnectionsSelectionEditPolicy extends
		SelectionEditPolicy {

	@Override
	protected void hideSelection() {
		for (ConnectionEditPart con : getConnections()) {
			con.getFigure().setVisible(false);
		}
	}


	@Override
	protected void showSelection() {
		for (ConnectionEditPart con : getConnections()) {
			con.getFigure().setVisible(true);
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<ConnectionEditPart> getConnections() {
		GraphicalEditPart gep = (GraphicalEditPart) getHost();
		
		List<ConnectionEditPart> connections = new ArrayList<ConnectionEditPart>(gep.getSourceConnections());
		connections.addAll(gep.getTargetConnections());

		return connections;
	}
}