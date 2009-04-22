package org.spbu.pldoctoolkit.exportutil.utils;

public interface EventLogger {
	public static final int SEVERITY_INFO = 0;
	public static final int SEVERITY_WARNING = 1;
	public static final int SEVERITY_ERROR = 2;
	
	public void logEvent(String message);
	public void logExeption(Exception e);
	public void logError(String systemId, int lineNumber, String message, int severity);
	
	public void endWork();
}
