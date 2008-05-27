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
import org.spbu.pldoctoolkit.graph.DrlFactory;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.Product;
import org.spbu.pldoctoolkit.graph.ProductDocumentation;
import org.spbu.pldoctoolkit.graph.ProductLine;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Product Line</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.ProductLineImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.ProductLineImpl#getProducts <em>Products</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.ProductLineImpl#getProductDocumentations <em>Product Documentations</em>}</li>
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
	 * The cached value of the '{@link #getProducts() <em>Products</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProducts()
	 * @generated
	 * @ordered
	 */
	protected EList<Product> products;

	/**
	 * The cached value of the '{@link #getProductDocumentations() <em>Product Documentations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProductDocumentations()
	 * @generated
	 * @ordered
	 */
	protected EList<ProductDocumentation> productDocumentations;

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
	@Override
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
	public EList<Product> getProducts() {
		if (products == null) {
			products = new EObjectContainmentEList<Product>(Product.class, this, DrlPackage.PRODUCT_LINE__PRODUCTS);
		}
		return products;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProductDocumentation> getProductDocumentations() {
		if (productDocumentations == null) {
			productDocumentations = new EObjectContainmentEList<ProductDocumentation>(ProductDocumentation.class, this, DrlPackage.PRODUCT_LINE__PRODUCT_DOCUMENTATIONS);
		}
		return productDocumentations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DrlPackage.PRODUCT_LINE__PRODUCTS:
				return ((InternalEList<?>)getProducts()).basicRemove(otherEnd, msgs);
			case DrlPackage.PRODUCT_LINE__PRODUCT_DOCUMENTATIONS:
				return ((InternalEList<?>)getProductDocumentations()).basicRemove(otherEnd, msgs);
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
			case DrlPackage.PRODUCT_LINE__NAME:
				return getName();
			case DrlPackage.PRODUCT_LINE__PRODUCTS:
				return getProducts();
			case DrlPackage.PRODUCT_LINE__PRODUCT_DOCUMENTATIONS:
				return getProductDocumentations();
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
			case DrlPackage.PRODUCT_LINE__NAME:
				setName((String)newValue);
				return;
			case DrlPackage.PRODUCT_LINE__PRODUCTS:
				getProducts().clear();
				getProducts().addAll((Collection<? extends Product>)newValue);
				return;
			case DrlPackage.PRODUCT_LINE__PRODUCT_DOCUMENTATIONS:
				getProductDocumentations().clear();
				getProductDocumentations().addAll((Collection<? extends ProductDocumentation>)newValue);
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
			case DrlPackage.PRODUCT_LINE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DrlPackage.PRODUCT_LINE__PRODUCTS:
				getProducts().clear();
				return;
			case DrlPackage.PRODUCT_LINE__PRODUCT_DOCUMENTATIONS:
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
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DrlPackage.PRODUCT_LINE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DrlPackage.PRODUCT_LINE__PRODUCTS:
				return products != null && !products.isEmpty();
			case DrlPackage.PRODUCT_LINE__PRODUCT_DOCUMENTATIONS:
				return productDocumentations != null && !productDocumentations.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlElementImpl#initializeAttributeNodes(org.w3c.dom.Element)
	 */
	@Override
	public void updateAttributeNodes() {
		super.updateAttributeNodes();
		
		Element elem = getNode();
		
		// name
		String nameAttrName = 
			DrlFactory.eINSTANCE.getDrlPackage().getProductLine_Name().getName();
		
		elem.setAttribute(nameAttrName, getName());
	}

} //ProductLineImpl