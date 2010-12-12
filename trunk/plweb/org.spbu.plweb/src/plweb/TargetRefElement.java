/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package plweb;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Target Ref Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link plweb.TargetRefElement#getParent <em>Parent</em>}</li>
 *   <li>{@link plweb.TargetRefElement#isOptional <em>Optional</em>}</li>
 * </ul>
 * </p>
 *
 * @see plweb.PlwebPackage#getTargetRefElement()
 * @model
 * @generated
 */
public interface TargetRefElement extends Element {
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #isSetParent()
	 * @see #unsetParent()
	 * @see #setParent(SourceRefElement)
	 * @see plweb.PlwebPackage#getTargetRefElement_Parent()
	 * @model unsettable="true"
	 * @generated
	 */
	SourceRefElement getParent();

	/**
	 * Sets the value of the '{@link plweb.TargetRefElement#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #isSetParent()
	 * @see #unsetParent()
	 * @see #getParent()
	 * @generated
	 */
	void setParent(SourceRefElement value);

	/**
	 * Unsets the value of the '{@link plweb.TargetRefElement#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetParent()
	 * @see #getParent()
	 * @see #setParent(SourceRefElement)
	 * @generated
	 */
	void unsetParent();

	/**
	 * Returns whether the value of the '{@link plweb.TargetRefElement#getParent <em>Parent</em>}' reference is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Parent</em>' reference is set.
	 * @see #unsetParent()
	 * @see #getParent()
	 * @see #setParent(SourceRefElement)
	 * @generated
	 */
	boolean isSetParent();

	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see #setOptional(boolean)
	 * @see plweb.PlwebPackage#getTargetRefElement_Optional()
	 * @model
	 * @generated
	 */
	boolean isOptional();

	/**
	 * Sets the value of the '{@link plweb.TargetRefElement#isOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see #isOptional()
	 * @generated
	 */
	void setOptional(boolean value);

} // TargetRefElement
