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
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.spbu.pldoctoolkit.graph.DrlFactory;
import org.spbu.pldoctoolkit.graph.DrlGraphPlugin;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElemRefGroup;
import org.spbu.pldoctoolkit.graph.InfElement;
import org.spbu.pldoctoolkit.graph.util.DrlResourceImpl;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Inf Elem Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.InfElemRefImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.InfElemRefImpl#getInfelem <em>Infelem</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.InfElemRefImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.InfElemRefImpl#isOptional <em>Optional</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InfElemRefImpl extends DrlElementImpl implements InfElemRef {
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
	 * The cached value of the '{@link #getInfelem() <em>Infelem</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfelem()
	 * @generated
	 * @ordered
	 */
	protected InfElement infelem;

	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected InfElemRefGroup group;

	/**
	 * The default value of the '{@link #isOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOptional()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OPTIONAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOptional()
	 * @generated
	 * @ordered
	 */
	protected boolean optional = OPTIONAL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InfElemRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DrlPackage.Literals.INF_ELEM_REF;
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
					DrlFactory.eINSTANCE.getDrlPackage().getInfElemRef_Id().getName(),
					id);
		}
		
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.INF_ELEM_REF__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfElement getInfelem() {
		if (infelem != null && infelem.eIsProxy()) {
			InternalEObject oldInfelem = (InternalEObject)infelem;
			infelem = (InfElement)eResolveProxy(oldInfelem);
			if (infelem != oldInfelem) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DrlPackage.INF_ELEM_REF__INFELEM, oldInfelem, infelem));
			}
		}
		return infelem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfElement basicGetInfelem() {
		return infelem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInfelem(InfElement newInfelem) {
		InfElement oldInfelem = infelem;
		infelem = newInfelem;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.INF_ELEM_REF__INFELEM, oldInfelem, infelem));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfElemRefGroup getGroup() {
		if (group != null && group.eIsProxy()) {
			InternalEObject oldGroup = (InternalEObject)group;
			group = (InfElemRefGroup)eResolveProxy(oldGroup);
			if (group != oldGroup) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DrlPackage.INF_ELEM_REF__GROUP, oldGroup, group));
			}
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfElemRefGroup basicGetGroup() {
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGroup(InfElemRefGroup newGroup, NotificationChain msgs) {
		InfElemRefGroup oldGroup = group;
		group = newGroup;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DrlPackage.INF_ELEM_REF__GROUP, oldGroup, newGroup);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGroup(InfElemRefGroup newGroup) {
		if (newGroup != group) {
			NotificationChain msgs = null;
			if (group != null)
				msgs = ((InternalEObject)group).eInverseRemove(this, DrlPackage.INF_ELEM_REF_GROUP__INF_ELEM_REFS_GROUP, InfElemRefGroup.class, msgs);
			if (newGroup != null)
				msgs = ((InternalEObject)newGroup).eInverseAdd(this, DrlPackage.INF_ELEM_REF_GROUP__INF_ELEM_REFS_GROUP, InfElemRefGroup.class, msgs);
			msgs = basicSetGroup(newGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.INF_ELEM_REF__GROUP, newGroup, newGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOptional() {
		return optional;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setOptional(boolean newOptional) {
		boolean oldOptional = optional;
		optional = newOptional;
		
		//HAND
		Element node = getNode();
		if(node != null) {
			node.setAttribute(
					DrlFactory.eINSTANCE.getDrlPackage().getInfElemRef_Optional().getName(),
					Boolean.toString(optional));
		}
		
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.INF_ELEM_REF__OPTIONAL, oldOptional, optional));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DrlPackage.INF_ELEM_REF__GROUP:
				if (group != null)
					msgs = ((InternalEObject)group).eInverseRemove(this, DrlPackage.INF_ELEM_REF_GROUP__INF_ELEM_REFS_GROUP, InfElemRefGroup.class, msgs);
				return basicSetGroup((InfElemRefGroup)otherEnd, msgs);
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
			case DrlPackage.INF_ELEM_REF__GROUP:
				return basicSetGroup(null, msgs);
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
			case DrlPackage.INF_ELEM_REF__ID:
				return getId();
			case DrlPackage.INF_ELEM_REF__INFELEM:
				if (resolve) return getInfelem();
				return basicGetInfelem();
			case DrlPackage.INF_ELEM_REF__GROUP:
				if (resolve) return getGroup();
				return basicGetGroup();
			case DrlPackage.INF_ELEM_REF__OPTIONAL:
				return isOptional() ? Boolean.TRUE : Boolean.FALSE;
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
			case DrlPackage.INF_ELEM_REF__ID:
				setId((String)newValue);
				return;
			case DrlPackage.INF_ELEM_REF__INFELEM:
				setInfelem((InfElement)newValue);
				return;
			case DrlPackage.INF_ELEM_REF__GROUP:
				setGroup((InfElemRefGroup)newValue);
				return;
			case DrlPackage.INF_ELEM_REF__OPTIONAL:
				setOptional(((Boolean)newValue).booleanValue());
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
			case DrlPackage.INF_ELEM_REF__ID:
				setId(ID_EDEFAULT);
				return;
			case DrlPackage.INF_ELEM_REF__INFELEM:
				setInfelem((InfElement)null);
				return;
			case DrlPackage.INF_ELEM_REF__GROUP:
				setGroup((InfElemRefGroup)null);
				return;
			case DrlPackage.INF_ELEM_REF__OPTIONAL:
				setOptional(OPTIONAL_EDEFAULT);
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
			case DrlPackage.INF_ELEM_REF__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DrlPackage.INF_ELEM_REF__INFELEM:
				return infelem != null;
			case DrlPackage.INF_ELEM_REF__GROUP:
				return group != null;
			case DrlPackage.INF_ELEM_REF__OPTIONAL:
				return optional != OPTIONAL_EDEFAULT;
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
		result.append(", optional: ");
		result.append(optional);
		result.append(')');
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.impl.DrlElementImpl#initializeAttributeNodes(org.w3c.dom.Element)
	 */
	@Override
	public void updateAttributeNodes() {
		super.updateAttributeNodes();

		DrlResourceImpl resource = (DrlResourceImpl) this.eResource();
		XMLHelper helper = resource.getHelper();
		
		Element elem = getNode();
		
		// id
		String idAttrName = 
			DrlFactory.eINSTANCE.getDrlPackage().getInfElemRef_Id().getName();
		
		elem.setAttribute(idAttrName, getId());

		// optional
		String optionalAttrName = 
			DrlFactory.eINSTANCE.getDrlPackage().getInfElemRef_Optional().getName();
		
		elem.setAttribute(optionalAttrName, Boolean.toString(isOptional()));
		
		// infelem
		String infelemAttrName =
			DrlFactory.eINSTANCE.getDrlPackage().getInfElemRef_Infelem().getName();
		
		InfElement infelem = getInfelem();
		String infelemHref = infelem == null? "" : helper.getHREF(infelem);
		elem.setAttribute(infelemAttrName, infelemHref);
		
		DrlGraphPlugin.logInfo("infelem id = " + elem.getAttribute(infelemAttrName));
		
		// infelemRefGroup
		String infelemRefGroupAttrName =
			DrlFactory.eINSTANCE.getDrlPackage().getInfElemRef_Group().getName();
		
		InfElemRefGroup infelemRefGroup = getGroup();
		String groupHref = infelemRefGroup == null? "" : helper.getHREF(infelemRefGroup);
		elem.setAttribute(infelemRefGroupAttrName, groupHref);
	}

} //InfElemRefImpl