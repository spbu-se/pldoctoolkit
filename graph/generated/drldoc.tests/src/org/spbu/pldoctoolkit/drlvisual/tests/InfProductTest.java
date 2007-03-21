/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.drlvisual.tests;

import junit.textui.TestRunner;

import org.spbu.pldoctoolkit.drlvisual.InfProduct;
import org.spbu.pldoctoolkit.drlvisual.drlFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Inf Product</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class InfProductTest extends GenericDocumentPartTest {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InfProductTest.class);
	}

	/**
	 * Constructs a new Inf Product test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfProductTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Inf Product test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private InfProduct getFixture() {
		return (InfProduct)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(drlFactory.eINSTANCE.createInfProduct());
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

} //InfProductTest
