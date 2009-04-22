package org.spbu.pldoctoolkit.exportutil;

import java.io.File;
import java.rmi.server.ExportException;

import org.spbu.pldoctoolkit.exportutil.utils.EventLogger;
import org.spbu.pldoctoolkit.exportutil.utils.EventLoggerImpl;
import org.spbu.pldoctoolkit.exportutil.utils.ProjectRegistry;
import org.spbu.pldoctoolkit.exportutil.utils.ProjectRegistryImpl;

public class ExportUtil {
	public static final String DRL_FILE_EXTENSION = "drl";
	
	public static final String HTML_FORMAT = "html";
	public static final String PDF_FORMAT = "pdf";
	
	private static ExportUtil exportUtil;
	private EventLogger logger = null;
	
	private final String srcDir;
	private final String srcFilename;
	private final String dstFilepath;
	private final String srcId;
	private final String format;
	
	public static void main(String[] args) {
		if (args.length < 2 || args.length > 5 || args.length == 3) {
			printHelp();
			return;
		} else if (args.length == 2) {
			exportUtil = new ExportUtil(args[0], args[1]);
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
	
	public ExportUtil(String srcDir, String srcFile) {
		this(srcDir, srcFile, null, null, null);
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
	
	public void run() {
		logger = new EventLoggerImpl();
		try {
			File srcDirFile = new File(srcDir);
			if (!srcDirFile.exists())
				throw new ExportException("Specified folder is not found");
			
			File srcFile = new File(srcDir + "\\" + srcFilename);
			if (!srcFile.exists())
				throw new ExportException("Specified file is not found");
			
			File dstFile = null;
			if (dstFilepath != null) {
				dstFile = new File(dstFilepath);
				if (dstFile.exists())
					dstFile.delete();
			}
			
			ProjectRegistry registry = new ProjectRegistryImpl();
			registry.registerDirectory(srcDirFile);
			
			BasicExportAction exportAction;
			
			if (this.format == null) {
				exportAction = new ValidateDrlAction();
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

		} catch (ExportException e) {
			logger.logExeption(e);
		} catch (Exception e) {
			logger.logExeption(e);
		} finally {
			logger.endWork();
		}
	}

}
