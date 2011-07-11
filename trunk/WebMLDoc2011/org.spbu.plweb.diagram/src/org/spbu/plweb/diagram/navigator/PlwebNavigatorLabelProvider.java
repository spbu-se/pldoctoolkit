package org.spbu.plweb.diagram.navigator;

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
import org.spbu.plweb.DiagramRoot;
import org.spbu.plweb.DocTopic;
import org.spbu.plweb.Group;
import org.spbu.plweb.Root;
import org.spbu.plweb.diagram.edit.parts.AreaEditPart;
import org.spbu.plweb.diagram.edit.parts.AreaTitleEditPart;
import org.spbu.plweb.diagram.edit.parts.DiagramRootEditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic2EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic3EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic4EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic5EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicName2EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicName3EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicName4EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicName5EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicNameEditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicEditPart;
import org.spbu.plweb.diagram.edit.parts.GroupEditPart;
import org.spbu.plweb.diagram.edit.parts.NodeEditPart;
import org.spbu.plweb.diagram.edit.parts.NodeTitleEditPart;
import org.spbu.plweb.diagram.edit.parts.PageEditPart;
import org.spbu.plweb.diagram.edit.parts.PageTitleEditPart;
import org.spbu.plweb.diagram.edit.parts.RootEditPart;
import org.spbu.plweb.diagram.edit.parts.SiteViewEditPart;
import org.spbu.plweb.diagram.edit.parts.SiteViewTitleEditPart;
import org.spbu.plweb.diagram.edit.parts.SourceRefElementClassEditPart;
import org.spbu.plweb.diagram.part.PlwebDiagramEditorPlugin;
import org.spbu.plweb.diagram.part.PlwebVisualIDRegistry;
import org.spbu.plweb.diagram.providers.PlwebElementTypes;
import org.spbu.plweb.diagram.providers.PlwebParserProvider;

/**
 * @generated
 */
public class PlwebNavigatorLabelProvider extends LabelProvider implements
		ICommonLabelProvider, ITreePathLabelProvider {

	/**
	 * @generated
	 */
	static {
		PlwebDiagramEditorPlugin
				.getInstance()
				.getImageRegistry()
				.put("Navigator?UnknownElement", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
		PlwebDiagramEditorPlugin
				.getInstance()
				.getImageRegistry()
				.put("Navigator?ImageNotFound", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	public void updateLabel(ViewerLabel label, TreePath elementPath) {
		Object element = elementPath.getLastSegment();
		if (element instanceof PlwebNavigatorItem
				&& !isOwnView(((PlwebNavigatorItem) element).getView())) {
			return;
		}
		label.setText(getText(element));
		label.setImage(getImage(element));
	}

	/**
	 * @generated
	 */
	public Image getImage(Object element) {
		if (element instanceof PlwebNavigatorGroup) {
			PlwebNavigatorGroup group = (PlwebNavigatorGroup) element;
			return PlwebDiagramEditorPlugin.getInstance().getBundledImage(
					group.getIcon());
		}

		if (element instanceof PlwebNavigatorItem) {
			PlwebNavigatorItem navigatorItem = (PlwebNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return super.getImage(element);
			}
			return getImage(navigatorItem.getView());
		}

		return super.getImage(element);
	}

	/**
	 * @generated
	 */
	public Image getImage(View view) {
		switch (PlwebVisualIDRegistry.getVisualID(view)) {
		case DocTopic2EditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://plweb/2.0?DocTopic", PlwebElementTypes.DocTopic_3006); //$NON-NLS-1$
		case GroupEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://plweb/2.0?Group", PlwebElementTypes.Group_2005); //$NON-NLS-1$
		case DocTopic4EditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://plweb/2.0?DocTopic", PlwebElementTypes.DocTopic_3008); //$NON-NLS-1$
		case NodeEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://plweb/2.0?Node", PlwebElementTypes.Node_2006); //$NON-NLS-1$
		case SourceRefElementClassEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Link?http://plweb/2.0?SourceRefElement?class", PlwebElementTypes.SourceRefElementClass_4001); //$NON-NLS-1$
		case SiteViewEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://plweb/2.0?SiteView", PlwebElementTypes.SiteView_2002); //$NON-NLS-1$
		case PageEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://plweb/2.0?Page", PlwebElementTypes.Page_2004); //$NON-NLS-1$
		case DocTopic5EditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://plweb/2.0?DocTopic", PlwebElementTypes.DocTopic_3009); //$NON-NLS-1$
		case DocTopic3EditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://plweb/2.0?DocTopic", PlwebElementTypes.DocTopic_3007); //$NON-NLS-1$
		case DiagramRootEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Diagram?http://plweb/2.0?DiagramRoot", PlwebElementTypes.DiagramRoot_1000); //$NON-NLS-1$
		case DocTopicEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://plweb/2.0?DocTopic", PlwebElementTypes.DocTopic_3005); //$NON-NLS-1$
		case AreaEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://plweb/2.0?Area", PlwebElementTypes.Area_2003); //$NON-NLS-1$
		case RootEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://plweb/2.0?Root", PlwebElementTypes.Root_2001); //$NON-NLS-1$
		}
		return getImage("Navigator?UnknownElement", null); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private Image getImage(String key, IElementType elementType) {
		ImageRegistry imageRegistry = PlwebDiagramEditorPlugin.getInstance()
				.getImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null && elementType != null
				&& PlwebElementTypes.isKnownElementType(elementType)) {
			image = PlwebElementTypes.getImage(elementType);
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
		if (element instanceof PlwebNavigatorGroup) {
			PlwebNavigatorGroup group = (PlwebNavigatorGroup) element;
			return group.getGroupName();
		}

		if (element instanceof PlwebNavigatorItem) {
			PlwebNavigatorItem navigatorItem = (PlwebNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return null;
			}
			return getText(navigatorItem.getView());
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
		switch (PlwebVisualIDRegistry.getVisualID(view)) {
		case DocTopic2EditPart.VISUAL_ID:
			return getDocTopic_3006Text(view);
		case GroupEditPart.VISUAL_ID:
			return getGroup_2005Text(view);
		case DocTopic4EditPart.VISUAL_ID:
			return getDocTopic_3008Text(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2006Text(view);
		case SourceRefElementClassEditPart.VISUAL_ID:
			return getSourceRefElementClass_4001Text(view);
		case SiteViewEditPart.VISUAL_ID:
			return getSiteView_2002Text(view);
		case PageEditPart.VISUAL_ID:
			return getPage_2004Text(view);
		case DocTopic5EditPart.VISUAL_ID:
			return getDocTopic_3009Text(view);
		case DocTopic3EditPart.VISUAL_ID:
			return getDocTopic_3007Text(view);
		case DiagramRootEditPart.VISUAL_ID:
			return getDiagramRoot_1000Text(view);
		case DocTopicEditPart.VISUAL_ID:
			return getDocTopic_3005Text(view);
		case AreaEditPart.VISUAL_ID:
			return getArea_2003Text(view);
		case RootEditPart.VISUAL_ID:
			return getRoot_2001Text(view);
		}
		return getUnknownElementText(view);
	}

	/**
	 * @generated
	 */
	private String getNode_2006Text(View view) {
		IParser parser = PlwebParserProvider.getParser(
				PlwebElementTypes.Node_2006,
				view.getElement() != null ? view.getElement() : view,
				PlwebVisualIDRegistry.getType(NodeTitleEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5004); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getSourceRefElementClass_4001Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getDocTopic_3008Text(View view) {
		IParser parser = PlwebParserProvider.getParser(
				PlwebElementTypes.DocTopic_3008,
				view.getElement() != null ? view.getElement() : view,
				PlwebVisualIDRegistry
						.getType(DocTopicDocTopicName3EditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5011); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getDiagramRoot_1000Text(View view) {
		DiagramRoot domainModelElement = (DiagramRoot) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getProjectPath();
		} else {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"No domain element for view with visualID = " + 1000); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getDocTopic_3005Text(View view) {
		IParser parser = PlwebParserProvider.getParser(
				PlwebElementTypes.DocTopic_3005,
				view.getElement() != null ? view.getElement() : view,
				PlwebVisualIDRegistry
						.getType(DocTopicDocTopicName5EditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5013); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getGroup_2005Text(View view) {
		Group domainModelElement = (Group) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getTitle();
		} else {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"No domain element for view with visualID = " + 2005); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getArea_2003Text(View view) {
		IParser parser = PlwebParserProvider.getParser(
				PlwebElementTypes.Area_2003,
				view.getElement() != null ? view.getElement() : view,
				PlwebVisualIDRegistry.getType(AreaTitleEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5002); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getDocTopic_3009Text(View view) {
		IParser parser = PlwebParserProvider.getParser(
				PlwebElementTypes.DocTopic_3009,
				view.getElement() != null ? view.getElement() : view,
				PlwebVisualIDRegistry
						.getType(DocTopicDocTopicName4EditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5012); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getRoot_2001Text(View view) {
		Root domainModelElement = (Root) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getTitle();
		} else {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"No domain element for view with visualID = " + 2001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getPage_2004Text(View view) {
		IParser parser = PlwebParserProvider.getParser(
				PlwebElementTypes.Page_2004,
				view.getElement() != null ? view.getElement() : view,
				PlwebVisualIDRegistry.getType(PageTitleEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5003); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getDocTopic_3006Text(View view) {
		IParser parser = PlwebParserProvider.getParser(
				PlwebElementTypes.DocTopic_3006,
				view.getElement() != null ? view.getElement() : view,
				PlwebVisualIDRegistry
						.getType(DocTopicDocTopicNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5009); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getDocTopic_3007Text(View view) {
		IParser parser = PlwebParserProvider.getParser(
				PlwebElementTypes.DocTopic_3007,
				view.getElement() != null ? view.getElement() : view,
				PlwebVisualIDRegistry
						.getType(DocTopicDocTopicName2EditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5010); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getSiteView_2002Text(View view) {
		IParser parser = PlwebParserProvider.getParser(
				PlwebElementTypes.SiteView_2002,
				view.getElement() != null ? view.getElement() : view,
				PlwebVisualIDRegistry.getType(SiteViewTitleEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
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
		return DiagramRootEditPart.MODEL_ID.equals(PlwebVisualIDRegistry
				.getModelID(view));
	}

}
