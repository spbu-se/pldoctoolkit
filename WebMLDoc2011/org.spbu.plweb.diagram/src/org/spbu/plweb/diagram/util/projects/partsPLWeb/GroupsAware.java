package org.spbu.plweb.diagram.util.projects.partsPLWeb;

import java.util.Collections;
import java.util.List;

public abstract class GroupsAware extends DocTopicsAware {

	private List<Node> nodes;
	private List<Group> groups;

	public GroupsAware(final boolean optional, final String title,
			final List<DocTopic> topics, final List<Node> nodes,
			final List<Group> groups) {

		super(optional, title, topics);
		this.groups = groups;
		this.nodes = nodes;
	}

	public List<Group> getGroups() {
		return Collections.unmodifiableList(groups);
	}

	public List<Node> getNodes() {
		return Collections.unmodifiableList(nodes);
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
}
