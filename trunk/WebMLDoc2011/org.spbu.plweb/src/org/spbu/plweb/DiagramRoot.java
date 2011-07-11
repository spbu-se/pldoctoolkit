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
 * A representation of the model object '<em><b>Diagram Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.plweb.DiagramRoot#getRoot <em>Root</em>}</li>
 *   <li>{@link org.spbu.plweb.DiagramRoot#getProjectPath <em>Project Path</em>}</li>
 *   <li>{@link org.spbu.plweb.DiagramRoot#getDiagramType <em>Diagram Type</em>}</li>
 *   <li>{@link org.spbu.plweb.DiagramRoot#getDocPath <em>Doc Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.plweb.PlwebPackage#getDiagramRoot()
 * @model
 * @generated
 */
public interface DiagramRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root</em>' containment reference.
	 * @see #setRoot(Root)
	 * @see org.spbu.plweb.PlwebPackage#getDiagramRoot_Root()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Root getRoot();

	/**
	 * Sets the value of the '{@link org.spbu.plweb.DiagramRoot#getRoot <em>Root</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root</em>' containment reference.
	 * @see #getRoot()
	 * @generated
	 */
	void setRoot(Root value);

	/**
	 * Returns the value of the '<em><b>Project Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Path</em>' attribute.
	 * @see #setProjectPath(String)
	 * @see org.spbu.plweb.PlwebPackage#getDiagramRoot_ProjectPath()
	 * @model
	 * @generated
	 */
	String getProjectPath();

	/**
	 * Sets the value of the '{@link org.spbu.plweb.DiagramRoot#getProjectPath <em>Project Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Path</em>' attribute.
	 * @see #getProjectPath()
	 * @generated
	 */
	void setProjectPath(String value);

	/**
	 * Returns the value of the '<em><b>Diagram Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.spbu.plweb.DiagramType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagram Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Diagram Type</em>' attribute.
	 * @see org.spbu.plweb.DiagramType
	 * @see #setDiagramType(DiagramType)
	 * @see org.spbu.plweb.PlwebPackage#getDiagramRoot_DiagramType()
	 * @model
	 * @generated
	 */
	DiagramType getDiagramType();

	/**
	 * Sets the value of the '{@link org.spbu.plweb.DiagramRoot#getDiagramType <em>Diagram Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagram Type</em>' attribute.
	 * @see org.spbu.plweb.DiagramType
	 * @see #getDiagramType()
	 * @generated
	 */
	void setDiagramType(DiagramType value);

	/**
	 * Returns the value of the '<em><b>Doc Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Doc Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Doc Path</em>' attribute.
	 * @see #setDocPath(String)
	 * @see org.spbu.plweb.PlwebPackage#getDiagramRoot_DocPath()
	 * @model
	 * @generated
	 */
	String getDocPath();

	/**
	 * Sets the value of the '{@link org.spbu.plweb.DiagramRoot#getDocPath <em>Doc Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Doc Path</em>' attribute.
	 * @see #getDocPath()
	 * @generated
	 */
	void setDocPath(String value);

} // DiagramRoot
