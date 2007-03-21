package org.spbu.pldoctoolkit.drlvisual.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.gef.commands.UnexecutableCommand;

import org.eclipse.gmf.runtime.emf.type.core.commands.CreateRelationshipCommand;

import org.spbu.pldoctoolkit.drlvisual.InfElemRef;
import org.spbu.pldoctoolkit.drlvisual.InfElement;
import org.spbu.pldoctoolkit.drlvisual.SubelementedElement;

import org.spbu.pldoctoolkit.drlvisual.diagram.providers.DRLModelElementTypes;

import org.spbu.pldoctoolkit.drlvisual.drlPackage;

/**
 * @generated
 */
public class InfElementItemSemanticEditPolicy extends
		DRLModelBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return getMSLWrapper(new DestroyElementCommand(req) {

			protected EObject getElementToDestroy() {
				View view = (View) getHost().getModel();
				EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
				if (annotation != null) {
					return view;
				}
				return super.getElementToDestroy();
			}

		});
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (DRLModelElementTypes.InfElemRef_3001 == req.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingInfElemRef3001Command(req)
					: getCreateCompleteIncomingInfElemRef3001Command(req);
		}
		if (DRLModelElementTypes.InfElemRef_3002 == req.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingInfElemRef3002Command(req)
					: getCreateCompleteIncomingInfElemRef3002Command(req);
		}
		if (DRLModelElementTypes.SubelementedElementElements_3003 == req
				.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingSubelementedElement_Elements3003Command(req)
					: null;
		}
		return super.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getCreateStartOutgoingInfElemRef3001Command(
			CreateRelationshipRequest req) {
		return new Command() {
		};
	}

	/**
	 * @generated
	 */
	protected Command getCreateCompleteIncomingInfElemRef3001Command(
			CreateRelationshipRequest req) {
		if (!(req.getSource() instanceof SubelementedElement)) {
			return UnexecutableCommand.INSTANCE;
		}
		final SubelementedElement element = (SubelementedElement) req
				.getSource();
		if (req.getContainmentFeature() == null) {
			req.setContainmentFeature(drlPackage.eINSTANCE
					.getSubelementedElement_Elements());
		}
		return getMSLWrapper(new CreateIncomingInfElemRef3001Command(req) {

			/**
			 * @generated
			 */
			protected EObject getElementToEdit() {
				return element;
			}
		});
	}

	/**
	 * @generated
	 */
	private static class CreateIncomingInfElemRef3001Command extends
			CreateRelationshipCommand {

		/**
		 * @generated
		 */
		public CreateIncomingInfElemRef3001Command(CreateRelationshipRequest req) {
			super(req);
		}

		/**
		 * @generated
		 */
		protected EClass getEClassToEdit() {
			return drlPackage.eINSTANCE.getSubelementedElement();
		};

		/**
		 * @generated
		 */
		protected void setElementToEdit(EObject element) {
			throw new UnsupportedOperationException();
		}

		/**
		 * @generated
		 */
		protected EObject doDefaultElementCreation() {
			InfElemRef newElement = (InfElemRef) super
					.doDefaultElementCreation();
			if (newElement != null) {
				newElement.setInfelem((InfElement) getTarget());
				DRLModelElementTypes.Initializers.InfElemRef_3001
						.init(newElement);
			}
			return newElement;
		}
	}

	/**
	 * @generated
	 */
	protected Command getCreateStartOutgoingInfElemRef3002Command(
			CreateRelationshipRequest req) {
		return new Command() {
		};
	}

	/**
	 * @generated
	 */
	protected Command getCreateCompleteIncomingInfElemRef3002Command(
			CreateRelationshipRequest req) {
		if (!(req.getSource() instanceof SubelementedElement)) {
			return UnexecutableCommand.INSTANCE;
		}
		final SubelementedElement element = (SubelementedElement) req
				.getSource();
		if (req.getContainmentFeature() == null) {
			req.setContainmentFeature(drlPackage.eINSTANCE
					.getSubelementedElement_Elements());
		}
		return getMSLWrapper(new CreateIncomingInfElemRef3002Command(req) {

			/**
			 * @generated
			 */
			protected EObject getElementToEdit() {
				return element;
			}
		});
	}

	/**
	 * @generated
	 */
	private static class CreateIncomingInfElemRef3002Command extends
			CreateRelationshipCommand {

		/**
		 * @generated
		 */
		public CreateIncomingInfElemRef3002Command(CreateRelationshipRequest req) {
			super(req);
		}

		/**
		 * @generated
		 */
		protected EClass getEClassToEdit() {
			return drlPackage.eINSTANCE.getSubelementedElement();
		};

		/**
		 * @generated
		 */
		protected void setElementToEdit(EObject element) {
			throw new UnsupportedOperationException();
		}

		/**
		 * @generated
		 */
		protected EObject doDefaultElementCreation() {
			InfElemRef newElement = (InfElemRef) super
					.doDefaultElementCreation();
			if (newElement != null) {
				newElement.setInfelem((InfElement) getTarget());
				DRLModelElementTypes.Initializers.InfElemRef_3002
						.init(newElement);
			}
			return newElement;
		}
	}

	/**
	 * @generated
	 */
	protected Command getCreateStartOutgoingSubelementedElement_Elements3003Command(
			CreateRelationshipRequest req) {

		return new Command() {
		};
	}
}
