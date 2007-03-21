package org.spbu.pldoctoolkit.drlvisual.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.notation.View;
import java.util.ArrayList;
import java.util.List;

import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElemRefGroupNameEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElemRefId2EditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElemRefIdEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfElementNameIdEditPart;
import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.InfProductNameIdEditPart;

import org.spbu.pldoctoolkit.drlvisual.diagram.part.DRLModelVisualIDRegistry;

import org.spbu.pldoctoolkit.drlvisual.drlPackage;

/**
 * @generated
 */
public class DRLModelParserProvider extends AbstractProvider implements
		IParserProvider {

	/**
	 * @generated
	 */
	private IParser infElementInfElementNameId_4001Parser;

	/**
	 * @generated
	 */
	private IParser getInfElementInfElementNameId_4001Parser() {
		if (infElementInfElementNameId_4001Parser == null) {
			infElementInfElementNameId_4001Parser = createInfElementInfElementNameId_4001Parser();
		}
		return infElementInfElementNameId_4001Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createInfElementInfElementNameId_4001Parser() {
		List features = new ArrayList(2);
		features.add(drlPackage.eINSTANCE.getGenericDocumentPart()
				.getEStructuralFeature("name")); //$NON-NLS-1$
		features.add(drlPackage.eINSTANCE.getGenericDocumentPart()
				.getEStructuralFeature("id")); //$NON-NLS-1$
		DRLModelStructuralFeaturesParser parser = new DRLModelStructuralFeaturesParser(
				features);
		parser.setViewPattern("{1}:{0}");
		parser.setEditPattern("{1}:{0}");
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser infProductInfProductNameId_4002Parser;

	/**
	 * @generated
	 */
	private IParser getInfProductInfProductNameId_4002Parser() {
		if (infProductInfProductNameId_4002Parser == null) {
			infProductInfProductNameId_4002Parser = createInfProductInfProductNameId_4002Parser();
		}
		return infProductInfProductNameId_4002Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createInfProductInfProductNameId_4002Parser() {
		List features = new ArrayList(2);
		features.add(drlPackage.eINSTANCE.getGenericDocumentPart()
				.getEStructuralFeature("name")); //$NON-NLS-1$
		features.add(drlPackage.eINSTANCE.getGenericDocumentPart()
				.getEStructuralFeature("id")); //$NON-NLS-1$
		DRLModelStructuralFeaturesParser parser = new DRLModelStructuralFeaturesParser(
				features);
		parser.setViewPattern("{1}:{0}");
		parser.setEditPattern("{1}:{0}");
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser infElemRefGroupInfElemRefGroupName_4003Parser;

	/**
	 * @generated
	 */
	private IParser getInfElemRefGroupInfElemRefGroupName_4003Parser() {
		if (infElemRefGroupInfElemRefGroupName_4003Parser == null) {
			infElemRefGroupInfElemRefGroupName_4003Parser = createInfElemRefGroupInfElemRefGroupName_4003Parser();
		}
		return infElemRefGroupInfElemRefGroupName_4003Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createInfElemRefGroupInfElemRefGroupName_4003Parser() {
		DRLModelStructuralFeatureParser parser = new DRLModelStructuralFeatureParser(
				drlPackage.eINSTANCE.getInfElemRefGroup()
						.getEStructuralFeature("name")); //$NON-NLS-1$
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser infElemRefInfElemRefId_4004Parser;

	/**
	 * @generated
	 */
	private IParser getInfElemRefInfElemRefId_4004Parser() {
		if (infElemRefInfElemRefId_4004Parser == null) {
			infElemRefInfElemRefId_4004Parser = createInfElemRefInfElemRefId_4004Parser();
		}
		return infElemRefInfElemRefId_4004Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createInfElemRefInfElemRefId_4004Parser() {
		DRLModelStructuralFeatureParser parser = new DRLModelStructuralFeatureParser(
				drlPackage.eINSTANCE.getInfElemRef()
						.getEStructuralFeature("id")); //$NON-NLS-1$
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser infElemRefInfElemRefId_4005Parser;

	/**
	 * @generated
	 */
	private IParser getInfElemRefInfElemRefId_4005Parser() {
		if (infElemRefInfElemRefId_4005Parser == null) {
			infElemRefInfElemRefId_4005Parser = createInfElemRefInfElemRefId_4005Parser();
		}
		return infElemRefInfElemRefId_4005Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createInfElemRefInfElemRefId_4005Parser() {
		DRLModelStructuralFeatureParser parser = new DRLModelStructuralFeatureParser(
				drlPackage.eINSTANCE.getInfElemRef()
						.getEStructuralFeature("id")); //$NON-NLS-1$
		return parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case InfElementNameIdEditPart.VISUAL_ID:
			return getInfElementInfElementNameId_4001Parser();
		case InfProductNameIdEditPart.VISUAL_ID:
			return getInfProductInfProductNameId_4002Parser();
		case InfElemRefGroupNameEditPart.VISUAL_ID:
			return getInfElemRefGroupInfElemRefGroupName_4003Parser();
		case InfElemRefIdEditPart.VISUAL_ID:
			return getInfElemRefInfElemRefId_4004Parser();
		case InfElemRefId2EditPart.VISUAL_ID:
			return getInfElemRefInfElemRefId_4005Parser();
		}
		return null;
	}

	/**
	 * @generated
	 */
	public IParser getParser(IAdaptable hint) {
		String vid = (String) hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(DRLModelVisualIDRegistry.getVisualID(vid));
		}
		View view = (View) hint.getAdapter(View.class);
		if (view != null) {
			return getParser(DRLModelVisualIDRegistry.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (DRLModelElementTypes.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}
}
