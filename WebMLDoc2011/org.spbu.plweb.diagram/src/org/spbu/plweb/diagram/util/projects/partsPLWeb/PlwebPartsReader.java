package org.spbu.plweb.diagram.util.projects.partsPLWeb;

import static org.spbu.plweb.diagram.util.CollectionsUtils.newList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.stream.events.Namespace;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class PlwebPartsReader {

	public static Map<String, List<String>> deletedMap = new HashMap<String, List<String>>();
	public static Map<String, List<String>> addedMap = new HashMap<String, List<String>>();
	public static Map<String, List<String>> changedMap = new HashMap<String, List<String>>();
	public static Map<String, TitleAware> mapAreaPlwebTree = new HashMap<String, TitleAware>();
	public static Map<String, TitleAware> mapSiteViewPlwebTree = new HashMap<String, TitleAware>();
	public static Map<String, TitleAware> mapPagePlwebTree = new HashMap<String, TitleAware>();

	public static final org.jdom.Namespace ns = org.jdom.Namespace
			.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");

	public static Root readParts(final File baseDir) {
		changedMap.clear();
		for (String i : PartsReader.changedList) {

			boolean ch = false;
			List<DocTopic> topics = newList();
			if (mapAreaPlwebTree.containsKey(i)) {
				ch = true;
				topics = ((DocTopicsAware) mapAreaPlwebTree.get(i)).getTopics();
			} else if (mapSiteViewPlwebTree.containsKey(i)) {
				ch = true;
				topics = ((DocTopicsAware) mapSiteViewPlwebTree.get(i))
						.getTopics();
			} else if (mapPagePlwebTree.containsKey(i)) {
				ch = true;
				topics = ((DocTopicsAware) mapSiteViewPlwebTree.get(i))
						.getTopics();
			}

			if (ch) {
				List<String> list = newList();

				for (DocTopic dt : topics) {
					list.add(dt.getName());
				}
				changedMap.put(i, list);
			}

		}
		deletedMap.clear();
		addedMap.clear();
		mapAreaPlwebTree.clear();
		mapPagePlwebTree.clear();
		mapSiteViewPlwebTree.clear();
		Element root = null;
		try {
			SAXBuilder builder = new SAXBuilder();
			try {
				Document doc = builder.build(baseDir);
				root = doc.getRootElement().getChild("root");
				Root treeRoot = new Root(getTitle(root),
						getChildrenSiteView(root), getChildrenNodes(root),
						getChildrenGroups(root));
				return treeRoot;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<SiteView> getAllChildrenSiteView(Object root) {
		List<SiteView> siteViews = newList();
		if (root instanceof Root) {
			Root r = (Root) root;
			siteViews.addAll(r.getSiteViews());
			for (Group g : r.getGroups()) {
				siteViews.addAll(getAllChildrenSiteView(g));
			}
			for (Node n : r.getNodes()) {
				siteViews.addAll(getAllChildrenSiteView(n));
			}
		}
		if (root instanceof Group) {
			Group r = (Group) root;
			siteViews.addAll(r.getSiteViews());
			for (Group g : r.getGroups()) {
				siteViews.addAll(getAllChildrenSiteView(g));
			}
			for (Node n : r.getNodes()) {
				siteViews.addAll(getAllChildrenSiteView(n));
			}
		}
		if (root instanceof Node) {
			Node r = (Node) root;
			siteViews.addAll(r.getSiteViews());
			for (Group g : r.getGroups()) {
				siteViews.addAll(getAllChildrenSiteView(g));
			}
			for (Node n : r.getNodes()) {
				siteViews.addAll(getAllChildrenSiteView(n));
			}
		}
		return siteViews;
	}

	public static List<Area> getAllChildrenArea(Object root) {
		List<Area> areas = newList();
		if (root instanceof SiteView) {
			SiteView r = (SiteView) root;
			areas.addAll(r.getAreas());
			for (Group g : r.getGroups()) {
				areas.addAll(getAllChildrenArea(g));
			}
			for (Node n : r.getNodes()) {
				areas.addAll(getAllChildrenArea(n));
			}
		}
		if (root instanceof Group) {
			Group r = (Group) root;
			areas.addAll(r.getAreas());
			for (Group g : r.getGroups()) {
				areas.addAll(getAllChildrenArea(g));
			}
			for (Node n : r.getNodes()) {
				areas.addAll(getAllChildrenArea(n));
			}
		}
		if (root instanceof Node) {
			Node r = (Node) root;
			areas.addAll(r.getAreas());
			for (Group g : r.getGroups()) {
				areas.addAll(getAllChildrenArea(g));
			}
			for (Node n : r.getNodes()) {
				areas.addAll(getAllChildrenArea(n));
			}
		}
		return areas;
	}

	public static List<Page> getAllChildrenPage(Object root) {
		List<Page> pages = newList();
		if (root instanceof SiteView) {
			SiteView r = (SiteView) root;
			pages.addAll(r.getPages());
			for (Group g : r.getGroups()) {
				pages.addAll(getAllChildrenPage(g));
			}
			for (Node n : r.getNodes()) {
				pages.addAll(getAllChildrenPage(n));
			}
			for (Area n : r.getAreas()) {
				pages.addAll(getAllChildrenPage(n));
			}
			return pages;
		}
		if (root instanceof Area) {
			Area r = (Area) root;
			pages.addAll(r.getPages());
			for (Group g : r.getGroups()) {
				pages.addAll(getAllChildrenPage(g));
			}
			for (Node n : r.getNodes()) {
				pages.addAll(getAllChildrenPage(n));
			}
			return pages;
		}
		if (root instanceof Group) {
			Group r = (Group) root;
			pages.addAll(r.getPages());
			for (Group g : r.getGroups()) {
				pages.addAll(getAllChildrenPage(g));
			}
			for (Node n : r.getNodes()) {
				pages.addAll(getAllChildrenPage(n));
			}
			return pages;
		}
		if (root instanceof Node) {
			Node r = (Node) root;
			pages.addAll(r.getPages());
			for (Group g : r.getGroups()) {
				pages.addAll(getAllChildrenPage(g));
			}
			for (Node n : r.getNodes()) {
				pages.addAll(getAllChildrenPage(n));
			}
			return pages;
		}
		return pages;
	}

	private static List<SiteView> getChildrenSiteView(Element root) {
		List<Element> children = root.getChildren("class");
		List<SiteView> siteViews = newList();
		for (Element c : children) {
			if ((c.getAttributeValue("type", ns) != null)
					&& (c.getAttributeValue("type", ns)
							.equals("plweb:SiteView"))) {
				SiteView newSiteView = new SiteView(getOptional(c),
						getTitle(c), getChildrenAreas(c), getChildrenPages(c),
						getChildrenNodes(c), getChildrenGroups(c),
						getChildrenTopics(c));
				siteViews.add(newSiteView);
				mapSiteViewPlwebTree.put(getTitle(c), newSiteView);
			}
		}
		return siteViews;
	}

	private static List<Area> getChildrenAreas(Element root) {
		List<Element> children = root.getChildren("class");
		List<Area> areas = newList();
		for (Element c : children) {
			if ((c.getAttributeValue("type", ns) != null)
					&& (c.getAttributeValue("type", ns).equals("plweb:Area"))) {
				Area newArea = new Area(getOptional(c), getTitle(c),
						getChildrenPages(c), getChildrenNodes(c),
						getChildrenGroups(c), getChildrenTopics(c));
				areas.add(newArea);
				PlwebPartsReader.mapAreaPlwebTree.put(getTitle(c), newArea);
			}
		}
		return areas;
	}

	private static List<Page> getChildrenPages(Element root) {
		List<Element> children = root.getChildren("class");
		List<Page> pages = newList();
		for (Element c : children) {
			if ((c.getAttributeValue("type", ns) != null)
					&& (c.getAttributeValue("type", ns).equals("plweb:Page"))) {
				Page newPage = new Page(getOptional(c), getTitle(c),
						getSource(c), getChildrenTopics(c));
				pages.add(newPage);
				mapPagePlwebTree.put(getTitle(c), newPage);
			}
		}
		return pages;
	}

	private static String getSource(Element c) {
		return c.getAttributeValue("source");
	}

	private static List<Node> getChildrenNodes(Element root) {
		List<Element> children = root.getChildren("class");
		List<Node> nodes = newList();
		for (Element c : children) {
			if ((c.getAttributeValue("type", ns) != null)
					&& (c.getAttributeValue("type", ns).equals("plweb:Node"))) {
				nodes.add(new Node(getOptional(c), getTitle(c),
						getChildrenPages(c), getChildrenSiteView(c),
						getChildrenAreas(c), getChildrenNodes(c),
						getChildrenGroups(c), getChildrenTopics(c)));
			}
		}
		return nodes;
	}

	private static List<Group> getChildrenGroups(Element root) {
		List<Element> children = root.getChildren("class");
		List<Group> groups = newList();
		for (Element c : children) {
			if ((c.getAttributeValue("type", ns) != null)
					&& (c.getAttributeValue("type", ns).equals("plweb:Group"))) {
				groups.add(new Group(getOptional(c), getTitle(c),
						getChildrenPages(c), getChildrenSiteView(c),
						getChildrenAreas(c), getChildrenNodes(c),
						getChildrenGroups(c), getChildrenTopics(c),
						getGroupType(c)));
			}
		}
		return groups;
	}

	private static List<DocTopic> getChildrenTopics(Element root) {
		List<Element> children = root.getChildren("docTopic");
		List<DocTopic> docTopics = newList();
		for (Element c : children) {
			docTopics.add(new DocTopic(c.getAttributeValue("docTopicName")));
		}
		return docTopics;
	}

	private static boolean getGroupType(Element c) {
		if ((c.getAttributeValue("type") != null)
				&& (c.getAttributeValue("type").equals("XOR")))
			return true;
		return false;
	}

	public static boolean getOptional(Element c) {
		if ((c.getAttributeValue("optional") != null)
				&& (c.getAttributeValue("optional").equals("true"))) {
			return true;
		}
		return false;
	}

	public static String getTitle(Element c) {
		return c.getAttributeValue("title");
	}

	public static boolean check(Object rootPlweb, Object rootWebRatio,
			Map<String, TitleAware> mapSV, Map<String, TitleAware> mapArea,
			Map<String, TitleAware> mapPage) {

		boolean res1 = checkOld(rootPlweb, mapSV, mapArea, mapPage);
		boolean res2 = checkNew(rootPlweb, rootWebRatio, mapSiteViewPlwebTree,
				mapAreaPlwebTree, mapPagePlwebTree);
		return (res1 | res2);
	}

	public static boolean checkNew(Object rootPlweb, Object rootWebRatio,
			Map<String, TitleAware> mapSV, Map<String, TitleAware> mapArea,
			Map<String, TitleAware> mapPage) {
		boolean res = false;

		if (rootWebRatio instanceof Root) {
			Root root = (Root) rootWebRatio;
			List<SiteView> listSiteView = root.getSiteViews();
			List<SiteView> newSiteView = newList();

			for (SiteView siteView : listSiteView) {
				if (!mapSV.containsKey(siteView.getTitle())) {
					newSiteView.add(siteView);
					addedMap.putAll(getListOfAddedElements(siteView));
					res = true;
				} else if (checkNew(mapSV.get(siteView.getTitle()), siteView,
						mapSV, mapArea, mapPage))
					res = true;
				;

			}
			for (SiteView sv : newSiteView) {
				((Root) rootPlweb).addSiteView(sv);
			}

		}
		if (rootWebRatio instanceof SiteView) {
			SiteView root = (SiteView) rootWebRatio;
			List<Area> listArea = root.getAreas();
			List<Area> newlistArea = newList();
			for (Area area : listArea) {
				if (!mapArea.containsKey(area.getTitle())) {
					newlistArea.add(area);
					addedMap.putAll(getListOfAddedElements(area));
					res = true;
				} else if (checkNew(mapArea.get(area.getTitle()), area, mapSV,
						mapArea, mapPage))
					res = true;
			}
			for (Area ar : newlistArea) {
				((SiteView) rootPlweb).addArea(ar);
			}

			List<Page> listPage = root.getPages();
			List<Page> newListPage = newList();
			for (Page page : listPage) {
				if (!mapPage.containsKey(page.getTitle())) {
					newListPage.add(page);
					addedMap.putAll(getListOfAddedElements(page));
					res = true;
				}
			}
			for (Page pg : newListPage) {
				((SiteView) rootPlweb).addPage(pg);
			}

		}
		if (rootWebRatio instanceof Area) {
			Area root = (Area) rootWebRatio;
			List<Page> listPage = root.getPages();
			List<Page> newListPage = newList();
			for (Page page : listPage) {
				if (!mapPage.containsKey(page.getTitle())) {
					newListPage.add(page);
					addedMap.putAll(getListOfAddedElements(page));
					res = true;
				}
			}
			for (Page pg : newListPage) {
				((Area) rootPlweb).addPage(pg);
			}
		}

		return res;
	}

	public static boolean checkOld(Object rootPlweb,
			Map<String, TitleAware> mapSV, Map<String, TitleAware> mapArea,
			Map<String, TitleAware> mapPage) {
		boolean res = false;
		if (rootPlweb instanceof Root) {
			Root root = (Root) rootPlweb;

			List<Group> groupsList = root.getGroups();
			for (Group group : groupsList) {
				if (checkOld(group, mapSV, mapArea, mapPage))
					res = true;
			}
			List<Node> nodesList = root.getNodes();
			for (Node node : nodesList) {
				if (checkOld(node, mapSV, mapArea, mapPage))
					res = true;
			}

			List<SiteView> newSvList = newList();
			List<SiteView> svList = root.getSiteViews();
			for (SiteView siteView : svList) {
				if (mapSV.containsKey(siteView.getTitle())) {
					newSvList.add(siteView);
					if (checkOld(siteView, mapSV, mapArea, mapPage))
						res = true;
				} else {
					deletedMap.putAll(getListOfDeletedElements(siteView));
					res = true;
				}
			}
			root.setSiteViews(newSvList);
		}

		if ((rootPlweb instanceof GroupsAware)) {
			GroupsAware root = (GroupsAware) rootPlweb;
			List<Group> groupsList = root.getGroups();
			for (Group group : groupsList) {
				if (checkOld(group, mapSV, mapArea, mapPage))
					res = true;
			}
			List<Node> nodesList = root.getNodes();
			for (Node node : nodesList) {
				if (checkOld(node, mapSV, mapArea, mapPage))
					res = true;
			}

			if (checkSiteViews(mapSV, mapArea, mapPage, res, root))
				res = true;
			if (checkAreas(mapSV, mapArea, mapPage, res, root))
				res = true;
			if (checkPages(mapSV, mapArea, mapPage, res, root))
				res = true;
		}

		return res;
	}

	private static boolean checkPages(Map<String, TitleAware> mapSV,
			Map<String, TitleAware> mapArea, Map<String, TitleAware> mapPage,
			boolean res, GroupsAware root) {

		if (root instanceof Node) {
			List<Page> newPageList = newList();
			List<Page> pageList = ((Node) root).getPages();
			for (Page page : pageList) {
				if (mapPage.containsKey(page.getTitle())) {
					newPageList.add(page);
					if (checkOld(page, mapSV, mapArea, mapPage))
						res = true;
				} else {
					res = true;
					List<String> list = new ArrayList<String>();
					for (DocTopic dt : page.getTopics()) {
						list.add(dt.getName());
					}
					deletedMap.put(page.getTitle(), list);
				}
			}
			((Node) root).setPages(newPageList);
		} else if (root instanceof Group) {
			List<Page> newPageList = newList();
			List<Page> pageList = ((Group) root).getPages();
			for (Page page : pageList) {
				if (mapPage.containsKey(page.getTitle())) {
					newPageList.add(page);
					if (checkOld(page, mapSV, mapArea, mapPage))
						res = true;
				} else {
					res = true;
					List<String> list = new ArrayList<String>();
					for (DocTopic dt : page.getTopics()) {
						list.add(dt.getName());
					}
					deletedMap.put(page.getTitle(), list);
				}
			}
			((Group) root).setPages(newPageList);
		} else if (root instanceof Area) {
			List<Page> newPageList = newList();
			List<Page> pageList = ((Area) root).getPages();
			for (Page page : pageList) {
				if (mapPage.containsKey(page.getTitle())) {
					newPageList.add(page);
					if (checkOld(page, mapSV, mapArea, mapPage))
						res = true;
				} else {
					res = true;
					List<String> list = new ArrayList<String>();
					for (DocTopic dt : page.getTopics()) {
						list.add(dt.getName());
					}
					deletedMap.put(page.getTitle(), list);

				}
			}
			((Area) root).setPages(newPageList);
		} else if (root instanceof SiteView) {
			List<Page> newPageList = newList();
			List<Page> pageList = ((SiteView) root).getPages();
			for (Page page : pageList) {
				if (mapPage.containsKey(page.getTitle())) {
					newPageList.add(page);
					if (checkOld(page, mapSV, mapArea, mapPage))
						res = true;
				} else {
					res = true;
					List<String> list = new ArrayList<String>();
					for (DocTopic dt : page.getTopics()) {
						list.add(dt.getName());
					}
					deletedMap.put(page.getTitle(), list);
				}
			}
			((SiteView) root).setPages(newPageList);
		}
		return res;
	}

	private static Map<String, List<String>> getListOfDeletedElements(
			DocTopicsAware element) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		if (element instanceof Page) {
			List<String> list = new ArrayList<String>();
			for (DocTopic dt : element.getTopics()) {
				list.add(dt.getName());
			}
			result.put(element.getTitle(), list);
		} else if (element instanceof GroupsAware) {
			List<String> list = new ArrayList<String>();
			for (DocTopic dt : element.getTopics()) {
				list.add(dt.getName());
			}
			result.put(element.getTitle(), list);
			for (Group group : ((GroupsAware) element).getGroups()) {
				result.putAll(getListOfDeletedElements(group));
			}
			for (Node node : ((GroupsAware) element).getNodes()) {
				result.putAll(getListOfDeletedElements(node));
			}
			if (element instanceof Group) {
				for (Area area : ((Group) element).getAreas()) {
					result.putAll(getListOfDeletedElements(area));
				}
				for (SiteView sv : ((Group) element).getSiteViews()) {
					result.putAll(getListOfDeletedElements(sv));
				}
				for (Page page : ((Group) element).getPages()) {
					result.putAll(getListOfDeletedElements(page));
				}
			} else if (element instanceof Node) {
				for (Area area : ((Node) element).getAreas()) {
					result.putAll(getListOfDeletedElements(area));
				}
				for (SiteView sv : ((Node) element).getSiteViews()) {
					result.putAll(getListOfDeletedElements(sv));
				}
				for (Page page : ((Node) element).getPages()) {
					result.putAll(getListOfDeletedElements(page));
				}
			} else if (element instanceof SiteView) {
				for (Area area : ((SiteView) element).getAreas()) {
					result.putAll(getListOfDeletedElements(area));
				}
				for (Page page : ((SiteView) element).getPages()) {
					result.putAll(getListOfDeletedElements(page));
				}
			} else if (element instanceof Area) {
				for (Page page : ((Area) element).getPages()) {
					result.putAll(getListOfDeletedElements(page));
				}
			}
		}
		return result;
	}

	private static Map<String, List<String>> getListOfAddedElements(
			DocTopicsAware element) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		if (element instanceof Page) {
			List<String> list = new ArrayList<String>();
			for (DocTopic dt : element.getTopics()) {
				list.add(dt.getName());
			}
			result.put(element.getTitle(), list);
		} else if (element instanceof GroupsAware) {
			List<String> list = new ArrayList<String>();
			for (DocTopic dt : element.getTopics()) {
				list.add(dt.getName());
			}
			result.put(element.getTitle(), list);
			if (element instanceof SiteView) {
				for (Area area : ((SiteView) element).getAreas()) {
					result.putAll(getListOfAddedElements(area));
				}
				for (Page page : ((SiteView) element).getPages()) {
					result.putAll(getListOfAddedElements(page));
				}
			} else if (element instanceof Area) {
				for (Page page : ((Area) element).getPages()) {
					result.putAll(getListOfAddedElements(page));
				}
			}
		}
		return result;
	}

	private static boolean checkAreas(Map<String, TitleAware> mapSV,
			Map<String, TitleAware> mapArea, Map<String, TitleAware> mapPage,
			boolean res, GroupsAware root) {

		if (root instanceof Node) {
			List<Area> newAreaList = newList();
			List<Area> areaList = ((Node) root).getAreas();
			for (Area area : areaList) {
				if (mapArea.containsKey(area.getTitle())) {
					newAreaList.add(area);
					if (checkOld(area, mapSV, mapArea, mapPage))
						res = true;
				} else {
					res = true;

					deletedMap.putAll(getListOfDeletedElements(area));
				}
			}
			((Node) root).setAreas(newAreaList);
		} else if (root instanceof Group) {
			List<Area> newAreaList = newList();
			List<Area> areaList = ((Group) root).getAreas();
			for (Area area : areaList) {
				if (mapArea.containsKey(area.getTitle())) {
					newAreaList.add(area);
					if (checkOld(area, mapSV, mapArea, mapPage))
						res = true;
				} else {
					res = true;
					deletedMap.putAll(getListOfDeletedElements(area));
				}
			}
			((Group) root).setAreas(newAreaList);
		} else if (root instanceof SiteView) {
			List<Area> newAreaList = newList();
			List<Area> areaList = ((SiteView) root).getAreas();
			for (Area area : areaList) {
				if (mapArea.containsKey(area.getTitle())) {
					newAreaList.add(area);
					if (checkOld(area, mapSV, mapArea, mapPage))
						res = true;
				} else {
					res = true;
					deletedMap.putAll(getListOfDeletedElements(area));
				}
			}
			((SiteView) root).setAreas(newAreaList);
		}
		return res;
	}

	private static boolean checkSiteViews(Map<String, TitleAware> mapSV,
			Map<String, TitleAware> mapArea, Map<String, TitleAware> mapPage,
			boolean res, GroupsAware root) {
		if (root instanceof Node) {
			List<SiteView> newSvList = newList();
			List<SiteView> svList = ((Node) root).getSiteViews();
			for (SiteView siteView : svList) {
				if (mapSV.containsKey(siteView.getTitle())) {
					newSvList.add(siteView);
					if (checkOld(siteView, mapSV, mapArea, mapPage))
						res = true;
				} else {
					res = true;
					deletedMap.putAll(getListOfDeletedElements(siteView));
				}
			}
			((Node) root).setSiteViews(newSvList);
		} else if (root instanceof Group) {
			List<SiteView> newSvList = newList();
			List<SiteView> svList = ((Group) root).getSiteViews();
			for (SiteView siteView : svList) {
				if (mapSV.containsKey(siteView.getTitle())) {
					newSvList.add(siteView);
					if (checkOld(siteView, mapSV, mapArea, mapPage))
						res = true;
				} else {
					res = true;
					deletedMap.putAll(getListOfDeletedElements(siteView));
				}
			}
			((Group) root).setSiteViews(newSvList);
		}
		return res;
	}

}
