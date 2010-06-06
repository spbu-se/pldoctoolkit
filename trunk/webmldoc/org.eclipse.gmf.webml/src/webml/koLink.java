/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package webml;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ko Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link webml.koLink#getSource <em>Source</em>}</li>
 *   <li>{@link webml.koLink#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see webml.WebmlPackage#getkoLink()
 * @model annotation="gmf.link source='source' target='target' style='solid' color='000000'"
 * @generated
 */
public interface koLink extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(Unit)
	 * @see webml.WebmlPackage#getkoLink_Source()
	 * @model
	 * @generated
	 */
	Unit getSource();

	/**
	 * Sets the value of the '{@link webml.koLink#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Unit value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Unit)
	 * @see webml.WebmlPackage#getkoLink_Target()
	 * @model
	 * @generated
	 */
	Unit getTarget();

	/**
	 * Sets the value of the '{@link webml.koLink#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Unit value);

} // koLink
