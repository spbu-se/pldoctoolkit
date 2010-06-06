/*
 * 
 */
package webml.diagram.providers;

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

import webml.diagram.edit.parts.Area2EditPart;
import webml.diagram.edit.parts.AreaAreaElementCompartment2EditPart;
import webml.diagram.edit.parts.AreaAreaElementCompartmentEditPart;
import webml.diagram.edit.parts.AreaEditPart;
import webml.diagram.edit.parts.ContentUnit2EditPart;
import webml.diagram.edit.parts.ContentUnitEditPart;
import webml.diagram.edit.parts.OperationUnit2EditPart;
import webml.diagram.edit.parts.OperationUnitEditPart;
import webml.diagram.edit.parts.Page2EditPart;
import webml.diagram.edit.parts.PageEditPart;
import webml.diagram.edit.parts.PagePageElementCompartment2EditPart;
import webml.diagram.edit.parts.PagePageElementCompartmentEditPart;
import webml.diagram.edit.parts.SiteviewEditPart;
import webml.diagram.part.Messages;
import webml.diagram.part.WebmlDiagramEditorPlugin;

/**
 * @generated
 */
public class WebmlModelingAssistantProvider extends ModelingAssistantProvider {

	/**
	 * @generated
	 */
	public List getTypesForPopupBar(IAdaptable host) {
		IGraphicalEditPart editPart = (IGraphicalEditPart) host
				.getAdapter(IGraphicalEditPart.class);
		if (editPart instanceof AreaEditPart) {
			ArrayList types = new ArrayList(1);
			types.add(WebmlElementTypes.DocTopic_3003);
			return types;
		}
		if (editPart instanceof PageEditPart) {
			ArrayList types = new ArrayList(1);
			types.add(WebmlElementTypes.DocTopic_3003);
			return types;
		}
		if (editPart instanceof ContentUnitEditPart) {
			ArrayList types = new ArrayList(1);
			types.add(WebmlElementTypes.DocTopic_3003);
			return types;
		}
		if (editPart instanceof Area2EditPart) {
			ArrayList types = new ArrayList(1);
			types.add(WebmlElementTypes.DocTopic_3003);
			return types;
		}
		if (editPart instanceof Page2EditPart) {
			ArrayList types = new ArrayList(1);
			types.add(WebmlElementTypes.DocTopic_3003);
			return types;
		}
		if (editPart instanceof ContentUnit2EditPart) {
			ArrayList types = new ArrayList(1);
			types.add(WebmlElementTypes.DocTopic_3003);
			return types;
		}
		if (editPart instanceof AreaAreaElementCompartmentEditPart) {
			ArrayList types = new ArrayList(4);
			types.add(WebmlElementTypes.Area_3005);
			types.add(WebmlElementTypes.Page_3001);
			types.add(WebmlElementTypes.ContentUnit_3002);
			types.add(WebmlElementTypes.OperationUnit_3004);
			return types;
		}
		if (editPart instanceof AreaAreaElementCompartment2EditPart) {
			ArrayList types = new ArrayList(4);
			types.add(WebmlElementTypes.Area_3005);
			types.add(WebmlElementTypes.Page_3001);
			types.add(WebmlElementTypes.ContentUnit_3002);
			types.add(WebmlElementTypes.OperationUnit_3004);
			return types;
		}
		if (editPart instanceof PagePageElementCompartmentEditPart) {
			ArrayList types = new ArrayList(2);
			types.add(WebmlElementTypes.Page_3001);
			types.add(WebmlElementTypes.ContentUnit_3002);
			return types;
		}
		if (editPart instanceof PagePageElementCompartment2EditPart) {
			ArrayList types = new ArrayList(2);
			types.add(WebmlElementTypes.Page_3001);
			types.add(WebmlElementTypes.ContentUnit_3002);
			return types;
		}
		if (editPart instanceof SiteviewEditPart) {
			ArrayList types = new ArrayList(4);
			types.add(WebmlElementTypes.Area_2001);
			types.add(WebmlElementTypes.Page_2002);
			types.add(WebmlElementTypes.ContentUnit_2003);
			types.add(WebmlElementTypes.OperationUnit_2004);
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
		if (sourceEditPart instanceof AreaEditPart) {
			return ((AreaEditPart) sourceEditPart).getMARelTypesOnSource();
		}
		if (sourceEditPart instanceof PageEditPart) {
			return ((PageEditPart) sourceEditPart).getMARelTypesOnSource();
		}
		if (sourceEditPart instanceof ContentUnitEditPart) {
			return ((ContentUnitEditPart) sourceEditPart)
					.getMARelTypesOnSource();
		}
		if (sourceEditPart instanceof OperationUnitEditPart) {
			return ((OperationUnitEditPart) sourceEditPart)
					.getMARelTypesOnSource();
		}
		if (sourceEditPart instanceof Area2EditPart) {
			return ((Area2EditPart) sourceEditPart).getMARelTypesOnSource();
		}
		if (sourceEditPart instanceof Page2EditPart) {
			return ((Page2EditPart) sourceEditPart).getMARelTypesOnSource();
		}
		if (sourceEditPart instanceof ContentUnit2EditPart) {
			return ((ContentUnit2EditPart) sourceEditPart)
					.getMARelTypesOnSource();
		}
		if (sourceEditPart instanceof OperationUnit2EditPart) {
			return ((OperationUnit2EditPart) sourceEditPart)
					.getMARelTypesOnSource();
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getRelTypesOnTarget(IAdaptable target) {
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
				.getAdapter(IGraphicalEditPart.class);
		if (targetEditPart instanceof AreaEditPart) {
			return ((AreaEditPart) targetEditPart).getMARelTypesOnTarget();
		}
		if (targetEditPart instanceof PageEditPart) {
			return ((PageEditPart) targetEditPart).getMARelTypesOnTarget();
		}
		if (targetEditPart instanceof ContentUnitEditPart) {
			return ((ContentUnitEditPart) targetEditPart)
					.getMARelTypesOnTarget();
		}
		if (targetEditPart instanceof OperationUnitEditPart) {
			return ((OperationUnitEditPart) targetEditPart)
					.getMARelTypesOnTarget();
		}
		if (targetEditPart instanceof Area2EditPart) {
			return ((Area2EditPart) targetEditPart).getMARelTypesOnTarget();
		}
		if (targetEditPart instanceof Page2EditPart) {
			return ((Page2EditPart) targetEditPart).getMARelTypesOnTarget();
		}
		if (targetEditPart instanceof ContentUnit2EditPart) {
			return ((ContentUnit2EditPart) targetEditPart)
					.getMARelTypesOnTarget();
		}
		if (targetEditPart instanceof OperationUnit2EditPart) {
			return ((OperationUnit2EditPart) targetEditPart)
					.getMARelTypesOnTarget();
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
		if (sourceEditPart instanceof AreaEditPart) {
			return ((AreaEditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		if (sourceEditPart instanceof PageEditPart) {
			return ((PageEditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		if (sourceEditPart instanceof ContentUnitEditPart) {
			return ((ContentUnitEditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		if (sourceEditPart instanceof OperationUnitEditPart) {
			return ((OperationUnitEditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		if (sourceEditPart instanceof Area2EditPart) {
			return ((Area2EditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		if (sourceEditPart instanceof Page2EditPart) {
			return ((Page2EditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		if (sourceEditPart instanceof ContentUnit2EditPart) {
			return ((ContentUnit2EditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		if (sourceEditPart instanceof OperationUnit2EditPart) {
			return ((OperationUnit2EditPart) sourceEditPart)
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
		if (targetEditPart instanceof AreaEditPart) {
			return ((AreaEditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		if (targetEditPart instanceof PageEditPart) {
			return ((PageEditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		if (targetEditPart instanceof ContentUnitEditPart) {
			return ((ContentUnitEditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		if (targetEditPart instanceof OperationUnitEditPart) {
			return ((OperationUnitEditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		if (targetEditPart instanceof Area2EditPart) {
			return ((Area2EditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		if (targetEditPart instanceof Page2EditPart) {
			return ((Page2EditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		if (targetEditPart instanceof ContentUnit2EditPart) {
			return ((ContentUnit2EditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		if (targetEditPart instanceof OperationUnit2EditPart) {
			return ((OperationUnit2EditPart) targetEditPart)
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
		if (sourceEditPart instanceof AreaEditPart) {
			return ((AreaEditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		if (sourceEditPart instanceof PageEditPart) {
			return ((PageEditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		if (sourceEditPart instanceof ContentUnitEditPart) {
			return ((ContentUnitEditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		if (sourceEditPart instanceof OperationUnitEditPart) {
			return ((OperationUnitEditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		if (sourceEditPart instanceof Area2EditPart) {
			return ((Area2EditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		if (sourceEditPart instanceof Page2EditPart) {
			return ((Page2EditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		if (sourceEditPart instanceof ContentUnit2EditPart) {
			return ((ContentUnit2EditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		if (sourceEditPart instanceof OperationUnit2EditPart) {
			return ((OperationUnit2EditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public EObject selectExistingElementForSource(IAdaptable target,
			IElementType relationshipType) {
		return selectExistingElement(target, getTypesForSource(target,
				relationshipType));
	}

	/**
	 * @generated
	 */
	public EObject selectExistingElementForTarget(IAdaptable source,
			IElementType relationshipType) {
		return selectExistingElement(source, getTypesForTarget(source,
				relationshipType));
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
		Collection elements = new HashSet();
		for (Iterator it = diagram.getElement().eAllContents(); it.hasNext();) {
			EObject element = (EObject) it.next();
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
				WebmlDiagramEditorPlugin.getInstance()
						.getItemProvidersAdapterFactory());
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				shell, labelProvider);
		dialog.setMessage(Messages.WebmlModelingAssistantProviderMessage);
		dialog.setTitle(Messages.WebmlModelingAssistantProviderTitle);
		dialog.setMultipleSelection(false);
		dialog.setElements(elements);
		EObject selected = null;
		if (dialog.open() == Window.OK) {
			selected = (EObject) dialog.getFirstResult();
		}
		return selected;
	}
}
