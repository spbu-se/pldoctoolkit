package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.core.util.PackageUtil;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateRelationshipCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElemRefGroup;
import org.spbu.pldoctoolkit.graph.InfElement;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.part.DrlModelDiagramEditorPlugin;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class InfElementItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

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
	 * @generated NOT
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (DrlModelElementTypes.InfElemRef_3001 == req.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingInfElemRef3001Command(req)
					: getCreateCompleteIncomingInfElemRef3001Command(req);
		}
		if (DrlModelElementTypes.GenericDocumentPartGroups_3002 == req
				.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingGenericDocumentPart_Groups3002Command(req)
					: null;
		}
		if (DrlModelElementTypes.InfElemRefGroupInfElemRefsGroup_3003 == req
				.getElementType()) {
			return req.getTarget() == null ? null
					: getCreateCompleteIncomingInfElemRefGroup_InfElemRefsGroup3003Command(req);
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
	 * @generated NOT
	 */
	protected Command getCreateCompleteIncomingInfElemRef3001Command(
			CreateRelationshipRequest req) {
		if (!(req.getSource() instanceof GenericDocumentPart)) {
			return UnexecutableCommand.INSTANCE;
		}

		final GenericDocumentPart sourceElement = (GenericDocumentPart) req
				.getSource();
		final InfElement targetElement = (InfElement) req.getTarget();

		// a manual restriction: single incoming InfElemRef
		if (targetElement.getOwnerInfElemRef() != null) {
			return UnexecutableCommand.INSTANCE;
		}

		// a manual restriction: loops forbidden
		if (sourceElement == targetElement) {
			return UnexecutableCommand.INSTANCE;
		}

		if (req.getContainmentFeature() == null) {
			req.setContainmentFeature(DrlPackage.eINSTANCE
					.getGenericDocumentPart_InfElemRefs());
		}
		return getMSLWrapper(new CreateIncomingInfElemRef3001Command(req) {

			/**
			 * @generated
			 */
			protected EObject getElementToEdit() {
				return sourceElement;
			}
		});
	}

	protected Command getCreateCompleteIncomingInfElemRefGroup_InfElemRefsGroup3003Command(
			CreateRelationshipRequest req) {
		//		DrlModelDiagramEditorPlugin.getInstance().logInfo("source: " + req.getSource().toString());

		if (!(req.getSource() instanceof InfElemRefGroup)) {
			return UnexecutableCommand.INSTANCE;
		}

		final InfElemRefGroup sourceElement = (InfElemRefGroup) req.getSource();
		final InfElement targetElement = (InfElement) req.getTarget();

		// a manual restriction: single incoming InfElemRef
		if (targetElement.getOwnerInfElemRef() != null) {
			return UnexecutableCommand.INSTANCE;
		}

		if (req.getContainmentFeature() == null) {
			req.setContainmentFeature(DrlPackage.eINSTANCE
					.getGenericDocumentPart_InfElemRefs());
		}

		return getMSLWrapper(new CreateIncomingInfElemRef3001Command(req) {

			protected EObject getElementToEdit() {
				return sourceElement.eContainer();
			}

			//XXX remove this
			/* (non-Javadoc)
			 * @see org.eclipse.gmf.runtime.emf.type.core.commands.CreateRelationshipCommand#canExecute()
			 */
			@Override
			public boolean canExecute() {
				if (getEClassToEdit() == null)
					return false;
				if (getContainmentFeature() != null) {
					org.eclipse.emf.ecore.EClassifier eClassifier = getContainmentFeature()
							.getEType();
					boolean result = true;
					if (eClassifier instanceof EClass)
						result = ((EClass) eClassifier)
								.isSuperTypeOf(getElementType().getEClass());
					result = result
							&& PackageUtil.canContain(getEClassToEdit(),
									getContainmentFeature(), getElementType()
											.getEClass(), false);
					return result && super.canExecute();
				} else {
					return false;
				}
			}

			protected EObject doDefaultElementCreation() {
				InfElemRef newElement = (InfElemRef) super
						.doDefaultElementCreation();
				if (newElement != null) {
					newElement.setGroup(sourceElement);
				}
				return newElement;
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
			return DrlPackage.eINSTANCE.getGenericDocumentPart();
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
				DrlModelElementTypes.Initializers.InfElemRef_3001
						.init(newElement);
			}
			return newElement;
		}
	}

	/**
	 * @generated
	 */
	protected Command getCreateStartOutgoingGenericDocumentPart_Groups3002Command(
			CreateRelationshipRequest req) {

		return new Command() {
		};
	}
}
