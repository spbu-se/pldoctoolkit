/*
 * 
 */
package webml.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

import webml.Siteview;
import webml.WebmlPackage;
import webml.diagram.edit.parts.Area2EditPart;
import webml.diagram.edit.parts.AreaAreaElementCompartment2EditPart;
import webml.diagram.edit.parts.AreaAreaElementCompartmentEditPart;
import webml.diagram.edit.parts.AreaAreaTopicCompartment2EditPart;
import webml.diagram.edit.parts.AreaAreaTopicCompartmentEditPart;
import webml.diagram.edit.parts.AreaEditPart;
import webml.diagram.edit.parts.AreaName2EditPart;
import webml.diagram.edit.parts.AreaNameEditPart;
import webml.diagram.edit.parts.ContentUnit2EditPart;
import webml.diagram.edit.parts.ContentUnitContentUnitTopicCompartment2EditPart;
import webml.diagram.edit.parts.ContentUnitContentUnitTopicCompartmentEditPart;
import webml.diagram.edit.parts.ContentUnitEditPart;
import webml.diagram.edit.parts.ContentUnitName2EditPart;
import webml.diagram.edit.parts.ContentUnitNameEditPart;
import webml.diagram.edit.parts.DocTopicEditPart;
import webml.diagram.edit.parts.DocTopicNameEditPart;
import webml.diagram.edit.parts.KoLinkEditPart;
import webml.diagram.edit.parts.NormalLinkEditPart;
import webml.diagram.edit.parts.OkLinkEditPart;
import webml.diagram.edit.parts.OperationUnit2EditPart;
import webml.diagram.edit.parts.OperationUnitEditPart;
import webml.diagram.edit.parts.OperationUnitName2EditPart;
import webml.diagram.edit.parts.OperationUnitNameEditPart;
import webml.diagram.edit.parts.Page2EditPart;
import webml.diagram.edit.parts.PageEditPart;
import webml.diagram.edit.parts.PageName2EditPart;
import webml.diagram.edit.parts.PageNameEditPart;
import webml.diagram.edit.parts.PagePageElementCompartment2EditPart;
import webml.diagram.edit.parts.PagePageElementCompartmentEditPart;
import webml.diagram.edit.parts.PagePageTopicCompartment2EditPart;
import webml.diagram.edit.parts.PagePageTopicCompartmentEditPart;
import webml.diagram.edit.parts.SiteviewEditPart;
import webml.diagram.edit.parts.TransportLinkEditPart;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented
 * by a domain model object.
 * 
 * @generated
 */
public class WebmlVisualIDRegistry {

	/**
	 * @generated
	 */
	private static final String DEBUG_KEY = "org.eclipse.gmf.webml.diagram/debug/visualID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static int getVisualID(View view) {
		if (view instanceof Diagram) {
			if (SiteviewEditPart.MODEL_ID.equals(view.getType())) {
				return SiteviewEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		return webml.diagram.part.WebmlVisualIDRegistry.getVisualID(view
				.getType());
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
				WebmlDiagramEditorPlugin.getInstance().logError(
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
		return String.valueOf(visualID);
	}

	/**
	 * @generated
	 */
	public static int getDiagramVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		if (WebmlPackage.eINSTANCE.getSiteview().isSuperTypeOf(
				domainElement.eClass())
				&& isDiagram((Siteview) domainElement)) {
			return SiteviewEditPart.VISUAL_ID;
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
		String containerModelID = webml.diagram.part.WebmlVisualIDRegistry
				.getModelID(containerView);
		if (!SiteviewEditPart.MODEL_ID.equals(containerModelID)
				&& !"webml".equals(containerModelID)) { //$NON-NLS-1$
			return -1;
		}
		int containerVisualID;
		if (SiteviewEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = webml.diagram.part.WebmlVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = SiteviewEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		switch (containerVisualID) {
		case AreaAreaTopicCompartmentEditPart.VISUAL_ID:
			if (WebmlPackage.eINSTANCE.getDocTopic().isSuperTypeOf(
					domainElement.eClass())) {
				return DocTopicEditPart.VISUAL_ID;
			}
			break;
		case AreaAreaElementCompartmentEditPart.VISUAL_ID:
			if (WebmlPackage.eINSTANCE.getArea().isSuperTypeOf(
					domainElement.eClass())) {
				return Area2EditPart.VISUAL_ID;
			}
			if (WebmlPackage.eINSTANCE.getPage().isSuperTypeOf(
					domainElement.eClass())) {
				return Page2EditPart.VISUAL_ID;
			}
			if (WebmlPackage.eINSTANCE.getContentUnit().isSuperTypeOf(
					domainElement.eClass())) {
				return ContentUnit2EditPart.VISUAL_ID;
			}
			if (WebmlPackage.eINSTANCE.getOperationUnit().isSuperTypeOf(
					domainElement.eClass())) {
				return OperationUnit2EditPart.VISUAL_ID;
			}
			break;
		case AreaAreaTopicCompartment2EditPart.VISUAL_ID:
			if (WebmlPackage.eINSTANCE.getDocTopic().isSuperTypeOf(
					domainElement.eClass())) {
				return DocTopicEditPart.VISUAL_ID;
			}
			break;
		case AreaAreaElementCompartment2EditPart.VISUAL_ID:
			if (WebmlPackage.eINSTANCE.getArea().isSuperTypeOf(
					domainElement.eClass())) {
				return Area2EditPart.VISUAL_ID;
			}
			if (WebmlPackage.eINSTANCE.getPage().isSuperTypeOf(
					domainElement.eClass())) {
				return Page2EditPart.VISUAL_ID;
			}
			if (WebmlPackage.eINSTANCE.getContentUnit().isSuperTypeOf(
					domainElement.eClass())) {
				return ContentUnit2EditPart.VISUAL_ID;
			}
			if (WebmlPackage.eINSTANCE.getOperationUnit().isSuperTypeOf(
					domainElement.eClass())) {
				return OperationUnit2EditPart.VISUAL_ID;
			}
			break;
		case PagePageTopicCompartmentEditPart.VISUAL_ID:
			if (WebmlPackage.eINSTANCE.getDocTopic().isSuperTypeOf(
					domainElement.eClass())) {
				return DocTopicEditPart.VISUAL_ID;
			}
			break;
		case PagePageElementCompartmentEditPart.VISUAL_ID:
			if (WebmlPackage.eINSTANCE.getPage().isSuperTypeOf(
					domainElement.eClass())) {
				return Page2EditPart.VISUAL_ID;
			}
			if (WebmlPackage.eINSTANCE.getContentUnit().isSuperTypeOf(
					domainElement.eClass())) {
				return ContentUnit2EditPart.VISUAL_ID;
			}
			break;
		case ContentUnitContentUnitTopicCompartmentEditPart.VISUAL_ID:
			if (WebmlPackage.eINSTANCE.getDocTopic().isSuperTypeOf(
					domainElement.eClass())) {
				return DocTopicEditPart.VISUAL_ID;
			}
			break;
		case PagePageTopicCompartment2EditPart.VISUAL_ID:
			if (WebmlPackage.eINSTANCE.getDocTopic().isSuperTypeOf(
					domainElement.eClass())) {
				return DocTopicEditPart.VISUAL_ID;
			}
			break;
		case PagePageElementCompartment2EditPart.VISUAL_ID:
			if (WebmlPackage.eINSTANCE.getPage().isSuperTypeOf(
					domainElement.eClass())) {
				return Page2EditPart.VISUAL_ID;
			}
			if (WebmlPackage.eINSTANCE.getContentUnit().isSuperTypeOf(
					domainElement.eClass())) {
				return ContentUnit2EditPart.VISUAL_ID;
			}
			break;
		case ContentUnitContentUnitTopicCompartment2EditPart.VISUAL_ID:
			if (WebmlPackage.eINSTANCE.getDocTopic().isSuperTypeOf(
					domainElement.eClass())) {
				return DocTopicEditPart.VISUAL_ID;
			}
			break;
		case SiteviewEditPart.VISUAL_ID:
			if (WebmlPackage.eINSTANCE.getArea().isSuperTypeOf(
					domainElement.eClass())) {
				return AreaEditPart.VISUAL_ID;
			}
			if (WebmlPackage.eINSTANCE.getPage().isSuperTypeOf(
					domainElement.eClass())) {
				return PageEditPart.VISUAL_ID;
			}
			if (WebmlPackage.eINSTANCE.getContentUnit().isSuperTypeOf(
					domainElement.eClass())) {
				return ContentUnitEditPart.VISUAL_ID;
			}
			if (WebmlPackage.eINSTANCE.getOperationUnit().isSuperTypeOf(
					domainElement.eClass())) {
				return OperationUnitEditPart.VISUAL_ID;
			}
			break;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static boolean canCreateNode(View containerView, int nodeVisualID) {
		String containerModelID = webml.diagram.part.WebmlVisualIDRegistry
				.getModelID(containerView);
		if (!SiteviewEditPart.MODEL_ID.equals(containerModelID)
				&& !"webml".equals(containerModelID)) { //$NON-NLS-1$
			return false;
		}
		int containerVisualID;
		if (SiteviewEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = webml.diagram.part.WebmlVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = SiteviewEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		switch (containerVisualID) {
		case AreaEditPart.VISUAL_ID:
			if (AreaNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (AreaAreaTopicCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (AreaAreaElementCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case PageEditPart.VISUAL_ID:
			if (PageNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (PagePageTopicCompartment2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (PagePageElementCompartment2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ContentUnitEditPart.VISUAL_ID:
			if (ContentUnitNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ContentUnitContentUnitTopicCompartment2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case OperationUnitEditPart.VISUAL_ID:
			if (OperationUnitNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case Area2EditPart.VISUAL_ID:
			if (AreaName2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (AreaAreaTopicCompartment2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (AreaAreaElementCompartment2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case Page2EditPart.VISUAL_ID:
			if (PageName2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (PagePageTopicCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (PagePageElementCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ContentUnit2EditPart.VISUAL_ID:
			if (ContentUnitName2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ContentUnitContentUnitTopicCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case DocTopicEditPart.VISUAL_ID:
			if (DocTopicNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case OperationUnit2EditPart.VISUAL_ID:
			if (OperationUnitName2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case AreaAreaTopicCompartmentEditPart.VISUAL_ID:
			if (DocTopicEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case AreaAreaElementCompartmentEditPart.VISUAL_ID:
			if (Area2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (Page2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ContentUnit2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (OperationUnit2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case AreaAreaTopicCompartment2EditPart.VISUAL_ID:
			if (DocTopicEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case AreaAreaElementCompartment2EditPart.VISUAL_ID:
			if (Area2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (Page2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ContentUnit2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (OperationUnit2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case PagePageTopicCompartmentEditPart.VISUAL_ID:
			if (DocTopicEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case PagePageElementCompartmentEditPart.VISUAL_ID:
			if (Page2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ContentUnit2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ContentUnitContentUnitTopicCompartmentEditPart.VISUAL_ID:
			if (DocTopicEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case PagePageTopicCompartment2EditPart.VISUAL_ID:
			if (DocTopicEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case PagePageElementCompartment2EditPart.VISUAL_ID:
			if (Page2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ContentUnit2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ContentUnitContentUnitTopicCompartment2EditPart.VISUAL_ID:
			if (DocTopicEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case SiteviewEditPart.VISUAL_ID:
			if (AreaEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (PageEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ContentUnitEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (OperationUnitEditPart.VISUAL_ID == nodeVisualID) {
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
		if (WebmlPackage.eINSTANCE.getokLink().isSuperTypeOf(
				domainElement.eClass())) {
			return OkLinkEditPart.VISUAL_ID;
		}
		if (WebmlPackage.eINSTANCE.getkoLink().isSuperTypeOf(
				domainElement.eClass())) {
			return KoLinkEditPart.VISUAL_ID;
		}
		if (WebmlPackage.eINSTANCE.getnormalLink().isSuperTypeOf(
				domainElement.eClass())) {
			return NormalLinkEditPart.VISUAL_ID;
		}
		if (WebmlPackage.eINSTANCE.gettransportLink().isSuperTypeOf(
				domainElement.eClass())) {
			return TransportLinkEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 * 
	 * @generated
	 */
	private static boolean isDiagram(Siteview element) {
		return true;
	}

}
