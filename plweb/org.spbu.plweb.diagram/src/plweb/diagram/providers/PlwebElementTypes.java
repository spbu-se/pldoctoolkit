package plweb.diagram.providers;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import plweb.PlwebPackage;
import plweb.diagram.edit.parts.AreaEditPart;
import plweb.diagram.edit.parts.DiagramRootEditPart;
import plweb.diagram.edit.parts.GroupEditPart;
import plweb.diagram.edit.parts.NodeEditPart;
import plweb.diagram.edit.parts.PageEditPart;
import plweb.diagram.edit.parts.SourceRefElementClassEditPart;
import plweb.diagram.part.PlwebDiagramEditorPlugin;

/**
 * @generated
 */
public class PlwebElementTypes extends ElementInitializers {

	/**
	 * @generated
	 */
	private PlwebElementTypes() {
	}

	/**
	 * @generated
	 */
	private static Map elements;

	/**
	 * @generated
	 */
	private static ImageRegistry imageRegistry;

	/**
	 * @generated
	 */
	private static Set KNOWN_ELEMENT_TYPES;

	/**
	 * @generated
	 */
	public static final IElementType DiagramRoot_1000 = getElementType("org.spbu.plweb.diagram.DiagramRoot_1000"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Area_2001 = getElementType("org.spbu.plweb.diagram.Area_2001"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Group_2002 = getElementType("org.spbu.plweb.diagram.Group_2002"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Page_2003 = getElementType("org.spbu.plweb.diagram.Page_2003"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Node_2004 = getElementType("org.spbu.plweb.diagram.Node_2004"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType SourceRefElementClass_4001 = getElementType("org.spbu.plweb.diagram.SourceRefElementClass_4001"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	private static ImageRegistry getImageRegistry() {
		if (imageRegistry == null) {
			imageRegistry = new ImageRegistry();
		}
		return imageRegistry;
	}

	/**
	 * @generated
	 */
	private static String getImageRegistryKey(ENamedElement element) {
		return element.getName();
	}

	/**
	 * @generated
	 */
	private static ImageDescriptor getProvidedImageDescriptor(
			ENamedElement element) {
		if (element instanceof EStructuralFeature) {
			EStructuralFeature feature = ((EStructuralFeature) element);
			EClass eContainingClass = feature.getEContainingClass();
			EClassifier eType = feature.getEType();
			if (eContainingClass != null && !eContainingClass.isAbstract()) {
				element = eContainingClass;
			} else if (eType instanceof EClass
					&& !((EClass) eType).isAbstract()) {
				element = eType;
			}
		}
		if (element instanceof EClass) {
			EClass eClass = (EClass) element;
			if (!eClass.isAbstract()) {
				return PlwebDiagramEditorPlugin.getInstance()
						.getItemImageDescriptor(
								eClass.getEPackage().getEFactoryInstance()
										.create(eClass));
			}
		}
		// TODO : support structural features
		return null;
	}

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(ENamedElement element) {
		String key = getImageRegistryKey(element);
		ImageDescriptor imageDescriptor = getImageRegistry().getDescriptor(key);
		if (imageDescriptor == null) {
			imageDescriptor = getProvidedImageDescriptor(element);
			if (imageDescriptor == null) {
				imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
			}
			getImageRegistry().put(key, imageDescriptor);
		}
		return imageDescriptor;
	}

	/**
	 * @generated
	 */
	public static Image getImage(ENamedElement element) {
		String key = getImageRegistryKey(element);
		Image image = getImageRegistry().get(key);
		if (image == null) {
			ImageDescriptor imageDescriptor = getProvidedImageDescriptor(element);
			if (imageDescriptor == null) {
				imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
			}
			getImageRegistry().put(key, imageDescriptor);
			image = getImageRegistry().get(key);
		}
		return image;
	}

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(IAdaptable hint) {
		ENamedElement element = getElement(hint);
		if (element == null) {
			return null;
		}
		return getImageDescriptor(element);
	}

	/**
	 * @generated
	 */
	public static Image getImage(IAdaptable hint) {
		ENamedElement element = getElement(hint);
		if (element == null) {
			return null;
		}
		return getImage(element);
	}

	/**
	 * Returns 'type' of the ecore object associated with the hint.
	 * 
	 * @generated
	 */
	public static ENamedElement getElement(IAdaptable hint) {
		Object type = hint.getAdapter(IElementType.class);
		if (elements == null) {
			elements = new IdentityHashMap();

			elements.put(DiagramRoot_1000, PlwebPackage.eINSTANCE
					.getDiagramRoot());

			elements.put(Area_2001, PlwebPackage.eINSTANCE.getArea());

			elements.put(Group_2002, PlwebPackage.eINSTANCE.getGroup());

			elements.put(Page_2003, PlwebPackage.eINSTANCE.getPage());

			elements.put(Node_2004, PlwebPackage.eINSTANCE.getNode());

			elements.put(SourceRefElementClass_4001, PlwebPackage.eINSTANCE
					.getSourceRefElement_Class());
		}
		return (ENamedElement) elements.get(type);
	}

	/**
	 * @generated
	 */
	private static IElementType getElementType(String id) {
		return ElementTypeRegistry.getInstance().getType(id);
	}

	/**
	 * @generated
	 */
	public static boolean isKnownElementType(IElementType elementType) {
		if (KNOWN_ELEMENT_TYPES == null) {
			KNOWN_ELEMENT_TYPES = new HashSet();
			KNOWN_ELEMENT_TYPES.add(DiagramRoot_1000);
			KNOWN_ELEMENT_TYPES.add(Area_2001);
			KNOWN_ELEMENT_TYPES.add(Group_2002);
			KNOWN_ELEMENT_TYPES.add(Page_2003);
			KNOWN_ELEMENT_TYPES.add(Node_2004);
			KNOWN_ELEMENT_TYPES.add(SourceRefElementClass_4001);
		}
		return KNOWN_ELEMENT_TYPES.contains(elementType);
	}

	/**
	 * @generated
	 */
	public static IElementType getElementType(int visualID) {
		switch (visualID) {
		case DiagramRootEditPart.VISUAL_ID:
			return DiagramRoot_1000;
		case AreaEditPart.VISUAL_ID:
			return Area_2001;
		case GroupEditPart.VISUAL_ID:
			return Group_2002;
		case PageEditPart.VISUAL_ID:
			return Page_2003;
		case NodeEditPart.VISUAL_ID:
			return Node_2004;
		case SourceRefElementClassEditPart.VISUAL_ID:
			return SourceRefElementClass_4001;
		}
		return null;
	}

}
