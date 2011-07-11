package org.spbu.plweb.diagram.util.projects.partsPLWeb;

import java.util.Collections;
import java.util.List;

public abstract class DocTopicsAware extends IsOptionalAware {
	
	private final List<DocTopic> topics;
	
	public DocTopicsAware(final boolean optional, final String title, final List<DocTopic> topics){
		super(optional, title);
		this.topics = topics;
	}
	
	public List<DocTopic> getTopics() {
		return Collections.unmodifiableList(topics);
	}
	

}
