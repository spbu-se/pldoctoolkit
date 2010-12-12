package plweb.diagram.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;

import plweb.diagram.providers.PlwebElementTypes;

/**
 * @generated
 */
public class PlwebPaletteFactory {

	/**
	 * @generated
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createPlweb1Group());
	}

	/**
	 * Creates "plweb" palette tool group
	 * @generated
	 */
	private PaletteContainer createPlweb1Group() {
		PaletteGroup paletteContainer = new PaletteGroup(
				Messages.Plweb1Group_title);
		paletteContainer.setId("createPlweb1Group"); //$NON-NLS-1$
		paletteContainer.add(createNode1CreationTool());
		paletteContainer.add(createGroup2CreationTool());
		paletteContainer.add(createPage3CreationTool());
		paletteContainer.add(createReference4CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createNode1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(PlwebElementTypes.Node_2004);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Node1CreationTool_title,
				Messages.Node1CreationTool_desc, types);
		entry.setId("createNode1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(PlwebElementTypes
				.getImageDescriptor(PlwebElementTypes.Node_2004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createGroup2CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(PlwebElementTypes.Group_2002);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Group2CreationTool_title,
				Messages.Group2CreationTool_desc, types);
		entry.setId("createGroup2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(PlwebElementTypes
				.getImageDescriptor(PlwebElementTypes.Group_2002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createPage3CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(PlwebElementTypes.Page_2003);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Page3CreationTool_title,
				Messages.Page3CreationTool_desc, types);
		entry.setId("createPage3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(PlwebElementTypes
				.getImageDescriptor(PlwebElementTypes.Page_2003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createReference4CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(PlwebElementTypes.SourceRefElementClass_4001);
		LinkToolEntry entry = new LinkToolEntry(
				Messages.Reference4CreationTool_title,
				Messages.Reference4CreationTool_desc, types);
		entry.setId("createReference4CreationTool"); //$NON-NLS-1$
		entry
				.setSmallIcon(PlwebElementTypes
						.getImageDescriptor(PlwebElementTypes.SourceRefElementClass_4001));
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
