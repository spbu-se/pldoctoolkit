/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.drlvisual;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Schema</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.Schema#getParts <em>Parts</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.drlvisual.drlPackage#getSchema()
 * @model
 * @generated
 */
public interface Schema extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * Returns the value of the '<em><b>Parts</b></em>' containment reference list.
	 * The list contents are of type {@link org.spbu.pldoctoolkit.drlvisual.GenericDocumentPart}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parts</em>' containment reference list.
	 * @see org.spbu.pldoctoolkit.drlvisual.drlPackage#getSchema_Parts()
	 * @model type="org.spbu.pldoctoolkit.drlvisual.GenericDocumentPart" containment="true"
	 * @generated
	 */
	EList getParts();

} // Schema