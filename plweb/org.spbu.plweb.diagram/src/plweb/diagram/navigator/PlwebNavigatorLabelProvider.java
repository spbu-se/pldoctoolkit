package plweb.diagram.navigator;

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

import plweb.DiagramRoot;
import plweb.Group;
import plweb.diagram.edit.parts.AreaEditPart;
import plweb.diagram.edit.parts.AreaTitleEditPart;
import plweb.diagram.edit.parts.DiagramRootEditPart;
import plweb.diagram.edit.parts.GroupEditPart;
import plweb.diagram.edit.parts.NodeEditPart;
import plweb.diagram.edit.parts.NodeTitleEditPart;
import plweb.diagram.edit.parts.PageEditPart;
import plweb.diagram.edit.parts.PageTitleEditPart;
import plweb.diagram.edit.parts.SourceRefElementClassEditPart;
import plweb.diagram.part.PlwebDiagramEditorPlugin;
import plweb.diagram.part.PlwebVisualIDRegistry;
import plweb.diagram.providers.PlwebElementTypes;
import plweb.diagram.providers.PlwebParserProvider;

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
				.put(
						"Navigator?UnknownElement", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
		PlwebDiagramEditorPlugin
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
		case DiagramRootEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Diagram?http://plweb/1.0?DiagramRoot", PlwebElementTypes.DiagramRoot_1000); //$NON-NLS-1$
		case AreaEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://plweb/1.0?Area", PlwebElementTypes.Area_2001); //$NON-NLS-1$
		case GroupEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://plweb/1.0?Group", PlwebElementTypes.Group_2002); //$NON-NLS-1$
		case PageEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://plweb/1.0?Page", PlwebElementTypes.Page_2003); //$NON-NLS-1$
		case NodeEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://plweb/1.0?Node", PlwebElementTypes.Node_2004); //$NON-NLS-1$
		case SourceRefElementClassEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Link?http://plweb/1.0?SourceRefElement?class", PlwebElementTypes.SourceRefElementClass_4001); //$NON-NLS-1$
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
		case DiagramRootEditPart.VISUAL_ID:
			return getDiagramRoot_1000Text(view);
		case AreaEditPart.VISUAL_ID:
			return getArea_2001Text(view);
		case GroupEditPart.VISUAL_ID:
			return getGroup_2002Text(view);
		case PageEditPart.VISUAL_ID:
			return getPage_2003Text(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2004Text(view);
		case SourceRefElementClassEditPart.VISUAL_ID:
			return getSourceRefElementClass_4001Text(view);
		}
		return getUnknownElementText(view);
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
	private String getArea_2001Text(View view) {
		IParser parser = PlwebParserProvider.getParser(
				PlwebElementTypes.Area_2001, view.getElement() != null ? view
						.getElement() : view, PlwebVisualIDRegistry
						.getType(AreaTitleEditPart.VISUAL_ID));
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
	private String getGroup_2002Text(View view) {
		Group domainModelElement = (Group) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getTitle();
		} else {
			PlwebDiagramEditorPlugin.getInstance().logError(
					"No domain element for view with visualID = " + 2002); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getPage_2003Text(View view) {
		IParser parser = PlwebParserProvider.getParser(
				PlwebElementTypes.Page_2003, view.getElement() != null ? view
						.getElement() : view, PlwebVisualIDRegistry
						.getType(PageTitleEditPart.VISUAL_ID));
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
	private String getNode_2004Text(View view) {
		IParser parser = PlwebParserProvider.getParser(
				PlwebElementTypes.Node_2004, view.getElement() != null ? view
						.getElement() : view, PlwebVisualIDRegistry
						.getType(NodeTitleEditPart.VISUAL_ID));
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
	private String getSourceRefElementClass_4001Text(View view) {
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
		return DiagramRootEditPart.MODEL_ID.equals(PlwebVisualIDRegistry
				.getModelID(view));
	}

}
