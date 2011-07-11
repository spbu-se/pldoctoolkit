package org.spbu.plweb.diagram.util.projects.partsPLWeb;

import java.util.Collections;
import java.util.List;

public class SiteView extends GroupsAware {

	public static final String PLWEB_TYPE = "plweb:SiteView";

	private List<Area> areas;
	private List<Page> pages;

	public SiteView(final boolean optional, final String title,
			final List<Area> areas, final List<Page> pages,
			final List<Node> nodes, final List<Group> groups,
			final List<DocTopic> topics) {

		super(optional, title, topics, nodes, groups);
		this.areas = areas;
		this.pages = pages;

	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
	
	public void setPages(List<Page> pages) {
		this.pages = pages;
	}
	public List<Area> getAreas() {
		return Collections.unmodifiableList(areas);
	}

	public List<Page> getPages() {
		return Collections.unmodifiableList(pages);
	}

	@Override
	public String toString() {
		String sAreas ="";
		String sPages = "";
		String sNodes = "";
		String sGroups = "";
		String sTopics = "";
		if(!getAreas().isEmpty()){
			sAreas = ", areas: "+getAreas();
		}
		if (!getGroups().isEmpty()){
			sGroups = ", groups: " + getGroups();
		}
		if (!getNodes().isEmpty()){
			sNodes = ", nodes: " + getNodes();
		}
		if (!getPages().isEmpty()){
			sPages = ", pages: " + pages;
		}
		if (!getTopics().isEmpty()){
			sTopics = ", topics: "
				+ getTopics();
		}
		return "SiteView {" + getTitle() + "}"+sAreas+sPages+sNodes+sGroups+sTopics;
	}
	
	public void addPage(Page page) {
		pages.add(page);
	}
	
	public void addArea(Area area) {
		areas.add(area);
	}

}
