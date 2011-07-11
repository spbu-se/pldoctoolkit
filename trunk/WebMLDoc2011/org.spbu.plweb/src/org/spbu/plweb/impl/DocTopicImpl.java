/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.plweb.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.spbu.plweb.DocTopic;
import org.spbu.plweb.PlwebPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Doc Topic</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.plweb.impl.DocTopicImpl#getDocTopicName <em>Doc Topic Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocTopicImpl extends EObjectImpl implements DocTopic {
	/**
	 * The default value of the '{@link #getDocTopicName() <em>Doc Topic Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocTopicName()
	 * @generated
	 * @ordered
	 */
	protected static final String DOC_TOPIC_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDocTopicName() <em>Doc Topic Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocTopicName()
	 * @generated
	 * @ordered
	 */
	protected String docTopicName = DOC_TOPIC_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocTopicImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PlwebPackage.Literals.DOC_TOPIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDocTopicName() {
		return docTopicName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocTopicName(String newDocTopicName) {
		String oldDocTopicName = docTopicName;
		docTopicName = newDocTopicName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PlwebPackage.DOC_TOPIC__DOC_TOPIC_NAME, oldDocTopicName, docTopicName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PlwebPackage.DOC_TOPIC__DOC_TOPIC_NAME:
				return getDocTopicName();
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
			case PlwebPackage.DOC_TOPIC__DOC_TOPIC_NAME:
				setDocTopicName((String)newValue);
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
			case PlwebPackage.DOC_TOPIC__DOC_TOPIC_NAME:
				setDocTopicName(DOC_TOPIC_NAME_EDEFAULT);
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
			case PlwebPackage.DOC_TOPIC__DOC_TOPIC_NAME:
				return DOC_TOPIC_NAME_EDEFAULT == null ? docTopicName != null : !DOC_TOPIC_NAME_EDEFAULT.equals(docTopicName);
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
		result.append(" (docTopicName: ");
		result.append(docTopicName);
		result.append(')');
		return result.toString();
	}

} //DocTopicImpl
