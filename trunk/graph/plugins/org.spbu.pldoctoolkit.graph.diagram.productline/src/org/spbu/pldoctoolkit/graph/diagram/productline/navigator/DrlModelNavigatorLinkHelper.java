package org.spbu.pldoctoolkit.graph.diagram.productline.navigator;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.gef.EditPart;

import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditorInput;

import org.eclipse.gmf.runtime.notation.View;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

import org.eclipse.ui.navigator.ILinkHelper;

/**
 * @generated
 */
public class DrlModelNavigatorLinkHelper implements ILinkHelper {

	/**
	 * @generated
	 */
	public IStructuredSelection findSelection(IEditorInput anInput) {
		return StructuredSelection.EMPTY;
	}

	/**
	 * @generated
	 */
	public void activateEditor(IWorkbenchPage aPage,
			IStructuredSelection aSelection) {
		if (aSelection == null || aSelection.isEmpty()) {
			return;
		}
		if (false == aSelection.getFirstElement() instanceof DrlModelAbstractNavigatorItem) {
			return;
		}

		DrlModelAbstractNavigatorItem navigatorItem = (DrlModelAbstractNavigatorItem) aSelection
				.getFirstElement();
		View navigatorView = null;
		if (navigatorItem instanceof DrlModelNavigatorItem) {
			navigatorView = ((DrlModelNavigatorItem) navigatorItem).getView();
		} else if (navigatorItem instanceof DrlModelNavigatorGroup) {
			DrlModelNavigatorGroup group = (DrlModelNavigatorGroup) navigatorItem;
			if (group.getParent() instanceof DrlModelNavigatorItem) {
				navigatorView = ((DrlModelNavigatorItem) group.getParent())
						.getView();
			} else if (group.getParent() instanceof IAdaptable) {
				navigatorView = (View) ((IAdaptable) group.getParent())
						.getAdapter(View.class);
			}
		}
		if (navigatorView == null) {
			return;
		}
		DiagramEditorInput editorInput = new DiagramEditorInput(navigatorView
				.getDiagram());
		IEditorPart editor = aPage.findEditor(editorInput);
		if (editor == null) {
			return;
		}
		aPage.bringToTop(editor);
		if (editor instanceof DiagramEditor) {
			DiagramEditor diagramEditor = (DiagramEditor) editor;
			Resource diagramResource = diagramEditor.getDiagram().eResource();

			EObject selectedView = diagramResource.getEObject(navigatorView
					.eResource().getURIFragment(navigatorView));
			if (selectedView == null) {
				return;
			}
			EditPart selectedEditPart = (EditPart) diagramEditor
					.getDiagramGraphicalViewer().getEditPartRegistry().get(
							selectedView);
			if (selectedEditPart != null) {
				diagramEditor.getDiagramGraphicalViewer().select(
						selectedEditPart);
			}
		}
	}

}
