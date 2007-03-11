package org.spbu.pldoctoolkit.actions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MarkingErrorHandler implements ErrorHandler {
	private IResource resource;
	
	public void setResource(IResource resource) {
		this.resource = resource;
	}
	
	public void error(SAXParseException exception) throws SAXException {
		if (resource == null) {
			System.out.println(exception);
			return;
		}
		try {
			IMarker marker = resource.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.LINE_NUMBER, exception.getLineNumber());
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			marker.setAttribute(IMarker.MESSAGE, exception.getMessage());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		if (resource == null) {
			System.out.println(exception);
			return;
		}
		try {
			IMarker marker = resource.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.LINE_NUMBER, exception.getLineNumber());
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			marker.setAttribute(IMarker.MESSAGE, exception.getMessage());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void warning(SAXParseException exception) throws SAXException {
		if (resource == null) {
			System.out.println(exception);
			return;
		}
		try {
			IMarker marker = resource.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.LINE_NUMBER, exception.getLineNumber());
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
			marker.setAttribute(IMarker.MESSAGE, exception.getMessage());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
