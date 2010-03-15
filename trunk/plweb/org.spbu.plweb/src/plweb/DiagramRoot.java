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

} // DiagramRoot
