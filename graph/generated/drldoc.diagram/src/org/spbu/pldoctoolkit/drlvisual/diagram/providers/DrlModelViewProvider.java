package org.spbu.pldoctoolkit.drlvisual.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.DocumentationCoreEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElemRefEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElemRefGroupEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElemRefGroupNameEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElemRefIdEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElementEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElementNameIdEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfProductNameIdEditPart;

import org.spbu.pldoctoolkit.drlvisual.diagram.part.DrlModelVisualIDRegistry;

import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.DocumentationCoreViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfElemRefGroupNameViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfElemRefGroupViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfElemRefIdViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfElemRefViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfElementNameIdViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfElementViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfProductNameIdViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfProductViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.SubelementedElementElementsViewFactory;

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
		if (DocumentationCoreEditPart.MODEL_ID.equals(diagramKind)
				&& DrlModelVisualIDRegistry.getDiagramVisualID(semanticElement) != -1) {
			return DocumentationCoreViewFactory.class;
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
		case InfElementEditPart.VISUAL_ID:
			return InfElementViewFactory.class;
		case InfElementNameIdEditPart.VISUAL_ID:
			return InfElementNameIdViewFactory.class;
		case InfProductEditPart.VISUAL_ID:
			return InfProductViewFactory.class;
		case InfProductNameIdEditPart.VISUAL_ID:
			return InfProductNameIdViewFactory.class;
		case InfElemRefGroupEditPart.VISUAL_ID:
			return InfElemRefGroupViewFactory.class;
		case InfElemRefGroupNameEditPart.VISUAL_ID:
			return InfElemRefGroupNameViewFactory.class;
		case InfElemRefIdEditPart.VISUAL_ID:
			return InfElemRefIdViewFactory.class;
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
		if (DrlModelElementTypes.SubelementedElementElements_3002
				.equals(elementType)) {
			return SubelementedElementElementsViewFactory.class;
		}
		EClass semanticType = getSemanticEClass(semanticAdapter);
		if (semanticType == null) {
			return null;
		}
		EObject semanticElement = getSemanticElement(semanticAdapter);
		int linkVID = DrlModelVisualIDRegistry.getLinkWithClassVisualID(
				semanticElement, semanticType);
		switch (linkVID) {
		case InfElemRefEditPart.VISUAL_ID:
			return InfElemRefViewFactory.class;
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
