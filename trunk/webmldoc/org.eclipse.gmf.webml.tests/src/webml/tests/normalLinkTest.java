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
import webml.normalLink;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>normal Link</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class normalLinkTest extends TestCase {

	/**
	 * The fixture for this normal Link test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected normalLink fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(normalLinkTest.class);
	}

	/**
	 * Constructs a new normal Link test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public normalLinkTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this normal Link test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(normalLink fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this normal Link test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected normalLink getFixture() {
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
		setFixture(WebmlFactory.eINSTANCE.createnormalLink());
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

} //normalLinkTest
