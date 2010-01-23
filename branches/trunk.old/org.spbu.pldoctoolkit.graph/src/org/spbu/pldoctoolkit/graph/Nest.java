/**
 * copyleft 2007
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Nest</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.Nest#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getNest()
 * @model
 * @generated
 */
public interface Nest extends DrlElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.spbu.pldoctoolkit.graph.NestType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.spbu.pldoctoolkit.graph.NestType
	 * @see #setType(NestType)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getNest_Type()
	 * @model
	 * @generated
	 */
	NestType getType();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.Nest#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.spbu.pldoctoolkit.graph.NestType
	 * @see #getType()
	 * @generated
	 */
	void setType(NestType value);

} // Nest
