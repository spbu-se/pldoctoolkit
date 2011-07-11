package org.spbu.plweb.diagram.util.projects.partsPLWeb;

import java.util.Collections;
import java.util.List;

public class Area extends GroupsAware implements TypeAware {

	public static final String PLWEB_TYPE = "plweb:Area";

	private List<Page> pages;

	public Area(final boolean optional, final String title,
			final List<Page> pages, final List<Node> nodes,
			final List<Group> groups, final List<DocTopic> topics) {

		super(optional, title, topics, nodes, groups);
		this.pages = pages;

	}

	public List<Page> getPages() {
		return Collections.unmodifiableList(pages);
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	@Override
	public String toString() {
		String sAreas ="";
		String sPages = "";
		String sNodes = "";
		String sGroups = "";
		String sTopics = "";
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
		return "Area {" + getTitle() + "}"+sAreas+sPages+sNodes+sGroups+sTopics;
	}

	@Override
	public String getType() {
		return "Area";
	}
	public void addPage(Page page) {
		pages.add(page);
	}
}
