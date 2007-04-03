package org.spbu.pldoctoolkit.graph.diagram.productline.navigator;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class DrlModelNavigatorItem extends DrlModelAbstractNavigatorItem {

	/**
	 * @generated
	 */
	static {
		final Class[] supportedTypes = new Class[] { View.class, EObject.class };
		Platform.getAdapterManager().registerAdapters(new IAdapterFactory() {

			public Object getAdapter(Object adaptableObject, Class adapterType) {
				if (adaptableObject instanceof DrlModelNavigatorItem
						&& (adapterType == View.class || adapterType == EObject.class)) {
					return ((DrlModelNavigatorItem) adaptableObject).getView();
				}
				return null;
			}

			public Class[] getAdapterList() {
				return supportedTypes;
			}
		}, DrlModelNavigatorItem.class);
	}

	/**
	 * @generated
	 */
	private View myView;

	/**
	 * @generated
	 */
	private boolean myLeaf = false;

	/**
	 * @generated
	 */
	public DrlModelNavigatorItem(View view, Object parent, boolean isLeaf) {
		super(parent);
		myView = view;
		myLeaf = isLeaf;
	}

	/**
	 * @generated
	 */
	public View getView() {
		return myView;
	}

	/**
	 * @generated
	 */
	public boolean isLeaf() {
		return myLeaf;
	}

	/**
	 * @generated
	 */
	public boolean equals(Object obj) {
		if (obj instanceof DrlModelNavigatorItem) {
			EObject eObject = getView().getElement();
			EObject anotherEObject = ((DrlModelNavigatorItem) obj).getView()
					.getElement();
			if (eObject == null) {
				return anotherEObject == null;
			} else if (anotherEObject == null) {
				return false;
			}
			if (eObject.eResource() != null) {
				return eObject.eResource().getURIFragment(eObject).equals(
						anotherEObject.eResource().getURIFragment(
								anotherEObject));
			}
		}
		return super.equals(obj);
	}

}
