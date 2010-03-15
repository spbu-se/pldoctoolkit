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
		paletteContainer.add(createArea1CreationTool());
		paletteContainer.add(createClass2CreationTool());
		paletteContainer.add(createGroup3CreationTool());
		paletteContainer.add(createPage4CreationTool());
		paletteContainer.add(createSourceRefElementClass5CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createArea1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(PlwebElementTypes.Area_2001);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Area1CreationTool_title,
				Messages.Area1CreationTool_desc, types);
		entry.setId("createArea1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(PlwebElementTypes
				.getImageDescriptor(PlwebElementTypes.Area_2001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createClass2CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(PlwebElementTypes.Class_2004);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Class2CreationTool_title,
				Messages.Class2CreationTool_desc, types);
		entry.setId("createClass2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(PlwebElementTypes
				.getImageDescriptor(PlwebElementTypes.Class_2004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createGroup3CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(PlwebElementTypes.Group_2002);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Group3CreationTool_title,
				Messages.Group3CreationTool_desc, types);
		entry.setId("createGroup3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(PlwebElementTypes
				.getImageDescriptor(PlwebElementTypes.Group_2002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createPage4CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(PlwebElementTypes.Page_2003);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Page4CreationTool_title,
				Messages.Page4CreationTool_desc, types);
		entry.setId("createPage4CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(PlwebElementTypes
				.getImageDescriptor(PlwebElementTypes.Page_2003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createSourceRefElementClass5CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(PlwebElementTypes.SourceRefElementClass_4001);
		LinkToolEntry entry = new LinkToolEntry(
				Messages.SourceRefElementClass5CreationTool_title,
				Messages.SourceRefElementClass5CreationTool_desc, types);
		entry.setId("createSourceRefElementClass5CreationTool"); //$NON-NLS-1$
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
