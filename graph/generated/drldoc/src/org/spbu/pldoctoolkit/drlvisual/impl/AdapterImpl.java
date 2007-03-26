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

import org.spbu.pldoctoolkit.drlvisual.Adapter;
import org.spbu.pldoctoolkit.drlvisual.DrlPackage;
import org.spbu.pldoctoolkit.drlvisual.InfElemRef;
import org.spbu.pldoctoolkit.drlvisual.NestPointRef;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Adapter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.impl.AdapterImpl#getNestPointRefs <em>Nest Point Refs</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.impl.AdapterImpl#getInfElemRef <em>Inf Elem Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AdapterImpl extends DrlElementImpl implements Adapter {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * The cached value of the '{@link #getNestPointRefs() <em>Nest Point Refs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNestPointRefs()
	 * @generated
	 * @ordered
	 */
	protected EList nestPointRefs = null;

	/**
	 * The cached value of the '{@link #getInfElemRef() <em>Inf Elem Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfElemRef()
	 * @generated
	 * @ordered
	 */
	protected InfElemRef infElemRef = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdapterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DrlPackage.Literals.ADAPTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getNestPointRefs() {
		if (nestPointRefs == null) {
			nestPointRefs = new EObjectContainmentEList(NestPointRef.class, this, DrlPackage.ADAPTER__NEST_POINT_REFS);
		}
		return nestPointRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfElemRef getInfElemRef() {
		if (infElemRef != null && infElemRef.eIsProxy()) {
			InternalEObject oldInfElemRef = (InternalEObject)infElemRef;
			infElemRef = (InfElemRef)eResolveProxy(oldInfElemRef);
			if (infElemRef != oldInfElemRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DrlPackage.ADAPTER__INF_ELEM_REF, oldInfElemRef, infElemRef));
			}
		}
		return infElemRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfElemRef basicGetInfElemRef() {
		return infElemRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInfElemRef(InfElemRef newInfElemRef) {
		InfElemRef oldInfElemRef = infElemRef;
		infElemRef = newInfElemRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.ADAPTER__INF_ELEM_REF, oldInfElemRef, infElemRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DrlPackage.ADAPTER__NEST_POINT_REFS:
				return ((InternalEList)getNestPointRefs()).basicRemove(otherEnd, msgs);
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
			case DrlPackage.ADAPTER__NEST_POINT_REFS:
				return getNestPointRefs();
			case DrlPackage.ADAPTER__INF_ELEM_REF:
				if (resolve) return getInfElemRef();
				return basicGetInfElemRef();
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
			case DrlPackage.ADAPTER__NEST_POINT_REFS:
				getNestPointRefs().clear();
				getNestPointRefs().addAll((Collection)newValue);
				return;
			case DrlPackage.ADAPTER__INF_ELEM_REF:
				setInfElemRef((InfElemRef)newValue);
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
			case DrlPackage.ADAPTER__NEST_POINT_REFS:
				getNestPointRefs().clear();
				return;
			case DrlPackage.ADAPTER__INF_ELEM_REF:
				setInfElemRef((InfElemRef)null);
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
			case DrlPackage.ADAPTER__NEST_POINT_REFS:
				return nestPointRefs != null && !nestPointRefs.isEmpty();
			case DrlPackage.ADAPTER__INF_ELEM_REF:
				return infElemRef != null;
		}
		return super.eIsSet(featureID);
	}

} //AdapterImpl