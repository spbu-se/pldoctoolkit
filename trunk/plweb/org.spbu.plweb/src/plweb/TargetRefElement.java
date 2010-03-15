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
	 * @see #setParent(SourceRefElement)
	 * @see plweb.PlwebPackage#getTargetRefElement_Parent()
	 * @model
	 * @generated
	 */
	SourceRefElement getParent();

	/**
	 * Sets the value of the '{@link plweb.TargetRefElement#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(SourceRefElement value);

} // TargetRefElement
