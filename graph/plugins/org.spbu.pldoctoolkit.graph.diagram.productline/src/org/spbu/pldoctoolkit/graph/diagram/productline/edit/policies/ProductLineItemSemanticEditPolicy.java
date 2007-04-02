package org.spbu.pldoctoolkit.graph.diagram.productline.edit.policies;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;

import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import org.eclipse.gmf.runtime.notation.View;

import org.spbu.pldoctoolkit.graph.DrlPackage;

import org.spbu.pldoctoolkit.graph.diagram.productline.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class ProductLineItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (DrlModelElementTypes.ProductLine_1001 == req.getElementType()) {
			return getMSLWrapper(new CreateProductLine_1001Command(req));
		}
		return super.getCreateCommand(req);
	}

	/**
	 * @generated
	 */
	private static class CreateProductLine_1001Command extends
			CreateElementCommand {

		/**
		 * @generated
		 */
		public CreateProductLine_1001Command(CreateElementRequest req) {
			super(req);
		}

		/**
		 * @generated
		 */
		protected EClass getEClassToEdit() {
			return DrlPackage.eINSTANCE.getProductLine();
		};

		/**
		 * @generated
		 */
		protected EObject getElementToEdit() {
			EObject container = ((CreateElementRequest) getRequest())
					.getContainer();
			if (container instanceof View) {
				container = ((View) container).getElement();
			}
			return container;
		}

		/**
		 * @generated
		 */
		public boolean canExecute() {
			if (getEClass() != null) {
				return getEClass().isSuperTypeOf(getEClassToEdit());
			}
			return true;
		}

		/**
		 * @generated
		 */
		protected EReference getContainmentFeature() {
			return null;
		}

		/**
		 * @generated
		 */
		protected EObject doDefaultElementCreation() {
			// Uncomment to put "phantom" objects into the diagram file.		
			//Resource resource = ((CreateElementRequest) getRequest()).getContainer().eResource();
			//if (resource == null) {
			//	return null;
			//}
			Resource resource = getElementToEdit().eResource();
			EClass eClass = getElementType().getEClass();
			EObject eObject = eClass.getEPackage().getEFactoryInstance()
					.create(eClass);
			resource.getContents().add(eObject);
			return eObject;
		}
	}

	/**
	 * @generated
	 */
	protected Command getDuplicateCommand(DuplicateElementsRequest req) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
				.getEditingDomain();
		return getMSLWrapper(new DuplicateAnythingCommand(editingDomain, req));
	}

	/**
	 * @generated
	 */
	private static class DuplicateAnythingCommand extends
			DuplicateEObjectsCommand {

		/**
		 * @generated
		 */
		public DuplicateAnythingCommand(
				TransactionalEditingDomain editingDomain,
				DuplicateElementsRequest req) {
			super(editingDomain, req.getLabel(), req
					.getElementsToBeDuplicated(), req
					.getAllDuplicatedElementsMap());
		}
	}
}
