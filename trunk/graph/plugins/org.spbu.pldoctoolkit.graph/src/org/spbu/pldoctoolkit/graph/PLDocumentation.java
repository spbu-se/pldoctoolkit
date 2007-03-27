/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>PL Documentation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.PLDocumentation#getDocumentationCore <em>Documentation Core</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.PLDocumentation#getProductDocumentations <em>Product Documentations</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getPLDocumentation()
 * @model
 * @generated
 */
public interface PLDocumentation extends DrlElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * Returns the value of the '<em><b>Documentation Core</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Documentation Core</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Documentation Core</em>' containment reference.
	 * @see #setDocumentationCore(DocumentationCore)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getPLDocumentation_DocumentationCore()
	 * @model containment="true" required="true"
	 * @generated
	 */
	DocumentationCore getDocumentationCore();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.PLDocumentation#getDocumentationCore <em>Documentation Core</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Documentation Core</em>' containment reference.
	 * @see #getDocumentationCore()
	 * @generated
	 */
	void setDocumentationCore(DocumentationCore value);

	/**
	 * Returns the value of the '<em><b>Product Documentations</b></em>' containment reference list.
	 * The list contents are of type {@link org.spbu.pldoctoolkit.graph.ProductDocumentation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Product Documentations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Product Documentations</em>' containment reference list.
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getPLDocumentation_ProductDocumentations()
	 * @model type="org.spbu.pldoctoolkit.graph.ProductDocumentation" containment="true"
	 * @generated
	 */
	EList getProductDocumentations();

} // PLDocumentation