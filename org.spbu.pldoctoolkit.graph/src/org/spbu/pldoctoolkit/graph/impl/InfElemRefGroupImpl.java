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
import org.eclipse.emf.ecore.util.EObjectWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.spbu.pldoctoolkit.graph.DrlFactory;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GroupType;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElemRefGroup;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Inf Elem Ref Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.InfElemRefGroupImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.InfElemRefGroupImpl#getModifier <em>Modifier</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.InfElemRefGroupImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.InfElemRefGroupImpl#getInfElemRefsGroup <em>Inf Elem Refs Group</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InfElemRefGroupImpl extends DrlElementImpl implements InfElemRefGroup {
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
	 * The default value of the '{@link #getModifier() <em>Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModifier()
	 * @generated
	 * @ordered
	 */
	protected static final GroupType MODIFIER_EDEFAULT = GroupType.OR_LITERAL;

	/**
	 * The cached value of the '{@link #getModifier() <em>Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModifier()
	 * @generated
	 * @ordered
	 */
	protected GroupType modifier = MODIFIER_EDEFAULT;

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
	 * The cached value of the '{@link #getInfElemRefsGroup() <em>Inf Elem Refs Group</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfElemRefsGroup()
	 * @generated
	 * @ordered
	 */
	protected EList infElemRefsGroup;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InfElemRefGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DrlPackage.Literals.INF_ELEM_REF_GROUP;
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
					DrlFactory.eINSTANCE.getDrlPackage().getInfElemRefGroup_Id().getName(),
					modifier.getName());
		}
		
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.INF_ELEM_REF_GROUP__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroupType getModifier() {
		return modifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModifier(GroupType newModifier) {
		GroupType oldModifier = modifier;
		modifier = newModifier == null ? MODIFIER_EDEFAULT : newModifier;
		
		//HAND
		Element node = getNode();
		if(node != null) {
			node.setAttribute(
					DrlFactory.eINSTANCE.getDrlPackage().getInfElemRefGroup_Modifier().getName(),
					modifier.getName());
		}		
		
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.INF_ELEM_REF_GROUP__MODIFIER, oldModifier, modifier));
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
					DrlFactory.eINSTANCE.getDrlPackage().getInfElemRefGroup_Id().getName(),
					newName);
		}
		
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.INF_ELEM_REF_GROUP__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getInfElemRefsGroup() {
		if (infElemRefsGroup == null) {
			infElemRefsGroup = new EObjectWithInverseEList(InfElemRef.class, this, DrlPackage.INF_ELEM_REF_GROUP__INF_ELEM_REFS_GROUP, DrlPackage.INF_ELEM_REF__GROUP);
		}
		return infElemRefsGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DrlPackage.INF_ELEM_REF_GROUP__INF_ELEM_REFS_GROUP:
				return ((InternalEList)getInfElemRefsGroup()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DrlPackage.INF_ELEM_REF_GROUP__INF_ELEM_REFS_GROUP:
				return ((InternalEList)getInfElemRefsGroup()).basicRemove(otherEnd, msgs);
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
			case DrlPackage.INF_ELEM_REF_GROUP__ID:
				return getId();
			case DrlPackage.INF_ELEM_REF_GROUP__MODIFIER:
				return getModifier();
			case DrlPackage.INF_ELEM_REF_GROUP__NAME:
				return getName();
			case DrlPackage.INF_ELEM_REF_GROUP__INF_ELEM_REFS_GROUP:
				return getInfElemRefsGroup();
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
			case DrlPackage.INF_ELEM_REF_GROUP__ID:
				setId((String)newValue);
				return;
			case DrlPackage.INF_ELEM_REF_GROUP__MODIFIER:
				setModifier((GroupType)newValue);
				return;
			case DrlPackage.INF_ELEM_REF_GROUP__NAME:
				setName((String)newValue);
				return;
			case DrlPackage.INF_ELEM_REF_GROUP__INF_ELEM_REFS_GROUP:
				getInfElemRefsGroup().clear();
				getInfElemRefsGroup().addAll((Collection)newValue);
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
			case DrlPackage.INF_ELEM_REF_GROUP__ID:
				setId(ID_EDEFAULT);
				return;
			case DrlPackage.INF_ELEM_REF_GROUP__MODIFIER:
				setModifier(MODIFIER_EDEFAULT);
				return;
			case DrlPackage.INF_ELEM_REF_GROUP__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DrlPackage.INF_ELEM_REF_GROUP__INF_ELEM_REFS_GROUP:
				getInfElemRefsGroup().clear();
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
			case DrlPackage.INF_ELEM_REF_GROUP__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DrlPackage.INF_ELEM_REF_GROUP__MODIFIER:
				return modifier != MODIFIER_EDEFAULT;
			case DrlPackage.INF_ELEM_REF_GROUP__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DrlPackage.INF_ELEM_REF_GROUP__INF_ELEM_REFS_GROUP:
				return infElemRefsGroup != null && !infElemRefsGroup.isEmpty();
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
		result.append(", modifier: ");
		result.append(modifier);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlElementImpl#initializeAttributeNodes(org.w3c.dom.Element)
	 */
	@Override
	protected void initializeAttributeNodes(Element elem) {
		super.initializeAttributeNodes(elem);

		// id
		String idAttrName = 
			DrlFactory.eINSTANCE.getDrlPackage().getInfElemRefGroup_Id().getName();
		
		elem.setAttribute(idAttrName, getId());

		// name
		String nameAttrName = 
			DrlFactory.eINSTANCE.getDrlPackage().getInfElemRefGroup_Name().getName();
		
		elem.setAttribute(nameAttrName, getName());
		
		// modifier
		String modifierAttrName = 
			DrlFactory.eINSTANCE.getDrlPackage().getInfElemRefGroup_Modifier().getName();
		
		elem.setAttribute(modifierAttrName, getModifier().getName());
	}

} //InfElemRefGroupImpl