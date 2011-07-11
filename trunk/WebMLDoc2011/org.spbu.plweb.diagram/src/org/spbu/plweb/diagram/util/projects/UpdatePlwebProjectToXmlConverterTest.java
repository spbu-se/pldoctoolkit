package org.spbu.plweb.diagram.util.projects;

import java.io.File;
import java.util.Map;

import org.junit.Test;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.PartsReader;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.PlwebPartsReader;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.Root;
import org.spbu.plweb.diagram.util.projects.partsPLWeb.TitleAware;


public class UpdatePlwebProjectToXmlConverterTest {
	private static final String SOURCE_PATH = "D:\\KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK\\Acme";
	private static final String SOURCE_PATH2 = "D:\\KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK\\runtime-EclipseApplication\\Acme\\Acme.plweb";
	private static final String docPath = null;

	@Test
	public void testConvert() {
		Root root = PartsReader.readParts(new File(SOURCE_PATH), 0); //WR!!!
		Root rootPlweb = PlwebPartsReader.readParts(new File(SOURCE_PATH2)); //plweb
		
		System.out.println(rootPlweb);
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
		System.out.println(PlwebPartsReader.check(rootPlweb, root, mapSV, mapArea, mapPage));

	System.out.println(rootPlweb);
	System.out.println();
	System.out.println();
		
		System.out.println(UpdatePlwebProjectToXmlConverter.convert(SOURCE_PATH, docPath, rootPlweb));
		UpdatePlwebProjectToXmlConverter.convertAndWrite(SOURCE_PATH, docPath, rootPlweb, "D:\\testout.plweb");
	}

//	@Test
//	public void testConvertAndWrite() {
//		final Root root = PartsReader.readParts(new File(SOURCE_PATH));
//		UpdatePlwebProjectToXmlConverter.convertAndWrite(SOURCE_PATH, root, "D:\\testout.plweb");
//	}
}
