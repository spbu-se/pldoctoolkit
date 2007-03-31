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
 *   <li>{@link org.spbu.pldoctoolkit.graph.InfElement#getOwnerInfElemRef <em>Owner Inf Elem Ref</em>}</li>
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

	/**
	 * Returns the value of the '<em><b>Owner Inf Elem Ref</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.spbu.pldoctoolkit.graph.InfElemRef#getInfelem <em>Infelem</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner Inf Elem Ref</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner Inf Elem Ref</em>' reference.
	 * @see #setOwnerInfElemRef(InfElemRef)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getInfElement_OwnerInfElemRef()
	 * @see org.spbu.pldoctoolkit.graph.InfElemRef#getInfelem
	 * @model opposite="infelem" transient="true" derived="true"
	 * @generated
	 */
	InfElemRef getOwnerInfElemRef();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.InfElement#getOwnerInfElemRef <em>Owner Inf Elem Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner Inf Elem Ref</em>' reference.
	 * @see #getOwnerInfElemRef()
	 * @generated
	 */
	void setOwnerInfElemRef(InfElemRef value);

} // InfElement