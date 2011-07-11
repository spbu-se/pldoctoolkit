package org.spbu.plweb.diagram.providers;

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
import org.spbu.plweb.PlwebPackage;
import org.spbu.plweb.diagram.edit.parts.AreaEditPart;
import org.spbu.plweb.diagram.edit.parts.DiagramRootEditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic2EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic3EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic4EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopic5EditPart;
import org.spbu.plweb.diagram.edit.parts.DocTopicEditPart;
import org.spbu.plweb.diagram.edit.parts.GroupEditPart;
import org.spbu.plweb.diagram.edit.parts.NodeEditPart;
import org.spbu.plweb.diagram.edit.parts.PageEditPart;
import org.spbu.plweb.diagram.edit.parts.RootEditPart;
import org.spbu.plweb.diagram.edit.parts.SiteViewEditPart;
import org.spbu.plweb.diagram.edit.parts.SourceRefElementClassEditPart;
import org.spbu.plweb.diagram.part.PlwebDiagramEditorPlugin;

/**
 * @generated
 */
public class PlwebElementTypes {

	/**
	 * @generated
	 */
	private PlwebElementTypes() {
	}

	/**
	 * @generated
	 */
	private static Map<IElementType, ENamedElement> elements;

	/**
	 * @generated
	 */
	private static ImageRegistry imageRegistry;

	/**
	 * @generated
	 */
	private static Set<IElementType> KNOWN_ELEMENT_TYPES;

	/**
	 * @generated
	 */
	public static final IElementType DiagramRoot_1000 = getElementType("org.spbu.plweb.diagram.DiagramRoot_1000"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Root_2001 = getElementType("org.spbu.plweb.diagram.Root_2001"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType SiteView_2002 = getElementType("org.spbu.plweb.diagram.SiteView_2002"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Area_2003 = getElementType("org.spbu.plweb.diagram.Area_2003"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Page_2004 = getElementType("org.spbu.plweb.diagram.Page_2004"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Group_2005 = getElementType("org.spbu.plweb.diagram.Group_2005"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Node_2006 = getElementType("org.spbu.plweb.diagram.Node_2006"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DocTopic_3005 = getElementType("org.spbu.plweb.diagram.DocTopic_3005"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType DocTopic_3006 = getElementType("org.spbu.plweb.diagram.DocTopic_3006"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType DocTopic_3007 = getElementType("org.spbu.plweb.diagram.DocTopic_3007"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType DocTopic_3008 = getElementType("org.spbu.plweb.diagram.DocTopic_3008"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType DocTopic_3009 = getElementType("org.spbu.plweb.diagram.DocTopic_3009"); //$NON-NLS-1$

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
			elements = new IdentityHashMap<IElementType, ENamedElement>();

			elements.put(DiagramRoot_1000,
					PlwebPackage.eINSTANCE.getDiagramRoot());

			elements.put(Root_2001, PlwebPackage.eINSTANCE.getRoot());

			elements.put(SiteView_2002, PlwebPackage.eINSTANCE.getSiteView());

			elements.put(Area_2003, PlwebPackage.eINSTANCE.getArea());

			elements.put(Page_2004, PlwebPackage.eINSTANCE.getPage());

			elements.put(Group_2005, PlwebPackage.eINSTANCE.getGroup());

			elements.put(Node_2006, PlwebPackage.eINSTANCE.getNode());

			elements.put(DocTopic_3005, PlwebPackage.eINSTANCE.getDocTopic());

			elements.put(DocTopic_3006, PlwebPackage.eINSTANCE.getDocTopic());

			elements.put(DocTopic_3007, PlwebPackage.eINSTANCE.getDocTopic());

			elements.put(DocTopic_3008, PlwebPackage.eINSTANCE.getDocTopic());

			elements.put(DocTopic_3009, PlwebPackage.eINSTANCE.getDocTopic());

			elements.put(SourceRefElementClass_4001,
					PlwebPackage.eINSTANCE.getSourceRefElement_Class());
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
			KNOWN_ELEMENT_TYPES = new HashSet<IElementType>();
			KNOWN_ELEMENT_TYPES.add(DiagramRoot_1000);
			KNOWN_ELEMENT_TYPES.add(Root_2001);
			KNOWN_ELEMENT_TYPES.add(SiteView_2002);
			KNOWN_ELEMENT_TYPES.add(Area_2003);
			KNOWN_ELEMENT_TYPES.add(Page_2004);
			KNOWN_ELEMENT_TYPES.add(Group_2005);
			KNOWN_ELEMENT_TYPES.add(Node_2006);
			KNOWN_ELEMENT_TYPES.add(DocTopic_3005);
			KNOWN_ELEMENT_TYPES.add(DocTopic_3006);
			KNOWN_ELEMENT_TYPES.add(DocTopic_3007);
			KNOWN_ELEMENT_TYPES.add(DocTopic_3008);
			KNOWN_ELEMENT_TYPES.add(DocTopic_3009);
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
		case RootEditPart.VISUAL_ID:
			return Root_2001;
		case SiteViewEditPart.VISUAL_ID:
			return SiteView_2002;
		case AreaEditPart.VISUAL_ID:
			return Area_2003;
		case PageEditPart.VISUAL_ID:
			return Page_2004;
		case GroupEditPart.VISUAL_ID:
			return Group_2005;
		case NodeEditPart.VISUAL_ID:
			return Node_2006;
		case DocTopicEditPart.VISUAL_ID:
			return DocTopic_3005;
		case DocTopic2EditPart.VISUAL_ID:
			return DocTopic_3006;
		case DocTopic3EditPart.VISUAL_ID:
			return DocTopic_3007;
		case DocTopic4EditPart.VISUAL_ID:
			return DocTopic_3008;
		case DocTopic5EditPart.VISUAL_ID:
			return DocTopic_3009;
		case SourceRefElementClassEditPart.VISUAL_ID:
			return SourceRefElementClass_4001;
		}
		return null;
	}

}
