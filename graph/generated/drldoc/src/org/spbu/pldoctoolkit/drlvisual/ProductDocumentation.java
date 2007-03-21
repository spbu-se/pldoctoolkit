/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.drlvisual;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Product Documentation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.ProductDocumentation#getProductId <em>Product Id</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.ProductDocumentation#getFinalInfProducts <em>Final Inf Products</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.drlvisual.drlPackage#getProductDocumentation()
 * @model
 * @generated
 */
public interface ProductDocumentation extends DrlElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * Returns the value of the '<em><b>Product Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Product Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Product Id</em>' attribute.
	 * @see #setProductId(String)
	 * @see org.spbu.pldoctoolkit.drlvisual.drlPackage#getProductDocumentation_ProductId()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getProductId();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.drlvisual.ProductDocumentation#getProductId <em>Product Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Product Id</em>' attribute.
	 * @see #getProductId()
	 * @generated
	 */
	void setProductId(String value);

	/**
	 * Returns the value of the '<em><b>Final Inf Products</b></em>' containment reference list.
	 * The list contents are of type {@link org.spbu.pldoctoolkit.drlvisual.FinalInfProduct}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Final Inf Products</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Final Inf Products</em>' containment reference list.
	 * @see org.spbu.pldoctoolkit.drlvisual.drlPackage#getProductDocumentation_FinalInfProducts()
	 * @model type="org.spbu.pldoctoolkit.drlvisual.FinalInfProduct" containment="true"
	 * @generated
	 */
	EList getFinalInfProducts();

} // ProductDocumentation