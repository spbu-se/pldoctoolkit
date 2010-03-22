/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package webml.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import webml.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class WebmlFactoryImpl extends EFactoryImpl implements WebmlFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static WebmlFactory init() {
		try {
			WebmlFactory theWebmlFactory = (WebmlFactory)EPackage.Registry.INSTANCE.getEFactory("webml"); 
			if (theWebmlFactory != null) {
				return theWebmlFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new WebmlFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WebmlFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case WebmlPackage.SITEVIEW: return createSiteview();
			case WebmlPackage.AREA: return createArea();
			case WebmlPackage.PAGE: return createPage();
			case WebmlPackage.OK_LINK: return createokLink();
			case WebmlPackage.KO_LINK: return createkoLink();
			case WebmlPackage.NORMAL_LINK: return createnormalLink();
			case WebmlPackage.TRANSPORT_LINK: return createtransportLink();
			case WebmlPackage.DOC_TOPIC: return createDocTopic();
			case WebmlPackage.CONTENT_UNIT: return createContentUnit();
			case WebmlPackage.OPERATION_UNIT: return createOperationUnit();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Siteview createSiteview() {
		SiteviewImpl siteview = new SiteviewImpl();
		return siteview;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Area createArea() {
		AreaImpl area = new AreaImpl();
		return area;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Page createPage() {
		PageImpl page = new PageImpl();
		return page;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public okLink createokLink() {
		okLinkImpl okLink = new okLinkImpl();
		return okLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public koLink createkoLink() {
		koLinkImpl koLink = new koLinkImpl();
		return koLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public normalLink createnormalLink() {
		normalLinkImpl normalLink = new normalLinkImpl();
		return normalLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public transportLink createtransportLink() {
		transportLinkImpl transportLink = new transportLinkImpl();
		return transportLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocTopic createDocTopic() {
		DocTopicImpl docTopic = new DocTopicImpl();
		return docTopic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContentUnit createContentUnit() {
		ContentUnitImpl contentUnit = new ContentUnitImpl();
		return contentUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationUnit createOperationUnit() {
		OperationUnitImpl operationUnit = new OperationUnitImpl();
		return operationUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WebmlPackage getWebmlPackage() {
		return (WebmlPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static WebmlPackage getPackage() {
		return WebmlPackage.eINSTANCE;
	}

} //WebmlFactoryImpl
