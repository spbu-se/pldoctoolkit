/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.drlvisual.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.spbu.pldoctoolkit.drlvisual.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.spbu.pldoctoolkit.drlvisual.DrlPackage
 * @generated
 */
public class DrlSwitch {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DrlPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DrlSwitch() {
		if (modelPackage == null) {
			modelPackage = DrlPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public Object doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch((EClass)eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case DrlPackage.DRL_ELEMENT: {
				DrlElement drlElement = (DrlElement)theEObject;
				Object result = caseDrlElement(drlElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.INF_ELEMENT: {
				InfElement infElement = (InfElement)theEObject;
				Object result = caseInfElement(infElement);
				if (result == null) result = caseGenericDocumentPart(infElement);
				if (result == null) result = caseDrlElement(infElement);
				if (result == null) result = caseSubelementedElement(infElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.INF_PRODUCT: {
				InfProduct infProduct = (InfProduct)theEObject;
				Object result = caseInfProduct(infProduct);
				if (result == null) result = caseGenericDocumentPart(infProduct);
				if (result == null) result = caseDrlElement(infProduct);
				if (result == null) result = caseSubelementedElement(infProduct);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.FINAL_INF_PRODUCT: {
				FinalInfProduct finalInfProduct = (FinalInfProduct)theEObject;
				Object result = caseFinalInfProduct(finalInfProduct);
				if (result == null) result = caseDrlElement(finalInfProduct);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.ADAPTER: {
				Adapter adapter = (Adapter)theEObject;
				Object result = caseAdapter(adapter);
				if (result == null) result = caseDrlElement(adapter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.NEST_POINT: {
				NestPoint nestPoint = (NestPoint)theEObject;
				Object result = caseNestPoint(nestPoint);
				if (result == null) result = caseInnerElement(nestPoint);
				if (result == null) result = caseSubelementedElement(nestPoint);
				if (result == null) result = caseDrlElement(nestPoint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.INNER_ELEMENT: {
				InnerElement innerElement = (InnerElement)theEObject;
				Object result = caseInnerElement(innerElement);
				if (result == null) result = caseDrlElement(innerElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.CONDITIONAL: {
				Conditional conditional = (Conditional)theEObject;
				Object result = caseConditional(conditional);
				if (result == null) result = caseInnerElement(conditional);
				if (result == null) result = caseSubelementedElement(conditional);
				if (result == null) result = caseDrlElement(conditional);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.NEST_POINT_REF: {
				NestPointRef nestPointRef = (NestPointRef)theEObject;
				Object result = caseNestPointRef(nestPointRef);
				if (result == null) result = caseDrlElement(nestPointRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.GENERIC_DOCUMENT_PART: {
				GenericDocumentPart genericDocumentPart = (GenericDocumentPart)theEObject;
				Object result = caseGenericDocumentPart(genericDocumentPart);
				if (result == null) result = caseDrlElement(genericDocumentPart);
				if (result == null) result = caseSubelementedElement(genericDocumentPart);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.INF_ELEM_REF: {
				InfElemRef infElemRef = (InfElemRef)theEObject;
				Object result = caseInfElemRef(infElemRef);
				if (result == null) result = caseInnerElement(infElemRef);
				if (result == null) result = caseDrlElement(infElemRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.INF_ELEM_REF_GROUP: {
				InfElemRefGroup infElemRefGroup = (InfElemRefGroup)theEObject;
				Object result = caseInfElemRefGroup(infElemRefGroup);
				if (result == null) result = caseInnerElement(infElemRefGroup);
				if (result == null) result = caseDrlElement(infElemRefGroup);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.SUBELEMENTED_ELEMENT: {
				SubelementedElement subelementedElement = (SubelementedElement)theEObject;
				Object result = caseSubelementedElement(subelementedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.PRODUCT_LINE: {
				ProductLine productLine = (ProductLine)theEObject;
				Object result = caseProductLine(productLine);
				if (result == null) result = caseDrlElement(productLine);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.PL_SCHEME: {
				PLScheme plScheme = (PLScheme)theEObject;
				Object result = casePLScheme(plScheme);
				if (result == null) result = caseDrlElement(plScheme);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.PL_DOCUMENTATION: {
				PLDocumentation plDocumentation = (PLDocumentation)theEObject;
				Object result = casePLDocumentation(plDocumentation);
				if (result == null) result = caseDrlElement(plDocumentation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.PRODUCT: {
				Product product = (Product)theEObject;
				Object result = caseProduct(product);
				if (result == null) result = caseDrlElement(product);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.DOCUMENTATION_CORE: {
				DocumentationCore documentationCore = (DocumentationCore)theEObject;
				Object result = caseDocumentationCore(documentationCore);
				if (result == null) result = caseDrlElement(documentationCore);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.PRODUCT_DOCUMENTATION: {
				ProductDocumentation productDocumentation = (ProductDocumentation)theEObject;
				Object result = caseProductDocumentation(productDocumentation);
				if (result == null) result = caseDrlElement(productDocumentation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DrlPackage.VARIABLE: {
				Variable variable = (Variable)theEObject;
				Object result = caseVariable(variable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDrlElement(DrlElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Inf Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Inf Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInfElement(InfElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Inf Product</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Inf Product</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInfProduct(InfProduct object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Final Inf Product</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Final Inf Product</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseFinalInfProduct(FinalInfProduct object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Adapter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Adapter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAdapter(Adapter object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Nest Point</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Nest Point</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseNestPoint(NestPoint object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Inner Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Inner Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInnerElement(InnerElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Conditional</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Conditional</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConditional(Conditional object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Nest Point Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Nest Point Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseNestPointRef(NestPointRef object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Generic Document Part</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Generic Document Part</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseGenericDocumentPart(GenericDocumentPart object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Inf Elem Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Inf Elem Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInfElemRef(InfElemRef object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Inf Elem Ref Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Inf Elem Ref Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInfElemRefGroup(InfElemRefGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Subelemented Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Subelemented Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSubelementedElement(SubelementedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Product Line</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Product Line</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProductLine(ProductLine object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>PL Scheme</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>PL Scheme</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePLScheme(PLScheme object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>PL Documentation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>PL Documentation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePLDocumentation(PLDocumentation object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Product</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Product</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProduct(Product object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Documentation Core</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Documentation Core</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDocumentationCore(DocumentationCore object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Product Documentation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Product Documentation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProductDocumentation(ProductDocumentation object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseVariable(Variable object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public Object defaultCase(EObject object) {
		return null;
	}

} //DrlSwitch
