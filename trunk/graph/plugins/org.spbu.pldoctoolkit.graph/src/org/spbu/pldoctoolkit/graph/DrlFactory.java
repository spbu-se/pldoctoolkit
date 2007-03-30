/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.spbu.pldoctoolkit.graph.DrlPackage
 * @generated
 */
public interface DrlFactory extends EFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "copyleft 2007";

	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DrlFactory eINSTANCE = org.spbu.pldoctoolkit.graph.impl.DrlFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Inf Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Inf Element</em>'.
	 * @generated
	 */
	InfElement createInfElement();

	/**
	 * Returns a new object of class '<em>Inf Product</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Inf Product</em>'.
	 * @generated
	 */
	InfProduct createInfProduct();

	/**
	 * Returns a new object of class '<em>Final Inf Product</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Final Inf Product</em>'.
	 * @generated
	 */
	FinalInfProduct createFinalInfProduct();

	/**
	 * Returns a new object of class '<em>Nest Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Nest Point</em>'.
	 * @generated
	 */
	NestPoint createNestPoint();

	/**
	 * Returns a new object of class '<em>Inf Elem Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Inf Elem Ref</em>'.
	 * @generated
	 */
	InfElemRef createInfElemRef();

	/**
	 * Returns a new object of class '<em>Inf Elem Ref Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Inf Elem Ref Group</em>'.
	 * @generated
	 */
	InfElemRefGroup createInfElemRefGroup();

	/**
	 * Returns a new object of class '<em>Product Line</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Product Line</em>'.
	 * @generated
	 */
	ProductLine createProductLine();

	/**
	 * Returns a new object of class '<em>Product</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Product</em>'.
	 * @generated
	 */
	Product createProduct();

	/**
	 * Returns a new object of class '<em>Documentation Core</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Documentation Core</em>'.
	 * @generated
	 */
	DocumentationCore createDocumentationCore();

	/**
	 * Returns a new object of class '<em>Product Documentation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Product Documentation</em>'.
	 * @generated
	 */
	ProductDocumentation createProductDocumentation();

	/**
	 * Returns a new object of class '<em>PL Scheme</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>PL Scheme</em>'.
	 * @generated
	 */
	PLScheme createPLScheme();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DrlPackage getDrlPackage();

} //DrlFactory
