/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.drlvisual;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Nest Point</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.NestPoint#getName <em>Name</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.NestPoint#getDescr <em>Descr</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.drlvisual.drlPackage#getNestPoint()
 * @model
 * @generated
 */
public interface NestPoint extends InnerElement, SubelementedElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.spbu.pldoctoolkit.drlvisual.drlPackage#getNestPoint_Name()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.drlvisual.NestPoint#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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
	 * @see org.spbu.pldoctoolkit.drlvisual.drlPackage#getNestPoint_Descr()
	 * @model
	 * @generated
	 */
	String getDescr();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.drlvisual.NestPoint#getDescr <em>Descr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Descr</em>' attribute.
	 * @see #getDescr()
	 * @generated
	 */
	void setDescr(String value);

} // NestPoint