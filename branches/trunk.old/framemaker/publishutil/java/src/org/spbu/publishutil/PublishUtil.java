package org.spbu.publishutil;

import java.io.File;
import java.rmi.server.ExportException;

import org.spbu.publishutil.utils.EventLogger;
import org.spbu.publishutil.utils.EventLoggerImpl;
import org.spbu.publishutil.utils.ProjectRegistry;
import org.spbu.publishutil.utils.ProjectRegistryImpl;

import static org.spbu.publishutil.utils.UtilConstants.*;

public class PublishUtil implements CJProxy {
	private static PublishUtil publishUtil;
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
			publishUtil = new PublishUtil(args[0], args[1]);
		} else if (args.length == 3) {
			publishUtil = new PublishUtil(args[0], args[1], args[2]);
		} else if (args.length == 4) {
			publishUtil = new PublishUtil(args[0], args[1], args[2], args[3]);
		} else if (args.length == 5) {
			publishUtil = new PublishUtil(args[0], args[1], args[2], args[3], args[4]);
		}
		
		System.out.println("Returned " + publishUtil.run());
	}
	
	public static void printHelp() {
		System.out.println("Usage: publishutil <directory> <mainfile> [inf product id] [<format> <destinationfilepath>]");
	}
	
	public static String getRunningAppPath() {
		String s;
		try {
			s = new File(PublishUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// if path contains spaces for some reason it converts to %20 and then it is unusable
		s = s.replace("%20", " "); 
		return s;
	}
	
	// dummy constructor for use with CJProxy interface
	public PublishUtil() {
	}
	
	public PublishUtil(String srcDir, String srcFile) {
		this(srcDir, srcFile, null, null, null);
	}
	
	public PublishUtil(String srcDir, String srcFile, String srcId) {
		this(srcDir, srcFile, srcId, null, null);
	}
	
	public PublishUtil(String srcDir, String srcFile, String format, String dstFilepath) {
		this(srcDir, srcFile, null, format, dstFilepath);
	}
	
	public PublishUtil(String srcDir, String srcFile, String srcId, String format, String dstFilepath) {
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
				throw new RuntimeException("Specified source folder is not found: " + srcDir);
			
			if (!srcDirFile.isDirectory())
				throw new RuntimeException("Specified input is not a correct folder path: " + srcDir);
			
			File srcFile = new File(srcDir + "/" + srcFilename);
			if (!srcFile.exists())
				throw new RuntimeException("Specified source file is not found: " + srcFile.getAbsolutePath());
			
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
			
			BasicPublishAction publishAction;
			
			if (this.format == null) {
				publishAction = new ValidatePublishAction();
			} else if (this.format.toLowerCase().equals(DOCBOOK_FORMAT)) {
				publishAction = new BasicPublishAction(dstFile, null, true);
			} else if (this.format.toLowerCase().equals(HTML_FORMAT)) {
				publishAction = new BasicPublishAction(dstFile, BasicPublishAction.DOCBOOK2HTML_FILE, false);
			} else if (this.format.toLowerCase().equals(PDF_FORMAT)) {
				publishAction = new PdfPublishAction(dstFile);
			} else {
				throw new ExportException("Specified format is not supported");
			}

			publishAction.setRegistry(registry);
			publishAction.setLogger(logger);
			publishAction.run(srcFile, srcId);
			
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
	
	@Override
	// this function is invoke by C code
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
