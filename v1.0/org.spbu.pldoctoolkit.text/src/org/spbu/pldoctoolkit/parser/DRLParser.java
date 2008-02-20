package org.spbu.pldoctoolkit.parser;

import java.io.IOException;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import com.thaiopensource.xml.sax.Jaxp11XMLReaderCreator;
//import org.apache.xerces.parsers.SAXParser;

public class DRLParser {
	private static XMLReader xmlReader;// = new Jaxp11XMLReaderCreator().createXMLReader();
	private static DRLContentHandler contetnHandler;
	
	static
	{
		try {
			xmlReader = new Jaxp11XMLReaderCreator().createXMLReader();
			contetnHandler = new DRLContentHandler();		
			xmlReader.setContentHandler(contetnHandler);
		}
		catch (SAXException e) {
			System.err.print(e.getMessage());
		}
	}
	
	public static DRLDocument parse(InputSource input, ProjectContent project)
	{		
		try {			 
						
			xmlReader.parse(input);			
		}
		catch (IOException e) {
			System.err.print(e.getMessage());
		}
		catch (SAXException e) {
			System.err.print(e.getMessage());
		}
		
		return ((DRLContentHandler)xmlReader.getContentHandler()).doc;
		//xmlReader.setErrorHandler(errorHandler);
	}	
	
	public void setProject(ProjectContent project) {
		contetnHandler.setProject(project);
	}
}
