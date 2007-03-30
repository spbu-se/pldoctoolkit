package org.spbu.pldoctoolkit.graph.diagram.infproduct.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.notation.View;
import java.util.ArrayList;
import java.util.List;

import org.spbu.pldoctoolkit.graph.DrlPackage;

import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefGroupModifierIdEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElemRefIdEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElementIdEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfElementNameEditPart;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.parts.InfProductIdEditPart;
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
				DrlPackage.eINSTANCE.getGenericDocumentPart()
						.getEStructuralFeature("name")); //$NON-NLS-1$
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser infElementInfElementId_4002Parser;

	/**
	 * @generated
	 */
	private IParser getInfElementInfElementId_4002Parser() {
		if (infElementInfElementId_4002Parser == null) {
			infElementInfElementId_4002Parser = createInfElementInfElementId_4002Parser();
		}
		return infElementInfElementId_4002Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createInfElementInfElementId_4002Parser() {
		DrlModelStructuralFeatureParser parser = new DrlModelStructuralFeatureParser(
				DrlPackage.eINSTANCE.getGenericDocumentPart()
						.getEStructuralFeature("id")); //$NON-NLS-1$
		parser.setViewPattern("id: {0}");
		parser.setEditPattern("id: {0}");
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser infProductInfProductName_4003Parser;

	/**
	 * @generated
	 */
	private IParser getInfProductInfProductName_4003Parser() {
		if (infProductInfProductName_4003Parser == null) {
			infProductInfProductName_4003Parser = createInfProductInfProductName_4003Parser();
		}
		return infProductInfProductName_4003Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createInfProductInfProductName_4003Parser() {
		DrlModelStructuralFeatureParser parser = new DrlModelStructuralFeatureParser(
				DrlPackage.eINSTANCE.getGenericDocumentPart()
						.getEStructuralFeature("name")); //$NON-NLS-1$
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser infProductInfProductId_4004Parser;

	/**
	 * @generated
	 */
	private IParser getInfProductInfProductId_4004Parser() {
		if (infProductInfProductId_4004Parser == null) {
			infProductInfProductId_4004Parser = createInfProductInfProductId_4004Parser();
		}
		return infProductInfProductId_4004Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createInfProductInfProductId_4004Parser() {
		DrlModelStructuralFeatureParser parser = new DrlModelStructuralFeatureParser(
				DrlPackage.eINSTANCE.getGenericDocumentPart()
						.getEStructuralFeature("id")); //$NON-NLS-1$
		parser.setViewPattern("id: {0}");
		parser.setEditPattern("id: {0}");
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser infElemRefGroupInfElemRefGroupModifierId_4005Parser;

	/**
	 * @generated
	 */
	private IParser getInfElemRefGroupInfElemRefGroupModifierId_4005Parser() {
		if (infElemRefGroupInfElemRefGroupModifierId_4005Parser == null) {
			infElemRefGroupInfElemRefGroupModifierId_4005Parser = createInfElemRefGroupInfElemRefGroupModifierId_4005Parser();
		}
		return infElemRefGroupInfElemRefGroupModifierId_4005Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createInfElemRefGroupInfElemRefGroupModifierId_4005Parser() {
		List features = new ArrayList(2);
		features.add(DrlPackage.eINSTANCE.getInfElemRefGroup()
				.getEStructuralFeature("modifier")); //$NON-NLS-1$
		features.add(DrlPackage.eINSTANCE.getInfElemRefGroup()
				.getEStructuralFeature("id")); //$NON-NLS-1$
		DrlModelStructuralFeaturesParser parser = new DrlModelStructuralFeaturesParser(
				features);
		parser.setViewPattern("{0}");
		parser.setEditPattern("id:{1}");
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser infElemRefInfElemRefId_4006Parser;

	/**
	 * @generated
	 */
	private IParser getInfElemRefInfElemRefId_4006Parser() {
		if (infElemRefInfElemRefId_4006Parser == null) {
			infElemRefInfElemRefId_4006Parser = createInfElemRefInfElemRefId_4006Parser();
		}
		return infElemRefInfElemRefId_4006Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createInfElemRefInfElemRefId_4006Parser() {
		DrlModelStructuralFeatureParser parser = new DrlModelStructuralFeatureParser(
				DrlPackage.eINSTANCE.getInfElemRef()
						.getEStructuralFeature("id")); //$NON-NLS-1$
		return parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case InfElementNameEditPart.VISUAL_ID:
			return getInfElementInfElementName_4001Parser();
		case InfElementIdEditPart.VISUAL_ID:
			return getInfElementInfElementId_4002Parser();
		case InfProductNameEditPart.VISUAL_ID:
			return getInfProductInfProductName_4003Parser();
		case InfProductIdEditPart.VISUAL_ID:
			return getInfProductInfProductId_4004Parser();
		case InfElemRefGroupModifierIdEditPart.VISUAL_ID:
			return getInfElemRefGroupInfElemRefGroupModifierId_4005Parser();
		case InfElemRefIdEditPart.VISUAL_ID:
			return getInfElemRefInfElemRefId_4006Parser();
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
