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
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLine2EditPart;

/**
 * @generated
 */
public class ProductLine2ItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return UnexecutableCommand.INSTANCE;
	}

	@Override
	protected boolean shouldProceed(DestroyRequest destroyRequest) {
		return false;
	}
}
