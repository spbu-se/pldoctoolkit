package org.spbu.plweb.diagram.util.projects.partsPLWeb;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class PlwebPartsReaderTest {
	
	private static final String SOURCE_PATH = "D:\\KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK\\Acme";
	private static final String SOURCE_PATH2 = "D:/KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK/Acme/Acme.plweb";
	
	@Test
	public void testReadParts() {
		
		Root root = PartsReader.readParts(new File(SOURCE_PATH), 0); //WR!!!
		Root rootPlweb = PlwebPartsReader.readParts(new File(SOURCE_PATH2)); //plweb
		System.out.println(root);
		System.out.println();
		System.out.println(rootPlweb);
		System.out.println();
//		System.out.println(PlwebPartsReader.getAllChildrenSiteView(rootPlweb));
//		System.out.println();
//		System.out.println(PlwebPartsReader.getAllChildrenArea(rootPlweb.getSiteViews().get(0)));
//		System.out.println();
//		System.out.println(PlwebPartsReader.getAllChildrenPage(rootPlweb.getSiteViews().get(0)));
//		
		Map<String, TitleAware> mapSV = PartsReader.mapSiteViewPlwebTree;
		System.out.println();
		Map<String, TitleAware> mapArea= PartsReader.mapAreaPlwebTree;
		System.out.println();
		Map<String, TitleAware> mapPage =PartsReader.mapPagePlwebTree;
		Map<String, TitleAware> mapSV2 = PlwebPartsReader.mapSiteViewPlwebTree;
		System.out.println();
		Map<String, TitleAware> mapArea2= PlwebPartsReader.mapAreaPlwebTree;
		System.out.println();
		Map<String, TitleAware> mapPage2 =PlwebPartsReader.mapPagePlwebTree;
		System.out.println();
		System.out.println("mapSV: "+mapSV);
		System.out.println("mapSV2: "+mapSV2);
		System.out.println("mapArea: "+mapArea);
		System.out.println("mapArea2: "+mapArea2);
		System.out.println("mapPage: "+mapPage);
		System.out.println("mapPage2: "+mapPage2);
		System.out.println("Home "+mapPage.get("Home"));
		System.out.println("Home "+mapPage2.get("Home"));
		System.out.println(rootPlweb);
		System.out.println(PlwebPartsReader.checkOld(rootPlweb, mapSV, mapArea, mapPage));
		System.out.println(rootPlweb);
		System.out.println(PlwebPartsReader.checkNew(rootPlweb, root, mapSV2, mapArea2, mapPage2));
	System.out.println(rootPlweb);
	
	
//	System.out.println(PlwebPartsReader.mapPagePlwebTree);
//	System.out.println(PartsReader.mapPagePlwebTree);
//	System.out.println(PlwebPartsReader.mapPagePlwebTree.get("Home Page"));
//	System.out.println(PartsReader.mapPagePlwebTree.get("Home Page"));
//	System.out.println("Modify combination "+PartsReader.mapPagePlwebTree.get("Modify combination"));
//	System.out.println("Home "+PartsReader.mapPagePlwebTree.get("Home"));
//	System.out.println("Products "+PartsReader.mapPagePlwebTree.get("Products"));
//	System.out.println("Combinations "+PartsReader.mapPagePlwebTree.get("Combinations"));
//	System.out.println("Technical record "+PartsReader.mapPagePlwebTree.get("Technical record"));
//	System.out.println("Modify store "+PartsReader.mapPagePlwebTree.get("Modify store"));
//	System.out.println("Images "+PartsReader.mapPagePlwebTree.get("Images"));
//	System.out.println("Stores "+PartsReader.mapPagePlwebTree.get("Stores"));
//	System.out.println("Home Page "+PartsReader.mapPagePlwebTree.get("Home Page"));
//	System.out.println("Categories "+PartsReader.mapPagePlwebTree.get("Categories"));
//	System.out.println("Modify product "+PartsReader.mapPagePlwebTree.get("Modify product"));
//	
//	System.out.println(PlwebPartsReader.mapPagePlwebTree);
//	System.out.println(PartsReader.mapPagePlwebTree);
//	System.out.println(PlwebPartsReader.mapPagePlwebTree.get("Home Page"));
//	System.out.println(PartsReader.mapPagePlwebTree.get("Home Page"));
//	System.out.println("Modify combination "+PlwebPartsReader.mapPagePlwebTree.get("Modify combination"));
//	System.out.println("Home "+PlwebPartsReader.mapPagePlwebTree.get("Home"));
//	System.out.println("Products "+PlwebPartsReader.mapPagePlwebTree.get("Products"));
//	System.out.println("Combinations "+PlwebPartsReader.mapPagePlwebTree.get("Combinations"));
//	System.out.println("Technical record "+PlwebPartsReader.mapPagePlwebTree.get("Technical record"));
//	System.out.println("Modify store "+PlwebPartsReader.mapPagePlwebTree.get("Modify store"));
//	System.out.println("Images "+PlwebPartsReader.mapPagePlwebTree.get("Images"));
//	System.out.println("Stores "+PlwebPartsReader.mapPagePlwebTree.get("Stores"));
//	System.out.println("Home Page "+PlwebPartsReader.mapPagePlwebTree.get("Home Page"));
//	System.out.println("Categories "+PlwebPartsReader.mapPagePlwebTree.get("Categories"));
//	System.out.println("Modify product "+PlwebPartsReader.mapPagePlwebTree.get("Modify product"));

	}
		

		
//		System.out.println(PartsReader.readParts(new File(SOURCE_PATH)));
//		System.out.println();
//		System.out.println(PartsReader.mapAreaPlwebTree);
//		System.out.println();
//		System.out.println(PartsReader.mapSiteViewPlwebTree);
//		System.out.println();
//		System.out.println(PartsReader.mapPagePlwebTree);
//		
//		Root rootPlweb = PlwebPartsReader.readParts(new File(SOURCE_PATH2));

		
//		checkOldChildren(root){
//			
//		}
//	}
//
//
//	private void checkOldChildren(TitleAware root) {
//		root 
//		
	

}
