	package org.spbu.pldoctoolkit.graph.diagram.fproduct.part;

import org.eclipse.emf.ecore.EObject;

		/**
 * @generated
 */
public class DrlModelNodeDescriptor {

			/**
 * @generated
 */
private EObject myModelElement;
	
		/**
 * @generated
 */
private int myVisualID;

		/**
 * @generated
 */
private String myType;
	
			/**
 * @generated
 */
public DrlModelNodeDescriptor(EObject modelElement, int visualID) {
	myModelElement = modelElement;
	myVisualID = visualID;
}
	
			/**
 * @generated
 */
public EObject getModelElement() {
	return myModelElement;
}
	
			/**
 * @generated
 */
public int getVisualID() {
	return myVisualID;
}
	
			/**
 * @generated
 */
public String getType() {
	if (myType == null) {
		myType = DrlModelVisualIDRegistry.getType(getVisualID());
	}
	return myType;
}
		
	}
