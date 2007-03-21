/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.drlvisual.tests;

import junit.textui.TestRunner;

import org.spbu.pldoctoolkit.drlvisual.ProductDocumentation;
import org.spbu.pldoctoolkit.drlvisual.drlFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Product Documentation</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProductDocumentationTest extends DrlElementTest {
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
		TestRunner.run(ProductDocumentationTest.class);
	}

	/**
	 * Constructs a new Product Documentation test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProductDocumentationTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Product Documentation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ProductDocumentation getFixture() {
		return (ProductDocumentation)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(drlFactory.eINSTANCE.createProductDocumentation());
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

} //ProductDocumentationTest
