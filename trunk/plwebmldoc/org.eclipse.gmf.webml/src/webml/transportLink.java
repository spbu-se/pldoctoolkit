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
 * A representation of the model object '<em><b>transport Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link webml.transportLink#getSource <em>Source</em>}</li>
 *   <li>{@link webml.transportLink#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see webml.WebmlPackage#gettransportLink()
 * @model annotation="gmf.link source='source' target='target' style='dash' color='000000'"
 * @generated
 */
public interface transportLink extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(ContentUnit)
	 * @see webml.WebmlPackage#gettransportLink_Source()
	 * @model
	 * @generated
	 */
	ContentUnit getSource();

	/**
	 * Sets the value of the '{@link webml.transportLink#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(ContentUnit value);

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
	 * @see webml.WebmlPackage#gettransportLink_Target()
	 * @model
	 * @generated
	 */
	Unit getTarget();

	/**
	 * Sets the value of the '{@link webml.transportLink#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Unit value);

} // transportLink
