package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.SemanticEditPolicy;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IEditHelperContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElemRefGroup;
import org.spbu.pldoctoolkit.graph.InfElement;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.helpers.DrlModelBaseEditHelper;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.part.DrlModelVisualIDRegistry;

/**
 * @generated
 */
public class DrlModelBaseItemSemanticEditPolicy extends SemanticEditPolicy {

	/**
	 * Extended request data key to hold editpart visual id.
	 * 
	 * @generated
	 */
	public static final String VISUAL_ID_KEY = "visual_id"; //$NON-NLS-1$

	/**
	 * Add visual id of edited editpart to extended data of the request
	 * so command switch can decide what kind of diagram element is being edited.
	 * It is done in those cases when it's not possible to deduce diagram
	 * element kind from domain element.
	 * 
	 * @generated
	 */
	public Command getCommand(Request request) {
		System.out.println("request: " + request);

		if (request instanceof ReconnectRequest) {
			Object view = ((ReconnectRequest) request).getConnectionEditPart()
					.getModel();
			if (view instanceof View) {
				Integer id = new Integer(DrlModelVisualIDRegistry
						.getVisualID((View) view));
				request.getExtendedData().put(VISUAL_ID_KEY, id);
			}
		}
		return super.getCommand(request);
	}

	/**
	 * Returns visual id from request parameters.
	 * 
	 * @generated
	 */
	protected int getVisualID(IEditCommandRequest request) {
		Object id = request.getParameter(VISUAL_ID_KEY);
		return id instanceof Integer ? ((Integer) id).intValue() : -1;
	}

	/**
	 * @generated
	 */
	protected Command getSemanticCommand(IEditCommandRequest request) {
		IEditCommandRequest completedRequest = completeRequest(request);
		Object editHelperContext = completedRequest.getEditHelperContext();
		if (editHelperContext instanceof View
				|| (editHelperContext instanceof IEditHelperContext && ((IEditHelperContext) editHelperContext)
						.getEObject() instanceof View)) {
			// no semantic commands are provided for pure design elements
			return null;
		}
		if (editHelperContext == null) {
			editHelperContext = ViewUtil
					.resolveSemanticElement((View) getHost().getModel());
		}
		IElementType elementType = ElementTypeRegistry.getInstance()
				.getElementType(editHelperContext);
		if (elementType == ElementTypeRegistry.getInstance().getType(
				"org.eclipse.gmf.runtime.emf.type.core.default")) { //$NON-NLS-1$ 
			elementType = null;
		}
		Command epCommand = getSemanticCommandSwitch(completedRequest);
		if (epCommand != null) {
			ICommand command = epCommand instanceof ICommandProxy ? ((ICommandProxy) epCommand)
					.getICommand()
					: new CommandProxy(epCommand);
			completedRequest.setParameter(
					DrlModelBaseEditHelper.EDIT_POLICY_COMMAND, command);
		}
		Command ehCommand = null;
		if (elementType != null) {
			ICommand command = elementType.getEditCommand(completedRequest);
			if (command != null) {
				if (!(command instanceof CompositeTransactionalCommand)) {
					TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
							.getEditingDomain();
					command = new CompositeTransactionalCommand(editingDomain,
							null).compose(command);
				}
				ehCommand = new ICommandProxy(command);
			}
		}
		boolean shouldProceed = true;
		if (completedRequest instanceof DestroyRequest) {
			shouldProceed = shouldProceed((DestroyRequest) completedRequest);
		}
		if (shouldProceed) {
			if (completedRequest instanceof DestroyRequest) {
				TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
						.getEditingDomain();
				Command deleteViewCommand = new ICommandProxy(
						new DeleteCommand(editingDomain, (View) getHost()
								.getModel()));
				ehCommand = ehCommand == null ? deleteViewCommand : ehCommand
						.chain(deleteViewCommand);
			}
			return ehCommand;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getSemanticCommandSwitch(IEditCommandRequest req) {
		if (req instanceof CreateRelationshipRequest) {
			return getCreateRelationshipCommand((CreateRelationshipRequest) req);
		} else if (req instanceof CreateElementRequest) {
			return getCreateCommand((CreateElementRequest) req);
		} else if (req instanceof ConfigureRequest) {
			return getConfigureCommand((ConfigureRequest) req);
		} else if (req instanceof DestroyElementRequest) {
			return getDestroyElementCommand((DestroyElementRequest) req);
		} else if (req instanceof DestroyReferenceRequest) {
			return getDestroyReferenceCommand((DestroyReferenceRequest) req);
		} else if (req instanceof DuplicateElementsRequest) {
			return getDuplicateCommand((DuplicateElementsRequest) req);
		} else if (req instanceof GetEditContextRequest) {
			return getEditContextCommand((GetEditContextRequest) req);
		} else if (req instanceof MoveRequest) {
			return getMoveCommand((MoveRequest) req);
		} else if (req instanceof ReorientReferenceRelationshipRequest) {
			return getReorientReferenceRelationshipCommand((ReorientReferenceRelationshipRequest) req);
		} else if (req instanceof ReorientRelationshipRequest) {
			return getReorientRelationshipCommand((ReorientRelationshipRequest) req);
		} else if (req instanceof SetRequest) {
			return getSetCommand((SetRequest) req);
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getConfigureCommand(ConfigureRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getSetCommand(SetRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getEditContextCommand(GetEditContextRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getDestroyReferenceCommand(DestroyReferenceRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getDuplicateCommand(DuplicateElementsRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getMoveCommand(MoveRequest req) {
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getReorientReferenceRelationshipCommand(
			ReorientReferenceRelationshipRequest req) {
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * @generated
	 */
	protected Command getReorientRelationshipCommand(
			ReorientRelationshipRequest req) {
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * @generated
	 */
	protected Command getMSLWrapper(ICommand cmd) {
		return new ICommandProxy(cmd);
	}

	/**
	 * @generated
	 */
	protected EObject getSemanticElement() {
		return ViewUtil.resolveSemanticElement((View) getHost().getModel());
	}

	/**
	 * Finds container element for the new relationship of the specified type.
	 * Default implementation goes up by containment hierarchy starting from
	 * the specified element and returns the first element that is instance of
	 * the specified container class.
	 * 
	 * @generated
	 */
	protected EObject getRelationshipContainer(Object uelement,
			EClass containerClass, IElementType relationshipType) {
		if (uelement instanceof EObject) {
			EObject element = (EObject) uelement;
			for (; element != null; element = element.eContainer()) {
				if (containerClass.isSuperTypeOf(element.eClass())) {
					return element;
				}
			}
		}
		return null;
	}

	/**
	 * @generated
	 */
	public static class LinkConstraints {
		/**
		 * @generated
		 */
		private static final String OPPOSITE_END_VAR = "oppositeEnd"; //$NON-NLS-1$

		/**
		 * @generated
		 */
		public static boolean canCreateInfElemRef_3001(
				GenericDocumentPart source, InfElement target) {
			return canExistInfElemRef_3001(source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateGenericDocumentPartGroups_3002(
				GenericDocumentPart source, InfElemRefGroup target) {
			if (source != null) {
				if (source.getGroups().contains(target)) {
					return false;
				}
			}
			return canExistGenericDocumentPartGroups_3002(source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canCreateInfElemRef_3003(
				GenericDocumentPart container, InfElemRefGroup source,
				InfElement target) {
			return canExistInfElemRef_3003(container, source, target);
		}

		/**
		 * @generated
		 */
		public static boolean canExistInfElemRef_3001(
				GenericDocumentPart source, InfElement target) {
			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistGenericDocumentPartGroups_3002(
				GenericDocumentPart source, InfElemRefGroup target) {
			return true;
		}

		/**
		 * @generated
		 */
		public static boolean canExistInfElemRef_3003(
				GenericDocumentPart container, InfElemRefGroup source,
				InfElement target) {
			return true;
		}

	}

}
