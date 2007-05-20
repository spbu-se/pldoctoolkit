/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.NameInfo;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.NameInfoImpl;
import org.spbu.pldoctoolkit.graph.DrlElement;
import org.spbu.pldoctoolkit.graph.DrlGraphPlugin;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.util.DrlResourceImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.spbu.pldoctoolkit.graph.impl.DrlElementImpl#getNode <em>Node</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DrlElementImpl extends EObjectImpl implements DrlElement {
	public static final String XSI_TYPE_NS = XMLResource.XSI_NS+":"+XMLResource.TYPE;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "copyleft 2007";

	/**
	 * The default value of the '{@link #getNode() <em>Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNode()
	 * @generated
	 * @ordered
	 */
	protected static final Element NODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNode() <em>Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNode()
	 * @generated
	 * @ordered
	 */
	protected Element node = NODE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DrlElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DrlPackage.Literals.DRL_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element getNode() {
		return node;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNode(Element newNode) {
		Element oldNode = node;
		node = newNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DrlPackage.DRL_ELEMENT__NODE, oldNode, node));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DrlPackage.DRL_ELEMENT__NODE:
				return getNode();
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
			case DrlPackage.DRL_ELEMENT__NODE:
				setNode((Element)newValue);
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
			case DrlPackage.DRL_ELEMENT__NODE:
				setNode(NODE_EDEFAULT);
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
			case DrlPackage.DRL_ELEMENT__NODE:
				return NODE_EDEFAULT == null ? node != null : !NODE_EDEFAULT.equals(node);
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
		result.append(" (node: ");
		result.append(node);
		result.append(')');
		return result.toString();
	}

	protected final Document getDocument() {
		DrlResourceImpl resource = (DrlResourceImpl) this.eResource();
		if(resource != null) {
			return resource.getDrlDocument();
		}
		return null;
	}
	
	/**
	 * Initialize the current node respecting the feature it is being assigned with.
	 * 
	 * @param feature
	 */
	public void initializeNode(EStructuralFeature feature) {
		Resource resource = this.eResource();
		if(resource instanceof DrlResourceImpl) {
			DrlResourceImpl drlResource = (DrlResourceImpl) resource;
			Document drlDocument = drlResource.getDrlDocument();
			XMLHelper helper = drlResource.getHelper();
			
			if(drlDocument != null) {
				NameInfo nameInfo = new NameInfoImpl();
		        helper.populateNameInfo(nameInfo, feature);
		        Element elem = drlDocument.createElementNS(nameInfo.getNamespaceURI(), nameInfo.getQualifiedName()); 
		        
				setNode(elem);
			} else {
				DrlGraphPlugin.logInfo("drldoc empty");
			}
		}
	}
	
	/**
	 * This method is needed to update the attributes values or create ones if necessary. 
	 * It is not called on node creation, so the user should call it manually when the node
	 * initialization is complete - i.e. on resource save reuest.
	 * 
	 * This method adds "xsi:type" attribute if needed.
	 * 
	 * Derived classes should override this method to update their specific attributes. 
	 */
	public void updateAttributeNodes() {
		// default implementation is empty
		if (needTypeInfo()) {
			DrlResourceImpl resource = (DrlResourceImpl) this.eResource();
			XMLHelper helper = resource.getHelper();
			NameInfo nameInfo = new NameInfoImpl();
			Element elem = getNode();
			
			helper.populateNameInfo(nameInfo, this.eClass());
			elem.setAttributeNS(ExtendedMetaData.XSI_URI,
					XSI_TYPE_NS, nameInfo.getQualifiedName());
		}
		
	}
	
	protected boolean needTypeInfo() {
		return false;
	}

} //DrlElementImpl
