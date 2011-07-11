/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.plweb;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.plweb.Element#getTitle <em>Title</em>}</li>
 *   <li>{@link org.spbu.plweb.Element#getDocTopic <em>Doc Topic</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.plweb.PlwebPackage#getElement()
 * @model
 * @generated
 */
public interface Element extends EObject {
	/**
	 * Returns the value of the '<em><b>Doc Topic</b></em>' containment reference list.
	 * The list contents are of type {@link org.spbu.plweb.DocTopic}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Doc Topic</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Doc Topic</em>' containment reference list.
	 * @see org.spbu.plweb.PlwebPackage#getElement_DocTopic()
	 * @model containment="true"
	 * @generated
	 */
	EList<DocTopic> getDocTopic();

	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see org.spbu.plweb.PlwebPackage#getElement_Title()
	 * @model default=""
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link org.spbu.plweb.Element#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

} // Element
