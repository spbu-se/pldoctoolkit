package org.spbu.pldoctoolkit.graph.diagram.productline.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
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

import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;

import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.PLSchemeProductsCompartmentViewFactory;
import org.spbu.pldoctoolkit.graph.diagram.productline.view.factories.PLSchemeViewFactory;
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
		if (elementType != null
				&& !DrlModelElementTypes.isKnownElementType(elementType)) {
			return null;
		}
		EClass semanticType = getSemanticEClass(semanticAdapter);
		EObject semanticElement = getSemanticElement(semanticAdapter);
		int nodeVID = DrlModelVisualIDRegistry.getNodeVisualID(containerView,
				semanticElement, semanticType, semanticHint);
		switch (nodeVID) {
		case ProductLine2EditPart.VISUAL_ID:
			return ProductLine2ViewFactory.class;
		case ProductLineNameEditPart.VISUAL_ID:
			return ProductLineNameViewFactory.class;
		case ProductLineDataEditPart.VISUAL_ID:
			return ProductLineDataViewFactory.class;
		case PLSchemeEditPart.VISUAL_ID:
			return PLSchemeViewFactory.class;
		case ProductEditPart.VISUAL_ID:
			return ProductViewFactory.class;
		case ProductNameEditPart.VISUAL_ID:
			return ProductNameViewFactory.class;
		case ProductLineProductLineDataCompartmentEditPart.VISUAL_ID:
			return ProductLineProductLineDataCompartmentViewFactory.class;
		case ProductLineDataPLSchemeCompartmentEditPart.VISUAL_ID:
			return ProductLineDataPLSchemeCompartmentViewFactory.class;
		case ProductLineDataDocumentationCoreCompartmentEditPart.VISUAL_ID:
			return ProductLineDataDocumentationCoreCompartmentViewFactory.class;
		case PLSchemeProductsCompartmentEditPart.VISUAL_ID:
			return PLSchemeProductsCompartmentViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Class getEdgeViewClass(IAdaptable semanticAdapter,
			View containerView, String semanticHint) {
		IElementType elementType = getSemanticElementType(semanticAdapter);
		if (elementType != null
				&& !DrlModelElementTypes.isKnownElementType(elementType)) {
			return null;
		}
		EClass semanticType = getSemanticEClass(semanticAdapter);
		if (semanticType == null) {
			return null;
		}
		EObject semanticElement = getSemanticElement(semanticAdapter);
		int linkVID = DrlModelVisualIDRegistry.getLinkWithClassVisualID(
				semanticElement, semanticType);
		switch (linkVID) {
		}
		return getUnrecognizedConnectorViewClass(semanticAdapter,
				containerView, semanticHint);
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

	/**
	 * @generated
	 */
	private Class getUnrecognizedConnectorViewClass(IAdaptable semanticAdapter,
			View containerView, String semanticHint) {
		// Handle unrecognized child node classes here
		return null;
	}

}
