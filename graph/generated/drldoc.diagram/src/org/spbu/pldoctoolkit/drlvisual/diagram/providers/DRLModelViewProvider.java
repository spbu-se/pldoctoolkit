package org.spbu.pldoctoolkit.drlvisual.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElemRef2EditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElemRefEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElemRefGroupEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElemRefGroupNameEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElemRefId2EditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElemRefIdEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElementEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElementNameIdEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfProductNameIdEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.SchemaEditPart;

import org.spbu.pldoctoolkit.drlvisual.diagram.part.DRLModelVisualIDRegistry;

import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfElemRef2ViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfElemRefGroupNameViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfElemRefGroupViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfElemRefId2ViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfElemRefIdViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfElemRefViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfElementNameIdViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfElementViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfProductNameIdViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.InfProductViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.SchemaViewFactory;
import org.spbu.pldoctoolkit.drlvisual.diagram.view.factories.SubelementedElementElementsViewFactory;

/**
 * @generated
 */
public class DRLModelViewProvider extends AbstractViewProvider {

	/**
	 * @generated
	 */
	protected Class getDiagramViewClass(IAdaptable semanticAdapter,
			String diagramKind) {
		EObject semanticElement = getSemanticElement(semanticAdapter);
		if (SchemaEditPart.MODEL_ID.equals(diagramKind)
				&& DRLModelVisualIDRegistry.getDiagramVisualID(semanticElement) != -1) {
			return SchemaViewFactory.class;
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
				&& !DRLModelElementTypes.isKnownElementType(elementType)) {
			return null;
		}
		EClass semanticType = getSemanticEClass(semanticAdapter);
		EObject semanticElement = getSemanticElement(semanticAdapter);
		int nodeVID = DRLModelVisualIDRegistry.getNodeVisualID(containerView,
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
		case InfElemRefId2EditPart.VISUAL_ID:
			return InfElemRefId2ViewFactory.class;
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
				&& !DRLModelElementTypes.isKnownElementType(elementType)) {
			return null;
		}
		if (DRLModelElementTypes.SubelementedElementElements_3003
				.equals(elementType)) {
			return SubelementedElementElementsViewFactory.class;
		}
		EClass semanticType = getSemanticEClass(semanticAdapter);
		if (semanticType == null) {
			return null;
		}
		EObject semanticElement = getSemanticElement(semanticAdapter);
		int linkVID = DRLModelVisualIDRegistry.getLinkWithClassVisualID(
				semanticElement, semanticType);
		switch (linkVID) {
		case InfElemRefEditPart.VISUAL_ID:
			return InfElemRefViewFactory.class;
		case InfElemRef2EditPart.VISUAL_ID:
			return InfElemRef2ViewFactory.class;
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
