package org.spbu.pldoctoolkit.exportutil.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventLoggerImpl implements EventLogger {
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private static final String DEFAULT_ERROR_FILEPATH = "error.log";
	
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
		errorLog.println("*** Marker: " + message + " @ " + systemId + " line " + lineNumber);
		System.out.println("*** Marker: " + message + " @ " + systemId + " line " + lineNumber);
	}

	public void logExeption(Exception e) {
		e.printStackTrace();
	} 

	public void logEvent(String message) {
		System.out.println("[" + getCurrentTime() + "] " + message);
	}
	
	private String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    return sdf.format(cal.getTime());
	}
}
