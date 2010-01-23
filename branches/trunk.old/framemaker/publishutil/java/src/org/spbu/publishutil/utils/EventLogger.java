package org.spbu.publishutil.utils;

public interface EventLogger {
	public static final int SEVERITY_INFO = 0;
	public static final int SEVERITY_WARNING = 1;
	public static final int SEVERITY_ERROR = 2;
	public static final int SEVERITY_FATAL_ERROR = 3;
	
	public void logEvent(String message);
	public void logException(Exception e, boolean displayMessage);
	public void logError(String systemId, int lineNumber, String message, int severity);
	public boolean isErrorHappened();
	
	public void endWork();
}
