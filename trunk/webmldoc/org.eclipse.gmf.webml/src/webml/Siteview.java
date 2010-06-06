/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package webml;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Siteview</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link webml.Siteview#getElement <em>Element</em>}</li>
 *   <li>{@link webml.Siteview#getOklink <em>Oklink</em>}</li>
 *   <li>{@link webml.Siteview#getKolink <em>Kolink</em>}</li>
 *   <li>{@link webml.Siteview#getNlink <em>Nlink</em>}</li>
 *   <li>{@link webml.Siteview#getTlink <em>Tlink</em>}</li>
 * </ul>
 * </p>
 *
 * @see webml.WebmlPackage#getSiteview()
 * @model annotation="gmf.diagram foo='bar'"
 * @generated
 */
public interface Siteview extends EObject {
	/**
	 * Returns the value of the '<em><b>Element</b></em>' containment reference list.
	 * The list contents are of type {@link webml.Unit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element</em>' containment reference list.
	 * @see webml.WebmlPackage#getSiteview_Element()
	 * @model containment="true"
	 * @generated
	 */
	EList<Unit> getElement();

	/**
	 * Returns the value of the '<em><b>Oklink</b></em>' containment reference list.
	 * The list contents are of type {@link webml.okLink}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Oklink</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Oklink</em>' containment reference list.
	 * @see webml.WebmlPackage#getSiteview_Oklink()
	 * @model containment="true"
	 * @generated
	 */
	EList<okLink> getOklink();

	/**
	 * Returns the value of the '<em><b>Kolink</b></em>' containment reference list.
	 * The list contents are of type {@link webml.koLink}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kolink</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kolink</em>' containment reference list.
	 * @see webml.WebmlPackage#getSiteview_Kolink()
	 * @model containment="true"
	 * @generated
	 */
	EList<koLink> getKolink();

	/**
	 * Returns the value of the '<em><b>Nlink</b></em>' containment reference list.
	 * The list contents are of type {@link webml.normalLink}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nlink</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nlink</em>' containment reference list.
	 * @see webml.WebmlPackage#getSiteview_Nlink()
	 * @model containment="true"
	 * @generated
	 */
	EList<normalLink> getNlink();

	/**
	 * Returns the value of the '<em><b>Tlink</b></em>' containment reference list.
	 * The list contents are of type {@link webml.transportLink}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tlink</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tlink</em>' containment reference list.
	 * @see webml.WebmlPackage#getSiteview_Tlink()
	 * @model containment="true"
	 * @generated
	 */
	EList<transportLink> getTlink();

} // Siteview
