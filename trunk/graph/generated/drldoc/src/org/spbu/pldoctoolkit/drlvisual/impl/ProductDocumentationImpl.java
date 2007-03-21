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

import org.spbu.pldoctoolkit.drlvisual.FinalInfProduct;
import org.spbu.pldoctoolkit.drlvisual.ProductDocumentation;
import org.spbu.pldoctoolkit.drlvisual.drlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Product Documentation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.impl.ProductDocumentationImpl#getProductId <em>Product Id</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.impl.ProductDocumentationImpl#getFinalInfProducts <em>Final Inf Products</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProductDocumentationImpl extends DrlElementImpl implements ProductDocumentation {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * The default value of the '{@link #getProductId() <em>Product Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProductId()
	 * @generated
	 * @ordered
	 */
	protected static final String PRODUCT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProductId() <em>Product Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProductId()
	 * @generated
	 * @ordered
	 */
	protected String productId = PRODUCT_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFinalInfProducts() <em>Final Inf Products</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFinalInfProducts()
	 * @generated
	 * @ordered
	 */
	protected EList finalInfProducts = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProductDocumentationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return drlPackage.Literals.PRODUCT_DOCUMENTATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProductId(String newProductId) {
		String oldProductId = productId;
		productId = newProductId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, drlPackage.PRODUCT_DOCUMENTATION__PRODUCT_ID, oldProductId, productId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getFinalInfProducts() {
		if (finalInfProducts == null) {
			finalInfProducts = new EObjectContainmentEList(FinalInfProduct.class, this, drlPackage.PRODUCT_DOCUMENTATION__FINAL_INF_PRODUCTS);
		}
		return finalInfProducts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case drlPackage.PRODUCT_DOCUMENTATION__FINAL_INF_PRODUCTS:
				return ((InternalEList)getFinalInfProducts()).basicRemove(otherEnd, msgs);
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
			case drlPackage.PRODUCT_DOCUMENTATION__PRODUCT_ID:
				return getProductId();
			case drlPackage.PRODUCT_DOCUMENTATION__FINAL_INF_PRODUCTS:
				return getFinalInfProducts();
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
			case drlPackage.PRODUCT_DOCUMENTATION__PRODUCT_ID:
				setProductId((String)newValue);
				return;
			case drlPackage.PRODUCT_DOCUMENTATION__FINAL_INF_PRODUCTS:
				getFinalInfProducts().clear();
				getFinalInfProducts().addAll((Collection)newValue);
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
			case drlPackage.PRODUCT_DOCUMENTATION__PRODUCT_ID:
				setProductId(PRODUCT_ID_EDEFAULT);
				return;
			case drlPackage.PRODUCT_DOCUMENTATION__FINAL_INF_PRODUCTS:
				getFinalInfProducts().clear();
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
			case drlPackage.PRODUCT_DOCUMENTATION__PRODUCT_ID:
				return PRODUCT_ID_EDEFAULT == null ? productId != null : !PRODUCT_ID_EDEFAULT.equals(productId);
			case drlPackage.PRODUCT_DOCUMENTATION__FINAL_INF_PRODUCTS:
				return finalInfProducts != null && !finalInfProducts.isEmpty();
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
		result.append(" (productId: ");
		result.append(productId);
		result.append(')');
		return result.toString();
	}

} //ProductDocumentationImpl