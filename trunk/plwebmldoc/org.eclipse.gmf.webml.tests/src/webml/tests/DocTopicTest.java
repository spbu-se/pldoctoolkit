/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package webml.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import webml.DocTopic;
import webml.WebmlFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Doc Topic</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class DocTopicTest extends TestCase {

	/**
	 * The fixture for this Doc Topic test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocTopic fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(DocTopicTest.class);
	}

	/**
	 * Constructs a new Doc Topic test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocTopicTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Doc Topic test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(DocTopic fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Doc Topic test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocTopic getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(WebmlFactory.eINSTANCE.createDocTopic());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //DocTopicTest
