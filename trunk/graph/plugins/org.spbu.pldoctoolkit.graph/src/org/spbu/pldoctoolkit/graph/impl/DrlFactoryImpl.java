/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.spbu.pldoctoolkit.graph.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DrlFactoryImpl extends EFactoryImpl implements DrlFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DrlFactory init() {
		try {
			DrlFactory theDrlFactory = (DrlFactory)EPackage.Registry.INSTANCE.getEFactory("http://tepkom.ru/drl"); 
			if (theDrlFactory != null) {
				return theDrlFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DrlFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DrlFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DrlPackage.INF_ELEMENT: return createInfElement();
			case DrlPackage.INF_PRODUCT: return createInfProduct();
			case DrlPackage.FINAL_INF_PRODUCT: return createFinalInfProduct();
			case DrlPackage.ADAPTER: return createAdapter();
			case DrlPackage.NEST_POINT: return createNestPoint();
			case DrlPackage.CONDITIONAL: return createConditional();
			case DrlPackage.NEST_POINT_REF: return createNestPointRef();
			case DrlPackage.INF_ELEM_REF: return createInfElemRef();
			case DrlPackage.INF_ELEM_REF_GROUP: return createInfElemRefGroup();
			case DrlPackage.PRODUCT_LINE: return createProductLine();
			case DrlPackage.PL_SCHEME: return createPLScheme();
			case DrlPackage.PL_DOCUMENTATION: return createPLDocumentation();
			case DrlPackage.PRODUCT: return createProduct();
			case DrlPackage.DOCUMENTATION_CORE: return createDocumentationCore();
			case DrlPackage.PRODUCT_DOCUMENTATION: return createProductDocumentation();
			case DrlPackage.VARIABLE: return createVariable();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case DrlPackage.NEST_POINT_TYPE:
				return createNestPointTypeFromString(eDataType, initialValue);
			case DrlPackage.GROUP_TYPE:
				return createGroupTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case DrlPackage.NEST_POINT_TYPE:
				return convertNestPointTypeToString(eDataType, instanceValue);
			case DrlPackage.GROUP_TYPE:
				return convertGroupTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfElement createInfElement() {
		InfElementImpl infElement = new InfElementImpl();
		return infElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfProduct createInfProduct() {
		InfProductImpl infProduct = new InfProductImpl();
		return infProduct;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FinalInfProduct createFinalInfProduct() {
		FinalInfProductImpl finalInfProduct = new FinalInfProductImpl();
		return finalInfProduct;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Adapter createAdapter() {
		AdapterImpl adapter = new AdapterImpl();
		return adapter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestPoint createNestPoint() {
		NestPointImpl nestPoint = new NestPointImpl();
		return nestPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Conditional createConditional() {
		ConditionalImpl conditional = new ConditionalImpl();
		return conditional;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestPointRef createNestPointRef() {
		NestPointRefImpl nestPointRef = new NestPointRefImpl();
		return nestPointRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfElemRef createInfElemRef() {
		InfElemRefImpl infElemRef = new InfElemRefImpl();
		return infElemRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfElemRefGroup createInfElemRefGroup() {
		InfElemRefGroupImpl infElemRefGroup = new InfElemRefGroupImpl();
		return infElemRefGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProductLine createProductLine() {
		ProductLineImpl productLine = new ProductLineImpl();
		return productLine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PLScheme createPLScheme() {
		PLSchemeImpl plScheme = new PLSchemeImpl();
		return plScheme;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PLDocumentation createPLDocumentation() {
		PLDocumentationImpl plDocumentation = new PLDocumentationImpl();
		return plDocumentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Product createProduct() {
		ProductImpl product = new ProductImpl();
		return product;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentationCore createDocumentationCore() {
		DocumentationCoreImpl documentationCore = new DocumentationCoreImpl();
		return documentationCore;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProductDocumentation createProductDocumentation() {
		ProductDocumentationImpl productDocumentation = new ProductDocumentationImpl();
		return productDocumentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable createVariable() {
		VariableImpl variable = new VariableImpl();
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestPointType createNestPointTypeFromString(EDataType eDataType, String initialValue) {
		NestPointType result = NestPointType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNestPointTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroupType createGroupTypeFromString(EDataType eDataType, String initialValue) {
		GroupType result = GroupType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGroupTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DrlPackage getDrlPackage() {
		return (DrlPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static DrlPackage getPackage() {
		return DrlPackage.eINSTANCE;
	}

} //DrlFactoryImpl
