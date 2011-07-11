package org.spbu.plweb.diagram.util.projects.partsPLWeb;

public abstract class TitleAware {

	private String title;
	
	public TitleAware (final String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
