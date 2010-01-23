package org.spbu.pldoctoolkit.graph.diagram.productline.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.DocumentationCoreEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.DocumentationCoreInfProductsCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.LineSeparatorEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.PLSchemeEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.PLSchemeProductsCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductInfProductLinkEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLine2EditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineDataDocumentationCoreCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineDataEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineDataPLSchemeCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineProductLineDataCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.DocumentationCoreInfProductsCompartmentViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.DocumentationCoreViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.InfProductNameViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.InfProductViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.LineSeparatorViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.PLSchemeProductsCompartmentViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.PLSchemeViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.ProductInfProductLinkViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.ProductLine2ViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.ProductLineDataDocumentationCoreCompartmentViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.ProductLineDataPLSchemeCompartmentViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.ProductLineDataViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.ProductLineNameViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.ProductLineProductLineDataCompartmentViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.ProductLineViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.ProductNameViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.ProductViewFactory;

/**
 * @generated
 */
public class DrlModelViewProvider extends AbstractViewProvider {

	/**
	 * @generated
	 */
	protected Class getDiagramViewClass(IAdaptable semanticAdapter,
			String diagramKind) {
		EObject semanticElement = getSemanticElement(semanticAdapter);
		if (ProductLineEditPart.MODEL_ID.equals(diagramKind)
				&& DrlModelVisualIDRegistry.getDiagramVisualID(semanticElement) != -1) {
			return ProductLineViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Class getNodeViewClass(IAdaptable semanticAdapter,
			View containerView, String semanticHint) {
		if (containerView == null) {
			return null;
		}
		IElementType elementType = getSemanticElementType(semanticAdapter);
		EObject domainElement = getSemanticElement(semanticAdapter);
		int visualID;
		if (semanticHint == null) {
			// Semantic hint is not specified. Can be a result of call from CanonicalEditPolicy.
			// In this situation there should be NO elementType, visualID will be determined
			// by VisualIDRegistry.getNodeVisualID() for domainElement.
			if (elementType != null || domainElement == null) {
				return null;
			}
			visualID = DrlModelVisualIDRegistry.getNodeVisualID(containerView,
					domainElement);
		} else {
			visualID = DrlModelVisualIDRegistry.getVisualID(semanticHint);
			if (elementType != null) {
				// Semantic hint is specified together with element type.
				// Both parameters should describe exactly the same diagram element.
				// In addition we check that visualID returned by VisualIDRegistry.getNodeVisualID() for
				// domainElement (if specified) is the same as in element type.
				if (!DrlModelElementTypes.isKnownElementType(elementType)
						|| (!(elementType instanceof IHintedType))) {
					return null; // foreign element type
				}
				String elementTypeHint = ((IHintedType) elementType)
						.getSemanticHint();
				if (!semanticHint.equals(elementTypeHint)) {
					return null; // if semantic hint is specified it should be the same as in element type
				}
				if (domainElement != null
						&& visualID != DrlModelVisualIDRegistry
								.getNodeVisualID(containerView, domainElement)) {
					return null; // visual id for node EClass should match visual id from element type
				}
			} else {
				// Element type is not specified. Domain element should be present (except pure design elements).
				// This method is called with EObjectAdapter as parameter from:
				//   - ViewService.createNode(View container, EObject eObject, String type, PreferencesHint preferencesHint) 
				//   - generated ViewFactory.decorateView() for parent element
				if (!ProductLineEditPart.MODEL_ID
						.equals(DrlModelVisualIDRegistry
								.getModelID(containerView))) {
					return null; // foreign diagram
				}
				switch (visualID) {
				case ProductLineDataEditPart.VISUAL_ID:
				case PLSchemeEditPart.VISUAL_ID:
				case DocumentationCoreEditPart.VISUAL_ID:
				// HAND
				case LineSeparatorEditPart.VISUAL_ID:
					break; // pure design element
				case ProductEditPart.VISUAL_ID:
				case InfProductEditPart.VISUAL_ID:
				case ProductLine2EditPart.VISUAL_ID:
					if (domainElement == null
							|| visualID != DrlModelVisualIDRegistry
									.getNodeVisualID(containerView,
											domainElement)) {
						return null; // visual id in semantic hint should match visual id for domain element
					}
					break;
				case ProductLineNameEditPart.VISUAL_ID:
				case ProductLineProductLineDataCompartmentEditPart.VISUAL_ID:
					if (ProductLine2EditPart.VISUAL_ID != DrlModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case ProductLineDataPLSchemeCompartmentEditPart.VISUAL_ID:
				case ProductLineDataDocumentationCoreCompartmentEditPart.VISUAL_ID:
					if (ProductLineDataEditPart.VISUAL_ID != DrlModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case PLSchemeProductsCompartmentEditPart.VISUAL_ID:
					if (PLSchemeEditPart.VISUAL_ID != DrlModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case ProductNameEditPart.VISUAL_ID:
					if (ProductEditPart.VISUAL_ID != DrlModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case DocumentationCoreInfProductsCompartmentEditPart.VISUAL_ID:
					if (DocumentationCoreEditPart.VISUAL_ID != DrlModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case InfProductNameEditPart.VISUAL_ID:
					if (InfProductEditPart.VISUAL_ID != DrlModelVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				default:
					return null;
				}
			}
		}
		return getNodeViewClass(containerView, visualID);
	}

	/**
	 * @generated
	 */
	protected Class getNodeViewClass(View containerView, int visualID) {
		if (containerView == null
				|| !DrlModelVisualIDRegistry.canCreateNode(containerView,
						visualID)) {
			return null;
		}
		switch (visualID) {
		case ProductLine2EditPart.VISUAL_ID:
			return ProductLine2ViewFactory.class;
		case ProductLineNameEditPart.VISUAL_ID:
			return ProductLineNameViewFactory.class;
		case ProductLineDataEditPart.VISUAL_ID:
			return ProductLineDataViewFactory.class;
		// HAND
		case LineSeparatorEditPart.VISUAL_ID:
			return LineSeparatorViewFactory.class;
		case PLSchemeEditPart.VISUAL_ID:
			return PLSchemeViewFactory.class;
		case ProductEditPart.VISUAL_ID:
			return ProductViewFactory.class;
		case ProductNameEditPart.VISUAL_ID:
			return ProductNameViewFactory.class;
		case DocumentationCoreEditPart.VISUAL_ID:
			return DocumentationCoreViewFactory.class;
		case InfProductEditPart.VISUAL_ID:
			return InfProductViewFactory.class;
		case InfProductNameEditPart.VISUAL_ID:
			return InfProductNameViewFactory.class;
		case ProductLineProductLineDataCompartmentEditPart.VISUAL_ID:
			return ProductLineProductLineDataCompartmentViewFactory.class;
		case ProductLineDataPLSchemeCompartmentEditPart.VISUAL_ID:
			return ProductLineDataPLSchemeCompartmentViewFactory.class;
		case ProductLineDataDocumentationCoreCompartmentEditPart.VISUAL_ID:
			return ProductLineDataDocumentationCoreCompartmentViewFactory.class;
		case PLSchemeProductsCompartmentEditPart.VISUAL_ID:
			return PLSchemeProductsCompartmentViewFactory.class;
		case DocumentationCoreInfProductsCompartmentEditPart.VISUAL_ID:
			return DocumentationCoreInfProductsCompartmentViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Class getEdgeViewClass(IAdaptable semanticAdapter,
			View containerView, String semanticHint) {
		IElementType elementType = getSemanticElementType(semanticAdapter);
		if (!DrlModelElementTypes.isKnownElementType(elementType)
				|| (!(elementType instanceof IHintedType))) {
			return null; // foreign element type
		}
		String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
		if (elementTypeHint == null) {
			return null; // our hint is visual id and must be specified
		}
		if (semanticHint != null && !semanticHint.equals(elementTypeHint)) {
			return null; // if semantic hint is specified it should be the same as in element type
		}
		int visualID = DrlModelVisualIDRegistry.getVisualID(elementTypeHint);
		EObject domainElement = getSemanticElement(semanticAdapter);
		if (domainElement != null
				&& visualID != DrlModelVisualIDRegistry
						.getLinkWithClassVisualID(domainElement)) {
			return null; // visual id for link EClass should match visual id from element type
		}
		return getEdgeViewClass(visualID);
	}

	/**
	 * @generated
	 */
	protected Class getEdgeViewClass(int visualID) {
		switch (visualID) {
		case ProductInfProductLinkEditPart.VISUAL_ID:
			return ProductInfProductLinkViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	private IElementType getSemanticElementType(IAdaptable semanticAdapter) {
		if (semanticAdapter == null) {
			return null;
		}
		return (IElementType) semanticAdapter.getAdapter(IElementType.class);
	}

}
