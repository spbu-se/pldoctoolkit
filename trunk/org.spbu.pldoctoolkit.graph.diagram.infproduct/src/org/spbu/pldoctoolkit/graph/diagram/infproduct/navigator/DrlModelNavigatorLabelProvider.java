package org.spbu.pldoctoolkit.graph.diagram.infproduct.navigator;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;

import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import org.eclipse.gmf.runtime.notation.View;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;

import org.eclipse.jface.viewers.ITreePathLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerLabel;

import org.eclipse.swt.graphics.Image;

import org.eclipse.ui.IMemento;

import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

import org.spbu.pldoctoolkit.graph.InfElemRefGroup;

import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.DocumentationCoreEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.GenericDocumentPartGroupsEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRef2EditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefGroupEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefId2EditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefIdEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElementEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElementNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfProductNameEditPart;

import org.spbu.pldoctoolkit.graph.diagram.infproduct.part.DrlModelDiagramEditorPlugin;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.part.DrlModelVisualIDRegistry;

import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class DrlModelNavigatorLabelProvider extends LabelProvider implements
		ICommonLabelProvider, ITreePathLabelProvider {

	/**
	 * @generated
	 */
	static {
		DrlModelDiagramEditorPlugin.getInstance().getImageRegistry().put(
				"Navigator?InvalidElement",
				ImageDescriptor.getMissingImageDescriptor());
		DrlModelDiagramEditorPlugin.getInstance().getImageRegistry().put(
				"Navigator?UnknownElement",
				ImageDescriptor.getMissingImageDescriptor());
		DrlModelDiagramEditorPlugin.getInstance().getImageRegistry().put(
				"Navigator?ImageNotFound",
				ImageDescriptor.getMissingImageDescriptor());
	}

	/**
	 * @generated
	 */
	public void updateLabel(ViewerLabel label, TreePath elementPath) {
		Object element = elementPath.getLastSegment();
		if (element instanceof DrlModelNavigatorItem
				&& !isOwnView(((DrlModelNavigatorItem) element).getView())) {
			return;
		}
		label.setText(getText(element));
		label.setImage(getImage(element));
	}

	/**
	 * @generated
	 */
	public Image getImage(Object element) {
		if (element instanceof DrlModelNavigatorGroup) {
			DrlModelNavigatorGroup group = (DrlModelNavigatorGroup) element;
			return DrlModelDiagramEditorPlugin.getInstance().getBundledImage(
					group.getIcon());
		}

		if (element instanceof DrlModelNavigatorItem) {
			DrlModelNavigatorItem navigatorItem = (DrlModelNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return super.getImage(element);
			}
			return getImage(navigatorItem.getView());
		}

		// Due to plugin.xml content will be called only for "own" views
		if (element instanceof IAdaptable) {
			View view = (View) ((IAdaptable) element).getAdapter(View.class);
			if (view != null && isOwnView(view)) {
				return getImage(view);
			}
		}
		return super.getImage(element);
	}

	/**
	 * @generated
	 */
	public Image getImage(View view) {
		switch (DrlModelVisualIDRegistry.getVisualID(view)) {
		case InfElementEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://math.spbu.ru/drl?InfElement",
					DrlModelElementTypes.InfElement_1001);
		case InfProductEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://math.spbu.ru/drl?InfProduct",
					DrlModelElementTypes.InfProduct_1002);
		case InfElemRefGroupEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://math.spbu.ru/drl?InfElemRefGroup",
					DrlModelElementTypes.InfElemRefGroup_1003);
		case DocumentationCoreEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Diagram?http://math.spbu.ru/drl?DocumentationCore",
					DrlModelElementTypes.DocumentationCore_79);
		case InfElemRefEditPart.VISUAL_ID:
			return getImage("Navigator?Link?http://math.spbu.ru/drl?InfElemRef",
					DrlModelElementTypes.InfElemRef_3001);
		case GenericDocumentPartGroupsEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Link?http://math.spbu.ru/drl?GenericDocumentPart?groups",
					DrlModelElementTypes.GenericDocumentPartGroups_3002);
		case InfElemRef2EditPart.VISUAL_ID:
			return getImage("Navigator?Link?http://math.spbu.ru/drl?InfElemRef",
					DrlModelElementTypes.InfElemRef_3003);
		default:
			return getImage("Navigator?UnknownElement", null);
		}
	}

	/**
	 * @generated
	 */
	private Image getImage(String key, IElementType elementType) {
		ImageRegistry imageRegistry = DrlModelDiagramEditorPlugin.getInstance()
				.getImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null && elementType != null
				&& DrlModelElementTypes.isKnownElementType(elementType)) {
			image = DrlModelElementTypes.getImage(elementType);
			imageRegistry.put(key, image);
		}

		if (image == null) {
			image = imageRegistry.get("Navigator?ImageNotFound");
			imageRegistry.put(key, image);
		}
		return image;
	}

	/**
	 * @generated
	 */
	public String getText(Object element) {
		if (element instanceof DrlModelNavigatorGroup) {
			DrlModelNavigatorGroup group = (DrlModelNavigatorGroup) element;
			return group.getGroupName();
		}

		if (element instanceof DrlModelNavigatorItem) {
			DrlModelNavigatorItem navigatorItem = (DrlModelNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return null;
			}
			return getText(navigatorItem.getView());
		}

		// Due to plugin.xml content will be called only for "own" views
		if (element instanceof IAdaptable) {
			View view = (View) ((IAdaptable) element).getAdapter(View.class);
			if (view != null && isOwnView(view)) {
				return getText(view);
			}
		}
		return super.getText(element);
	}

	/**
	 * @generated
	 */
	public String getText(View view) {
		if (view.getElement() != null && view.getElement().eIsProxy()) {
			return getUnresolvedDomainElementProxyText(view);
		}
		switch (DrlModelVisualIDRegistry.getVisualID(view)) {
		case InfElementEditPart.VISUAL_ID:
			return getInfElement_1001Text(view);
		case InfProductEditPart.VISUAL_ID:
			return getInfProduct_1002Text(view);
		case InfElemRefGroupEditPart.VISUAL_ID:
			return getInfElemRefGroup_1003Text(view);
		case DocumentationCoreEditPart.VISUAL_ID:
			return getDocumentationCore_79Text(view);
		case InfElemRefEditPart.VISUAL_ID:
			return getInfElemRef_3001Text(view);
		case GenericDocumentPartGroupsEditPart.VISUAL_ID:
			return getGenericDocumentPartGroups_3002Text(view);
		case InfElemRef2EditPart.VISUAL_ID:
			return getInfElemRef_3003Text(view);
		default:
			return getUnknownElementText(view);
		}
	}

	/**
	 * @generated
	 */
	private String getInfElement_1001Text(View view) {
		IParser parser = ParserService.getInstance().getParser(
				new IAdaptable() {
					public Object getAdapter(Class adapter) {
						if (String.class.equals(adapter)) {
							return DrlModelVisualIDRegistry
									.getType(InfElementNameEditPart.VISUAL_ID);
						}
						if (IElementType.class.equals(adapter)) {
							return DrlModelElementTypes.InfElement_1001;
						}
						return null;
					}
				});
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 4001);
			return "";
		}
	}

	/**
	 * @generated
	 */
	private String getInfProduct_1002Text(View view) {
		IParser parser = ParserService.getInstance().getParser(
				new IAdaptable() {
					public Object getAdapter(Class adapter) {
						if (String.class.equals(adapter)) {
							return DrlModelVisualIDRegistry
									.getType(InfProductNameEditPart.VISUAL_ID);
						}
						if (IElementType.class.equals(adapter)) {
							return DrlModelElementTypes.InfProduct_1002;
						}
						return null;
					}
				});
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 4002);
			return "";
		}
	}

	/**
	 * @generated
	 */
	private String getInfElemRefGroup_1003Text(View view) {
		EObject domainModelElement = view.getElement();
		if (domainModelElement != null) {
			return ((InfElemRefGroup) domainModelElement).getName();
		} else {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"No domain element for view with visualID = " + 1003);
			return "";
		}
	}

	/**
	 * @generated
	 */
	private String getDocumentationCore_79Text(View view) {
		return "";
	}

	/**
	 * @generated
	 */
	private String getInfElemRef_3001Text(View view) {
		IParser parser = ParserService.getInstance().getParser(
				new IAdaptable() {
					public Object getAdapter(Class adapter) {
						if (String.class.equals(adapter)) {
							return DrlModelVisualIDRegistry
									.getType(InfElemRefIdEditPart.VISUAL_ID);
						}
						if (IElementType.class.equals(adapter)) {
							return DrlModelElementTypes.InfElemRef_3001;
						}
						return null;
					}
				});
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 4003);
			return "";
		}
	}

	/**
	 * @generated
	 */
	private String getGenericDocumentPartGroups_3002Text(View view) {
		return "";
	}

	/**
	 * @generated
	 */
	private String getInfElemRef_3003Text(View view) {
		IParser parser = ParserService.getInstance().getParser(
				new IAdaptable() {
					public Object getAdapter(Class adapter) {
						if (String.class.equals(adapter)) {
							return DrlModelVisualIDRegistry
									.getType(InfElemRefId2EditPart.VISUAL_ID);
						}
						if (IElementType.class.equals(adapter)) {
							return DrlModelElementTypes.InfElemRef_3003;
						}
						return null;
					}
				});
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 4004);
			return "";
		}
	}

	/**
	 * @generated
	 */
	private String getUnknownElementText(View view) {
		return "<UnknownElement Visual_ID = " + view.getType() + ">";
	}

	/**
	 * @generated
	 */
	private String getUnresolvedDomainElementProxyText(View view) {
		return "<Unresolved domain element Visual_ID = " + view.getType() + ">";
	}

	/**
	 * @generated
	 */
	public void init(ICommonContentExtensionSite aConfig) {
	}

	/**
	 * @generated
	 */
	public void restoreState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	public void saveState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	public String getDescription(Object anElement) {
		return null;
	}

	/**
	 * @generated
	 */
	private boolean isOwnView(View view) {
		return DocumentationCoreEditPart.MODEL_ID
				.equals(DrlModelVisualIDRegistry.getModelID(view));
	}

}
