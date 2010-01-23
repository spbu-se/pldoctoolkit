/**
 * copyleft 2007
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.spbu.pldoctoolkit.graph.Adapter;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.InfElement;
import org.spbu.pldoctoolkit.graph.Nest;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Adapter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.AdapterImpl#getNests <em>Nests</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.AdapterImpl#getInfelemrefid <em>Infelemrefid</em>}</li>
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
	 * The cached value of the '{@link #getNests() <em>Nests</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNests()
	 * @generated
	 * @ordered
	 */
	protected Nest nests;

	/**
	 * The default value of the '{@link #getInfelemrefid() <em>Infelemrefid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfelemrefid()
	 * @generated
	 * @ordered
	 */
	protected static final String INFELEMREFID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInfelemrefid() <em>Infelemrefid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfelemrefid()
	 * @generated
	 * @ordered
	 */
	protected String infelemrefid = INFELEMREFID_EDEFAULT;

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
	@Override
	protected EClass eStaticClass() {
		return DrlPackage.Literals.ADAPTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Nest getNests() {
		return nests;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNests(Nest newNests, NotificationChain msgs) {
		Nest oldNests = nests;
		nests = newNests;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DrlPackage.ADAPTER__NESTS, oldNests, newNests);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNests(Nest newNests) {
		if (newNests != nests) {
			NotificationChain msgs = null;
			if (nests != null)
				msgs = ((InternalEObject)nests).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DrlPackage.ADAPTER__NESTS, null, msgs);
			if (newNests != null)
				msgs = ((InternalEObject)newNests).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DrlPackage.ADAPTER__NESTS, null, msgs);
			msgs = basicSetNests(newNests, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.ADAPTER__NESTS, newNests, newNests));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInfelemrefid() {
		return infelemrefid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInfelemrefid(String newInfelemrefid) {
		String oldInfelemrefid = infelemrefid;
		infelemrefid = newInfelemrefid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.ADAPTER__INFELEMREFID, oldInfelemrefid, infelemrefid));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DrlPackage.ADAPTER__NESTS:
				return basicSetNests(null, msgs);
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
			case DrlPackage.ADAPTER__NESTS:
				return getNests();
			case DrlPackage.ADAPTER__INFELEMREFID:
				return getInfelemrefid();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DrlPackage.ADAPTER__NESTS:
				setNests((Nest)newValue);
				return;
			case DrlPackage.ADAPTER__INFELEMREFID:
				setInfelemrefid((String)newValue);
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
			case DrlPackage.ADAPTER__NESTS:
				setNests((Nest)null);
				return;
			case DrlPackage.ADAPTER__INFELEMREFID:
				setInfelemrefid(INFELEMREFID_EDEFAULT);
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
			case DrlPackage.ADAPTER__NESTS:
				return nests != null;
			case DrlPackage.ADAPTER__INFELEMREFID:
				return INFELEMREFID_EDEFAULT == null ? infelemrefid != null : !INFELEMREFID_EDEFAULT.equals(infelemrefid);
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
		result.append(" (infelemrefid: ");
		result.append(infelemrefid);
		result.append(')');
		return result.toString();
	}

} //AdapterImpl
