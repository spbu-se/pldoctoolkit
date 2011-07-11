package org.spbu.plweb.diagram.util.projects.parts;

import java.util.Collections;
import java.util.List;

public class Area implements TypeAware{
	
	public static final String PLWEB_TYPE = "plweb:Area";
	
	private final String title;
	private final List<Page> pages;
	
	public Area(final String title, final List<Page> pages) {
		this.title = title;
		this.pages = pages;
	}
	
	public String getTitle() {
		return title;
	}
	
	public List<Page> getPages() {
		return Collections.unmodifiableList(pages);
	}
	
	@Override
	public String toString() {
	   return "Area {"+title+"}, pages: " + pages;
	}
	
	@Override
	public String getType() {
		return "Area";
	}
}
