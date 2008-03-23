package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.DeferredLayoutCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalConnectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.FinalInfProduct;
import org.spbu.pldoctoolkit.graph.InfProduct;
import org.spbu.pldoctoolkit.graph.Product;
import org.spbu.pldoctoolkit.graph.ProductDocumentation;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductInfProductLinkEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLine2EditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelDiagramEditorPlugin;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelDiagramUpdater;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelLinkDescriptor;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelNodeDescriptor;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;
import org.spbu.pldoctoolkit.graph.diagram.productline.providers.DrlModelElementTypes;
import org.spbu.pldoctoolkit.registry.RegisteredLocation;

/**
 * @generated
 */
public class ProductLineCanonicalEditPolicy extends
		CanonicalConnectionEditPolicy {

	/**
	 * @generated
	 */
	protected List getSemanticChildrenList() {
		View viewObject = (View) getHost().getModel();
		List result = new LinkedList();
		for (Iterator it = DrlModelDiagramUpdater
				.getProductLine_79SemanticChildren(viewObject).iterator(); it
				.hasNext();) {
			result.add(((DrlModelNodeDescriptor) it.next()).getModelElement());
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected boolean shouldDeleteView(View view) {
		return true;
	}

	/**
	 * @generated
	 */
	protected boolean isOrphaned(Collection semanticChildren, final View view) {
		int visualID = DrlModelVisualIDRegistry.getVisualID(view);
		switch (visualID) {
		case ProductLine2EditPart.VISUAL_ID:
			return !semanticChildren.contains(view.getElement())
					|| visualID != DrlModelVisualIDRegistry.getNodeVisualID(
							(View) getHost().getModel(), view.getElement());
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected String getDefaultFactoryHint() {
		return null;
	}

	/**
	 * @generated
	 */
	protected List getSemanticConnectionsList() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	protected EObject getSourceElement(EObject relationship) {
		return null;
	}

	/**
	 * @generated
	 */
	protected EObject getTargetElement(EObject relationship) {
		return null;
	}

	/**
	 * @generated
	 */
	protected boolean shouldIncludeConnection(Edge connector,
			Collection children) {
		return false;
	}

	/**
	 * @generated
	 */
	protected void refreshSemantic() {
		List createdViews = new LinkedList();
		createdViews.addAll(refreshSemanticChildren());
		List createdConnectionViews = new LinkedList();
		createdConnectionViews.addAll(refreshSemanticConnections());
		createdConnectionViews.addAll(refreshConnections());

		if (createdViews.size() > 1) {
			// perform a layout of the container
			DeferredLayoutCommand layoutCmd = new DeferredLayoutCommand(host()
					.getEditingDomain(), createdViews, host());
			executeCommand(new ICommandProxy(layoutCmd));
		}

		createdViews.addAll(createdConnectionViews);
		makeViewsImmutable(createdViews);
	}

	/**
	 * @generated NOT
	 */
	private Collection refreshConnections() {
		Map domain2NotationMap = new HashMap();
		Collection linkDescriptors = collectAllLinks(getDiagram(),
				domain2NotationMap);
		// HAND
		linkDescriptors.addAll(collectProductInfProductLinks(domain2NotationMap));
		
		Collection existingLinks = new LinkedList(getDiagram().getEdges());
		for (Iterator linksIterator = existingLinks.iterator(); linksIterator
				.hasNext();) {
			Edge nextDiagramLink = (Edge) linksIterator.next();
			int diagramLinkVisualID = DrlModelVisualIDRegistry
					.getVisualID(nextDiagramLink);
			if (diagramLinkVisualID == -1) {
				if (nextDiagramLink.getSource() != null
						&& nextDiagramLink.getTarget() != null) {
					linksIterator.remove();
				}
				continue;
			}
			EObject diagramLinkObject = nextDiagramLink.getElement();
			EObject diagramLinkSrc = nextDiagramLink.getSource().getElement();
			EObject diagramLinkDst = nextDiagramLink.getTarget().getElement();
			for (Iterator linkDescriptorsIterator = linkDescriptors.iterator(); linkDescriptorsIterator
					.hasNext();) {
				DrlModelLinkDescriptor nextLinkDescriptor = (DrlModelLinkDescriptor) linkDescriptorsIterator
						.next();
				if (diagramLinkObject == nextLinkDescriptor.getModelElement()
						&& diagramLinkSrc == nextLinkDescriptor.getSource()
						&& diagramLinkDst == nextLinkDescriptor
								.getDestination()
						&& diagramLinkVisualID == nextLinkDescriptor
								.getVisualID()) {
					linksIterator.remove();
					linkDescriptorsIterator.remove();
				}
			}
		}
		deleteViews(existingLinks.iterator());
		return createConnections(linkDescriptors, domain2NotationMap);
	}

	/**
	 * HAND
	 * 
	 * First, loads all resources containing final inf products. Then for each
	 * InfProduct on the diagram finds and displays the corresponding
	 * FinalInfProduct.<p>
	 * 
	 * @param domain2NotationMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Collection collectProductInfProductLinks(Map domain2NotationMap) {
		final Map<EObject, View> d2n = domain2NotationMap;
		final List<DrlModelLinkDescriptor> result = new ArrayList<DrlModelLinkDescriptor>();
		
		TransactionalEditingDomain domain = ((GraphicalEditPart)getHost()).getEditingDomain();
		Set<Resource> resourceSet = loadResources(domain.getResourceSet());
		
//		EList<Resource> resourceList = domain.getResourceSet().getResources();

		for(Resource resource : resourceSet) {
			TreeIterator<EObject> nodeIter = resource.getAllContents();
			while(nodeIter.hasNext()) {
				EObject node = nodeIter.next();
				if(DrlPackage.PRODUCT_DOCUMENTATION == node.eClass().getClassifierID()) {
					ProductDocumentation doc = (ProductDocumentation) node;
					
					for(FinalInfProduct finInfProd : doc.getFinalInfProducts()) {
						InfProduct infProd = finInfProd.getProduct();
						View infProductView = d2n.get(infProd);
						if(null == infProductView) {
							warn("this InfProduct doesn't has view on the diagram, skipping");
							continue;
						}
						
						Product product = doc.getProduct();
						View productView = d2n.get(product);
						if(null == productView) {
							warn("this product doesn't has view on the diagram, skipping");
							continue;
						}
						
						result.add(new DrlModelLinkDescriptor(
								product,
								infProd,
								DrlModelElementTypes.ProductDocumentationFinalInfProducts_3001,
								ProductInfProductLinkEditPart.VISUAL_ID
								)); 
					}
				}
			}
		}
		
		return result;
	}

	/**
	 * Checks Registry and loads into resource set all resources which contain
	 * FinalInfProducts.
	 * 
	 * @param resourceSet
	 * @return list of resources containing FinalInfProduct
	 */
	private Set<Resource> loadResources(ResourceSet resourceSet) {
		//TODO how to determine project name from URI?
		URI diagramURI = ((View) getHost().getModel()).eResource().getURI();
		String projectName = diagramURI.segment(1);

		List<RegisteredLocation> finalInfProducts = 
				PLDocToolkitPlugin.getRegistry(projectName)
				.findForType(RegisteredLocation.FINAL_INF_PRODUCT);

		final Set<Resource> result = new HashSet<Resource>();
		
		for(RegisteredLocation finInfProd : finalInfProducts) {
			String pathname = finInfProd.getFile().getFullPath().toFile().toString();
			Resource r = resourceSet.getResource(URI.createPlatformResourceURI(pathname, true), true);
			result.add(r);
		}
		
		return result;
	}
	
	private void log(String msg) {
		DrlModelDiagramEditorPlugin.getInstance().logInfo(msg);
	}
	
	private void warn(String msg) {
		DrlModelDiagramEditorPlugin.getInstance().logError(msg);
	}
	
	/**
	 * @generated
	 */
	private Collection collectAllLinks(View view, Map domain2NotationMap) {
		if (!ProductLineEditPart.MODEL_ID.equals(DrlModelVisualIDRegistry
				.getModelID(view))) {
			return Collections.EMPTY_LIST;
		}
		Collection result = new LinkedList();
		switch (DrlModelVisualIDRegistry.getVisualID(view)) {
		case ProductLineEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(DrlModelDiagramUpdater
						.getProductLine_79ContainedLinks(view));
			}
			if (!domain2NotationMap.containsKey(view.getElement())
					|| view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		case ProductLine2EditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(DrlModelDiagramUpdater
						.getProductLine_1001ContainedLinks(view));
			}
			if (!domain2NotationMap.containsKey(view.getElement())
					|| view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		case ProductEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(DrlModelDiagramUpdater
						.getProduct_2003ContainedLinks(view));
			}
			if (!domain2NotationMap.containsKey(view.getElement())
					|| view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		case InfProductEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(DrlModelDiagramUpdater
						.getInfProduct_2005ContainedLinks(view));
			}
			if (!domain2NotationMap.containsKey(view.getElement())
					|| view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		}
		for (Iterator children = view.getChildren().iterator(); children
				.hasNext();) {
			result.addAll(collectAllLinks((View) children.next(),
					domain2NotationMap));
		}
		for (Iterator edges = view.getSourceEdges().iterator(); edges.hasNext();) {
			result.addAll(collectAllLinks((View) edges.next(),
					domain2NotationMap));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection createConnections(Collection linkDescriptors,
			Map domain2NotationMap) {
		List adapters = new LinkedList();
		for (Iterator linkDescriptorsIterator = linkDescriptors.iterator(); linkDescriptorsIterator
				.hasNext();) {
			final DrlModelLinkDescriptor nextLinkDescriptor = (DrlModelLinkDescriptor) linkDescriptorsIterator
					.next();
			EditPart sourceEditPart = getEditPart(nextLinkDescriptor
					.getSource(), domain2NotationMap);
			EditPart targetEditPart = getEditPart(nextLinkDescriptor
					.getDestination(), domain2NotationMap);
			if (sourceEditPart == null || targetEditPart == null) {
				continue;
			}
			CreateConnectionViewRequest.ConnectionViewDescriptor descriptor = new CreateConnectionViewRequest.ConnectionViewDescriptor(
					nextLinkDescriptor.getSemanticAdapter(), null,
					ViewUtil.APPEND, false, ((IGraphicalEditPart) getHost())
							.getDiagramPreferencesHint());
			CreateConnectionViewRequest ccr = new CreateConnectionViewRequest(
					descriptor);
			ccr.setType(RequestConstants.REQ_CONNECTION_START);
			ccr.setSourceEditPart(sourceEditPart);
			sourceEditPart.getCommand(ccr);
			ccr.setTargetEditPart(targetEditPart);
			ccr.setType(RequestConstants.REQ_CONNECTION_END);
			Command cmd = targetEditPart.getCommand(ccr);
			if (cmd != null && cmd.canExecute()) {
				executeCommand(cmd);
				IAdaptable viewAdapter = (IAdaptable) ccr.getNewObject();
				if (viewAdapter != null) {
					adapters.add(viewAdapter);
				}
			}
		}
		return adapters;
	}

	/**
	 * @generated
	 */
	private EditPart getEditPart(EObject domainModelElement,
			Map domain2NotationMap) {
		View view = (View) domain2NotationMap.get(domainModelElement);
		if (view != null) {
			return (EditPart) getHost().getViewer().getEditPartRegistry().get(
					view);
		}
		return null;
	}

	/**
	 * @generated
	 */
	private Diagram getDiagram() {
		return ((View) getHost().getModel()).getDiagram();
	}

}
