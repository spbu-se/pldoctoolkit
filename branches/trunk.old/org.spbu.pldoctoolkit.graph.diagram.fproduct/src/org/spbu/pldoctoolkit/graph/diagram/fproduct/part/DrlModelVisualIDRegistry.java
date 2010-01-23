	package org.spbu.pldoctoolkit.graph.diagram.fproduct.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.FinalInfProduct;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.FinalInfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.InfElemRefEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.InfElementEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.InfElementNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.InfProductNameEditPart;

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
private static final String DEBUG_KEY = DrlModelDiagramEditorPlugin.getInstance().getBundle().getSymbolicName() + "/debug/visualID"; //$NON-NLS-1$
	
			/**
 * @generated
 */
public static int getVisualID(View view) {
	if (view instanceof Diagram) {
		if (FinalInfProductEditPart.MODEL_ID.equals(view.getType())) {
			return FinalInfProductEditPart.VISUAL_ID;
		} else {
			return -1;
		}
	}
	return org.spbu.pldoctoolkit.graph.diagram.fproduct.part.DrlModelVisualIDRegistry.getVisualID(view.getType());
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
		if (Boolean.TRUE.toString().equalsIgnoreCase(Platform.getDebugOption(DEBUG_KEY))) {
			DrlModelDiagramEditorPlugin.getInstance().logError("Unable to parse view type as a visualID number: " + type);
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
	if (DrlPackage.eINSTANCE.getFinalInfProduct().isSuperTypeOf(domainElement.eClass()) && isDiagram((FinalInfProduct) domainElement)) {
	return FinalInfProductEditPart.VISUAL_ID;
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
	String containerModelID = org.spbu.pldoctoolkit.graph.diagram.fproduct.part.DrlModelVisualIDRegistry.getModelID(containerView);
	if (!FinalInfProductEditPart.MODEL_ID.equals(containerModelID)) { 
		return -1;
	}
	int containerVisualID;
	if (FinalInfProductEditPart.MODEL_ID.equals(containerModelID)) {
		containerVisualID = org.spbu.pldoctoolkit.graph.diagram.fproduct.part.DrlModelVisualIDRegistry.getVisualID(containerView);
	} else {
		if (containerView instanceof Diagram) {
			containerVisualID = FinalInfProductEditPart.VISUAL_ID;		
		} else {
			return -1;
		}
	}
	switch (containerVisualID) {
					case FinalInfProductEditPart.VISUAL_ID:
	if (DrlPackage.eINSTANCE.getInfProduct().isSuperTypeOf(domainElement.eClass())) {
	return InfProductEditPart.VISUAL_ID;
}
if (DrlPackage.eINSTANCE.getInfElement().isSuperTypeOf(domainElement.eClass())) {
	return InfElementEditPart.VISUAL_ID;
}
	break;
		}
	return -1;
}
	
			/**
 * @generated
 */
public static boolean canCreateNode(View containerView, int nodeVisualID) {
	String containerModelID = org.spbu.pldoctoolkit.graph.diagram.fproduct.part.DrlModelVisualIDRegistry.getModelID(containerView);
	if (!FinalInfProductEditPart.MODEL_ID.equals(containerModelID)) { 
		return false;
	}
	int containerVisualID;
	if (FinalInfProductEditPart.MODEL_ID.equals(containerModelID)) {
		containerVisualID = org.spbu.pldoctoolkit.graph.diagram.fproduct.part.DrlModelVisualIDRegistry.getVisualID(containerView);
	} else {
		if (containerView instanceof Diagram) {
			containerVisualID = FinalInfProductEditPart.VISUAL_ID;		
		} else {
			return false;
		}
	}
	switch (containerVisualID) {
		case InfProductEditPart.VISUAL_ID:
	if (InfProductNameEditPart.VISUAL_ID == nodeVisualID) {
	return true;
}
	break;
case InfElementEditPart.VISUAL_ID:
	if (InfElementNameEditPart.VISUAL_ID == nodeVisualID) {
	return true;
}
	break;
case FinalInfProductEditPart.VISUAL_ID:
	if (InfProductEditPart.VISUAL_ID == nodeVisualID) {
	return true;
}
if (InfElementEditPart.VISUAL_ID == nodeVisualID) {
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
	if (	DrlPackage.eINSTANCE.getInfElemRef().isSuperTypeOf(domainElement.eClass())) {
	return InfElemRefEditPart.VISUAL_ID;
}
	return -1;
}
	
				/**
 * User can change implementation of this method to handle some specific
 * situations not covered by default logic.
 * 
 * @generated
 */
private static boolean isDiagram(FinalInfProduct element) {
	return true;
}
					
	}
