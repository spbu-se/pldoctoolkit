package org.spbu.pldoctoolkit.graph.diagram.fproduct.providers;

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
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.InfElementNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.edit.parts.InfProductNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.parsers.MessageFormatParser;
import org.spbu.pldoctoolkit.graph.diagram.fproduct.part.DrlModelVisualIDRegistry;

/**
 * @generated
 */
public class DrlModelParserProvider extends AbstractProvider implements
		IParserProvider {

	/**
	 * @generated
	 */
	private IParser infProductName_4001Parser;

	/**
	 * @generated
	 */
	private IParser getInfProductName_4001Parser() {
		if (infProductName_4001Parser == null) {
			infProductName_4001Parser = createInfProductName_4001Parser();
		}
		return infProductName_4001Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createInfProductName_4001Parser() {
		EAttribute[] features = new EAttribute[] { DrlPackage.eINSTANCE
				.getGenericDocumentPart_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser infElementName_4002Parser;

	/**
	 * @generated
	 */
	private IParser getInfElementName_4002Parser() {
		if (infElementName_4002Parser == null) {
			infElementName_4002Parser = createInfElementName_4002Parser();
		}
		return infElementName_4002Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createInfElementName_4002Parser() {
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
		case InfProductNameEditPart.VISUAL_ID:
			return getInfProductName_4001Parser();
		case InfElementNameEditPart.VISUAL_ID:
			return getInfElementName_4002Parser();
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
