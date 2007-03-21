/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.drlvisual;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Nest Point Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.NestPointRef#getType <em>Type</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.NestPointRef#getText <em>Text</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.NestPointRef#getNestPoint <em>Nest Point</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.drlvisual.drlPackage#getNestPointRef()
 * @model
 * @generated
 */
public interface NestPointRef extends DrlElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link org.spbu.pldoctoolkit.drlvisual.NestPointType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.spbu.pldoctoolkit.drlvisual.NestPointType
	 * @see #setType(NestPointType)
	 * @see org.spbu.pldoctoolkit.drlvisual.drlPackage#getNestPointRef_Type()
	 * @model default="" required="true"
	 * @generated
	 */
	NestPointType getType();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.drlvisual.NestPointRef#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.spbu.pldoctoolkit.drlvisual.NestPointType
	 * @see #getType()
	 * @generated
	 */
	void setType(NestPointType value);

	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see org.spbu.pldoctoolkit.drlvisual.drlPackage#getNestPointRef_Text()
	 * @model required="true"
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.drlvisual.NestPointRef#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);

	/**
	 * Returns the value of the '<em><b>Nest Point</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nest Point</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nest Point</em>' reference.
	 * @see #setNestPoint(NestPoint)
	 * @see org.spbu.pldoctoolkit.drlvisual.drlPackage#getNestPointRef_NestPoint()
	 * @model required="true"
	 * @generated
	 */
	NestPoint getNestPoint();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.drlvisual.NestPointRef#getNestPoint <em>Nest Point</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nest Point</em>' reference.
	 * @see #getNestPoint()
	 * @generated
	 */
	void setNestPoint(NestPoint value);

} // NestPointRef