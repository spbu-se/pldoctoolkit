package org.spbu.pldoctoolkit.graph.diagram.fproduct.navigator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
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
import org.spbu.pldoctoolkit.graph.FinalInfProduct;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.FinalInfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.InfElementEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.InfElementNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.InfProductNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.part.DrlModelDiagramEditorPlugin;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.part.DrlModelVisualIDRegistry;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.providers.DrlModelElementTypes;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.providers.DrlModelParserProvider;

/**
 * @generated
 */
public class DrlModelNavigatorLabelProvider extends LabelProvider implements
		ICommonLabelProvider, ITreePathLabelProvider {

	/**
	 * @generated
	 */
	static {
		DrlModelDiagramEditorPlugin
				.getInstance()
				.getImageRegistry()
				.put(
						"Navigator?UnknownElement", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
		DrlModelDiagramEditorPlugin
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

		return super.getImage(element);
	}

	/**
	 * @generated
	 */
	public Image getImage(View view) {
		switch (DrlModelVisualIDRegistry.getVisualID(view)) {
		case FinalInfProductEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Diagram?http://math.spbu.ru/drl?FinalInfProduct", DrlModelElementTypes.FinalInfProduct_79); //$NON-NLS-1$
		case InfProductEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://math.spbu.ru/drl?InfProduct", DrlModelElementTypes.InfProduct_1001); //$NON-NLS-1$
		case InfElementEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://math.spbu.ru/drl?InfElement", DrlModelElementTypes.InfElement_1002); //$NON-NLS-1$
		}
		return getImage("Navigator?UnknownElement", null); //$NON-NLS-1$
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
			image = imageRegistry.get("Navigator?ImageNotFound"); //$NON-NLS-1$
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
		case FinalInfProductEditPart.VISUAL_ID:
			return getFinalInfProduct_79Text(view);
		case InfProductEditPart.VISUAL_ID:
			return getInfProduct_1001Text(view);
		case InfElementEditPart.VISUAL_ID:
			return getInfElement_1002Text(view);
		}
		return getUnknownElementText(view);
	}

	/**
	 * @generated
	 */
	private String getFinalInfProduct_79Text(View view) {
		FinalInfProduct domainModelElement = (FinalInfProduct) view
				.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getId();
		} else {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"No domain element for view with visualID = " + 79); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getInfProduct_1001Text(View view) {
		IAdaptable hintAdapter = new DrlModelParserProvider.HintAdapter(
				DrlModelElementTypes.InfProduct_1001,
				(view.getElement() != null ? view.getElement() : view),
				DrlModelVisualIDRegistry
						.getType(InfProductNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE
					.intValue());
		} else {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 4001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getInfElement_1002Text(View view) {
		IAdaptable hintAdapter = new DrlModelParserProvider.HintAdapter(
				DrlModelElementTypes.InfElement_1002,
				(view.getElement() != null ? view.getElement() : view),
				DrlModelVisualIDRegistry
						.getType(InfElementNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE
					.intValue());
		} else {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 4002); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getUnknownElementText(View view) {
		return "<UnknownElement Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @generated
	 */
	private String getUnresolvedDomainElementProxyText(View view) {
		return "<Unresolved domain element Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
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
		return FinalInfProductEditPart.MODEL_ID.equals(DrlModelVisualIDRegistry
				.getModelID(view));
	}

}
