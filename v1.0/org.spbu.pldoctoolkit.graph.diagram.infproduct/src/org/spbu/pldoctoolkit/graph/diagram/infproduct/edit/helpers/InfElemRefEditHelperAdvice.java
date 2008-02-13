package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.helpers;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

/**
 * @generated
 */
public class InfElemRefEditHelperAdvice extends AbstractEditHelperAdvice {

	@Override
	protected ICommand getAfterCreateCommand(CreateElementRequest request) {
		System.out.println("after create command");
		return super.getAfterCreateCommand(request);
	}

}