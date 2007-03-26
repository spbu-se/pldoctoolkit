/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.drlvisual.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.spbu.pldoctoolkit.drlvisual.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.spbu.pldoctoolkit.drlvisual.DrlPackage
 * @generated
 */
public class DrlAdapterFactory extends AdapterFactoryImpl {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DrlPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DrlAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DrlPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch the delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DrlSwitch modelSwitch =
		new DrlSwitch() {
			public Object caseDrlElement(DrlElement object) {
				return createDrlElementAdapter();
			}
			public Object caseInfElement(InfElement object) {
				return createInfElementAdapter();
			}
			public Object caseInfProduct(InfProduct object) {
				return createInfProductAdapter();
			}
			public Object caseFinalInfProduct(FinalInfProduct object) {
				return createFinalInfProductAdapter();
			}
			public Object caseAdapter(org.spbu.pldoctoolkit.drlvisual.Adapter object) {
				return createAdapterAdapter();
			}
			public Object caseNestPoint(NestPoint object) {
				return createNestPointAdapter();
			}
			public Object caseInnerElement(InnerElement object) {
				return createInnerElementAdapter();
			}
			public Object caseConditional(Conditional object) {
				return createConditionalAdapter();
			}
			public Object caseNestPointRef(NestPointRef object) {
				return createNestPointRefAdapter();
			}
			public Object caseGenericDocumentPart(GenericDocumentPart object) {
				return createGenericDocumentPartAdapter();
			}
			public Object caseInfElemRef(InfElemRef object) {
				return createInfElemRefAdapter();
			}
			public Object caseInfElemRefGroup(InfElemRefGroup object) {
				return createInfElemRefGroupAdapter();
			}
			public Object caseSubelementedElement(SubelementedElement object) {
				return createSubelementedElementAdapter();
			}
			public Object caseProductLine(ProductLine object) {
				return createProductLineAdapter();
			}
			public Object casePLScheme(PLScheme object) {
				return createPLSchemeAdapter();
			}
			public Object casePLDocumentation(PLDocumentation object) {
				return createPLDocumentationAdapter();
			}
			public Object caseProduct(Product object) {
				return createProductAdapter();
			}
			public Object caseDocumentationCore(DocumentationCore object) {
				return createDocumentationCoreAdapter();
			}
			public Object caseProductDocumentation(ProductDocumentation object) {
				return createProductDocumentationAdapter();
			}
			public Object caseVariable(Variable object) {
				return createVariableAdapter();
			}
			public Object defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	public Adapter createAdapter(Notifier target) {
		return (Adapter)modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.DrlElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.DrlElement
	 * @generated
	 */
	public Adapter createDrlElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.InfElement <em>Inf Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.InfElement
	 * @generated
	 */
	public Adapter createInfElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.InfProduct <em>Inf Product</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.InfProduct
	 * @generated
	 */
	public Adapter createInfProductAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.FinalInfProduct <em>Final Inf Product</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.FinalInfProduct
	 * @generated
	 */
	public Adapter createFinalInfProductAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.Adapter <em>Adapter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.Adapter
	 * @generated
	 */
	public Adapter createAdapterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.NestPoint <em>Nest Point</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.NestPoint
	 * @generated
	 */
	public Adapter createNestPointAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.InnerElement <em>Inner Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.InnerElement
	 * @generated
	 */
	public Adapter createInnerElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.Conditional <em>Conditional</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.Conditional
	 * @generated
	 */
	public Adapter createConditionalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.NestPointRef <em>Nest Point Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.NestPointRef
	 * @generated
	 */
	public Adapter createNestPointRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.GenericDocumentPart <em>Generic Document Part</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.GenericDocumentPart
	 * @generated
	 */
	public Adapter createGenericDocumentPartAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.InfElemRef <em>Inf Elem Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.InfElemRef
	 * @generated
	 */
	public Adapter createInfElemRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.InfElemRefGroup <em>Inf Elem Ref Group</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.InfElemRefGroup
	 * @generated
	 */
	public Adapter createInfElemRefGroupAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.SubelementedElement <em>Subelemented Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.SubelementedElement
	 * @generated
	 */
	public Adapter createSubelementedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.ProductLine <em>Product Line</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.ProductLine
	 * @generated
	 */
	public Adapter createProductLineAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.PLScheme <em>PL Scheme</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.PLScheme
	 * @generated
	 */
	public Adapter createPLSchemeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.PLDocumentation <em>PL Documentation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.PLDocumentation
	 * @generated
	 */
	public Adapter createPLDocumentationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.Product <em>Product</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.Product
	 * @generated
	 */
	public Adapter createProductAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.DocumentationCore <em>Documentation Core</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.DocumentationCore
	 * @generated
	 */
	public Adapter createDocumentationCoreAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.ProductDocumentation <em>Product Documentation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.ProductDocumentation
	 * @generated
	 */
	public Adapter createProductDocumentationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.spbu.pldoctoolkit.drlvisual.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.spbu.pldoctoolkit.drlvisual.Variable
	 * @generated
	 */
	public Adapter createVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //DrlAdapterFactory
