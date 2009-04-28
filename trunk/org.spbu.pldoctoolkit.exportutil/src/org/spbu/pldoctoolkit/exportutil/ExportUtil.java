package org.spbu.pldoctoolkit.exportutil;

import java.io.File;
import java.rmi.server.ExportException;

import org.spbu.pldoctoolkit.exportutil.utils.EventLogger;
import org.spbu.pldoctoolkit.exportutil.utils.EventLoggerImpl;
import org.spbu.pldoctoolkit.exportutil.utils.ProjectRegistry;
import org.spbu.pldoctoolkit.exportutil.utils.ProjectRegistryImpl;

public class ExportUtil implements CJProxy {
	public static final int SUCCESS = 0;
	public static final int ERROR = 1;
	
	public static final String DRL_FILE_EXTENSION = "drl";
	
	public static final String DOCBOOK_FORMAT = "docbook";
	public static final String HTML_FORMAT = "html";
	public static final String PDF_FORMAT = "pdf";
	
	private static ExportUtil exportUtil;
	private EventLogger logger = null;
	
	private String srcDir = null;
	private String srcFilename = null;
	private String dstFilepath = null;
	private String srcId = null;
	private String format = null;
	
	public static void main(String[] args) {	
		if (args.length < 2 || args.length > 5 || args.length == 3) {
			printHelp();
			return;
		} else if (args.length == 2) {
			exportUtil = new ExportUtil(args[0], args[1]);
		} else if (args.length == 3) {
			exportUtil = new ExportUtil(args[0], args[1], args[2]);
		} else if (args.length == 4) {
			exportUtil = new ExportUtil(args[0], args[1], args[2], args[3]);
		} else if (args.length == 5) {
			exportUtil = new ExportUtil(args[0], args[1], args[2], args[3], args[4]);
		}
		
		exportUtil.run();
	}
	
	public static void printHelp() {
		System.out.println("Usage: exportutil <directory> <mainfile> [inf product id] [<format> <destinationfilepath>]");
	}
	
	public static String getRunningAppPath() {
		return new File(ExportUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
	}
	
	// dummy constructor for use with CJProxy interface
	public ExportUtil() {
	}
	
	public ExportUtil(String srcDir, String srcFile) {
		this(srcDir, srcFile, null, null, null);
	}
	
	public ExportUtil(String srcDir, String srcFile, String srcId) {
		this(srcDir, srcFile, srcId, null, null);
	}
	
	public ExportUtil(String srcDir, String srcFile, String format, String dstFilepath) {
		this(srcDir, srcFile, null, format, dstFilepath);
	}
	
	public ExportUtil(String srcDir, String srcFile, String srcId, String format, String dstFilepath) {
		this.srcDir = srcDir;
		this.srcFilename = srcFile;
		this.srcId = srcId;
		this.format = format;
		this.dstFilepath = dstFilepath;
	}
	
	public int run() {
		logger = new EventLoggerImpl();
		try {
			File srcDirFile = new File(srcDir);
			if (!srcDirFile.exists())
				throw new ExportException("Specified source folder is not found");
			
			File srcFile = new File(srcDir + "/" + srcFilename);
			if (!srcFile.exists())
				throw new ExportException("Specified source file is not found");
			
			File dstFile = null;
			if (dstFilepath != null) {
				dstFile = new File(dstFilepath);
				if (dstFile.exists())
					dstFile.delete();
			}
			
			ValidateDrlAction validateAction = new ValidateDrlAction();
			validateAction.setLogger(logger);
			
			logger.logEvent("Validating DRL files...");
			for (File file: srcDirFile.listFiles()) {
				if (!file.isDirectory() && file.getName().endsWith(DRL_FILE_EXTENSION))
					validateAction.run(file);
			}
			
			ProjectRegistry registry = new ProjectRegistryImpl();
			registry.registerDirectory(srcDirFile);
			
			BasicExportAction exportAction;
			
			if (this.format == null) {
				exportAction = new ValidateExportAction();
			} else if (this.format.toLowerCase().equals(DOCBOOK_FORMAT)) {
				exportAction = new BasicExportAction(dstFile, null, true);
			} else if (this.format.toLowerCase().equals(HTML_FORMAT)) {
				exportAction = new BasicExportAction(dstFile, BasicExportAction.DOCBOOK2HTML_FILE, false);
			} else if (this.format.toLowerCase().equals(PDF_FORMAT)) {
				exportAction = new PdfExportAction(dstFile);
			} else {
				throw new ExportException("Specified format is not supported");
			}

			exportAction.setRegistry(registry);
			exportAction.setLogger(logger);
			exportAction.run(srcFile, srcId);
			
			logger.logEvent("Done");

		} catch (Exception e) {
			logger.logExeption(e);
			return ERROR;
		} finally {
			logger.endWork();
		}
		
		return SUCCESS;
	}

	@Override
	public Object execute(Object args) throws Exception { 	
		if (!(args instanceof String)) {
			throw new RuntimeException("Invalid type for execute");
		}
		
		String input[] = ((String) args).split("\\|");
		
		if (input.length != 5) {
			throw new RuntimeException("Invalid input");
		}
		
		if (!input[0].trim().isEmpty()) {
			this.srcDir = input[0]; 
		}
		if (!input[1].trim().isEmpty()) {
			this.srcFilename = input[1];
		}
		if (!input[2].trim().isEmpty()) {
			this.srcId = input[2];
		}
		if (!input[3].trim().isEmpty()) {
			this.format = input[3];
		}
		if (!input[4].trim().isEmpty()) {
			this.dstFilepath = input[4];
		}
	
		return this.run() + "";
	}
}
