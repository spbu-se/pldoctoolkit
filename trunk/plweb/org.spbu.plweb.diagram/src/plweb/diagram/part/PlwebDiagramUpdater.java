package plweb.diagram.part;

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

import plweb.Area;
import plweb.DiagramRoot;
import plweb.Group;
import plweb.Node;
import plweb.Page;
import plweb.PlwebPackage;
import plweb.SourceRefElement;
import plweb.TargetRefElement;
import plweb.diagram.edit.parts.AreaEditPart;
import plweb.diagram.edit.parts.DiagramRootEditPart;
import plweb.diagram.edit.parts.GroupEditPart;
import plweb.diagram.edit.parts.NodeEditPart;
import plweb.diagram.edit.parts.PageEditPart;
import plweb.diagram.edit.parts.SourceRefElementClassEditPart;
import plweb.diagram.providers.PlwebElementTypes;

/**
 * @generated
 */
public class PlwebDiagramUpdater {

	/**
	 * @generated
	 */
	public static List getSemanticChildren(View view) {
		switch (PlwebVisualIDRegistry.getVisualID(view)) {
		case DiagramRootEditPart.VISUAL_ID:
			return getDiagramRoot_1000SemanticChildren(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getDiagramRoot_1000SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		DiagramRoot modelElement = (DiagramRoot) view.getElement();
		List result = new LinkedList();
		{
			Area childElement = modelElement.getArea();
			int visualID = PlwebVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == AreaEditPart.VISUAL_ID) {
				result.add(new PlwebNodeDescriptor(childElement, visualID));
			}
		}
		Resource resource = modelElement.eResource();
		for (Iterator semanticIterator = getPhantomNodesIterator(resource); semanticIterator
				.hasNext();) {
			EObject childElement = (EObject) semanticIterator.next();
			if (childElement == modelElement) {
				continue;
			}
			if (PlwebVisualIDRegistry.getNodeVisualID(view, childElement) == GroupEditPart.VISUAL_ID) {
				result.add(new PlwebNodeDescriptor(childElement,
						GroupEditPart.VISUAL_ID));
				continue;
			}
			if (PlwebVisualIDRegistry.getNodeVisualID(view, childElement) == PageEditPart.VISUAL_ID) {
				result.add(new PlwebNodeDescriptor(childElement,
						PageEditPart.VISUAL_ID));
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
	private static Iterator getPhantomNodesIterator(Resource resource) {
		return resource.getAllContents();
	}

	/**
	 * @generated
	 */
	public static List getContainedLinks(View view) {
		switch (PlwebVisualIDRegistry.getVisualID(view)) {
		case DiagramRootEditPart.VISUAL_ID:
			return getDiagramRoot_1000ContainedLinks(view);
		case AreaEditPart.VISUAL_ID:
			return getArea_2001ContainedLinks(view);
		case GroupEditPart.VISUAL_ID:
			return getGroup_2002ContainedLinks(view);
		case PageEditPart.VISUAL_ID:
			return getPage_2003ContainedLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2004ContainedLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getIncomingLinks(View view) {
		switch (PlwebVisualIDRegistry.getVisualID(view)) {
		case AreaEditPart.VISUAL_ID:
			return getArea_2001IncomingLinks(view);
		case GroupEditPart.VISUAL_ID:
			return getGroup_2002IncomingLinks(view);
		case PageEditPart.VISUAL_ID:
			return getPage_2003IncomingLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2004IncomingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOutgoingLinks(View view) {
		switch (PlwebVisualIDRegistry.getVisualID(view)) {
		case AreaEditPart.VISUAL_ID:
			return getArea_2001OutgoingLinks(view);
		case GroupEditPart.VISUAL_ID:
			return getGroup_2002OutgoingLinks(view);
		case PageEditPart.VISUAL_ID:
			return getPage_2003OutgoingLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2004OutgoingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getDiagramRoot_1000ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getArea_2001ContainedLinks(View view) {
		Area modelElement = (Area) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getGroup_2002ContainedLinks(View view) {
		Group modelElement = (Group) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPage_2003ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getNode_2004ContainedLinks(View view) {
		Node modelElement = (Node) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getArea_2001IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getGroup_2002IncomingLinks(View view) {
		Group modelElement = (Group) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result
				.addAll(getIncomingFeatureModelFacetLinks_SourceRefElement_Class_4001(
						modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPage_2003IncomingLinks(View view) {
		Page modelElement = (Page) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result
				.addAll(getIncomingFeatureModelFacetLinks_SourceRefElement_Class_4001(
						modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getNode_2004IncomingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result
				.addAll(getIncomingFeatureModelFacetLinks_SourceRefElement_Class_4001(
						modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getArea_2001OutgoingLinks(View view) {
		Area modelElement = (Area) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getGroup_2002OutgoingLinks(View view) {
		Group modelElement = (Group) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPage_2003OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getNode_2004OutgoingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingFeatureModelFacetLinks_SourceRefElement_Class_4001(
			TargetRefElement target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it
					.next();
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
	private static Collection getOutgoingFeatureModelFacetLinks_SourceRefElement_Class_4001(
			SourceRefElement source) {
		Collection result = new LinkedList();
		for (Iterator destinations = source.getClass_().iterator(); destinations
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
