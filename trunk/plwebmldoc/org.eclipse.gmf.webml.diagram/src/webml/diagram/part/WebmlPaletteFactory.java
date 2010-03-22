/*
 * 
 */
package webml.diagram.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;

import webml.diagram.providers.WebmlElementTypes;

/**
 * @generated
 */
public class WebmlPaletteFactory {

	/**
	 * @generated
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createObjects1Group());
		paletteRoot.add(createConnections2Group());
	}

	/**
	 * Creates "Objects" palette tool group
	 * @generated
	 */
	private PaletteContainer createObjects1Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(
				Messages.Objects1Group_title);
		paletteContainer.setId("createObjects1Group"); //$NON-NLS-1$
		paletteContainer.add(createArea1CreationTool());
		paletteContainer.add(createPage2CreationTool());
		paletteContainer.add(createContentUnit3CreationTool());
		paletteContainer.add(createOperationUnit4CreationTool());
		paletteContainer.add(createDocTopic5CreationTool());
		return paletteContainer;
	}

	/**
	 * Creates "Connections" palette tool group
	 * @generated
	 */
	private PaletteContainer createConnections2Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(
				Messages.Connections2Group_title);
		paletteContainer.setId("createConnections2Group"); //$NON-NLS-1$
		paletteContainer.add(createOkLink1CreationTool());
		paletteContainer.add(createKoLink2CreationTool());
		paletteContainer.add(createLink3CreationTool());
		paletteContainer.add(createTransportLink4CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createArea1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(WebmlElementTypes.Area_2001);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Area1CreationTool_title,
				Messages.Area1CreationTool_desc, types);
		entry.setId("createArea1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(WebmlElementTypes
				.getImageDescriptor(WebmlElementTypes.Area_2001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createPage2CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(2);
		types.add(WebmlElementTypes.Page_3001);
		types.add(WebmlElementTypes.Page_2002);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Page2CreationTool_title,
				Messages.Page2CreationTool_desc, types);
		entry.setId("createPage2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(WebmlElementTypes
				.getImageDescriptor(WebmlElementTypes.Page_3001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createContentUnit3CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(2);
		types.add(WebmlElementTypes.ContentUnit_3002);
		types.add(WebmlElementTypes.ContentUnit_2003);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.ContentUnit3CreationTool_title,
				Messages.ContentUnit3CreationTool_desc, types);
		entry.setId("createContentUnit3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(WebmlElementTypes
				.getImageDescriptor(WebmlElementTypes.ContentUnit_3002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createOperationUnit4CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(2);
		types.add(WebmlElementTypes.OperationUnit_3004);
		types.add(WebmlElementTypes.OperationUnit_2004);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.OperationUnit4CreationTool_title,
				Messages.OperationUnit4CreationTool_desc, types);
		entry.setId("createOperationUnit4CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(WebmlElementTypes
				.getImageDescriptor(WebmlElementTypes.OperationUnit_3004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createDocTopic5CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(WebmlElementTypes.DocTopic_3003);
		NodeToolEntry entry = new NodeToolEntry(
				Messages.DocTopic5CreationTool_title,
				Messages.DocTopic5CreationTool_desc, types);
		entry.setId("createDocTopic5CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(WebmlElementTypes
				.getImageDescriptor(WebmlElementTypes.DocTopic_3003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createOkLink1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(WebmlElementTypes.OkLink_4001);
		LinkToolEntry entry = new LinkToolEntry(
				Messages.OkLink1CreationTool_title,
				Messages.OkLink1CreationTool_desc, types);
		entry.setId("createOkLink1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(WebmlElementTypes
				.getImageDescriptor(WebmlElementTypes.OkLink_4001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createKoLink2CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(WebmlElementTypes.KoLink_4002);
		LinkToolEntry entry = new LinkToolEntry(
				Messages.KoLink2CreationTool_title,
				Messages.KoLink2CreationTool_desc, types);
		entry.setId("createKoLink2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(WebmlElementTypes
				.getImageDescriptor(WebmlElementTypes.KoLink_4002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createLink3CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(WebmlElementTypes.NormalLink_4003);
		LinkToolEntry entry = new LinkToolEntry(
				Messages.Link3CreationTool_title,
				Messages.Link3CreationTool_desc, types);
		entry.setId("createLink3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(WebmlElementTypes
				.getImageDescriptor(WebmlElementTypes.NormalLink_4003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createTransportLink4CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(WebmlElementTypes.TransportLink_4004);
		LinkToolEntry entry = new LinkToolEntry(
				Messages.TransportLink4CreationTool_title,
				Messages.TransportLink4CreationTool_desc, types);
		entry.setId("createTransportLink4CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(WebmlElementTypes
				.getImageDescriptor(WebmlElementTypes.TransportLink_4004));
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
