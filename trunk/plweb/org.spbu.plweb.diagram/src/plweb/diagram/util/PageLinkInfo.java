package plweb.diagram.util;

import java.util.List;

public class PageLinkInfo {
	
	private String id = null;
	
	private String title = null;
	
	private List<String> links = null;
	
	public PageLinkInfo(String id, String title, List<String> links) {
		this.id = id;
		this.title = title;
		this.links = links;
	}
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public List<String> getLinks() {
		return links;
	}
}
