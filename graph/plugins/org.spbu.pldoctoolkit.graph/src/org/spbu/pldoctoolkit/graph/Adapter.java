/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Adapter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.Adapter#getNestPointRefs <em>Nest Point Refs</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.Adapter#getInfElemRef <em>Inf Elem Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getAdapter()
 * @model
 * @generated
 */
public interface Adapter extends DrlElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * Returns the value of the '<em><b>Nest Point Refs</b></em>' containment reference list.
	 * The list contents are of type {@link org.spbu.pldoctoolkit.graph.NestPointRef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nest Point Refs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nest Point Refs</em>' containment reference list.
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getAdapter_NestPointRefs()
	 * @model type="org.spbu.pldoctoolkit.graph.NestPointRef" containment="true"
	 * @generated
	 */
	EList getNestPointRefs();

	/**
	 * Returns the value of the '<em><b>Inf Elem Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inf Elem Ref</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inf Elem Ref</em>' reference.
	 * @see #setInfElemRef(InfElemRef)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getAdapter_InfElemRef()
	 * @model required="true"
	 * @generated
	 */
	InfElemRef getInfElemRef();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.Adapter#getInfElemRef <em>Inf Elem Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inf Elem Ref</em>' reference.
	 * @see #getInfElemRef()
	 * @generated
	 */
	void setInfElemRef(InfElemRef value);

} // Adapter