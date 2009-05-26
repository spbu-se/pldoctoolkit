package org.spbu.publishutil.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.spbu.publishutil.PublishUtil;

public class EventLoggerImpl implements EventLogger {
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private static final String DEFAULT_ERROR_FILEPATH = PublishUtil.getRunningAppPath() + "/error.log";
	
	private boolean errorHappened = false;
	private PrintStream errorLog;
	
	public EventLoggerImpl() {
		this(DEFAULT_ERROR_FILEPATH);
	}
	
	public EventLoggerImpl(String errorLogFilepath) {
		File errorLogFile = null;
		try {
			errorLogFile = new File(errorLogFilepath);
			if (errorLogFile.exists())
				errorLogFile.delete();
			
			errorLogFile.createNewFile();
			
			errorLog = new PrintStream(errorLogFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void endWork() {
		errorLog.close();
	}
	
	public void logError(String systemId, int lineNumber, String message, int severity) {
		if (severity == SEVERITY_FATAL_ERROR && !errorHappened)
			errorHappened = true;
		errorLog.println(message + " (" + systemId + "  " + lineNumber + ")");
		System.out.println(message + " (" + systemId + "  " + lineNumber + ")");
	}

	public void logException(Exception e, boolean displayMessage) {
		errorHappened = true;
		if (displayMessage) {
			System.out.println(e.getMessage());
			errorLog.println(e.getMessage());
		}
		e.printStackTrace();
	} 

	public void logEvent(String message) {
		System.out.println("[" + getCurrentTime() + "] " + message);
	}
	
	public boolean isErrorHappened() {
		return errorHappened;
	}
	
	private String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    return sdf.format(cal.getTime());
	}
}
