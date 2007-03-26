/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.drlvisual.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.spbu.pldoctoolkit.drlvisual.DocumentationCore;
import org.spbu.pldoctoolkit.drlvisual.DrlPackage;
import org.spbu.pldoctoolkit.drlvisual.PLDocumentation;
import org.spbu.pldoctoolkit.drlvisual.ProductDocumentation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>PL Documentation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.impl.PLDocumentationImpl#getDocumentationCore <em>Documentation Core</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.impl.PLDocumentationImpl#getProductDocumentations <em>Product Documentations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PLDocumentationImpl extends DrlElementImpl implements PLDocumentation {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * The cached value of the '{@link #getDocumentationCore() <em>Documentation Core</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentationCore()
	 * @generated
	 * @ordered
	 */
	protected DocumentationCore documentationCore = null;

	/**
	 * The cached value of the '{@link #getProductDocumentations() <em>Product Documentations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProductDocumentations()
	 * @generated
	 * @ordered
	 */
	protected EList productDocumentations = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PLDocumentationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DrlPackage.Literals.PL_DOCUMENTATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentationCore getDocumentationCore() {
		return documentationCore;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDocumentationCore(DocumentationCore newDocumentationCore, NotificationChain msgs) {
		DocumentationCore oldDocumentationCore = documentationCore;
		documentationCore = newDocumentationCore;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DrlPackage.PL_DOCUMENTATION__DOCUMENTATION_CORE, oldDocumentationCore, newDocumentationCore);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocumentationCore(DocumentationCore newDocumentationCore) {
		if (newDocumentationCore != documentationCore) {
			NotificationChain msgs = null;
			if (documentationCore != null)
				msgs = ((InternalEObject)documentationCore).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DrlPackage.PL_DOCUMENTATION__DOCUMENTATION_CORE, null, msgs);
			if (newDocumentationCore != null)
				msgs = ((InternalEObject)newDocumentationCore).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DrlPackage.PL_DOCUMENTATION__DOCUMENTATION_CORE, null, msgs);
			msgs = basicSetDocumentationCore(newDocumentationCore, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.PL_DOCUMENTATION__DOCUMENTATION_CORE, newDocumentationCore, newDocumentationCore));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getProductDocumentations() {
		if (productDocumentations == null) {
			productDocumentations = new EObjectContainmentEList(ProductDocumentation.class, this, DrlPackage.PL_DOCUMENTATION__PRODUCT_DOCUMENTATIONS);
		}
		return productDocumentations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DrlPackage.PL_DOCUMENTATION__DOCUMENTATION_CORE:
				return basicSetDocumentationCore(null, msgs);
			case DrlPackage.PL_DOCUMENTATION__PRODUCT_DOCUMENTATIONS:
				return ((InternalEList)getProductDocumentations()).basicRemove(otherEnd, msgs);
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
			case DrlPackage.PL_DOCUMENTATION__DOCUMENTATION_CORE:
				return getDocumentationCore();
			case DrlPackage.PL_DOCUMENTATION__PRODUCT_DOCUMENTATIONS:
				return getProductDocumentations();
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
			case DrlPackage.PL_DOCUMENTATION__DOCUMENTATION_CORE:
				setDocumentationCore((DocumentationCore)newValue);
				return;
			case DrlPackage.PL_DOCUMENTATION__PRODUCT_DOCUMENTATIONS:
				getProductDocumentations().clear();
				getProductDocumentations().addAll((Collection)newValue);
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
			case DrlPackage.PL_DOCUMENTATION__DOCUMENTATION_CORE:
				setDocumentationCore((DocumentationCore)null);
				return;
			case DrlPackage.PL_DOCUMENTATION__PRODUCT_DOCUMENTATIONS:
				getProductDocumentations().clear();
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
			case DrlPackage.PL_DOCUMENTATION__DOCUMENTATION_CORE:
				return documentationCore != null;
			case DrlPackage.PL_DOCUMENTATION__PRODUCT_DOCUMENTATIONS:
				return productDocumentations != null && !productDocumentations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PLDocumentationImpl