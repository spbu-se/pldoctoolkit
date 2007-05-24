package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.command.diagram.DrlElementCreateCommand;

/**
 * @generated
 */
public class InfElemRefGroupCreateCommand extends DrlElementCreateCommand {

	/**
	 * @generated
	 */
	public InfElemRefGroupCreateCommand(CreateElementRequest req) {
		super(req);
	}

	protected EClass getEClassToEdit() {
		return DrlPackage.eINSTANCE.getGenericDocumentPart();
	}
	
	/**
	 * @generated
	 */
	protected EObject getElementToEdit() {
		EObject container = ((CreateElementRequest) getRequest())
				.getContainer();
		if (container instanceof View) {
			container = ((View) container).getElement();
		}
		return container;
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		if (getEClass() != null) {
			return getEClass().isSuperTypeOf(getEClassToEdit());
		}
		return true;
	}

	/**
	 * @generated
	 */
//	protected EReference getContainmentFeature() {
//		return null;
//	}

	/**
	 * @generated NOT
	 */
//	protected EObject doDefaultElementCreation() {
//		// Uncomment to put "phantom" objects into the diagram file.		
//		//Resource resource = ((CreateElementRequest) getRequest()).getContainer().eResource();
//		//if (resource == null) {
//		//	return null;
//		//}
//		Resource resource = getElementToEdit().eResource();
//		EClass eClass = getElementType().getEClass();
//		DrlElement drlObject = (DrlElement) eClass.getEPackage().getEFactoryInstance().create(
//				eClass);
//		resource.getContents().add(drlObject);
//		
//		helper.doDefaultElementCreation(drlObject);
//		
//		return drlObject;
//	}

}
