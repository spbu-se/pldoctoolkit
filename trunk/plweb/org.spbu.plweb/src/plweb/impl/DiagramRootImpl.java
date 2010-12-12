/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package plweb.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import plweb.Area;
import plweb.DiagramRoot;
import plweb.DiagramType;
import plweb.PlwebPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diagram Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link plweb.impl.DiagramRootImpl#getArea <em>Area</em>}</li>
 *   <li>{@link plweb.impl.DiagramRootImpl#getProjectPath <em>Project Path</em>}</li>
 *   <li>{@link plweb.impl.DiagramRootImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DiagramRootImpl extends EObjectImpl implements DiagramRoot {
	/**
	 * The cached value of the '{@link #getArea() <em>Area</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArea()
	 * @generated
	 * @ordered
	 */
	protected Area area;

	/**
	 * The default value of the '{@link #getProjectPath() <em>Project Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProjectPath() <em>Project Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectPath()
	 * @generated
	 * @ordered
	 */
	protected String projectPath = PROJECT_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final DiagramType TYPE_EDEFAULT = DiagramType.MODEL;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected DiagramType type = TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DiagramRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PlwebPackage.Literals.DIAGRAM_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArea(Area newArea, NotificationChain msgs) {
		Area oldArea = area;
		area = newArea;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PlwebPackage.DIAGRAM_ROOT__AREA, oldArea, newArea);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArea(Area newArea) {
		if (newArea != area) {
			NotificationChain msgs = null;
			if (area != null)
				msgs = ((InternalEObject)area).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PlwebPackage.DIAGRAM_ROOT__AREA, null, msgs);
			if (newArea != null)
				msgs = ((InternalEObject)newArea).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PlwebPackage.DIAGRAM_ROOT__AREA, null, msgs);
			msgs = basicSetArea(newArea, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PlwebPackage.DIAGRAM_ROOT__AREA, newArea, newArea));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProjectPath() {
		return projectPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectPath(String newProjectPath) {
		String oldProjectPath = projectPath;
		projectPath = newProjectPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PlwebPackage.DIAGRAM_ROOT__PROJECT_PATH, oldProjectPath, projectPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiagramType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(DiagramType newType) {
		DiagramType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PlwebPackage.DIAGRAM_ROOT__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PlwebPackage.DIAGRAM_ROOT__AREA:
				return basicSetArea(null, msgs);
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
			case PlwebPackage.DIAGRAM_ROOT__AREA:
				return getArea();
			case PlwebPackage.DIAGRAM_ROOT__PROJECT_PATH:
				return getProjectPath();
			case PlwebPackage.DIAGRAM_ROOT__TYPE:
				return getType();
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
			case PlwebPackage.DIAGRAM_ROOT__AREA:
				setArea((Area)newValue);
				return;
			case PlwebPackage.DIAGRAM_ROOT__PROJECT_PATH:
				setProjectPath((String)newValue);
				return;
			case PlwebPackage.DIAGRAM_ROOT__TYPE:
				setType((DiagramType)newValue);
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
			case PlwebPackage.DIAGRAM_ROOT__AREA:
				setArea((Area)null);
				return;
			case PlwebPackage.DIAGRAM_ROOT__PROJECT_PATH:
				setProjectPath(PROJECT_PATH_EDEFAULT);
				return;
			case PlwebPackage.DIAGRAM_ROOT__TYPE:
				setType(TYPE_EDEFAULT);
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
			case PlwebPackage.DIAGRAM_ROOT__AREA:
				return area != null;
			case PlwebPackage.DIAGRAM_ROOT__PROJECT_PATH:
				return PROJECT_PATH_EDEFAULT == null ? projectPath != null : !PROJECT_PATH_EDEFAULT.equals(projectPath);
			case PlwebPackage.DIAGRAM_ROOT__TYPE:
				return type != TYPE_EDEFAULT;
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
		result.append(" (projectPath: ");
		result.append(projectPath);
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} //DiagramRootImpl
