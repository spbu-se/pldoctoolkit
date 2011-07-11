/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.plweb;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Doc Topic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.plweb.DocTopic#getDocTopicName <em>Doc Topic Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.plweb.PlwebPackage#getDocTopic()
 * @model
 * @generated
 */
public interface DocTopic extends EObject {
	/**
	 * Returns the value of the '<em><b>Doc Topic Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Doc Topic Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Doc Topic Name</em>' attribute.
	 * @see #setDocTopicName(String)
	 * @see org.spbu.plweb.PlwebPackage#getDocTopic_DocTopicName()
	 * @model
	 * @generated
	 */
	String getDocTopicName();

	/**
	 * Sets the value of the '{@link org.spbu.plweb.DocTopic#getDocTopicName <em>Doc Topic Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Doc Topic Name</em>' attribute.
	 * @see #getDocTopicName()
	 * @generated
	 */
	void setDocTopicName(String value);

} // DocTopic
