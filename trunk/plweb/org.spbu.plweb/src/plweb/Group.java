/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package plweb;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link plweb.Group#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see plweb.PlwebPackage#getGroup()
 * @model
 * @generated
 */
public interface Group extends SourceRefElement, TargetRefElement {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link plweb.GroupType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see plweb.GroupType
	 * @see #setType(GroupType)
	 * @see plweb.PlwebPackage#getGroup_Type()
	 * @model
	 * @generated
	 */
	GroupType getType();

	/**
	 * Sets the value of the '{@link plweb.Group#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see plweb.GroupType
	 * @see #getType()
	 * @generated
	 */
	void setType(GroupType value);

} // Group
