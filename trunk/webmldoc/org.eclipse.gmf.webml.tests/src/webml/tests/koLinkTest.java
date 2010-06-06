/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package webml.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import webml.WebmlFactory;
import webml.koLink;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>ko Link</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class koLinkTest extends TestCase {

	/**
	 * The fixture for this ko Link test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected koLink fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(koLinkTest.class);
	}

	/**
	 * Constructs a new ko Link test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public koLinkTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this ko Link test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(koLink fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this ko Link test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected koLink getFixture() {
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
		setFixture(WebmlFactory.eINSTANCE.createkoLink());
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

} //koLinkTest
