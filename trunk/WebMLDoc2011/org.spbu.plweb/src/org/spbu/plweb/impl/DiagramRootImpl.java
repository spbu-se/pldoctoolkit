/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.plweb.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.spbu.plweb.DiagramRoot;
import org.spbu.plweb.DiagramType;
import org.spbu.plweb.PlwebPackage;
import org.spbu.plweb.Root;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diagram Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.plweb.impl.DiagramRootImpl#getRoot <em>Root</em>}</li>
 *   <li>{@link org.spbu.plweb.impl.DiagramRootImpl#getProjectPath <em>Project Path</em>}</li>
 *   <li>{@link org.spbu.plweb.impl.DiagramRootImpl#getDiagramType <em>Diagram Type</em>}</li>
 *   <li>{@link org.spbu.plweb.impl.DiagramRootImpl#getDocPath <em>Doc Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DiagramRootImpl extends EObjectImpl implements DiagramRoot {
	/**
	 * The cached value of the '{@link #getRoot() <em>Root</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoot()
	 * @generated
	 * @ordered
	 */
	protected Root root;

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
	 * The default value of the '{@link #getDiagramType() <em>Diagram Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiagramType()
	 * @generated
	 * @ordered
	 */
	protected static final DiagramType DIAGRAM_TYPE_EDEFAULT = DiagramType.MODEL;

	/**
	 * The cached value of the '{@link #getDiagramType() <em>Diagram Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiagramType()
	 * @generated
	 * @ordered
	 */
	protected DiagramType diagramType = DIAGRAM_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDocPath() <em>Doc Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocPath()
	 * @generated
	 * @ordered
	 */
	protected static final String DOC_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDocPath() <em>Doc Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocPath()
	 * @generated
	 * @ordered
	 */
	protected String docPath = DOC_PATH_EDEFAULT;

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
	public Root getRoot() {
		return root;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRoot(Root newRoot, NotificationChain msgs) {
		Root oldRoot = root;
		root = newRoot;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PlwebPackage.DIAGRAM_ROOT__ROOT, oldRoot, newRoot);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRoot(Root newRoot) {
		if (newRoot != root) {
			NotificationChain msgs = null;
			if (root != null)
				msgs = ((InternalEObject)root).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PlwebPackage.DIAGRAM_ROOT__ROOT, null, msgs);
			if (newRoot != null)
				msgs = ((InternalEObject)newRoot).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PlwebPackage.DIAGRAM_ROOT__ROOT, null, msgs);
			msgs = basicSetRoot(newRoot, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PlwebPackage.DIAGRAM_ROOT__ROOT, newRoot, newRoot));
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
	public DiagramType getDiagramType() {
		return diagramType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDiagramType(DiagramType newDiagramType) {
		DiagramType oldDiagramType = diagramType;
		diagramType = newDiagramType == null ? DIAGRAM_TYPE_EDEFAULT : newDiagramType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PlwebPackage.DIAGRAM_ROOT__DIAGRAM_TYPE, oldDiagramType, diagramType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDocPath() {
		return docPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocPath(String newDocPath) {
		String oldDocPath = docPath;
		docPath = newDocPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PlwebPackage.DIAGRAM_ROOT__DOC_PATH, oldDocPath, docPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PlwebPackage.DIAGRAM_ROOT__ROOT:
				return basicSetRoot(null, msgs);
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
			case PlwebPackage.DIAGRAM_ROOT__ROOT:
				return getRoot();
			case PlwebPackage.DIAGRAM_ROOT__PROJECT_PATH:
				return getProjectPath();
			case PlwebPackage.DIAGRAM_ROOT__DIAGRAM_TYPE:
				return getDiagramType();
			case PlwebPackage.DIAGRAM_ROOT__DOC_PATH:
				return getDocPath();
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
			case PlwebPackage.DIAGRAM_ROOT__ROOT:
				setRoot((Root)newValue);
				return;
			case PlwebPackage.DIAGRAM_ROOT__PROJECT_PATH:
				setProjectPath((String)newValue);
				return;
			case PlwebPackage.DIAGRAM_ROOT__DIAGRAM_TYPE:
				setDiagramType((DiagramType)newValue);
				return;
			case PlwebPackage.DIAGRAM_ROOT__DOC_PATH:
				setDocPath((String)newValue);
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
			case PlwebPackage.DIAGRAM_ROOT__ROOT:
				setRoot((Root)null);
				return;
			case PlwebPackage.DIAGRAM_ROOT__PROJECT_PATH:
				setProjectPath(PROJECT_PATH_EDEFAULT);
				return;
			case PlwebPackage.DIAGRAM_ROOT__DIAGRAM_TYPE:
				setDiagramType(DIAGRAM_TYPE_EDEFAULT);
				return;
			case PlwebPackage.DIAGRAM_ROOT__DOC_PATH:
				setDocPath(DOC_PATH_EDEFAULT);
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
			case PlwebPackage.DIAGRAM_ROOT__ROOT:
				return root != null;
			case PlwebPackage.DIAGRAM_ROOT__PROJECT_PATH:
				return PROJECT_PATH_EDEFAULT == null ? projectPath != null : !PROJECT_PATH_EDEFAULT.equals(projectPath);
			case PlwebPackage.DIAGRAM_ROOT__DIAGRAM_TYPE:
				return diagramType != DIAGRAM_TYPE_EDEFAULT;
			case PlwebPackage.DIAGRAM_ROOT__DOC_PATH:
				return DOC_PATH_EDEFAULT == null ? docPath != null : !DOC_PATH_EDEFAULT.equals(docPath);
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
		result.append(", DiagramType: ");
		result.append(diagramType);
		result.append(", docPath: ");
		result.append(docPath);
		result.append(')');
		return result.toString();
	}

} //DiagramRootImpl
