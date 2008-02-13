/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
	String eNS_URI = "http://math.spbu.ru/drl";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "d";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DrlPackage eINSTANCE = org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.GenericDocumentPartImpl <em>Generic Document Part</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.GenericDocumentPartImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getGenericDocumentPart()
	 * @generated
	 */
	int GENERIC_DOCUMENT_PART = 4;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.InfElementImpl <em>Inf Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.InfElementImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getInfElement()
	 * @generated
	 */
	int INF_ELEMENT = 0;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.InfProductImpl <em>Inf Product</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.InfProductImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getInfProduct()
	 * @generated
	 */
	int INF_PRODUCT = 1;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.FinalInfProductImpl <em>Final Inf Product</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.FinalInfProductImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getFinalInfProduct()
	 * @generated
	 */
	int FINAL_INF_PRODUCT = 2;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.NestPointImpl <em>Nest Point</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.NestPointImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getNestPoint()
	 * @generated
	 */
	int NEST_POINT = 3;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.InfElemRefImpl <em>Inf Elem Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.InfElemRefImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getInfElemRef()
	 * @generated
	 */
	int INF_ELEM_REF = 5;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.InfElemRefGroupImpl <em>Inf Elem Ref Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.InfElemRefGroupImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getInfElemRefGroup()
	 * @generated
	 */
	int INF_ELEM_REF_GROUP = 6;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.DrlElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlElementImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getDrlElement()
	 * @generated
	 */
	int DRL_ELEMENT = 11;

	/**
	 * The feature id for the '<em><b>Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRL_ELEMENT__NODE = 0;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRL_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DOCUMENT_PART__NODE = DRL_ELEMENT__NODE;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DOCUMENT_PART__ID = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DOCUMENT_PART__NAME = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Inf Elem Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DOCUMENT_PART__INF_ELEM_REFS = DRL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DOCUMENT_PART__GROUPS = DRL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Generic Document Part</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DOCUMENT_PART_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEMENT__NODE = GENERIC_DOCUMENT_PART__NODE;

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
	 * The feature id for the '<em><b>Inf Elem Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEMENT__INF_ELEM_REFS = GENERIC_DOCUMENT_PART__INF_ELEM_REFS;

	/**
	 * The feature id for the '<em><b>Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEMENT__GROUPS = GENERIC_DOCUMENT_PART__GROUPS;

	/**
	 * The feature id for the '<em><b>Nest Points</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEMENT__NEST_POINTS = GENERIC_DOCUMENT_PART_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Inf Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEMENT_FEATURE_COUNT = GENERIC_DOCUMENT_PART_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_PRODUCT__NODE = GENERIC_DOCUMENT_PART__NODE;

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
	 * The feature id for the '<em><b>Inf Elem Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_PRODUCT__INF_ELEM_REFS = GENERIC_DOCUMENT_PART__INF_ELEM_REFS;

	/**
	 * The feature id for the '<em><b>Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_PRODUCT__GROUPS = GENERIC_DOCUMENT_PART__GROUPS;

	/**
	 * The number of structural features of the '<em>Inf Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_PRODUCT_FEATURE_COUNT = GENERIC_DOCUMENT_PART_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_INF_PRODUCT__NODE = DRL_ELEMENT__NODE;

	/**
	 * The feature id for the '<em><b>Product</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_INF_PRODUCT__PRODUCT = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_INF_PRODUCT__ID = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Final Inf Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_INF_PRODUCT_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEST_POINT__NODE = DRL_ELEMENT__NODE;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEST_POINT__ID = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Descr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEST_POINT__DESCR = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Nest Point</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEST_POINT_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF__NODE = DRL_ELEMENT__NODE;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF__ID = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Infelem</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF__INFELEM = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF__GROUP = DRL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF__OPTIONAL = DRL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Inf Elem Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF_GROUP__NODE = DRL_ELEMENT__NODE;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF_GROUP__ID = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Modifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF_GROUP__MODIFIER = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF_GROUP__NAME = DRL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Inf Elem Refs Group</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF_GROUP__INF_ELEM_REFS_GROUP = DRL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Inf Elem Ref Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_ELEM_REF_GROUP_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.ProductLineImpl <em>Product Line</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.ProductLineImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getProductLine()
	 * @generated
	 */
	int PRODUCT_LINE = 7;

	/**
	 * The feature id for the '<em><b>Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_LINE__NODE = DRL_ELEMENT__NODE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_LINE__NAME = DRL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Products</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_LINE__PRODUCTS = DRL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Product Line</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_LINE_FEATURE_COUNT = DRL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.impl.ProductImpl <em>Product</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.impl.ProductImpl
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getProduct()
	 * @generated
	 */
	int PRODUCT = 8;

	/**
	 * The feature id for the '<em><b>Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__NODE = DRL_ELEMENT__NODE;

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
	int DOCUMENTATION_CORE = 9;

	/**
	 * The feature id for the '<em><b>Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENTATION_CORE__NODE = DRL_ELEMENT__NODE;

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
	int PRODUCT_DOCUMENTATION = 10;

	/**
	 * The feature id for the '<em><b>Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_DOCUMENTATION__NODE = DRL_ELEMENT__NODE;

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
	 * The meta object id for the '{@link org.spbu.pldoctoolkit.graph.GroupType <em>Group Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.pldoctoolkit.graph.GroupType
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getGroupType()
	 * @generated
	 */
	int GROUP_TYPE = 12;


	/**
	 * The meta object id for the '<em>Node Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.w3c.dom.Element
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getNodeType()
	 * @generated
	 */
	int NODE_TYPE = 13;


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
	 * Returns the meta object for the containment reference list '{@link org.spbu.pldoctoolkit.graph.InfElement#getNestPoints <em>Nest Points</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nest Points</em>'.
	 * @see org.spbu.pldoctoolkit.graph.InfElement#getNestPoints()
	 * @see #getInfElement()
	 * @generated
	 */
	EReference getInfElement_NestPoints();

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
	 * Returns the meta object for the containment reference list '{@link org.spbu.pldoctoolkit.graph.GenericDocumentPart#getInfElemRefs <em>Inf Elem Refs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inf Elem Refs</em>'.
	 * @see org.spbu.pldoctoolkit.graph.GenericDocumentPart#getInfElemRefs()
	 * @see #getGenericDocumentPart()
	 * @generated
	 */
	EReference getGenericDocumentPart_InfElemRefs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.spbu.pldoctoolkit.graph.GenericDocumentPart#getGroups <em>Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Groups</em>'.
	 * @see org.spbu.pldoctoolkit.graph.GenericDocumentPart#getGroups()
	 * @see #getGenericDocumentPart()
	 * @generated
	 */
	EReference getGenericDocumentPart_Groups();

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
	 * Returns the meta object for the reference list '{@link org.spbu.pldoctoolkit.graph.InfElemRefGroup#getInfElemRefsGroup <em>Inf Elem Refs Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Inf Elem Refs Group</em>'.
	 * @see org.spbu.pldoctoolkit.graph.InfElemRefGroup#getInfElemRefsGroup()
	 * @see #getInfElemRefGroup()
	 * @generated
	 */
	EReference getInfElemRefGroup_InfElemRefsGroup();

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
	 * Returns the meta object for the containment reference list '{@link org.spbu.pldoctoolkit.graph.ProductLine#getProducts <em>Products</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Products</em>'.
	 * @see org.spbu.pldoctoolkit.graph.ProductLine#getProducts()
	 * @see #getProductLine()
	 * @generated
	 */
	EReference getProductLine_Products();

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
	 * Returns the meta object for class '{@link org.spbu.pldoctoolkit.graph.DrlElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see org.spbu.pldoctoolkit.graph.DrlElement
	 * @generated
	 */
	EClass getDrlElement();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.pldoctoolkit.graph.DrlElement#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Node</em>'.
	 * @see org.spbu.pldoctoolkit.graph.DrlElement#getNode()
	 * @see #getDrlElement()
	 * @generated
	 */
	EAttribute getDrlElement_Node();

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
	 * Returns the meta object for data type '{@link org.w3c.dom.Element <em>Node Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Node Type</em>'.
	 * @see org.w3c.dom.Element
	 * @model instanceClass="org.w3c.dom.Element"
	 * @generated
	 */
	EDataType getNodeType();

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
	interface Literals  {
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
		 * The meta object literal for the '<em><b>Nest Points</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INF_ELEMENT__NEST_POINTS = eINSTANCE.getInfElement_NestPoints();

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
		 * The meta object literal for the '<em><b>Inf Elem Refs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_DOCUMENT_PART__INF_ELEM_REFS = eINSTANCE.getGenericDocumentPart_InfElemRefs();

		/**
		 * The meta object literal for the '<em><b>Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_DOCUMENT_PART__GROUPS = eINSTANCE.getGenericDocumentPart_Groups();

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
		 * The meta object literal for the '<em><b>Inf Elem Refs Group</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INF_ELEM_REF_GROUP__INF_ELEM_REFS_GROUP = eINSTANCE.getInfElemRefGroup_InfElemRefsGroup();

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
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRODUCT_LINE__NAME = eINSTANCE.getProductLine_Name();

		/**
		 * The meta object literal for the '<em><b>Products</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRODUCT_LINE__PRODUCTS = eINSTANCE.getProductLine_Products();

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
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.impl.DrlElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlElementImpl
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getDrlElement()
		 * @generated
		 */
		EClass DRL_ELEMENT = eINSTANCE.getDrlElement();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DRL_ELEMENT__NODE = eINSTANCE.getDrlElement_Node();

		/**
		 * The meta object literal for the '{@link org.spbu.pldoctoolkit.graph.GroupType <em>Group Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.pldoctoolkit.graph.GroupType
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getGroupType()
		 * @generated
		 */
		EEnum GROUP_TYPE = eINSTANCE.getGroupType();

		/**
		 * The meta object literal for the '<em>Node Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.w3c.dom.Element
		 * @see org.spbu.pldoctoolkit.graph.impl.DrlPackageImpl#getNodeType()
		 * @generated
		 */
		EDataType NODE_TYPE = eINSTANCE.getNodeType();

	}

} //DrlPackage
