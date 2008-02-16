package org.spbu.pldoctoolkit.graph.diagram.productline.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.command.diagram.DrlElementCreateCommand;

/**
 * @generated
 */
public class InfProductCreateCommand extends DrlElementCreateCommand {

	/**
	 * @generated
	 */
	public InfProductCreateCommand(CreateElementRequest req) {
		super(req);
	}

	/**
	 * @generated
	 */
	protected EClass getEClassToEdit() {
		return DrlPackage.eINSTANCE.getDocumentationCore();
	}

	/**
	 * @generated NOT
	 */
	protected EObject getElementToEdit() {
		//XXX a hack - check the diagram structure
//		EObject container = ((CreateElementRequest) getRequest())
//				.getContainer();
		
		EObject container = (EObject) ((CreateElementRequest) getRequest())
				.getParameter("container");
		if (container instanceof View) {
			container = ((View) container).getElement();
		}
		return container;
	}

}
