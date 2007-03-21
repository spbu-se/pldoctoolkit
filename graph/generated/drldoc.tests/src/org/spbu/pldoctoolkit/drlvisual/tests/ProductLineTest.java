/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.drlvisual.tests;

import junit.textui.TestRunner;

import org.spbu.pldoctoolkit.drlvisual.ProductLine;
import org.spbu.pldoctoolkit.drlvisual.drlFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Product Line</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProductLineTest extends DrlElementTest {
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
		TestRunner.run(ProductLineTest.class);
	}

	/**
	 * Constructs a new Product Line test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProductLineTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Product Line test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ProductLine getFixture() {
		return (ProductLine)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(drlFactory.eINSTANCE.createProductLine());
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

} //ProductLineTest
