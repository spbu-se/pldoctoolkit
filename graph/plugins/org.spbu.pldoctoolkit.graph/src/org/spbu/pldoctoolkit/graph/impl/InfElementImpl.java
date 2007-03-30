/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.InfElement;
import org.spbu.pldoctoolkit.graph.NestPoint;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Inf Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.InfElementImpl#getNestPoints <em>Nest Points</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InfElementImpl extends GenericDocumentPartImpl implements InfElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * The cached value of the '{@link #getNestPoints() <em>Nest Points</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNestPoints()
	 * @generated
	 * @ordered
	 */
	protected NestPoint nestPoints = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InfElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DrlPackage.Literals.INF_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestPoint getNestPoints() {
		return nestPoints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNestPoints(NestPoint newNestPoints, NotificationChain msgs) {
		NestPoint oldNestPoints = nestPoints;
		nestPoints = newNestPoints;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DrlPackage.INF_ELEMENT__NEST_POINTS, oldNestPoints, newNestPoints);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNestPoints(NestPoint newNestPoints) {
		if (newNestPoints != nestPoints) {
			NotificationChain msgs = null;
			if (nestPoints != null)
				msgs = ((InternalEObject)nestPoints).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DrlPackage.INF_ELEMENT__NEST_POINTS, null, msgs);
			if (newNestPoints != null)
				msgs = ((InternalEObject)newNestPoints).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DrlPackage.INF_ELEMENT__NEST_POINTS, null, msgs);
			msgs = basicSetNestPoints(newNestPoints, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.INF_ELEMENT__NEST_POINTS, newNestPoints, newNestPoints));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DrlPackage.INF_ELEMENT__NEST_POINTS:
				return basicSetNestPoints(null, msgs);
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
			case DrlPackage.INF_ELEMENT__NEST_POINTS:
				return getNestPoints();
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
			case DrlPackage.INF_ELEMENT__NEST_POINTS:
				setNestPoints((NestPoint)newValue);
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
			case DrlPackage.INF_ELEMENT__NEST_POINTS:
				setNestPoints((NestPoint)null);
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
			case DrlPackage.INF_ELEMENT__NEST_POINTS:
				return nestPoints != null;
		}
		return super.eIsSet(featureID);
	}

} //InfElementImpl