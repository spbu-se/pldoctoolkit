package org.spbu.pldoctoolkit.graph.diagram.productline.navigator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonContentProvider;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.DocumentationCoreEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.DocumentationCoreInfProductsCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.PLSchemeEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.PLSchemeProductsCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLine2EditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineDataDocumentationCoreCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineDataEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineDataPLSchemeCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineProductLineDataCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;

/**
 * @generated
 */
public class DrlModelNavigatorContentProvider implements ICommonContentProvider {

	/**
	 * @generated
	 */
	private static final Object[] EMPTY_ARRAY = new Object[0];

	/**
	 * @generated
	 */
	private Viewer myViewer;
	/**
	 * @generated
	 */
	private AdapterFactoryEditingDomain myEditingDomain;
	/**
	 * @generated
	 */
	private WorkspaceSynchronizer myWorkspaceSynchronizer;
	/**
	 * @generated
	 */
	private Runnable myViewerRefreshRunnable;

	/**
	 * @generated
	 */
	public DrlModelNavigatorContentProvider() {
		TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE
				.createEditingDomain();
		myEditingDomain = (AdapterFactoryEditingDomain) editingDomain;
		myEditingDomain.setResourceToReadOnlyMap(new HashMap() {
			public Object get(Object key) {
				if (!containsKey(key)) {
					put(key, Boolean.TRUE);
				}
				return super.get(key);
			}
		});
		myViewerRefreshRunnable = new Runnable() {
			public void run() {
				if (myViewer != null) {
					myViewer.refresh();
				}
			}
		};
		myWorkspaceSynchronizer = new WorkspaceSynchronizer(editingDomain,
				new WorkspaceSynchronizer.Delegate() {
					public void dispose() {
					}

					public boolean handleResourceChanged(final Resource resource) {
						for (Iterator it = myEditingDomain.getResourceSet()
								.getResources().iterator(); it.hasNext();) {
							Resource nextResource = (Resource) it.next();
							nextResource.unload();
						}
						if (myViewer != null) {
							myViewer.getControl().getDisplay().asyncExec(
									myViewerRefreshRunnable);
						}
						return true;
					}

					public boolean handleResourceDeleted(Resource resource) {
						for (Iterator it = myEditingDomain.getResourceSet()
								.getResources().iterator(); it.hasNext();) {
							Resource nextResource = (Resource) it.next();
							nextResource.unload();
						}
						if (myViewer != null) {
							myViewer.getControl().getDisplay().asyncExec(
									myViewerRefreshRunnable);
						}
						return true;
					}

					public boolean handleResourceMoved(Resource resource,
							final URI newURI) {
						for (Iterator it = myEditingDomain.getResourceSet()
								.getResources().iterator(); it.hasNext();) {
							Resource nextResource = (Resource) it.next();
							nextResource.unload();
						}
						if (myViewer != null) {
							myViewer.getControl().getDisplay().asyncExec(
									myViewerRefreshRunnable);
						}
						return true;
					}
				});
	}

	/**
	 * @generated
	 */
	public void dispose() {
		myWorkspaceSynchronizer.dispose();
		myWorkspaceSynchronizer = null;
		myViewerRefreshRunnable = null;
		for (Iterator it = myEditingDomain.getResourceSet().getResources()
				.iterator(); it.hasNext();) {
			Resource resource = (Resource) it.next();
			resource.unload();
		}
		((TransactionalEditingDomain) myEditingDomain).dispose();
		myEditingDomain = null;
	}

	/**
	 * @generated
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		myViewer = viewer;
	}

	/**
	 * @generated
	 */
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	/**
	 * @generated
	 */
	public void restoreState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	public void saveState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	public void init(ICommonContentExtensionSite aConfig) {
	}

	/**
	 * @generated
	 */
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IFile) {
			IFile file = (IFile) parentElement;
			URI fileURI = URI.createPlatformResourceURI(file.getFullPath()
					.toString(), true);
			Resource resource = myEditingDomain.getResourceSet().getResource(
					fileURI, true);
			Collection result = new ArrayList();
			result
					.addAll(createNavigatorItems(selectViewsByType(resource
							.getContents(), ProductLineEditPart.MODEL_ID),
							file, false));
			return result.toArray();
		}

		if (parentElement instanceof DrlModelNavigatorGroup) {
			DrlModelNavigatorGroup group = (DrlModelNavigatorGroup) parentElement;
			return group.getChildren();
		}

		if (parentElement instanceof DrlModelNavigatorItem) {
			DrlModelNavigatorItem navigatorItem = (DrlModelNavigatorItem) parentElement;
			if (navigatorItem.isLeaf() || !isOwnView(navigatorItem.getView())) {
				return EMPTY_ARRAY;
			}
			return getViewChildren(navigatorItem.getView(), parentElement);
		}

		return EMPTY_ARRAY;
	}

	/**
	 * @generated
	 */
	private Object[] getViewChildren(View view, Object parentElement) {
		switch (DrlModelVisualIDRegistry.getVisualID(view)) {

		case ProductLineEditPart.VISUAL_ID: {
			Collection result = new ArrayList();
			Collection connectedViews = getChildrenByType(Collections
					.singleton(view), ProductLine2EditPart.VISUAL_ID);
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			return result.toArray();
		}

		case ProductLine2EditPart.VISUAL_ID: {
			Collection result = new ArrayList();
			Collection connectedViews = getChildrenByType(Collections
					.singleton(view),
					ProductLineProductLineDataCompartmentEditPart.VISUAL_ID);
			connectedViews = getChildrenByType(connectedViews,
					ProductLineDataEditPart.VISUAL_ID);
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			return result.toArray();
		}

		case ProductLineDataEditPart.VISUAL_ID: {
			Collection result = new ArrayList();
			Collection connectedViews = getChildrenByType(Collections
					.singleton(view),
					ProductLineDataPLSchemeCompartmentEditPart.VISUAL_ID);
			connectedViews = getChildrenByType(connectedViews,
					PLSchemeEditPart.VISUAL_ID);
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			connectedViews = getChildrenByType(
					Collections.singleton(view),
					ProductLineDataDocumentationCoreCompartmentEditPart.VISUAL_ID);
			connectedViews = getChildrenByType(connectedViews,
					DocumentationCoreEditPart.VISUAL_ID);
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			return result.toArray();
		}

		case PLSchemeEditPart.VISUAL_ID: {
			Collection result = new ArrayList();
			Collection connectedViews = getChildrenByType(Collections
					.singleton(view),
					PLSchemeProductsCompartmentEditPart.VISUAL_ID);
			connectedViews = getChildrenByType(connectedViews,
					ProductEditPart.VISUAL_ID);
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			return result.toArray();
		}

		case DocumentationCoreEditPart.VISUAL_ID: {
			Collection result = new ArrayList();
			Collection connectedViews = getChildrenByType(Collections
					.singleton(view),
					DocumentationCoreInfProductsCompartmentEditPart.VISUAL_ID);
			connectedViews = getChildrenByType(connectedViews,
					InfProductEditPart.VISUAL_ID);
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			return result.toArray();
		}
		}
		return EMPTY_ARRAY;
	}

	/**
	 * @generated
	 */
	private Collection getLinksSourceByType(Collection edges, int visualID) {
		Collection result = new ArrayList();
		String type = DrlModelVisualIDRegistry.getType(visualID);
		for (Iterator it = edges.iterator(); it.hasNext();) {
			Edge nextEdge = (Edge) it.next();
			View nextEdgeSource = nextEdge.getSource();
			if (type.equals(nextEdgeSource.getType())
					&& isOwnView(nextEdgeSource)) {
				result.add(nextEdgeSource);
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection getLinksTargetByType(Collection edges, int visualID) {
		Collection result = new ArrayList();
		String type = DrlModelVisualIDRegistry.getType(visualID);
		for (Iterator it = edges.iterator(); it.hasNext();) {
			Edge nextEdge = (Edge) it.next();
			View nextEdgeTarget = nextEdge.getTarget();
			if (type.equals(nextEdgeTarget.getType())
					&& isOwnView(nextEdgeTarget)) {
				result.add(nextEdgeTarget);
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection getOutgoingLinksByType(Collection nodes, int visualID) {
		Collection result = new ArrayList();
		String type = DrlModelVisualIDRegistry.getType(visualID);
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			View nextNode = (View) it.next();
			result.addAll(selectViewsByType(nextNode.getSourceEdges(), type));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection getIncomingLinksByType(Collection nodes, int visualID) {
		Collection result = new ArrayList();
		String type = DrlModelVisualIDRegistry.getType(visualID);
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			View nextNode = (View) it.next();
			result.addAll(selectViewsByType(nextNode.getTargetEdges(), type));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection getChildrenByType(Collection nodes, int visualID) {
		Collection result = new ArrayList();
		String type = DrlModelVisualIDRegistry.getType(visualID);
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			View nextNode = (View) it.next();
			result.addAll(selectViewsByType(nextNode.getChildren(), type));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection getDiagramLinksByType(Collection diagrams, int visualID) {
		Collection result = new ArrayList();
		String type = DrlModelVisualIDRegistry.getType(visualID);
		for (Iterator it = diagrams.iterator(); it.hasNext();) {
			Diagram nextDiagram = (Diagram) it.next();
			result.addAll(selectViewsByType(nextDiagram.getEdges(), type));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection selectViewsByType(Collection views, String type) {
		Collection result = new ArrayList();
		for (Iterator it = views.iterator(); it.hasNext();) {
			View nextView = (View) it.next();
			if (type.equals(nextView.getType()) && isOwnView(nextView)) {
				result.add(nextView);
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private boolean isOwnView(View view) {
		return ProductLineEditPart.MODEL_ID.equals(DrlModelVisualIDRegistry
				.getModelID(view));
	}

	/**
	 * @generated
	 */
	private Collection createNavigatorItems(Collection views, Object parent,
			boolean isLeafs) {
		Collection result = new ArrayList();
		for (Iterator it = views.iterator(); it.hasNext();) {
			result.add(new DrlModelNavigatorItem((View) it.next(), parent,
					isLeafs));
		}
		return result;
	}

	/**
	 * @generated
	 */
	public Object getParent(Object element) {
		if (element instanceof DrlModelAbstractNavigatorItem) {
			DrlModelAbstractNavigatorItem abstractNavigatorItem = (DrlModelAbstractNavigatorItem) element;
			return abstractNavigatorItem.getParent();
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean hasChildren(Object element) {
		return element instanceof IFile || getChildren(element).length > 0;
	}

}
