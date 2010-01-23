package org.spbu.publishutil;

import java.io.File;

public class ExportUtil extends ImportUtil {
	
	public static void main(String[] args) {
		try {
			if (args.length != 1)
				return;
			ExportUtil iu = new ExportUtil();
			iu.execute(args[0]);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public File getXSLFile() {
		return DirectoryXSLApplyAction.DRLEXPORT_FILE;
	}
	
}
