/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.NestPoint;
import org.spbu.pldoctoolkit.graph.NestPointRef;
import org.spbu.pldoctoolkit.graph.NestPointType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Nest Point Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.NestPointRefImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.NestPointRefImpl#getText <em>Text</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.NestPointRefImpl#getNestPoint <em>Nest Point</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NestPointRefImpl extends DrlElementImpl implements NestPointRef {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final NestPointType TYPE_EDEFAULT = NestPointType.INSERT_BEFORE_LITERAL;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected NestPointType type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getText() <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getText()
	 * @generated
	 * @ordered
	 */
	protected static final String TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getText() <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getText()
	 * @generated
	 * @ordered
	 */
	protected String text = TEXT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNestPoint() <em>Nest Point</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNestPoint()
	 * @generated
	 * @ordered
	 */
	protected NestPoint nestPoint = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NestPointRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DrlPackage.Literals.NEST_POINT_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestPointType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(NestPointType newType) {
		NestPointType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.NEST_POINT_REF__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText() {
		return text;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setText(String newText) {
		String oldText = text;
		text = newText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.NEST_POINT_REF__TEXT, oldText, text));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestPoint getNestPoint() {
		if (nestPoint != null && nestPoint.eIsProxy()) {
			InternalEObject oldNestPoint = (InternalEObject)nestPoint;
			nestPoint = (NestPoint)eResolveProxy(oldNestPoint);
			if (nestPoint != oldNestPoint) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DrlPackage.NEST_POINT_REF__NEST_POINT, oldNestPoint, nestPoint));
			}
		}
		return nestPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestPoint basicGetNestPoint() {
		return nestPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNestPoint(NestPoint newNestPoint) {
		NestPoint oldNestPoint = nestPoint;
		nestPoint = newNestPoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.NEST_POINT_REF__NEST_POINT, oldNestPoint, nestPoint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DrlPackage.NEST_POINT_REF__TYPE:
				return getType();
			case DrlPackage.NEST_POINT_REF__TEXT:
				return getText();
			case DrlPackage.NEST_POINT_REF__NEST_POINT:
				if (resolve) return getNestPoint();
				return basicGetNestPoint();
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
			case DrlPackage.NEST_POINT_REF__TYPE:
				setType((NestPointType)newValue);
				return;
			case DrlPackage.NEST_POINT_REF__TEXT:
				setText((String)newValue);
				return;
			case DrlPackage.NEST_POINT_REF__NEST_POINT:
				setNestPoint((NestPoint)newValue);
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
			case DrlPackage.NEST_POINT_REF__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case DrlPackage.NEST_POINT_REF__TEXT:
				setText(TEXT_EDEFAULT);
				return;
			case DrlPackage.NEST_POINT_REF__NEST_POINT:
				setNestPoint((NestPoint)null);
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
			case DrlPackage.NEST_POINT_REF__TYPE:
				return type != TYPE_EDEFAULT;
			case DrlPackage.NEST_POINT_REF__TEXT:
				return TEXT_EDEFAULT == null ? text != null : !TEXT_EDEFAULT.equals(text);
			case DrlPackage.NEST_POINT_REF__NEST_POINT:
				return nestPoint != null;
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
		result.append(" (type: ");
		result.append(type);
		result.append(", text: ");
		result.append(text);
		result.append(')');
		return result.toString();
	}

} //NestPointRefImpl