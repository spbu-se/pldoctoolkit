/*
 * 
 */
package webml.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;

import webml.WebmlPackage;
import webml.diagram.edit.parts.AreaName2EditPart;
import webml.diagram.edit.parts.AreaNameEditPart;
import webml.diagram.edit.parts.ContentUnitName2EditPart;
import webml.diagram.edit.parts.ContentUnitNameEditPart;
import webml.diagram.edit.parts.DocTopicNameEditPart;
import webml.diagram.edit.parts.OperationUnitName2EditPart;
import webml.diagram.edit.parts.OperationUnitNameEditPart;
import webml.diagram.edit.parts.PageName2EditPart;
import webml.diagram.edit.parts.PageNameEditPart;
import webml.diagram.parsers.MessageFormatParser;
import webml.diagram.part.WebmlVisualIDRegistry;

/**
 * @generated
 */
public class WebmlParserProvider extends AbstractProvider implements
		IParserProvider {

	/**
	 * @generated
	 */
	private IParser areaName_5005Parser;

	/**
	 * @generated
	 */
	private IParser getAreaName_5005Parser() {
		if (areaName_5005Parser == null) {
			EAttribute[] features = new EAttribute[] { WebmlPackage.eINSTANCE
					.getUnit_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			areaName_5005Parser = parser;
		}
		return areaName_5005Parser;
	}

	/**
	 * @generated
	 */
	private IParser pageName_5006Parser;

	/**
	 * @generated
	 */
	private IParser getPageName_5006Parser() {
		if (pageName_5006Parser == null) {
			EAttribute[] features = new EAttribute[] { WebmlPackage.eINSTANCE
					.getUnit_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			pageName_5006Parser = parser;
		}
		return pageName_5006Parser;
	}

	/**
	 * @generated
	 */
	private IParser contentUnitName_5007Parser;

	/**
	 * @generated
	 */
	private IParser getContentUnitName_5007Parser() {
		if (contentUnitName_5007Parser == null) {
			EAttribute[] features = new EAttribute[] { WebmlPackage.eINSTANCE
					.getUnit_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			contentUnitName_5007Parser = parser;
		}
		return contentUnitName_5007Parser;
	}

	/**
	 * @generated
	 */
	private IParser operationUnitName_5008Parser;

	/**
	 * @generated
	 */
	private IParser getOperationUnitName_5008Parser() {
		if (operationUnitName_5008Parser == null) {
			EAttribute[] features = new EAttribute[] { WebmlPackage.eINSTANCE
					.getUnit_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			operationUnitName_5008Parser = parser;
		}
		return operationUnitName_5008Parser;
	}

	/**
	 * @generated
	 */
	private IParser areaName_5009Parser;

	/**
	 * @generated
	 */
	private IParser getAreaName_5009Parser() {
		if (areaName_5009Parser == null) {
			EAttribute[] features = new EAttribute[] { WebmlPackage.eINSTANCE
					.getUnit_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			areaName_5009Parser = parser;
		}
		return areaName_5009Parser;
	}

	/**
	 * @generated
	 */
	private IParser pageName_5003Parser;

	/**
	 * @generated
	 */
	private IParser getPageName_5003Parser() {
		if (pageName_5003Parser == null) {
			EAttribute[] features = new EAttribute[] { WebmlPackage.eINSTANCE
					.getUnit_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			pageName_5003Parser = parser;
		}
		return pageName_5003Parser;
	}

	/**
	 * @generated
	 */
	private IParser contentUnitName_5002Parser;

	/**
	 * @generated
	 */
	private IParser getContentUnitName_5002Parser() {
		if (contentUnitName_5002Parser == null) {
			EAttribute[] features = new EAttribute[] { WebmlPackage.eINSTANCE
					.getUnit_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			contentUnitName_5002Parser = parser;
		}
		return contentUnitName_5002Parser;
	}

	/**
	 * @generated
	 */
	private IParser docTopicName_5001Parser;

	/**
	 * @generated
	 */
	private IParser getDocTopicName_5001Parser() {
		if (docTopicName_5001Parser == null) {
			EAttribute[] features = new EAttribute[] { WebmlPackage.eINSTANCE
					.getDocTopic_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			docTopicName_5001Parser = parser;
		}
		return docTopicName_5001Parser;
	}

	/**
	 * @generated
	 */
	private IParser operationUnitName_5004Parser;

	/**
	 * @generated
	 */
	private IParser getOperationUnitName_5004Parser() {
		if (operationUnitName_5004Parser == null) {
			EAttribute[] features = new EAttribute[] { WebmlPackage.eINSTANCE
					.getUnit_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			operationUnitName_5004Parser = parser;
		}
		return operationUnitName_5004Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case AreaNameEditPart.VISUAL_ID:
			return getAreaName_5005Parser();
		case PageNameEditPart.VISUAL_ID:
			return getPageName_5006Parser();
		case ContentUnitNameEditPart.VISUAL_ID:
			return getContentUnitName_5007Parser();
		case OperationUnitNameEditPart.VISUAL_ID:
			return getOperationUnitName_5008Parser();
		case AreaName2EditPart.VISUAL_ID:
			return getAreaName_5009Parser();
		case PageName2EditPart.VISUAL_ID:
			return getPageName_5003Parser();
		case ContentUnitName2EditPart.VISUAL_ID:
			return getContentUnitName_5002Parser();
		case DocTopicNameEditPart.VISUAL_ID:
			return getDocTopicName_5001Parser();
		case OperationUnitName2EditPart.VISUAL_ID:
			return getOperationUnitName_5004Parser();
		}
		return null;
	}

	/**
	 * Utility method that consults ParserService
	 * @generated
	 */
	public static IParser getParser(IElementType type, EObject object,
			String parserHint) {
		return ParserService.getInstance().getParser(
				new HintAdapter(type, object, parserHint));
	}

	/**
	 * @generated
	 */
	public IParser getParser(IAdaptable hint) {
		String vid = (String) hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(WebmlVisualIDRegistry.getVisualID(vid));
		}
		View view = (View) hint.getAdapter(View.class);
		if (view != null) {
			return getParser(WebmlVisualIDRegistry.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (WebmlElementTypes.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static class HintAdapter extends ParserHintAdapter {

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
