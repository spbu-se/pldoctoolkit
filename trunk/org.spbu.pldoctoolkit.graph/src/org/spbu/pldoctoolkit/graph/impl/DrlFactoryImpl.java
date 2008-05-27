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
import org.spbu.pldoctoolkit.graph.DocumentationCore;
import org.spbu.pldoctoolkit.graph.DrlFactory;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.FinalInfProduct;
import org.spbu.pldoctoolkit.graph.GroupType;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElemRefGroup;
import org.spbu.pldoctoolkit.graph.InfElement;
import org.spbu.pldoctoolkit.graph.InfProduct;
import org.spbu.pldoctoolkit.graph.NestPoint;
import org.spbu.pldoctoolkit.graph.Product;
import org.spbu.pldoctoolkit.graph.ProductDocumentation;
import org.spbu.pldoctoolkit.graph.ProductLine;
import org.w3c.dom.Element;

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
			DrlFactory theDrlFactory = (DrlFactory)EPackage.Registry.INSTANCE.getEFactory("http://math.spbu.ru/drl"); 
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
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DrlPackage.INF_ELEMENT: return createInfElement();
			case DrlPackage.INF_PRODUCT: return createInfProduct();
			case DrlPackage.FINAL_INF_PRODUCT: return createFinalInfProduct();
			case DrlPackage.NEST_POINT: return createNestPoint();
			case DrlPackage.INF_ELEM_REF: return createInfElemRef();
			case DrlPackage.INF_ELEM_REF_GROUP: return createInfElemRefGroup();
			case DrlPackage.PRODUCT_LINE: return createProductLine();
			case DrlPackage.PRODUCT: return createProduct();
			case DrlPackage.DOCUMENTATION_CORE: return createDocumentationCore();
			case DrlPackage.PRODUCT_DOCUMENTATION: return createProductDocumentation();
			case DrlPackage.ADAPTER: return createAdapter();
			case DrlPackage.NEST: return createNest();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case DrlPackage.GROUP_TYPE:
				return createGroupTypeFromString(eDataType, initialValue);
			case DrlPackage.NEST_TYPE:
				return createNestTypeFromString(eDataType, initialValue);
			case DrlPackage.NODE_TYPE:
				return createNodeTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case DrlPackage.GROUP_TYPE:
				return convertGroupTypeToString(eDataType, instanceValue);
			case DrlPackage.NEST_TYPE:
				return convertNestTypeToString(eDataType, instanceValue);
			case DrlPackage.NODE_TYPE:
				return convertNodeTypeToString(eDataType, instanceValue);
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
	public NestPoint createNestPoint() {
		NestPointImpl nestPoint = new NestPointImpl();
		return nestPoint;
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
	public Adapter createAdapter() {
		AdapterImpl adapter = new AdapterImpl();
		return adapter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Nest createNest() {
		NestImpl nest = new NestImpl();
		return nest;
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
	public NestType createNestTypeFromString(EDataType eDataType, String initialValue) {
		NestType result = NestType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNestTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element createNodeTypeFromString(EDataType eDataType, String initialValue) {
		return (Element)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNodeTypeToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
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
	@Deprecated
	public static DrlPackage getPackage() {
		return DrlPackage.eINSTANCE;
	}

} //DrlFactoryImpl
