/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.DrlElement#getNode <em>Node</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getDrlElement()
 * @model abstract="true"
 * @generated
 */
public interface DrlElement extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * Returns the value of the '<em><b>Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Node</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node</em>' attribute.
	 * @see #setNode(Element)
	 * @see org.spbu.pldoctoolkit.graph.DrlPackage#getDrlElement_Node()
	 * @model dataType="org.spbu.pldoctoolkit.graph.NodeType"
	 * @generated
	 */
	Element getNode();

	/**
	 * Sets the value of the '{@link org.spbu.pldoctoolkit.graph.DrlElement#getNode <em>Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node</em>' attribute.
	 * @see #getNode()
	 * @generated
	 */
	void setNode(Element value);
	
	/*
	 * In case the corresponding DOM node has not been initialized, does this.
	 */
	void initializeNode(EStructuralFeature feature);

	void updateAttributeNodes();
	
} // DrlElement
