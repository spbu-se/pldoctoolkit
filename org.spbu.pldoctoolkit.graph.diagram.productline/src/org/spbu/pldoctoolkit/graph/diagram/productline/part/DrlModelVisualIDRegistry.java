package org.spbu.pldoctoolkit.graph.diagram.productline.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.ProductLine;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.DocumentationCoreEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.DocumentationCoreInfProductsCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.LineSeparatorEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.PLSchemeEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.PLSchemeProductsCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLine2EditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineDataDocumentationCoreCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineDataEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineDataPLSchemeCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineProductLineDataCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductNameEditPart;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented
 * by a domain model object.
 * 
 * @generated
 */
public class DrlModelVisualIDRegistry {

	/**
	 * @generated
	 */
	private static final String DEBUG_KEY = DrlModelDiagramEditorPlugin
			.getInstance().getBundle().getSymbolicName()
			+ "/debug/visualID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static int getVisualID(View view) {
		if (view instanceof Diagram) {
			if (ProductLineEditPart.MODEL_ID.equals(view.getType())) {
				return ProductLineEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		return org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry
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
				DrlModelDiagramEditorPlugin.getInstance().logError(
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
		if (DrlPackage.eINSTANCE.getProductLine().isSuperTypeOf(
				domainElement.eClass())
				&& isDiagram((ProductLine) domainElement)) {
			return ProductLineEditPart.VISUAL_ID;
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
		String containerModelID = org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry
				.getModelID(containerView);
		if (!ProductLineEditPart.MODEL_ID.equals(containerModelID)) {
			return -1;
		}
		int containerVisualID;
		if (ProductLineEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = ProductLineEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		switch (containerVisualID) {
		case PLSchemeProductsCompartmentEditPart.VISUAL_ID:
			if (DrlPackage.eINSTANCE.getProduct().isSuperTypeOf(
					domainElement.eClass())) {
				return ProductEditPart.VISUAL_ID;
			}
			break;
		case DocumentationCoreInfProductsCompartmentEditPart.VISUAL_ID:
			if (DrlPackage.eINSTANCE.getInfProduct().isSuperTypeOf(
					domainElement.eClass())) {
				return InfProductEditPart.VISUAL_ID;
			}
			break;
		case ProductLineEditPart.VISUAL_ID:
			if (DrlPackage.eINSTANCE.getProductLine().isSuperTypeOf(
					domainElement.eClass())) {
				return ProductLine2EditPart.VISUAL_ID;
			}
			break;
		}
		return -1;
	}

	/**
	 * @generated NOT
	 */
	public static boolean canCreateNode(View containerView, int nodeVisualID) {
		String containerModelID = org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry
				.getModelID(containerView);
		if (!ProductLineEditPart.MODEL_ID.equals(containerModelID)) {
			return false;
		}
		int containerVisualID;
		if (ProductLineEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = ProductLineEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		switch (containerVisualID) {
		case ProductLine2EditPart.VISUAL_ID:
			if (ProductLineNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ProductLineProductLineDataCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ProductLineDataEditPart.VISUAL_ID:
			if (ProductLineDataPLSchemeCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (ProductLineDataDocumentationCoreCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			//HAND
			if (LineSeparatorEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case PLSchemeEditPart.VISUAL_ID:
			if (PLSchemeProductsCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ProductEditPart.VISUAL_ID:
			if (ProductNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case DocumentationCoreEditPart.VISUAL_ID:
			if (DocumentationCoreInfProductsCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case InfProductEditPart.VISUAL_ID:
			if (InfProductNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ProductLineProductLineDataCompartmentEditPart.VISUAL_ID:
			if (ProductLineDataEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ProductLineDataPLSchemeCompartmentEditPart.VISUAL_ID:
			if (PLSchemeEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ProductLineDataDocumentationCoreCompartmentEditPart.VISUAL_ID:
			if (DocumentationCoreEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case PLSchemeProductsCompartmentEditPart.VISUAL_ID:
			if (ProductEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case DocumentationCoreInfProductsCompartmentEditPart.VISUAL_ID:
			if (InfProductEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case ProductLineEditPart.VISUAL_ID:
			if (ProductLine2EditPart.VISUAL_ID == nodeVisualID) {
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
	private static boolean isDiagram(ProductLine element) {
		return true;
	}

}
