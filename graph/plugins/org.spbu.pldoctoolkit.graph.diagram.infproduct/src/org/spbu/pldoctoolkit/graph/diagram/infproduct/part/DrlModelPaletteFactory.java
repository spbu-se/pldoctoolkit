package org.spbu.pldoctoolkit.graph.diagram.infproduct.part;

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

import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class DrlModelPaletteFactory {

	/**
	 * @generated
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createDrlTools1Group());
	}

	/**
	 * @generated
	 */
	private PaletteContainer createDrlTools1Group() {
		PaletteContainer paletteContainer = new PaletteGroup("Drl Tools");
		paletteContainer.add(createInfProduct1CreationTool());
		paletteContainer.add(createInfElement2CreationTool());
		paletteContainer.add(createInfElemRefGroup3CreationTool());
		paletteContainer.add(createInfElemRefConnection4CreationTool());
		paletteContainer.add(createInfElemRefGroupConnection5CreationTool());
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
	private ToolEntry createInfElemRefGroup3CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.InfElemRefGroup_1003);

		largeImage = smallImage;

		final List elementTypes = new ArrayList();
		elementTypes.add(DrlModelElementTypes.InfElemRefGroup_1003);
		ToolEntry result = new NodeToolEntry("InfElemRefGroup",
				"Create new InfElemRefGroup", smallImage, largeImage,
				elementTypes);

		return result;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElemRefConnection4CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.InfElemRef_3001);

		largeImage = smallImage;

		final List relationshipTypes = new ArrayList();
		relationshipTypes.add(DrlModelElementTypes.InfElemRef_3001);
		relationshipTypes
				.add(DrlModelElementTypes.InfElemRefGroupInfElemRefsGroup_3003);
		ToolEntry result = new LinkToolEntry("InfElemRef Connection",
				"Create new InfElement reference", smallImage, largeImage,
				relationshipTypes);

		return result;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElemRefGroupConnection5CreationTool() {
		ImageDescriptor smallImage;
		ImageDescriptor largeImage;

		smallImage = DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.GenericDocumentPartGroups_3002);

		largeImage = smallImage;

		final List relationshipTypes = new ArrayList();
		relationshipTypes
				.add(DrlModelElementTypes.GenericDocumentPartGroups_3002);
		ToolEntry result = new LinkToolEntry("InfElemRefGroup Connection",
				"Assign InfElemRefGroup to an element", smallImage, largeImage,
				relationshipTypes);

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
