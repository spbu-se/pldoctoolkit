package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.DocumentationCore;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.ProductLine;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.DocumentationCoreInfProductsCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelDiagramEditorPlugin;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;

/**
 * @generated
 */
public class DocumentationCoreInfProductsCompartmentCanonicalEditPolicy extends
		CanonicalEditPolicy {

	/**
	 * @generated NOT
	 */
	protected List getSemanticChildrenList() {
		//HAND
		
//		List<EObject> result = new LinkedList<EObject>();
//		
//		TransactionalEditingDomain domain = ((DocumentationCoreInfProductsCompartmentEditPart)getHost()).getEditingDomain();
//		EList<Resource> resourceList = domain.getResourceSet().getResources();
//		for(Resource resource : resourceList) {
//			DrlModelDiagramEditorPlugin.getInstance().logInfo("resource: " + resource.toString());
//			
//			//XXX
//			for(EObject node : resource.getContents()) {
//				DrlModelDiagramEditorPlugin.getInstance().logInfo("node: " + node.toString());
//				TreeIterator<EObject> nodeContentsIterator = node.eAllContents();
//				while(nodeContentsIterator.hasNext()) {
//					EObject item = nodeContentsIterator.next();
////					DrlModelDiagramEditorPlugin.getInstance().logInfo("next item: " + item);
//				}
//			}
//			
//			TreeIterator<EObject> nodeIter = resource.getAllContents();
//			int nodeVID;
//			View viewObject = (View) getHost().getModel();
//			while(nodeIter.hasNext()) {
//				EObject node = nodeIter.next();
//				if(DrlPackage.INF_PRODUCT == node.eClass().getClassifierID()) {
//					nodeVID = DrlModelVisualIDRegistry.getNodeVisualID(viewObject, node);
//					if(InfProductEditPart.VISUAL_ID == nodeVID) {
//						result.add(node);
//					}
//				}
//			}
//		}
		//TODO add 
		
		DrlModelDiagramEditorPlugin.getInstance().logInfo("refreshing");
		
		List result = new LinkedList();
		EObject modelObject = ((View) getHost().getParent().getModel()).getElement();
		View viewObject = (View) getHost().getModel();
		
		int nodeVID;
		for (Iterator docCores = ((ProductLine) modelObject)
				.getDocumentationCores().iterator(); docCores.hasNext();) {

			DocumentationCore nextDocCore = (DocumentationCore) docCores.next();
			for(Iterator partsIterator = nextDocCore.getParts().iterator();
				partsIterator.hasNext();) {
				
				GenericDocumentPart nextPart = (GenericDocumentPart) partsIterator.next();
				if(DrlPackage.INF_PRODUCT == nextPart.eClass().getClassifierID()) {
					nodeVID = DrlModelVisualIDRegistry.getNodeVisualID(viewObject, nextPart);
					if(InfProductEditPart.VISUAL_ID == nodeVID) {
						result.add(nextPart);
					}
				}
			}
		}
		return result;
	}

	/**
	 * @generated NOT
	 */
	protected boolean shouldDeleteView(View view) {
//		return view.isSetElement() && view.getElement() != null
//				&& view.getElement().eIsProxy();
		return false;
	}

	/**
	 * @generated
	 */
	protected String getDefaultFactoryHint() {
		return null;
	}

}
