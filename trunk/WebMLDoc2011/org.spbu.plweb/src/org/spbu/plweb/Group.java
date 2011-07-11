/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.plweb;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.plweb.Group#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.plweb.PlwebPackage#getGroup()
 * @model
 * @generated
 */
public interface Group extends SourceRefElement, TargetRefElement {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.spbu.plweb.GroupType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.spbu.plweb.GroupType
	 * @see #setType(GroupType)
	 * @see org.spbu.plweb.PlwebPackage#getGroup_Type()
	 * @model
	 * @generated
	 */
	GroupType getType();

	/**
	 * Sets the value of the '{@link org.spbu.plweb.Group#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.spbu.plweb.GroupType
	 * @see #getType()
	 * @generated
	 */
	void setType(GroupType value);

} // Group
