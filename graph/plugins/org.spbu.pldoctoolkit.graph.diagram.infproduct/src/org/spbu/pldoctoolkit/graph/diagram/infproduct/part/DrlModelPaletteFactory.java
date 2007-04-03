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
		PaletteGroup paletteContainer = new PaletteGroup(
				Messages.DrlTools1Group_title);
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
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(DrlModelElementTypes.InfProduct_1002);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.InfProduct1CreationTool_title,
				Messages.InfProduct1CreationTool_desc, types);
		entry.setSmallIcon(DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.InfProduct_1002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElement2CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(DrlModelElementTypes.InfElement_1001);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.InfElement2CreationTool_title,
				Messages.InfElement2CreationTool_desc, types);
		entry.setSmallIcon(DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.InfElement_1001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElemRefGroup3CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(DrlModelElementTypes.InfElemRefGroup_1003);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.InfElemRefGroup3CreationTool_title,
				Messages.InfElemRefGroup3CreationTool_desc, types);
		entry.setSmallIcon(DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.InfElemRefGroup_1003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElemRefConnection4CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(2);
		types.add(DrlModelElementTypes.InfElemRef_3001);
		types.add(DrlModelElementTypes.InfElemRefGroupInfElemRefsGroup_3003);
		LinkToolEntry entry = new LinkToolEntry(
				Messages.InfElemRefConnection4CreationTool_title,
				Messages.InfElemRefConnection4CreationTool_desc, types);
		entry.setSmallIcon(DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.InfElemRef_3001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElemRefGroupConnection5CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(DrlModelElementTypes.GenericDocumentPartGroups_3002);
		LinkToolEntry entry = new LinkToolEntry(
				Messages.InfElemRefGroupConnection5CreationTool_title,
				Messages.InfElemRefGroupConnection5CreationTool_desc, types);
		entry
				.setSmallIcon(DrlModelElementTypes
						.getImageDescriptor(DrlModelElementTypes.GenericDocumentPartGroups_3002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
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
				List elementTypes) {
			super(title, description, null, null);
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
				List relationshipTypes) {
			super(title, description, null, null);
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
