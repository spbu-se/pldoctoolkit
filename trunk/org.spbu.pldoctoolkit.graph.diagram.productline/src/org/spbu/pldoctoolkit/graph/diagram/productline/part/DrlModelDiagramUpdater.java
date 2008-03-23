package org.spbu.pldoctoolkit.graph.diagram.productline.part;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.Product;
import org.spbu.pldoctoolkit.graph.ProductLine;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLine2EditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineProductLineDataCompartmentEditPart;

/**
 * @generated
 */
public class DrlModelDiagramUpdater {

	/**
	 * @generated
	 */
	public static List getSemanticChildren(View view) {
		switch (DrlModelVisualIDRegistry.getVisualID(view)) {
		case ProductLineProductLineDataCompartmentEditPart.VISUAL_ID:
			return getProductLineProductLineDataCompartment_5001SemanticChildren(view);
		case ProductLineEditPart.VISUAL_ID:
			return getProductLine_79SemanticChildren(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getProductLineProductLineDataCompartment_5001SemanticChildren(
			View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getProductLine_79SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		ProductLine modelElement = (ProductLine) view.getElement();
		List result = new LinkedList();
		Resource resource = modelElement.eResource();
		for (Iterator semanticIterator = getPhantomNodesIterator(resource); semanticIterator
				.hasNext();) {
			EObject childElement = (EObject) semanticIterator.next();
			if (childElement == modelElement) {
				continue;
			}
			if (DrlModelVisualIDRegistry.getNodeVisualID(view, childElement) == ProductLine2EditPart.VISUAL_ID) {
				result.add(new DrlModelNodeDescriptor(childElement,
						ProductLine2EditPart.VISUAL_ID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Iterator getPhantomNodesIterator(Resource resource) {
		return resource.getAllContents();
	}

	/**
	 * @generated
	 */
	public static List getContainedLinks(View view) {
		switch (DrlModelVisualIDRegistry.getVisualID(view)) {
		case ProductLineEditPart.VISUAL_ID:
			return getProductLine_79ContainedLinks(view);
		case ProductLine2EditPart.VISUAL_ID:
			return getProductLine_1001ContainedLinks(view);
		case ProductEditPart.VISUAL_ID:
			return getProduct_2003ContainedLinks(view);
		case InfProductEditPart.VISUAL_ID:
			return getInfProduct_2005ContainedLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getIncomingLinks(View view) {
		switch (DrlModelVisualIDRegistry.getVisualID(view)) {
		case ProductLine2EditPart.VISUAL_ID:
			return getProductLine_1001IncomingLinks(view);
		case ProductEditPart.VISUAL_ID:
			return getProduct_2003IncomingLinks(view);
		case InfProductEditPart.VISUAL_ID:
			return getInfProduct_2005IncomingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOutgoingLinks(View view) {
		switch (DrlModelVisualIDRegistry.getVisualID(view)) {
		case ProductLine2EditPart.VISUAL_ID:
			return getProductLine_1001OutgoingLinks(view);
		case ProductEditPart.VISUAL_ID:
			return getProduct_2003OutgoingLinks(view);
		case InfProductEditPart.VISUAL_ID:
			return getInfProduct_2005OutgoingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getProductLine_79ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getProductLine_1001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getProduct_2003ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getInfProduct_2005ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getProductLine_1001IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getProduct_2003IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getInfProduct_2005IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getProductLine_1001OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getProduct_2003OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getInfProduct_2005OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

}
