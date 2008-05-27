/**
 * copyleft 2007
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Adapter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.Adapter#getNests <em>Nests</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.Adapter#getInfelemrefid <em>Infelemrefid</em>}</li>
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
	 * Returns the value of the '<em><b>Nests</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nests</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nests</em>' containment reference.
	 * @see #setNests(Nest)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getAdapter_Nests()
	 * @model containment="true"
	 * @generated
	 */
	Nest getNests();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.Adapter#getNests <em>Nests</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nests</em>' containment reference.
	 * @see #getNests()
	 * @generated
	 */
	void setNests(Nest value);

	/**
	 * Returns the value of the '<em><b>Infelemrefid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Infelemrefid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Infelemrefid</em>' attribute.
	 * @see #setInfelemrefid(String)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getAdapter_Infelemrefid()
	 * @model required="true"
	 * @generated
	 */
	String getInfelemrefid();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.Adapter#getInfelemrefid <em>Infelemrefid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Infelemrefid</em>' attribute.
	 * @see #getInfelemrefid()
	 * @generated
	 */
	void setInfelemrefid(String value);

} // Adapter
