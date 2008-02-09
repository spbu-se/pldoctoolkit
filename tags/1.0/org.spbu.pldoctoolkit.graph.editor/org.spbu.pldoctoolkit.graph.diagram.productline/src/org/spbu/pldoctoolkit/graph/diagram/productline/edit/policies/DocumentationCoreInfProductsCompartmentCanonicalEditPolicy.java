package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.DocumentationCoreInfProductsCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelDiagramEditorPlugin;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;
import org.spbu.pldoctoolkit.registry.RegisteredLocation;

/**
 * @generated
 */
public class DocumentationCoreInfProductsCompartmentCanonicalEditPolicy extends
		CanonicalEditPolicy {

	/**
	 * First all the resources containing inf products are loaded into the resource set.
	 * Then InfProducts are searched in the resources from the resource set. 
	 * 
	 * @generated NOT
	 * 
	 * HAND
	 */
	protected List getSemanticChildrenList() {
		TransactionalEditingDomain domain = ((DocumentationCoreInfProductsCompartmentEditPart)getHost()).getEditingDomain();
		
		loadResources(domain.getResourceSet());
		
		EList<Resource> resourceList = domain.getResourceSet().getResources();

		List<EObject> result = new LinkedList<EObject>();

		for(Resource resource : resourceList) {
			DrlModelDiagramEditorPlugin.getInstance().logInfo("resource: " + resource.toString());
			
			TreeIterator<EObject> nodeIter = resource.getAllContents();
			int nodeVID;
			View viewObject = (View) getHost().getModel();
			while(nodeIter.hasNext()) {
				EObject node = nodeIter.next();
				if(DrlPackage.INF_PRODUCT == node.eClass().getClassifierID()) {
					nodeVID = DrlModelVisualIDRegistry.getNodeVisualID(viewObject, node);
					if(InfProductEditPart.VISUAL_ID == nodeVID) {
						result.add(node);
					}
				}
			}
		}
		
		return result;
	}

	/**
	 * Checks Registry and loads into resource set all resources which contain InfProducts.
	 * @param resourceSet 
	 */
	private void loadResources(ResourceSet resourceSet) {
		//TODO how to determine project name from URI?
		URI diagramURI = ((View) getHost().getModel()).eResource().getURI();
		String projectName = diagramURI.segment(1);

		List<RegisteredLocation> infProducts = 
				PLDocToolkitPlugin.getRegistry(projectName)
				.findForType(RegisteredLocation.INF_PRODUCT);
		
		for(RegisteredLocation infProd : infProducts) {
			String pathname = infProd.getFile().getFullPath().toFile().toString();
			resourceSet.getResource(URI.createPlatformResourceURI(pathname, true), true);
		}
	}
	
	/**
	 * @generated NOT
	 */
	protected boolean shouldDeleteView(View view) {
		return true;
	}

	/**
	 * @generated
	 */
	protected String getDefaultFactoryHint() {
		return null;
	}

}
