package org.spbu.plweb.diagram.util.projects.partsPLWeb;

import java.util.Collections;
import java.util.List;

public class Root extends TitleAware {

	private List<SiteView> siteViews;
	private List<Node> nodes;
	private List<Group> groups;

	public Root(final String title, final List<SiteView> siteViews,
			final List<Node> nodes, final List<Group> groups) {

		super(title);
		this.siteViews = siteViews;
		this.nodes = nodes;
		this.groups = groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public void setSiteViews(List<SiteView> siteViews) {
		this.siteViews = siteViews;
	}
	
	public List<SiteView> getSiteViews() {
		return Collections.unmodifiableList(siteViews);
	}

	public List<Group> getGroups() {
		return Collections.unmodifiableList(groups);
	}

	public List<Node> getNodes() {
		return Collections.unmodifiableList(nodes);
	}

	@Override
	public String toString() {
		String sAreas ="";
		String sPages = "";
		String sNodes = "";
		String sGroups = "";

		if (!getGroups().isEmpty()){
			sGroups = ", groups: " + getGroups();
		}
		if (!getNodes().isEmpty()){
			sNodes = ", nodes: " + getNodes();
		}
		return "Root {" + getTitle() + "}, site views:" + siteViews +sAreas+sNodes;
	}
	public void addSiteView(SiteView siteView) {
		siteViews.add(siteView);
	}
}
