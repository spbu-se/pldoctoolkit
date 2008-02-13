package org.spbu.pldoctoolkit.parser;

import java.io.IOException;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import com.thaiopensource.xml.sax.Jaxp11XMLReaderCreator;
//import org.apache.xerces.parsers.SAXParser;

public class DRLParser {
	private static XMLReader xmlReader;// = new Jaxp11XMLReaderCreator().createXMLReader();
	
	static
	{
		try {
			xmlReader = new Jaxp11XMLReaderCreator().createXMLReader();			
		}
		catch (SAXException e) {
			System.err.print(e.getMessage());
		}
	}
	
	public static DRLDocument parse(InputSource input)
	{		
		try {
			xmlReader.setContentHandler(new DRLContentHandler());
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

}
