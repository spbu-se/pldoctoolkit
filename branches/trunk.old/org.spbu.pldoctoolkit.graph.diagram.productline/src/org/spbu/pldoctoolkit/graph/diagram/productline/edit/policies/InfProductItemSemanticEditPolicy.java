package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.swt.widgets.Shell;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.InfProduct;
import org.spbu.pldoctoolkit.graph.Product;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.commands.ProductInfProductLinkCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.productline.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class InfProductItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	/**
	 * @generated NOT
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		//		CompoundCommand cc = getDestroyEdgesCommand();
		//		addDestroyShortcutsCommand(cc);
		//		cc.add(getGEFWrapper(new DestroyElementCommand(req)));
		//		return cc.unwrap();

		return UnexecutableCommand.INSTANCE;
	}

	@Override
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		
		if (DrlModelElementTypes.ProductDocumentationFinalInfProducts_3001 == req.getElementType()) {
			return req.getTarget() == null ? null
					: getCreateCompleteIncomingProductDocumentationFinalInfProducts_3001Command(req);
		}

		return super.getCreateRelationshipCommand(req);
	}
	
	protected Command getCreateCompleteIncomingProductDocumentationFinalInfProducts_3001Command(
			CreateRelationshipRequest req) {
		EObject sourceEObject = req.getSource();
		EObject targetEObject = req.getTarget();
		if (false == sourceEObject instanceof Product
				|| false == targetEObject instanceof InfProduct) {
			return UnexecutableCommand.INSTANCE;
		}
		Product source = (Product) sourceEObject;
		InfProduct target = (InfProduct) targetEObject;
		if (!DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateProductDocumentationFinalInfProducts_3001(source, target)) {
			return UnexecutableCommand.INSTANCE;
		}
		if (req.getContainmentFeature() == null) {
			req.setContainmentFeature(DrlPackage.eINSTANCE
					.getGenericDocumentPart_InfElemRefs());
		}
		
		//XXX
		Shell shell = this.getHost().getViewer().getControl().getShell();
		
		return getMSLWrapper(
				new ProductInfProductLinkCreateCommand(shell, req, sourceEObject, targetEObject));
	}
}
