/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.drlvisual;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inf Elem Ref Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.InfElemRefGroup#getId <em>Id</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.InfElemRefGroup#getModifier <em>Modifier</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.InfElemRefGroup#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.drlvisual.DrlPackage#getInfElemRefGroup()
 * @model
 * @generated
 */
public interface InfElemRefGroup extends InnerElement {
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
	 * @see org.spbu.pldoctoolkit.drlvisual.DrlPackage#getInfElemRefGroup_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.drlvisual.InfElemRefGroup#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Modifier</b></em>' attribute.
	 * The literals are from the enumeration {@link org.spbu.pldoctoolkit.drlvisual.GroupType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modifier</em>' attribute.
	 * @see org.spbu.pldoctoolkit.drlvisual.GroupType
	 * @see #setModifier(GroupType)
	 * @see org.spbu.pldoctoolkit.drlvisual.DrlPackage#getInfElemRefGroup_Modifier()
	 * @model required="true"
	 * @generated
	 */
	GroupType getModifier();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.drlvisual.InfElemRefGroup#getModifier <em>Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Modifier</em>' attribute.
	 * @see org.spbu.pldoctoolkit.drlvisual.GroupType
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
	 * @see org.spbu.pldoctoolkit.drlvisual.DrlPackage#getInfElemRefGroup_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.drlvisual.InfElemRefGroup#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // InfElemRefGroup