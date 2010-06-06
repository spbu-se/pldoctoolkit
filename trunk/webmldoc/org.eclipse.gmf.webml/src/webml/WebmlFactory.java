/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package webml;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see webml.WebmlPackage
 * @generated
 */
public interface WebmlFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	WebmlFactory eINSTANCE = webml.impl.WebmlFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Siteview</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Siteview</em>'.
	 * @generated
	 */
	Siteview createSiteview();

	/**
	 * Returns a new object of class '<em>Area</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Area</em>'.
	 * @generated
	 */
	Area createArea();

	/**
	 * Returns a new object of class '<em>Page</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Page</em>'.
	 * @generated
	 */
	Page createPage();

	/**
	 * Returns a new object of class '<em>ok Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ok Link</em>'.
	 * @generated
	 */
	okLink createokLink();

	/**
	 * Returns a new object of class '<em>ko Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ko Link</em>'.
	 * @generated
	 */
	koLink createkoLink();

	/**
	 * Returns a new object of class '<em>normal Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>normal Link</em>'.
	 * @generated
	 */
	normalLink createnormalLink();

	/**
	 * Returns a new object of class '<em>transport Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>transport Link</em>'.
	 * @generated
	 */
	transportLink createtransportLink();

	/**
	 * Returns a new object of class '<em>Doc Topic</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Doc Topic</em>'.
	 * @generated
	 */
	DocTopic createDocTopic();

	/**
	 * Returns a new object of class '<em>Content Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Content Unit</em>'.
	 * @generated
	 */
	ContentUnit createContentUnit();

	/**
	 * Returns a new object of class '<em>Operation Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation Unit</em>'.
	 * @generated
	 */
	OperationUnit createOperationUnit();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	WebmlPackage getWebmlPackage();

} //WebmlFactory
