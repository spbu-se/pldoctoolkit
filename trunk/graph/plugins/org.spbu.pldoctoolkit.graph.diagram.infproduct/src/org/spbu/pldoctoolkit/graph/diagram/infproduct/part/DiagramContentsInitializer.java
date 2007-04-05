package org.spbu.pldoctoolkit.graph.diagram.infproduct.part;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;

import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

import org.spbu.pldoctoolkit.graph.DocumentationCore;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElemRefGroup;

import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.GenericDocumentPartGroupsEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefGroupEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefGroupInfElemRefsGroupEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElementEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfProductEditPart;

import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class DiagramContentsInitializer {

	/**
	 * @generated
	 */
	private Collection myLinkDescriptors = new LinkedList();

	/**
	 * @generated
	 */
	private Map myEObject2NodeMap = new HashMap();

	/**
	 * @generated
	 */
	public void initDiagramContents(Diagram diagram, EObject diagramModelObject) {
		createDocumentationCore_79Children(diagram, diagramModelObject);
		Resource resource = diagramModelObject.eResource();
		for (Iterator it = resource.getContents().iterator(); it.hasNext();) {
			EObject nextResourceObject = (EObject) it.next();
			if (nextResourceObject == diagramModelObject) {
				continue;
			}
			int nodeVID = DrlModelVisualIDRegistry.getNodeVisualID(diagram,
					nextResourceObject);
			switch (nodeVID) {
			case InfElemRefGroupEditPart.VISUAL_ID: {
				Node nextNode = ViewService.createNode(diagram,
						nextResourceObject, DrlModelVisualIDRegistry
								.getType(InfElemRefGroupEditPart.VISUAL_ID),
						DrlModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				myEObject2NodeMap.put(nextResourceObject, nextNode);
				createInfElemRefGroup_1003Children(nextNode, nextResourceObject);
				break;
			}
			}
		}
		createLinks(diagram);
	}

	/**
	 * @generated
	 */
	private void createInfElement_1001Children(View viewObject,
			EObject modelObject) {

		storeLinks(modelObject, viewObject.getDiagram());
	}

	/**
	 * @generated
	 */
	private void createInfProduct_1002Children(View viewObject,
			EObject modelObject) {

		storeLinks(modelObject, viewObject.getDiagram());
	}

	/**
	 * @generated
	 */
	private void createInfElemRefGroup_1003Children(View viewObject,
			EObject modelObject) {

		storeLinks(modelObject, viewObject.getDiagram());
	}

	/**
	 * @generated
	 */
	private void createDocumentationCore_79Children(View viewObject,
			EObject modelObject) {
		EObject nextValue;
		int nodeVID;
		for (Iterator values = ((DocumentationCore) modelObject).getParts()
				.iterator(); values.hasNext();) {
			nextValue = (EObject) values.next();
			nodeVID = DrlModelVisualIDRegistry.getNodeVisualID(viewObject,
					nextValue);
			switch (nodeVID) {
			case InfElementEditPart.VISUAL_ID: {
				Node nextNode = ViewService.createNode(viewObject, nextValue,
						DrlModelVisualIDRegistry
								.getType(InfElementEditPart.VISUAL_ID),
						DrlModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				myEObject2NodeMap.put(nextValue, nextNode);
				createInfElement_1001Children(nextNode, nextValue);
				break;
			}
			case InfProductEditPart.VISUAL_ID: {
				Node nextNode = ViewService.createNode(viewObject, nextValue,
						DrlModelVisualIDRegistry
								.getType(InfProductEditPart.VISUAL_ID),
						DrlModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				myEObject2NodeMap.put(nextValue, nextNode);
				createInfProduct_1002Children(nextNode, nextValue);
				break;
			}
			}
		}
		storeLinks(modelObject, viewObject.getDiagram());
	}

	/**
	 *@generated
	 */
	private void storeLinks(EObject container, Diagram diagram) {
		EClass containerMetaclass = container.eClass();
		storeFeatureModelFacetLinks(container, containerMetaclass, diagram);
		storeTypeModelFacetLinks(container, containerMetaclass);
	}

	/**
	 * @generated
	 */
	private void storeTypeModelFacetLinks(EObject container,
			EClass containerMetaclass) {
		storeTypeModelFacetLinks_InfElemRef_3001(container, containerMetaclass);
	}

	/**
	 * @generated
	 */
	private void storeTypeModelFacetLinks_InfElemRef_3001(EObject container,
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
						myLinkDescriptors.add(new LinkDescriptor(src, dst,
								nextValue,
								DrlModelElementTypes.InfElemRef_3001, linkVID));
					}
				}
			}
		}
	}

	/**
	 *@generated
	 */
	private void storeFeatureModelFacetLinks(EObject container,
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
									container,
									nextDestination,
									DrlModelElementTypes.GenericDocumentPartGroups_3002,
									GenericDocumentPartGroupsEditPart.VISUAL_ID));
					Node nextNode = ViewService
							.createNode(
									diagram,
									nextDestination,
									DrlModelVisualIDRegistry
											.getType(InfElemRefGroupEditPart.VISUAL_ID),
									DrlModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
					myEObject2NodeMap.put(nextDestination, nextNode);
					createInfElemRefGroup_1003Children(nextNode,
							nextDestination);

				}
			}
		}

		if (DrlPackage.eINSTANCE.getInfElemRefGroup().isSuperTypeOf(
				containerMetaclass)) {
			for (Iterator destinations = ((InfElemRefGroup) container)
					.getInfElemRefsGroup().iterator(); destinations.hasNext();) {
				EObject nextDestination = (EObject) destinations.next();
				myLinkDescriptors
						.add(new LinkDescriptor(
								container,
								nextDestination,
								DrlModelElementTypes.InfElemRefGroupInfElemRefsGroup_3003,
								InfElemRefGroupInfElemRefsGroupEditPart.VISUAL_ID));

			}
		}
	}

	/**
	 * @generated
	 */
	private void createLinks(Diagram diagram) {
		for (Iterator it = myLinkDescriptors.iterator(); it.hasNext();) {
			LinkDescriptor nextLinkDescriptor = (LinkDescriptor) it.next();
			Edge edge = (Edge) ViewService.getInstance().createEdge(
					nextLinkDescriptor.getSemanticAdapter(), diagram,
					String.valueOf(nextLinkDescriptor.getVisualID()),
					ViewUtil.APPEND,
					DrlModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
			if (edge != null) {
				edge.setSource((Node) myEObject2NodeMap.get(nextLinkDescriptor
						.getSource()));
				edge.setTarget((Node) myEObject2NodeMap.get(nextLinkDescriptor
						.getDestination()));
			}
		}
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

		/**
		 * @generated
		 */
		protected LinkDescriptor(EObject source, EObject destination,
				EObject linkElement, IElementType elementType, int linkVID) {
			this(source, destination, linkVID);
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
		protected LinkDescriptor(EObject source, EObject destination,
				IElementType elementType, int linkVID) {
			this(source, destination, linkVID);
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
		 * @generated
		 */
		private LinkDescriptor(EObject source, EObject destination, int linkVID) {
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
	}
}
