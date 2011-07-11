package org.spbu.plweb.diagram.part;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.plweb.Area;
import org.spbu.plweb.DiagramRoot;
import org.spbu.plweb.DocTopic;
import org.spbu.plweb.Group;
import org.spbu.plweb.Node;
import org.spbu.plweb.Page;
import org.spbu.plweb.PlwebPackage;
import org.spbu.plweb.Root;
import org.spbu.plweb.SiteView;
import org.spbu.plweb.SourceRefElement;
import org.spbu.plweb.TargetRefElement;
import org.spbu.plweb.diagram.edit.parts.AreaEditPart;
import org.spbu.plweb.diagram.edit.parts.AreaTopicAreaCompartmentEditPart;
import org.spbu.plweb.diagram.edit.parts.DiagramRootEditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic2EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic3EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic4EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic5EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicEditPart;
import org.spbu.plweb.diagram.edit.parts.GroupEditPart;
import org.spbu.plweb.diagram.edit.parts.NodeEditPart;
import org.spbu.plweb.diagram.edit.parts.NodeTopicNodeCompartmentEditPart;
import org.spbu.plweb.diagram.edit.parts.PageEditPart;
import org.spbu.plweb.diagram.edit.parts.PageTopicPageCompartmentEditPart;
import org.spbu.plweb.diagram.edit.parts.RootEditPart;
import org.spbu.plweb.diagram.edit.parts.RootTopicRootCompartmentEditPart;
import org.spbu.plweb.diagram.edit.parts.SiteViewEditPart;
import org.spbu.plweb.diagram.edit.parts.SiteViewTopicSiteViewCompartmentEditPart;
import org.spbu.plweb.diagram.edit.parts.SourceRefElementClassEditPart;
import org.spbu.plweb.diagram.providers.PlwebElementTypes;

/**
 * @generated
 */
public class PlwebDiagramUpdater {

	/**
	 * @generated
	 */
	public static List<PlwebNodeDescriptor> getSemanticChildren(View view) {
		switch (PlwebVisualIDRegistry.getVisualID(view)) {
		case DiagramRootEditPart.VISUAL_ID:
			return getDiagramRoot_1000SemanticChildren(view);
		case RootEditPart.VISUAL_ID:
			return getRoot_2001SemanticChildren(view);
		case SiteViewEditPart.VISUAL_ID:
			return getSiteView_2002SemanticChildren(view);
		case AreaEditPart.VISUAL_ID:
			return getArea_2003SemanticChildren(view);
		case PageEditPart.VISUAL_ID:
			return getPage_2004SemanticChildren(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2006SemanticChildren(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebNodeDescriptor> getDiagramRoot_1000SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		DiagramRoot modelElement = (DiagramRoot) view.getElement();
		LinkedList<PlwebNodeDescriptor> result = new LinkedList<PlwebNodeDescriptor>();
		{
			Root childElement = modelElement.getRoot();
			int visualID = PlwebVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == RootEditPart.VISUAL_ID) {
				result.add(new PlwebNodeDescriptor(childElement, visualID));
			}
		}
		Resource resource = modelElement.eResource();
		for (Iterator<EObject> it = getPhantomNodesIterator(resource); it
				.hasNext();) {
			EObject childElement = it.next();
			if (childElement == modelElement) {
				continue;
			}
			if (PlwebVisualIDRegistry.getNodeVisualID(view, childElement) == SiteViewEditPart.VISUAL_ID) {
				result.add(new PlwebNodeDescriptor(childElement,
						SiteViewEditPart.VISUAL_ID));
				continue;
			}
			if (PlwebVisualIDRegistry.getNodeVisualID(view, childElement) == AreaEditPart.VISUAL_ID) {
				result.add(new PlwebNodeDescriptor(childElement,
						AreaEditPart.VISUAL_ID));
				continue;
			}
			if (PlwebVisualIDRegistry.getNodeVisualID(view, childElement) == PageEditPart.VISUAL_ID) {
				result.add(new PlwebNodeDescriptor(childElement,
						PageEditPart.VISUAL_ID));
				continue;
			}
			if (PlwebVisualIDRegistry.getNodeVisualID(view, childElement) == GroupEditPart.VISUAL_ID) {
				result.add(new PlwebNodeDescriptor(childElement,
						GroupEditPart.VISUAL_ID));
				continue;
			}
			if (PlwebVisualIDRegistry.getNodeVisualID(view, childElement) == NodeEditPart.VISUAL_ID) {
				result.add(new PlwebNodeDescriptor(childElement,
						NodeEditPart.VISUAL_ID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebNodeDescriptor> getRoot_2001SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Root modelElement = (Root) view.getElement();
		LinkedList<PlwebNodeDescriptor> result = new LinkedList<PlwebNodeDescriptor>();
		for (Iterator<?> it = modelElement.getDocTopic().iterator(); it
				.hasNext();) {
			DocTopic childElement = (DocTopic) it.next();
			int visualID = PlwebVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == DocTopicEditPart.VISUAL_ID) {
				result.add(new PlwebNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebNodeDescriptor> getSiteView_2002SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		SiteView modelElement = (SiteView) view.getElement();
		LinkedList<PlwebNodeDescriptor> result = new LinkedList<PlwebNodeDescriptor>();
		for (Iterator<?> it = modelElement.getDocTopic().iterator(); it
				.hasNext();) {
			DocTopic childElement = (DocTopic) it.next();
			int visualID = PlwebVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == DocTopic2EditPart.VISUAL_ID) {
				result.add(new PlwebNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebNodeDescriptor> getArea_2003SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Area modelElement = (Area) view.getElement();
		LinkedList<PlwebNodeDescriptor> result = new LinkedList<PlwebNodeDescriptor>();
		for (Iterator<?> it = modelElement.getDocTopic().iterator(); it
				.hasNext();) {
			DocTopic childElement = (DocTopic) it.next();
			int visualID = PlwebVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == DocTopic3EditPart.VISUAL_ID) {
				result.add(new PlwebNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebNodeDescriptor> getPage_2004SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Page modelElement = (Page) view.getElement();
		LinkedList<PlwebNodeDescriptor> result = new LinkedList<PlwebNodeDescriptor>();
		for (Iterator<?> it = modelElement.getDocTopic().iterator(); it
				.hasNext();) {
			DocTopic childElement = (DocTopic) it.next();
			int visualID = PlwebVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == DocTopic4EditPart.VISUAL_ID) {
				result.add(new PlwebNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebNodeDescriptor> getNode_2006SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Node modelElement = (Node) view.getElement();
		LinkedList<PlwebNodeDescriptor> result = new LinkedList<PlwebNodeDescriptor>();
		for (Iterator<?> it = modelElement.getDocTopic().iterator(); it
				.hasNext();) {
			DocTopic childElement = (DocTopic) it.next();
			int visualID = PlwebVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == DocTopic5EditPart.VISUAL_ID) {
				result.add(new PlwebNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Iterator<EObject> getPhantomNodesIterator(Resource resource) {
		return resource.getAllContents();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getContainedLinks(View view) {
		switch (PlwebVisualIDRegistry.getVisualID(view)) {
		case DiagramRootEditPart.VISUAL_ID:
			return getDiagramRoot_1000ContainedLinks(view);
		case RootEditPart.VISUAL_ID:
			return getRoot_2001ContainedLinks(view);
		case SiteViewEditPart.VISUAL_ID:
			return getSiteView_2002ContainedLinks(view);
		case AreaEditPart.VISUAL_ID:
			return getArea_2003ContainedLinks(view);
		case PageEditPart.VISUAL_ID:
			return getPage_2004ContainedLinks(view);
		case GroupEditPart.VISUAL_ID:
			return getGroup_2005ContainedLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2006ContainedLinks(view);
		case DocTopicEditPart.VISUAL_ID:
			return getDocTopic_3005ContainedLinks(view);
		case DocTopic2EditPart.VISUAL_ID:
			return getDocTopic_3006ContainedLinks(view);
		case DocTopic3EditPart.VISUAL_ID:
			return getDocTopic_3007ContainedLinks(view);
		case DocTopic4EditPart.VISUAL_ID:
			return getDocTopic_3008ContainedLinks(view);
		case DocTopic5EditPart.VISUAL_ID:
			return getDocTopic_3009ContainedLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getIncomingLinks(View view) {
		switch (PlwebVisualIDRegistry.getVisualID(view)) {
		case RootEditPart.VISUAL_ID:
			return getRoot_2001IncomingLinks(view);
		case SiteViewEditPart.VISUAL_ID:
			return getSiteView_2002IncomingLinks(view);
		case AreaEditPart.VISUAL_ID:
			return getArea_2003IncomingLinks(view);
		case PageEditPart.VISUAL_ID:
			return getPage_2004IncomingLinks(view);
		case GroupEditPart.VISUAL_ID:
			return getGroup_2005IncomingLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2006IncomingLinks(view);
		case DocTopicEditPart.VISUAL_ID:
			return getDocTopic_3005IncomingLinks(view);
		case DocTopic2EditPart.VISUAL_ID:
			return getDocTopic_3006IncomingLinks(view);
		case DocTopic3EditPart.VISUAL_ID:
			return getDocTopic_3007IncomingLinks(view);
		case DocTopic4EditPart.VISUAL_ID:
			return getDocTopic_3008IncomingLinks(view);
		case DocTopic5EditPart.VISUAL_ID:
			return getDocTopic_3009IncomingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getOutgoingLinks(View view) {
		switch (PlwebVisualIDRegistry.getVisualID(view)) {
		case RootEditPart.VISUAL_ID:
			return getRoot_2001OutgoingLinks(view);
		case SiteViewEditPart.VISUAL_ID:
			return getSiteView_2002OutgoingLinks(view);
		case AreaEditPart.VISUAL_ID:
			return getArea_2003OutgoingLinks(view);
		case PageEditPart.VISUAL_ID:
			return getPage_2004OutgoingLinks(view);
		case GroupEditPart.VISUAL_ID:
			return getGroup_2005OutgoingLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2006OutgoingLinks(view);
		case DocTopicEditPart.VISUAL_ID:
			return getDocTopic_3005OutgoingLinks(view);
		case DocTopic2EditPart.VISUAL_ID:
			return getDocTopic_3006OutgoingLinks(view);
		case DocTopic3EditPart.VISUAL_ID:
			return getDocTopic_3007OutgoingLinks(view);
		case DocTopic4EditPart.VISUAL_ID:
			return getDocTopic_3008OutgoingLinks(view);
		case DocTopic5EditPart.VISUAL_ID:
			return getDocTopic_3009OutgoingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDiagramRoot_1000ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getRoot_2001ContainedLinks(View view) {
		Root modelElement = (Root) view.getElement();
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getSiteView_2002ContainedLinks(
			View view) {
		SiteView modelElement = (SiteView) view.getElement();
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getArea_2003ContainedLinks(View view) {
		Area modelElement = (Area) view.getElement();
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getPage_2004ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getGroup_2005ContainedLinks(
			View view) {
		Group modelElement = (Group) view.getElement();
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getNode_2006ContainedLinks(View view) {
		Node modelElement = (Node) view.getElement();
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDocTopic_3005ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDocTopic_3006ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDocTopic_3007ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDocTopic_3008ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDocTopic_3009ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getRoot_2001IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getSiteView_2002IncomingLinks(
			View view) {
		SiteView modelElement = (SiteView) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_SourceRefElement_Class_4001(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getArea_2003IncomingLinks(View view) {
		Area modelElement = (Area) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_SourceRefElement_Class_4001(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getPage_2004IncomingLinks(View view) {
		Page modelElement = (Page) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_SourceRefElement_Class_4001(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getGroup_2005IncomingLinks(View view) {
		Group modelElement = (Group) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_SourceRefElement_Class_4001(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getNode_2006IncomingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_SourceRefElement_Class_4001(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDocTopic_3005IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDocTopic_3006IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDocTopic_3007IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDocTopic_3008IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDocTopic_3009IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getRoot_2001OutgoingLinks(View view) {
		Root modelElement = (Root) view.getElement();
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getSiteView_2002OutgoingLinks(
			View view) {
		SiteView modelElement = (SiteView) view.getElement();
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getArea_2003OutgoingLinks(View view) {
		Area modelElement = (Area) view.getElement();
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getPage_2004OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getGroup_2005OutgoingLinks(View view) {
		Group modelElement = (Group) view.getElement();
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getNode_2006OutgoingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDocTopic_3005OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDocTopic_3006OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDocTopic_3007OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDocTopic_3008OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<PlwebLinkDescriptor> getDocTopic_3009OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	private static Collection<PlwebLinkDescriptor> getIncomingFeatureModelFacetLinks_SourceRefElement_Class_4001(
			TargetRefElement target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences
				.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == PlwebPackage.eINSTANCE
					.getSourceRefElement_Class()) {
				result.add(new PlwebLinkDescriptor(setting.getEObject(),
						target, PlwebElementTypes.SourceRefElementClass_4001,
						SourceRefElementClassEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<PlwebLinkDescriptor> getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(
			SourceRefElement source) {
		LinkedList<PlwebLinkDescriptor> result = new LinkedList<PlwebLinkDescriptor>();
		for (Iterator<?> destinations = source.getClass_().iterator(); destinations
				.hasNext();) {
			TargetRefElement destination = (TargetRefElement) destinations
					.next();
			result.add(new PlwebLinkDescriptor(source, destination,
					PlwebElementTypes.SourceRefElementClass_4001,
					SourceRefElementClassEditPart.VISUAL_ID));
		}
		return result;
	}

}
