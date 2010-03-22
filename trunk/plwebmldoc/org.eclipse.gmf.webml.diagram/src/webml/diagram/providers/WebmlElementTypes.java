/*
 * 
 */
package webml.diagram.providers;

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

import webml.WebmlPackage;
import webml.diagram.edit.parts.AreaEditPart;
import webml.diagram.edit.parts.ContentUnit2EditPart;
import webml.diagram.edit.parts.ContentUnitEditPart;
import webml.diagram.edit.parts.DocTopicEditPart;
import webml.diagram.edit.parts.KoLinkEditPart;
import webml.diagram.edit.parts.NormalLinkEditPart;
import webml.diagram.edit.parts.OkLinkEditPart;
import webml.diagram.edit.parts.OperationUnit2EditPart;
import webml.diagram.edit.parts.OperationUnitEditPart;
import webml.diagram.edit.parts.Page2EditPart;
import webml.diagram.edit.parts.PageEditPart;
import webml.diagram.edit.parts.SiteviewEditPart;
import webml.diagram.edit.parts.TransportLinkEditPart;
import webml.diagram.part.WebmlDiagramEditorPlugin;

/**
 * @generated
 */
public class WebmlElementTypes extends ElementInitializers {

	/**
	 * @generated
	 */
	private WebmlElementTypes() {
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
	public static final IElementType Siteview_1000 = getElementType("org.eclipse.gmf.webml.diagram.Siteview_1000"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Area_2001 = getElementType("org.eclipse.gmf.webml.diagram.Area_2001"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Page_2002 = getElementType("org.eclipse.gmf.webml.diagram.Page_2002"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ContentUnit_2003 = getElementType("org.eclipse.gmf.webml.diagram.ContentUnit_2003"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OperationUnit_2004 = getElementType("org.eclipse.gmf.webml.diagram.OperationUnit_2004"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Page_3001 = getElementType("org.eclipse.gmf.webml.diagram.Page_3001"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ContentUnit_3002 = getElementType("org.eclipse.gmf.webml.diagram.ContentUnit_3002"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DocTopic_3003 = getElementType("org.eclipse.gmf.webml.diagram.DocTopic_3003"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OperationUnit_3004 = getElementType("org.eclipse.gmf.webml.diagram.OperationUnit_3004"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OkLink_4001 = getElementType("org.eclipse.gmf.webml.diagram.OkLink_4001"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType KoLink_4002 = getElementType("org.eclipse.gmf.webml.diagram.KoLink_4002"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType NormalLink_4003 = getElementType("org.eclipse.gmf.webml.diagram.NormalLink_4003"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TransportLink_4004 = getElementType("org.eclipse.gmf.webml.diagram.TransportLink_4004"); //$NON-NLS-1$

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
				return WebmlDiagramEditorPlugin.getInstance()
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

			elements.put(Siteview_1000, WebmlPackage.eINSTANCE.getSiteview());

			elements.put(Area_2001, WebmlPackage.eINSTANCE.getArea());

			elements.put(Page_2002, WebmlPackage.eINSTANCE.getPage());

			elements.put(ContentUnit_2003, WebmlPackage.eINSTANCE
					.getContentUnit());

			elements.put(OperationUnit_2004, WebmlPackage.eINSTANCE
					.getOperationUnit());

			elements.put(Page_3001, WebmlPackage.eINSTANCE.getPage());

			elements.put(ContentUnit_3002, WebmlPackage.eINSTANCE
					.getContentUnit());

			elements.put(DocTopic_3003, WebmlPackage.eINSTANCE.getDocTopic());

			elements.put(OperationUnit_3004, WebmlPackage.eINSTANCE
					.getOperationUnit());

			elements.put(OkLink_4001, WebmlPackage.eINSTANCE.getokLink());

			elements.put(KoLink_4002, WebmlPackage.eINSTANCE.getkoLink());

			elements.put(NormalLink_4003, WebmlPackage.eINSTANCE
					.getnormalLink());

			elements.put(TransportLink_4004, WebmlPackage.eINSTANCE
					.gettransportLink());
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
			KNOWN_ELEMENT_TYPES.add(Siteview_1000);
			KNOWN_ELEMENT_TYPES.add(Area_2001);
			KNOWN_ELEMENT_TYPES.add(Page_2002);
			KNOWN_ELEMENT_TYPES.add(ContentUnit_2003);
			KNOWN_ELEMENT_TYPES.add(OperationUnit_2004);
			KNOWN_ELEMENT_TYPES.add(Page_3001);
			KNOWN_ELEMENT_TYPES.add(ContentUnit_3002);
			KNOWN_ELEMENT_TYPES.add(DocTopic_3003);
			KNOWN_ELEMENT_TYPES.add(OperationUnit_3004);
			KNOWN_ELEMENT_TYPES.add(OkLink_4001);
			KNOWN_ELEMENT_TYPES.add(KoLink_4002);
			KNOWN_ELEMENT_TYPES.add(NormalLink_4003);
			KNOWN_ELEMENT_TYPES.add(TransportLink_4004);
		}
		return KNOWN_ELEMENT_TYPES.contains(elementType);
	}

	/**
	 * @generated
	 */
	public static IElementType getElementType(int visualID) {
		switch (visualID) {
		case SiteviewEditPart.VISUAL_ID:
			return Siteview_1000;
		case AreaEditPart.VISUAL_ID:
			return Area_2001;
		case PageEditPart.VISUAL_ID:
			return Page_2002;
		case ContentUnitEditPart.VISUAL_ID:
			return ContentUnit_2003;
		case OperationUnitEditPart.VISUAL_ID:
			return OperationUnit_2004;
		case Page2EditPart.VISUAL_ID:
			return Page_3001;
		case ContentUnit2EditPart.VISUAL_ID:
			return ContentUnit_3002;
		case DocTopicEditPart.VISUAL_ID:
			return DocTopic_3003;
		case OperationUnit2EditPart.VISUAL_ID:
			return OperationUnit_3004;
		case OkLinkEditPart.VISUAL_ID:
			return OkLink_4001;
		case KoLinkEditPart.VISUAL_ID:
			return KoLink_4002;
		case NormalLinkEditPart.VISUAL_ID:
			return NormalLink_4003;
		case TransportLinkEditPart.VISUAL_ID:
			return TransportLink_4004;
		}
		return null;
	}

}
