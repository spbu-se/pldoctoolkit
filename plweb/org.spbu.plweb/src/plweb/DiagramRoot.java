/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package plweb;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Diagram Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link plweb.DiagramRoot#getArea <em>Area</em>}</li>
 *   <li>{@link plweb.DiagramRoot#getProjectPath <em>Project Path</em>}</li>
 *   <li>{@link plweb.DiagramRoot#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see plweb.PlwebPackage#getDiagramRoot()
 * @model
 * @generated
 */
public interface DiagramRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Area</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Area</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Area</em>' containment reference.
	 * @see #setArea(Area)
	 * @see plweb.PlwebPackage#getDiagramRoot_Area()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Area getArea();

	/**
	 * Sets the value of the '{@link plweb.DiagramRoot#getArea <em>Area</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Area</em>' containment reference.
	 * @see #getArea()
	 * @generated
	 */
	void setArea(Area value);

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
	 * @see plweb.PlwebPackage#getDiagramRoot_ProjectPath()
	 * @model
	 * @generated
	 */
	String getProjectPath();

	/**
	 * Sets the value of the '{@link plweb.DiagramRoot#getProjectPath <em>Project Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Path</em>' attribute.
	 * @see #getProjectPath()
	 * @generated
	 */
	void setProjectPath(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link plweb.DiagramType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see plweb.DiagramType
	 * @see #setType(DiagramType)
	 * @see plweb.PlwebPackage#getDiagramRoot_Type()
	 * @model
	 * @generated
	 */
	DiagramType getType();

	/**
	 * Sets the value of the '{@link plweb.DiagramRoot#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see plweb.DiagramType
	 * @see #getType()
	 * @generated
	 */
	void setType(DiagramType value);

} // DiagramRoot
