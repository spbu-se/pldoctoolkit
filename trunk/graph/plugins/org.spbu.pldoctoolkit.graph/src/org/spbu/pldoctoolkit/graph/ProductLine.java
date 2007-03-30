/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Product Line</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.ProductLine#getName <em>Name</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.ProductLine#getProductDocumentations <em>Product Documentations</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.ProductLine#getDocumentationCore <em>Documentation Core</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.ProductLine#getScheme <em>Scheme</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getProductLine()
 * @model
 * @generated
 */
public interface ProductLine extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getProductLine_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.ProductLine#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Product Documentations</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Product Documentations</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Product Documentations</em>' reference.
	 * @see #setProductDocumentations(ProductDocumentation)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getProductLine_ProductDocumentations()
	 * @model
	 * @generated
	 */
	ProductDocumentation getProductDocumentations();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.ProductLine#getProductDocumentations <em>Product Documentations</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Product Documentations</em>' reference.
	 * @see #getProductDocumentations()
	 * @generated
	 */
	void setProductDocumentations(ProductDocumentation value);

	/**
	 * Returns the value of the '<em><b>Documentation Core</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Documentation Core</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Documentation Core</em>' reference.
	 * @see #setDocumentationCore(DocumentationCore)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getProductLine_DocumentationCore()
	 * @model
	 * @generated
	 */
	DocumentationCore getDocumentationCore();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.ProductLine#getDocumentationCore <em>Documentation Core</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Documentation Core</em>' reference.
	 * @see #getDocumentationCore()
	 * @generated
	 */
	void setDocumentationCore(DocumentationCore value);

	/**
	 * Returns the value of the '<em><b>Scheme</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scheme</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scheme</em>' containment reference.
	 * @see #setScheme(PLScheme)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getProductLine_Scheme()
	 * @model containment="true" required="true"
	 * @generated
	 */
	PLScheme getScheme();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.ProductLine#getScheme <em>Scheme</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scheme</em>' containment reference.
	 * @see #getScheme()
	 * @generated
	 */
	void setScheme(PLScheme value);

} // ProductLine