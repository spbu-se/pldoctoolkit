package org.spbu.pldoctoolkit.drlvisual.diagram.edit.policies;

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

import org.spbu.pldoctoolkit.drlvisual.diagram.providers.DRLModelElementTypes;

import org.spbu.pldoctoolkit.drlvisual.drlPackage;

/**
 * @generated
 */
public class SchemaItemSemanticEditPolicy extends
		DRLModelBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (DRLModelElementTypes.InfElement_1001 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(drlPackage.eINSTANCE
						.getSchema_Parts());
			}
			return getMSLWrapper(new CreateInfElement_1001Command(req));
		}
		if (DRLModelElementTypes.InfProduct_1002 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(drlPackage.eINSTANCE
						.getSchema_Parts());
			}
			return getMSLWrapper(new CreateInfProduct_1002Command(req));
		}
		if (DRLModelElementTypes.InfElemRefGroup_1003 == req.getElementType()) {
			return getMSLWrapper(new CreateInfElemRefGroup_1003Command(req));
		}
		return super.getCreateCommand(req);
	}

	/**
	 * @generated
	 */
	private static class CreateInfElement_1001Command extends
			CreateElementCommand {

		/**
		 * @generated
		 */
		public CreateInfElement_1001Command(CreateElementRequest req) {
			super(req);
		}

		/**
		 * @generated
		 */
		protected EClass getEClassToEdit() {
			return drlPackage.eINSTANCE.getSchema();
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
	}

	/**
	 * @generated
	 */
	private static class CreateInfProduct_1002Command extends
			CreateElementCommand {

		/**
		 * @generated
		 */
		public CreateInfProduct_1002Command(CreateElementRequest req) {
			super(req);
		}

		/**
		 * @generated
		 */
		protected EClass getEClassToEdit() {
			return drlPackage.eINSTANCE.getSchema();
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
	}

	/**
	 * @generated
	 */
	private static class CreateInfElemRefGroup_1003Command extends
			CreateElementCommand {

		/**
		 * @generated
		 */
		public CreateInfElemRefGroup_1003Command(CreateElementRequest req) {
			super(req);
		}

		/**
		 * @generated
		 */
		protected EClass getEClassToEdit() {
			return drlPackage.eINSTANCE.getSchema();
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
