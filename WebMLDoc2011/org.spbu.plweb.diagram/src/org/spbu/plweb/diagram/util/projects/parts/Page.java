package org.spbu.plweb.diagram.util.projects.parts;

public class Page {
	
	public static final String PLWEB_TYPE = "plweb:Page";
	
	private final String title;
	private final String source;
	
	public Page(final String title, final String source){
		this.title = title;
		this.source = source;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getSource() {
		return source;
	}
	
	@Override
	public String toString() {
		return "Page title="+title+", source=" + source;
	}
}
