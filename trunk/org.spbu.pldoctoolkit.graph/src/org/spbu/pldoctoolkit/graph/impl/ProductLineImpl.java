/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.spbu.pldoctoolkit.graph.DocumentationCore;
import org.spbu.pldoctoolkit.graph.DrlFactory;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.PLScheme;
import org.spbu.pldoctoolkit.graph.ProductLine;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Product Line</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.ProductLineImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.ProductLineImpl#getProductDocumentations <em>Product Documentations</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.ProductLineImpl#getDocumentationCores <em>Documentation Cores</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.ProductLineImpl#getScheme <em>Scheme</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProductLineImpl extends DrlElementImpl implements ProductLine {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDocumentationCores() <em>Documentation Cores</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentationCores()
	 * @generated
	 * @ordered
	 */
	protected EList documentationCores;

	/**
	 * The cached value of the '{@link #getScheme() <em>Scheme</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScheme()
	 * @generated
	 * @ordered
	 */
	protected PLScheme scheme;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProductLineImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DrlPackage.Literals.PRODUCT_LINE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.PRODUCT_LINE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getProductDocumentations() {
		// TODO: implement this method to return the 'Product Documentations' reference list
		// Ensure that you remove @generated or mark it @generated NOT
		// The list is expected to implement org.eclipse.emf.ecore.util.InternalEList and org.eclipse.emf.ecore.EStructuralFeature.Setting
		// so it's likely that an appropriate subclass of org.eclipse.emf.ecore.util.EcoreEList should be used.
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDocumentationCores() {
		if (documentationCores == null) {
			documentationCores = new EObjectResolvingEList(DocumentationCore.class, this, DrlPackage.PRODUCT_LINE__DOCUMENTATION_CORES);
		}
		return documentationCores;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PLScheme getScheme() {
		return scheme;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetScheme(PLScheme newScheme, NotificationChain msgs) {
		PLScheme oldScheme = scheme;
		scheme = newScheme;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DrlPackage.PRODUCT_LINE__SCHEME, oldScheme, newScheme);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScheme(PLScheme newScheme) {
		if (newScheme != scheme) {
			NotificationChain msgs = null;
			if (scheme != null)
				msgs = ((InternalEObject)scheme).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DrlPackage.PRODUCT_LINE__SCHEME, null, msgs);
			if (newScheme != null)
				msgs = ((InternalEObject)newScheme).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DrlPackage.PRODUCT_LINE__SCHEME, null, msgs);
			msgs = basicSetScheme(newScheme, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.PRODUCT_LINE__SCHEME, newScheme, newScheme));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DrlPackage.PRODUCT_LINE__SCHEME:
				return basicSetScheme(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DrlPackage.PRODUCT_LINE__NAME:
				return getName();
			case DrlPackage.PRODUCT_LINE__PRODUCT_DOCUMENTATIONS:
				return getProductDocumentations();
			case DrlPackage.PRODUCT_LINE__DOCUMENTATION_CORES:
				return getDocumentationCores();
			case DrlPackage.PRODUCT_LINE__SCHEME:
				return getScheme();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DrlPackage.PRODUCT_LINE__NAME:
				setName((String)newValue);
				return;
			case DrlPackage.PRODUCT_LINE__PRODUCT_DOCUMENTATIONS:
				getProductDocumentations().clear();
				getProductDocumentations().addAll((Collection)newValue);
				return;
			case DrlPackage.PRODUCT_LINE__DOCUMENTATION_CORES:
				getDocumentationCores().clear();
				getDocumentationCores().addAll((Collection)newValue);
				return;
			case DrlPackage.PRODUCT_LINE__SCHEME:
				setScheme((PLScheme)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case DrlPackage.PRODUCT_LINE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DrlPackage.PRODUCT_LINE__PRODUCT_DOCUMENTATIONS:
				getProductDocumentations().clear();
				return;
			case DrlPackage.PRODUCT_LINE__DOCUMENTATION_CORES:
				getDocumentationCores().clear();
				return;
			case DrlPackage.PRODUCT_LINE__SCHEME:
				setScheme((PLScheme)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DrlPackage.PRODUCT_LINE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DrlPackage.PRODUCT_LINE__PRODUCT_DOCUMENTATIONS:
				return !getProductDocumentations().isEmpty();
			case DrlPackage.PRODUCT_LINE__DOCUMENTATION_CORES:
				return documentationCores != null && !documentationCores.isEmpty();
			case DrlPackage.PRODUCT_LINE__SCHEME:
				return scheme != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlElementImpl#initializeAttributeNodes(org.w3c.dom.Element)
	 */
	@Override
	protected void initializeAttributeNodes(Element elem) {
		super.initializeAttributeNodes(elem);
		
		// name
		String nameAttrName = 
			DrlFactory.eINSTANCE.getDrlPackage().getProductLine_Name().getName();
		
		elem.setAttribute(nameAttrName, getName());
	}

} //ProductLineImpl