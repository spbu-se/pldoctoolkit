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

import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.InnerElement;
import org.spbu.pldoctoolkit.graph.NestPoint;
import org.spbu.pldoctoolkit.graph.SubelementedElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Nest Point</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.NestPointImpl#getElements <em>Elements</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.NestPointImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.NestPointImpl#getDescr <em>Descr</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NestPointImpl extends InnerElementImpl implements NestPoint {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList elements = null;

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
	 * The default value of the '{@link #getDescr() <em>Descr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescr()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescr() <em>Descr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescr()
	 * @generated
	 * @ordered
	 */
	protected String descr = DESCR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NestPointImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DrlPackage.Literals.NEST_POINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getElements() {
		if (elements == null) {
			elements = new EObjectContainmentEList(InnerElement.class, this, DrlPackage.NEST_POINT__ELEMENTS);
		}
		return elements;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.NEST_POINT__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescr() {
		return descr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescr(String newDescr) {
		String oldDescr = descr;
		descr = newDescr;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.NEST_POINT__DESCR, oldDescr, descr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DrlPackage.NEST_POINT__ELEMENTS:
				return ((InternalEList)getElements()).basicRemove(otherEnd, msgs);
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
			case DrlPackage.NEST_POINT__ELEMENTS:
				return getElements();
			case DrlPackage.NEST_POINT__ID:
				return getId();
			case DrlPackage.NEST_POINT__DESCR:
				return getDescr();
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
			case DrlPackage.NEST_POINT__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection)newValue);
				return;
			case DrlPackage.NEST_POINT__ID:
				setId((String)newValue);
				return;
			case DrlPackage.NEST_POINT__DESCR:
				setDescr((String)newValue);
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
			case DrlPackage.NEST_POINT__ELEMENTS:
				getElements().clear();
				return;
			case DrlPackage.NEST_POINT__ID:
				setId(ID_EDEFAULT);
				return;
			case DrlPackage.NEST_POINT__DESCR:
				setDescr(DESCR_EDEFAULT);
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
			case DrlPackage.NEST_POINT__ELEMENTS:
				return elements != null && !elements.isEmpty();
			case DrlPackage.NEST_POINT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DrlPackage.NEST_POINT__DESCR:
				return DESCR_EDEFAULT == null ? descr != null : !DESCR_EDEFAULT.equals(descr);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class baseClass) {
		if (baseClass == SubelementedElement.class) {
			switch (derivedFeatureID) {
				case DrlPackage.NEST_POINT__ELEMENTS: return DrlPackage.SUBELEMENTED_ELEMENT__ELEMENTS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class baseClass) {
		if (baseClass == SubelementedElement.class) {
			switch (baseFeatureID) {
				case DrlPackage.SUBELEMENTED_ELEMENT__ELEMENTS: return DrlPackage.NEST_POINT__ELEMENTS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(", descr: ");
		result.append(descr);
		result.append(')');
		return result.toString();
	}

} //NestPointImpl