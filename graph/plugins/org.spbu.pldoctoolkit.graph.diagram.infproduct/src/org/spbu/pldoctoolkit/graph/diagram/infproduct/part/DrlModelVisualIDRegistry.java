package org.spbu.pldoctoolkit.graph.diagram.infproduct.part;

import org.eclipse.core.runtime.Platform;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

import org.spbu.pldoctoolkit.graph.DocumentationCore;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElemRefGroup;
import org.spbu.pldoctoolkit.graph.InfElement;
import org.spbu.pldoctoolkit.graph.InfProduct;

import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.DocumentationCoreEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefGroupEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefIdEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElementEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElementNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfProductNameEditPart;

import org.spbu.pldoctoolkit.graph.diagram.infproduct.expressions.DrlModelAbstractExpression;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.expressions.DrlModelOCLFactory;

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
			if (DocumentationCoreEditPart.MODEL_ID.equals(view.getType())) {
				return DocumentationCoreEditPart.VISUAL_ID;
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
		if (DrlPackage.eINSTANCE.getDocumentationCore().isSuperTypeOf(
				domainElementMetaclass)
				&& isDiagramDocumentationCore_79((DocumentationCore) domainElement)) {
			return DocumentationCoreEditPart.VISUAL_ID;
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
		if (!DocumentationCoreEditPart.MODEL_ID.equals(containerModelID)) {
			return -1;
		}
		int containerVisualID;
		if (DocumentationCoreEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = DocumentationCoreEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		int nodeVisualID = semanticHint != null ? getVisualID(semanticHint)
				: -1;
		switch (containerVisualID) {
		case InfElementEditPart.VISUAL_ID:
			if (InfElementNameEditPart.VISUAL_ID == nodeVisualID) {
				return InfElementNameEditPart.VISUAL_ID;
			}
			return getUnrecognizedInfElement_1001ChildNodeID(domainElement,
					semanticHint);
		case InfProductEditPart.VISUAL_ID:
			if (InfProductNameEditPart.VISUAL_ID == nodeVisualID) {
				return InfProductNameEditPart.VISUAL_ID;
			}
			return getUnrecognizedInfProduct_1002ChildNodeID(domainElement,
					semanticHint);
		case InfElemRefGroupEditPart.VISUAL_ID:
			return getUnrecognizedInfElemRefGroup_1003ChildNodeID(
					domainElement, semanticHint);
		case DocumentationCoreEditPart.VISUAL_ID:
			if ((semanticHint == null || InfElementEditPart.VISUAL_ID == nodeVisualID)
					&& DrlPackage.eINSTANCE.getInfElement().isSuperTypeOf(
							domainElementMetaclass)
					&& (domainElement == null || isNodeInfElement_1001((InfElement) domainElement))) {
				return InfElementEditPart.VISUAL_ID;
			}
			if ((semanticHint == null || InfProductEditPart.VISUAL_ID == nodeVisualID)
					&& DrlPackage.eINSTANCE.getInfProduct().isSuperTypeOf(
							domainElementMetaclass)
					&& (domainElement == null || isNodeInfProduct_1002((InfProduct) domainElement))) {
				return InfProductEditPart.VISUAL_ID;
			}
			if ((semanticHint == null || InfElemRefGroupEditPart.VISUAL_ID == nodeVisualID)
					&& DrlPackage.eINSTANCE.getInfElemRefGroup().isSuperTypeOf(
							domainElementMetaclass)
					&& (domainElement == null || isNodeInfElemRefGroup_1003((InfElemRefGroup) domainElement))) {
				return InfElemRefGroupEditPart.VISUAL_ID;
			}
			return getUnrecognizedDocumentationCore_79ChildNodeID(
					domainElement, semanticHint);
		case InfElemRefEditPart.VISUAL_ID:
			if (InfElemRefIdEditPart.VISUAL_ID == nodeVisualID) {
				return InfElemRefIdEditPart.VISUAL_ID;
			}
			return getUnrecognizedInfElemRef_3001LinkLabelID(semanticHint);
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
		if (DrlPackage.eINSTANCE.getInfElemRef().isSuperTypeOf(
				domainElementMetaclass)
				&& (domainElement == null || isLinkWithClassInfElemRef_3001((InfElemRef) domainElement))) {
			return InfElemRefEditPart.VISUAL_ID;
		} else {
			return getUnrecognizedLinkWithClassID(domainElement);
		}
	}

	/**
	 * User can change implementation of this method to check some additional 
	 * conditions here.
	 *
	 * @generated
	 */
	private static boolean isDiagramDocumentationCore_79(
			DocumentationCore element) {
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
	private static boolean isNodeInfElement_1001(InfElement element) {
		return true;
	}

	/**
	 * User can change implementation of this method to check some additional 
	 * conditions here.
	 *
	 * @generated
	 */
	private static boolean isNodeInfProduct_1002(InfProduct element) {
		return true;
	}

	/**
	 * User can change implementation of this method to check some additional 
	 * conditions here.
	 *
	 * @generated
	 */
	private static boolean isNodeInfElemRefGroup_1003(InfElemRefGroup element) {
		return true;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 *
	 * @generated
	 */
	private static int getUnrecognizedInfElement_1001ChildNodeID(
			EObject domainElement, String semanticHint) {
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 *
	 * @generated
	 */
	private static int getUnrecognizedInfProduct_1002ChildNodeID(
			EObject domainElement, String semanticHint) {
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 *
	 * @generated
	 */
	private static int getUnrecognizedInfElemRefGroup_1003ChildNodeID(
			EObject domainElement, String semanticHint) {
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 *
	 * @generated
	 */
	private static int getUnrecognizedDocumentationCore_79ChildNodeID(
			EObject domainElement, String semanticHint) {
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 *
	 * @generated
	 */
	private static int getUnrecognizedInfElemRef_3001LinkLabelID(
			String semanticHint) {
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

	/**
	 * User can change implementation of this method to check some additional 
	 * conditions here.
	 *
	 * @generated
	 */
	private static boolean isLinkWithClassInfElemRef_3001(InfElemRef element) {
		return InfElemRef_3001.matches(element);
	}

	/**
	 * @generated
	 */
	private static final Matcher InfElemRef_3001 = new Matcher(
			DrlModelOCLFactory.getExpression("self.optional = true", //$NON-NLS-1$
					DrlPackage.eINSTANCE.getInfElemRef()));

	/**
	 * @generated	
	 */
	static class Matcher {

		/**
		 * @generated	
		 */
		private DrlModelAbstractExpression condition;

		/**
		 * @generated	
		 */
		Matcher(DrlModelAbstractExpression conditionExpression) {
			this.condition = conditionExpression;
		}

		/**
		 * @generated	
		 */
		boolean matches(EObject object) {
			Object result = condition.evaluate(object);
			return result instanceof Boolean
					&& ((Boolean) result).booleanValue();
		}
	}// Matcher
}
