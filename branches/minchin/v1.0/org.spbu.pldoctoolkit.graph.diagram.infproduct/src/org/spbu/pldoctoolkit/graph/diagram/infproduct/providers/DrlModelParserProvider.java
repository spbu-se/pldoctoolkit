package org.spbu.pldoctoolkit.graph.diagram.infproduct.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElementNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfProductNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.part.DrlModelVisualIDRegistry;

/**
 * @generated
 */
public class DrlModelParserProvider extends AbstractProvider implements
		IParserProvider {

	/**
	 * @generated
	 */
	private IParser infElementInfElementName_4001Parser;

	/**
	 * @generated
	 */
	private IParser getInfElementInfElementName_4001Parser() {
		if (infElementInfElementName_4001Parser == null) {
			infElementInfElementName_4001Parser = createInfElementInfElementName_4001Parser();
		}
		return infElementInfElementName_4001Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createInfElementInfElementName_4001Parser() {
		DrlModelStructuralFeatureParser parser = new DrlModelStructuralFeatureParser(
				DrlPackage.eINSTANCE.getGenericDocumentPart_Name());
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
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case InfElementNameEditPart.VISUAL_ID:
			return getInfElementInfElementName_4001Parser();
		case InfProductNameEditPart.VISUAL_ID:
			return getInfProductInfProductName_4002Parser();
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
