/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inf Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.InfElement#getNestPoints <em>Nest Points</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getInfElement()
 * @model
 * @generated
 */
public interface InfElement extends GenericDocumentPart {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * Returns the value of the '<em><b>Nest Points</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nest Points</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nest Points</em>' containment reference.
	 * @see #setNestPoints(NestPoint)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getInfElement_NestPoints()
	 * @model containment="true"
	 * @generated
	 */
	NestPoint getNestPoints();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.InfElement#getNestPoints <em>Nest Points</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nest Points</em>' containment reference.
	 * @see #getNestPoints()
	 * @generated
	 */
	void setNestPoints(NestPoint value);

} // InfElement