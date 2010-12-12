/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package plweb;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Source Ref Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link plweb.SourceRefElement#getClass_ <em>Class</em>}</li>
 * </ul>
 * </p>
 *
 * @see plweb.PlwebPackage#getSourceRefElement()
 * @model
 * @generated
 */
public interface SourceRefElement extends Element {
	/**
	 * Returns the value of the '<em><b>Class</b></em>' containment reference list.
	 * The list contents are of type {@link plweb.TargetRefElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class</em>' containment reference list.
	 * @see plweb.PlwebPackage#getSourceRefElement_Class()
	 * @model containment="true"
	 * @generated
	 */
	EList<TargetRefElement> getClass_();

} // SourceRefElement
