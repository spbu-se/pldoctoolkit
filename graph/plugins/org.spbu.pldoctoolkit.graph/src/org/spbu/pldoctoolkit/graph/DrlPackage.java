/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.spbu.pldoctoolkit.graph.DrlFactory
 * @model kind="package"
 * @generated
 */
public interface DrlPackage extends EPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "graph";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tepkom.ru/drl";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "v";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DrlPackage eINSTANCE = org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.DrlElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlElementImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getDrlElement()
	 * @generated
	 */
	int DRL_ELEMENT = 0;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRL_ELEMENT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.GenericDocumentPartImpl <em>Generic Document Part</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.GenericDocumentPartImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getGenericDocumentPart()
	 * @generated
	 */
	int GENERIC_DOCUMENT_PART = 9;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DOCUMENT_PART__ELEMENTS = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DOCUMENT_PART__ID = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DOCUMENT_PART__NAME = DRL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Generic Document Part</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DOCUMENT_PART_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.InfElementImpl <em>Inf Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.InfElementImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getInfElement()
	 * @generated
	 */
	int INF_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEMENT__ELEMENTS = GENERIC_DOCUMENT_PART__ELEMENTS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEMENT__ID = GENERIC_DOCUMENT_PART__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEMENT__NAME = GENERIC_DOCUMENT_PART__NAME;

	/**
	 * The number of structural features of the '<em>Inf Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEMENT_FEATURE_COUNT = GENERIC_DOCUMENT_PART_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.InfProductImpl <em>Inf Product</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.InfProductImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getInfProduct()
	 * @generated
	 */
	int INF_PRODUCT = 2;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_PRODUCT__ELEMENTS = GENERIC_DOCUMENT_PART__ELEMENTS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_PRODUCT__ID = GENERIC_DOCUMENT_PART__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_PRODUCT__NAME = GENERIC_DOCUMENT_PART__NAME;

	/**
	 * The number of structural features of the '<em>Inf Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_PRODUCT_FEATURE_COUNT = GENERIC_DOCUMENT_PART_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.FinalInfProductImpl <em>Final Inf Product</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.FinalInfProductImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getFinalInfProduct()
	 * @generated
	 */
	int FINAL_INF_PRODUCT = 3;

	/**
	 * The feature id for the '<em><b>Adapters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_INF_PRODUCT__ADAPTERS = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Product</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_INF_PRODUCT__PRODUCT = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_INF_PRODUCT__ID = DRL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_INF_PRODUCT__VARIABLES = DRL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Final Inf Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_INF_PRODUCT_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.AdapterImpl <em>Adapter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.AdapterImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getAdapter()
	 * @generated
	 */
	int ADAPTER = 4;

	/**
	 * The feature id for the '<em><b>Nest Point Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER__NEST_POINT_REFS = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Inf Elem Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER__INF_ELEM_REF = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Adapter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.InnerElementImpl <em>Inner Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.InnerElementImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getInnerElement()
	 * @generated
	 */
	int INNER_ELEMENT = 6;

	/**
	 * The number of structural features of the '<em>Inner Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INNER_ELEMENT_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.NestPointImpl <em>Nest Point</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.NestPointImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getNestPoint()
	 * @generated
	 */
	int NEST_POINT = 5;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEST_POINT__ELEMENTS = INNER_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEST_POINT__ID = INNER_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Descr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEST_POINT__DESCR = INNER_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Nest Point</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEST_POINT_FEATURE_COUNT = INNER_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.ConditionalImpl <em>Conditional</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.ConditionalImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getConditional()
	 * @generated
	 */
	int CONDITIONAL = 7;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL__ELEMENTS = INNER_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL__CONDITION = INNER_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Conditional</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_FEATURE_COUNT = INNER_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.NestPointRefImpl <em>Nest Point Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.NestPointRefImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getNestPointRef()
	 * @generated
	 */
	int NEST_POINT_REF = 8;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEST_POINT_REF__TYPE = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEST_POINT_REF__TEXT = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Nest Point</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEST_POINT_REF__NEST_POINT = DRL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Nest Point Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEST_POINT_REF_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.InfElemRefImpl <em>Inf Elem Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.InfElemRefImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getInfElemRef()
	 * @generated
	 */
	int INF_ELEM_REF = 10;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF__ID = INNER_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Infelem</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF__INFELEM = INNER_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF__GROUP = INNER_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF__OPTIONAL = INNER_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Inf Elem Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF_FEATURE_COUNT = INNER_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.InfElemRefGroupImpl <em>Inf Elem Ref Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.InfElemRefGroupImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getInfElemRefGroup()
	 * @generated
	 */
	int INF_ELEM_REF_GROUP = 11;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF_GROUP__ID = INNER_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Modifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF_GROUP__MODIFIER = INNER_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF_GROUP__NAME = INNER_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Inf Elem Ref Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF_GROUP_FEATURE_COUNT = INNER_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.SubelementedElementImpl <em>Subelemented Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.SubelementedElementImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getSubelementedElement()
	 * @generated
	 */
	int SUBELEMENTED_ELEMENT = 12;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBELEMENTED_ELEMENT__ELEMENTS = 0;

	/**
	 * The number of structural features of the '<em>Subelemented Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBELEMENTED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.ProductLineImpl <em>Product Line</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.ProductLineImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getProductLine()
	 * @generated
	 */
	int PRODUCT_LINE = 13;

	/**
	 * The feature id for the '<em><b>Scheme</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_LINE__SCHEME = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_LINE__DOCUMENTATION = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_LINE__NAME = DRL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Product Line</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_LINE_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.PLSchemeImpl <em>PL Scheme</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.PLSchemeImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getPLScheme()
	 * @generated
	 */
	int PL_SCHEME = 14;

	/**
	 * The feature id for the '<em><b>Products</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PL_SCHEME__PRODUCTS = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>PL Scheme</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PL_SCHEME_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.PLDocumentationImpl <em>PL Documentation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.PLDocumentationImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getPLDocumentation()
	 * @generated
	 */
	int PL_DOCUMENTATION = 15;

	/**
	 * The feature id for the '<em><b>Documentation Core</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PL_DOCUMENTATION__DOCUMENTATION_CORE = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Product Documentations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PL_DOCUMENTATION__PRODUCT_DOCUMENTATIONS = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>PL Documentation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PL_DOCUMENTATION_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.ProductImpl <em>Product</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.ProductImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getProduct()
	 * @generated
	 */
	int PRODUCT = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__NAME = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__ID = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.DocumentationCoreImpl <em>Documentation Core</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.DocumentationCoreImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getDocumentationCore()
	 * @generated
	 */
	int DOCUMENTATION_CORE = 17;

	/**
	 * The feature id for the '<em><b>Parts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENTATION_CORE__PARTS = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Documentation Core</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENTATION_CORE_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.ProductDocumentationImpl <em>Product Documentation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.ProductDocumentationImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getProductDocumentation()
	 * @generated
	 */
	int PRODUCT_DOCUMENTATION = 18;

	/**
	 * The feature id for the '<em><b>Final Inf Products</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_DOCUMENTATION__FINAL_INF_PRODUCTS = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Product</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_DOCUMENTATION__PRODUCT = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Product Documentation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_DOCUMENTATION_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.VariableImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.NestPointType <em>Nest Point Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.NestPointType
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getNestPointType()
	 * @generated
	 */
	int NEST_POINT_TYPE = 20;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.GroupType <em>Group Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.GroupType
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getGroupType()
	 * @generated
	 */
	int GROUP_TYPE = 21;


	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.DrlElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see org.spbu.pldoctoolkit.graph.DrlElement
	 * @generated
	 */
	EClass getDrlElement();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.InfElement <em>Inf Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inf Element</em>'.
	 * @see org.spbu.pldoctoolkit.graph.InfElement
	 * @generated
	 */
	EClass getInfElement();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.InfProduct <em>Inf Product</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inf Product</em>'.
	 * @see org.spbu.pldoctoolkit.graph.InfProduct
	 * @generated
	 */
	EClass getInfProduct();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.FinalInfProduct <em>Final Inf Product</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Final Inf Product</em>'.
	 * @see org.spbu.pldoctoolkit.graph.FinalInfProduct
	 * @generated
	 */
	EClass getFinalInfProduct();

	/**
	 * Returns the meta object for the containment reference list '{@link org.spbu.pldoctoolkit.graph.FinalInfProduct#getAdapters <em>Adapters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Adapters</em>'.
	 * @see org.spbu.pldoctoolkit.graph.FinalInfProduct#getAdapters()
	 * @see #getFinalInfProduct()
	 * @generated
	 */
	EReference getFinalInfProduct_Adapters();

	/**
	 * Returns the meta object for the reference '{@link org.spbu.pldoctoolkit.graph.FinalInfProduct#getProduct <em>Product</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Product</em>'.
	 * @see org.spbu.pldoctoolkit.graph.FinalInfProduct#getProduct()
	 * @see #getFinalInfProduct()
	 * @generated
	 */
	EReference getFinalInfProduct_Product();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.FinalInfProduct#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.spbu.pldoctoolkit.graph.FinalInfProduct#getId()
	 * @see #getFinalInfProduct()
	 * @generated
	 */
	EAttribute getFinalInfProduct_Id();

	/**
	 * Returns the meta object for the containment reference '{@link org.spbu.pldoctoolkit.graph.FinalInfProduct#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Variables</em>'.
	 * @see org.spbu.pldoctoolkit.graph.FinalInfProduct#getVariables()
	 * @see #getFinalInfProduct()
	 * @generated
	 */
	EReference getFinalInfProduct_Variables();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.Adapter <em>Adapter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adapter</em>'.
	 * @see org.spbu.pldoctoolkit.graph.Adapter
	 * @generated
	 */
	EClass getAdapter();

	/**
	 * Returns the meta object for the containment reference list '{@link org.spbu.pldoctoolkit.graph.Adapter#getNestPointRefs <em>Nest Point Refs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nest Point Refs</em>'.
	 * @see org.spbu.pldoctoolkit.graph.Adapter#getNestPointRefs()
	 * @see #getAdapter()
	 * @generated
	 */
	EReference getAdapter_NestPointRefs();

	/**
	 * Returns the meta object for the reference '{@link org.spbu.pldoctoolkit.graph.Adapter#getInfElemRef <em>Inf Elem Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Inf Elem Ref</em>'.
	 * @see org.spbu.pldoctoolkit.graph.Adapter#getInfElemRef()
	 * @see #getAdapter()
	 * @generated
	 */
	EReference getAdapter_InfElemRef();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.NestPoint <em>Nest Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Nest Point</em>'.
	 * @see org.spbu.pldoctoolkit.graph.NestPoint
	 * @generated
	 */
	EClass getNestPoint();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.NestPoint#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.spbu.pldoctoolkit.graph.NestPoint#getId()
	 * @see #getNestPoint()
	 * @generated
	 */
	EAttribute getNestPoint_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.NestPoint#getDescr <em>Descr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Descr</em>'.
	 * @see org.spbu.pldoctoolkit.graph.NestPoint#getDescr()
	 * @see #getNestPoint()
	 * @generated
	 */
	EAttribute getNestPoint_Descr();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.InnerElement <em>Inner Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inner Element</em>'.
	 * @see org.spbu.pldoctoolkit.graph.InnerElement
	 * @generated
	 */
	EClass getInnerElement();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.Conditional <em>Conditional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conditional</em>'.
	 * @see org.spbu.pldoctoolkit.graph.Conditional
	 * @generated
	 */
	EClass getConditional();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.Conditional#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Condition</em>'.
	 * @see org.spbu.pldoctoolkit.graph.Conditional#getCondition()
	 * @see #getConditional()
	 * @generated
	 */
	EAttribute getConditional_Condition();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.NestPointRef <em>Nest Point Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Nest Point Ref</em>'.
	 * @see org.spbu.pldoctoolkit.graph.NestPointRef
	 * @generated
	 */
	EClass getNestPointRef();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.NestPointRef#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.spbu.pldoctoolkit.graph.NestPointRef#getType()
	 * @see #getNestPointRef()
	 * @generated
	 */
	EAttribute getNestPointRef_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.NestPointRef#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.spbu.pldoctoolkit.graph.NestPointRef#getText()
	 * @see #getNestPointRef()
	 * @generated
	 */
	EAttribute getNestPointRef_Text();

	/**
	 * Returns the meta object for the reference '{@link org.spbu.pldoctoolkit.graph.NestPointRef#getNestPoint <em>Nest Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Nest Point</em>'.
	 * @see org.spbu.pldoctoolkit.graph.NestPointRef#getNestPoint()
	 * @see #getNestPointRef()
	 * @generated
	 */
	EReference getNestPointRef_NestPoint();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.GenericDocumentPart <em>Generic Document Part</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Document Part</em>'.
	 * @see org.spbu.pldoctoolkit.graph.GenericDocumentPart
	 * @generated
	 */
	EClass getGenericDocumentPart();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.GenericDocumentPart#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.spbu.pldoctoolkit.graph.GenericDocumentPart#getId()
	 * @see #getGenericDocumentPart()
	 * @generated
	 */
	EAttribute getGenericDocumentPart_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.GenericDocumentPart#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.spbu.pldoctoolkit.graph.GenericDocumentPart#getName()
	 * @see #getGenericDocumentPart()
	 * @generated
	 */
	EAttribute getGenericDocumentPart_Name();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.InfElemRef <em>Inf Elem Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inf Elem Ref</em>'.
	 * @see org.spbu.pldoctoolkit.graph.InfElemRef
	 * @generated
	 */
	EClass getInfElemRef();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.InfElemRef#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.spbu.pldoctoolkit.graph.InfElemRef#getId()
	 * @see #getInfElemRef()
	 * @generated
	 */
	EAttribute getInfElemRef_Id();

	/**
	 * Returns the meta object for the reference '{@link org.spbu.pldoctoolkit.graph.InfElemRef#getInfelem <em>Infelem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Infelem</em>'.
	 * @see org.spbu.pldoctoolkit.graph.InfElemRef#getInfelem()
	 * @see #getInfElemRef()
	 * @generated
	 */
	EReference getInfElemRef_Infelem();

	/**
	 * Returns the meta object for the reference '{@link org.spbu.pldoctoolkit.graph.InfElemRef#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Group</em>'.
	 * @see org.spbu.pldoctoolkit.graph.InfElemRef#getGroup()
	 * @see #getInfElemRef()
	 * @generated
	 */
	EReference getInfElemRef_Group();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.InfElemRef#isOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see org.spbu.pldoctoolkit.graph.InfElemRef#isOptional()
	 * @see #getInfElemRef()
	 * @generated
	 */
	EAttribute getInfElemRef_Optional();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.InfElemRefGroup <em>Inf Elem Ref Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inf Elem Ref Group</em>'.
	 * @see org.spbu.pldoctoolkit.graph.InfElemRefGroup
	 * @generated
	 */
	EClass getInfElemRefGroup();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.InfElemRefGroup#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.spbu.pldoctoolkit.graph.InfElemRefGroup#getId()
	 * @see #getInfElemRefGroup()
	 * @generated
	 */
	EAttribute getInfElemRefGroup_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.InfElemRefGroup#getModifier <em>Modifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Modifier</em>'.
	 * @see org.spbu.pldoctoolkit.graph.InfElemRefGroup#getModifier()
	 * @see #getInfElemRefGroup()
	 * @generated
	 */
	EAttribute getInfElemRefGroup_Modifier();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.InfElemRefGroup#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.spbu.pldoctoolkit.graph.InfElemRefGroup#getName()
	 * @see #getInfElemRefGroup()
	 * @generated
	 */
	EAttribute getInfElemRefGroup_Name();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.SubelementedElement <em>Subelemented Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subelemented Element</em>'.
	 * @see org.spbu.pldoctoolkit.graph.SubelementedElement
	 * @generated
	 */
	EClass getSubelementedElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.spbu.pldoctoolkit.graph.SubelementedElement#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see org.spbu.pldoctoolkit.graph.SubelementedElement#getElements()
	 * @see #getSubelementedElement()
	 * @generated
	 */
	EReference getSubelementedElement_Elements();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.ProductLine <em>Product Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Product Line</em>'.
	 * @see org.spbu.pldoctoolkit.graph.ProductLine
	 * @generated
	 */
	EClass getProductLine();

	/**
	 * Returns the meta object for the containment reference '{@link org.spbu.pldoctoolkit.graph.ProductLine#getScheme <em>Scheme</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Scheme</em>'.
	 * @see org.spbu.pldoctoolkit.graph.ProductLine#getScheme()
	 * @see #getProductLine()
	 * @generated
	 */
	EReference getProductLine_Scheme();

	/**
	 * Returns the meta object for the containment reference '{@link org.spbu.pldoctoolkit.graph.ProductLine#getDocumentation <em>Documentation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Documentation</em>'.
	 * @see org.spbu.pldoctoolkit.graph.ProductLine#getDocumentation()
	 * @see #getProductLine()
	 * @generated
	 */
	EReference getProductLine_Documentation();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.ProductLine#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.spbu.pldoctoolkit.graph.ProductLine#getName()
	 * @see #getProductLine()
	 * @generated
	 */
	EAttribute getProductLine_Name();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.PLScheme <em>PL Scheme</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>PL Scheme</em>'.
	 * @see org.spbu.pldoctoolkit.graph.PLScheme
	 * @generated
	 */
	EClass getPLScheme();

	/**
	 * Returns the meta object for the containment reference list '{@link org.spbu.pldoctoolkit.graph.PLScheme#getProducts <em>Products</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Products</em>'.
	 * @see org.spbu.pldoctoolkit.graph.PLScheme#getProducts()
	 * @see #getPLScheme()
	 * @generated
	 */
	EReference getPLScheme_Products();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.PLDocumentation <em>PL Documentation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>PL Documentation</em>'.
	 * @see org.spbu.pldoctoolkit.graph.PLDocumentation
	 * @generated
	 */
	EClass getPLDocumentation();

	/**
	 * Returns the meta object for the containment reference '{@link org.spbu.pldoctoolkit.graph.PLDocumentation#getDocumentationCore <em>Documentation Core</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Documentation Core</em>'.
	 * @see org.spbu.pldoctoolkit.graph.PLDocumentation#getDocumentationCore()
	 * @see #getPLDocumentation()
	 * @generated
	 */
	EReference getPLDocumentation_DocumentationCore();

	/**
	 * Returns the meta object for the containment reference list '{@link org.spbu.pldoctoolkit.graph.PLDocumentation#getProductDocumentations <em>Product Documentations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Product Documentations</em>'.
	 * @see org.spbu.pldoctoolkit.graph.PLDocumentation#getProductDocumentations()
	 * @see #getPLDocumentation()
	 * @generated
	 */
	EReference getPLDocumentation_ProductDocumentations();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.Product <em>Product</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Product</em>'.
	 * @see org.spbu.pldoctoolkit.graph.Product
	 * @generated
	 */
	EClass getProduct();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.Product#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.spbu.pldoctoolkit.graph.Product#getName()
	 * @see #getProduct()
	 * @generated
	 */
	EAttribute getProduct_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.Product#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.spbu.pldoctoolkit.graph.Product#getId()
	 * @see #getProduct()
	 * @generated
	 */
	EAttribute getProduct_Id();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.DocumentationCore <em>Documentation Core</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Documentation Core</em>'.
	 * @see org.spbu.pldoctoolkit.graph.DocumentationCore
	 * @generated
	 */
	EClass getDocumentationCore();

	/**
	 * Returns the meta object for the containment reference list '{@link org.spbu.pldoctoolkit.graph.DocumentationCore#getParts <em>Parts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parts</em>'.
	 * @see org.spbu.pldoctoolkit.graph.DocumentationCore#getParts()
	 * @see #getDocumentationCore()
	 * @generated
	 */
	EReference getDocumentationCore_Parts();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.ProductDocumentation <em>Product Documentation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Product Documentation</em>'.
	 * @see org.spbu.pldoctoolkit.graph.ProductDocumentation
	 * @generated
	 */
	EClass getProductDocumentation();

	/**
	 * Returns the meta object for the containment reference list '{@link org.spbu.pldoctoolkit.graph.ProductDocumentation#getFinalInfProducts <em>Final Inf Products</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Final Inf Products</em>'.
	 * @see org.spbu.pldoctoolkit.graph.ProductDocumentation#getFinalInfProducts()
	 * @see #getProductDocumentation()
	 * @generated
	 */
	EReference getProductDocumentation_FinalInfProducts();

	/**
	 * Returns the meta object for the reference '{@link org.spbu.pldoctoolkit.graph.ProductDocumentation#getProduct <em>Product</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Product</em>'.
	 * @see org.spbu.pldoctoolkit.graph.ProductDocumentation#getProduct()
	 * @see #getProductDocumentation()
	 * @generated
	 */
	EReference getProductDocumentation_Product();

	/**
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see org.spbu.pldoctoolkit.graph.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.Variable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.spbu.pldoctoolkit.graph.Variable#getName()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_Name();

	/**
	 * Returns the meta object for enum '{@link org.spbu.pldoctoolkit.graph.NestPointType <em>Nest Point Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Nest Point Type</em>'.
	 * @see org.spbu.pldoctoolkit.graph.NestPointType
	 * @generated
	 */
	EEnum getNestPointType();

	/**
	 * Returns the meta object for enum '{@link org.spbu.pldoctoolkit.graph.GroupType <em>Group Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Group Type</em>'.
	 * @see org.spbu.pldoctoolkit.graph.GroupType
	 * @generated
	 */
	EEnum getGroupType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DrlFactory getDrlFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.DrlElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlElementImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getDrlElement()
		 * @generated
		 */
		EClass DRL_ELEMENT = eINSTANCE.getDrlElement();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.InfElementImpl <em>Inf Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.InfElementImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getInfElement()
		 * @generated
		 */
		EClass INF_ELEMENT = eINSTANCE.getInfElement();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.InfProductImpl <em>Inf Product</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.InfProductImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getInfProduct()
		 * @generated
		 */
		EClass INF_PRODUCT = eINSTANCE.getInfProduct();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.FinalInfProductImpl <em>Final Inf Product</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.FinalInfProductImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getFinalInfProduct()
		 * @generated
		 */
		EClass FINAL_INF_PRODUCT = eINSTANCE.getFinalInfProduct();

		/**
		 * The meta object literal for the '<em><b>Adapters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FINAL_INF_PRODUCT__ADAPTERS = eINSTANCE.getFinalInfProduct_Adapters();

		/**
		 * The meta object literal for the '<em><b>Product</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FINAL_INF_PRODUCT__PRODUCT = eINSTANCE.getFinalInfProduct_Product();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FINAL_INF_PRODUCT__ID = eINSTANCE.getFinalInfProduct_Id();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FINAL_INF_PRODUCT__VARIABLES = eINSTANCE.getFinalInfProduct_Variables();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.AdapterImpl <em>Adapter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.AdapterImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getAdapter()
		 * @generated
		 */
		EClass ADAPTER = eINSTANCE.getAdapter();

		/**
		 * The meta object literal for the '<em><b>Nest Point Refs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADAPTER__NEST_POINT_REFS = eINSTANCE.getAdapter_NestPointRefs();

		/**
		 * The meta object literal for the '<em><b>Inf Elem Ref</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADAPTER__INF_ELEM_REF = eINSTANCE.getAdapter_InfElemRef();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.NestPointImpl <em>Nest Point</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.NestPointImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getNestPoint()
		 * @generated
		 */
		EClass NEST_POINT = eINSTANCE.getNestPoint();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NEST_POINT__ID = eINSTANCE.getNestPoint_Id();

		/**
		 * The meta object literal for the '<em><b>Descr</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NEST_POINT__DESCR = eINSTANCE.getNestPoint_Descr();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.InnerElementImpl <em>Inner Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.InnerElementImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getInnerElement()
		 * @generated
		 */
		EClass INNER_ELEMENT = eINSTANCE.getInnerElement();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.ConditionalImpl <em>Conditional</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.ConditionalImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getConditional()
		 * @generated
		 */
		EClass CONDITIONAL = eINSTANCE.getConditional();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITIONAL__CONDITION = eINSTANCE.getConditional_Condition();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.NestPointRefImpl <em>Nest Point Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.NestPointRefImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getNestPointRef()
		 * @generated
		 */
		EClass NEST_POINT_REF = eINSTANCE.getNestPointRef();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NEST_POINT_REF__TYPE = eINSTANCE.getNestPointRef_Type();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NEST_POINT_REF__TEXT = eINSTANCE.getNestPointRef_Text();

		/**
		 * The meta object literal for the '<em><b>Nest Point</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NEST_POINT_REF__NEST_POINT = eINSTANCE.getNestPointRef_NestPoint();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.GenericDocumentPartImpl <em>Generic Document Part</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.GenericDocumentPartImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getGenericDocumentPart()
		 * @generated
		 */
		EClass GENERIC_DOCUMENT_PART = eINSTANCE.getGenericDocumentPart();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GENERIC_DOCUMENT_PART__ID = eINSTANCE.getGenericDocumentPart_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GENERIC_DOCUMENT_PART__NAME = eINSTANCE.getGenericDocumentPart_Name();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.InfElemRefImpl <em>Inf Elem Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.InfElemRefImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getInfElemRef()
		 * @generated
		 */
		EClass INF_ELEM_REF = eINSTANCE.getInfElemRef();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INF_ELEM_REF__ID = eINSTANCE.getInfElemRef_Id();

		/**
		 * The meta object literal for the '<em><b>Infelem</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INF_ELEM_REF__INFELEM = eINSTANCE.getInfElemRef_Infelem();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INF_ELEM_REF__GROUP = eINSTANCE.getInfElemRef_Group();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INF_ELEM_REF__OPTIONAL = eINSTANCE.getInfElemRef_Optional();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.InfElemRefGroupImpl <em>Inf Elem Ref Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.InfElemRefGroupImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getInfElemRefGroup()
		 * @generated
		 */
		EClass INF_ELEM_REF_GROUP = eINSTANCE.getInfElemRefGroup();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INF_ELEM_REF_GROUP__ID = eINSTANCE.getInfElemRefGroup_Id();

		/**
		 * The meta object literal for the '<em><b>Modifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INF_ELEM_REF_GROUP__MODIFIER = eINSTANCE.getInfElemRefGroup_Modifier();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INF_ELEM_REF_GROUP__NAME = eINSTANCE.getInfElemRefGroup_Name();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.SubelementedElementImpl <em>Subelemented Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.SubelementedElementImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getSubelementedElement()
		 * @generated
		 */
		EClass SUBELEMENTED_ELEMENT = eINSTANCE.getSubelementedElement();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBELEMENTED_ELEMENT__ELEMENTS = eINSTANCE.getSubelementedElement_Elements();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.ProductLineImpl <em>Product Line</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.ProductLineImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getProductLine()
		 * @generated
		 */
		EClass PRODUCT_LINE = eINSTANCE.getProductLine();

		/**
		 * The meta object literal for the '<em><b>Scheme</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRODUCT_LINE__SCHEME = eINSTANCE.getProductLine_Scheme();

		/**
		 * The meta object literal for the '<em><b>Documentation</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRODUCT_LINE__DOCUMENTATION = eINSTANCE.getProductLine_Documentation();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRODUCT_LINE__NAME = eINSTANCE.getProductLine_Name();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.PLSchemeImpl <em>PL Scheme</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.PLSchemeImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getPLScheme()
		 * @generated
		 */
		EClass PL_SCHEME = eINSTANCE.getPLScheme();

		/**
		 * The meta object literal for the '<em><b>Products</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PL_SCHEME__PRODUCTS = eINSTANCE.getPLScheme_Products();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.PLDocumentationImpl <em>PL Documentation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.PLDocumentationImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getPLDocumentation()
		 * @generated
		 */
		EClass PL_DOCUMENTATION = eINSTANCE.getPLDocumentation();

		/**
		 * The meta object literal for the '<em><b>Documentation Core</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PL_DOCUMENTATION__DOCUMENTATION_CORE = eINSTANCE.getPLDocumentation_DocumentationCore();

		/**
		 * The meta object literal for the '<em><b>Product Documentations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PL_DOCUMENTATION__PRODUCT_DOCUMENTATIONS = eINSTANCE.getPLDocumentation_ProductDocumentations();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.ProductImpl <em>Product</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.ProductImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getProduct()
		 * @generated
		 */
		EClass PRODUCT = eINSTANCE.getProduct();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRODUCT__NAME = eINSTANCE.getProduct_Name();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRODUCT__ID = eINSTANCE.getProduct_Id();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.DocumentationCoreImpl <em>Documentation Core</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.DocumentationCoreImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getDocumentationCore()
		 * @generated
		 */
		EClass DOCUMENTATION_CORE = eINSTANCE.getDocumentationCore();

		/**
		 * The meta object literal for the '<em><b>Parts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENTATION_CORE__PARTS = eINSTANCE.getDocumentationCore_Parts();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.ProductDocumentationImpl <em>Product Documentation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.ProductDocumentationImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getProductDocumentation()
		 * @generated
		 */
		EClass PRODUCT_DOCUMENTATION = eINSTANCE.getProductDocumentation();

		/**
		 * The meta object literal for the '<em><b>Final Inf Products</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRODUCT_DOCUMENTATION__FINAL_INF_PRODUCTS = eINSTANCE.getProductDocumentation_FinalInfProducts();

		/**
		 * The meta object literal for the '<em><b>Product</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRODUCT_DOCUMENTATION__PRODUCT = eINSTANCE.getProductDocumentation_Product();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.VariableImpl <em>Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.VariableImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE__NAME = eINSTANCE.getVariable_Name();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.NestPointType <em>Nest Point Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.NestPointType
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getNestPointType()
		 * @generated
		 */
		EEnum NEST_POINT_TYPE = eINSTANCE.getNestPointType();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.GroupType <em>Group Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.GroupType
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getGroupType()
		 * @generated
		 */
		EEnum GROUP_TYPE = eINSTANCE.getGroupType();

	}

} //DrlPackage
