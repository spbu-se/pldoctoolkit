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
 * A representation of the model object '<em><b>PL Scheme</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.PLScheme#getProducts <em>Products</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.drlvisual.drlPackage#getPLScheme()
 * @model
 * @generated
 */
public interface PLScheme extends DrlElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * Returns the value of the '<em><b>Products</b></em>' containment reference list.
	 * The list contents are of type {@link org.spbu.pldoctoolkit.drlvisual.Product}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Products</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Products</em>' containment reference list.
	 * @see org.spbu.pldoctoolkit.drlvisual.drlPackage#getPLScheme_Products()
	 * @model type="org.spbu.pldoctoolkit.drlvisual.Product" containment="true"
	 * @generated
	 */
	EList getProducts();

} // PLScheme