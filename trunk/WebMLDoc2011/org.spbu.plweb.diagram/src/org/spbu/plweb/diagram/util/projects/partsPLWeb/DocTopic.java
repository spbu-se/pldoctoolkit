package org.spbu.plweb.diagram.util.projects.partsPLWeb;

import java.util.Collections;
import java.util.List;

public class DocTopic implements TypeAware {
	
public static final String PLWEB_TYPE = "plweb:DocTopic";
	
	private final String name;
	
	public DocTopic(final String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	
	@Override
	public String toString() {
	   return "DocTopic {"+name+"}";
	}
	
	@Override
	public String getType() {
		return "DocTopic";
	}

}
