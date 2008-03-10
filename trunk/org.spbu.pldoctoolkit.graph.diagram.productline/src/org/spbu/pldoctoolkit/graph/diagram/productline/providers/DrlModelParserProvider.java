package org.spbu.pldoctoolkit.graph.diagram.productline.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.InfProductNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductLineNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts.ProductNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.productline.parsers.MessageFormatParser;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelVisualIDRegistry;

/**
 * @generated
 */
public class DrlModelParserProvider extends AbstractProvider implements
		IParserProvider {

	/**
	 * @generated
	 */
	private IParser productLineName_4003Parser;

	/**
	 * @generated
	 */
	private IParser getProductLineName_4003Parser() {
		if (productLineName_4003Parser == null) {
			productLineName_4003Parser = createProductLineName_4003Parser();
		}
		return productLineName_4003Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createProductLineName_4003Parser() {
		EAttribute[] features = new EAttribute[] { DrlPackage.eINSTANCE
				.getProductLine_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser productName_4001Parser;

	/**
	 * @generated
	 */
	private IParser getProductName_4001Parser() {
		if (productName_4001Parser == null) {
			productName_4001Parser = createProductName_4001Parser();
		}
		return productName_4001Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createProductName_4001Parser() {
		EAttribute[] features = new EAttribute[] { DrlPackage.eINSTANCE
				.getProduct_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser infProductName_4002Parser;

	/**
	 * @generated
	 */
	private IParser getInfProductName_4002Parser() {
		if (infProductName_4002Parser == null) {
			infProductName_4002Parser = createInfProductName_4002Parser();
		}
		return infProductName_4002Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createInfProductName_4002Parser() {
		EAttribute[] features = new EAttribute[] { DrlPackage.eINSTANCE
				.getGenericDocumentPart_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case ProductLineNameEditPart.VISUAL_ID:
			return getProductLineName_4003Parser();
		case ProductNameEditPart.VISUAL_ID:
			return getProductName_4001Parser();
		case InfProductNameEditPart.VISUAL_ID:
			return getInfProductName_4002Parser();
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

	/**
	 * @generated
	 */
	public static class HintAdapter extends ParserHintAdapter {

		/**
		 * @generated
		 */
		private final IElementType elementType;

		/**
		 * @generated
		 */
		public HintAdapter(IElementType type, EObject object, String parserHint) {
			super(object, parserHint);
			assert type != null;
			elementType = type;
		}

		/**
		 * @generated
		 */
		public Object getAdapter(Class adapter) {
			if (IElementType.class.equals(adapter)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}
}
