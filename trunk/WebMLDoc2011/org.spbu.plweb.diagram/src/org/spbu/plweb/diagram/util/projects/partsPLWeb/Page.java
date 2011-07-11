package org.spbu.plweb.diagram.util.projects.partsPLWeb;

import java.util.Collections;
import java.util.List;

public class Page extends DocTopicsAware{
	
	public static final String PLWEB_TYPE = "plweb:Page";
	
	private final String source;
	
	public Page(final Boolean optional, final String title, final String source, final List<DocTopic> topics){

		super(optional, title, topics);
		this.source = source;
	}
	
	public String getSource() {
		return source;
	}

	@Override
	public String toString() {
		String sTopics = "";
		
		if (!getTopics().isEmpty()){
			sTopics = ", topics: "
				+ getTopics();
		}
		return "Page title="+getTitle()+", source=" + source + sTopics;
	}
}
