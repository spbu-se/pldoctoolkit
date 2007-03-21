/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.drlvisual.tests;

import junit.textui.TestRunner;

import org.spbu.pldoctoolkit.drlvisual.Product;
import org.spbu.pldoctoolkit.drlvisual.drlFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Product</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProductTest extends DrlElementTest {
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
		TestRunner.run(ProductTest.class);
	}

	/**
	 * Constructs a new Product test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProductTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Product test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private Product getFixture() {
		return (Product)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(drlFactory.eINSTANCE.createProduct());
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

} //ProductTest
