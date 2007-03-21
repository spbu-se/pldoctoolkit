package org.spbu.pldoctoolkit.drlvisual.diagram.part;

import org.eclipse.draw2d.DelegatingLayout;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.LayeredPane;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.editor.FileDiagramEditor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.core.resources.IFile;

import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;

import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.document.StorageDiagramDocumentProvider;

import org.spbu.pldoctoolkit.drlvisual.diagram.edit.parts.DRLModelEditPartFactory;

/**
 * @generated
 */
public class DRLModelDiagramEditor extends FileDiagramEditor implements
		IGotoMarker {

	/**
	 * @generated
	 */
	public static final String ID = "org.spbu.pldoctoolkit.drlvisual.diagram.part.DRLModelDiagramEditorID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public DRLModelDiagramEditor() {
		super(true);
	}

	/**
	 * @generated
	 */
	protected String getEditingDomainID() {
		return "drldoc.diagram.EditingDomain"; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	protected TransactionalEditingDomain createEditingDomain() {
		TransactionalEditingDomain domain = super.createEditingDomain();
		domain.setID(getEditingDomainID());
		return domain;
	}

	/**
	 * @generated
	 */
	protected void setDocumentProvider(IEditorInput input) {
		if (input.getAdapter(IFile.class) != null) {
			setDocumentProvider(new DRLModelDocumentProvider());
		} else {
			setDocumentProvider(new StorageDiagramDocumentProvider());
		}
	}

	/**
	 * @generated
	 */
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		DiagramRootEditPart root = (DiagramRootEditPart) getDiagramGraphicalViewer()
				.getRootEditPart();
		LayeredPane printableLayers = (LayeredPane) root
				.getLayer(LayerConstants.PRINTABLE_LAYERS);
		FreeformLayer extLabelsLayer = new FreeformLayer();
		extLabelsLayer.setLayoutManager(new DelegatingLayout());
		printableLayers.addLayerAfter(extLabelsLayer,
				DRLModelEditPartFactory.EXTERNAL_NODE_LABELS_LAYER,
				LayerConstants.PRIMARY_LAYER);
		LayeredPane scalableLayers = (LayeredPane) root
				.getLayer(LayerConstants.SCALABLE_LAYERS);
		FreeformLayer scaledFeedbackLayer = new FreeformLayer();
		scaledFeedbackLayer.setEnabled(false);
		scalableLayers.addLayerAfter(scaledFeedbackLayer,
				LayerConstants.SCALED_FEEDBACK_LAYER,
				DiagramRootEditPart.DECORATION_UNPRINTABLE_LAYER);
	}

	/**
	 * @generated
	 */
	protected PreferencesHint getPreferencesHint() {
		return DRLModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
	}
}
