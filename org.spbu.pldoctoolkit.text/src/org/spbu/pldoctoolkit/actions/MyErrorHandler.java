package org.spbu.pldoctoolkit.actions;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MyErrorHandler implements ErrorHandler {
	boolean result = true;
	public String errorMessage;
	
	public boolean succeded()
	{
		return result;
	}	
	
	public void error(SAXParseException exception) throws SAXException {
		errorMessage += exception.getMessage() + "/n";
		result = false;
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		errorMessage += exception.getMessage() + "/n";;
		result = false;
	}

	public void warning(SAXParseException exception) throws SAXException {
		errorMessage += exception.getMessage() + "/n";;
		result = false;
	}

}
