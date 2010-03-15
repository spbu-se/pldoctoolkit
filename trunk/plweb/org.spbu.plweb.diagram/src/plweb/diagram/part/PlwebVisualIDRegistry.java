package plweb.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

import plweb.DiagramRoot;
import plweb.PlwebPackage;
import plweb.diagram.edit.parts.AreaEditPart;
import plweb.diagram.edit.parts.AreaTitleEditPart;
import plweb.diagram.edit.parts.ClassEditPart;
import plweb.diagram.edit.parts.ClassTitleEditPart;
import plweb.diagram.edit.parts.DiagramRootEditPart;
import plweb.diagram.edit.parts.GroupEditPart;
import plweb.diagram.edit.parts.PageEditPart;
import plweb.diagram.edit.parts.PageTitleEditPart;

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
		return plweb.diagram.part.PlwebVisualIDRegistry.getVisualID(view
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
		return String.valueOf(visualID);
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
		String containerModelID = plweb.diagram.part.PlwebVisualIDRegistry
				.getModelID(containerView);
		if (!DiagramRootEditPart.MODEL_ID.equals(containerModelID)) {
			return -1;
		}
		int containerVisualID;
		if (DiagramRootEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = plweb.diagram.part.PlwebVisualIDRegistry
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
			if (PlwebPackage.eINSTANCE.getArea().isSuperTypeOf(
					domainElement.eClass())) {
				return AreaEditPart.VISUAL_ID;
			}
			if (PlwebPackage.eINSTANCE.getGroup().isSuperTypeOf(
					domainElement.eClass())) {
				return GroupEditPart.VISUAL_ID;
			}
			if (PlwebPackage.eINSTANCE.getPage().isSuperTypeOf(
					domainElement.eClass())) {
				return PageEditPart.VISUAL_ID;
			}
			if (PlwebPackage.eINSTANCE.getClass_().isSuperTypeOf(
					domainElement.eClass())) {
				return ClassEditPart.VISUAL_ID;
			}
			break;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static boolean canCreateNode(View containerView, int nodeVisualID) {
		String containerModelID = plweb.diagram.part.PlwebVisualIDRegistry
				.getModelID(containerView);
		if (!DiagramRootEditPart.MODEL_ID.equals(containerModelID)) {
			return false;
		}
		int containerVisualID;
		if (DiagramRootEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = plweb.diagram.part.PlwebVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = DiagramRootEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		switch (containerVisualID) {
		case AreaEditPart.VISUAL_ID:
			if (AreaTitleEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case PageEditPart.VISUAL_ID:
			if (PageTitleEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ClassEditPart.VISUAL_ID:
			if (ClassTitleEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case DiagramRootEditPart.VISUAL_ID:
			if (AreaEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (GroupEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (PageEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ClassEditPart.VISUAL_ID == nodeVisualID) {
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
