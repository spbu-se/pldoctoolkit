/*
 * 
 */
package webml.diagram.navigator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
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

import webml.diagram.edit.parts.Area2EditPart;
import webml.diagram.edit.parts.AreaEditPart;
import webml.diagram.edit.parts.AreaName2EditPart;
import webml.diagram.edit.parts.AreaNameEditPart;
import webml.diagram.edit.parts.ContentUnit2EditPart;
import webml.diagram.edit.parts.ContentUnitEditPart;
import webml.diagram.edit.parts.ContentUnitName2EditPart;
import webml.diagram.edit.parts.ContentUnitNameEditPart;
import webml.diagram.edit.parts.DocTopicEditPart;
import webml.diagram.edit.parts.DocTopicNameEditPart;
import webml.diagram.edit.parts.KoLinkEditPart;
import webml.diagram.edit.parts.NormalLinkEditPart;
import webml.diagram.edit.parts.OkLinkEditPart;
import webml.diagram.edit.parts.OperationUnit2EditPart;
import webml.diagram.edit.parts.OperationUnitEditPart;
import webml.diagram.edit.parts.OperationUnitName2EditPart;
import webml.diagram.edit.parts.OperationUnitNameEditPart;
import webml.diagram.edit.parts.Page2EditPart;
import webml.diagram.edit.parts.PageEditPart;
import webml.diagram.edit.parts.PageName2EditPart;
import webml.diagram.edit.parts.PageNameEditPart;
import webml.diagram.edit.parts.SiteviewEditPart;
import webml.diagram.edit.parts.TransportLinkEditPart;
import webml.diagram.part.WebmlDiagramEditorPlugin;
import webml.diagram.part.WebmlVisualIDRegistry;
import webml.diagram.providers.WebmlElementTypes;
import webml.diagram.providers.WebmlParserProvider;

/**
 * @generated
 */
public class WebmlNavigatorLabelProvider extends LabelProvider implements
		ICommonLabelProvider, ITreePathLabelProvider {

	/**
	 * @generated
	 */
	static {
		WebmlDiagramEditorPlugin
				.getInstance()
				.getImageRegistry()
				.put(
						"Navigator?UnknownElement", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
		WebmlDiagramEditorPlugin
				.getInstance()
				.getImageRegistry()
				.put(
						"Navigator?ImageNotFound", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	public void updateLabel(ViewerLabel label, TreePath elementPath) {
		Object element = elementPath.getLastSegment();
		if (element instanceof WebmlNavigatorItem
				&& !isOwnView(((WebmlNavigatorItem) element).getView())) {
			return;
		}
		label.setText(getText(element));
		label.setImage(getImage(element));
	}

	/**
	 * @generated
	 */
	public Image getImage(Object element) {
		if (element instanceof WebmlNavigatorGroup) {
			WebmlNavigatorGroup group = (WebmlNavigatorGroup) element;
			return WebmlDiagramEditorPlugin.getInstance().getBundledImage(
					group.getIcon());
		}

		if (element instanceof WebmlNavigatorItem) {
			WebmlNavigatorItem navigatorItem = (WebmlNavigatorItem) element;
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
		switch (WebmlVisualIDRegistry.getVisualID(view)) {
		case SiteviewEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Diagram?webml?Siteview", WebmlElementTypes.Siteview_1000); //$NON-NLS-1$
		case AreaEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?webml?Area", WebmlElementTypes.Area_2001); //$NON-NLS-1$
		case PageEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?webml?Page", WebmlElementTypes.Page_2002); //$NON-NLS-1$
		case ContentUnitEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?webml?ContentUnit", WebmlElementTypes.ContentUnit_2003); //$NON-NLS-1$
		case OperationUnitEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?webml?OperationUnit", WebmlElementTypes.OperationUnit_2004); //$NON-NLS-1$
		case Area2EditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?webml?Area", WebmlElementTypes.Area_3005); //$NON-NLS-1$
		case Page2EditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?webml?Page", WebmlElementTypes.Page_3001); //$NON-NLS-1$
		case ContentUnit2EditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?webml?ContentUnit", WebmlElementTypes.ContentUnit_3002); //$NON-NLS-1$
		case DocTopicEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?webml?DocTopic", WebmlElementTypes.DocTopic_3003); //$NON-NLS-1$
		case OperationUnit2EditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?webml?OperationUnit", WebmlElementTypes.OperationUnit_3004); //$NON-NLS-1$
		case OkLinkEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Link?webml?okLink", WebmlElementTypes.OkLink_4001); //$NON-NLS-1$
		case KoLinkEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Link?webml?koLink", WebmlElementTypes.KoLink_4002); //$NON-NLS-1$
		case NormalLinkEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Link?webml?normalLink", WebmlElementTypes.NormalLink_4003); //$NON-NLS-1$
		case TransportLinkEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Link?webml?transportLink", WebmlElementTypes.TransportLink_4004); //$NON-NLS-1$
		}
		return getImage("Navigator?UnknownElement", null); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private Image getImage(String key, IElementType elementType) {
		ImageRegistry imageRegistry = WebmlDiagramEditorPlugin.getInstance()
				.getImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null && elementType != null
				&& WebmlElementTypes.isKnownElementType(elementType)) {
			image = WebmlElementTypes.getImage(elementType);
			imageRegistry.put(key, image);
		}

		if (image == null) {
			image = imageRegistry.get("Navigator?ImageNotFound"); //$NON-NLS-1$
			imageRegistry.put(key, image);
		}
		return image;
	}

	/**
	 * @generated
	 */
	public String getText(Object element) {
		if (element instanceof WebmlNavigatorGroup) {
			WebmlNavigatorGroup group = (WebmlNavigatorGroup) element;
			return group.getGroupName();
		}

		if (element instanceof WebmlNavigatorItem) {
			WebmlNavigatorItem navigatorItem = (WebmlNavigatorItem) element;
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
		switch (WebmlVisualIDRegistry.getVisualID(view)) {
		case SiteviewEditPart.VISUAL_ID:
			return getSiteview_1000Text(view);
		case AreaEditPart.VISUAL_ID:
			return getArea_2001Text(view);
		case PageEditPart.VISUAL_ID:
			return getPage_2002Text(view);
		case ContentUnitEditPart.VISUAL_ID:
			return getContentUnit_2003Text(view);
		case OperationUnitEditPart.VISUAL_ID:
			return getOperationUnit_2004Text(view);
		case Area2EditPart.VISUAL_ID:
			return getArea_3005Text(view);
		case Page2EditPart.VISUAL_ID:
			return getPage_3001Text(view);
		case ContentUnit2EditPart.VISUAL_ID:
			return getContentUnit_3002Text(view);
		case DocTopicEditPart.VISUAL_ID:
			return getDocTopic_3003Text(view);
		case OperationUnit2EditPart.VISUAL_ID:
			return getOperationUnit_3004Text(view);
		case OkLinkEditPart.VISUAL_ID:
			return getOkLink_4001Text(view);
		case KoLinkEditPart.VISUAL_ID:
			return getKoLink_4002Text(view);
		case NormalLinkEditPart.VISUAL_ID:
			return getNormalLink_4003Text(view);
		case TransportLinkEditPart.VISUAL_ID:
			return getTransportLink_4004Text(view);
		}
		return getUnknownElementText(view);
	}

	/**
	 * @generated
	 */
	private String getSiteview_1000Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getArea_2001Text(View view) {
		IParser parser = WebmlParserProvider.getParser(
				WebmlElementTypes.Area_2001, view.getElement() != null ? view
						.getElement() : view, WebmlVisualIDRegistry
						.getType(AreaNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			WebmlDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5005); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getPage_2002Text(View view) {
		IParser parser = WebmlParserProvider.getParser(
				WebmlElementTypes.Page_2002, view.getElement() != null ? view
						.getElement() : view, WebmlVisualIDRegistry
						.getType(PageNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			WebmlDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5006); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getContentUnit_2003Text(View view) {
		IParser parser = WebmlParserProvider.getParser(
				WebmlElementTypes.ContentUnit_2003,
				view.getElement() != null ? view.getElement() : view,
				WebmlVisualIDRegistry
						.getType(ContentUnitNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			WebmlDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5007); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getOperationUnit_2004Text(View view) {
		IParser parser = WebmlParserProvider.getParser(
				WebmlElementTypes.OperationUnit_2004,
				view.getElement() != null ? view.getElement() : view,
				WebmlVisualIDRegistry
						.getType(OperationUnitNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			WebmlDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5008); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getArea_3005Text(View view) {
		IParser parser = WebmlParserProvider.getParser(
				WebmlElementTypes.Area_3005, view.getElement() != null ? view
						.getElement() : view, WebmlVisualIDRegistry
						.getType(AreaName2EditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			WebmlDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5009); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getPage_3001Text(View view) {
		IParser parser = WebmlParserProvider.getParser(
				WebmlElementTypes.Page_3001, view.getElement() != null ? view
						.getElement() : view, WebmlVisualIDRegistry
						.getType(PageName2EditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			WebmlDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5003); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getContentUnit_3002Text(View view) {
		IParser parser = WebmlParserProvider.getParser(
				WebmlElementTypes.ContentUnit_3002,
				view.getElement() != null ? view.getElement() : view,
				WebmlVisualIDRegistry
						.getType(ContentUnitName2EditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			WebmlDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5002); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getDocTopic_3003Text(View view) {
		IParser parser = WebmlParserProvider.getParser(
				WebmlElementTypes.DocTopic_3003,
				view.getElement() != null ? view.getElement() : view,
				WebmlVisualIDRegistry.getType(DocTopicNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			WebmlDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getOperationUnit_3004Text(View view) {
		IParser parser = WebmlParserProvider.getParser(
				WebmlElementTypes.OperationUnit_3004,
				view.getElement() != null ? view.getElement() : view,
				WebmlVisualIDRegistry
						.getType(OperationUnitName2EditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			WebmlDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5004); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getOkLink_4001Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getKoLink_4002Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getNormalLink_4003Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getTransportLink_4004Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getUnknownElementText(View view) {
		return "<UnknownElement Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$  //$NON-NLS-2$
	}

	/**
	 * @generated
	 */
	private String getUnresolvedDomainElementProxyText(View view) {
		return "<Unresolved domain element Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$  //$NON-NLS-2$
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
		return SiteviewEditPart.MODEL_ID.equals(WebmlVisualIDRegistry
				.getModelID(view));
	}

}
