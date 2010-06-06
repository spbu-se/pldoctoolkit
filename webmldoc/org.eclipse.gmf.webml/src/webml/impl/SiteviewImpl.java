/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package webml.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import webml.Siteview;
import webml.Unit;
import webml.WebmlPackage;
import webml.koLink;
import webml.normalLink;
import webml.okLink;
import webml.transportLink;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Siteview</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link webml.impl.SiteviewImpl#getElement <em>Element</em>}</li>
 *   <li>{@link webml.impl.SiteviewImpl#getOklink <em>Oklink</em>}</li>
 *   <li>{@link webml.impl.SiteviewImpl#getKolink <em>Kolink</em>}</li>
 *   <li>{@link webml.impl.SiteviewImpl#getNlink <em>Nlink</em>}</li>
 *   <li>{@link webml.impl.SiteviewImpl#getTlink <em>Tlink</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SiteviewImpl extends EObjectImpl implements Siteview {
	/**
	 * The cached value of the '{@link #getElement() <em>Element</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElement()
	 * @generated
	 * @ordered
	 */
	protected EList<Unit> element;

	/**
	 * The cached value of the '{@link #getOklink() <em>Oklink</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOklink()
	 * @generated
	 * @ordered
	 */
	protected EList<okLink> oklink;

	/**
	 * The cached value of the '{@link #getKolink() <em>Kolink</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKolink()
	 * @generated
	 * @ordered
	 */
	protected EList<koLink> kolink;

	/**
	 * The cached value of the '{@link #getNlink() <em>Nlink</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNlink()
	 * @generated
	 * @ordered
	 */
	protected EList<normalLink> nlink;

	/**
	 * The cached value of the '{@link #getTlink() <em>Tlink</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTlink()
	 * @generated
	 * @ordered
	 */
	protected EList<transportLink> tlink;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SiteviewImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WebmlPackage.Literals.SITEVIEW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Unit> getElement() {
		if (element == null) {
			element = new EObjectContainmentEList<Unit>(Unit.class, this, WebmlPackage.SITEVIEW__ELEMENT);
		}
		return element;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<okLink> getOklink() {
		if (oklink == null) {
			oklink = new EObjectContainmentEList<okLink>(okLink.class, this, WebmlPackage.SITEVIEW__OKLINK);
		}
		return oklink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<koLink> getKolink() {
		if (kolink == null) {
			kolink = new EObjectContainmentEList<koLink>(koLink.class, this, WebmlPackage.SITEVIEW__KOLINK);
		}
		return kolink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<normalLink> getNlink() {
		if (nlink == null) {
			nlink = new EObjectContainmentEList<normalLink>(normalLink.class, this, WebmlPackage.SITEVIEW__NLINK);
		}
		return nlink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<transportLink> getTlink() {
		if (tlink == null) {
			tlink = new EObjectContainmentEList<transportLink>(transportLink.class, this, WebmlPackage.SITEVIEW__TLINK);
		}
		return tlink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case WebmlPackage.SITEVIEW__ELEMENT:
				return ((InternalEList<?>)getElement()).basicRemove(otherEnd, msgs);
			case WebmlPackage.SITEVIEW__OKLINK:
				return ((InternalEList<?>)getOklink()).basicRemove(otherEnd, msgs);
			case WebmlPackage.SITEVIEW__KOLINK:
				return ((InternalEList<?>)getKolink()).basicRemove(otherEnd, msgs);
			case WebmlPackage.SITEVIEW__NLINK:
				return ((InternalEList<?>)getNlink()).basicRemove(otherEnd, msgs);
			case WebmlPackage.SITEVIEW__TLINK:
				return ((InternalEList<?>)getTlink()).basicRemove(otherEnd, msgs);
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
			case WebmlPackage.SITEVIEW__ELEMENT:
				return getElement();
			case WebmlPackage.SITEVIEW__OKLINK:
				return getOklink();
			case WebmlPackage.SITEVIEW__KOLINK:
				return getKolink();
			case WebmlPackage.SITEVIEW__NLINK:
				return getNlink();
			case WebmlPackage.SITEVIEW__TLINK:
				return getTlink();
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
			case WebmlPackage.SITEVIEW__ELEMENT:
				getElement().clear();
				getElement().addAll((Collection<? extends Unit>)newValue);
				return;
			case WebmlPackage.SITEVIEW__OKLINK:
				getOklink().clear();
				getOklink().addAll((Collection<? extends okLink>)newValue);
				return;
			case WebmlPackage.SITEVIEW__KOLINK:
				getKolink().clear();
				getKolink().addAll((Collection<? extends koLink>)newValue);
				return;
			case WebmlPackage.SITEVIEW__NLINK:
				getNlink().clear();
				getNlink().addAll((Collection<? extends normalLink>)newValue);
				return;
			case WebmlPackage.SITEVIEW__TLINK:
				getTlink().clear();
				getTlink().addAll((Collection<? extends transportLink>)newValue);
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
			case WebmlPackage.SITEVIEW__ELEMENT:
				getElement().clear();
				return;
			case WebmlPackage.SITEVIEW__OKLINK:
				getOklink().clear();
				return;
			case WebmlPackage.SITEVIEW__KOLINK:
				getKolink().clear();
				return;
			case WebmlPackage.SITEVIEW__NLINK:
				getNlink().clear();
				return;
			case WebmlPackage.SITEVIEW__TLINK:
				getTlink().clear();
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
			case WebmlPackage.SITEVIEW__ELEMENT:
				return element != null && !element.isEmpty();
			case WebmlPackage.SITEVIEW__OKLINK:
				return oklink != null && !oklink.isEmpty();
			case WebmlPackage.SITEVIEW__KOLINK:
				return kolink != null && !kolink.isEmpty();
			case WebmlPackage.SITEVIEW__NLINK:
				return nlink != null && !nlink.isEmpty();
			case WebmlPackage.SITEVIEW__TLINK:
				return tlink != null && !tlink.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SiteviewImpl
