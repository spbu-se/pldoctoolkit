/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package webml;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see webml.WebmlFactory
 * @model kind="package"
 *        annotation="gmf foo='bar'"
 * @generated
 */
public interface WebmlPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "webml";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "webml";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "webml";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	WebmlPackage eINSTANCE = webml.impl.WebmlPackageImpl.init();

	/**
	 * The meta object id for the '{@link webml.impl.SiteviewImpl <em>Siteview</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see webml.impl.SiteviewImpl
	 * @see webml.impl.WebmlPackageImpl#getSiteview()
	 * @generated
	 */
	int SITEVIEW = 0;

	/**
	 * The feature id for the '<em><b>Element</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITEVIEW__ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Oklink</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITEVIEW__OKLINK = 1;

	/**
	 * The feature id for the '<em><b>Kolink</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITEVIEW__KOLINK = 2;

	/**
	 * The feature id for the '<em><b>Nlink</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITEVIEW__NLINK = 3;

	/**
	 * The feature id for the '<em><b>Tlink</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITEVIEW__TLINK = 4;

	/**
	 * The number of structural features of the '<em>Siteview</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SITEVIEW_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link webml.impl.UnitImpl <em>Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see webml.impl.UnitImpl
	 * @see webml.impl.WebmlPackageImpl#getUnit()
	 * @generated
	 */
	int UNIT = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT__ID = 1;

	/**
	 * The number of structural features of the '<em>Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link webml.impl.ContentUnitImpl <em>Content Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see webml.impl.ContentUnitImpl
	 * @see webml.impl.WebmlPackageImpl#getContentUnit()
	 * @generated
	 */
	int CONTENT_UNIT = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_UNIT__NAME = UNIT__NAME;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_UNIT__ID = UNIT__ID;

	/**
	 * The feature id for the '<em><b>Topic</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_UNIT__TOPIC = UNIT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Content Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_UNIT_FEATURE_COUNT = UNIT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link webml.impl.PageImpl <em>Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see webml.impl.PageImpl
	 * @see webml.impl.WebmlPackageImpl#getPage()
	 * @generated
	 */
	int PAGE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__NAME = CONTENT_UNIT__NAME;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__ID = CONTENT_UNIT__ID;

	/**
	 * The feature id for the '<em><b>Topic</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__TOPIC = CONTENT_UNIT__TOPIC;

	/**
	 * The feature id for the '<em><b>Element</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__ELEMENT = CONTENT_UNIT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_FEATURE_COUNT = CONTENT_UNIT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link webml.impl.AreaImpl <em>Area</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see webml.impl.AreaImpl
	 * @see webml.impl.WebmlPackageImpl#getArea()
	 * @generated
	 */
	int AREA = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA__NAME = PAGE__NAME;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA__ID = PAGE__ID;

	/**
	 * The feature id for the '<em><b>Topic</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA__TOPIC = PAGE__TOPIC;

	/**
	 * The feature id for the '<em><b>Element</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA__ELEMENT = PAGE__ELEMENT;

	/**
	 * The number of structural features of the '<em>Area</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AREA_FEATURE_COUNT = PAGE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link webml.impl.okLinkImpl <em>ok Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see webml.impl.okLinkImpl
	 * @see webml.impl.WebmlPackageImpl#getokLink()
	 * @generated
	 */
	int OK_LINK = 3;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OK_LINK__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OK_LINK__TARGET = 1;

	/**
	 * The number of structural features of the '<em>ok Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OK_LINK_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link webml.impl.koLinkImpl <em>ko Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see webml.impl.koLinkImpl
	 * @see webml.impl.WebmlPackageImpl#getkoLink()
	 * @generated
	 */
	int KO_LINK = 4;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KO_LINK__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KO_LINK__TARGET = 1;

	/**
	 * The number of structural features of the '<em>ko Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KO_LINK_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link webml.impl.normalLinkImpl <em>normal Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see webml.impl.normalLinkImpl
	 * @see webml.impl.WebmlPackageImpl#getnormalLink()
	 * @generated
	 */
	int NORMAL_LINK = 5;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_LINK__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_LINK__TARGET = 1;

	/**
	 * The number of structural features of the '<em>normal Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_LINK_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link webml.impl.transportLinkImpl <em>transport Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see webml.impl.transportLinkImpl
	 * @see webml.impl.WebmlPackageImpl#gettransportLink()
	 * @generated
	 */
	int TRANSPORT_LINK = 6;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSPORT_LINK__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSPORT_LINK__TARGET = 1;

	/**
	 * The number of structural features of the '<em>transport Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSPORT_LINK_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link webml.impl.DocTopicImpl <em>Doc Topic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see webml.impl.DocTopicImpl
	 * @see webml.impl.WebmlPackageImpl#getDocTopic()
	 * @generated
	 */
	int DOC_TOPIC = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOC_TOPIC__NAME = 0;

	/**
	 * The number of structural features of the '<em>Doc Topic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOC_TOPIC_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link webml.impl.OperationUnitImpl <em>Operation Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see webml.impl.OperationUnitImpl
	 * @see webml.impl.WebmlPackageImpl#getOperationUnit()
	 * @generated
	 */
	int OPERATION_UNIT = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_UNIT__NAME = UNIT__NAME;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_UNIT__ID = UNIT__ID;

	/**
	 * The number of structural features of the '<em>Operation Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_UNIT_FEATURE_COUNT = UNIT_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link webml.Siteview <em>Siteview</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Siteview</em>'.
	 * @see webml.Siteview
	 * @generated
	 */
	EClass getSiteview();

	/**
	 * Returns the meta object for the containment reference list '{@link webml.Siteview#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Element</em>'.
	 * @see webml.Siteview#getElement()
	 * @see #getSiteview()
	 * @generated
	 */
	EReference getSiteview_Element();

	/**
	 * Returns the meta object for the containment reference list '{@link webml.Siteview#getOklink <em>Oklink</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Oklink</em>'.
	 * @see webml.Siteview#getOklink()
	 * @see #getSiteview()
	 * @generated
	 */
	EReference getSiteview_Oklink();

	/**
	 * Returns the meta object for the containment reference list '{@link webml.Siteview#getKolink <em>Kolink</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Kolink</em>'.
	 * @see webml.Siteview#getKolink()
	 * @see #getSiteview()
	 * @generated
	 */
	EReference getSiteview_Kolink();

	/**
	 * Returns the meta object for the containment reference list '{@link webml.Siteview#getNlink <em>Nlink</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nlink</em>'.
	 * @see webml.Siteview#getNlink()
	 * @see #getSiteview()
	 * @generated
	 */
	EReference getSiteview_Nlink();

	/**
	 * Returns the meta object for the containment reference list '{@link webml.Siteview#getTlink <em>Tlink</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tlink</em>'.
	 * @see webml.Siteview#getTlink()
	 * @see #getSiteview()
	 * @generated
	 */
	EReference getSiteview_Tlink();

	/**
	 * Returns the meta object for class '{@link webml.Area <em>Area</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Area</em>'.
	 * @see webml.Area
	 * @generated
	 */
	EClass getArea();

	/**
	 * Returns the meta object for class '{@link webml.Page <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Page</em>'.
	 * @see webml.Page
	 * @generated
	 */
	EClass getPage();

	/**
	 * Returns the meta object for the containment reference list '{@link webml.Page#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Element</em>'.
	 * @see webml.Page#getElement()
	 * @see #getPage()
	 * @generated
	 */
	EReference getPage_Element();

	/**
	 * Returns the meta object for class '{@link webml.okLink <em>ok Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ok Link</em>'.
	 * @see webml.okLink
	 * @generated
	 */
	EClass getokLink();

	/**
	 * Returns the meta object for the reference '{@link webml.okLink#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see webml.okLink#getSource()
	 * @see #getokLink()
	 * @generated
	 */
	EReference getokLink_Source();

	/**
	 * Returns the meta object for the reference '{@link webml.okLink#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see webml.okLink#getTarget()
	 * @see #getokLink()
	 * @generated
	 */
	EReference getokLink_Target();

	/**
	 * Returns the meta object for class '{@link webml.koLink <em>ko Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ko Link</em>'.
	 * @see webml.koLink
	 * @generated
	 */
	EClass getkoLink();

	/**
	 * Returns the meta object for the reference '{@link webml.koLink#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see webml.koLink#getSource()
	 * @see #getkoLink()
	 * @generated
	 */
	EReference getkoLink_Source();

	/**
	 * Returns the meta object for the reference '{@link webml.koLink#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see webml.koLink#getTarget()
	 * @see #getkoLink()
	 * @generated
	 */
	EReference getkoLink_Target();

	/**
	 * Returns the meta object for class '{@link webml.normalLink <em>normal Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>normal Link</em>'.
	 * @see webml.normalLink
	 * @generated
	 */
	EClass getnormalLink();

	/**
	 * Returns the meta object for the reference '{@link webml.normalLink#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see webml.normalLink#getSource()
	 * @see #getnormalLink()
	 * @generated
	 */
	EReference getnormalLink_Source();

	/**
	 * Returns the meta object for the reference '{@link webml.normalLink#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see webml.normalLink#getTarget()
	 * @see #getnormalLink()
	 * @generated
	 */
	EReference getnormalLink_Target();

	/**
	 * Returns the meta object for class '{@link webml.transportLink <em>transport Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>transport Link</em>'.
	 * @see webml.transportLink
	 * @generated
	 */
	EClass gettransportLink();

	/**
	 * Returns the meta object for the reference '{@link webml.transportLink#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see webml.transportLink#getSource()
	 * @see #gettransportLink()
	 * @generated
	 */
	EReference gettransportLink_Source();

	/**
	 * Returns the meta object for the reference '{@link webml.transportLink#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see webml.transportLink#getTarget()
	 * @see #gettransportLink()
	 * @generated
	 */
	EReference gettransportLink_Target();

	/**
	 * Returns the meta object for class '{@link webml.DocTopic <em>Doc Topic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Doc Topic</em>'.
	 * @see webml.DocTopic
	 * @generated
	 */
	EClass getDocTopic();

	/**
	 * Returns the meta object for the attribute '{@link webml.DocTopic#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see webml.DocTopic#getName()
	 * @see #getDocTopic()
	 * @generated
	 */
	EAttribute getDocTopic_Name();

	/**
	 * Returns the meta object for class '{@link webml.ContentUnit <em>Content Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Content Unit</em>'.
	 * @see webml.ContentUnit
	 * @generated
	 */
	EClass getContentUnit();

	/**
	 * Returns the meta object for the containment reference list '{@link webml.ContentUnit#getTopic <em>Topic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Topic</em>'.
	 * @see webml.ContentUnit#getTopic()
	 * @see #getContentUnit()
	 * @generated
	 */
	EReference getContentUnit_Topic();

	/**
	 * Returns the meta object for class '{@link webml.OperationUnit <em>Operation Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation Unit</em>'.
	 * @see webml.OperationUnit
	 * @generated
	 */
	EClass getOperationUnit();

	/**
	 * Returns the meta object for class '{@link webml.Unit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unit</em>'.
	 * @see webml.Unit
	 * @generated
	 */
	EClass getUnit();

	/**
	 * Returns the meta object for the attribute '{@link webml.Unit#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see webml.Unit#getName()
	 * @see #getUnit()
	 * @generated
	 */
	EAttribute getUnit_Name();

	/**
	 * Returns the meta object for the attribute '{@link webml.Unit#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see webml.Unit#getId()
	 * @see #getUnit()
	 * @generated
	 */
	EAttribute getUnit_Id();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	WebmlFactory getWebmlFactory();

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
		 * The meta object literal for the '{@link webml.impl.SiteviewImpl <em>Siteview</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see webml.impl.SiteviewImpl
		 * @see webml.impl.WebmlPackageImpl#getSiteview()
		 * @generated
		 */
		EClass SITEVIEW = eINSTANCE.getSiteview();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITEVIEW__ELEMENT = eINSTANCE.getSiteview_Element();

		/**
		 * The meta object literal for the '<em><b>Oklink</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITEVIEW__OKLINK = eINSTANCE.getSiteview_Oklink();

		/**
		 * The meta object literal for the '<em><b>Kolink</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITEVIEW__KOLINK = eINSTANCE.getSiteview_Kolink();

		/**
		 * The meta object literal for the '<em><b>Nlink</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITEVIEW__NLINK = eINSTANCE.getSiteview_Nlink();

		/**
		 * The meta object literal for the '<em><b>Tlink</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SITEVIEW__TLINK = eINSTANCE.getSiteview_Tlink();

		/**
		 * The meta object literal for the '{@link webml.impl.AreaImpl <em>Area</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see webml.impl.AreaImpl
		 * @see webml.impl.WebmlPackageImpl#getArea()
		 * @generated
		 */
		EClass AREA = eINSTANCE.getArea();

		/**
		 * The meta object literal for the '{@link webml.impl.PageImpl <em>Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see webml.impl.PageImpl
		 * @see webml.impl.WebmlPackageImpl#getPage()
		 * @generated
		 */
		EClass PAGE = eINSTANCE.getPage();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAGE__ELEMENT = eINSTANCE.getPage_Element();

		/**
		 * The meta object literal for the '{@link webml.impl.okLinkImpl <em>ok Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see webml.impl.okLinkImpl
		 * @see webml.impl.WebmlPackageImpl#getokLink()
		 * @generated
		 */
		EClass OK_LINK = eINSTANCE.getokLink();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OK_LINK__SOURCE = eINSTANCE.getokLink_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OK_LINK__TARGET = eINSTANCE.getokLink_Target();

		/**
		 * The meta object literal for the '{@link webml.impl.koLinkImpl <em>ko Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see webml.impl.koLinkImpl
		 * @see webml.impl.WebmlPackageImpl#getkoLink()
		 * @generated
		 */
		EClass KO_LINK = eINSTANCE.getkoLink();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference KO_LINK__SOURCE = eINSTANCE.getkoLink_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference KO_LINK__TARGET = eINSTANCE.getkoLink_Target();

		/**
		 * The meta object literal for the '{@link webml.impl.normalLinkImpl <em>normal Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see webml.impl.normalLinkImpl
		 * @see webml.impl.WebmlPackageImpl#getnormalLink()
		 * @generated
		 */
		EClass NORMAL_LINK = eINSTANCE.getnormalLink();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NORMAL_LINK__SOURCE = eINSTANCE.getnormalLink_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NORMAL_LINK__TARGET = eINSTANCE.getnormalLink_Target();

		/**
		 * The meta object literal for the '{@link webml.impl.transportLinkImpl <em>transport Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see webml.impl.transportLinkImpl
		 * @see webml.impl.WebmlPackageImpl#gettransportLink()
		 * @generated
		 */
		EClass TRANSPORT_LINK = eINSTANCE.gettransportLink();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSPORT_LINK__SOURCE = eINSTANCE.gettransportLink_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSPORT_LINK__TARGET = eINSTANCE.gettransportLink_Target();

		/**
		 * The meta object literal for the '{@link webml.impl.DocTopicImpl <em>Doc Topic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see webml.impl.DocTopicImpl
		 * @see webml.impl.WebmlPackageImpl#getDocTopic()
		 * @generated
		 */
		EClass DOC_TOPIC = eINSTANCE.getDocTopic();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOC_TOPIC__NAME = eINSTANCE.getDocTopic_Name();

		/**
		 * The meta object literal for the '{@link webml.impl.ContentUnitImpl <em>Content Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see webml.impl.ContentUnitImpl
		 * @see webml.impl.WebmlPackageImpl#getContentUnit()
		 * @generated
		 */
		EClass CONTENT_UNIT = eINSTANCE.getContentUnit();

		/**
		 * The meta object literal for the '<em><b>Topic</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTENT_UNIT__TOPIC = eINSTANCE.getContentUnit_Topic();

		/**
		 * The meta object literal for the '{@link webml.impl.OperationUnitImpl <em>Operation Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see webml.impl.OperationUnitImpl
		 * @see webml.impl.WebmlPackageImpl#getOperationUnit()
		 * @generated
		 */
		EClass OPERATION_UNIT = eINSTANCE.getOperationUnit();

		/**
		 * The meta object literal for the '{@link webml.impl.UnitImpl <em>Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see webml.impl.UnitImpl
		 * @see webml.impl.WebmlPackageImpl#getUnit()
		 * @generated
		 */
		EClass UNIT = eINSTANCE.getUnit();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNIT__NAME = eINSTANCE.getUnit_Name();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNIT__ID = eINSTANCE.getUnit_Id();

	}

} //WebmlPackage
