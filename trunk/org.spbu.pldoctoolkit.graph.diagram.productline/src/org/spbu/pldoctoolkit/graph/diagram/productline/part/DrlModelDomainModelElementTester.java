package org.spbu.pldoctoolkit.graph.diagram.productline.part;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.spbu.pldoctoolkit.graph.DrlPackage;

/**
 * @generated
 */
public class DrlModelDomainModelElementTester extends PropertyTester {

	/**
	 * @generated
	 */
	public boolean test(Object receiver, String method, Object[] args,
			Object expectedValue) {
		if (false == receiver instanceof EObject) {
			return false;
		}
		EObject eObject = (EObject) receiver;
		EClass eClass = eObject.eClass();
		if (eClass == DrlPackage.eINSTANCE.getInfElement()) {
			return true;
		}
		if (eClass == DrlPackage.eINSTANCE.getInfProduct()) {
			return true;
		}
		if (eClass == DrlPackage.eINSTANCE.getFinalInfProduct()) {
			return true;
		}
		if (eClass == DrlPackage.eINSTANCE.getNestPoint()) {
			return true;
		}
		if (eClass == DrlPackage.eINSTANCE.getGenericDocumentPart()) {
			return true;
		}
		if (eClass == DrlPackage.eINSTANCE.getInfElemRef()) {
			return true;
		}
		if (eClass == DrlPackage.eINSTANCE.getInfElemRefGroup()) {
			return true;
		}
		if (eClass == DrlPackage.eINSTANCE.getProductLine()) {
			return true;
		}
		if (eClass == DrlPackage.eINSTANCE.getProduct()) {
			return true;
		}
		if (eClass == DrlPackage.eINSTANCE.getDocumentationCore()) {
			return true;
		}
		if (eClass == DrlPackage.eINSTANCE.getProductDocumentation()) {
			return true;
		}
		if (eClass == DrlPackage.eINSTANCE.getDrlElement()) {
			return true;
		}
		return false;
	}

}
