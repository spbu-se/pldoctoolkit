/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Product Documentation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.ProductDocumentation#getFinalInfProducts <em>Final Inf Products</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.ProductDocumentation#getProduct <em>Product</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getProductDocumentation()
 * @model
 * @generated
 */
public interface ProductDocumentation extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * Returns the value of the '<em><b>Final Inf Products</b></em>' containment reference list.
	 * The list contents are of type {@link org.spbu.pldoctoolkit.graph.FinalInfProduct}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Final Inf Products</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Final Inf Products</em>' containment reference list.
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getProductDocumentation_FinalInfProducts()
	 * @model type="org.spbu.pldoctoolkit.graph.FinalInfProduct" containment="true"
	 * @generated
	 */
	EList getFinalInfProducts();

	/**
	 * Returns the value of the '<em><b>Product</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Product</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Product</em>' reference.
	 * @see #setProduct(Product)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getProductDocumentation_Product()
	 * @model
	 * @generated
	 */
	Product getProduct();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.ProductDocumentation#getProduct <em>Product</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Product</em>' reference.
	 * @see #getProduct()
	 * @generated
	 */
	void setProduct(Product value);

} // ProductDocumentation