package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.spbu.pldoctoolkit.graph.DrlElement;
import org.spbu.pldoctoolkit.graph.DrlFactory;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.InfElemRefGroup;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.InfElemRef2TypeLinkCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.InfElemRefGroupCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.InfElemRefTypeLinkCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands.InfElementCreateCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class DocumentationCoreItemSemanticEditPolicy extends
		DrlModelBaseItemSemanticEditPolicy {

	/* (non-Javadoc)
	 * @see org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies.DrlModelBaseItemSemanticEditPolicy#getCommand(org.eclipse.gef.Request)
	 */
	@Override
	public Command getCommand(Request request) {
		// TODO Auto-generated method stub
		return super.getCommand(request);
	}

	/**
	 * @generated NOT
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		DrlElement parent = (DrlElement) req.getParameter("element");
		if(parent != null) {
			if (DrlModelElementTypes.InfElement_1001 == req.getElementType()) {
				return getInfElementCreateCommand(req, parent);
			}
			if (DrlModelElementTypes.InfElemRefGroup_1003 == req.getElementType()) {
				req.setContainer(parent);
				return getMSLWrapper(new InfElemRefGroupCreateCommand(req));
			}
		}
		
		return super.getCreateCommand(req);
	}

	/**
	 * @param req
	 * @param parent
	 * @return
	 */
	private Command getInfElementCreateCommand(CreateElementRequest req,
			DrlElement parent) {
		CompoundCommand cc = new CompoundCommand();
		ICommand createRefCommand = null;
		
		if (req.getContainmentFeature() == null) {
			req.setContainmentFeature(DrlPackage.eINSTANCE
					.getDocumentationCore_Parts());
		}
		if(DrlFactory.eINSTANCE.getDrlPackage().getGenericDocumentPart()
			.isSuperTypeOf(parent.eClass())) {
			GenericDocumentPart container = (GenericDocumentPart) parent;

			CreateRelationshipRequest createRefRequest = new DynamicTargetCreateRelationshipRequest(
					req.getEditingDomain(), container, container, null, 
					DrlModelElementTypes.InfElemRef_3001, 
					DrlPackage.eINSTANCE.getGenericDocumentPart_InfElemRefs());
			createRefRequest.setParameter("createRequest", req);
			createRefCommand = new InfElemRefTypeLinkCreateCommand(createRefRequest);
		} else if(DrlFactory.eINSTANCE.getDrlPackage().getInfElemRefGroup()
				.isSuperTypeOf(parent.eClass())) {
			InfElemRefGroup group = (InfElemRefGroup) parent;
			GenericDocumentPart container = (GenericDocumentPart) group.eContainer();
			
			CreateRelationshipRequest createRefRequest = new DynamicTargetCreateRelationshipRequest(
					req.getEditingDomain(), container, group, null, 
					DrlModelElementTypes.InfElemRef_3003, 
					DrlPackage.eINSTANCE.getGenericDocumentPart_InfElemRefs());

			createRefRequest.setParameter("createRequest", req);
			createRefCommand = new InfElemRef2TypeLinkCreateCommand(createRefRequest, container);
		}
		
		cc.add(getMSLWrapper(new InfElementCreateCommand(req)));
		if(createRefCommand != null) {
			cc.add(getMSLWrapper(createRefCommand));
		}
		
		return cc;
	}

	private class DynamicTargetCreateRelationshipRequest extends CreateRelationshipRequest {
		public DynamicTargetCreateRelationshipRequest(
				TransactionalEditingDomain editingDomain, EObject container,
				EObject source, EObject target, IElementType elementType,
				EReference containmentFeature) {
			super(editingDomain, container, source, target, elementType, containmentFeature);
		}

		public EObject getTarget() {
			return ((CreateElementRequest)getParameter("createRequest"))
				.getNewElement();
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
