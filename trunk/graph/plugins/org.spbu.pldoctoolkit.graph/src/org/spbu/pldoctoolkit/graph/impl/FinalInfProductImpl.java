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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.spbu.pldoctoolkit.graph.Adapter;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.FinalInfProduct;
import org.spbu.pldoctoolkit.graph.InfProduct;
import org.spbu.pldoctoolkit.graph.Variable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Final Inf Product</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.FinalInfProductImpl#getAdapters <em>Adapters</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.FinalInfProductImpl#getProduct <em>Product</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.FinalInfProductImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.FinalInfProductImpl#getVariables <em>Variables</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FinalInfProductImpl extends DrlElementImpl implements FinalInfProduct {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * The cached value of the '{@link #getAdapters() <em>Adapters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdapters()
	 * @generated
	 * @ordered
	 */
	protected EList adapters = null;

	/**
	 * The cached value of the '{@link #getProduct() <em>Product</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProduct()
	 * @generated
	 * @ordered
	 */
	protected InfProduct product = null;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected Variable variables = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FinalInfProductImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DrlPackage.Literals.FINAL_INF_PRODUCT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getAdapters() {
		if (adapters == null) {
			adapters = new EObjectContainmentEList(Adapter.class, this, DrlPackage.FINAL_INF_PRODUCT__ADAPTERS);
		}
		return adapters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfProduct getProduct() {
		if (product != null && product.eIsProxy()) {
			InternalEObject oldProduct = (InternalEObject)product;
			product = (InfProduct)eResolveProxy(oldProduct);
			if (product != oldProduct) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DrlPackage.FINAL_INF_PRODUCT__PRODUCT, oldProduct, product));
			}
		}
		return product;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfProduct basicGetProduct() {
		return product;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProduct(InfProduct newProduct) {
		InfProduct oldProduct = product;
		product = newProduct;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.FINAL_INF_PRODUCT__PRODUCT, oldProduct, product));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.FINAL_INF_PRODUCT__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getVariables() {
		return variables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVariables(Variable newVariables, NotificationChain msgs) {
		Variable oldVariables = variables;
		variables = newVariables;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DrlPackage.FINAL_INF_PRODUCT__VARIABLES, oldVariables, newVariables);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariables(Variable newVariables) {
		if (newVariables != variables) {
			NotificationChain msgs = null;
			if (variables != null)
				msgs = ((InternalEObject)variables).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DrlPackage.FINAL_INF_PRODUCT__VARIABLES, null, msgs);
			if (newVariables != null)
				msgs = ((InternalEObject)newVariables).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DrlPackage.FINAL_INF_PRODUCT__VARIABLES, null, msgs);
			msgs = basicSetVariables(newVariables, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.FINAL_INF_PRODUCT__VARIABLES, newVariables, newVariables));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DrlPackage.FINAL_INF_PRODUCT__ADAPTERS:
				return ((InternalEList)getAdapters()).basicRemove(otherEnd, msgs);
			case DrlPackage.FINAL_INF_PRODUCT__VARIABLES:
				return basicSetVariables(null, msgs);
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
			case DrlPackage.FINAL_INF_PRODUCT__ADAPTERS:
				return getAdapters();
			case DrlPackage.FINAL_INF_PRODUCT__PRODUCT:
				if (resolve) return getProduct();
				return basicGetProduct();
			case DrlPackage.FINAL_INF_PRODUCT__ID:
				return getId();
			case DrlPackage.FINAL_INF_PRODUCT__VARIABLES:
				return getVariables();
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
			case DrlPackage.FINAL_INF_PRODUCT__ADAPTERS:
				getAdapters().clear();
				getAdapters().addAll((Collection)newValue);
				return;
			case DrlPackage.FINAL_INF_PRODUCT__PRODUCT:
				setProduct((InfProduct)newValue);
				return;
			case DrlPackage.FINAL_INF_PRODUCT__ID:
				setId((String)newValue);
				return;
			case DrlPackage.FINAL_INF_PRODUCT__VARIABLES:
				setVariables((Variable)newValue);
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
			case DrlPackage.FINAL_INF_PRODUCT__ADAPTERS:
				getAdapters().clear();
				return;
			case DrlPackage.FINAL_INF_PRODUCT__PRODUCT:
				setProduct((InfProduct)null);
				return;
			case DrlPackage.FINAL_INF_PRODUCT__ID:
				setId(ID_EDEFAULT);
				return;
			case DrlPackage.FINAL_INF_PRODUCT__VARIABLES:
				setVariables((Variable)null);
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
			case DrlPackage.FINAL_INF_PRODUCT__ADAPTERS:
				return adapters != null && !adapters.isEmpty();
			case DrlPackage.FINAL_INF_PRODUCT__PRODUCT:
				return product != null;
			case DrlPackage.FINAL_INF_PRODUCT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DrlPackage.FINAL_INF_PRODUCT__VARIABLES:
				return variables != null;
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
		result.append(" (id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} //FinalInfProductImpl