package org.spbu.plweb.diagram.util.projects.partsPLWeb;

import java.io.File;
import java.util.Iterator;

import org.junit.Test;

public class PartsReaderTest {

	private static final String SOURCE_PATH = "D:\\KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK\\webratioWS\\Acme";
	private static final String SOURCE_PATH2 = "D:\\KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK\\Acme.plweb";
	private static final String SOURCE_PATH3 = "D:\\KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK\\doc";
	
	@Test
	public void testReadParts() {
		System.out.println(PartsReader.readParts(new File(SOURCE_PATH), 0));
		System.out.println();
		System.out.println(PartsReader.mapAreaPlwebTree);
		System.out.println();
		System.out.println(PartsReader.mapSiteViewPlwebTree);
		System.out.println();
		System.out.println(PartsReader.mapPagePlwebTree);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		DocTopicsPartsReader.getListOfDocElements(SOURCE_PATH3);
	}
	
//	@Test
//	public void testReadParts2() {
//		System.out.println(PlwebPartsReader.readParts(new File(SOURCE_PATH2)));
//	}

}
