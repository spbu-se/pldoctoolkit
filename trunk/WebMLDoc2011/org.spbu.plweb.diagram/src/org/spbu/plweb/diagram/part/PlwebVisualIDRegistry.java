package org.spbu.plweb.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.plweb.DiagramRoot;
import org.spbu.plweb.PlwebPackage;
import org.spbu.plweb.diagram.edit.parts.AreaEditPart;
import org.spbu.plweb.diagram.edit.parts.AreaTitleEditPart;
import org.spbu.plweb.diagram.edit.parts.AreaTopicAreaCompartmentEditPart;
import org.spbu.plweb.diagram.edit.parts.DiagramRootEditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic2EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic3EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic4EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic5EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicName2EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicName3EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicName4EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicName5EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicNameEditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicEditPart;
import org.spbu.plweb.diagram.edit.parts.GroupEditPart;
import org.spbu.plweb.diagram.edit.parts.NodeEditPart;
import org.spbu.plweb.diagram.edit.parts.NodeTitleEditPart;
import org.spbu.plweb.diagram.edit.parts.NodeTopicNodeCompartmentEditPart;
import org.spbu.plweb.diagram.edit.parts.PageEditPart;
import org.spbu.plweb.diagram.edit.parts.PageTitleEditPart;
import org.spbu.plweb.diagram.edit.parts.PageTopicPageCompartmentEditPart;
import org.spbu.plweb.diagram.edit.parts.RootEditPart;
import org.spbu.plweb.diagram.edit.parts.RootTopicRootCompartmentEditPart;
import org.spbu.plweb.diagram.edit.parts.SiteViewEditPart;
import org.spbu.plweb.diagram.edit.parts.SiteViewTitleEditPart;
import org.spbu.plweb.diagram.edit.parts.SiteViewTopicSiteViewCompartmentEditPart;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented
 * by a domain model object.
 * 
 * @generated
 */
public class PlwebVisualIDRegistry {

	/**
	 * @generated
	 */
	private static final String DEBUG_KEY = "org.spbu.plweb.diagram/debug/visualID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static int getVisualID(View view) {
		if (view instanceof Diagram) {
			if (DiagramRootEditPart.MODEL_ID.equals(view.getType())) {
				return DiagramRootEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		return org.spbu.plweb.diagram.part.PlwebVisualIDRegistry
				.getVisualID(view.getType());
	}

	/**
	 * @generated
	 */
	public static String getModelID(View view) {
		View diagram = view.getDiagram();
		while (view != diagram) {
			EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
			if (annotation != null) {
				return (String) annotation.getDetails().get("modelID"); //$NON-NLS-1$
			}
			view = (View) view.eContainer();
		}
		return diagram != null ? diagram.getType() : null;
	}

	/**
	 * @generated
	 */
	public static int getVisualID(String type) {
		try {
			return Integer.parseInt(type);
		} catch (NumberFormatException e) {
			if (Boolean.TRUE.toString().equalsIgnoreCase(
					Platform.getDebugOption(DEBUG_KEY))) {
				PlwebDiagramEditorPlugin.getInstance().logError(
						"Unable to parse view type as a visualID number: "
								+ type);
			}
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static String getType(int visualID) {
		return Integer.toString(visualID);
	}

	/**
	 * @generated
	 */
	public static int getDiagramVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		if (PlwebPackage.eINSTANCE.getDiagramRoot().isSuperTypeOf(
				domainElement.eClass())
				&& isDiagram((DiagramRoot) domainElement)) {
			return DiagramRootEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static int getNodeVisualID(View containerView, EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		String containerModelID = org.spbu.plweb.diagram.part.PlwebVisualIDRegistry
				.getModelID(containerView);
		if (!DiagramRootEditPart.MODEL_ID.equals(containerModelID)) {
			return -1;
		}
		int containerVisualID;
		if (DiagramRootEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.spbu.plweb.diagram.part.PlwebVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = DiagramRootEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		switch (containerVisualID) {
		case DiagramRootEditPart.VISUAL_ID:
			if (PlwebPackage.eINSTANCE.getRoot().isSuperTypeOf(
					domainElement.eClass())) {
				return RootEditPart.VISUAL_ID;
			}
			if (PlwebPackage.eINSTANCE.getSiteView().isSuperTypeOf(
					domainElement.eClass())) {
				return SiteViewEditPart.VISUAL_ID;
			}
			if (PlwebPackage.eINSTANCE.getArea().isSuperTypeOf(
					domainElement.eClass())) {
				return AreaEditPart.VISUAL_ID;
			}
			if (PlwebPackage.eINSTANCE.getPage().isSuperTypeOf(
					domainElement.eClass())) {
				return PageEditPart.VISUAL_ID;
			}
			if (PlwebPackage.eINSTANCE.getGroup().isSuperTypeOf(
					domainElement.eClass())) {
				return GroupEditPart.VISUAL_ID;
			}
			if (PlwebPackage.eINSTANCE.getNode().isSuperTypeOf(
					domainElement.eClass())) {
				return NodeEditPart.VISUAL_ID;
			}
			break;
		case RootEditPart.VISUAL_ID:
			if (PlwebPackage.eINSTANCE.getDocTopic().isSuperTypeOf(
					domainElement.eClass())) {
				return DocTopicEditPart.VISUAL_ID;
			}
			break;
		case SiteViewEditPart.VISUAL_ID:
			if (PlwebPackage.eINSTANCE.getDocTopic().isSuperTypeOf(
					domainElement.eClass())) {
				return DocTopic2EditPart.VISUAL_ID;
			}
			break;
		case AreaEditPart.VISUAL_ID:
			if (PlwebPackage.eINSTANCE.getDocTopic().isSuperTypeOf(
					domainElement.eClass())) {
				return DocTopic3EditPart.VISUAL_ID;
			}
			break;
		case PageEditPart.VISUAL_ID:
			if (PlwebPackage.eINSTANCE.getDocTopic().isSuperTypeOf(
					domainElement.eClass())) {
				return DocTopic4EditPart.VISUAL_ID;
			}
			break;
		case NodeEditPart.VISUAL_ID:
			if (PlwebPackage.eINSTANCE.getDocTopic().isSuperTypeOf(
					domainElement.eClass())) {
				return DocTopic5EditPart.VISUAL_ID;
			}
			break;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static boolean canCreateNode(View containerView, int nodeVisualID) {
		String containerModelID = org.spbu.plweb.diagram.part.PlwebVisualIDRegistry
				.getModelID(containerView);
		if (!DiagramRootEditPart.MODEL_ID.equals(containerModelID)) {
			return false;
		}
		int containerVisualID;
		if (DiagramRootEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.spbu.plweb.diagram.part.PlwebVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = DiagramRootEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		switch (containerVisualID) {
		case DiagramRootEditPart.VISUAL_ID:
			if (RootEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (SiteViewEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (AreaEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (PageEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (GroupEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (NodeEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case RootEditPart.VISUAL_ID:
			if (RootTopicRootCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (DocTopicEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case SiteViewEditPart.VISUAL_ID:
			if (SiteViewTitleEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (SiteViewTopicSiteViewCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (DocTopic2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case AreaEditPart.VISUAL_ID:
			if (AreaTitleEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (AreaTopicAreaCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (DocTopic3EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case PageEditPart.VISUAL_ID:
			if (PageTitleEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (PageTopicPageCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (DocTopic4EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case NodeEditPart.VISUAL_ID:
			if (NodeTitleEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (NodeTopicNodeCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (DocTopic5EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case DocTopicEditPart.VISUAL_ID:
			if (DocTopicDocTopicName5EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case DocTopic2EditPart.VISUAL_ID:
			if (DocTopicDocTopicNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case DocTopic3EditPart.VISUAL_ID:
			if (DocTopicDocTopicName2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case DocTopic4EditPart.VISUAL_ID:
			if (DocTopicDocTopicName3EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case DocTopic5EditPart.VISUAL_ID:
			if (DocTopicDocTopicName4EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static int getLinkWithClassVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 * 
	 * @generated
	 */
	private static boolean isDiagram(DiagramRoot element) {
		return true;
	}

}
