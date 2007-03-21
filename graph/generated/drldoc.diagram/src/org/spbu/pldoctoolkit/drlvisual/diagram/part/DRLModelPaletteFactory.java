package org.spbu.pldoctoolkit.drlvisual.diagram.part;

import java.util.List;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.jface.resource.ImageDescriptor;
import java.util.ArrayList;

import org.eclipse.gef.palette.PaletteGroup;

import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;

import org.spbu.pldoctoolkit.drlvisual.diagram.providers.DRLModelElementTypes;

/**
 * @generated
 */
public class DRLModelPaletteFactory {

	/**
	 * @generated
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createelements1Group());
	}

	/**
	 * @generated
	 */
	private PaletteContainer createelements1Group() {
		PaletteContainer paletteContainer = new PaletteGroup("elements");
		paletteContainer.add(createInfElement1CreationTool());
		paletteContainer.add(createInfProduct2CreationTool());
		paletteContainer.add(createInfElemRefOptional3CreationTool());
		paletteContainer.add(createInfElemRefMandatory4CreationTool());
		paletteContainer.add(createInfElemRefGroup5CreationTool());
		paletteContainer.add(createSubelementedElementElements6CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElement1CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DRLModelElementTypes
				.getImageDescriptor(DRLModelElementTypes.InfElement_1001);

		largeImage = smallImage;

		final List elementTypes = new ArrayList();
		elementTypes.add(DRLModelElementTypes.InfElement_1001);
		ToolEntry result = new NodeToolEntry("InfElement",
				"Create new InfElement", smallImage, largeImage, elementTypes);

		return result;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfProduct2CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DRLModelElementTypes
				.getImageDescriptor(DRLModelElementTypes.InfProduct_1002);

		largeImage = smallImage;

		final List elementTypes = new ArrayList();
		elementTypes.add(DRLModelElementTypes.InfProduct_1002);
		ToolEntry result = new NodeToolEntry("InfProduct",
				"Create new InfProduct", smallImage, largeImage, elementTypes);

		return result;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElemRefOptional3CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DRLModelElementTypes
				.getImageDescriptor(DRLModelElementTypes.InfElemRef_3001);

		largeImage = smallImage;

		final List relationshipTypes = new ArrayList();
		relationshipTypes.add(DRLModelElementTypes.InfElemRef_3001);
		ToolEntry result = new LinkToolEntry("InfElemRefOptional",
				"Create new InfElemRefOptional", smallImage, largeImage,
				relationshipTypes);

		return result;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElemRefMandatory4CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DRLModelElementTypes
				.getImageDescriptor(DRLModelElementTypes.InfElemRef_3002);

		largeImage = smallImage;

		final List relationshipTypes = new ArrayList();
		relationshipTypes.add(DRLModelElementTypes.InfElemRef_3002);
		ToolEntry result = new LinkToolEntry("InfElemRefMandatory",
				"Create new InfElemRefMandatory", smallImage, largeImage,
				relationshipTypes);

		return result;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElemRefGroup5CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DRLModelElementTypes
				.getImageDescriptor(DRLModelElementTypes.InfElemRefGroup_1003);

		largeImage = smallImage;

		final List elementTypes = new ArrayList();
		elementTypes.add(DRLModelElementTypes.InfElemRefGroup_1003);
		ToolEntry result = new NodeToolEntry("InfElemRefGroup",
				"Create new InfElemRefGroup", smallImage, largeImage,
				elementTypes);

		return result;
	}

	/**
	 * @generated
	 */
	private ToolEntry createSubelementedElementElements6CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DRLModelElementTypes
				.getImageDescriptor(DRLModelElementTypes.SubelementedElementElements_3003);

		largeImage = smallImage;

		final List relationshipTypes = new ArrayList();
		relationshipTypes
				.add(DRLModelElementTypes.SubelementedElementElements_3003);
		ToolEntry result = new LinkToolEntry("SubelementedElementElements",
				"Create new SubelementedElementElements", smallImage,
				largeImage, relationshipTypes);

		return result;
	}

	/**
	 * @generated
	 */
	private static class NodeToolEntry extends ToolEntry {

		/**
		 * @generated
		 */
		private final List elementTypes;

		/**
		 * @generated
		 */
		private NodeToolEntry(String title, String description,
				ImageDescriptor smallIcon, ImageDescriptor largeIcon,
				List elementTypes) {
			super(title, description, smallIcon, largeIcon);
			this.elementTypes = elementTypes;
		}

		/**
		 * @generated
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeCreationTool(elementTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}

	/**
	 * @generated
	 */
	private static class LinkToolEntry extends ToolEntry {

		/**
		 * @generated
		 */
		private final List relationshipTypes;

		/**
		 * @generated
		 */
		private LinkToolEntry(String title, String description,
				ImageDescriptor smallIcon, ImageDescriptor largeIcon,
				List relationshipTypes) {
			super(title, description, smallIcon, largeIcon);
			this.relationshipTypes = relationshipTypes;
		}

		/**
		 * @generated
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}
}
