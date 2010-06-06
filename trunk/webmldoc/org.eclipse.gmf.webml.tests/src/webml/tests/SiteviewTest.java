/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package webml.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import webml.Siteview;
import webml.WebmlFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Siteview</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class SiteviewTest extends TestCase {

	/**
	 * The fixture for this Siteview test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Siteview fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SiteviewTest.class);
	}

	/**
	 * Constructs a new Siteview test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SiteviewTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Siteview test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Siteview fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Siteview test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Siteview getFixture() {
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
		setFixture(WebmlFactory.eINSTANCE.createSiteview());
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

} //SiteviewTest
