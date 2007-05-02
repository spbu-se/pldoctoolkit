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
import org.spbu.pldoctoolkit.graph.DrlFactory;
import org.spbu.pldoctoolkit.graph.DrlGraphPlugin;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElemRefGroup;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic Document Part</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.GenericDocumentPartImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.GenericDocumentPartImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.GenericDocumentPartImpl#getInfElemRefs <em>Inf Elem Refs</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.GenericDocumentPartImpl#getGroups <em>Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class GenericDocumentPartImpl extends DrlElementImpl implements GenericDocumentPart {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

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
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInfElemRefs() <em>Inf Elem Refs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfElemRefs()
	 * @generated
	 * @ordered
	 */
	protected EList infElemRefs;

	/**
	 * The cached value of the '{@link #getGroups() <em>Groups</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroups()
	 * @generated
	 * @ordered
	 */
	protected EList groups;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenericDocumentPartImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DrlPackage.Literals.GENERIC_DOCUMENT_PART;
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
	 * @generated NOT
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		
		//HAND
		Element node = getNode();
		if(node != null) {
			node.setAttribute(
					DrlFactory.eINSTANCE.getDrlPackage().getGenericDocumentPart_Id().getName(), 
					newId);
		} else {
			DrlGraphPlugin.logInfo("node is null");
		}
		
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.GENERIC_DOCUMENT_PART__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		
		//HAND
		Element node = getNode();
		if(node != null) {
			node.setAttribute(
					DrlFactory.eINSTANCE.getDrlPackage().getGenericDocumentPart_Name().getName(),
					newName);
		}
		
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.GENERIC_DOCUMENT_PART__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getInfElemRefs() {
		if (infElemRefs == null) {
			infElemRefs = new EObjectContainmentEList(InfElemRef.class, this, DrlPackage.GENERIC_DOCUMENT_PART__INF_ELEM_REFS);
		}
		return infElemRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getGroups() {
		if (groups == null) {
			groups = new EObjectContainmentEList(InfElemRefGroup.class, this, DrlPackage.GENERIC_DOCUMENT_PART__GROUPS);
		}
		return groups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DrlPackage.GENERIC_DOCUMENT_PART__INF_ELEM_REFS:
				return ((InternalEList)getInfElemRefs()).basicRemove(otherEnd, msgs);
			case DrlPackage.GENERIC_DOCUMENT_PART__GROUPS:
				return ((InternalEList)getGroups()).basicRemove(otherEnd, msgs);
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
			case DrlPackage.GENERIC_DOCUMENT_PART__ID:
				return getId();
			case DrlPackage.GENERIC_DOCUMENT_PART__NAME:
				return getName();
			case DrlPackage.GENERIC_DOCUMENT_PART__INF_ELEM_REFS:
				return getInfElemRefs();
			case DrlPackage.GENERIC_DOCUMENT_PART__GROUPS:
				return getGroups();
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
			case DrlPackage.GENERIC_DOCUMENT_PART__ID:
				setId((String)newValue);
				return;
			case DrlPackage.GENERIC_DOCUMENT_PART__NAME:
				setName((String)newValue);
				return;
			case DrlPackage.GENERIC_DOCUMENT_PART__INF_ELEM_REFS:
				getInfElemRefs().clear();
				getInfElemRefs().addAll((Collection)newValue);
				return;
			case DrlPackage.GENERIC_DOCUMENT_PART__GROUPS:
				getGroups().clear();
				getGroups().addAll((Collection)newValue);
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
			case DrlPackage.GENERIC_DOCUMENT_PART__ID:
				setId(ID_EDEFAULT);
				return;
			case DrlPackage.GENERIC_DOCUMENT_PART__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DrlPackage.GENERIC_DOCUMENT_PART__INF_ELEM_REFS:
				getInfElemRefs().clear();
				return;
			case DrlPackage.GENERIC_DOCUMENT_PART__GROUPS:
				getGroups().clear();
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
			case DrlPackage.GENERIC_DOCUMENT_PART__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DrlPackage.GENERIC_DOCUMENT_PART__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DrlPackage.GENERIC_DOCUMENT_PART__INF_ELEM_REFS:
				return infElemRefs != null && !infElemRefs.isEmpty();
			case DrlPackage.GENERIC_DOCUMENT_PART__GROUPS:
				return groups != null && !groups.isEmpty();
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
		result.append(" (id: ");
		result.append(id);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlElementImpl#initializeAttributeNodes(org.w3c.dom.Document, org.w3c.dom.Element)
	 */
	@Override
	public void updateAttributeNodes() {
		super.updateAttributeNodes();
		
		Element elem = getNode();
		
		// id
		String idAttrName = 
			DrlFactory.eINSTANCE.getDrlPackage().getGenericDocumentPart_Id().getName();
		
		elem.setAttribute(idAttrName, getId());

		// name
		String nameAttrName = 
			DrlFactory.eINSTANCE.getDrlPackage().getGenericDocumentPart_Name().getName();
		
		elem.setAttribute(nameAttrName, getName());
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlElementImpl#needTypeInfo()
	 */
	@Override
	protected boolean needTypeInfo() {
		return true;
	}
	
	
} //GenericDocumentPartImpl