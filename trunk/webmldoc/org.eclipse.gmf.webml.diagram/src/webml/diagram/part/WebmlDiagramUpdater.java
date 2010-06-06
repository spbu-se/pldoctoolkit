/*
 * 
 */
package webml.diagram.part;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;

import webml.Area;
import webml.ContentUnit;
import webml.DocTopic;
import webml.OperationUnit;
import webml.Page;
import webml.Siteview;
import webml.Unit;
import webml.WebmlPackage;
import webml.diagram.edit.parts.Area2EditPart;
import webml.diagram.edit.parts.AreaAreaElementCompartment2EditPart;
import webml.diagram.edit.parts.AreaAreaElementCompartmentEditPart;
import webml.diagram.edit.parts.AreaAreaTopicCompartment2EditPart;
import webml.diagram.edit.parts.AreaAreaTopicCompartmentEditPart;
import webml.diagram.edit.parts.AreaEditPart;
import webml.diagram.edit.parts.ContentUnit2EditPart;
import webml.diagram.edit.parts.ContentUnitContentUnitTopicCompartment2EditPart;
import webml.diagram.edit.parts.ContentUnitContentUnitTopicCompartmentEditPart;
import webml.diagram.edit.parts.ContentUnitEditPart;
import webml.diagram.edit.parts.DocTopicEditPart;
import webml.diagram.edit.parts.KoLinkEditPart;
import webml.diagram.edit.parts.NormalLinkEditPart;
import webml.diagram.edit.parts.OkLinkEditPart;
import webml.diagram.edit.parts.OperationUnit2EditPart;
import webml.diagram.edit.parts.OperationUnitEditPart;
import webml.diagram.edit.parts.Page2EditPart;
import webml.diagram.edit.parts.PageEditPart;
import webml.diagram.edit.parts.PagePageElementCompartment2EditPart;
import webml.diagram.edit.parts.PagePageElementCompartmentEditPart;
import webml.diagram.edit.parts.PagePageTopicCompartment2EditPart;
import webml.diagram.edit.parts.PagePageTopicCompartmentEditPart;
import webml.diagram.edit.parts.SiteviewEditPart;
import webml.diagram.edit.parts.TransportLinkEditPart;
import webml.diagram.providers.WebmlElementTypes;

/**
 * @generated
 */
public class WebmlDiagramUpdater {

	/**
	 * @generated
	 */
	public static boolean isShortcutOrphaned(View view) {
		return !view.isSetElement() || view.getElement() == null
				|| view.getElement().eIsProxy();
	}

	/**
	 * @generated
	 */
	public static List getSemanticChildren(View view) {
		switch (WebmlVisualIDRegistry.getVisualID(view)) {
		case AreaAreaTopicCompartmentEditPart.VISUAL_ID:
			return getAreaAreaTopicCompartment_7001SemanticChildren(view);
		case AreaAreaElementCompartmentEditPart.VISUAL_ID:
			return getAreaAreaElementCompartment_7002SemanticChildren(view);
		case AreaAreaTopicCompartment2EditPart.VISUAL_ID:
			return getAreaAreaTopicCompartment_7009SemanticChildren(view);
		case AreaAreaElementCompartment2EditPart.VISUAL_ID:
			return getAreaAreaElementCompartment_7010SemanticChildren(view);
		case PagePageTopicCompartmentEditPart.VISUAL_ID:
			return getPagePageTopicCompartment_7003SemanticChildren(view);
		case PagePageElementCompartmentEditPart.VISUAL_ID:
			return getPagePageElementCompartment_7004SemanticChildren(view);
		case ContentUnitContentUnitTopicCompartmentEditPart.VISUAL_ID:
			return getContentUnitContentUnitTopicCompartment_7005SemanticChildren(view);
		case PagePageTopicCompartment2EditPart.VISUAL_ID:
			return getPagePageTopicCompartment_7006SemanticChildren(view);
		case PagePageElementCompartment2EditPart.VISUAL_ID:
			return getPagePageElementCompartment_7007SemanticChildren(view);
		case ContentUnitContentUnitTopicCompartment2EditPart.VISUAL_ID:
			return getContentUnitContentUnitTopicCompartment_7008SemanticChildren(view);
		case SiteviewEditPart.VISUAL_ID:
			return getSiteview_1000SemanticChildren(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getAreaAreaTopicCompartment_7001SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Area modelElement = (Area) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getTopic().iterator(); it.hasNext();) {
			DocTopic childElement = (DocTopic) it.next();
			int visualID = WebmlVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == DocTopicEditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getAreaAreaElementCompartment_7002SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Area modelElement = (Area) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getElement().iterator(); it.hasNext();) {
			Unit childElement = (Unit) it.next();
			int visualID = WebmlVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == Area2EditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Page2EditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == ContentUnit2EditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == OperationUnit2EditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getAreaAreaTopicCompartment_7009SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Area modelElement = (Area) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getTopic().iterator(); it.hasNext();) {
			DocTopic childElement = (DocTopic) it.next();
			int visualID = WebmlVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == DocTopicEditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getAreaAreaElementCompartment_7010SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Area modelElement = (Area) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getElement().iterator(); it.hasNext();) {
			Unit childElement = (Unit) it.next();
			int visualID = WebmlVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == Area2EditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Page2EditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == ContentUnit2EditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == OperationUnit2EditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPagePageTopicCompartment_7003SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Page modelElement = (Page) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getTopic().iterator(); it.hasNext();) {
			DocTopic childElement = (DocTopic) it.next();
			int visualID = WebmlVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == DocTopicEditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPagePageElementCompartment_7004SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Page modelElement = (Page) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getElement().iterator(); it.hasNext();) {
			Unit childElement = (Unit) it.next();
			int visualID = WebmlVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == Page2EditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == ContentUnit2EditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getContentUnitContentUnitTopicCompartment_7005SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		ContentUnit modelElement = (ContentUnit) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getTopic().iterator(); it.hasNext();) {
			DocTopic childElement = (DocTopic) it.next();
			int visualID = WebmlVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == DocTopicEditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPagePageTopicCompartment_7006SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Page modelElement = (Page) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getTopic().iterator(); it.hasNext();) {
			DocTopic childElement = (DocTopic) it.next();
			int visualID = WebmlVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == DocTopicEditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPagePageElementCompartment_7007SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Page modelElement = (Page) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getElement().iterator(); it.hasNext();) {
			Unit childElement = (Unit) it.next();
			int visualID = WebmlVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == Page2EditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == ContentUnit2EditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getContentUnitContentUnitTopicCompartment_7008SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		ContentUnit modelElement = (ContentUnit) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getTopic().iterator(); it.hasNext();) {
			DocTopic childElement = (DocTopic) it.next();
			int visualID = WebmlVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == DocTopicEditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getSiteview_1000SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Siteview modelElement = (Siteview) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getElement().iterator(); it.hasNext();) {
			Unit childElement = (Unit) it.next();
			int visualID = WebmlVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == AreaEditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PageEditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == ContentUnitEditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == OperationUnitEditPart.VISUAL_ID) {
				result.add(new WebmlNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getContainedLinks(View view) {
		switch (WebmlVisualIDRegistry.getVisualID(view)) {
		case SiteviewEditPart.VISUAL_ID:
			return getSiteview_1000ContainedLinks(view);
		case AreaEditPart.VISUAL_ID:
			return getArea_2001ContainedLinks(view);
		case PageEditPart.VISUAL_ID:
			return getPage_2002ContainedLinks(view);
		case ContentUnitEditPart.VISUAL_ID:
			return getContentUnit_2003ContainedLinks(view);
		case OperationUnitEditPart.VISUAL_ID:
			return getOperationUnit_2004ContainedLinks(view);
		case Area2EditPart.VISUAL_ID:
			return getArea_3005ContainedLinks(view);
		case Page2EditPart.VISUAL_ID:
			return getPage_3001ContainedLinks(view);
		case ContentUnit2EditPart.VISUAL_ID:
			return getContentUnit_3002ContainedLinks(view);
		case DocTopicEditPart.VISUAL_ID:
			return getDocTopic_3003ContainedLinks(view);
		case OperationUnit2EditPart.VISUAL_ID:
			return getOperationUnit_3004ContainedLinks(view);
		case OkLinkEditPart.VISUAL_ID:
			return getOkLink_4001ContainedLinks(view);
		case KoLinkEditPart.VISUAL_ID:
			return getKoLink_4002ContainedLinks(view);
		case NormalLinkEditPart.VISUAL_ID:
			return getNormalLink_4003ContainedLinks(view);
		case TransportLinkEditPart.VISUAL_ID:
			return getTransportLink_4004ContainedLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getIncomingLinks(View view) {
		switch (WebmlVisualIDRegistry.getVisualID(view)) {
		case AreaEditPart.VISUAL_ID:
			return getArea_2001IncomingLinks(view);
		case PageEditPart.VISUAL_ID:
			return getPage_2002IncomingLinks(view);
		case ContentUnitEditPart.VISUAL_ID:
			return getContentUnit_2003IncomingLinks(view);
		case OperationUnitEditPart.VISUAL_ID:
			return getOperationUnit_2004IncomingLinks(view);
		case Area2EditPart.VISUAL_ID:
			return getArea_3005IncomingLinks(view);
		case Page2EditPart.VISUAL_ID:
			return getPage_3001IncomingLinks(view);
		case ContentUnit2EditPart.VISUAL_ID:
			return getContentUnit_3002IncomingLinks(view);
		case DocTopicEditPart.VISUAL_ID:
			return getDocTopic_3003IncomingLinks(view);
		case OperationUnit2EditPart.VISUAL_ID:
			return getOperationUnit_3004IncomingLinks(view);
		case OkLinkEditPart.VISUAL_ID:
			return getOkLink_4001IncomingLinks(view);
		case KoLinkEditPart.VISUAL_ID:
			return getKoLink_4002IncomingLinks(view);
		case NormalLinkEditPart.VISUAL_ID:
			return getNormalLink_4003IncomingLinks(view);
		case TransportLinkEditPart.VISUAL_ID:
			return getTransportLink_4004IncomingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOutgoingLinks(View view) {
		switch (WebmlVisualIDRegistry.getVisualID(view)) {
		case AreaEditPart.VISUAL_ID:
			return getArea_2001OutgoingLinks(view);
		case PageEditPart.VISUAL_ID:
			return getPage_2002OutgoingLinks(view);
		case ContentUnitEditPart.VISUAL_ID:
			return getContentUnit_2003OutgoingLinks(view);
		case OperationUnitEditPart.VISUAL_ID:
			return getOperationUnit_2004OutgoingLinks(view);
		case Area2EditPart.VISUAL_ID:
			return getArea_3005OutgoingLinks(view);
		case Page2EditPart.VISUAL_ID:
			return getPage_3001OutgoingLinks(view);
		case ContentUnit2EditPart.VISUAL_ID:
			return getContentUnit_3002OutgoingLinks(view);
		case DocTopicEditPart.VISUAL_ID:
			return getDocTopic_3003OutgoingLinks(view);
		case OperationUnit2EditPart.VISUAL_ID:
			return getOperationUnit_3004OutgoingLinks(view);
		case OkLinkEditPart.VISUAL_ID:
			return getOkLink_4001OutgoingLinks(view);
		case KoLinkEditPart.VISUAL_ID:
			return getKoLink_4002OutgoingLinks(view);
		case NormalLinkEditPart.VISUAL_ID:
			return getNormalLink_4003OutgoingLinks(view);
		case TransportLinkEditPart.VISUAL_ID:
			return getTransportLink_4004OutgoingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getSiteview_1000ContainedLinks(View view) {
		Siteview modelElement = (Siteview) view.getElement();
		List result = new LinkedList();
		result
				.addAll(getContainedTypeModelFacetLinks_okLink_4001(modelElement));
		result
				.addAll(getContainedTypeModelFacetLinks_koLink_4002(modelElement));
		result
				.addAll(getContainedTypeModelFacetLinks_normalLink_4003(modelElement));
		result
				.addAll(getContainedTypeModelFacetLinks_transportLink_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getArea_2001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPage_2002ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getContentUnit_2003ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOperationUnit_2004ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getArea_3005ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPage_3001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getContentUnit_3002ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getDocTopic_3003ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOperationUnit_3004ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOkLink_4001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getKoLink_4002ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getNormalLink_4003ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getTransportLink_4004ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getArea_2001IncomingLinks(View view) {
		Area modelElement = (Area) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_okLink_4001(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_koLink_4002(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_normalLink_4003(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_transportLink_4004(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPage_2002IncomingLinks(View view) {
		Page modelElement = (Page) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_okLink_4001(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_koLink_4002(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_normalLink_4003(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_transportLink_4004(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getContentUnit_2003IncomingLinks(View view) {
		ContentUnit modelElement = (ContentUnit) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_okLink_4001(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_koLink_4002(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_normalLink_4003(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_transportLink_4004(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getOperationUnit_2004IncomingLinks(View view) {
		OperationUnit modelElement = (OperationUnit) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_okLink_4001(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_koLink_4002(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_normalLink_4003(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_transportLink_4004(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getArea_3005IncomingLinks(View view) {
		Area modelElement = (Area) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_okLink_4001(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_koLink_4002(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_normalLink_4003(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_transportLink_4004(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPage_3001IncomingLinks(View view) {
		Page modelElement = (Page) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_okLink_4001(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_koLink_4002(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_normalLink_4003(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_transportLink_4004(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getContentUnit_3002IncomingLinks(View view) {
		ContentUnit modelElement = (ContentUnit) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_okLink_4001(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_koLink_4002(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_normalLink_4003(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_transportLink_4004(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getDocTopic_3003IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOperationUnit_3004IncomingLinks(View view) {
		OperationUnit modelElement = (OperationUnit) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_okLink_4001(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_koLink_4002(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_normalLink_4003(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_transportLink_4004(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getOkLink_4001IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getKoLink_4002IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getNormalLink_4003IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getTransportLink_4004IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getArea_2001OutgoingLinks(View view) {
		Area modelElement = (Area) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_okLink_4001(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_koLink_4002(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_normalLink_4003(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_transportLink_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPage_2002OutgoingLinks(View view) {
		Page modelElement = (Page) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_okLink_4001(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_koLink_4002(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_normalLink_4003(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_transportLink_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getContentUnit_2003OutgoingLinks(View view) {
		ContentUnit modelElement = (ContentUnit) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_okLink_4001(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_koLink_4002(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_normalLink_4003(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_transportLink_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getOperationUnit_2004OutgoingLinks(View view) {
		OperationUnit modelElement = (OperationUnit) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_okLink_4001(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_koLink_4002(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_normalLink_4003(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_transportLink_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getArea_3005OutgoingLinks(View view) {
		Area modelElement = (Area) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_okLink_4001(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_koLink_4002(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_normalLink_4003(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_transportLink_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPage_3001OutgoingLinks(View view) {
		Page modelElement = (Page) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_okLink_4001(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_koLink_4002(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_normalLink_4003(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_transportLink_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getContentUnit_3002OutgoingLinks(View view) {
		ContentUnit modelElement = (ContentUnit) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_okLink_4001(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_koLink_4002(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_normalLink_4003(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_transportLink_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getDocTopic_3003OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOperationUnit_3004OutgoingLinks(View view) {
		OperationUnit modelElement = (OperationUnit) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_okLink_4001(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_koLink_4002(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_normalLink_4003(modelElement));
		result
				.addAll(getOutgoingTypeModelFacetLinks_transportLink_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getOkLink_4001OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getKoLink_4002OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getNormalLink_4003OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getTransportLink_4004OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	private static Collection getContainedTypeModelFacetLinks_okLink_4001(
			Siteview container) {
		Collection result = new LinkedList();
		for (Iterator links = container.getOklink().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof webml.okLink) {
				continue;
			}
			webml.okLink link = (webml.okLink) linkObject;
			if (OkLinkEditPart.VISUAL_ID != WebmlVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Unit dst = link.getTarget();
			Unit src = link.getSource();
			result.add(new WebmlLinkDescriptor(src, dst, link,
					WebmlElementTypes.OkLink_4001, OkLinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getContainedTypeModelFacetLinks_koLink_4002(
			Siteview container) {
		Collection result = new LinkedList();
		for (Iterator links = container.getKolink().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof webml.koLink) {
				continue;
			}
			webml.koLink link = (webml.koLink) linkObject;
			if (KoLinkEditPart.VISUAL_ID != WebmlVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Unit dst = link.getTarget();
			Unit src = link.getSource();
			result.add(new WebmlLinkDescriptor(src, dst, link,
					WebmlElementTypes.KoLink_4002, KoLinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getContainedTypeModelFacetLinks_normalLink_4003(
			Siteview container) {
		Collection result = new LinkedList();
		for (Iterator links = container.getNlink().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof webml.normalLink) {
				continue;
			}
			webml.normalLink link = (webml.normalLink) linkObject;
			if (NormalLinkEditPart.VISUAL_ID != WebmlVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Unit dst = link.getTarget();
			Unit src = link.getSource();
			result.add(new WebmlLinkDescriptor(src, dst, link,
					WebmlElementTypes.NormalLink_4003,
					NormalLinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getContainedTypeModelFacetLinks_transportLink_4004(
			Siteview container) {
		Collection result = new LinkedList();
		for (Iterator links = container.getTlink().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof webml.transportLink) {
				continue;
			}
			webml.transportLink link = (webml.transportLink) linkObject;
			if (TransportLinkEditPart.VISUAL_ID != WebmlVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Unit dst = link.getTarget();
			Unit src = link.getSource();
			result.add(new WebmlLinkDescriptor(src, dst, link,
					WebmlElementTypes.TransportLink_4004,
					TransportLinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingTypeModelFacetLinks_okLink_4001(
			Unit target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it
					.next();
			if (setting.getEStructuralFeature() != WebmlPackage.eINSTANCE
					.getokLink_Target()
					|| false == setting.getEObject() instanceof webml.okLink) {
				continue;
			}
			webml.okLink link = (webml.okLink) setting.getEObject();
			if (OkLinkEditPart.VISUAL_ID != WebmlVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Unit src = link.getSource();
			result.add(new WebmlLinkDescriptor(src, target, link,
					WebmlElementTypes.OkLink_4001, OkLinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingTypeModelFacetLinks_koLink_4002(
			Unit target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it
					.next();
			if (setting.getEStructuralFeature() != WebmlPackage.eINSTANCE
					.getkoLink_Target()
					|| false == setting.getEObject() instanceof webml.koLink) {
				continue;
			}
			webml.koLink link = (webml.koLink) setting.getEObject();
			if (KoLinkEditPart.VISUAL_ID != WebmlVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Unit src = link.getSource();
			result.add(new WebmlLinkDescriptor(src, target, link,
					WebmlElementTypes.KoLink_4002, KoLinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingTypeModelFacetLinks_normalLink_4003(
			Unit target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it
					.next();
			if (setting.getEStructuralFeature() != WebmlPackage.eINSTANCE
					.getnormalLink_Target()
					|| false == setting.getEObject() instanceof webml.normalLink) {
				continue;
			}
			webml.normalLink link = (webml.normalLink) setting.getEObject();
			if (NormalLinkEditPart.VISUAL_ID != WebmlVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Unit src = link.getSource();
			result.add(new WebmlLinkDescriptor(src, target, link,
					WebmlElementTypes.NormalLink_4003,
					NormalLinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingTypeModelFacetLinks_transportLink_4004(
			Unit target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it
					.next();
			if (setting.getEStructuralFeature() != WebmlPackage.eINSTANCE
					.gettransportLink_Target()
					|| false == setting.getEObject() instanceof webml.transportLink) {
				continue;
			}
			webml.transportLink link = (webml.transportLink) setting
					.getEObject();
			if (TransportLinkEditPart.VISUAL_ID != WebmlVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Unit src = link.getSource();
			result.add(new WebmlLinkDescriptor(src, target, link,
					WebmlElementTypes.TransportLink_4004,
					TransportLinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getOutgoingTypeModelFacetLinks_okLink_4001(
			Unit source) {
		Siteview container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof Siteview) {
				container = (Siteview) element;
			}
		}
		if (container == null) {
			return Collections.EMPTY_LIST;
		}
		Collection result = new LinkedList();
		for (Iterator links = container.getOklink().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof webml.okLink) {
				continue;
			}
			webml.okLink link = (webml.okLink) linkObject;
			if (OkLinkEditPart.VISUAL_ID != WebmlVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Unit dst = link.getTarget();
			Unit src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new WebmlLinkDescriptor(src, dst, link,
					WebmlElementTypes.OkLink_4001, OkLinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getOutgoingTypeModelFacetLinks_koLink_4002(
			Unit source) {
		Siteview container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof Siteview) {
				container = (Siteview) element;
			}
		}
		if (container == null) {
			return Collections.EMPTY_LIST;
		}
		Collection result = new LinkedList();
		for (Iterator links = container.getKolink().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof webml.koLink) {
				continue;
			}
			webml.koLink link = (webml.koLink) linkObject;
			if (KoLinkEditPart.VISUAL_ID != WebmlVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Unit dst = link.getTarget();
			Unit src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new WebmlLinkDescriptor(src, dst, link,
					WebmlElementTypes.KoLink_4002, KoLinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getOutgoingTypeModelFacetLinks_normalLink_4003(
			Unit source) {
		Siteview container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof Siteview) {
				container = (Siteview) element;
			}
		}
		if (container == null) {
			return Collections.EMPTY_LIST;
		}
		Collection result = new LinkedList();
		for (Iterator links = container.getNlink().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof webml.normalLink) {
				continue;
			}
			webml.normalLink link = (webml.normalLink) linkObject;
			if (NormalLinkEditPart.VISUAL_ID != WebmlVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Unit dst = link.getTarget();
			Unit src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new WebmlLinkDescriptor(src, dst, link,
					WebmlElementTypes.NormalLink_4003,
					NormalLinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getOutgoingTypeModelFacetLinks_transportLink_4004(
			Unit source) {
		Siteview container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof Siteview) {
				container = (Siteview) element;
			}
		}
		if (container == null) {
			return Collections.EMPTY_LIST;
		}
		Collection result = new LinkedList();
		for (Iterator links = container.getTlink().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof webml.transportLink) {
				continue;
			}
			webml.transportLink link = (webml.transportLink) linkObject;
			if (TransportLinkEditPart.VISUAL_ID != WebmlVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Unit dst = link.getTarget();
			Unit src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new WebmlLinkDescriptor(src, dst, link,
					WebmlElementTypes.TransportLink_4004,
					TransportLinkEditPart.VISUAL_ID));
		}
		return result;
	}

}
