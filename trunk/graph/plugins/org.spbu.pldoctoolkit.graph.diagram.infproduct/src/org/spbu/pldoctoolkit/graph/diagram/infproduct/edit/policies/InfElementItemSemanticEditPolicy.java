package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.InfElement;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.InfElemRefTypeLinkCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElementEditPart;
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
		CompoundCommand cc = new CompoundCommand();
		Collection allEdges = new ArrayList();
		View view = (View) getHost().getModel();
		allEdges.addAll(view.getSourceEdges());
		allEdges.addAll(view.getTargetEdges());
		for (Iterator it = allEdges.iterator(); it.hasNext();) {
			Edge nextEdge = (Edge) it.next();
			EditPart nextEditPart = (EditPart) getHost().getViewer()
					.getEditPartRegistry().get(nextEdge);
			EditCommandRequestWrapper editCommandRequest = new EditCommandRequestWrapper(
					new DestroyElementRequest(((InfElementEditPart) getHost())
							.getEditingDomain(), req.isConfirmationRequired()),
					Collections.EMPTY_MAP);
			cc.add(nextEditPart.getCommand(editCommandRequest));
		}
		cc.add(getMSLWrapper(new DestroyElementCommand(req) {

			protected EObject getElementToDestroy() {
				View view = (View) getHost().getModel();
				EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
				if (annotation != null) {
					return view;
				}
				return super.getElementToDestroy();
			}

		}));
		return cc;
	}

	/**
	 * @generated NOT
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (DrlModelElementTypes.InfElemRef_3001 == req.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingInfElemRef_3001Command(req)
					: getCreateCompleteIncomingInfElemRef_3001Command(req);
		}
		if (DrlModelElementTypes.GenericDocumentPartGroups_3002 == req
				.getElementType()) {
			return req.getTarget() == null ? getCreateStartOutgoingGenericDocumentPartGroups_3002Command(req)
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
	protected Command getCreateStartOutgoingInfElemRef_3001Command(
			CreateRelationshipRequest req) {
		EObject sourceEObject = req.getSource();
		if (false == sourceEObject instanceof GenericDocumentPart) {
			return UnexecutableCommand.INSTANCE;
		}
		GenericDocumentPart source = (GenericDocumentPart) sourceEObject;
		if (!DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateInfElemRef_3001(source, null)) {
			return UnexecutableCommand.INSTANCE;
		}
		return new Command() {
		};
	}

	/**
	 * @generated
	 */
	protected Command getCreateCompleteIncomingInfElemRef_3001Command(
			CreateRelationshipRequest req) {
		EObject sourceEObject = req.getSource();
		EObject targetEObject = req.getTarget();
		if (false == sourceEObject instanceof GenericDocumentPart
				|| false == targetEObject instanceof InfElement) {
			return UnexecutableCommand.INSTANCE;
		}
		GenericDocumentPart source = (GenericDocumentPart) sourceEObject;
		InfElement target = (InfElement) targetEObject;
		if (!DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateInfElemRef_3001(source, target)) {
			return UnexecutableCommand.INSTANCE;
		}
		if (req.getContainmentFeature() == null) {
			req.setContainmentFeature(DrlPackage.eINSTANCE
					.getGenericDocumentPart_InfElemRefs());
		}
		
		return getMSLWrapper(new InfElemRefTypeLinkCreateCommand(req, source,
				target));
	}

	/**
	 * @generated
	 */
	protected Command getCreateStartOutgoingGenericDocumentPartGroups_3002Command(
			CreateRelationshipRequest req) {
		EObject sourceEObject = req.getSource();
		if (false == sourceEObject instanceof GenericDocumentPart) {
			return UnexecutableCommand.INSTANCE;
		}
		GenericDocumentPart source = (GenericDocumentPart) sourceEObject;
		if (!DrlModelBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateGenericDocumentPartGroups_3002(source, null)) {
			return UnexecutableCommand.INSTANCE;
		}
		return new Command() {
		};
	}

	protected Command getCreateCompleteIncomingInfElemRefGroup_InfElemRefsGroup3003Command(
			CreateRelationshipRequest req) {
		return UnexecutableCommand.INSTANCE;
		/*		//		DrlModelDiagramEditorPlugin.getInstance().logInfo("source: " + req.getSource().toString());

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
		 */
	}
}
