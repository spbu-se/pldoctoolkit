package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.DeferredLayoutCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetViewMutabilityCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalConnectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElemRefGroup;
import org.spbu.pldoctoolkit.graph.InfElement;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.DocumentationCoreEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.GenericDocumentPartGroupsEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRef2EditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefGroupEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElementEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.part.DrlModelVisualIDRegistry;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class DocumentationCoreCanonicalEditPolicy extends
		CanonicalConnectionEditPolicy {

	/**
	 * @generated
	 */
	protected List getSemanticChildrenList() {
		List result = new UniqueEList<EObject>();

		GenericDocumentPart rootElement = findRootObject();
		result = new ArrayList();
		findAccessibleElements(rootElement, result);
		
		return result;
	}

	/**
	 * @return The GenericDocumentPart for the view marked with EAnnotation "root". 
	 */
	@SuppressWarnings("unchecked")
	private GenericDocumentPart findRootObject() {
		EObject result = null;
		
		Diagram diagram = ((View)getHost().getModel()).getDiagram();
		EList<View> childrenViews = diagram.getVisibleChildren();
		Iterator<View> viewIter = childrenViews.iterator();
		View resultView = null;
		while(viewIter.hasNext()) {
			View nextView = viewIter.next();
			if(nextView.getEAnnotation("root") != null) {
				resultView = nextView;
				break;
			}
		}
		
		if(resultView != null) {
			result = resultView.getElement();
		}
		
		return (GenericDocumentPart) result;
	}

	@SuppressWarnings("unchecked")
	private void findAccessibleElements(GenericDocumentPart curElement, List currentResult) {
		if(curElement == null) {
			return;
		}
		
		currentResult.add(curElement);
		
		Iterator groupIter = curElement.getGroups().iterator();
		while(groupIter.hasNext()) {
			currentResult.add(groupIter.next());
		}
		
		EList<InfElemRef> infElemRefs = curElement.getInfElemRefs();
		for(InfElemRef ref : infElemRefs) {
			InfElement element = ref.getInfelem();
			findAccessibleElements(element, currentResult);
		}
		
		return;
	}

	/**
	 * @generated NOT
	 */
	protected boolean shouldDeleteView(View view) {
//		if (view.getEAnnotation("Shortcut") != null) { //$NON-NLS-1$
//			return view.isSetElement()
//					&& (view.getElement() == null || view.getElement()
//							.eIsProxy());
//		}
		int nodeVID = DrlModelVisualIDRegistry.getVisualID(view);
		switch (nodeVID) {
		case InfElementEditPart.VISUAL_ID:
		case InfProductEditPart.VISUAL_ID:
		case InfElemRefEditPart.VISUAL_ID:
		case InfElemRef2EditPart.VISUAL_ID:
		case InfElemRefGroupEditPart.VISUAL_ID:
//			return view.isSetElement()
//			&& (view.getElement() == null || view.getElement()
//					.eIsProxy());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected String getDefaultFactoryHint() {
		return null;
	}

	/**
	 * @generated
	 */
	protected List getSemanticConnectionsList() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	protected EObject getSourceElement(EObject relationship) {
		return null;
	}

	/**
	 * @generated
	 */
	protected EObject getTargetElement(EObject relationship) {
		return null;
	}

	/**
	 * @generated
	 */
	protected boolean shouldIncludeConnection(Edge connector,
			Collection children) {
		return false;
	}

	/**
	 * @generated NOT
	 */
	protected void refreshSemantic() {
		List createdViews = new LinkedList();
		createdViews.addAll(refreshSemanticChildren());
//		createdViews.addAll(refreshPhantoms());
		
		List createdConnectionViews = new LinkedList();
		createdConnectionViews.addAll(refreshSemanticConnections());
		createdConnectionViews.addAll(refreshConnections());

		createdViews.addAll(createdConnectionViews);
		makeViewsImmutable(createdViews);
		
		if (createdViews.size() > 1) {
			// perform a layout of the container
			DeferredLayoutCommand layoutCmd = new DeferredLayoutCommand(host()
					.getEditingDomain(), createdViews, host());
			executeCommand(new ICommandProxy(layoutCmd));
		}
		
		markAllInitialized(getDiagram());
	}

	/**
	 * @generated
	 */
	private EditPart getDiagramEditPart() {
		return (EditPart) getHost().getViewer().getEditPartRegistry().get(
				getDiagram());
	}

	/**
	 * @generated
	 */
	private Collection myLinkDescriptors = new LinkedList();

	/**
	 * @generated
	 */
//	private Map myEObject2ViewMap = new HashMap();
	
	private List<View> myNewViews = new LinkedList<View>();
	
	/**
	 * @generated NOT
	 */
	private Collection refreshConnections() {
		try {
			collectAllLinks(getDiagram());
			Collection existingLinks = new LinkedList(getDiagram().getEdges());
			for (Iterator diagramLinks = existingLinks.iterator(); diagramLinks
					.hasNext();) {
				Edge nextDiagramLink = (Edge) diagramLinks.next();
				EObject diagramLinkObject = nextDiagramLink.getElement();
				View diagramLinkSrcView = nextDiagramLink.getSource();
				EObject diagramLinkSrc = diagramLinkSrcView.getElement();
				EObject diagramLinkDst = nextDiagramLink.getTarget()
						.getElement();
				int diagramLinkVisualID = DrlModelVisualIDRegistry
						.getVisualID(nextDiagramLink);
				for (Iterator modelLinkDescriptors = myLinkDescriptors
						.iterator(); modelLinkDescriptors.hasNext();) {
					LinkDescriptor nextLinkDescriptor = (LinkDescriptor) modelLinkDescriptors
							.next();
					if (diagramLinkObject == nextLinkDescriptor
							.getLinkElement()
							&& diagramLinkSrc == nextLinkDescriptor.getSource()
							&& diagramLinkDst == nextLinkDescriptor
									.getDestination()
							//HAND
							&& diagramLinkSrcView == nextLinkDescriptor.getSourceView()
							&& diagramLinkVisualID == nextLinkDescriptor
									.getVisualID()) {
						diagramLinks.remove();
						modelLinkDescriptors.remove();
					}
				}
			}
			for(Iterator linksToDeleteIter = existingLinks.iterator();
					linksToDeleteIter.hasNext();) {
				Edge nextLink = (Edge) linksToDeleteIter.next();
				View nextLinkTargetView = nextLink.getTarget();
				markInitialized(nextLinkTargetView, false);
				myNewViews.add(nextLinkTargetView);
			}
			
			deleteViews(existingLinks.iterator());
			return createConnections(myLinkDescriptors);
		} catch(Exception e) {
			e.printStackTrace();
			return Collections.EMPTY_LIST;
		} finally {
			myLinkDescriptors.clear();
//			myEObject2ViewMap.clear();
			myNewViews.clear();
		}
	}

	/**
	 * @generated NOT
	 */
	private void collectAllLinks(View view) {
		EObject modelElement = view.getElement();
		int diagramElementVisualID = DrlModelVisualIDRegistry.getVisualID(view);
		switch (diagramElementVisualID) {
		case InfElementEditPart.VISUAL_ID:
		case InfProductEditPart.VISUAL_ID:
		case InfElemRefGroupEditPart.VISUAL_ID:
		case DocumentationCoreEditPart.VISUAL_ID: {
//			myEObject2ViewMap.put(modelElement, view); 
			if(!isInitialized(view)) {
				myNewViews.add(view);
			}
			
			storeLinks(view, modelElement, getDiagram());
		}
		default: {
		}
			for (Iterator children = view.getChildren().iterator(); children
					.hasNext();) {
				View childView = (View) children.next();
				collectAllLinks(childView);
			}
		}
	}

	//TODO extract Visitor and Acceptor
	private void markAllInitialized(View view) {
		EObject modelElement = view.getElement();
		int diagramElementVisualID = DrlModelVisualIDRegistry.getVisualID(view);
		switch (diagramElementVisualID) {
		case InfElementEditPart.VISUAL_ID:
		case InfProductEditPart.VISUAL_ID:
		case InfElemRefGroupEditPart.VISUAL_ID:
		case DocumentationCoreEditPart.VISUAL_ID: {
			markInitialized(view, true);
		}
		default: {
		}
			for (Iterator children = view.getChildren().iterator(); children
					.hasNext();) {
				View childView = (View) children.next();
				markAllInitialized(childView);
			}
		}
	}
	
	private boolean isInitialized(View view) {
		return view.getEAnnotation("initialized") != null;
	}
	
	private void markInitialized(final View view, final boolean initialize) {
		if(!(isInitialized(view) ^ initialize)) {
			return;
		}
		
		final EAnnotation initializedAnnotation;
		if(initialize) {
			initializedAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			initializedAnnotation.setSource("initialized"); //$NON-NLS-1$
		} else {
			initializedAnnotation = view.getEAnnotation("initialized");
		}
		
		AbstractTransactionalCommand setAnnotationCommand = new AbstractTransactionalCommand(
				host().getEditingDomain(), "Mark initialized", Collections.EMPTY_LIST){
			@Override
			protected CommandResult doExecuteWithResult(
					IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				
				EList<EAnnotation> annotations = view.getEAnnotations();
				if(initialize) {
					annotations.add(initializedAnnotation);
				} else {
					annotations.remove(initializedAnnotation);
				}
				return CommandResult.newOKCommandResult();
			}
			
		};
		
		try {
			setAnnotationCommand.execute(new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @generated
	 */
	private Collection createConnections(Collection linkDescriptors) {
		if (linkDescriptors.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		List adapters = new LinkedList();
		for (Iterator linkDescriptorsIterator = linkDescriptors.iterator(); linkDescriptorsIterator
				.hasNext();) {
			final LinkDescriptor nextLinkDescriptor = (LinkDescriptor) linkDescriptorsIterator
					.next();
			//HAND
			EditPart sourceEditPart = getEditPartFor(nextLinkDescriptor
					.getSourceView());
			EditPart targetEditPart = getNewEditPartFor(nextLinkDescriptor
					.getDestination(), true);
			if (sourceEditPart == null || targetEditPart == null) {
				continue;
			}
			CreateConnectionViewRequest.ConnectionViewDescriptor descriptor = new CreateConnectionViewRequest.ConnectionViewDescriptor(
					nextLinkDescriptor.getSemanticAdapter(), null,
					ViewUtil.APPEND, false, ((IGraphicalEditPart) getHost())
							.getDiagramPreferencesHint());
			CreateConnectionViewRequest ccr = new CreateConnectionViewRequest(
					descriptor);
			ccr.setType(RequestConstants.REQ_CONNECTION_START);
			ccr.setSourceEditPart(sourceEditPart);
			sourceEditPart.getCommand(ccr);
			ccr.setTargetEditPart(targetEditPart);
			ccr.setType(RequestConstants.REQ_CONNECTION_END);
			Command cmd = targetEditPart.getCommand(ccr);
			if (cmd != null && cmd.canExecute()) {
				executeCommand(cmd);
				IAdaptable viewAdapter = (IAdaptable) ccr.getNewObject();
				if (viewAdapter != null) {
					adapters.add(viewAdapter);
				}
			}
		}
		return adapters;
	}

	private EditPart getNewEditPartFor(EObject modelElement, boolean doMarkViewUsed) {
		View modelElementView = getNewViewFor(modelElement, doMarkViewUsed);
		
		return getEditPartFor(modelElementView);
	}

	private View getNewViewFor(EObject modelElement, boolean doMarkViewUsed) {
		View modelElementView = null;
		for(View view: myNewViews) {
			EObject viewElement = view.getElement();
			if(viewElement == modelElement) {
				modelElementView = view;
				if(doMarkViewUsed) {
					myNewViews.remove(view);
				}
				break;
			}
		}
		return modelElementView;
	}
	
	private EditPart getEditPartFor(View view) {
		if (view != null) {
			return (EditPart) getHost().getViewer().getEditPartRegistry().get(
					view);
		}
		return null;	
	}

	/**
	 *@generated
	 */
	private void storeLinks(View view, EObject container, Diagram diagram) {
		EClass containerMetaclass = container.eClass();
		storeFeatureModelFacetLinks(view, container, containerMetaclass, diagram);
		storeTypeModelFacetLinks(view, container, containerMetaclass);
	}

	/**
	 * @generated NOT
	 */
	private void storeTypeModelFacetLinks(View containerView, EObject container,
			EClass containerMetaclass) {
		storeTypeModelFacetLinks_InfElemRef_3001(containerView, container, containerMetaclass);
		storeTypeModelFacetLinks_InfElemRef_3003(containerView, container, containerMetaclass);
	}

	/**
	 * @generated NOT
	 */
	private void storeTypeModelFacetLinks_InfElemRef_3001(
			View containerView, EObject container,
			EClass containerMetaclass) {
		if (DrlPackage.eINSTANCE.getGenericDocumentPart().isSuperTypeOf(
				containerMetaclass)) {
			for (Iterator values = ((GenericDocumentPart) container)
					.getInfElemRefs().iterator(); values.hasNext();) {
				EObject nextValue = ((EObject) values.next());
				int linkVID = DrlModelVisualIDRegistry
						.getLinkWithClassVisualID(nextValue);
				if (InfElemRefEditPart.VISUAL_ID == linkVID) {
					Object structuralFeatureResult = ((InfElemRef) nextValue)
							.getInfelem();
					if (structuralFeatureResult instanceof EObject) {
						EObject dst = (EObject) structuralFeatureResult;
						EObject src = container;
						myLinkDescriptors.add(new LinkDescriptor(
								containerView,
								src, 
								dst,
								nextValue,
								DrlModelElementTypes.InfElemRef_3001, linkVID));
					}
				}
			}
		}
	}

	/**
	 * @generated NOT
	 */
	private void storeTypeModelFacetLinks_InfElemRef_3003(
			View containerView,
			EObject container,
			EClass containerMetaclass) {
		if (DrlPackage.eINSTANCE.getInfElemRefGroup().isSuperTypeOf(
				containerMetaclass)) {
			GenericDocumentPart groupOwner = (GenericDocumentPart) ((InfElemRefGroup) container)
					.eContainer();
			for (Iterator values = groupOwner.getInfElemRefs()
					.iterator(); values.hasNext();) {
				EObject nextValue = ((EObject) values.next());
				int linkVID = DrlModelVisualIDRegistry
						.getLinkWithClassVisualID(nextValue);
				if (InfElemRef2EditPart.VISUAL_ID == linkVID) {
					Object structuralFeatureResult = ((InfElemRef) nextValue)
							.getInfelem();
					if (structuralFeatureResult instanceof EObject) {
						EObject dst = (EObject) structuralFeatureResult;
						structuralFeatureResult = ((InfElemRef) nextValue)
								.getGroup();
						if (structuralFeatureResult instanceof EObject 
								&& structuralFeatureResult == container) {
							myLinkDescriptors.add(new LinkDescriptor(containerView, 
									container, 
									dst,
									nextValue,
									DrlModelElementTypes.InfElemRef_3003,
									linkVID));
						}
					}
				}
			}
		}
	}

	private View getInfelemRefGroupView(View containerView, InfElemRefGroup src) {
		View result = null;
		
		for(Iterator edgesIter = containerView.getSourceEdges().iterator(); edgesIter.hasNext();) {
			Edge nextEdge = (Edge) edgesIter.next();
			View targetView = nextEdge.getTarget();
			EObject targetObject = targetView .getElement();
			if(targetObject == src) {
				result = targetView;
				break;
			}
		}
		
		return result;
	}

	/**
	 *@generated NOT
	 */
	private void storeFeatureModelFacetLinks(View containerView, EObject container,
			EClass containerMetaclass, Diagram diagram) {

		if (DrlPackage.eINSTANCE.getGenericDocumentPart().isSuperTypeOf(
				containerMetaclass)) {
			for (Iterator destinations = ((GenericDocumentPart) container)
					.getGroups().iterator(); destinations.hasNext();) {
				EObject nextDestination = (EObject) destinations.next();
				if (InfElemRefGroupEditPart.VISUAL_ID == DrlModelVisualIDRegistry
						.getNodeVisualID(diagram, nextDestination)) {
					myLinkDescriptors
							.add(new LinkDescriptor(
									containerView,
									container,
									nextDestination,
									DrlModelElementTypes.GenericDocumentPartGroups_3002,
									GenericDocumentPartGroupsEditPart.VISUAL_ID));

				}
			}
		}

	}

	/**
	 * @generated
	 */
	private Diagram getDiagram() {
		return ((View) getHost().getModel()).getDiagram();
	}

	/**
	 * @generated
	 */
	private class LinkDescriptor {

		/**
		 * @generated
		 */
		private EObject mySource;

		/**
		 * @generated
		 */
		private EObject myDestination;

		/**
		 * @generated
		 */
		private EObject myLinkElement;

		/**
		 * @generated
		 */
		private int myVisualID;

		/**
		 * @generated
		 */
		private IAdaptable mySemanticAdapter;

		private View mySourceView;

		/**
		 * @generated
		 */
		protected LinkDescriptor(View sourceView, EObject source, EObject destination,
				EObject linkElement, IElementType elementType, int linkVID) {
			this(sourceView, source, destination, linkVID);
			myLinkElement = linkElement;
			final IElementType elementTypeCopy = elementType;
			mySemanticAdapter = new EObjectAdapter(linkElement) {
				public Object getAdapter(Class adapter) {
					if (IElementType.class.equals(adapter)) {
						return elementTypeCopy;
					}
					return super.getAdapter(adapter);
				}
			};
		}

		/**
		 * @generated
		 */
		protected LinkDescriptor(View sourceView, EObject source, EObject destination,
				IElementType elementType, int linkVID) {
			this(sourceView, source, destination, linkVID);
			myLinkElement = null;
			final IElementType elementTypeCopy = elementType;
			mySemanticAdapter = new IAdaptable() {
				public Object getAdapter(Class adapter) {
					if (IElementType.class.equals(adapter)) {
						return elementTypeCopy;
					}
					return null;
				}
			};
		}

		/**
		 * @generated NOT
		 */
		private LinkDescriptor(View sourceView, EObject source, EObject destination, int linkVID) {
			mySourceView = sourceView;
			mySource = source;
			myDestination = destination;
			myVisualID = linkVID;
		}

		/**
		 * @generated
		 */
		protected EObject getSource() {
			return mySource;
		}

		/**
		 * @generated
		 */
		protected EObject getDestination() {
			return myDestination;
		}

		/**
		 * @generated
		 */
		protected EObject getLinkElement() {
			return myLinkElement;
		}

		/**
		 * @generated
		 */
		protected int getVisualID() {
			return myVisualID;
		}

		/**
		 * @generated
		 */
		protected IAdaptable getSemanticAdapter() {
			return mySemanticAdapter;
		}

		/**
		 * @return the mySourceView
		 */
		protected View getSourceView() {
			return mySourceView;
		}

		/**
		 * @param mySourceView
		 */
		public void setMySourceView(View mySourceView) {
			this.mySourceView = mySourceView;
		}
	}

}
