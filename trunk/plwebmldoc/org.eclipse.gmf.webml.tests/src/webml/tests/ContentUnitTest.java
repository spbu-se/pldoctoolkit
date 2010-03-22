/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package webml.tests;

import junit.textui.TestRunner;

import webml.ContentUnit;
import webml.WebmlFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Content Unit</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ContentUnitTest extends UnitTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ContentUnitTest.class);
	}

	/**
	 * Constructs a new Content Unit test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContentUnitTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Content Unit test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ContentUnit getFixture() {
		return (ContentUnit)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(WebmlFactory.eINSTANCE.createContentUnit());
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

} //ContentUnitTest
