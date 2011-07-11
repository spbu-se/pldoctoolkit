package org.spbu.plweb.diagram.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.spbu.plweb.diagram.edit.parts.AreaEditPart;
import org.spbu.plweb.diagram.edit.parts.DiagramRootEditPart;
import org.spbu.plweb.diagram.edit.parts.GroupEditPart;
import org.spbu.plweb.diagram.edit.parts.NodeEditPart;
import org.spbu.plweb.diagram.edit.parts.PageEditPart;
import org.spbu.plweb.diagram.edit.parts.RootEditPart;
import org.spbu.plweb.diagram.edit.parts.SiteViewEditPart;
import org.spbu.plweb.diagram.part.Messages;
import org.spbu.plweb.diagram.part.PlwebDiagramEditorPlugin;

/**
 * @generated
 */
public class PlwebModelingAssistantProvider extends ModelingAssistantProvider {

	/**
	 * @generated
	 */
	public List getTypesForPopupBar(IAdaptable host) {
		IGraphicalEditPart editPart = (IGraphicalEditPart) host
				.getAdapter(IGraphicalEditPart.class);
		if (editPart instanceof DiagramRootEditPart) {
			ArrayList<IElementType> types = new ArrayList<IElementType>(6);
			types.add(PlwebElementTypes.Root_2001);
			types.add(PlwebElementTypes.SiteView_2002);
			types.add(PlwebElementTypes.Area_2003);
			types.add(PlwebElementTypes.Page_2004);
			types.add(PlwebElementTypes.Group_2005);
			types.add(PlwebElementTypes.Node_2006);
			return types;
		}
		if (editPart instanceof RootEditPart) {
			ArrayList<IElementType> types = new ArrayList<IElementType>(1);
			types.add(PlwebElementTypes.DocTopic_3005);
			return types;
		}
		if (editPart instanceof SiteViewEditPart) {
			ArrayList<IElementType> types = new ArrayList<IElementType>(1);
			types.add(PlwebElementTypes.DocTopic_3006);
			return types;
		}
		if (editPart instanceof AreaEditPart) {
			ArrayList<IElementType> types = new ArrayList<IElementType>(1);
			types.add(PlwebElementTypes.DocTopic_3007);
			return types;
		}
		if (editPart instanceof PageEditPart) {
			ArrayList<IElementType> types = new ArrayList<IElementType>(1);
			types.add(PlwebElementTypes.DocTopic_3008);
			return types;
		}
		if (editPart instanceof NodeEditPart) {
			ArrayList<IElementType> types = new ArrayList<IElementType>(1);
			types.add(PlwebElementTypes.DocTopic_3009);
			return types;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getRelTypesOnSource(IAdaptable source) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
				.getAdapter(IGraphicalEditPart.class);
		if (sourceEditPart instanceof RootEditPart) {
			return ((RootEditPart) sourceEditPart).getMARelTypesOnSource();
		}
		if (sourceEditPart instanceof SiteViewEditPart) {
			return ((SiteViewEditPart) sourceEditPart).getMARelTypesOnSource();
		}
		if (sourceEditPart instanceof AreaEditPart) {
			return ((AreaEditPart) sourceEditPart).getMARelTypesOnSource();
		}
		if (sourceEditPart instanceof GroupEditPart) {
			return ((GroupEditPart) sourceEditPart).getMARelTypesOnSource();
		}
		if (sourceEditPart instanceof NodeEditPart) {
			return ((NodeEditPart) sourceEditPart).getMARelTypesOnSource();
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getRelTypesOnTarget(IAdaptable target) {
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
				.getAdapter(IGraphicalEditPart.class);
		if (targetEditPart instanceof SiteViewEditPart) {
			return ((SiteViewEditPart) targetEditPart).getMARelTypesOnTarget();
		}
		if (targetEditPart instanceof AreaEditPart) {
			return ((AreaEditPart) targetEditPart).getMARelTypesOnTarget();
		}
		if (targetEditPart instanceof PageEditPart) {
			return ((PageEditPart) targetEditPart).getMARelTypesOnTarget();
		}
		if (targetEditPart instanceof GroupEditPart) {
			return ((GroupEditPart) targetEditPart).getMARelTypesOnTarget();
		}
		if (targetEditPart instanceof NodeEditPart) {
			return ((NodeEditPart) targetEditPart).getMARelTypesOnTarget();
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getRelTypesOnSourceAndTarget(IAdaptable source,
			IAdaptable target) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
				.getAdapter(IGraphicalEditPart.class);
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
				.getAdapter(IGraphicalEditPart.class);
		if (sourceEditPart instanceof RootEditPart) {
			return ((RootEditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		if (sourceEditPart instanceof SiteViewEditPart) {
			return ((SiteViewEditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		if (sourceEditPart instanceof AreaEditPart) {
			return ((AreaEditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		if (sourceEditPart instanceof GroupEditPart) {
			return ((GroupEditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		if (sourceEditPart instanceof NodeEditPart) {
			return ((NodeEditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getTypesForSource(IAdaptable target,
			IElementType relationshipType) {
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
				.getAdapter(IGraphicalEditPart.class);
		if (targetEditPart instanceof SiteViewEditPart) {
			return ((SiteViewEditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		if (targetEditPart instanceof AreaEditPart) {
			return ((AreaEditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		if (targetEditPart instanceof PageEditPart) {
			return ((PageEditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		if (targetEditPart instanceof GroupEditPart) {
			return ((GroupEditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		if (targetEditPart instanceof NodeEditPart) {
			return ((NodeEditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getTypesForTarget(IAdaptable source,
			IElementType relationshipType) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
				.getAdapter(IGraphicalEditPart.class);
		if (sourceEditPart instanceof RootEditPart) {
			return ((RootEditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		if (sourceEditPart instanceof SiteViewEditPart) {
			return ((SiteViewEditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		if (sourceEditPart instanceof AreaEditPart) {
			return ((AreaEditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		if (sourceEditPart instanceof GroupEditPart) {
			return ((GroupEditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		if (sourceEditPart instanceof NodeEditPart) {
			return ((NodeEditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public EObject selectExistingElementForSource(IAdaptable target,
			IElementType relationshipType) {
		return selectExistingElement(target,
				getTypesForSource(target, relationshipType));
	}

	/**
	 * @generated
	 */
	public EObject selectExistingElementForTarget(IAdaptable source,
			IElementType relationshipType) {
		return selectExistingElement(source,
				getTypesForTarget(source, relationshipType));
	}

	/**
	 * @generated
	 */
	protected EObject selectExistingElement(IAdaptable host, Collection types) {
		if (types.isEmpty()) {
			return null;
		}
		IGraphicalEditPart editPart = (IGraphicalEditPart) host
				.getAdapter(IGraphicalEditPart.class);
		if (editPart == null) {
			return null;
		}
		Diagram diagram = (Diagram) editPart.getRoot().getContents().getModel();
		HashSet<EObject> elements = new HashSet<EObject>();
		for (Iterator<EObject> it = diagram.getElement().eAllContents(); it
				.hasNext();) {
			EObject element = it.next();
			if (isApplicableElement(element, types)) {
				elements.add(element);
			}
		}
		if (elements.isEmpty()) {
			return null;
		}
		return selectElement((EObject[]) elements.toArray(new EObject[elements
				.size()]));
	}

	/**
	 * @generated
	 */
	protected boolean isApplicableElement(EObject element, Collection types) {
		IElementType type = ElementTypeRegistry.getInstance().getElementType(
				element);
		return types.contains(type);
	}

	/**
	 * @generated
	 */
	protected EObject selectElement(EObject[] elements) {
		Shell shell = Display.getCurrent().getActiveShell();
		ILabelProvider labelProvider = new AdapterFactoryLabelProvider(
				PlwebDiagramEditorPlugin.getInstance()
						.getItemProvidersAdapterFactory());
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				shell, labelProvider);
		dialog.setMessage(Messages.PlwebModelingAssistantProviderMessage);
		dialog.setTitle(Messages.PlwebModelingAssistantProviderTitle);
		dialog.setMultipleSelection(false);
		dialog.setElements(elements);
		EObject selected = null;
		if (dialog.open() == Window.OK) {
			selected = (EObject) dialog.getFirstResult();
		}
		return selected;
	}
}
