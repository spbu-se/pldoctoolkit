package org.spbu.pldoctoolkit.graph.diagram.part;

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

import org.spbu.pldoctoolkit.graph.diagram.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class DrlModelPaletteFactory {

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
		paletteContainer.add(createInfProduct1CreationTool());
		paletteContainer.add(createInfElement2CreationTool());
		paletteContainer.add(createInfElemRef3CreationTool());
		paletteContainer.add(createInfElemRefGroup4CreationTool());
		paletteContainer.add(createSubelementedElementElements5CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfProduct1CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.InfProduct_1002);

		largeImage = smallImage;

		final List elementTypes = new ArrayList();
		elementTypes.add(DrlModelElementTypes.InfProduct_1002);
		ToolEntry result = new NodeToolEntry("InfProduct",
				"Create new InfProduct", smallImage, largeImage, elementTypes);

		return result;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElement2CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.InfElement_1001);

		largeImage = smallImage;

		final List elementTypes = new ArrayList();
		elementTypes.add(DrlModelElementTypes.InfElement_1001);
		ToolEntry result = new NodeToolEntry("InfElement",
				"Create new InfElement", smallImage, largeImage, elementTypes);

		return result;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElemRef3CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.InfElemRef_3001);

		largeImage = smallImage;

		final List relationshipTypes = new ArrayList();
		relationshipTypes.add(DrlModelElementTypes.InfElemRef_3001);
		ToolEntry result = new LinkToolEntry("InfElemRef",
				"Create new InfElemRefOptional", smallImage, largeImage,
				relationshipTypes);

		return result;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElemRefGroup4CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = null;

		largeImage = smallImage;

		ToolEntry result = new ToolEntry("InfElemRefGroup",
				"Create new InfElemRefGroup", smallImage, largeImage) {
		};

		return result;
	}

	/**
	 * @generated
	 */
	private ToolEntry createSubelementedElementElements5CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.InfElemRefGroup_1003);

		largeImage = smallImage;

		final List elementTypes = new ArrayList();
		elementTypes.add(DrlModelElementTypes.InfElemRefGroup_1003);
		ToolEntry result = new NodeToolEntry("SubelementedElementElements",
				"Create new SubelementedElementElements", smallImage,
				largeImage, elementTypes);

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
