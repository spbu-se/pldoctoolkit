package org.spbu.pldoctoolkit.graph.diagram.fproduct.part;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.Adapter;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.FinalInfProduct;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElement;
import org.spbu.pldoctoolkit.graph.InfProduct;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.FinalInfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.InfElemRefEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.InfElementEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class DrlModelDiagramUpdater {

	/**
	 * @generated
	 */
	public static List getSemanticChildren(View view) {
		switch (DrlModelVisualIDRegistry.getVisualID(view)) {
		case FinalInfProductEditPart.VISUAL_ID:
			return getFinalInfProduct_79SemanticChildren(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated NOT
	 */
	public static List<DrlModelNodeDescriptor> getFinalInfProduct_79SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		FinalInfProduct modelElement = (FinalInfProduct) view.getElement();
		List<DrlModelNodeDescriptor> result = new LinkedList<DrlModelNodeDescriptor>();
		//		Resource resource = modelElement.eResource();
		//		for (Iterator semanticIterator = getPhantomNodesIterator(resource); semanticIterator
		//				.hasNext();) {
		//			EObject childElement = (EObject) semanticIterator.next();
		//			if (childElement == modelElement) {
		//				continue;
		//			}
		//			if (DrlModelVisualIDRegistry.getNodeVisualID(view, childElement) == InfProductEditPart.VISUAL_ID) {
		//				result.add(new DrlModelNodeDescriptor(childElement,
		//						InfProductEditPart.VISUAL_ID));
		//				continue;
		//			}
		//			if (DrlModelVisualIDRegistry.getNodeVisualID(view, childElement) == InfElementEditPart.VISUAL_ID) {
		//				result.add(new DrlModelNodeDescriptor(childElement,
		//						InfElementEditPart.VISUAL_ID));
		//				continue;
		//			}
		//		}

		// HAND 
		List<Adapter> adapters = modelElement.getAdapters();
		Set<String> infelemrefids = new HashSet<String>(adapters.size());
		for (Adapter adapter : adapters) {
			infelemrefids.add(adapter.getInfelemrefid());
		}
		
		List<GenericDocumentPart> partsList = new LinkedList<GenericDocumentPart>();
		InfProduct rootElement = modelElement.getProduct();
		
		findAccessibleElements(infelemrefids, rootElement, partsList);

		for (GenericDocumentPart genericDocumentPart : partsList) {
			result.add(new DrlModelNodeDescriptor(genericDocumentPart,
					DrlModelVisualIDRegistry.getNodeVisualID(view,
							genericDocumentPart)));
		}

		return result;
	}

	/**
	 * @param idToInfElemRefMap 
	 * 
	 * @param curElement
	 * @param currentResult
	 */
	@SuppressWarnings("unchecked")
	private static void findAccessibleElements(final Set<String> infelemrefids, GenericDocumentPart curElement,
			List<GenericDocumentPart> currentResult) {
		if (curElement == null) {
			return;
		}

		currentResult.add(curElement);

		EList<InfElemRef> infElemRefs = curElement.getInfElemRefs();
		for (InfElemRef ref : infElemRefs) {
			String refId = ref.getId();
			if(!infelemrefids.contains(refId)) {
				if(null != ref.getGroup() || ref.isOptional()) {
					System.out.println("ref id not included, skipping: " + refId);
					continue;
				}
			}
			
			InfElement element = ref.getInfelem();
			findAccessibleElements(infelemrefids, element, currentResult);
		}

		return;
	}

	/**
	 * @generated
	 */
	private static Iterator getPhantomNodesIterator(Resource resource) {
		return resource.getAllContents();
	}

	/**
	 * @generated
	 */
	public static List getContainedLinks(View view) {
		switch (DrlModelVisualIDRegistry.getVisualID(view)) {
		case FinalInfProductEditPart.VISUAL_ID:
			return getFinalInfProduct_79ContainedLinks(view);
		case InfProductEditPart.VISUAL_ID:
			return getInfProduct_1001ContainedLinks(view);
		case InfElementEditPart.VISUAL_ID:
			return getInfElement_1002ContainedLinks(view);
		case InfElemRefEditPart.VISUAL_ID:
			return getInfElemRef_3001ContainedLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getIncomingLinks(View view) {
		switch (DrlModelVisualIDRegistry.getVisualID(view)) {
		case InfProductEditPart.VISUAL_ID:
			return getInfProduct_1001IncomingLinks(view);
		case InfElementEditPart.VISUAL_ID:
			return getInfElement_1002IncomingLinks(view);
		case InfElemRefEditPart.VISUAL_ID:
			return getInfElemRef_3001IncomingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOutgoingLinks(View view) {
		switch (DrlModelVisualIDRegistry.getVisualID(view)) {
		case InfProductEditPart.VISUAL_ID:
			return getInfProduct_1001OutgoingLinks(view);
		case InfElementEditPart.VISUAL_ID:
			return getInfElement_1002OutgoingLinks(view);
		case InfElemRefEditPart.VISUAL_ID:
			return getInfElemRef_3001OutgoingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getFinalInfProduct_79ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getInfProduct_1001ContainedLinks(View view) {
		InfProduct modelElement = (InfProduct) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getContainedTypeModelFacetLinks_InfElemRef_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getInfElement_1002ContainedLinks(View view) {
		InfElement modelElement = (InfElement) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getContainedTypeModelFacetLinks_InfElemRef_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getInfElemRef_3001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getInfProduct_1001IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getInfElement_1002IncomingLinks(View view) {
		InfElement modelElement = (InfElement) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_InfElemRef_3001(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getInfElemRef_3001IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getInfProduct_1001OutgoingLinks(View view) {
		InfProduct modelElement = (InfProduct) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getContainedTypeModelFacetLinks_InfElemRef_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getInfElement_1002OutgoingLinks(View view) {
		InfElement modelElement = (InfElement) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getContainedTypeModelFacetLinks_InfElemRef_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getInfElemRef_3001OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	private static Collection getContainedTypeModelFacetLinks_InfElemRef_3001(
			GenericDocumentPart container) {
		Collection result = new LinkedList();
		for (Iterator links = container.getInfElemRefs().iterator(); links
				.hasNext();) {
			Object linkObject = links.next();
			if (false == linkObject instanceof InfElemRef) {
				continue;
			}
			InfElemRef link = (InfElemRef) linkObject;
			if (InfElemRefEditPart.VISUAL_ID != DrlModelVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			InfElement dst = link.getInfelem();
			result.add(new DrlModelLinkDescriptor(container, dst, link,
					DrlModelElementTypes.InfElemRef_3001,
					InfElemRefEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingTypeModelFacetLinks_InfElemRef_3001(
			InfElement target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it
					.next();
			if (setting.getEStructuralFeature() != DrlPackage.eINSTANCE
					.getInfElemRef_Infelem()
					|| false == setting.getEObject() instanceof InfElemRef) {
				continue;
			}
			InfElemRef link = (InfElemRef) setting.getEObject();
			if (InfElemRefEditPart.VISUAL_ID != DrlModelVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			if (false == link.eContainer() instanceof GenericDocumentPart) {
				continue;
			}
			GenericDocumentPart container = (GenericDocumentPart) link
					.eContainer();
			result.add(new DrlModelLinkDescriptor(container, target, link,
					DrlModelElementTypes.InfElemRef_3001,
					InfElemRefEditPart.VISUAL_ID));

		}
		return result;
	}

}
