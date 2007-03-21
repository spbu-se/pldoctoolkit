/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.drlvisual.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.spbu.pldoctoolkit.drlvisual.InnerElement;
import org.spbu.pldoctoolkit.drlvisual.SubelementedElement;
import org.spbu.pldoctoolkit.drlvisual.drlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subelemented Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.drlvisual.impl.SubelementedElementImpl#getElements <em>Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class SubelementedElementImpl extends EObjectImpl implements SubelementedElement {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubelementedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return drlPackage.Literals.SUBELEMENTED_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getElements() {
		if (elements == null) {
			elements = new EObjectContainmentEList(InnerElement.class, this, drlPackage.SUBELEMENTED_ELEMENT__ELEMENTS);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case drlPackage.SUBELEMENTED_ELEMENT__ELEMENTS:
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
			case drlPackage.SUBELEMENTED_ELEMENT__ELEMENTS:
				return getElements();
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
			case drlPackage.SUBELEMENTED_ELEMENT__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection)newValue);
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
			case drlPackage.SUBELEMENTED_ELEMENT__ELEMENTS:
				getElements().clear();
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
			case drlPackage.SUBELEMENTED_ELEMENT__ELEMENTS:
				return elements != null && !elements.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SubelementedElementImpl