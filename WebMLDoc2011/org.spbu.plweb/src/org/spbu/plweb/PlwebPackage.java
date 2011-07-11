/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.plweb;

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
 * @see org.spbu.plweb.PlwebFactory
 * @model kind="package"
 * @generated
 */
public interface PlwebPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "plweb";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://plweb/2.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "plweb";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PlwebPackage eINSTANCE = org.spbu.plweb.impl.PlwebPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.spbu.plweb.impl.ElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.plweb.impl.ElementImpl
	 * @see org.spbu.plweb.impl.PlwebPackageImpl#getElement()
	 * @generated
	 */
	int ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__TITLE = 0;

	/**
	 * The feature id for the '<em><b>Doc Topic</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__DOC_TOPIC = 1;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.spbu.plweb.impl.TargetRefElementImpl <em>Target Ref Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.plweb.impl.TargetRefElementImpl
	 * @see org.spbu.plweb.impl.PlwebPackageImpl#getTargetRefElement()
	 * @generated
	 */
	int TARGET_REF_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_REF_ELEMENT__TITLE = ELEMENT__TITLE;

	/**
	 * The feature id for the '<em><b>Doc Topic</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_REF_ELEMENT__DOC_TOPIC = ELEMENT__DOC_TOPIC;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_REF_ELEMENT__PARENT = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_REF_ELEMENT__OPTIONAL = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Target Ref Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_REF_ELEMENT_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.spbu.plweb.impl.SourceRefElementImpl <em>Source Ref Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.plweb.impl.SourceRefElementImpl
	 * @see org.spbu.plweb.impl.PlwebPackageImpl#getSourceRefElement()
	 * @generated
	 */
	int SOURCE_REF_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_REF_ELEMENT__TITLE = ELEMENT__TITLE;

	/**
	 * The feature id for the '<em><b>Doc Topic</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_REF_ELEMENT__DOC_TOPIC = ELEMENT__DOC_TOPIC;

	/**
	 * The feature id for the '<em><b>Class</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_REF_ELEMENT__CLASS = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Source Ref Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_REF_ELEMENT_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.spbu.plweb.impl.PageImpl <em>Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.plweb.impl.PageImpl
	 * @see org.spbu.plweb.impl.PlwebPackageImpl#getPage()
	 * @generated
	 */
	int PAGE = 3;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__TITLE = TARGET_REF_ELEMENT__TITLE;

	/**
	 * The feature id for the '<em><b>Doc Topic</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__DOC_TOPIC = TARGET_REF_ELEMENT__DOC_TOPIC;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__PARENT = TARGET_REF_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__OPTIONAL = TARGET_REF_ELEMENT__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__SOURCE = TARGET_REF_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_FEATURE_COUNT = TARGET_REF_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.spbu.plweb.impl.GroupImpl <em>Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.plweb.impl.GroupImpl
	 * @see org.spbu.plweb.impl.PlwebPackageImpl#getGroup()
	 * @generated
	 */
	int GROUP = 4;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__TITLE = SOURCE_REF_ELEMENT__TITLE;

	/**
	 * The feature id for the '<em><b>Doc Topic</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__DOC_TOPIC = SOURCE_REF_ELEMENT__DOC_TOPIC;

	/**
	 * The feature id for the '<em><b>Class</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__CLASS = SOURCE_REF_ELEMENT__CLASS;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__PARENT = SOURCE_REF_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__OPTIONAL = SOURCE_REF_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__TYPE = SOURCE_REF_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_FEATURE_COUNT = SOURCE_REF_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.spbu.plweb.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.plweb.impl.NodeImpl
	 * @see org.spbu.plweb.impl.PlwebPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 5;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__TITLE = SOURCE_REF_ELEMENT__TITLE;

	/**
	 * The feature id for the '<em><b>Doc Topic</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__DOC_TOPIC = SOURCE_REF_ELEMENT__DOC_TOPIC;

	/**
	 * The feature id for the '<em><b>Class</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__CLASS = SOURCE_REF_ELEMENT__CLASS;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__PARENT = SOURCE_REF_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__OPTIONAL = SOURCE_REF_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = SOURCE_REF_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.spbu.plweb.impl.AreaImpl <em>Area</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.plweb.impl.AreaImpl
	 * @see org.spbu.plweb.impl.PlwebPackageImpl#getArea()
	 * @generated
	 */
	int AREA = 6;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA__TITLE = SOURCE_REF_ELEMENT__TITLE;

	/**
	 * The feature id for the '<em><b>Doc Topic</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA__DOC_TOPIC = SOURCE_REF_ELEMENT__DOC_TOPIC;

	/**
	 * The feature id for the '<em><b>Class</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA__CLASS = SOURCE_REF_ELEMENT__CLASS;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA__PARENT = SOURCE_REF_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA__OPTIONAL = SOURCE_REF_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Area</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_FEATURE_COUNT = SOURCE_REF_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.spbu.plweb.impl.SiteViewImpl <em>Site View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.plweb.impl.SiteViewImpl
	 * @see org.spbu.plweb.impl.PlwebPackageImpl#getSiteView()
	 * @generated
	 */
	int SITE_VIEW = 7;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE_VIEW__TITLE = SOURCE_REF_ELEMENT__TITLE;

	/**
	 * The feature id for the '<em><b>Doc Topic</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE_VIEW__DOC_TOPIC = SOURCE_REF_ELEMENT__DOC_TOPIC;

	/**
	 * The feature id for the '<em><b>Class</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE_VIEW__CLASS = SOURCE_REF_ELEMENT__CLASS;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE_VIEW__PARENT = SOURCE_REF_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE_VIEW__OPTIONAL = SOURCE_REF_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Site View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITE_VIEW_FEATURE_COUNT = SOURCE_REF_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.spbu.plweb.impl.RootImpl <em>Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.plweb.impl.RootImpl
	 * @see org.spbu.plweb.impl.PlwebPackageImpl#getRoot()
	 * @generated
	 */
	int ROOT = 8;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT__TITLE = SOURCE_REF_ELEMENT__TITLE;

	/**
	 * The feature id for the '<em><b>Doc Topic</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT__DOC_TOPIC = SOURCE_REF_ELEMENT__DOC_TOPIC;

	/**
	 * The feature id for the '<em><b>Class</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT__CLASS = SOURCE_REF_ELEMENT__CLASS;

	/**
	 * The number of structural features of the '<em>Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_FEATURE_COUNT = SOURCE_REF_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.spbu.plweb.impl.DiagramRootImpl <em>Diagram Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.plweb.impl.DiagramRootImpl
	 * @see org.spbu.plweb.impl.PlwebPackageImpl#getDiagramRoot()
	 * @generated
	 */
	int DIAGRAM_ROOT = 9;

	/**
	 * The feature id for the '<em><b>Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ROOT__ROOT = 0;

	/**
	 * The feature id for the '<em><b>Project Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ROOT__PROJECT_PATH = 1;

	/**
	 * The feature id for the '<em><b>Diagram Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ROOT__DIAGRAM_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Doc Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ROOT__DOC_PATH = 3;

	/**
	 * The number of structural features of the '<em>Diagram Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ROOT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.spbu.plweb.impl.DocTopicImpl <em>Doc Topic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.plweb.impl.DocTopicImpl
	 * @see org.spbu.plweb.impl.PlwebPackageImpl#getDocTopic()
	 * @generated
	 */
	int DOC_TOPIC = 10;

	/**
	 * The feature id for the '<em><b>Doc Topic Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOC_TOPIC__DOC_TOPIC_NAME = 0;

	/**
	 * The number of structural features of the '<em>Doc Topic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOC_TOPIC_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.spbu.plweb.GroupType <em>Group Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.plweb.GroupType
	 * @see org.spbu.plweb.impl.PlwebPackageImpl#getGroupType()
	 * @generated
	 */
	int GROUP_TYPE = 11;

	/**
	 * The meta object id for the '{@link org.spbu.plweb.DiagramType <em>Diagram Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.spbu.plweb.DiagramType
	 * @see org.spbu.plweb.impl.PlwebPackageImpl#getDiagramType()
	 * @generated
	 */
	int DIAGRAM_TYPE = 12;


	/**
	 * Returns the meta object for class '{@link org.spbu.plweb.Element <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see org.spbu.plweb.Element
	 * @generated
	 */
	EClass getElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.spbu.plweb.Element#getDocTopic <em>Doc Topic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Doc Topic</em>'.
	 * @see org.spbu.plweb.Element#getDocTopic()
	 * @see #getElement()
	 * @generated
	 */
	EReference getElement_DocTopic();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.plweb.Element#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see org.spbu.plweb.Element#getTitle()
	 * @see #getElement()
	 * @generated
	 */
	EAttribute getElement_Title();

	/**
	 * Returns the meta object for class '{@link org.spbu.plweb.TargetRefElement <em>Target Ref Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target Ref Element</em>'.
	 * @see org.spbu.plweb.TargetRefElement
	 * @generated
	 */
	EClass getTargetRefElement();

	/**
	 * Returns the meta object for the reference '{@link org.spbu.plweb.TargetRefElement#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see org.spbu.plweb.TargetRefElement#getParent()
	 * @see #getTargetRefElement()
	 * @generated
	 */
	EReference getTargetRefElement_Parent();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.plweb.TargetRefElement#isOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see org.spbu.plweb.TargetRefElement#isOptional()
	 * @see #getTargetRefElement()
	 * @generated
	 */
	EAttribute getTargetRefElement_Optional();

	/**
	 * Returns the meta object for class '{@link org.spbu.plweb.SourceRefElement <em>Source Ref Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source Ref Element</em>'.
	 * @see org.spbu.plweb.SourceRefElement
	 * @generated
	 */
	EClass getSourceRefElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.spbu.plweb.SourceRefElement#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Class</em>'.
	 * @see org.spbu.plweb.SourceRefElement#getClass_()
	 * @see #getSourceRefElement()
	 * @generated
	 */
	EReference getSourceRefElement_Class();

	/**
	 * Returns the meta object for class '{@link org.spbu.plweb.Page <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Page</em>'.
	 * @see org.spbu.plweb.Page
	 * @generated
	 */
	EClass getPage();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.plweb.Page#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see org.spbu.plweb.Page#getSource()
	 * @see #getPage()
	 * @generated
	 */
	EAttribute getPage_Source();

	/**
	 * Returns the meta object for class '{@link org.spbu.plweb.Group <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group</em>'.
	 * @see org.spbu.plweb.Group
	 * @generated
	 */
	EClass getGroup();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.plweb.Group#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.spbu.plweb.Group#getType()
	 * @see #getGroup()
	 * @generated
	 */
	EAttribute getGroup_Type();

	/**
	 * Returns the meta object for class '{@link org.spbu.plweb.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see org.spbu.plweb.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for class '{@link org.spbu.plweb.Area <em>Area</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Area</em>'.
	 * @see org.spbu.plweb.Area
	 * @generated
	 */
	EClass getArea();

	/**
	 * Returns the meta object for class '{@link org.spbu.plweb.SiteView <em>Site View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Site View</em>'.
	 * @see org.spbu.plweb.SiteView
	 * @generated
	 */
	EClass getSiteView();

	/**
	 * Returns the meta object for class '{@link org.spbu.plweb.Root <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root</em>'.
	 * @see org.spbu.plweb.Root
	 * @generated
	 */
	EClass getRoot();

	/**
	 * Returns the meta object for class '{@link org.spbu.plweb.DiagramRoot <em>Diagram Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Diagram Root</em>'.
	 * @see org.spbu.plweb.DiagramRoot
	 * @generated
	 */
	EClass getDiagramRoot();

	/**
	 * Returns the meta object for the containment reference '{@link org.spbu.plweb.DiagramRoot#getRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Root</em>'.
	 * @see org.spbu.plweb.DiagramRoot#getRoot()
	 * @see #getDiagramRoot()
	 * @generated
	 */
	EReference getDiagramRoot_Root();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.plweb.DiagramRoot#getProjectPath <em>Project Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Path</em>'.
	 * @see org.spbu.plweb.DiagramRoot#getProjectPath()
	 * @see #getDiagramRoot()
	 * @generated
	 */
	EAttribute getDiagramRoot_ProjectPath();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.plweb.DiagramRoot#getDiagramType <em>Diagram Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Diagram Type</em>'.
	 * @see org.spbu.plweb.DiagramRoot#getDiagramType()
	 * @see #getDiagramRoot()
	 * @generated
	 */
	EAttribute getDiagramRoot_DiagramType();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.plweb.DiagramRoot#getDocPath <em>Doc Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Doc Path</em>'.
	 * @see org.spbu.plweb.DiagramRoot#getDocPath()
	 * @see #getDiagramRoot()
	 * @generated
	 */
	EAttribute getDiagramRoot_DocPath();

	/**
	 * Returns the meta object for class '{@link org.spbu.plweb.DocTopic <em>Doc Topic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Doc Topic</em>'.
	 * @see org.spbu.plweb.DocTopic
	 * @generated
	 */
	EClass getDocTopic();

	/**
	 * Returns the meta object for the attribute '{@link org.spbu.plweb.DocTopic#getDocTopicName <em>Doc Topic Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Doc Topic Name</em>'.
	 * @see org.spbu.plweb.DocTopic#getDocTopicName()
	 * @see #getDocTopic()
	 * @generated
	 */
	EAttribute getDocTopic_DocTopicName();

	/**
	 * Returns the meta object for enum '{@link org.spbu.plweb.GroupType <em>Group Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Group Type</em>'.
	 * @see org.spbu.plweb.GroupType
	 * @generated
	 */
	EEnum getGroupType();

	/**
	 * Returns the meta object for enum '{@link org.spbu.plweb.DiagramType <em>Diagram Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Diagram Type</em>'.
	 * @see org.spbu.plweb.DiagramType
	 * @generated
	 */
	EEnum getDiagramType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PlwebFactory getPlwebFactory();

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
		 * The meta object literal for the '{@link org.spbu.plweb.impl.ElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.plweb.impl.ElementImpl
		 * @see org.spbu.plweb.impl.PlwebPackageImpl#getElement()
		 * @generated
		 */
		EClass ELEMENT = eINSTANCE.getElement();

		/**
		 * The meta object literal for the '<em><b>Doc Topic</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT__DOC_TOPIC = eINSTANCE.getElement_DocTopic();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT__TITLE = eINSTANCE.getElement_Title();

		/**
		 * The meta object literal for the '{@link org.spbu.plweb.impl.TargetRefElementImpl <em>Target Ref Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.plweb.impl.TargetRefElementImpl
		 * @see org.spbu.plweb.impl.PlwebPackageImpl#getTargetRefElement()
		 * @generated
		 */
		EClass TARGET_REF_ELEMENT = eINSTANCE.getTargetRefElement();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET_REF_ELEMENT__PARENT = eINSTANCE.getTargetRefElement_Parent();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARGET_REF_ELEMENT__OPTIONAL = eINSTANCE.getTargetRefElement_Optional();

		/**
		 * The meta object literal for the '{@link org.spbu.plweb.impl.SourceRefElementImpl <em>Source Ref Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.plweb.impl.SourceRefElementImpl
		 * @see org.spbu.plweb.impl.PlwebPackageImpl#getSourceRefElement()
		 * @generated
		 */
		EClass SOURCE_REF_ELEMENT = eINSTANCE.getSourceRefElement();

		/**
		 * The meta object literal for the '<em><b>Class</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_REF_ELEMENT__CLASS = eINSTANCE.getSourceRefElement_Class();

		/**
		 * The meta object literal for the '{@link org.spbu.plweb.impl.PageImpl <em>Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.plweb.impl.PageImpl
		 * @see org.spbu.plweb.impl.PlwebPackageImpl#getPage()
		 * @generated
		 */
		EClass PAGE = eINSTANCE.getPage();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGE__SOURCE = eINSTANCE.getPage_Source();

		/**
		 * The meta object literal for the '{@link org.spbu.plweb.impl.GroupImpl <em>Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.plweb.impl.GroupImpl
		 * @see org.spbu.plweb.impl.PlwebPackageImpl#getGroup()
		 * @generated
		 */
		EClass GROUP = eINSTANCE.getGroup();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GROUP__TYPE = eINSTANCE.getGroup_Type();

		/**
		 * The meta object literal for the '{@link org.spbu.plweb.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.plweb.impl.NodeImpl
		 * @see org.spbu.plweb.impl.PlwebPackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '{@link org.spbu.plweb.impl.AreaImpl <em>Area</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.plweb.impl.AreaImpl
		 * @see org.spbu.plweb.impl.PlwebPackageImpl#getArea()
		 * @generated
		 */
		EClass AREA = eINSTANCE.getArea();

		/**
		 * The meta object literal for the '{@link org.spbu.plweb.impl.SiteViewImpl <em>Site View</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.plweb.impl.SiteViewImpl
		 * @see org.spbu.plweb.impl.PlwebPackageImpl#getSiteView()
		 * @generated
		 */
		EClass SITE_VIEW = eINSTANCE.getSiteView();

		/**
		 * The meta object literal for the '{@link org.spbu.plweb.impl.RootImpl <em>Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.plweb.impl.RootImpl
		 * @see org.spbu.plweb.impl.PlwebPackageImpl#getRoot()
		 * @generated
		 */
		EClass ROOT = eINSTANCE.getRoot();

		/**
		 * The meta object literal for the '{@link org.spbu.plweb.impl.DiagramRootImpl <em>Diagram Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.plweb.impl.DiagramRootImpl
		 * @see org.spbu.plweb.impl.PlwebPackageImpl#getDiagramRoot()
		 * @generated
		 */
		EClass DIAGRAM_ROOT = eINSTANCE.getDiagramRoot();

		/**
		 * The meta object literal for the '<em><b>Root</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIAGRAM_ROOT__ROOT = eINSTANCE.getDiagramRoot_Root();

		/**
		 * The meta object literal for the '<em><b>Project Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM_ROOT__PROJECT_PATH = eINSTANCE.getDiagramRoot_ProjectPath();

		/**
		 * The meta object literal for the '<em><b>Diagram Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM_ROOT__DIAGRAM_TYPE = eINSTANCE.getDiagramRoot_DiagramType();

		/**
		 * The meta object literal for the '<em><b>Doc Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM_ROOT__DOC_PATH = eINSTANCE.getDiagramRoot_DocPath();

		/**
		 * The meta object literal for the '{@link org.spbu.plweb.impl.DocTopicImpl <em>Doc Topic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.plweb.impl.DocTopicImpl
		 * @see org.spbu.plweb.impl.PlwebPackageImpl#getDocTopic()
		 * @generated
		 */
		EClass DOC_TOPIC = eINSTANCE.getDocTopic();

		/**
		 * The meta object literal for the '<em><b>Doc Topic Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOC_TOPIC__DOC_TOPIC_NAME = eINSTANCE.getDocTopic_DocTopicName();

		/**
		 * The meta object literal for the '{@link org.spbu.plweb.GroupType <em>Group Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.plweb.GroupType
		 * @see org.spbu.plweb.impl.PlwebPackageImpl#getGroupType()
		 * @generated
		 */
		EEnum GROUP_TYPE = eINSTANCE.getGroupType();

		/**
		 * The meta object literal for the '{@link org.spbu.plweb.DiagramType <em>Diagram Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.spbu.plweb.DiagramType
		 * @see org.spbu.plweb.impl.PlwebPackageImpl#getDiagramType()
		 * @generated
		 */
		EEnum DIAGRAM_TYPE = eINSTANCE.getDiagramType();

	}

} //PlwebPackage
