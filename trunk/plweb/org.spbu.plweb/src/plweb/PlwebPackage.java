/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package plweb;

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
 * @see plweb.PlwebFactory
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
	String eNS_URI = "http://plweb/1.0";

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
	PlwebPackage eINSTANCE = plweb.impl.PlwebPackageImpl.init();

	/**
	 * The meta object id for the '{@link plweb.impl.DiagramRootImpl <em>Diagram Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see plweb.impl.DiagramRootImpl
	 * @see plweb.impl.PlwebPackageImpl#getDiagramRoot()
	 * @generated
	 */
	int DIAGRAM_ROOT = 0;

	/**
	 * The feature id for the '<em><b>Area</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ROOT__AREA = 0;

	/**
	 * The feature id for the '<em><b>Project Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ROOT__PROJECT_PATH = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ROOT__TYPE = 2;

	/**
	 * The number of structural features of the '<em>Diagram Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ROOT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link plweb.impl.ElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see plweb.impl.ElementImpl
	 * @see plweb.impl.PlwebPackageImpl#getElement()
	 * @generated
	 */
	int ELEMENT = 5;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__TITLE = 0;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link plweb.impl.SourceRefElementImpl <em>Source Ref Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see plweb.impl.SourceRefElementImpl
	 * @see plweb.impl.PlwebPackageImpl#getSourceRefElement()
	 * @generated
	 */
	int SOURCE_REF_ELEMENT = 6;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_REF_ELEMENT__TITLE = ELEMENT__TITLE;

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
	 * The meta object id for the '{@link plweb.impl.AreaImpl <em>Area</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see plweb.impl.AreaImpl
	 * @see plweb.impl.PlwebPackageImpl#getArea()
	 * @generated
	 */
	int AREA = 1;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA__TITLE = SOURCE_REF_ELEMENT__TITLE;

	/**
	 * The feature id for the '<em><b>Class</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA__CLASS = SOURCE_REF_ELEMENT__CLASS;

	/**
	 * The number of structural features of the '<em>Area</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_FEATURE_COUNT = SOURCE_REF_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link plweb.impl.TargetRefElementImpl <em>Target Ref Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see plweb.impl.TargetRefElementImpl
	 * @see plweb.impl.PlwebPackageImpl#getTargetRefElement()
	 * @generated
	 */
	int TARGET_REF_ELEMENT = 7;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_REF_ELEMENT__TITLE = ELEMENT__TITLE;

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
	 * The meta object id for the '{@link plweb.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see plweb.impl.NodeImpl
	 * @see plweb.impl.PlwebPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 2;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__TITLE = TARGET_REF_ELEMENT__TITLE;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__PARENT = TARGET_REF_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__OPTIONAL = TARGET_REF_ELEMENT__OPTIONAL;

	/**
	 * The feature id for the '<em><b>Class</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__CLASS = TARGET_REF_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = TARGET_REF_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link plweb.impl.GroupImpl <em>Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see plweb.impl.GroupImpl
	 * @see plweb.impl.PlwebPackageImpl#getGroup()
	 * @generated
	 */
	int GROUP = 3;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__TITLE = SOURCE_REF_ELEMENT__TITLE;

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
	 * The meta object id for the '{@link plweb.impl.PageImpl <em>Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see plweb.impl.PageImpl
	 * @see plweb.impl.PlwebPackageImpl#getPage()
	 * @generated
	 */
	int PAGE = 4;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__TITLE = TARGET_REF_ELEMENT__TITLE;

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
	 * The meta object id for the '{@link plweb.GroupType <em>Group Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see plweb.GroupType
	 * @see plweb.impl.PlwebPackageImpl#getGroupType()
	 * @generated
	 */
	int GROUP_TYPE = 8;

	/**
	 * The meta object id for the '{@link plweb.DiagramType <em>Diagram Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see plweb.DiagramType
	 * @see plweb.impl.PlwebPackageImpl#getDiagramType()
	 * @generated
	 */
	int DIAGRAM_TYPE = 9;


	/**
	 * Returns the meta object for class '{@link plweb.DiagramRoot <em>Diagram Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Diagram Root</em>'.
	 * @see plweb.DiagramRoot
	 * @generated
	 */
	EClass getDiagramRoot();

	/**
	 * Returns the meta object for the containment reference '{@link plweb.DiagramRoot#getArea <em>Area</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Area</em>'.
	 * @see plweb.DiagramRoot#getArea()
	 * @see #getDiagramRoot()
	 * @generated
	 */
	EReference getDiagramRoot_Area();

	/**
	 * Returns the meta object for the attribute '{@link plweb.DiagramRoot#getProjectPath <em>Project Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Path</em>'.
	 * @see plweb.DiagramRoot#getProjectPath()
	 * @see #getDiagramRoot()
	 * @generated
	 */
	EAttribute getDiagramRoot_ProjectPath();

	/**
	 * Returns the meta object for the attribute '{@link plweb.DiagramRoot#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see plweb.DiagramRoot#getType()
	 * @see #getDiagramRoot()
	 * @generated
	 */
	EAttribute getDiagramRoot_Type();

	/**
	 * Returns the meta object for class '{@link plweb.Area <em>Area</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Area</em>'.
	 * @see plweb.Area
	 * @generated
	 */
	EClass getArea();

	/**
	 * Returns the meta object for class '{@link plweb.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see plweb.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for class '{@link plweb.Group <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group</em>'.
	 * @see plweb.Group
	 * @generated
	 */
	EClass getGroup();

	/**
	 * Returns the meta object for the attribute '{@link plweb.Group#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see plweb.Group#getType()
	 * @see #getGroup()
	 * @generated
	 */
	EAttribute getGroup_Type();

	/**
	 * Returns the meta object for class '{@link plweb.Page <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Page</em>'.
	 * @see plweb.Page
	 * @generated
	 */
	EClass getPage();

	/**
	 * Returns the meta object for the attribute '{@link plweb.Page#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see plweb.Page#getSource()
	 * @see #getPage()
	 * @generated
	 */
	EAttribute getPage_Source();

	/**
	 * Returns the meta object for class '{@link plweb.Element <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see plweb.Element
	 * @generated
	 */
	EClass getElement();

	/**
	 * Returns the meta object for the attribute '{@link plweb.Element#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see plweb.Element#getTitle()
	 * @see #getElement()
	 * @generated
	 */
	EAttribute getElement_Title();

	/**
	 * Returns the meta object for class '{@link plweb.SourceRefElement <em>Source Ref Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source Ref Element</em>'.
	 * @see plweb.SourceRefElement
	 * @generated
	 */
	EClass getSourceRefElement();

	/**
	 * Returns the meta object for the containment reference list '{@link plweb.SourceRefElement#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Class</em>'.
	 * @see plweb.SourceRefElement#getClass_()
	 * @see #getSourceRefElement()
	 * @generated
	 */
	EReference getSourceRefElement_Class();

	/**
	 * Returns the meta object for class '{@link plweb.TargetRefElement <em>Target Ref Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target Ref Element</em>'.
	 * @see plweb.TargetRefElement
	 * @generated
	 */
	EClass getTargetRefElement();

	/**
	 * Returns the meta object for the reference '{@link plweb.TargetRefElement#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see plweb.TargetRefElement#getParent()
	 * @see #getTargetRefElement()
	 * @generated
	 */
	EReference getTargetRefElement_Parent();

	/**
	 * Returns the meta object for the attribute '{@link plweb.TargetRefElement#isOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see plweb.TargetRefElement#isOptional()
	 * @see #getTargetRefElement()
	 * @generated
	 */
	EAttribute getTargetRefElement_Optional();

	/**
	 * Returns the meta object for enum '{@link plweb.GroupType <em>Group Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Group Type</em>'.
	 * @see plweb.GroupType
	 * @generated
	 */
	EEnum getGroupType();

	/**
	 * Returns the meta object for enum '{@link plweb.DiagramType <em>Diagram Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Diagram Type</em>'.
	 * @see plweb.DiagramType
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
		 * The meta object literal for the '{@link plweb.impl.DiagramRootImpl <em>Diagram Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see plweb.impl.DiagramRootImpl
		 * @see plweb.impl.PlwebPackageImpl#getDiagramRoot()
		 * @generated
		 */
		EClass DIAGRAM_ROOT = eINSTANCE.getDiagramRoot();

		/**
		 * The meta object literal for the '<em><b>Area</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIAGRAM_ROOT__AREA = eINSTANCE.getDiagramRoot_Area();

		/**
		 * The meta object literal for the '<em><b>Project Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM_ROOT__PROJECT_PATH = eINSTANCE.getDiagramRoot_ProjectPath();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM_ROOT__TYPE = eINSTANCE.getDiagramRoot_Type();

		/**
		 * The meta object literal for the '{@link plweb.impl.AreaImpl <em>Area</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see plweb.impl.AreaImpl
		 * @see plweb.impl.PlwebPackageImpl#getArea()
		 * @generated
		 */
		EClass AREA = eINSTANCE.getArea();

		/**
		 * The meta object literal for the '{@link plweb.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see plweb.impl.NodeImpl
		 * @see plweb.impl.PlwebPackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '{@link plweb.impl.GroupImpl <em>Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see plweb.impl.GroupImpl
		 * @see plweb.impl.PlwebPackageImpl#getGroup()
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
		 * The meta object literal for the '{@link plweb.impl.PageImpl <em>Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see plweb.impl.PageImpl
		 * @see plweb.impl.PlwebPackageImpl#getPage()
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
		 * The meta object literal for the '{@link plweb.impl.ElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see plweb.impl.ElementImpl
		 * @see plweb.impl.PlwebPackageImpl#getElement()
		 * @generated
		 */
		EClass ELEMENT = eINSTANCE.getElement();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT__TITLE = eINSTANCE.getElement_Title();

		/**
		 * The meta object literal for the '{@link plweb.impl.SourceRefElementImpl <em>Source Ref Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see plweb.impl.SourceRefElementImpl
		 * @see plweb.impl.PlwebPackageImpl#getSourceRefElement()
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
		 * The meta object literal for the '{@link plweb.impl.TargetRefElementImpl <em>Target Ref Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see plweb.impl.TargetRefElementImpl
		 * @see plweb.impl.PlwebPackageImpl#getTargetRefElement()
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
		 * The meta object literal for the '{@link plweb.GroupType <em>Group Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see plweb.GroupType
		 * @see plweb.impl.PlwebPackageImpl#getGroupType()
		 * @generated
		 */
		EEnum GROUP_TYPE = eINSTANCE.getGroupType();

		/**
		 * The meta object literal for the '{@link plweb.DiagramType <em>Diagram Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see plweb.DiagramType
		 * @see plweb.impl.PlwebPackageImpl#getDiagramType()
		 * @generated
		 */
		EEnum DIAGRAM_TYPE = eINSTANCE.getDiagramType();

	}

} //PlwebPackage
