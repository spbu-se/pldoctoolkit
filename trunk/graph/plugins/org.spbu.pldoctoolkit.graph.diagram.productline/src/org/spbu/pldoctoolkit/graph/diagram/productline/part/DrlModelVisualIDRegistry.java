package org.spbu.pldoctoolkit.graph.diagram.productline.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.PLScheme;
import org.spbu.pldoctoolkit.graph.Product;
import org.spbu.pldoctoolkit.graph.ProductLine;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.PLSchemeEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.PLSchemeProductsCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLine2EditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineProductsEditPart;
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
		return getVisualID(view.getType());
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
		EClass domainElementMetaclass = domainElement.eClass();
		return getDiagramVisualID(domainElement, domainElementMetaclass);
	}

	/**
	 * @generated
	 */
	private static int getDiagramVisualID(EObject domainElement,
			EClass domainElementMetaclass) {
		if (DrlPackage.eINSTANCE.getProductLine().isSuperTypeOf(
				domainElementMetaclass)
				&& isDiagramProductLine_79((ProductLine) domainElement)) {
			return ProductLineEditPart.VISUAL_ID;
		}
		return getUnrecognizedDiagramID(domainElement);
	}

	/**
	 * @generated
	 */
	public static int getNodeVisualID(View containerView, EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		EClass domainElementMetaclass = domainElement.eClass();
		return getNodeVisualID(containerView, domainElement,
				domainElementMetaclass, null);
	}

	/**
	 * @generated
	 */
	public static int getNodeVisualID(View containerView,
			EObject domainElement, EClass domainElementMetaclass,
			String semanticHint) {
		String containerModelID = getModelID(containerView);
		if (!ProductLineEditPart.MODEL_ID.equals(containerModelID)) {
			return -1;
		}
		int containerVisualID;
		if (ProductLineEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = ProductLineEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		int nodeVisualID = semanticHint != null ? getVisualID(semanticHint)
				: -1;
		switch (containerVisualID) {
		case ProductLine2EditPart.VISUAL_ID:
			if (ProductLineNameEditPart.VISUAL_ID == nodeVisualID) {
				return ProductLineNameEditPart.VISUAL_ID;
			}
			if (ProductLineProductsEditPart.VISUAL_ID == nodeVisualID) {
				return ProductLineProductsEditPart.VISUAL_ID;
			}
			return getUnrecognizedProductLine_1001ChildNodeID(domainElement,
					semanticHint);
		case PLSchemeEditPart.VISUAL_ID:
			if (PLSchemeProductsCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return PLSchemeProductsCompartmentEditPart.VISUAL_ID;
			}
			return getUnrecognizedPLScheme_2001ChildNodeID(domainElement,
					semanticHint);
		case ProductEditPart.VISUAL_ID:
			if (ProductNameEditPart.VISUAL_ID == nodeVisualID) {
				return ProductNameEditPart.VISUAL_ID;
			}
			return getUnrecognizedProduct_2002ChildNodeID(domainElement,
					semanticHint);
		case ProductLineProductsEditPart.VISUAL_ID:
			if ((semanticHint == null || PLSchemeEditPart.VISUAL_ID == nodeVisualID)
					&& DrlPackage.eINSTANCE.getPLScheme().isSuperTypeOf(
							domainElementMetaclass)
					&& (domainElement == null || isNodePLScheme_2001((PLScheme) domainElement))) {
				return PLSchemeEditPart.VISUAL_ID;
			}
			return getUnrecognizedProductLineProducts_5001ChildNodeID(
					domainElement, semanticHint);
		case PLSchemeProductsCompartmentEditPart.VISUAL_ID:
			if ((semanticHint == null || ProductEditPart.VISUAL_ID == nodeVisualID)
					&& DrlPackage.eINSTANCE.getProduct().isSuperTypeOf(
							domainElementMetaclass)
					&& (domainElement == null || isNodeProduct_2002((Product) domainElement))) {
				return ProductEditPart.VISUAL_ID;
			}
			return getUnrecognizedPLSchemeProductsCompartment_5002ChildNodeID(
					domainElement, semanticHint);
		case ProductLineEditPart.VISUAL_ID:
			if ((semanticHint == null || ProductLine2EditPart.VISUAL_ID == nodeVisualID)
					&& DrlPackage.eINSTANCE.getProductLine().isSuperTypeOf(
							domainElementMetaclass)
					&& (domainElement == null || isNodeProductLine_1001((ProductLine) domainElement))) {
				return ProductLine2EditPart.VISUAL_ID;
			}
			return getUnrecognizedProductLine_79ChildNodeID(domainElement,
					semanticHint);
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static int getLinkWithClassVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		EClass domainElementMetaclass = domainElement.eClass();
		return getLinkWithClassVisualID(domainElement, domainElementMetaclass);
	}

	/**
	 * @generated
	 */
	public static int getLinkWithClassVisualID(EObject domainElement,
			EClass domainElementMetaclass) {
		{
			return getUnrecognizedLinkWithClassID(domainElement);
		}
	}

	/**
	 * User can change implementation of this method to check some additional
	 * conditions here.
	 *
	 * @generated
	 */
	private static boolean isDiagramProductLine_79(ProductLine element) {
		return true;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 *
	 * @generated
	 */
	private static int getUnrecognizedDiagramID(EObject domainElement) {
		return -1;
	}

	/**
	 * User can change implementation of this method to check some additional
	 * conditions here.
	 *
	 * @generated
	 */
	private static boolean isNodeProductLine_1001(ProductLine element) {
		return true;
	}

	/**
	 * User can change implementation of this method to check some additional
	 * conditions here.
	 *
	 * @generated
	 */
	private static boolean isNodePLScheme_2001(PLScheme element) {
		return true;
	}

	/**
	 * User can change implementation of this method to check some additional
	 * conditions here.
	 *
	 * @generated
	 */
	private static boolean isNodeProduct_2002(Product element) {
		return true;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 *
	 * @generated
	 */
	private static int getUnrecognizedProductLine_1001ChildNodeID(
			EObject domainElement, String semanticHint) {
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 *
	 * @generated
	 */
	private static int getUnrecognizedPLScheme_2001ChildNodeID(
			EObject domainElement, String semanticHint) {
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 *
	 * @generated
	 */
	private static int getUnrecognizedProduct_2002ChildNodeID(
			EObject domainElement, String semanticHint) {
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 *
	 * @generated
	 */
	private static int getUnrecognizedProductLineProducts_5001ChildNodeID(
			EObject domainElement, String semanticHint) {
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 *
	 * @generated
	 */
	private static int getUnrecognizedPLSchemeProductsCompartment_5002ChildNodeID(
			EObject domainElement, String semanticHint) {
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 *
	 * @generated
	 */
	private static int getUnrecognizedProductLine_79ChildNodeID(
			EObject domainElement, String semanticHint) {
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 *
	 * @generated
	 */
	private static int getUnrecognizedLinkWithClassID(EObject domainElement) {
		return -1;
	}
}
