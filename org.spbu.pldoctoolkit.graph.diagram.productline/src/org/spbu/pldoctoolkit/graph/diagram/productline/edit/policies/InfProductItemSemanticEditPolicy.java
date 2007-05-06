package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductEditPart;

/**
 * @generated
 */
public class InfProductItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
//		CompoundCommand cc = new CompoundCommand();
//		Collection allEdges = new ArrayList();
//		View view = (View) getHost().getModel();
//		allEdges.addAll(view.getSourceEdges());
//		allEdges.addAll(view.getTargetEdges());
//		for (Iterator it = allEdges.iterator(); it.hasNext();) {
//			Edge nextEdge = (Edge) it.next();
//			EditPart nextEditPart = (EditPart) getHost().getViewer()
//					.getEditPartRegistry().get(nextEdge);
//			EditCommandRequestWrapper editCommandRequest = new EditCommandRequestWrapper(
//					new DestroyElementRequest(((InfProductEditPart) getHost())
//							.getEditingDomain(), req.isConfirmationRequired()),
//					Collections.EMPTY_MAP);
//			cc.add(nextEditPart.getCommand(editCommandRequest));
//		}
//		cc.add(getMSLWrapper(new DestroyElementCommand(req)));
//		return cc;
		
		return UnexecutableCommand.INSTANCE;
	}
	
	
}
