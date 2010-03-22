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
import webml.transportLink;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>transport Link</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class transportLinkTest extends TestCase {

	/**
	 * The fixture for this transport Link test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected transportLink fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(transportLinkTest.class);
	}

	/**
	 * Constructs a new transport Link test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public transportLinkTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this transport Link test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(transportLink fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this transport Link test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected transportLink getFixture() {
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
		setFixture(WebmlFactory.eINSTANCE.createtransportLink());
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

} //transportLinkTest
