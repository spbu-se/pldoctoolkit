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
 * A representation of the model object '<em><b>Generic Document Part</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.GenericDocumentPart#getId <em>Id</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.GenericDocumentPart#getName <em>Name</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.GenericDocumentPart#getInfElemRefs <em>Inf Elem Refs</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.GenericDocumentPart#getGroups <em>Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getGenericDocumentPart()
 * @model abstract="true"
 * @generated
 */
public interface GenericDocumentPart extends DrlElement {
	//TODO move to the appropriate place
	static final String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

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
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getGenericDocumentPart_Id()
	 * @model id="true" required="true" derived="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.GenericDocumentPart#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

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
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getGenericDocumentPart_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.GenericDocumentPart#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Inf Elem Refs</b></em>' containment reference list.
	 * The list contents are of type {@link org.spbu.pldoctoolkit.graph.InfElemRef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inf Elem Refs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inf Elem Refs</em>' containment reference list.
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getGenericDocumentPart_InfElemRefs()
	 * @model type="org.spbu.pldoctoolkit.graph.InfElemRef" containment="true"
	 * @generated
	 */
	EList getInfElemRefs();

	/**
	 * Returns the value of the '<em><b>Groups</b></em>' containment reference list.
	 * The list contents are of type {@link org.spbu.pldoctoolkit.graph.InfElemRefGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Groups</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Groups</em>' containment reference list.
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getGenericDocumentPart_Groups()
	 * @model type="org.spbu.pldoctoolkit.graph.InfElemRefGroup" containment="true"
	 * @generated
	 */
	EList getGroups();

} // GenericDocumentPart