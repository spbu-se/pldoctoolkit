/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Nest Point</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.NestPoint#getId <em>Id</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.NestPoint#getDescr <em>Descr</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getNestPoint()
 * @model
 * @generated
 */
public interface NestPoint extends DrlElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getNestPoint_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.NestPoint#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Descr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Descr</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Descr</em>' attribute.
	 * @see #setDescr(String)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getNestPoint_Descr()
	 * @model
	 * @generated
	 */
	String getDescr();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.NestPoint#getDescr <em>Descr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Descr</em>' attribute.
	 * @see #getDescr()
	 * @generated
	 */
	void setDescr(String value);

} // NestPoint