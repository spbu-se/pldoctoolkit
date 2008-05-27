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
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.spbu.pldoctoolkit.graph.DrlFactory;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.FinalInfProduct;
import org.spbu.pldoctoolkit.graph.InfProduct;
import org.spbu.pldoctoolkit.graph.Product;
import org.spbu.pldoctoolkit.graph.util.DrlResourceImpl;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Final Inf Product</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.FinalInfProductImpl#getProduct <em>Product</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.FinalInfProductImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.FinalInfProductImpl#getAdapters <em>Adapters</em>}</li>
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
	 * The cached value of the '{@link #getProduct() <em>Product</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProduct()
	 * @generated
	 * @ordered
	 */
	protected InfProduct product;

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
	 * The cached value of the '{@link #getAdapters() <em>Adapters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdapters()
	 * @generated
	 * @ordered
	 */
	protected EList<Adapter> adapters;

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
	@Override
	protected EClass eStaticClass() {
		return DrlPackage.Literals.FINAL_INF_PRODUCT;
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
	 * @generated NOT
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		
		//HAND
		Element node = getNode();
		if(node != null) {
			node.setAttribute(
					DrlFactory.eINSTANCE.getDrlPackage().getFinalInfProduct_Id().getName(), 
					newId);
		}
		
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.FINAL_INF_PRODUCT__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Adapter> getAdapters() {
		if (adapters == null) {
			adapters = new EObjectContainmentEList<Adapter>(Adapter.class, this, DrlPackage.FINAL_INF_PRODUCT__ADAPTERS);
		}
		return adapters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DrlPackage.FINAL_INF_PRODUCT__ADAPTERS:
				return ((InternalEList<?>)getAdapters()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DrlPackage.FINAL_INF_PRODUCT__PRODUCT:
				if (resolve) return getProduct();
				return basicGetProduct();
			case DrlPackage.FINAL_INF_PRODUCT__ID:
				return getId();
			case DrlPackage.FINAL_INF_PRODUCT__ADAPTERS:
				return getAdapters();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DrlPackage.FINAL_INF_PRODUCT__PRODUCT:
				setProduct((InfProduct)newValue);
				return;
			case DrlPackage.FINAL_INF_PRODUCT__ID:
				setId((String)newValue);
				return;
			case DrlPackage.FINAL_INF_PRODUCT__ADAPTERS:
				getAdapters().clear();
				getAdapters().addAll((Collection<? extends Adapter>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DrlPackage.FINAL_INF_PRODUCT__PRODUCT:
				setProduct((InfProduct)null);
				return;
			case DrlPackage.FINAL_INF_PRODUCT__ID:
				setId(ID_EDEFAULT);
				return;
			case DrlPackage.FINAL_INF_PRODUCT__ADAPTERS:
				getAdapters().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DrlPackage.FINAL_INF_PRODUCT__PRODUCT:
				return product != null;
			case DrlPackage.FINAL_INF_PRODUCT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DrlPackage.FINAL_INF_PRODUCT__ADAPTERS:
				return adapters != null && !adapters.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlElementImpl#initializeAttributeNodes(org.w3c.dom.Document, org.w3c.dom.Element)
	 */
	@Override
	public void updateAttributeNodes() {
		super.updateAttributeNodes();

		DrlResourceImpl resource = (DrlResourceImpl) this.eResource();
		XMLHelper helper = resource.getHelper();
		
		Element elem = getNode();
		
		// id
		String idAttrName = 
			DrlFactory.eINSTANCE.getDrlPackage().getFinalInfProduct_Id().getName();
		
		elem.setAttribute(idAttrName, getId());
		
		// product
		String productAttrName =
			DrlFactory.eINSTANCE.getDrlPackage().getFinalInfProduct_Product().getName();
		
		InfProduct product = getProduct();
		String productHref = product == null? "" : helper.getHREF(product);
		elem.setAttribute(productAttrName, productHref);
	}

	
} //FinalInfProductImpl