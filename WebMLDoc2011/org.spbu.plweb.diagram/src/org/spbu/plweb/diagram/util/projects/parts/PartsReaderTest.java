package org.spbu.plweb.diagram.util.projects.parts;

import java.io.File;

import org.junit.Test;

public class PartsReaderTest {

	private static final String SOURCE_PATH = "D:\\KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK\\webratioWS\\Acme";
	
	@Test
	public static void testReadParts() {
		System.out.println(PartsReader.readParts(new File(SOURCE_PATH)));
	}

}
