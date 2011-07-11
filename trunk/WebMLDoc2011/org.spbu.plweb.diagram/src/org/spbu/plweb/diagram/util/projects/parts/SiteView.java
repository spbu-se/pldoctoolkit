package org.spbu.plweb.diagram.util.projects.parts;

import java.util.Collections;
import java.util.List;

public class SiteView {
	
	public static final String PLWEB_TYPE = "plweb:SiteView";
	
	private final String title;
	private final List<Area> areas;
	private final List<Page> pages;
	
	public SiteView(final String title, final List<Area> areas, 
			final List<Page> pages) {
		this.title = title;
		this.areas = areas;
		this.pages = pages;
	}
	
	public String getTitle() {
		return title;
	}
	
	public List<Area> getAreas() {
		return Collections.unmodifiableList(areas);
	}
	
	public List<Page> getPages(){
		return Collections.unmodifiableList(pages);
	}
	
	@Override
	public String toString() {
	   return "SiteView {"+title+"}, areas:" + areas + ", pages: " + pages;
	}
	
}
