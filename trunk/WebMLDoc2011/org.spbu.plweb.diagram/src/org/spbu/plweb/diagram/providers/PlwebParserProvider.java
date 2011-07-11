package org.spbu.plweb.diagram.providers;

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
import org.spbu.plweb.PlwebPackage;
import org.spbu.plweb.diagram.edit.parts.AreaTitleEditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicName2EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicName3EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicName4EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicName5EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicDocTopicNameEditPart;
import org.spbu.plweb.diagram.edit.parts.NodeTitleEditPart;
import org.spbu.plweb.diagram.edit.parts.PageTitleEditPart;
import org.spbu.plweb.diagram.edit.parts.SiteViewTitleEditPart;
import org.spbu.plweb.diagram.parsers.MessageFormatParser;
import org.spbu.plweb.diagram.part.PlwebVisualIDRegistry;

/**
 * @generated
 */
public class PlwebParserProvider extends AbstractProvider implements
		IParserProvider {

	/**
	 * @generated
	 */
	private IParser siteViewTitle_5001Parser;

	/**
	 * @generated
	 */
	private IParser getSiteViewTitle_5001Parser() {
		if (siteViewTitle_5001Parser == null) {
			EAttribute[] features = new EAttribute[] { PlwebPackage.eINSTANCE
					.getElement_Title() };
			MessageFormatParser parser = new MessageFormatParser(features);
			siteViewTitle_5001Parser = parser;
		}
		return siteViewTitle_5001Parser;
	}

	/**
	 * @generated
	 */
	private IParser areaTitle_5002Parser;

	/**
	 * @generated
	 */
	private IParser getAreaTitle_5002Parser() {
		if (areaTitle_5002Parser == null) {
			EAttribute[] features = new EAttribute[] { PlwebPackage.eINSTANCE
					.getElement_Title() };
			MessageFormatParser parser = new MessageFormatParser(features);
			areaTitle_5002Parser = parser;
		}
		return areaTitle_5002Parser;
	}

	/**
	 * @generated
	 */
	private IParser pageTitle_5003Parser;

	/**
	 * @generated
	 */
	private IParser getPageTitle_5003Parser() {
		if (pageTitle_5003Parser == null) {
			EAttribute[] features = new EAttribute[] { PlwebPackage.eINSTANCE
					.getElement_Title() };
			MessageFormatParser parser = new MessageFormatParser(features);
			pageTitle_5003Parser = parser;
		}
		return pageTitle_5003Parser;
	}

	/**
	 * @generated
	 */
	private IParser nodeTitle_5004Parser;

	/**
	 * @generated
	 */
	private IParser getNodeTitle_5004Parser() {
		if (nodeTitle_5004Parser == null) {
			EAttribute[] features = new EAttribute[] { PlwebPackage.eINSTANCE
					.getElement_Title() };
			MessageFormatParser parser = new MessageFormatParser(features);
			nodeTitle_5004Parser = parser;
		}
		return nodeTitle_5004Parser;
	}

	/**
	 * @generated
	 */
	private IParser docTopicDocTopicName_5013Parser;

	/**
	 * @generated
	 */
	private IParser getDocTopicDocTopicName_5013Parser() {
		if (docTopicDocTopicName_5013Parser == null) {
			EAttribute[] features = new EAttribute[] { PlwebPackage.eINSTANCE
					.getDocTopic_DocTopicName() };
			MessageFormatParser parser = new MessageFormatParser(features);
			docTopicDocTopicName_5013Parser = parser;
		}
		return docTopicDocTopicName_5013Parser;
	}

	/**
	 * @generated
	 */
	private IParser docTopicDocTopicName_5009Parser;

	/**
	 * @generated
	 */
	private IParser getDocTopicDocTopicName_5009Parser() {
		if (docTopicDocTopicName_5009Parser == null) {
			EAttribute[] features = new EAttribute[] { PlwebPackage.eINSTANCE
					.getDocTopic_DocTopicName() };
			MessageFormatParser parser = new MessageFormatParser(features);
			docTopicDocTopicName_5009Parser = parser;
		}
		return docTopicDocTopicName_5009Parser;
	}

	/**
	 * @generated
	 */
	private IParser docTopicDocTopicName_5010Parser;

	/**
	 * @generated
	 */
	private IParser getDocTopicDocTopicName_5010Parser() {
		if (docTopicDocTopicName_5010Parser == null) {
			EAttribute[] features = new EAttribute[] { PlwebPackage.eINSTANCE
					.getDocTopic_DocTopicName() };
			MessageFormatParser parser = new MessageFormatParser(features);
			docTopicDocTopicName_5010Parser = parser;
		}
		return docTopicDocTopicName_5010Parser;
	}

	/**
	 * @generated
	 */
	private IParser docTopicDocTopicName_5011Parser;

	/**
	 * @generated
	 */
	private IParser getDocTopicDocTopicName_5011Parser() {
		if (docTopicDocTopicName_5011Parser == null) {
			EAttribute[] features = new EAttribute[] { PlwebPackage.eINSTANCE
					.getDocTopic_DocTopicName() };
			MessageFormatParser parser = new MessageFormatParser(features);
			docTopicDocTopicName_5011Parser = parser;
		}
		return docTopicDocTopicName_5011Parser;
	}

	/**
	 * @generated
	 */
	private IParser docTopicDocTopicName_5012Parser;

	/**
	 * @generated
	 */
	private IParser getDocTopicDocTopicName_5012Parser() {
		if (docTopicDocTopicName_5012Parser == null) {
			EAttribute[] features = new EAttribute[] { PlwebPackage.eINSTANCE
					.getDocTopic_DocTopicName() };
			MessageFormatParser parser = new MessageFormatParser(features);
			docTopicDocTopicName_5012Parser = parser;
		}
		return docTopicDocTopicName_5012Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case SiteViewTitleEditPart.VISUAL_ID:
			return getSiteViewTitle_5001Parser();
		case AreaTitleEditPart.VISUAL_ID:
			return getAreaTitle_5002Parser();
		case PageTitleEditPart.VISUAL_ID:
			return getPageTitle_5003Parser();
		case NodeTitleEditPart.VISUAL_ID:
			return getNodeTitle_5004Parser();
		case DocTopicDocTopicName5EditPart.VISUAL_ID:
			return getDocTopicDocTopicName_5013Parser();
		case DocTopicDocTopicNameEditPart.VISUAL_ID:
			return getDocTopicDocTopicName_5009Parser();
		case DocTopicDocTopicName2EditPart.VISUAL_ID:
			return getDocTopicDocTopicName_5010Parser();
		case DocTopicDocTopicName3EditPart.VISUAL_ID:
			return getDocTopicDocTopicName_5011Parser();
		case DocTopicDocTopicName4EditPart.VISUAL_ID:
			return getDocTopicDocTopicName_5012Parser();
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
			return getParser(PlwebVisualIDRegistry.getVisualID(vid));
		}
		View view = (View) hint.getAdapter(View.class);
		if (view != null) {
			return getParser(PlwebVisualIDRegistry.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (PlwebElementTypes.getElement(hint) == null) {
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
