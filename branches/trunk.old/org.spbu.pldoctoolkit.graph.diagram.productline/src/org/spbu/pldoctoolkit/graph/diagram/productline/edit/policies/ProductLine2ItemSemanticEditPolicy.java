package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLine2EditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineDataEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineProductLineDataCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;

/**
 * @generated
 */
public class ProductLine2ItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * @generated
	 * 
	 * TODO  this was added in GMF 2.0.2
	 */
	protected void addDestroyChildNodesCommand(CompoundCommand cmd) {
		View view = (View) getHost().getModel();
		EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
		if (annotation != null) {
			return;
		}
		for (Iterator it = view.getChildren().iterator(); it.hasNext();) {
			Node node = (Node) it.next();
			switch (DrlModelVisualIDRegistry.getVisualID(node)) {
			case ProductLineProductLineDataCompartmentEditPart.VISUAL_ID:
				for (Iterator cit = node.getChildren().iterator(); cit
						.hasNext();) {
					Node cnode = (Node) cit.next();
					switch (DrlModelVisualIDRegistry.getVisualID(cnode)) {
					case ProductLineDataEditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					}
				}
				break;
			}
		}
	}

	@Override
	protected boolean shouldProceed(DestroyRequest destroyRequest) {
		return false;
	}
}
