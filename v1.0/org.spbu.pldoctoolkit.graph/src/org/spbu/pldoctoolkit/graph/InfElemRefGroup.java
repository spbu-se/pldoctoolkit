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
 * A representation of the model object '<em><b>Inf Elem Ref Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.InfElemRefGroup#getId <em>Id</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.InfElemRefGroup#getModifier <em>Modifier</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.InfElemRefGroup#getName <em>Name</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.InfElemRefGroup#getInfElemRefsGroup <em>Inf Elem Refs Group</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getInfElemRefGroup()
 * @model
 * @generated
 */
public interface InfElemRefGroup extends DrlElement {
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
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getInfElemRefGroup_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.InfElemRefGroup#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Modifier</b></em>' attribute.
	 * The literals are from the enumeration {@link org.spbu.pldoctoolkit.graph.GroupType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modifier</em>' attribute.
	 * @see org.spbu.pldoctoolkit.graph.GroupType
	 * @see #setModifier(GroupType)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getInfElemRefGroup_Modifier()
	 * @model required="true"
	 * @generated
	 */
	GroupType getModifier();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.InfElemRefGroup#getModifier <em>Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Modifier</em>' attribute.
	 * @see org.spbu.pldoctoolkit.graph.GroupType
	 * @see #getModifier()
	 * @generated
	 */
	void setModifier(GroupType value);

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
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getInfElemRefGroup_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.InfElemRefGroup#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Inf Elem Refs Group</b></em>' reference list.
	 * The list contents are of type {@link org.spbu.pldoctoolkit.graph.InfElemRef}.
	 * It is bidirectional and its opposite is '{@link org.spbu.pldoctoolkit.graph.InfElemRef#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inf Elem Refs Group</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inf Elem Refs Group</em>' reference list.
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getInfElemRefGroup_InfElemRefsGroup()
	 * @see org.spbu.pldoctoolkit.graph.InfElemRef#getGroup
	 * @model type="org.spbu.pldoctoolkit.graph.InfElemRef" opposite="group" resolveProxies="false" transient="true" derived="true"
	 * @generated
	 */
	EList<InfElemRef> getInfElemRefsGroup();

} // InfElemRefGroup