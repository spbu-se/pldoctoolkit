package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.DocumentationCore;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.ProductLine;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;

/**
 * @generated
 */
public class DocumentationCoreInfProductsCompartmentCanonicalEditPolicy extends
		CanonicalEditPolicy {

	/**
	 * @generated
	 */
	protected List getSemanticChildrenList() {
//		TransactionalEditingDomain domain = ((DocumentationCoreInfProductsCompartmentEditPart)getHost()).getEditingDomain();
//		ResourceSet resources = domain.getResourceSet();
		//TODO add 
		
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
					nodeVID = DrlModelVisualIDRegistry.getNodeVisualID(viewObject, nextDocCore);
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
