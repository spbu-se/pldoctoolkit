package org.spbu.pldoctoolkit.graph.diagram.productline.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;

/**
 * @generated
 */
public class DrlModelParserProvider extends AbstractProvider implements
		IParserProvider {

	/**
	 * @generated
	 */
	private IParser productProductName_4001Parser;

	/**
	 * @generated
	 */
	private IParser getProductProductName_4001Parser() {
		if (productProductName_4001Parser == null) {
			productProductName_4001Parser = createProductProductName_4001Parser();
		}
		return productProductName_4001Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createProductProductName_4001Parser() {
		DrlModelStructuralFeatureParser parser = new DrlModelStructuralFeatureParser(
				DrlPackage.eINSTANCE.getProduct_Name());
		parser.setViewPattern("{0}");
		parser.setEditPattern("{0}");
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser infProductInfProductName_4002Parser;

	/**
	 * @generated
	 */
	private IParser getInfProductInfProductName_4002Parser() {
		if (infProductInfProductName_4002Parser == null) {
			infProductInfProductName_4002Parser = createInfProductInfProductName_4002Parser();
		}
		return infProductInfProductName_4002Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createInfProductInfProductName_4002Parser() {
		DrlModelStructuralFeatureParser parser = new DrlModelStructuralFeatureParser(
				DrlPackage.eINSTANCE.getGenericDocumentPart_Name());
		parser.setViewPattern("{0}");
		parser.setEditPattern("{0}");
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser productLineProductLineName_4003Parser;

	/**
	 * @generated
	 */
	private IParser getProductLineProductLineName_4003Parser() {
		if (productLineProductLineName_4003Parser == null) {
			productLineProductLineName_4003Parser = createProductLineProductLineName_4003Parser();
		}
		return productLineProductLineName_4003Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createProductLineProductLineName_4003Parser() {
		DrlModelStructuralFeatureParser parser = new DrlModelStructuralFeatureParser(
				DrlPackage.eINSTANCE.getProductLine_Name());
		parser.setViewPattern("{0}");
		parser.setEditPattern("{0}");
		return parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case ProductNameEditPart.VISUAL_ID:
			return getProductProductName_4001Parser();
		case InfProductNameEditPart.VISUAL_ID:
			return getInfProductInfProductName_4002Parser();
		case ProductLineNameEditPart.VISUAL_ID:
			return getProductLineProductLineName_4003Parser();
		}
		return null;
	}

	/**
	 * @generated
	 */
	public IParser getParser(IAdaptable hint) {
		String vid = (String) hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(DrlModelVisualIDRegistry.getVisualID(vid));
		}
		View view = (View) hint.getAdapter(View.class);
		if (view != null) {
			return getParser(DrlModelVisualIDRegistry.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (DrlModelElementTypes.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}
}
