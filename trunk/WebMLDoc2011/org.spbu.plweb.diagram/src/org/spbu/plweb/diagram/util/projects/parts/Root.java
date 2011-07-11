package org.spbu.plweb.diagram.util.projects.parts;

import java.util.Collections;
import java.util.List;

public class Root {
  
   private final String title;
   private final List<SiteView> siteViews;
   
   public Root(final String title, final List<SiteView> siteViews) {
	   this.title = title;
	   this.siteViews = siteViews;
   }
   
   public String getTitle() {
	   return title;
   }
   
   public List<SiteView> getSiteViews() {
	   return Collections.unmodifiableList(siteViews);
   }
    
   @Override
	public String toString() {
	   return "Root {"+title+"}, site views:" + siteViews;
	}
}
