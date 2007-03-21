/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.drlvisual.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.spbu.pldoctoolkit.drlvisual.Schema;
import org.spbu.pldoctoolkit.drlvisual.drlFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Schema</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class SchemaTest extends TestCase {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * The fixture for this Schema test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Schema fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SchemaTest.class);
	}

	/**
	 * Constructs a new Schema test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SchemaTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Schema test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Schema fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Schema test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private Schema getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(drlFactory.eINSTANCE.createSchema());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //SchemaTest
