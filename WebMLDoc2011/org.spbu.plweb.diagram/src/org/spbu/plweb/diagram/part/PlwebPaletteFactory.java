package org.spbu.plweb.diagram.part;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.spbu.plweb.diagram.providers.PlwebElementTypes;

/**
 * @generated
 */
public class PlwebPaletteFactory {

	/**
	 * @generated NOT
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		//		paletteRoot.add(createPlweb1Group()); <-- Empty palette 
	}

	/**
	 * Creates "plweb" palette tool group
	 * @generated
	 */
	private PaletteContainer createPlweb1Group() {
		PaletteGroup paletteContainer = new PaletteGroup(
				Messages.Plweb1Group_title);
		paletteContainer.setId("createPlweb1Group"); //$NON-NLS-1$
		paletteContainer.add(createReference1CreationTool());
		paletteContainer.add(createPage2CreationTool());
		paletteContainer.add(createGroup3CreationTool());
		paletteContainer.add(createNode4CreationTool());
		paletteContainer.add(createDocTopic5CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createReference1CreationTool() {
		LinkToolEntry entry = new LinkToolEntry(
				Messages.Reference1CreationTool_title,
				Messages.Reference1CreationTool_desc,
				Collections
						.singletonList(PlwebElementTypes.SourceRefElementClass_4001));
		entry.setId("createReference1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(PlwebElementTypes
				.getImageDescriptor(PlwebElementTypes.SourceRefElementClass_4001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createPage2CreationTool() {
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Page2CreationTool_title,
				Messages.Page2CreationTool_desc,
				Collections.singletonList(PlwebElementTypes.Page_2004));
		entry.setId("createPage2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(PlwebElementTypes
				.getImageDescriptor(PlwebElementTypes.Page_2004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createGroup3CreationTool() {
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Group3CreationTool_title,
				Messages.Group3CreationTool_desc,
				Collections.singletonList(PlwebElementTypes.Group_2005));
		entry.setId("createGroup3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(PlwebElementTypes
				.getImageDescriptor(PlwebElementTypes.Group_2005));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createNode4CreationTool() {
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Node4CreationTool_title,
				Messages.Node4CreationTool_desc,
				Collections.singletonList(PlwebElementTypes.Node_2006));
		entry.setId("createNode4CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(PlwebElementTypes
				.getImageDescriptor(PlwebElementTypes.Node_2006));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createDocTopic5CreationTool() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(5);
		types.add(PlwebElementTypes.DocTopic_3005);
		types.add(PlwebElementTypes.DocTopic_3006);
		types.add(PlwebElementTypes.DocTopic_3007);
		types.add(PlwebElementTypes.DocTopic_3008);
		types.add(PlwebElementTypes.DocTopic_3009);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.DocTopic5CreationTool_title,
				Messages.DocTopic5CreationTool_desc, types);
		entry.setId("createDocTopic5CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(PlwebElementTypes
				.getImageDescriptor(PlwebElementTypes.DocTopic_3005));
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
		private final List<IElementType> elementTypes;

		/**
		 * @generated
		 */
		private NodeToolEntry(String title, String description,
				List<IElementType> elementTypes) {
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
		private final List<IElementType> relationshipTypes;

		/**
		 * @generated
		 */
		private LinkToolEntry(String title, String description,
				List<IElementType> relationshipTypes) {
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
