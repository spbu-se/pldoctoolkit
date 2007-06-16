package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.spbu.pldoctoolkit.graph.DocumentationCore;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.commands.InfProductCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.DocumentationCoreInfProductsCompartmentEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelDiagramEditorPlugin;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;
import org.spbu.pldoctoolkit.graph.diagram.productline.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class DocumentationCoreInfProductsCompartmentItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	/**
	 * @generated NOT
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (DrlModelElementTypes.InfProduct_2005 == req.getElementType()) {
				req.setContainmentFeature(DrlPackage.eINSTANCE
						.getDocumentationCore_Parts());
			
			DocumentationCore container = findDocumentationCore();
			if(container == null) {
//				final String header = "Please create documentation core file"; 
				final String message = "Please first create at least one documentation core file with an informational product!";
//				ErrorDialog.openError(
//						this.getHost().getViewer().getControl().getShell(), 
//						header, message, 
//						new Status(IStatus.ERROR, DrlModelDiagramEditorPlugin.ID, ""));
//				
				DrlModelDiagramEditorPlugin.getInstance().logError(message);
				return UnexecutableCommand.INSTANCE;
			} else {
				//XXX this is a hack - review diagram structure
//				if (req.getContainer() == null) {
//					req.setContainer(container);
//				}
				
				req.setParameter("container", container);
				
				return getMSLWrapper(new InfProductCreateCommand(req));
			}
		}
		
		return super.getCreateCommand(req);
	}

	private DocumentationCore findDocumentationCore() {
		TransactionalEditingDomain domain = ((DocumentationCoreInfProductsCompartmentEditPart)getHost()).getEditingDomain();
		EList<Resource> resourceList = domain.getResourceSet().getResources();
		
		for(Resource resource : resourceList) {
			TreeIterator<EObject> nodeIter = resource.getAllContents();
			
			while(nodeIter.hasNext()) {
				EObject node = nodeIter.next();
				if(DrlPackage.DOCUMENTATION_CORE == node.eClass().getClassifierID()) {
					return (DocumentationCore) node;
				}
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.SemanticEditPolicy#shouldProceed(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest)
	 */
	@Override
	protected boolean shouldProceed(DestroyRequest destroyRequest) {
		return false;
	}

}
