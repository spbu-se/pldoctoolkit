package org.spbu.pldoctoolkit.graph.diagram.productline.navigator;

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
import org.spbu.pldoctoolkit.graph.ProductLine;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.DocumentationCoreEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.PLSchemeEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLine2EditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineDataEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelDiagramEditorPlugin;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;
import org.spbu.pldoctoolkit.graph.diagram.productline.providers.DrlModelElementTypes;
import org.spbu.pldoctoolkit.graph.diagram.productline.providers.DrlModelParserProvider;

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
		case ProductLineEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Diagram?http://math.spbu.ru/drl?ProductLine", DrlModelElementTypes.ProductLine_79); //$NON-NLS-1$
		case ProductLine2EditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://math.spbu.ru/drl?ProductLine", DrlModelElementTypes.ProductLine_1001); //$NON-NLS-1$
		case ProductLineDataEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?org.eclipse.draw2d.RectangleFigure", DrlModelElementTypes.Node_2001); //$NON-NLS-1$
		case PLSchemeEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?PLSchemeFigure", DrlModelElementTypes.Node_2002); //$NON-NLS-1$
		case ProductEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://math.spbu.ru/drl?Product", DrlModelElementTypes.Product_2003); //$NON-NLS-1$
		case DocumentationCoreEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?org.eclipse.draw2d.RectangleFigure", DrlModelElementTypes.Node_2004); //$NON-NLS-1$
		case InfProductEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://math.spbu.ru/drl?InfProduct", DrlModelElementTypes.InfProduct_2005); //$NON-NLS-1$
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
		case ProductLineEditPart.VISUAL_ID:
			return getProductLine_79Text(view);
		case ProductLine2EditPart.VISUAL_ID:
			return getProductLine_1001Text(view);
		case ProductLineDataEditPart.VISUAL_ID:
			return getNode_2001Text(view);
		case PLSchemeEditPart.VISUAL_ID:
			return getNode_2002Text(view);
		case ProductEditPart.VISUAL_ID:
			return getProduct_2003Text(view);
		case DocumentationCoreEditPart.VISUAL_ID:
			return getNode_2004Text(view);
		case InfProductEditPart.VISUAL_ID:
			return getInfProduct_2005Text(view);
		}
		return getUnknownElementText(view);
	}

	/**
	 * @generated
	 */
	private String getProductLine_1001Text(View view) {
		IAdaptable hintAdapter = new DrlModelParserProvider.HintAdapter(
				DrlModelElementTypes.ProductLine_1001,
				(view.getElement() != null ? view.getElement() : view),
				DrlModelVisualIDRegistry
						.getType(ProductLineNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE
					.intValue());
		} else {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 4003); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getNode_2001Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getNode_2002Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getProduct_2003Text(View view) {
		IAdaptable hintAdapter = new DrlModelParserProvider.HintAdapter(
				DrlModelElementTypes.Product_2003,
				(view.getElement() != null ? view.getElement() : view),
				DrlModelVisualIDRegistry.getType(ProductNameEditPart.VISUAL_ID));
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
	private String getNode_2004Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getInfProduct_2005Text(View view) {
		IAdaptable hintAdapter = new DrlModelParserProvider.HintAdapter(
				DrlModelElementTypes.InfProduct_2005,
				(view.getElement() != null ? view.getElement() : view),
				DrlModelVisualIDRegistry
						.getType(InfProductNameEditPart.VISUAL_ID));
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
	private String getProductLine_79Text(View view) {
		ProductLine domainModelElement = (ProductLine) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getName();
		} else {
			DrlModelDiagramEditorPlugin.getInstance().logError(
					"No domain element for view with visualID = " + 79); //$NON-NLS-1$
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
		return ProductLineEditPart.MODEL_ID.equals(DrlModelVisualIDRegistry
				.getModelID(view));
	}

}
