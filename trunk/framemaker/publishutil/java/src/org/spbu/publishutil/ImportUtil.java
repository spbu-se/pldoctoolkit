package org.spbu.publishutil;

import static org.spbu.publishutil.utils.UtilConstants.DRL_FILE_EXTENSION;
import static org.spbu.publishutil.utils.UtilConstants.ERROR;
import static org.spbu.publishutil.utils.UtilConstants.SUCCESS;

import java.io.File;

import org.spbu.publishutil.utils.EventLogger;
import org.spbu.publishutil.utils.EventLoggerImpl;
import org.spbu.publishutil.utils.ProjectRegistry;
import org.spbu.publishutil.utils.ProjectRegistryImpl;

public class ImportUtil implements CJProxy {
	private String srcDir = null;
	private EventLogger logger = null;
	
	public static void main(String[] args) {
		try {
			if (args.length != 1)
				return;
			ImportUtil iu = new ImportUtil();
			iu.execute(args[0]);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ImportUtil() {}
	
	public int run() {
		logger = new EventLoggerImpl();
		try {
			File srcDirFile = new File(srcDir);
			if (!srcDirFile.exists())
				throw new RuntimeException("Specified source folder is not found: " + srcDir);
			
			if (!srcDirFile.isDirectory())
				throw new RuntimeException("Specified input is not a correct folder path: " + srcDir);
			
			ProjectRegistry registry = new ProjectRegistryImpl();
			registry.registerDirectory(srcDirFile);
			
			DirectoryXSLApplyAction drlImportAction = new DirectoryXSLApplyAction(getXSLFile());
			drlImportAction.setRegistry(registry);
			drlImportAction.setLogger(logger);
			
			for (File file: srcDirFile.listFiles()) {
				if (!file.isDirectory() && file.getName().endsWith(DRL_FILE_EXTENSION))
					drlImportAction.run(file);
			}
			
			logger.logEvent("Done");
			
		} catch (Exception e) {
			logger.logException(e, true);
		} finally {
			logger.endWork();
		}
		
		if (logger.isErrorHappened())
			return ERROR;
		
		return SUCCESS;
	}
	
	public File getXSLFile() {
		return DirectoryXSLApplyAction.DRLIMPORT_FILE;
	}
	
	@Override
	// this function is invoke by C code
	public Object execute(Object args) throws Exception {
		if (!(args instanceof String)) {
			throw new RuntimeException("Invalid type for execute");
		}
		
		String input = (String) args;
		
		this.srcDir = input;
		
		return this.run() + "";
	}

}
