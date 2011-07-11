package org.spbu.plweb.diagram.util.projects;

import java.io.File;

import org.junit.Test;
import org.spbu.plweb.diagram.util.projects.parts.PartsReader;
import org.spbu.plweb.diagram.util.projects.parts.Root;

public class WebRatioProjectToXmlConverterTest {

	private static final String SOURCE_PATH = "D:\\KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK\\Acme";
	private static final String DOC_PATH = null;

	@Test
	public void testConvert() {
		final Root root = PartsReader.readParts(new File(SOURCE_PATH));
		System.out.println(WebRatioProjectToXmlConverter.convert(SOURCE_PATH, DOC_PATH, root));
	}

	@Test
	public void testConvertAndWrite() {
		final Root root = PartsReader.readParts(new File(SOURCE_PATH));
		WebRatioProjectToXmlConverter.convertAndWrite(SOURCE_PATH,DOC_PATH, root, "D:\\testout.plweb");
	}

}
