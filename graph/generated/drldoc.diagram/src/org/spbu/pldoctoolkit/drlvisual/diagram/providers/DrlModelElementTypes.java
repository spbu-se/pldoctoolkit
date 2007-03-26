package org.spbu.pldoctoolkit.drlvisual.diagram.providers;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.emf.ecore.EObject;

import org.spbu.pldoctoolkit.drlvisual.DrlPackage;

import org.spbu.pldoctoolkit.drlvisual.diagram.expressions.DrlModelAbstractExpression;
import org.spbu.pldoctoolkit.drlvisual.diagram.expressions.DrlModelOCLFactory;

import org.spbu.pldoctoolkit.drlvisual.diagram.part.DrlModelDiagramEditorPlugin;

/**
 * @generated
 */
public class DrlModelElementTypes {

	/**
	 * @generated
	 */
	private DrlModelElementTypes() {
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
			element = ((EStructuralFeature) element).getEContainingClass();
		}
		if (element instanceof EClass) {
			EClass eClass = (EClass) element;
			if (!eClass.isAbstract()) {
				return DrlModelDiagramEditorPlugin.getInstance()
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
			elements.put(DocumentationCore_79, DrlPackage.eINSTANCE
					.getDocumentationCore());
			elements.put(InfElement_1001, DrlPackage.eINSTANCE.getInfElement());
			elements.put(InfProduct_1002, DrlPackage.eINSTANCE.getInfProduct());
			elements.put(InfElemRefGroup_1003, DrlPackage.eINSTANCE
					.getInfElemRefGroup());
			elements.put(InfElemRef_3001, DrlPackage.eINSTANCE.getInfElemRef());
			elements.put(SubelementedElementElements_3002, DrlPackage.eINSTANCE
					.getSubelementedElement_Elements());
		}
		return (ENamedElement) elements.get(type);
	}

	/**
	 * @generated
	 */
	public static final IElementType DocumentationCore_79 = getElementType("drldoc.diagram.DocumentationCore_79"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType InfElement_1001 = getElementType("drldoc.diagram.InfElement_1001"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType InfProduct_1002 = getElementType("drldoc.diagram.InfProduct_1002"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType InfElemRefGroup_1003 = getElementType("drldoc.diagram.InfElemRefGroup_1003"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType InfElemRef_3001 = getElementType("drldoc.diagram.InfElemRef_3001"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType SubelementedElementElements_3002 = getElementType("drldoc.diagram.SubelementedElementElements_3002"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	private static IElementType getElementType(String id) {
		return ElementTypeRegistry.getInstance().getType(id);
	}

	/**
	 * @generated
	 */
	private static Set KNOWN_ELEMENT_TYPES;

	/**
	 * @generated
	 */
	public static boolean isKnownElementType(IElementType elementType) {
		if (KNOWN_ELEMENT_TYPES == null) {
			KNOWN_ELEMENT_TYPES = new HashSet();
			KNOWN_ELEMENT_TYPES.add(DocumentationCore_79);
			KNOWN_ELEMENT_TYPES.add(InfElement_1001);
			KNOWN_ELEMENT_TYPES.add(InfProduct_1002);
			KNOWN_ELEMENT_TYPES.add(InfElemRefGroup_1003);
			KNOWN_ELEMENT_TYPES.add(InfElemRef_3001);
			KNOWN_ELEMENT_TYPES.add(SubelementedElementElements_3002);
		}
		return KNOWN_ELEMENT_TYPES.contains(elementType);
	}

	/**
	 * @generated
	 */
	public static class Initializers {

		/**
		 * @generated
		 */
		public static final ObjectInitializer InfElemRef_3001 = new ObjectInitializer(
				new FeatureInitializer[] { new FeatureInitializer(
						DrlModelOCLFactory.getExpression("true", //$NON-NLS-1$
								DrlPackage.eINSTANCE.getInfElemRef()),
						DrlPackage.eINSTANCE.getInfElemRef_Optional()) });

		/** 
		 * @generated
		 */
		private Initializers() {
		}

		/** 
		 * @generated
		 */
		public static class ObjectInitializer {
			/** 
			 * @generated
			 */
			private FeatureInitializer[] initExpressions;

			/** 
			 * @generated
			 */
			ObjectInitializer(FeatureInitializer[] initExpressions) {
				this.initExpressions = initExpressions;
			}

			/** 
			 * @generated
			 */
			public void init(EObject instance) {
				for (int i = 0; i < initExpressions.length; i++) {
					FeatureInitializer nextExpr = initExpressions[i];
					try {
						nextExpr.init(instance);
					} catch (RuntimeException e) {
						DrlModelDiagramEditorPlugin.getInstance().logError(
								"Feature initialization failed", e); //$NON-NLS-1$						
					}
				}
			}
		} // end of ObjectInitializer

		/** 
		 * @generated
		 */
		static class FeatureInitializer {

			/** 
			 * @generated
			 */
			private EStructuralFeature sFeature;

			/** 
			 * @generated
			 */
			private DrlModelAbstractExpression expression;

			/**
			 * @generated
			 */
			FeatureInitializer(DrlModelAbstractExpression expression,
					EStructuralFeature sFeature) {
				this.sFeature = sFeature;
				this.expression = expression;
			}

			/** 
			 * @generated
			 */
			void init(EObject contextInstance) {
				expression.assignTo(sFeature, contextInstance);
			}
		} // end of FeatureInitializer
	} // end of Initializers
}
