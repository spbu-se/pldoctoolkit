package org.spbu.pldoctoolkit.graph.diagram.productline.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.spbu.pldoctoolkit.graph.diagram.productline.providers.DrlModelElementTypes;

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
	 * Creates "Drl Tools" palette tool group
	 * @generated
	 */
	private PaletteContainer createDrlTools1Group() {
		PaletteGroup paletteContainer = new PaletteGroup(
				Messages.DrlTools1Group_title);
		paletteContainer.add(createProduct1CreationTool());
		paletteContainer.add(createInfProduct2CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createProduct1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(DrlModelElementTypes.Product_2003);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Product1CreationTool_title,
				Messages.Product1CreationTool_desc, types);
		entry.setSmallIcon(DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.Product_2003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInfProduct2CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(DrlModelElementTypes.InfProduct_2005);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.InfProduct2CreationTool_title,
				Messages.InfProduct2CreationTool_desc, types);
		entry.setSmallIcon(DrlModelElementTypes
				.getImageDescriptor(DrlModelElementTypes.InfProduct_2005));
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
}
