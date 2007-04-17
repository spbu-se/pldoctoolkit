/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph;

import org.eclipse.emf.common.util.EList;
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
 *   <li>{@link org.spbu.pldoctoolkit.graph.ProductLine#getDocumentationCores <em>Documentation Cores</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.ProductLine#getProducts <em>Products</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getProductLine()
 * @model
 * @generated
 */
public interface ProductLine extends DrlElement {
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
	 * Returns the value of the '<em><b>Product Documentations</b></em>' reference list.
	 * The list contents are of type {@link org.spbu.pldoctoolkit.graph.ProductDocumentation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Product Documentations</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Product Documentations</em>' reference list.
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getProductLine_ProductDocumentations()
	 * @model type="org.spbu.pldoctoolkit.graph.ProductDocumentation" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	EList getProductDocumentations();

	/**
	 * Returns the value of the '<em><b>Documentation Cores</b></em>' reference list.
	 * The list contents are of type {@link org.spbu.pldoctoolkit.graph.DocumentationCore}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Documentation Cores</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Documentation Cores</em>' reference list.
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getProductLine_DocumentationCores()
	 * @model type="org.spbu.pldoctoolkit.graph.DocumentationCore"
	 * @generated
	 */
	EList getDocumentationCores();

	/**
	 * Returns the value of the '<em><b>Products</b></em>' containment reference list.
	 * The list contents are of type {@link org.spbu.pldoctoolkit.graph.Product}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Products</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Products</em>' containment reference list.
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getProductLine_Products()
	 * @model type="org.spbu.pldoctoolkit.graph.Product" containment="true"
	 * @generated
	 */
	EList getProducts();

} // ProductLine