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
 * A representation of the model object '<em><b>Final Inf Product</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.FinalInfProduct#getProduct <em>Product</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.FinalInfProduct#getId <em>Id</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.FinalInfProduct#getAdapters <em>Adapters</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getFinalInfProduct()
 * @model
 * @generated
 */
public interface FinalInfProduct extends DrlElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * Returns the value of the '<em><b>Product</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Product</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Product</em>' reference.
	 * @see #setProduct(InfProduct)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getFinalInfProduct_Product()
	 * @model required="true"
	 * @generated
	 */
	InfProduct getProduct();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.FinalInfProduct#getProduct <em>Product</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Product</em>' reference.
	 * @see #getProduct()
	 * @generated
	 */
	void setProduct(InfProduct value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getFinalInfProduct_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.FinalInfProduct#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Adapters</b></em>' containment reference list.
	 * The list contents are of type {@link org.spbu.pldoctoolkit.graph.Adapter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adapters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Adapters</em>' containment reference list.
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getFinalInfProduct_Adapters()
	 * @model containment="true"
	 * @generated
	 */
	EList<Adapter> getAdapters();

} // FinalInfProduct