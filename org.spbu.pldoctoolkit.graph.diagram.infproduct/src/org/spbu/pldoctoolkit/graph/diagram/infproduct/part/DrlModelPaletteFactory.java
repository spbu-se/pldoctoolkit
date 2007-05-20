package org.spbu.pldoctoolkit.graph.diagram.infproduct.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
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
		paletteContainer.add(createInfElement1CreationTool());
		paletteContainer.add(createInfElemRefGroup2CreationTool());
//		paletteContainer.add(createInfElemRefConnection3CreationTool());
//		paletteContainer.add(createInfElemRefGroupConnection4CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElement1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(DrlModelElementTypes.InfElement_1001);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.InfElement1CreationTool_title,
				Messages.InfElement1CreationTool_desc, types);
		entry.setSmallIcon(DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.InfElement_1001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElemRefGroup2CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(DrlModelElementTypes.InfElemRefGroup_1003);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.InfElemRefGroup2CreationTool_title,
				Messages.InfElemRefGroup2CreationTool_desc, types);
		entry.setSmallIcon(DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.InfElemRefGroup_1003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElemRefConnection3CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(2);
		types.add(DrlModelElementTypes.InfElemRef_3001);
		types.add(DrlModelElementTypes.InfElemRef_3003);
		LinkToolEntry entry = new LinkToolEntry(
				Messages.InfElemRefConnection3CreationTool_title,
				Messages.InfElemRefConnection3CreationTool_desc, types);
		entry.setSmallIcon(DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.InfElemRef_3001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfElemRefGroupConnection4CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(DrlModelElementTypes.GenericDocumentPartGroups_3002);
		LinkToolEntry entry = new LinkToolEntry(
				Messages.InfElemRefGroupConnection4CreationTool_title,
				Messages.InfElemRefGroupConnection4CreationTool_desc, types);
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
